//package com.jobportal.student-server.repository;
package com.jobportal.student_server.repository;
import com.jobportal.student_server.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsn(String usn);
    Optional<Student> findByUsn(String usn);

}
