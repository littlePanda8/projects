import javax.swing.*;
import java.awt.*;

public class AdminSettings extends JPanel {

    private AdminMainFrame adminMainFrame;

    public AdminSettings(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;

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

// ================= LEFT PANEL =================
JPanel leftPanel = new JPanel();
leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
leftPanel.setBackground(Color.WHITE);
leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

// --- ADMIN ACCOUNT INFORMATION ---
JPanel adminInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
adminInfoPanel.setOpaque(false);
adminInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 0));
JLabel accountLabel = new JLabel("Account Information");
accountLabel.setFont(new Font("Arial", Font.BOLD, 16));
accountLabel.setForeground(Color.BLACK);
adminInfoPanel.add(accountLabel);
leftPanel.add(adminInfoPanel);

// --- ADMIN USERNAME ---
JPanel usernamePanel = new JPanel();
usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
usernamePanel.setOpaque(false);
usernamePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
JLabel usernameLabel = new JLabel("Username: ");
usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
usernameLabel.setForeground(Color.BLACK);
JTextField usernameField = new JTextField("admin (read-only)");
usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
usernameField.setEditable(false);
usernameField.setBackground(new Color(238, 238, 238));
usernameField.setPreferredSize(new Dimension(200, 30));
usernamePanel.add(usernameLabel);
usernamePanel.add(Box.createHorizontalStrut(5));
usernamePanel.add(usernameField);
usernamePanel.add(Box.createHorizontalGlue());
leftPanel.add(usernamePanel);

// --- ADMIN PASSWORD ---
JPanel passwordPanel = new JPanel();
passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
passwordPanel.setOpaque(false);
passwordPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
JLabel passwordLabel = new JLabel("Password: ");
passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
passwordLabel.setForeground(Color.BLACK);
JPasswordField passwordField = new JPasswordField("password");
passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
passwordField.setEditable(false);
passwordField.setBackground(new Color(238, 238, 238));
passwordField.setPreferredSize(new Dimension(200, 30));
passwordPanel.add(passwordLabel);
passwordPanel.add(Box.createHorizontalStrut(5));
passwordPanel.add(passwordField);
passwordPanel.add(Box.createHorizontalGlue());
leftPanel.add(passwordPanel);

// --- CHANGE PASSWORD SECTION (ALL FIELDS WRAPPED) ---
JPanel changePasswordSection = new JPanel();
changePasswordSection.setLayout(new BoxLayout(changePasswordSection, BoxLayout.Y_AXIS));
changePasswordSection.setOpaque(false);
changePasswordSection.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

// --- Change Password Label ---
JLabel changePassLabel = new JLabel("Change Password");
changePassLabel.setFont(new Font("Arial", Font.BOLD, 16));
changePassLabel.setForeground(Color.BLACK);
JPanel changePassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
changePassPanel.setOpaque(false);
changePassPanel.add(changePassLabel);
changePasswordSection.add(changePassPanel);

// --- Current Password ---
JPanel currentPassPanel = new JPanel();
currentPassPanel.setLayout(new BoxLayout(currentPassPanel, BoxLayout.Y_AXIS));
currentPassPanel.setOpaque(false);
currentPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
JLabel currentPassLabel = new JLabel("Current Password: ");
currentPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
JPasswordField currentPassField = new JPasswordField("Enter Current Password");
currentPassField.setFont(new Font("Arial", Font.PLAIN, 14));
currentPassField.setForeground(Color.GRAY);
currentPassField.setBackground(new Color(238, 238, 238));
currentPassField.setPreferredSize(new Dimension(200, 30));
currentPassPanel.add(currentPassLabel);
currentPassPanel.add(currentPassField);
changePasswordSection.add(currentPassPanel);

// --- New Password ---
JPanel newPassPanel = new JPanel();
newPassPanel.setLayout(new BoxLayout(newPassPanel, BoxLayout.Y_AXIS));
newPassPanel.setOpaque(false);
newPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
JLabel newPassLabel = new JLabel("New Password: ");
newPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
JPasswordField newPassField = new JPasswordField("Enter New Password");
newPassField.setFont(new Font("Arial", Font.PLAIN, 14));
newPassField.setForeground(Color.GRAY);
newPassField.setBackground(new Color(238, 238, 238));
newPassField.setPreferredSize(new Dimension(200, 30));
newPassPanel.add(newPassLabel);
newPassPanel.add(newPassField);
changePasswordSection.add(newPassPanel);

