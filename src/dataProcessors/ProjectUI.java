package dataProcessors;
import dataClasses.Entry;
import dataClasses.Pair;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProjectUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton fileButton;
    private JButton backButton;
    private boolean findLongest = false;

    public ProjectUI() {
        setTitle("Employee Project Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fileButton = new JButton("Select CSV File");
        backButton = new JButton("Go Back");
        table = new JTable();

        setLayout(new BorderLayout());
        add(fileButton, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        showOptionsDialog();
    }

    private void showOptionsDialog() {
        String[] options = {"Find All Pairs", "Find Longest Pair"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "What would you like to do?",
                "Choose an Option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == -1) {
            dispose();
            return;
        }
        if (choice == 0) {
            dispose();
            findLongest = false;
        } else if (choice == 1) {
            dispose();
            findLongest = true;
        }
        initializeButtonAction();
    }

    private void initializeButtonAction() {
        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(ProjectUI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                List<Entry> entries = CSVReader.readEntriesFromCSV(fileChooser.getSelectedFile().getAbsolutePath());
                if (findLongest) {
                    Pair longestPair = DataProcessor.findLongestWorkingPair(entries);
                    displayData(List.of(longestPair), false);
                } else {
                    List<Pair> projectPairs = DataProcessor.findProjectPairs(entries);
                    displayData(projectPairs, true);
                }
            }
        });

        backButton.addActionListener(e -> {
            for (Window window : Window.getWindows()) {
                window.dispose();
            }

            SwingUtilities.invokeLater(() -> {
                ProjectUI frame = new ProjectUI();
                frame.setVisible(true);
            });
            showOptionsDialog();
        });
    }

    private void displayData(List<Pair> projectPairs, boolean findAllPairs) {
        if (findAllPairs) {
            tableModel = new DefaultTableModel(new Object[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days Worked"}, 0);
        } else {
            tableModel = new DefaultTableModel(new Object[]{"Employee ID #1", "Employee ID #2", "Total Days Worked"}, 0);
        }

        table.setModel(tableModel);

        for (Pair pair : projectPairs) {
            if (findAllPairs) {
                tableModel.addRow(new Object[]{pair.getEmp1(), pair.getEmp2(), pair.getProjectID(), pair.getOverlapDays()});
            } else {
                tableModel.addRow(new Object[]{pair.getEmp1(), pair.getEmp2(), pair.getOverlapDays()});
            }
        }
    }
}
