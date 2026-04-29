package taskmanagerapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TaskView extends JFrame implements TaskObserver {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JTable taskTable;
    private final DefaultTableModel tableModel;

    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;
    private final JButton completeButton;
    private final JButton pendingButton;
    private final JButton clearButton;

    public TaskView() {
        setTitle("Task Manager Application");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleField = new JTextField();
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");
        completeButton = new JButton("Mark Completed");
        pendingButton = new JButton("Mark Pending");
        clearButton = new JButton("Clear Fields");

        String[] columns = {"ID", "Title", "Description", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        buildLayout();
    }

    private void buildLayout() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Task Details"));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.add(new JLabel("Title:"), BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout(5, 5));
        descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        fieldsPanel.add(titlePanel);
        fieldsPanel.add(descriptionPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 8, 8));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(pendingButton);
        buttonPanel.add(clearButton);

        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Task List"));
        tablePanel.add(new JScrollPane(taskTable), BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    @Override
    public void onTasksChanged(List<Task> tasks) {
        tableModel.setRowCount(0);

        for (Task task : tasks) {
            String status = task.isCompleted() ? "Completed" : "Pending";

            tableModel.addRow(new Object[]{
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    status
            });
        }
    }

    public String getTaskTitle() {
        return titleField.getText();
    }

    public String getTaskDescription() {
        return descriptionArea.getText();
    }

    public int getSelectedTaskId() {
        int selectedRow = taskTable.getSelectedRow();

        if (selectedRow == -1) {
            return -1;
        }

        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    public void fillFieldsFromSelectedRow() {
        int selectedRow = taskTable.getSelectedRow();

        if (selectedRow != -1) {
            titleField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            descriptionArea.setText(tableModel.getValueAt(selectedRow, 2).toString());
        }
    }

    public void clearFields() {
        titleField.setText("");
        descriptionArea.setText("");
        taskTable.clearSelection();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getCompleteButton() {
        return completeButton;
    }

    public JButton getPendingButton() {
        return pendingButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JTable getTaskTable() {
        return taskTable;
    }
}