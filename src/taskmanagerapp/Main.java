package taskmanagerapp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskModel model = new TaskModel();
            TaskView view = new TaskView();
            new TaskController(model, view);

            view.setVisible(true);
        });
    }
}