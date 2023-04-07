package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetOneTask() throws Exception {
        //Given
        Task task = new Task(1L, "title", "content");
        TaskDto taskDto = new TaskDto(1L, "taskDto", "contentDto");

        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(dbService.getTask(anyLong())).thenReturn(task);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/tasks/" + 1)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("taskDto")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("contentDto")));

    }

    @Test
    void shouldGetListOfTasks() throws Exception {
        //Given
        TaskDto task1 = new TaskDto(1L, "task1", "content1");
        TaskDto task2 = new TaskDto(2L, "task2", "content2");

        when(dbService.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(List.of(task1, task2));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/tasks")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("content1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id",Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("task2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("content2")));

    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Long taskId = 1L;

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "content");
        TaskDto taskDto = new TaskDto(10L, "taskDto", "contentDto");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/v1/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("taskDto")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("contentDto")));

    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "content");
        TaskDto taskDto = new TaskDto(10L, "taskDto", "contentDto");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
