package com.jobportal.student_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.student_server.model.Student;
import com.jobportal.student_server.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import({
        StudentController.class,
        StudentControllerTest.TestConfig.class
})
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- TEST CONFIG ----------
    @Configuration
    static class TestConfig {
        @Bean
        StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }

    private Student sampleStudent() {
        Student s = new Student();
        s.setId(1L);
        s.setUsn("1MS22CS001");
        s.setName("Sooraj");
        s.setEmail("sooraj@gmail.com");
        s.setBranch("CSE");
        s.setSemester(7);
        s.setCgpa(9.2);
        s.setResumeUrl("resume.pdf");
        return s;
    }

    // CREATE
    @Test
    void createStudent_success() throws Exception {
        Student student = sampleStudent();

        Mockito.when(studentService.createStudent(Mockito.any(Student.class)))
                .thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sooraj"));
    }

    // GET BY ID
    @Test
    void getStudent_success() throws Exception {
        Mockito.when(studentService.getStudent(1L))
                .thenReturn(sampleStudent());

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usn").value("1MS22CS001"));
    }

    // GET ALL
    @Test
    void getAllStudents_success() throws Exception {
        Mockito.when(studentService.getAllStudents())
                .thenReturn(List.of(sampleStudent()));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // UPDATE
    @Test
    void updateStudent_success() throws Exception {
        Student updatedStudent = sampleStudent();
        updatedStudent.setName("Updated Name");
        updatedStudent.setCgpa(9.5);

        Mockito.when(studentService.updateStudent(Mockito.eq(1L), Mockito.any(Student.class)))
                .thenReturn(updatedStudent);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.cgpa").value(9.5));
    }


    // DELETE
    @Test
    void deleteStudent_success() throws Exception {
        Mockito.doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }
}
