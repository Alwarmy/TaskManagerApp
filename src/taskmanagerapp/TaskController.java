package taskmanagerapp;

public class TaskController {
    private final TaskModel model;
    private final TaskView view;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;

        this.model.addObserver(view);

        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getAddButton().addActionListener(event -> addTask());

        view.getUpdateButton().addActionListener(event -> updateTask());

        view.getDeleteButton().addActionListener(event -> deleteTask());

        view.getCompleteButton().addActionListener(event -> markCompleted());

        view.getPendingButton().addActionListener(event -> markPending());

        view.getClearButton().addActionListener(event -> view.clearFields());

        view.getTaskTable().getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                view.fillFieldsFromSelectedRow();
            }
        });
    }

    private void addTask() {
        try {
            String title = view.getTaskTitle();
            String description = view.getTaskDescription();

            model.addTask(title, description);

            javax.swing.SwingUtilities.invokeLater(() -> view.clearFields());

        } catch (IllegalArgumentException exception) {
            view.showMessage(exception.getMessage());
        }
    }

    private void updateTask() {
        int selectedTaskId = view.getSelectedTaskId();

        if (selectedTaskId == -1) {
            view.showMessage("Please select a task to update.");
            return;
        }

        try {
            String title = view.getTaskTitle();
            String description = view.getTaskDescription();

            model.updateTask(selectedTaskId, title, description);
            view.clearFields();
        } catch (IllegalArgumentException exception) {
            view.showMessage(exception.getMessage());
        }
    }

    private void deleteTask() {
        int selectedTaskId = view.getSelectedTaskId();

        if (selectedTaskId == -1) {
            view.showMessage("Please select a task to delete.");
            return;
        }

        model.deleteTask(selectedTaskId);
        view.clearFields();
    }

    private void markCompleted() {
        int selectedTaskId = view.getSelectedTaskId();

        if (selectedTaskId == -1) {
            view.showMessage("Please select a task to mark as completed.");
            return;
        }

        model.markTaskCompleted(selectedTaskId);
        view.clearFields();
    }

    private void markPending() {
        int selectedTaskId = view.getSelectedTaskId();

        if (selectedTaskId == -1) {
            view.showMessage("Please select a task to mark as pending.");
            return;
        }

        model.markTaskPending(selectedTaskId);
        view.clearFields();
    }
}