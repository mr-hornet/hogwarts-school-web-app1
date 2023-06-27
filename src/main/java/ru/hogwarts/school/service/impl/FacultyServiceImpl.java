package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty faculty = getById(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty findFaculty = getById(faculty.getId());
        facultyRepository.save(faculty);
        return findFaculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        if (!StringUtils.hasText(color)) {
            throw new IncorrectArgumentException();
        }
        return getAll().stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
