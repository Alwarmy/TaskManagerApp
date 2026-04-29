package taskmanagerapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskModel {
    private final List<Task> tasks;
    private final List<TaskObserver> observers;
    private int nextId;

    public TaskModel() {
        this.tasks = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.nextId = 1;
    }

    public void addObserver(TaskObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void addTask(String title, String description) {
        Task task = new Task(nextId, title, description, false);
        tasks.add(task);
        nextId++;
        notifyObservers();
    }

    public void updateTask(int id, String newTitle, String newDescription) {
        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);

            if (currentTask.getId() == id) {
                Task updatedTask = currentTask.withUpdatedDetails(newTitle, newDescription);
                tasks.set(i, updatedTask);
                notifyObservers();
                return;
            }
        }
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        notifyObservers();
    }

    public void markTaskCompleted(int id) {
        changeTaskStatus(id, true);
    }

    public void markTaskPending(int id) {
        changeTaskStatus(id, false);
    }

    private void changeTaskStatus(int id, boolean completed) {
        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);

            if (currentTask.getId() == id) {
                Task updatedTask = currentTask.withCompletedStatus(completed);
                tasks.set(i, updatedTask);
                notifyObservers();
                return;
            }
        }
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    private void notifyObservers() {
        List<Task> readOnlyTasks = Collections.unmodifiableList(new ArrayList<>(tasks));

        for (TaskObserver observer : observers) {
            observer.onTasksChanged(readOnlyTasks);
        }
    }
}