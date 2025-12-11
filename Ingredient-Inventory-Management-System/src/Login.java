import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {

    private Map<String, String> staffCredentials = new HashMap<>();
    private Map<String, String> adminCredentials = new HashMap<>();
    private boolean isAdmin = false;
    private JPanel slider;

    public Login() {

        staffCredentials.put("1", "1");
        adminCredentials.put("2", "2");

        setTitle("Ingredient Inventory Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ------------------ Logo ------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(248,249,255)); // match your BG

        // ▼ Create logo component ▼
        ImageIcon logoIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/img/logo.png"))
                .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)
        );

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // <-- VERY IMPORTANT
        logoLabel.setOpaque(false); // no background (or setColor if needed)

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(0));   // distance logo → login


        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(248, 249, 255));
 
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loginPanel);

        add(mainPanel, BorderLayout.CENTER);

        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 40, 50));
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        loginPanel.add(innerPanel, centerGbc);

        Font serifFont = new Font("Serif", Font.PLAIN, 16);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        // ---------------- TITLE ----------------
        JLabel titleLabel = new JLabel("Ingredient Inventory Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // insets ONLY for title
        gbc.insets = new Insets(4, 10, 0, 10);   // top=4 bottom=0 → closer spacing
        gbc.gridy = 0;
        innerPanel.add(titleLabel, gbc);


        // ---------------- SUBTITLE ----------------
        JLabel subtitleLabel = new JLabel("Login in to manage your ingredient inventory", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // insets ONLY for subtitle
        gbc.insets = new Insets(0, 10, 6, 10);   // no top space → moves closer to title
        gbc.gridy = 1;
        innerPanel.add(subtitleLabel, gbc);

        // ------------------ Sliding toggle ------------------
        int toggleWidth = 300, toggleHeight = 30, gap = 4;
        JPanel slidingToggle = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(217, 217, 217));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), toggleHeight, toggleHeight);
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, toggleHeight, toggleHeight);
            }
        };
        slidingToggle.setOpaque(false);
        slidingToggle.setPreferredSize(new Dimension(toggleWidth, toggleHeight));

        JLabel staffLabel = new JLabel("Staff", SwingConstants.CENTER);
        staffLabel.setBounds(0, 0, toggleWidth / 2, toggleHeight);
        staffLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        slidingToggle.add(staffLabel);

        JLabel adminLabel = new JLabel("Admin", SwingConstants.CENTER);
        adminLabel.setBounds(toggleWidth / 2, 0, toggleWidth / 2, toggleHeight);
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        slidingToggle.add(adminLabel);

        int sliderWidth = (toggleWidth / 2) - 2 * gap;
        slider = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            }
        };
        slider.setOpaque(false);
        slider.setBounds(gap, gap, sliderWidth, toggleHeight - 2 * gap);
        slidingToggle.add(slider);

        final boolean[] isOn = {true}; // default Staff
        slidingToggle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                boolean turnOn = x < toggleWidth / 2;
                int targetX = turnOn ? gap : toggleWidth / 2 + gap;

                Timer timer = new Timer(5, null);
                timer.addActionListener(ev -> {
                    int currentX = slider.getX();
                    if (currentX < targetX)
                        slider.setLocation(Math.min(currentX + 5, targetX), slider.getY());
                    else if (currentX > targetX)
                        slider.setLocation(Math.max(currentX - 5, targetX), slider.getY());

                    if (slider.getX() == targetX) {
                        timer.stop();
                        isOn[0] = turnOn;
                        isAdmin = !turnOn;
                    }
                });
                timer.start();
            }
        });
        gbc.gridy = 2;
        innerPanel.add(slidingToggle, gbc);

        // ------------------ Username label and field ------------------
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 2, 10);
        innerPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(serifFont);
        String userPlaceholder = " Enter Username";
        usernameField.setForeground(Color.GRAY);
        usernameField.setText(userPlaceholder);
        usernameField.getCaret().setVisible(false);
        usernameField.setPreferredSize(new Dimension(250, 35));
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(userPlaceholder)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
                usernameField.getCaret().setVisible(true);
            }
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(userPlaceholder);
                    usernameField.setForeground(Color.GRAY);
                    usernameField.getCaret().setVisible(false);
                }
            }
        });
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 10, 2, 10);
        innerPanel.add(usernameField, gbc);

        // ------------------ Password label and field ------------------
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 0, 0);
        innerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(serifFont);
        String passPlaceholder = " Enter Password";
        passwordField.setForeground(Color.GRAY);
        passwordField.setText(passPlaceholder);
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setEchoChar((char) 0);
        passwordField.getCaret().setVisible(false);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String text = new String(passwordField.getPassword());
                if (text.equals(passPlaceholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022');
                }
                passwordField.getCaret().setVisible(true);
            }
            public void focusLost(FocusEvent e) {
                String text = new String(passwordField.getPassword());
                if (text.isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(passPlaceholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.getCaret().setVisible(false);
                }
            }
        });
        gbc.gridy = 6;
        gbc.insets = new Insets(2, 10, 10, 10);
        innerPanel.add(passwordField, gbc);

        // ------------------ Login button ------------------
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        Color normalColor = loginButton.getBackground();
        Color hoverColor = new Color(34, 34, 34);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(normalColor);
            }
        });

        gbc.gridy = 7;
        gbc.insets = new Insets (20, 10, 0, 10);
        innerPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String usernameInput = usernameField.getText().trim();
            if (usernameInput.equals(userPlaceholder)) usernameInput = "";

            String passwordInput = new String(passwordField.getPassword()).trim();
            if (passwordInput.equals(passPlaceholder)) passwordInput = "";

            Map<String, String> credentials = isAdmin ? adminCredentials : staffCredentials;

            if (credentials.containsKey(usernameInput) && credentials.get(usernameInput).equals(passwordInput)) {
                dispose(); // Close login window

                if (isAdmin) {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            AdminMainFrame adminFrame = new AdminMainFrame();
                            adminFrame.setVisible(true); // Ensure visibility
                        } catch (Exception ex) {
                            ex.printStackTrace(); // Print any initialization errors
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        StaffMainFrame staffFrame = new StaffMainFrame();
                        staffFrame.setVisible(true);
                    });
                }
            } else {
                JOptionPane.showMessageDialog(Login.this, "Invalid username or password");
            }
        });

        setVisible(true);
    } 
}