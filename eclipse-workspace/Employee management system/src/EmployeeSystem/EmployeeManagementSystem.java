package EmployeeSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeManagementSystem extends JFrame {

    private JTextField idField, nameField, designationField, salaryField;
    private JButton addButton, deleteButton, editButton, calculateButton, markAttendanceButton;
    private JTextArea displayArea;

    private Connection connection;

    public EmployeeManagementSystem() {
        setTitle("Employee Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        displayArea = new JTextArea(10, 40);

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField(10);
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Designation:"));
        designationField = new JTextField(10);
        inputPanel.add(designationField);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField(10);
        inputPanel.add(salaryField);

        addButton = new JButton("Add Employee");
        deleteButton = new JButton("Delete Employee");
        editButton = new JButton("Edit Employee");
        calculateButton = new JButton("Calculate Payroll");
        markAttendanceButton = new JButton("Mark Attendance");

        addButton.addActionListener(new ButtonClickListener());
        deleteButton.addActionListener(new ButtonClickListener());
        editButton.addActionListener(new ButtonClickListener());
        calculateButton.addActionListener(new ButtonClickListener());
        markAttendanceButton.addActionListener(new ButtonClickListener());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(markAttendanceButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/employee";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database.");
            System.exit(1);
        }
    }

    private void addEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String designation = designationField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        String query = "INSERT INTO employees (id, name, designation, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, designation);
            statement.setDouble(4, salary);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add employee.");
        }
    }

    private void deleteEmployee() {
        int id = Integer.parseInt(idField.getText());
        String query = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete employee.");
        }
    }

    private void editEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String designation = designationField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        String query = "UPDATE employees SET name=?, designation=?, salary=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, designation);
            statement.setDouble(3, salary);
            statement.setInt(4, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Employee edited successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to edit employee.");
        }
    }

    private void calculatePayroll() {
        String query = "SELECT SUM(salary) FROM employees";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                double totalPayroll = resultSet.getDouble(1);
                JOptionPane.showMessageDialog(this, "Total Payroll: $" + totalPayroll);
            } else {
                JOptionPane.showMessageDialog(this, "No employees found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to calculate payroll.");
        }
    }

    private void markAttendance() {
        int employeeId = Integer.parseInt(idField.getText());
        String query = "INSERT INTO attendance (employee_id, date) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendance marked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to mark attendance.");
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                addEmployee();
            } else if (e.getSource() == deleteButton) {
                deleteEmployee();
            } else if (e.getSource() == editButton) {
                editEmployee();
            } else if (e.getSource() == calculateButton) {
                calculatePayroll();
            } else if (e.getSource() == markAttendanceButton) {
                markAttendance();
            }
        }
    }

    public static void main(String[] args) {
        new EmployeeManagementSystem();
    }
}