// --- Confirm New Password ---
JPanel confirmNewPassPanel = new JPanel();
confirmNewPassPanel.setLayout(new BoxLayout(confirmNewPassPanel, BoxLayout.Y_AXIS));
confirmNewPassPanel.setOpaque(false);
confirmNewPassPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
JLabel confirmNewPassLabel = new JLabel("Confirm New Password: ");
confirmNewPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
JPasswordField confirmNewPassField = new JPasswordField("Confirm New Password");
confirmNewPassField.setFont(new Font("Arial", Font.PLAIN, 14));
confirmNewPassField.setForeground(Color.GRAY);
confirmNewPassField.setBackground(new Color(238, 238, 238));
confirmNewPassField.setPreferredSize(new Dimension(200, 30));
confirmNewPassPanel.add(confirmNewPassLabel);
confirmNewPassPanel.add(confirmNewPassField);
changePasswordSection.add(confirmNewPassPanel);

// --- Change Button ---
JButton changePasswordBtn = new JButton("Change Password");
changePasswordBtn.setBackground(Color.BLACK);
changePasswordBtn.setForeground(Color.WHITE);
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
buttonPanel.setOpaque(false);
buttonPanel.add(changePasswordBtn);
changePasswordSection.add(buttonPanel);

// --- Add change-password section to leftPanel ---
leftPanel.add(changePasswordSection);

// --- Wrap leftPanel with margin ---
JPanel leftWrapper = new JPanel(new BorderLayout());
leftWrapper.setOpaque(false);
leftWrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 15));
leftWrapper.add(leftPanel, BorderLayout.NORTH);


        // --------- RIGHT PANEL ----------------------
JPanel rightPanel = new JPanel();
rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
rightPanel.setBackground(Color.WHITE);
rightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

// ===== STAFF ACCOUNT INFORMATION LABEL =====
JPanel staffInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
staffInfoPanel.setOpaque(false);

JLabel staffAccountLabel = new JLabel("Staff Account Information");
staffAccountLabel.setFont(new Font("Arial", Font.BOLD, 16));
staffAccountLabel.setForeground(Color.BLACK);

staffInfoPanel.add(staffAccountLabel);
rightPanel.add(staffInfoPanel);
rightPanel.add(Box.createVerticalStrut(10)); // spacing after label

// ===== STAFF USERNAME =====
JPanel staffUserPanel = new JPanel();
staffUserPanel.setLayout(new BoxLayout(staffUserPanel, BoxLayout.X_AXIS));
staffUserPanel.setOpaque(false);

JLabel staffUserLabel = new JLabel("Username: ");
staffUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
staffUserLabel.setForeground(Color.BLACK);

JTextField staffUserField = new JTextField("staff (read-only)");
staffUserField.setFont(new Font("Arial", Font.PLAIN, 14));
staffUserField.setEditable(false);
staffUserField.setBackground(new Color(238, 238, 238));
staffUserField.setPreferredSize(new Dimension(200, 30));
staffUserField.setMaximumSize(new Dimension(200, 30));

staffUserPanel.add(staffUserLabel);
staffUserPanel.add(Box.createHorizontalStrut(5));
staffUserPanel.add(staffUserField);
staffUserPanel.add(Box.createHorizontalGlue());
rightPanel.add(staffUserPanel);
rightPanel.add(Box.createVerticalStrut(10));

// ===== STAFF PASSWORD =====
JPanel staffPassPanel = new JPanel();
staffPassPanel.setLayout(new BoxLayout(staffPassPanel, BoxLayout.X_AXIS));
staffPassPanel.setOpaque(false);

JLabel staffPassLabel = new JLabel("Password: ");
staffPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
staffPassLabel.setForeground(Color.BLACK);

JPasswordField staffPassField = new JPasswordField("password");
staffPassField.setFont(new Font("Arial", Font.PLAIN, 14));
staffPassField.setEditable(false);
staffPassField.setBackground(new Color(238, 238, 238));
staffPassField.setEchoChar('\u2022'); // bullet
staffPassField.setPreferredSize(new Dimension(200, 30));
staffPassField.setMaximumSize(new Dimension(200, 30));

staffPassPanel.add(staffPassLabel);
staffPassPanel.add(Box.createHorizontalStrut(5));
staffPassPanel.add(staffPassField);
staffPassPanel.add(Box.createHorizontalGlue());
rightPanel.add(staffPassPanel);
rightPanel.add(Box.createVerticalStrut(15));

// ===== RESET PASSWORD BUTTON =====
JButton resetPasswordBtn = new JButton("Reset Password");
resetPasswordBtn.setBackground(Color.BLACK);
resetPasswordBtn.setForeground(Color.WHITE);
resetPasswordBtn.setFocusPainted(false);

