package com.jobportal.student_server.repository;

import com.jobportal.student_server.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student createStudent() {
        Student s = new Student();
        s.setUsn("1MS22CS001");
        s.setName("Sooraj");
        s.setEmail("sooraj@gmail.com");
        s.setBranch("CSE");
        s.setSemester(7);
        s.setCgpa(9.2);
        s.setResumeUrl("resume.pdf");
        return s;
    }

    @Test
    void saveStudent_success() {
        Student saved = studentRepository.save(createStudent());

        assertNotNull(saved.getId());
    }

    @Test
    void existsByUsn_success() {
        studentRepository.save(createStudent());

        boolean exists = studentRepository.existsByUsn("1MS22CS001");

        assertTrue(exists);
    }

    @Test
    void existsByUsn_false() {
        boolean exists = studentRepository.existsByUsn("INVALID");

        assertFalse(exists);
    }
}
