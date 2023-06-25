package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private static Long idCounter = 1L;

    @Override
    public Faculty add(Faculty faculty) {
        return faculties.put(idCounter++, faculty);
    }

    @Override
    public Faculty remove(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            return faculties.put(faculty.getId(), faculty);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Faculty> getAll() {
        return faculties.values();
    }

    @Override
    public Faculty getById(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        if (!StringUtils.hasText(color)) {
            throw new IncorrectArgumentException();
        }
        return faculties.values().stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
