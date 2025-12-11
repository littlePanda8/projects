// StaffTopNavBar.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminTopNavBar extends JPanel {

    private AdminMainFrame adminMainFrame;
    private JLabel overviewLabel, inventoryLabel, reportsLabel, settingsLabel;
    private JLabel accountName;
    private JButton logoutButton;
    private JPanel topNav;
    private JLabel selectedLabel;

    public AdminTopNavBar(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
        setLayout(new BorderLayout());

        // ================= HEADER (Top Section) =================
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        // Top panel (white full width) with shadow
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        // Logo + Label Panel
        JPanel logoLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        logoLabelPanel.setOpaque(false);

        java.net.URL logoURL = getClass().getResource("/img/logoHeader.png");
        if (logoURL != null) {
            ImageIcon originalLogo = new ImageIcon(logoURL);
            Image scaledLogo = originalLogo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabelPanel.add(logoLabel);
        }

        JLabel navLabel = new JLabel("Ingredient Inventory Management System");
        navLabel.setForeground(Color.BLACK);
        navLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logoLabelPanel.add(navLabel);

        topPanel.add(logoLabelPanel, BorderLayout.WEST);

        // Right panel with account + logout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        accountName = new JLabel("Admin");
        accountName.setForeground(Color.BLACK);
        accountName.setFont(new Font("Arial", Font.PLAIN, 16));
        accountName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        java.net.URL imgURL = getClass().getResource("/img/logout.png");
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            logoutButton.setIcon(new ImageIcon(scaledImage));
        }

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    adminMainFrame,
                    "<html><center><font size='5'>Are you sure you want to log out?</font></center></html>",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                if (adminMainFrame != null) adminMainFrame.dispose();
                SwingUtilities.invokeLater(() -> new Login().setVisible(true));
            }
        });

        rightPanel.add(Box.createHorizontalGlue());
        rightPanel.add(accountName);
        rightPanel.add(logoutButton);

        topPanel.add(rightPanel, BorderLayout.EAST);
        topContainer.add(topPanel);

        // ================= NAVIGATION BAR =================
        topNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        topNav.setOpaque(true);
        topNav.setBackground(new Color(248, 249, 255));

        // Staff navigation labels (Reports removed)
        overviewLabel = createNavLabel("Overview", "/img/overview.png", false);
        inventoryLabel = createNavLabel("Inventory", "/img/inventory.png", false);
        reportsLabel = createNavLabel("Reports", "/img/reports.png", false);
        settingsLabel = createNavLabel("Settings", "/img/settings.png", false);

        // Click Actions
        overviewLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (adminMainFrame != null) adminMainFrame.showPanel(adminMainFrame.adminOverview);
                setSelectedNavLabel(overviewLabel);
            }
        });
        inventoryLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (adminMainFrame != null) adminMainFrame.showPanel(adminMainFrame.adminInventory);
                setSelectedNavLabel(inventoryLabel);
            }
        });
        reportsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (adminMainFrame != null) adminMainFrame.showPanel(adminMainFrame.adminReports);
                setSelectedNavLabel(reportsLabel);
            }
        });
        settingsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (adminMainFrame != null) adminMainFrame.showPanel(adminMainFrame.adminSettings);
                setSelectedNavLabel(settingsLabel);
            }
        });

        topNav.add(overviewLabel);
        topNav.add(inventoryLabel);
        topNav.add(reportsLabel);
        topNav.add(settingsLabel);

        // ================= NAV WRAPPER (full width + shadow) =================
        JPanel navWrapper = new JPanel(new BorderLayout());
        navWrapper.setOpaque(true);
        navWrapper.setBackground(new Color(248, 249, 255));
        topNav.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        navWrapper.add(topNav, BorderLayout.CENTER);
        topContainer.add(navWrapper);

        add(topContainer, BorderLayout.NORTH);

        // Default selected
        setSelectedNavLabel(overviewLabel);
    }

    // ================= CREATE NAV LABEL WITH ICON =================
    private JLabel createNavLabel(String text, String iconPath, boolean selected) {
        ImageIcon icon = null;
        if (iconPath != null) {
            java.net.URL imgURL = getClass().getResource(iconPath);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
            }
        }

        JLabel label = new JLabel(text, icon, JLabel.LEFT) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getBackground() != null && isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                }
                super.paintComponent(g);
            }
        };

        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setOpaque(selected);
        label.setBackground(selected ? new Color(233, 232, 255) : null);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return label;
    }

    // ================= ACTIVE HIGHLIGHT =================
    private void setSelectedNavLabel(JLabel label) {
        if (selectedLabel != null) {
            selectedLabel.setOpaque(false);
            selectedLabel.setBackground(null);
        }
        label.setOpaque(true);
        label.setBackground(new Color(233, 232, 255));
        selectedLabel = label;
    }

                public static void main(String[] args) {
                JFrame frame = new JFrame("AdminInventory Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new AdminTopNavBar(new AdminMainFrame()));
                frame.setSize(1200, 700);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
}