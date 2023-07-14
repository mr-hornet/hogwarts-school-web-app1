package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);


    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method for remove faculty");
        Faculty faculty = getById(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        Faculty findFaculty = getById(faculty.getId());
        facultyRepository.save(faculty);
        return findFaculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getById(Long id) {
        logger.info("Was invoked method for get by ID faculty");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            logger.error("Faculty not found, id = " + id);
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get by name or color faculty");
        if (!StringUtils.hasText(name) && !StringUtils.hasText(color)) {
            logger.error("Incorrect arguments to find faculties");

            throw new IncorrectArgumentException();
        }
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoked method for get longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}
