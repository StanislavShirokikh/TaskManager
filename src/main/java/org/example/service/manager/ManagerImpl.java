package org.example.service.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.in_memory.TaskDao;
import org.example.entity.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.entity.Status;
import org.example.entity.SubTask;
import org.example.entity.Task;
import org.example.dto.UpdateEpicDto;
import org.example.dto.UpdateSubTaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.service.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ManagerImpl implements Manager {
    private final TaskDao taskDao;

    @Override
    public int saveTask(SaveTaskDto saveTaskDto) {
        Task task = Converter.convertToTask(saveTaskDto);
        return taskDao.saveTask(task);
    }

    @Override
    public int saveEpic(SaveEpicDto saveEpicDto) {
        Epic epic = Converter.converterToEpic(saveEpicDto);
        return taskDao.saveEpic(epic);
    }

    @Override
    public int saveSubtask(SaveSubTaskDto saveSubTaskDto) {
        SubTask subTask = Converter.converterToSubTask(saveSubTaskDto);
        subTask.setStatus(Status.NEW);
        return taskDao.saveSubtask(subTask);
    }


    @Override
    public Task getTaskById(int id) {
        return taskDao.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        log.info("Получение эпика из памяти {}", id);
        Epic epic = taskDao.getEpicById(id);
        if (epic != null) {
            epic.setStatus(getEpicStatus(epic));
            log.info("Найден эпик его статус равен {}", epic.getStatus());
        } else {
            log.info("epic was not found with id = [{}]", id);
        }

        return epic;
    }

    @Override
    public SubTask getSubTasksById(int id) {
        return taskDao.getSubTasksById(id);
    }

    @Override
    public void updateTask(UpdateTaskDto updateTaskDto) {
        Task task = Converter.convertToTask(updateTaskDto);
        taskDao.updateTask(task);
    }

    @Override
    public void updateEpic(UpdateEpicDto updateEpicDto) {
        Epic epic = Converter.convertToEpic(updateEpicDto);
        taskDao.updateEpic(epic);
    }

    @Override
    public void updateSubTask(UpdateSubTaskDto updateSubTaskDto) {
        SubTask subTask = Converter.converterToSubTask(updateSubTaskDto);
        taskDao.updateSubTask(subTask);
    }

    @Override
    public void removeTaskById(int id) {
        taskDao.removeTaskById(id);
    }

    @Override
    public void removeEpicById(int id) {
        taskDao.removeEpicById(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        taskDao.removeSubTaskById(id);
    }

    private Status getEpicStatus(Epic epic) {
        List<SubTask> list = getSubtasks(epic);

        Set<Status> hashSet = list.stream()
                .map(SubTask::getStatus)
                .collect(Collectors.toSet());

        Status status;
        if (hashSet.size() == 1 && hashSet.contains(Status.NEW) || epic.getSubtasksId().isEmpty()) {
            status = Status.NEW;
        } else if (hashSet.size() == 1 && hashSet.contains(Status.DONE)) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }

        log.info("Calculated status for epic with id = [{}] is [{}]", epic.getId(), status);
        return status;
    }

    private List<SubTask> getSubtasks(Epic epic) {
        List<Integer> list = epic.getSubtasksId();
        List<SubTask> subTasks = new ArrayList<>();
        for (Integer integer : list) {
            SubTask subTask = taskDao.getSubTasksById(integer);
            subTasks.add(subTask);
        }
        return subTasks;
    }
}
