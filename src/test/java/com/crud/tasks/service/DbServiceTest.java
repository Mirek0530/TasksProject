package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testSaveTask() {
        //Given
        Task task = new Task(1L, "title", "content");

        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        assertEquals(1L, savedTask.getId());
    }

    @Test
    void testGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "title", "content");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Task result = dbService.getTask(1L);

        //Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAllTasks() {
        //Given
        Task firstTask = new Task(1L, "task1", "first task to do");
        Task secondTask = new Task(3L, "task for you", "special functionality to implement");

        when(taskRepository.findAll()).thenReturn(List.of(firstTask, secondTask));

        //When
        List<Task> result = dbService.getAllTasks();

        //Then
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteTask() {
        //When
        dbService.deleteTask(10L);

        //Then
        Mockito.verify(taskRepository, times(1)).deleteById(10L);
    }
}
