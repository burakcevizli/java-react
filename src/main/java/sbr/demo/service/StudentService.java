package sbr.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbr.demo.exception.StudentAlreadyExistsException;
import sbr.demo.exception.StudentNotFoundException;
import sbr.demo.model.Student;
import sbr.demo.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService{

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        if(studentAlreadyExist(student.getEmail())){
            throw new StudentAlreadyExistsException(student.getEmail() + " Already Exist!");
        }
        return studentRepository.save(student);
    }

//Pushlamak için deneme

    @Override
    public Student updateStudent(Student student, Long id) {
        return studentRepository.findById(id).map(st -> {
            st.setFirstName(student.getFirstName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());
            st.setDepartment(student.getDepartment());
            return studentRepository.save(st);
        }).orElseThrow(()-> new StudentNotFoundException("Sorry, this student could not be found!"));
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Sorry,Student with this id is not found: " +id));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)){
            throw new StudentNotFoundException("Sorry, student not found");
        }
        studentRepository.deleteById(id);
    }

    private boolean studentAlreadyExist(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }
}
