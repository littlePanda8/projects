import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AdminOverview extends JPanel {

    private AdminMainFrame adminMainFrame;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public AdminOverview(AdminMainFrame adminMainFrame, List<Ingredient> inventory) {
        this.adminMainFrame = adminMainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 255));

        // ================== Top Panel ==================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(248, 249, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel overviewLabel = new JLabel("Overview");
        overviewLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel subtitleLabel = new JLabel("View key insights and alerts at a glance.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(overviewLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitleLabel);

        add(topPanel, BorderLayout.NORTH);

        add(createCenterPanel(inventory), BorderLayout.CENTER);
    }

    private JPanel createCenterPanel(List<Ingredient> inventory) {
        // ================== Compute Inventory Counts ==================
        int totalIngredients = inventory.size();
        int lowStock = 0;
        int expiringSoon = 0;
        int expired = 0;
        int outOfStock = 0;

        LocalDate today = LocalDate.now();

        for (Ingredient ing : inventory) {
            String expiry = ing.getExpiryDate();
            LocalDate expDate = null;
            if (expiry != null && !expiry.isEmpty()) {
                expDate = LocalDate.parse(expiry, DATE_FORMAT);
            }

            if (ing.getQuantity() == 0) outOfStock++;
            if (ing.getQuantity() > 0 && ing.getQuantity() <= ing.getMinLevel()) lowStock++;
            if (expDate != null) {
                if (expDate.isBefore(today)) expired++;
                else if (!expDate.isBefore(today) && expDate.isBefore(today.plusDays(7))) expiringSoon++;
            }
        }

        // ================== Box Panel ==================
        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        boxPanel.setBackground(new Color(248, 249, 255));
        boxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        boxPanel.add(createBoxPanel("Total Ingredients", String.valueOf(totalIngredients), "Active inventory items"));
        boxPanel.add(createBoxPanel("Low Stock Items", String.valueOf(lowStock), "Below minimum stock level"));
        boxPanel.add(createBoxPanel("Expiring Soon", String.valueOf(expiringSoon), "Within 7 days"));
        boxPanel.add(createBoxPanel("Expired Items", String.valueOf(expired), "Require immediate attention"));
        boxPanel.add(createBoxPanel("Out of Stock", String.valueOf(outOfStock), "Require immediate attention"));

        // ================== Alerts Panel ==================
        JPanel alertNotifsPanel = new JPanel();
        alertNotifsPanel.setLayout(new BoxLayout(alertNotifsPanel, BoxLayout.Y_AXIS));
        alertNotifsPanel.setBackground(new Color(248, 249, 255));
        alertNotifsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        alertNotifsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 30));

        JLabel alertNotifsLabel = new JLabel("Alerts & Notifications");
        alertNotifsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        alertNotifsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        alertNotifsPanel.add(alertNotifsLabel);
        alertNotifsPanel.add(Box.createVerticalStrut(10));

        // ---- Collect alerts safely ----
        List<String> outOfStockItems = new ArrayList<>();
        List<String> expiredItems = new ArrayList<>();
        List<String> expiringItems = new ArrayList<>();
        List<String> lowStockItems = new ArrayList<>();

        for (Ingredient ing : inventory) {
            LocalDate expDate = null;
            String expiry = ing.getExpiryDate();
            if (expiry != null && !expiry.isEmpty()) {
                expDate = LocalDate.parse(expiry, DATE_FORMAT);
            }

            if (ing.getQuantity() == 0) outOfStockItems.add(ing.getName());
            if (ing.getQuantity() > 0 && ing.getQuantity() <= ing.getMinLevel()) lowStockItems.add(ing.getName());
            if (expDate != null) {
                if (expDate.isBefore(LocalDate.now())) expiredItems.add(ing.getName());
                else if (!expDate.isBefore(LocalDate.now()) && expDate.isBefore(LocalDate.now().plusDays(7)))
                    expiringItems.add(ing.getName());
            }
        }

        Consumer<JPanel> addAlertAtTop = panel -> {
            alertNotifsPanel.add(panel, 2);
            alertNotifsPanel.add(Box.createVerticalStrut(10), 3);
        };

        boolean hasAlerts = false;

        if (!outOfStockItems.isEmpty()) {
            addAlertAtTop.accept(createAlertPanel("Out of Stock Alert",
                    "These items are out of stock and require immediate replenishment.",
                    outOfStockItems, Color.decode("#DC0000"), Color.decode("#F9DFDF"),
                    Color.decode("#BF1A1A"), Color.decode("#D2665A")));
            hasAlerts = true;
        }

        if (!expiredItems.isEmpty()) {
            addAlertAtTop.accept(createAlertPanel("Expired Alert",
                    "These items are expired and require immediate action.",
                    expiredItems, Color.decode("#DC0000"), Color.decode("#F9DFDF"),
                    Color.decode("#BF1A1A"), Color.decode("#D2665A")));
            hasAlerts = true;
        }

        if (!expiringItems.isEmpty()) {
            addAlertAtTop.accept(createAlertPanel("Expiring Soon",
                    "These items will expire soon, consider reordering.",
                    expiringItems, Color.decode("#F29F58"), Color.decode("#F2E8C6"),
                    Color.decode("#DC6B19"), Color.decode("#FF7D29")));
            hasAlerts = true;
        }

        if (!lowStockItems.isEmpty()) {
            addAlertAtTop.accept(createAlertPanel("Low Stock Warning",
                    "These items are running low and should be reordered soon.",
                    lowStockItems, Color.decode("#CCCC00"), Color.decode("#FFF5E0"),
                    Color.decode("#FFB823"), Color.decode("#FB9E3A")));
            hasAlerts = true;
        }

        if (!hasAlerts) {
            JLabel noAlertsLabel = new JLabel("No alerts. All ingredients are in good stock!");
            noAlertsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noAlertsLabel.setForeground(Color.GRAY);
            noAlertsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            alertNotifsPanel.add(noAlertsLabel);
            alertNotifsPanel.add(Box.createVerticalStrut(10));
        }

        // ================== Center Panel ==================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(248, 249, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        centerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(boxPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(alertNotifsPanel);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(centerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    // ================== Box Panel Creation ==================
    private JPanel createBoxPanel(String labelText, String countText, String subLabelText) {
        JPanel box = new JPanel();
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(200, 100));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel count = new JLabel(countText);
        count.setFont(new Font("Arial", Font.PLAIN, 20));
        count.setForeground(Color.RED);
        count.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabel = new JLabel(subLabelText);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(label);
        box.add(Box.createVerticalStrut(15));
        box.add(count);
        box.add(Box.createVerticalStrut(5));
        box.add(subLabel);

        return box;
    }

    // ================== Alert Panel Creation ==================
    private JPanel createAlertPanel(String alertText, String subLabelText, List<String> items,
                                    Color borderColor, Color bgColor, Color labelColor, Color textColor) {
        JPanel alertPanel = new JPanel();
        alertPanel.setBackground(bgColor);
        alertPanel.setOpaque(true);
        alertPanel.setLayout(new BoxLayout(alertPanel, BoxLayout.Y_AXIS));
        alertPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        alertPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel alertLabel = new JLabel(alertText);
        alertLabel.setFont(new Font("Arial", Font.BOLD, 16));
        alertLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        alertLabel.setForeground(labelColor);

        JPanel subLabelPanel = new JPanel();
        subLabelPanel.setLayout(new BoxLayout(subLabelPanel, BoxLayout.Y_AXIS));
        subLabelPanel.setBackground(bgColor);
        subLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel alertSubLabel = new JLabel(subLabelText);
        alertSubLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        alertSubLabel.setForeground(textColor);
        alertSubLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subLabelPanel.add(alertSubLabel);

        for (String item : items) {
            JLabel listItem = new JLabel("<html>&#8226; " + item + "</html>");
            listItem.setFont(new Font("Arial", Font.PLAIN, 12));
            listItem.setForeground(textColor);
            listItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            subLabelPanel.add(listItem);
        }

        alertPanel.add(alertLabel);
        alertPanel.add(Box.createVerticalStrut(3));
        alertPanel.add(subLabelPanel);

        return alertPanel;
    }

    public void refreshOverview(List<Ingredient> inventory) {
        removeAll();
        add(createCenterPanel(inventory), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
