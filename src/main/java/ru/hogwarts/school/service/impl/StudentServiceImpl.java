package ru.hogwarts.school.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for add student");

        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for remove student");

        Student student = getById(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");

        Student findStudent = getById(student.getId());
        studentRepository.save(student);
        return findStudent;
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");

        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        logger.info("Was invoked method for get by id student");

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            logger.error("Student not found, id = " + id);

            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Student> getByAge(Integer firstAge, Integer secondAge) {
        logger.info("Was invoked method for get by age student");
        logger.debug("getByAge, firstAge = " + firstAge + ", secondAge = " + secondAge);

        if ((firstAge < 16 || firstAge > 90) && (secondAge < 16 || secondAge > 90)) {
            logger.error("Incorrect arguments to find");

            throw new IncorrectArgumentException();
        }
        return studentRepository.findStudentsByAgeBetween(firstAge, secondAge);
    }

    @Override
    public Integer getCount() {
        logger.info("Was invoked method for get count student");
        return studentRepository.getCount();
    }

    @Override
    public Float getAverageAge() {
        logger.info("Was invoked method for get average age students");

        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");

        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getNamesByA() {
        logger.info("Was invoked method for get student names by A");

        return studentRepository.findAll().stream()
                .filter(c -> c.getName().startsWith("A"))
                .map(c -> c.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByStream() {
        logger.info("Was invoked method for get average age by sream");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @Override
    public void printStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printStudentName);
            printStudentsInNewThread(students.subList(2, 4));
            printStudentsInNewThread(students.subList(4, 6));
        }
    }

    @Override
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printStudentName);
            printStudentsInNewThreadSync(students.subList(2, 4));
            printStudentsInNewThreadSync(students.subList(4, 6));
        }
    }

    private void printStudentName(Student student) {
        logger.info("Student, id = " + student.getId() + ", name = " + student.getName());
    }

    private synchronized void printStudentNameSync(Student student) {
        logger.info("Student, id = " + student.getId() + ", name = " + student.getName());
    }

    private void printStudentsInNewThread(List<Student> students) {
        new Thread(() -> students.forEach(this::printStudentName)).start();
    }

    private void printStudentsInNewThreadSync(List<Student> students) {
        new Thread(() -> students.forEach(this::printStudentNameSync)).start();
    }
}
