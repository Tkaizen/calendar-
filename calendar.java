import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarApp {
    private JFrame frame;
    private JPanel panel;
    private JLabel monthLabel;
    private JButton prevButton, nextButton;
    private LocalDate currentDate;

    public CalendarApp() {
        currentDate = LocalDate.now();  // Current date

        frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Month Label and Navigation Buttons
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        prevButton = new JButton("<");
        nextButton = new JButton(">");

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 20));

        headerPanel.add(prevButton);
        headerPanel.add(monthLabel);
        headerPanel.add(nextButton);

        // Calendar Panel (Days of the month)
        JPanel daysPanel = new JPanel();
        daysPanel.setLayout(new GridLayout(0, 7));

        // Days of the week labels (Sun, Mon, Tue, etc.)
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        // Populate the calendar with days
        updateCalendar(daysPanel);

        // Add components to the main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(daysPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);

        // Action listeners for navigation buttons
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar(daysPanel);
        });

        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar(daysPanel);
        });
    }

    private void updateCalendar(JPanel daysPanel) {
        // Clear previous day buttons
        daysPanel.removeAll();

        // Update the month label
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthLabel.setText(currentDate.format(formatter));

        // Get first day of the month
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        // Get last day of the month
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Get the day of the week for the first day (0 = Sunday, 6 = Saturday)
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;

        // Add empty cells before the first day
        for (int i = 0; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Add day buttons for the days of the current month
        int daysInMonth = currentDate.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate dayDate = currentDate.withDayOfMonth(day);
            JButton dayButton = new JButton(String.valueOf(day));

            // Optional: Highlight today's date
            if (dayDate.equals(LocalDate.now())) {
                dayButton.setBackground(Color.YELLOW);
            }

            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Selected Date: " + dayDate);
                }
            });

            daysPanel.add(dayButton);
        }

        // Revalidate and repaint to update the panel
        daysPanel.revalidate();
        daysPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarApp();
            }
        });
    }
}
