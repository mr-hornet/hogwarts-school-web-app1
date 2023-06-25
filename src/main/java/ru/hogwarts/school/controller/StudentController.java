package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

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

    @GetMapping()
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("age")
    @Operation(summary = "Получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }


}
