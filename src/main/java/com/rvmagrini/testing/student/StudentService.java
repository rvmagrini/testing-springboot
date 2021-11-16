package com.rvmagrini.testing.student;

import com.rvmagrini.testing.student.exception.BadRequestException;
import com.rvmagrini.testing.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) throws BadRequestException {
        Boolean existsEmail = studentRepository.selectExistsEmail(student.getEmail());
        if (existsEmail) {
            throw new BadRequestException("Email " + student.getEmail() + " already taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) throws StudentNotFoundException {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

}