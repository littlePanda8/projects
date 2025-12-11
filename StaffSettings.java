import javax.swing.*;
import java.awt.*;

public class StaffSettings extends JPanel {

    private StaffMainFrame staffMainFrame;

    public StaffSettings(StaffMainFrame staffMainFrame) {
        this.staffMainFrame = staffMainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 255));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(248, 249, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        settingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabel = new JLabel("Manage account preferences and system options.");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(settingsLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subLabel);

        add(topPanel, BorderLayout.NORTH);

        //CONTENT PANEL - PANEL THAT HOLDS ALL COMPONENTS INSIDE THE CONTENT WITH WHITE BACKGROUND
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE); // Changed to White
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ACCOUNT INFORMATION LABEL
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        accountInfoPanel.setOpaque(false);
        accountInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        JLabel accountLabel = new JLabel("Account Information");
        accountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        accountLabel.setForeground(Color.BLACK);

        accountInfoPanel.add(accountLabel);

        contentPanel.add(accountInfoPanel);

        //STAFF USERNAME
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setOpaque(false);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        usernamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setOpaque(false);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setText("staff (read-only)");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        usernameField.setEditable(false);
        usernameField.setBackground(new Color(238, 238, 238));
        usernameField.setForeground(Color.BLACK);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension textFieldSize = new Dimension(200, 30);
        usernameField.setPreferredSize(textFieldSize);
        usernameField.setMinimumSize(textFieldSize);
        usernameField.setMaximumSize(textFieldSize);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createHorizontalStrut(5));
        usernamePanel.add(usernameField);
        usernamePanel.add(Box.createHorizontalGlue());

        contentPanel.add(usernamePanel);

        // STAFF PASSWORD
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setOpaque(false);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setOpaque(false);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setText("password");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 10,0, 0));
        passwordField.setEditable(false);
        passwordField.setBackground(new Color(238, 238, 238));
        passwordField.setForeground(Color.BLACK);
        passwordField.setEchoChar('\u2022');
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension passwordFieldSize = new Dimension(200, 30);
        passwordField.setPreferredSize(passwordFieldSize);
        passwordField.setMinimumSize(passwordFieldSize);
        passwordField.setMaximumSize(passwordFieldSize);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(5));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalGlue());

        contentPanel.add(passwordPanel);

        // CHANGE PASSWORD LABEL
        JPanel changePassLabelPanel = new JPanel();
        changePassLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        changePassLabelPanel.setOpaque(false);
        changePassLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        changePassLabelPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        JLabel changePassLabel = new JLabel("Change Password");
        changePassLabel.setFont(new Font("Arial", Font.BOLD, 16));
        changePassLabel.setForeground(Color.BLACK);

        changePassLabelPanel.add(changePassLabel);

        contentPanel.add(changePassLabelPanel);

        // CURRENT PASSWORD
        JPanel currentPassPanel = new JPanel();
        currentPassPanel.setLayout(new BoxLayout(currentPassPanel, BoxLayout.Y_AXIS));
        currentPassPanel.setOpaque(false);
        currentPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        currentPassPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel currentPassLabel = new JLabel("Current Password: ");
        currentPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPassLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        currentPassLabel.setForeground(Color.BLACK);
        currentPassLabel.setOpaque(false);
        currentPassLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField currentPassField = new JPasswordField();
        currentPassField.setText("Enter Current Password");
        currentPassField.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPassField.setEditable(true);
        currentPassField.setBackground(new Color(238, 238, 238));
        currentPassField.setForeground(Color.GRAY);
        currentPassField.setEchoChar((char) 0);
        currentPassField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension currentPassFieldSize = new Dimension(450, 30);
        
        currentPassField.setPreferredSize(currentPassFieldSize);
        currentPassField.setMinimumSize(currentPassFieldSize);
        currentPassField.setMaximumSize(currentPassFieldSize);

        currentPassPanel.add(currentPassLabel);
        currentPassPanel.add(currentPassField);

        contentPanel.add(currentPassPanel);

        // NEW PASSWORD
        JPanel newPassPanel = new JPanel();
        newPassPanel.setLayout(new BoxLayout(newPassPanel, BoxLayout.Y_AXIS));
        newPassPanel.setOpaque(false);
        newPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        newPassPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel newPassLabel = new JLabel("New Password: ");
        newPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newPassLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        newPassLabel.setForeground(Color.BLACK);
        newPassLabel.setOpaque(false);
        newPassLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField newPassField = new JPasswordField();
        newPassField.setText("Enter New Password");
        newPassField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPassField.setEditable(true);
        newPassField.setBackground(new Color(238, 238, 238));
        newPassField.setForeground(Color.GRAY);
        newPassField.setEchoChar((char) 0);
        newPassField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension newPassFieldSize = new Dimension(450, 30);
        newPassField.setPreferredSize(newPassFieldSize);
        newPassField.setMinimumSize(newPassFieldSize);
        newPassField.setMaximumSize(newPassFieldSize);

        newPassPanel.add(newPassLabel);
        newPassPanel.add(newPassField);

        contentPanel.add(newPassPanel);

        // CONFIRM NEW PASSWORD
        JPanel confirmNewPassPanel = new JPanel();
        confirmNewPassPanel.setLayout(new BoxLayout(confirmNewPassPanel, BoxLayout.Y_AXIS));
        confirmNewPassPanel.setOpaque(false);
        confirmNewPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        confirmNewPassPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel confirmNewPassLabel = new JLabel("Confirm New Password: ");
        confirmNewPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmNewPassLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        confirmNewPassLabel.setForeground(Color.BLACK);
        confirmNewPassLabel.setOpaque(false);
        confirmNewPassLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField confirmNewPassField = new JPasswordField();
        confirmNewPassField.setText("Confirm New Password");
        confirmNewPassField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmNewPassField.setEditable(true);
        confirmNewPassField.setBackground(new Color(238, 238, 238));
        confirmNewPassField.setForeground(Color.GRAY);
        confirmNewPassField.setEchoChar((char) 0);
        confirmNewPassField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension confirmNewPassFieldSize = new Dimension(450, 30);
        confirmNewPassField.setPreferredSize(confirmNewPassFieldSize);
        confirmNewPassField.setMinimumSize(confirmNewPassFieldSize);
        confirmNewPassField.setMaximumSize(confirmNewPassFieldSize);

        confirmNewPassPanel.add(confirmNewPassLabel);
        confirmNewPassPanel.add(confirmNewPassField);

        contentPanel.add(confirmNewPassPanel);

        // CHANGE BUTTON
        JButton changePassword = new JButton("Change Password");
        changePassword.setBackground(Color.BLACK);
        changePassword.setForeground(Color.WHITE);
        changePassword.setFont(new Font("Arial", Font.PLAIN, 14));
        changePassword.setFocusPainted(false); // remove the focus border

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));  // ADD THIS LINE

        buttonPanel.add(changePassword);
        contentPanel.add(buttonPanel);

        // OUTER PANEL - TO CREATE A MARGIN AROUND CONTENTPANEL
        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setOpaque(false);
        outerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        outerWrapper.setBackground(new Color(248, 249, 255));

        outerWrapper.add(contentPanel, BorderLayout.NORTH);
        add(outerWrapper, BorderLayout.CENTER);
    }

    // --- TESTING MAIN ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("StaffSettings Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new StaffSettings(new StaffMainFrame()));
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}