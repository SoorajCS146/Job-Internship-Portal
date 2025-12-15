package com.jobportal.placement_service.client;

import com.jobportal.placement_service.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentClient {

    @GetMapping("/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);

    @GetMapping("/students/usn/{usn}")
    StudentDTO getStudentByUsn(@PathVariable("usn") String usn);
}

