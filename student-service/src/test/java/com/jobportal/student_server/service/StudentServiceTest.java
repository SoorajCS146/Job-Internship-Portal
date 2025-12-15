package com.jobportal.student_server.service;

import com.jobportal.student_server.model.Student;
import com.jobportal.student_server.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setId(1L);
        student.setUsn("1MS22CS001");
        student.setName("Sooraj");
        student.setEmail("sooraj@gmail.com");
        student.setBranch("CSE");
        student.setSemester(7);
        student.setCgpa(9.2);
        student.setResumeUrl("resume.pdf");
    }

    // CREATE
    @Test
    void createStudent_success() {
        when(studentRepository.existsByUsn(student.getUsn())).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        Student saved = studentService.createStudent(student);

        assertNotNull(saved);
        assertEquals("Sooraj", saved.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void createStudent_usnAlreadyExists() {
        when(studentRepository.existsByUsn(student.getUsn())).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> studentService.createStudent(student)
        );

        assertEquals("USN already exists!", ex.getMessage());
        verify(studentRepository, never()).save(any());
    }

    // READ
    @Test
    void getStudent_success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudent(1L);

        assertEquals("Sooraj", result.getName());
    }

    @Test
    void getStudent_notFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> studentService.getStudent(1L)
        );

        assertEquals("Student not found", ex.getMessage());
    }

    // READ ALL
    @Test
    void getAllStudents_success() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.getAllStudents();

        assertEquals(1, students.size());
    }

    // UPDATE
    @Test
    void updateStudent_success() {
        Student updated = new Student();
        updated.setName("Updated Name");
        updated.setEmail("updated@gmail.com");
        updated.setBranch("ISE");
        updated.setSemester(8);
        updated.setCgpa(9.5);
        updated.setResumeUrl("new.pdf");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.updateStudent(1L, updated);

        assertEquals("Updated Name", result.getName());
        assertEquals("ISE", result.getBranch());
        verify(studentRepository).save(student);
    }

    // DELETE
    @Test
    void deleteStudent_success() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_notFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> studentService.deleteStudent(1L)
        );

        assertEquals("Student not found", ex.getMessage());
        verify(studentRepository, never()).deleteById(any());
    }
}