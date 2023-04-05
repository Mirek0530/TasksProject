package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTestSuite {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void testGetTasks() {
        //Given
        Task task = new Task(10L, "test_task", "content");
        TaskDto taskDto = new TaskDto(10L, "test_task_dto", "content_dto");

        when(dbService.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(List.of(taskDto));

        //When
        ResponseEntity<List<TaskDto>> tasks = taskController.getTasks();

        //Then
        assertEquals(1, tasks.getBody().size());
    }

    @Test
    void testGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(10L, "task", "content");
        TaskDto taskDto = new TaskDto(10L, "test_task_dto", "content_dto");

        when(dbService.getTask(10L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        //When
        ResponseEntity<TaskDto> result = taskController.getTask(10L);

        //Then
        assertEquals("test_task_dto", result.getBody().getTitle());
    }

    @Test
    void testCreateTask() {
        //Given
        Task task = new Task(11L, "task", "content");
        TaskDto taskDto = new TaskDto(11L, "test_task_dto", "content_dto");

        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        //When
        ResponseEntity<Void> response = taskController.createTask(taskDto);

        //Then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTask() {
        //Given
        Task task = new Task(11L, "task", "content");
        TaskDto taskDto = new TaskDto(11L, "test_task_dto", "content_dto");

        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When
        ResponseEntity<TaskDto> response = taskController.updateTask(taskDto);

        //Then
        assertEquals("content_dto", response.getBody().getContent());
    }

    @Test
    void testDeleteTask() {
        //When
        ResponseEntity<Void> response = taskController.deleteTask(11L);

        //Then
        assertEquals(204, response.getStatusCodeValue());
    }
}
