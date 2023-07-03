package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createFaculty() throws Exception {

        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

        JSONObject jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", MOCK_FACULTY_NAME);
        jsonObjectFaculty.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObjectFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void getFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        JSONObject jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", MOCK_FACULTY_NAME);
        jsonObjectFaculty.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void getUnknownFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        JSONObject jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", MOCK_FACULTY_NAME);
        jsonObjectFaculty.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        JSONObject jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", MOCK_FACULTY_NAME);
        jsonObjectFaculty.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUnknownFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        JSONObject jsonObjectFaculty = new JSONObject();
        jsonObjectFaculty.put("name", MOCK_FACULTY_NAME);
        jsonObjectFaculty.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));
        MOCK_FACULTY.setName(MOCK_FACULTY_NEW_NAME);

        JSONObject jsonObjectUpdateFaculty = new JSONObject();
        jsonObjectUpdateFaculty.put("id", MOCK_FACULTY.getId());
        jsonObjectUpdateFaculty.put("name", MOCK_FACULTY.getName());
        jsonObjectUpdateFaculty.put("color", MOCK_FACULTY.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObjectUpdateFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY.getColor()));
    }

    @Test
    public void getAllFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void getFacultyByNameOrColor() throws Exception {
        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString())).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter?name=" + MOCK_FACULTY_NAME + "&color=" + MOCK_FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void getStudentByFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/students/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}