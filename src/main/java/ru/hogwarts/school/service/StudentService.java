package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student add(Student student);
    Student remove(Long id);
    Student update(Student student);
    Collection<Student> getAll();
    Student getById(Long id);
    Collection<Student> getByAge(Integer firstAge, Integer secondAge);
}
