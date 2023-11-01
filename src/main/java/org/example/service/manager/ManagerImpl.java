package org.example.service.manager;

import org.example.dao.InMemoryTaskDao;
import org.example.dao.TaskDao;
import org.example.dto.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.dto.Status;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.dto.UpdateEpicDto;
import org.example.dto.UpdateSubTaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.service.Converter;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerImpl implements Manager {
    private final TaskDao taskDao = new InMemoryTaskDao();
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
        Epic epic = taskDao.getEpicById(id);
        if (epic != null) {
            epic.setStatus(getEpicStatus(epic));
            return epic;
        }
        return null;
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

            if (hashSet.size() == 1 && hashSet.contains(Status.NEW) || epic.getSubtasksId().isEmpty()) {
                return Status.NEW;
            } else if (hashSet.size() == 1 && hashSet.contains(Status.DONE)) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
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
