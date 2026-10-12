package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        validateEmail(email);
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, batch);
        students.add(student);
        return student;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student", id);
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    public void updateStudent(int id, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);
        if (email != null && !email.isEmpty()) {
            validateEmail(email);
            student.setEmail(email);
        }
        if (batch != null && !batch.isEmpty()) {
            student.setBatch(batch);
        }
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        findStudentById(id).setActive(false);
    }

    public void removeStudent(int id) throws EntityNotFoundException {
        students.remove(findStudentById(id));
    }

    private void validateEmail(String email) throws InvalidInputException {
        if (!InputValidator.isValidEmail(email)) {
            throw new InvalidInputException("'" + email + "' is not a valid email address.");
        }
    }
}
