
package studentgradetrackergui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel avgLabel, highLabel, lowLabel;
    private ArrayList<Student> students;

    public StudentGradeTrackerGUI() {
        students = new ArrayList<>();

        setTitle("Student Grade Tracker ?");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(243, 229, 245)); // Soft Lavender

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("📝 Student Information"));
        inputPanel.setBackground(new Color(225, 190, 231)); // Light Purple

        JLabel nameLbl = styledLabel(" Name:");
        inputPanel.add(nameLbl);

        nameField = new JTextField();
        styleTextField(nameField);
        inputPanel.add(nameField);

        JButton addButton = styledButton(" Add Student", new Color(142, 36, 170));
        inputPanel.add(addButton);

        JLabel gradeLbl = styledLabel(" Grade:");
        inputPanel.add(gradeLbl);

        gradeField = new JTextField();
        styleTextField(gradeField);
        inputPanel.add(gradeField);

        JButton reportButton = styledButton("Generate Report", new Color(106, 27, 154));
        inputPanel.add(reportButton);

        add(inputPanel, BorderLayout.NORTH);

        String[] columns = {"Name", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.setBackground(Color.WHITE);
        table.setForeground(new Color(74, 20, 140)); // Dark Purple
        table.setGridColor(new Color(186, 104, 200)); // Medium Purple
        table.setSelectionBackground(new Color(142, 36, 170)); // Purple Highlight
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
        table.setRowHeight(25);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(106, 27, 154)); // Deep Purple
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("📋 Student Records"));
        tableScroll.getViewport().setBackground(new Color(243, 229, 245));
        add(tableScroll, BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("📊 Summary"));
        summaryPanel.setBackground(new Color(225, 190, 231));

        avgLabel = styledSummaryLabel("📐 Average: -", new Color(74, 20, 140));
        highLabel = styledSummaryLabel("🏆 Highest: -", new Color(56, 142, 60));
        lowLabel = styledSummaryLabel("⬇️ Lowest: -", new Color(211, 47, 47));

        summaryPanel.add(avgLabel);
        summaryPanel.add(highLabel);
        summaryPanel.add(lowLabel);

        add(summaryPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> generateReport());
    }

    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(74, 20, 140)); // Dark Purple
        label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
        return label;
    }

    private JButton styledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(74, 20, 140));
        field.setCaretColor(new Color(106, 27, 154));
        field.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200)));
    }

    private JLabel styledSummaryLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
        label.setForeground(color);
        return label;
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            students.add(new Student(name, grade));
            tableModel.addRow(new Object[]{name, grade});

            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Invalid grade. Enter a number.");
        }
    }

    private void generateReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No student data available.");
            return;
        }

        double sum = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            sum += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = sum / students.size();

        avgLabel.setText("Average: " + String.format("%.2f", average));
        highLabel.setText(" Highest: " + highest + " (" + topStudent + ")");
        lowLabel.setText("️ Lowest: " + lowest + " (" + lowStudent + ")");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}
