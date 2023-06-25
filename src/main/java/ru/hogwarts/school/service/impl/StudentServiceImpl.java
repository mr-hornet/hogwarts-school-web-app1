package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private static Long idCounter = 1L;

    @Override
    public Student add(Student student) {
        students.put(idCounter++, student);
        return student;
    }

    @Override
    public Student remove(Long id) {
        if (students.containsKey(id)) {
            Student student = students.get(id);
            students.remove(id);
            return student;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Student update(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Student> getAll() {
        return students.values();
    }

    @Override
    public Student getById(Long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Student> getByAge(Integer age) {
        if (age < 16 || age > 90) {
            throw new IncorrectArgumentException();
        }
        return students.values().stream()
                .filter(e -> e.getAge().equals(age))
                .collect(Collectors.toList());
    }
}
