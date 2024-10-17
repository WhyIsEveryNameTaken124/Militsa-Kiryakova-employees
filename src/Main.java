import dataProcessors.ProjectUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectUI frame = new ProjectUI();
            frame.setVisible(true);
        });
    }
}