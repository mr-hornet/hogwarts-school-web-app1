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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentServiceImpl studentService;
    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createStudent() throws Exception {

        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(jsonObjectStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void getStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void getUnknownStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUnknownStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));
        MOCK_STUDENT.setName(MOCK_STUDENT_NEW_NAME);

        JSONObject jsonObjectUpdateStudent = new JSONObject();
        jsonObjectUpdateStudent.put("id", MOCK_STUDENT.getId());
        jsonObjectUpdateStudent.put("name", MOCK_STUDENT.getName());
        jsonObjectUpdateStudent.put("age", MOCK_STUDENT.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .content(jsonObjectUpdateStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT.getName()))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT.getAge()));
    }

    @Test
    public void getAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void getStudentsBetweenAge() throws Exception {
        when(studentRepository.findStudentsByAgeBetween(anyInt(), anyInt())).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age?firstAge=20&secondAge=30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void getFacultyByStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        JSONObject jsonObjectStudent = new JSONObject();
        jsonObjectStudent.put("name", MOCK_STUDENT_NAME);
        jsonObjectStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/faculty/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}