package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;


import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.add(faculty));
    }

    @PutMapping
    @Operation(summary = "Обновление факультета")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.update(faculty));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.remove(id));
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета по ID")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getById(id));
    }

    @GetMapping()
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAll() {
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("filter")
    @Operation(summary = "Получение факультетов по названию и цвету")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getByNameOrColor(name, color));
    }

    @GetMapping("students/{facultyId}")
    @Operation(summary = "Получение студентов факультета")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long facultyId) {
        List<Student> students = facultyService.getById(facultyId).getStudents();
        return ResponseEntity.ok(students);
    }
}
