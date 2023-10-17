package org.example;

public class Main {
    public static void main(String[] args) {
        GeneratorId generatorId = new GeneratorId();
        InMemoryTaskDao inMemoryTaskDao = new InMemoryTaskDao();
        Manager manager = new Manager(inMemoryTaskDao);
        Task task1 = manager.createTask(new Task("Задача1", "Выполнить задачу1", generatorId.getTaskId(),
                Status.NEW));
        manager.saveTaskInMemory(task1);

        Task task2 = manager.createTask(new Task("Задача2", "Выполнить задачу2", generatorId.getTaskId(),
                Status.NEW));
        manager.saveTaskInMemory(task2);

        Epic epic1 = manager.createEpic(new Epic("Эпик1", "Выполнить эпик1", generatorId.getTaskId()));
        manager.saveEpicInMemory(epic1);

        SubTask subTask1 = manager.createSubTask(new SubTask("Подзадача1", "Выполнить подзадачу1",
                generatorId.getTaskId(), Status.NEW, epic1));
        manager.saveSubtaskInMemory(subTask1);

        SubTask subTask2 = manager.createSubTask(new SubTask("Подзадача2", "Выполнить подзадачу2",
                generatorId.getTaskId(), Status.NEW, epic1));
        manager.saveSubtaskInMemory(subTask2);

        Epic epic2 = manager.createEpic(new Epic("Эпик2", "Выполнить эпик2", generatorId.getTaskId()));
        manager.saveEpicInMemory(epic2);

        SubTask subTask = manager.createSubTask(new SubTask("Подзадача", "Выполнить подзадачу",
                generatorId.getTaskId(), Status.NEW, epic2));
        manager.saveSubtaskInMemory(subTask);
    }
}