JPanel resetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
resetPanel.setOpaque(false);
resetPanel.add(resetPasswordBtn);
rightPanel.add(resetPanel);
rightPanel.add(Box.createVerticalStrut(15));

// ===== TEMPORARY PASSWORD DISPLAY =====
JPanel tempPassPanel = new JPanel();
tempPassPanel.setLayout(new BoxLayout(tempPassPanel, BoxLayout.Y_AXIS));
tempPassPanel.setOpaque(false);

JLabel tempPassLabel = new JLabel("Temporary Generated Password:");
tempPassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
tempPassLabel.setForeground(Color.BLACK);

JTextField tempPassField = new JTextField("staff123");
tempPassField.setFont(new Font("Arial", Font.PLAIN, 14));
tempPassField.setEditable(false);
tempPassField.setBackground(new Color(238, 238, 238));
tempPassField.setPreferredSize(new Dimension(200, 30));
tempPassField.setMaximumSize(new Dimension(200, 30));

tempPassPanel.add(tempPassLabel);
tempPassPanel.add(Box.createVerticalStrut(5));
tempPassPanel.add(tempPassField);
rightPanel.add(tempPassPanel);

// ===== WRAPPER FOR MARGIN =====
JPanel rightWrapper = new JPanel(new BorderLayout());
rightWrapper.setOpaque(false);
rightWrapper.setBorder(BorderFactory.createEmptyBorder(20, 15, 0, 30));
rightWrapper.add(rightPanel, BorderLayout.NORTH);

// --- LEFT SIDE WRAPPER (left panel + backup panel stacked vertically) ---
JPanel leftSideHolder = new JPanel();
leftSideHolder.setLayout(new BorderLayout()); // use BorderLayout for top + bottom
leftSideHolder.setOpaque(false);

// Top part: leftWrapper (account + change password)
leftSideHolder.add(leftWrapper, BorderLayout.NORTH);

// Bottom part: backup panel
JPanel backupPanel = new JPanel();
backupPanel.setLayout(new BoxLayout(backupPanel, BoxLayout.Y_AXIS));
backupPanel.setBackground(Color.WHITE);
backupPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // rectangle border
backupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
backupPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

JLabel backupLabel = new JLabel("Backup and Recovery");
backupLabel.setFont(new Font("Arial", Font.BOLD, 16));
backupLabel.setForeground(Color.BLACK);
backupLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

JLabel subBackupLabel = new JLabel("Last Backup: 11/22/2025 15:30");
subBackupLabel.setFont(new Font("Arial", Font.PLAIN, 14));
subBackupLabel.setForeground(Color.BLACK);

JPanel backupButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
backupButtonPanel.setOpaque(false);

JPanel backupLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
backupLabelPanel.setOpaque(false);
backupLabelPanel.add(backupLabel);
backupLabelPanel.add(subBackupLabel);
backupPanel.add(backupLabelPanel);


JButton backupButton = new JButton("Backup Data");
backupButton.setBackground(Color.BLACK);
backupButton.setForeground(Color.WHITE);
backupButton.setFocusPainted(false);

JButton restoreButton = new JButton("Restore Backup");
restoreButton.setBackground(Color.BLACK);
restoreButton.setForeground(Color.WHITE);
restoreButton.setFocusPainted(false);

backupButtonPanel.add(backupButton);
backupButtonPanel.add(restoreButton);

// Add labels and buttons to backupPanel
backupPanel.add(backupLabel);
backupPanel.add(Box.createVerticalStrut(5));
backupPanel.add(subBackupLabel);
backupPanel.add(Box.createVerticalStrut(10));
backupPanel.add(backupButtonPanel);

// Add backupPanel at the bottom of leftSideHolder
leftSideHolder.add(backupPanel, BorderLayout.SOUTH);

// --- DUAL HOLDER (left side + right panel) ---
JPanel dualHolder = new JPanel();
dualHolder.setLayout(new BoxLayout(dualHolder, BoxLayout.X_AXIS));
dualHolder.setOpaque(false);
dualHolder.add(leftSideHolder); // left side with backup at bottom
dualHolder.add(Box.createHorizontalStrut(20));
dualHolder.add(rightWrapper);   // right panel


// --- CONTENT WRAPPER ---
JPanel contentWrapper = new JPanel();
contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
contentWrapper.setOpaque(false);
contentWrapper.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
contentWrapper.add(dualHolder);

add(contentWrapper, BorderLayout.CENTER);

    }


    // --- TESTING MAIN ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("Settings Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AdminSettings(null));
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}