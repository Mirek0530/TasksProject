package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(10L, "task_to_map", "content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(10L, task.getId());
        assertEquals("task_to_map", task.getTitle());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(10L , "task", "content_regular_task");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals("content_regular_task", taskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        Task firstTask = new Task(1L, "task1", "first task to do");
        Task secondTask = new Task(3L, "task for you", "special functionality to implement");

        //When
        List<TaskDto> list = taskMapper.mapToTaskDtoList(List.of(firstTask, secondTask));

        //Then
        assertEquals(2, list.size());
    }
}
