package com.jobportal.student_server.service;

import com.jobportal.student_server.model.Student;
import com.jobportal.student_server.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByUsn(student.getUsn())) {
            throw new RuntimeException("USN already exists!");
        }
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getByUsn(String usn) {
        return studentRepository.findByUsn(usn)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    // UPDATE
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudent(id);

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setBranch(updatedStudent.getBranch());
        existing.setSemester(updatedStudent.getSemester());
        existing.setCgpa(updatedStudent.getCgpa());
        existing.setResumeUrl(updatedStudent.getResumeUrl());

        return studentRepository.save(existing);
    }
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
    }
}
