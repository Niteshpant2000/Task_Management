package com.indiasatcom.taskmanagement.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indiasatcom.taskmanagement.dto.TaskRequest;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskManagementControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Desc");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() + 86400000));

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTaskById() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("Fetch Task");
        request.setDescription("Desc");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() + 86400000));
        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        mockMvc.perform(get("/tasks/invalid-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setDescription(" Past Due Date");
        request.setTitle("Past Task");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() + 86400000));


        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

     
        String updateJson = """
                {
                    "title": "Updated Title"
                }
                """;

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void shouldFailWhenDueDateInPast() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setDescription(" Past Due Date");
        request.setTitle("Past Task");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() + 86400000));

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        request.setDescription(" Past Due Date");
        request.setTitle("Past Task");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() - 96400000));

        mockMvc.perform(put("/tasks/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setDescription("Delete Desc");
        request.setTitle("Delete Task");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(new java.sql.Date(System.currentTimeMillis() + 86400000)); // Tomorrow

        MvcResult result = mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated()) 
        .andReturn();

        String response = result.getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingInvalidTask() throws Exception {
        mockMvc.perform(delete("/tasks/invalid-id"))
                .andExpect(status().isNotFound());
    }
}
