package sbr.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbr.demo.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
