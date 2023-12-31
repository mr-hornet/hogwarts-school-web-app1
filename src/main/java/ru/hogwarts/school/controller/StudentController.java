package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.add(student));
    }

    @PutMapping
    @Operation(summary = "Обновление студента")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(student));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.remove(id));
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента по ID")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("age")
    @Operation(summary = "Получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getAge(@RequestParam Integer firstAge, @RequestParam Integer secondAge) {
        return ResponseEntity.ok(studentService.getByAge(firstAge, secondAge));
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета студента")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long studentId) {
        Faculty faculty = studentService.getById(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("count")
    @Operation(summary = "Получение колличества студентов")
    public ResponseEntity<Integer> getStudentsCount() {
        return ResponseEntity.ok(studentService.getCount());
    }

    @GetMapping("age/average")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Float> getStudentsAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("last")
    @Operation(summary = "Получение 5-ти последних студентов")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("names-by-a")
    @Operation(summary = "Получение имен студентов на букву А")
    public ResponseEntity<Collection<String>> getNamesByA() {
        List<String> namesByA = studentService.getNamesByA();
        return ResponseEntity.ok(namesByA);
    }

    @GetMapping("age/average-stream")
    @Operation(summary = "Получение среднего возраста студентов (stream)")
    public ResponseEntity<Double> getAverageAgeByStream() {
        Double averageAge = studentService.getAverageAgeByStream();
        return ResponseEntity.ok(averageAge);
    }



}
