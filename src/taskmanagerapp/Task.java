package taskmanagerapp;

import java.util.Objects;

/**
 * Immutable ADT representing a single task.
 *
 * Representation Invariant (RI):
 * - id > 0
 * - title != null
 * - title is not empty
 * - description != null
 * - completed is either true or false
 *
 * Abstraction Function (AF):
 * A Task represents one task in the task manager application.
 * The id uniquely identifies the task, the title and description describe the task,
 * and completed shows whether the task is completed or still pending.
 */
public final class Task {
    private final int id;
    private final String title;
    private final String description;
    private final boolean completed;

    public Task(int id, String title, String description, boolean completed) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task id must be positive.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title must not be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description must not be empty.");
        }

        this.id = id;
        this.title = title.trim();
        this.description = description.trim();
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Task withUpdatedDetails(String newTitle, String newDescription) {
        return new Task(this.id, newTitle, newDescription, this.completed);
    }

    public Task withCompletedStatus(boolean completed) {
        return new Task(this.id, this.title, this.description, completed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Task)) {
            return false;
        }

        Task other = (Task) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}