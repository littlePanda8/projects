import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class AdminReports extends JPanel {

    private AdminMainFrame mainFrame;
    private DefaultTableModel tableModel;

    public AdminReports(AdminMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 255));

        // ─── TOP SECTION ───────────────────────────────────────
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(248, 249, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel reportsLabel = new JLabel("Reports");
        reportsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel subtitleLabel = new JLabel("Generate summaries of stock, usage, and expirations.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(reportsLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitleLabel);

        // ─── MAIN CONTENT PANEL ────────────────────────────────
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(248, 249, 255));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ─── TOP LEFT CONTAINER ────────────────────────────────
        JPanel topLeftContainer = new JPanel();
        topLeftContainer.setLayout(new BoxLayout(topLeftContainer, BoxLayout.X_AXIS));
        topLeftContainer.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel statusPanel = createStatusPanel();
        JPanel topUsedPanel = createTopUsedPanel();

        topLeftContainer.add(statusPanel);
        topLeftContainer.add(topUsedPanel);
        topLeftContainer.add(Box.createVerticalGlue());

        mainContentPanel.add(topLeftContainer);

        // ─── INVENTORY PANEL ────────────────────────────────
        JPanel inventoryPanel = createInventoryPanel();

        JPanel inventoryWrapperPanel = new JPanel();
        inventoryWrapperPanel.setLayout(new BorderLayout());
        inventoryWrapperPanel.setBackground(new Color(248, 249, 255));
        inventoryWrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inventoryWrapperPanel.add(inventoryPanel, BorderLayout.CENTER);

        mainContentPanel.add(inventoryWrapperPanel);

        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    // Add these at the top of AdminReports class
    private JLabel totalIngredientsLabel;
    private JLabel lowStockLabel;
    private JLabel expiringLabel;
    private JLabel expiredLabel;
    private JLabel outOfStockLabel;

    // Updated createStatusPanel method
    private JPanel createStatusPanel() {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));

    JLabel titleLabel = new JLabel("Stock Status Summary");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.add(titleLabel);
    card.add(Box.createVerticalStrut(20));

    card.add(createStatusRow("Total Ingredients", String.valueOf(mainFrame.inventory.size()), Color.BLACK));
    card.add(Box.createVerticalStrut(3));
    card.add(createStatusRow("Low Stock Items", String.valueOf(
            mainFrame.inventory.stream().filter(i -> i.getQuantity() < i.getMinLevel() && i.getQuantity() > 0).count()
    ), new Color(255, 140, 0)));
    card.add(Box.createVerticalStrut(3));
    card.add(createStatusRow("Expiring Items", String.valueOf(
            mainFrame.inventory.stream().filter(i -> i.isExpiringSoon()).count()
    ), new Color(255, 140, 0)));
    card.add(Box.createVerticalStrut(3));
    card.add(createStatusRow("Expired Items", String.valueOf(
            mainFrame.inventory.stream().filter(i -> i.isExpired()).count()
    ), new Color(220, 53, 69)));
    card.add(Box.createVerticalStrut(3));
    card.add(createStatusRow("Out of Stock", String.valueOf(
            mainFrame.inventory.stream().filter(i -> i.getQuantity() == 0).count()
    ), new Color(220, 53, 69)));

    return card;
}

    // New method to refresh summary labels
    public void refreshSummary() {
        totalIngredientsLabel.setText(String.valueOf(mainFrame.inventory.size()));
        lowStockLabel.setText(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.getQuantity() < i.getMinLevel() && i.getQuantity() > 0).count()
        ));
        expiringLabel.setText(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.isExpiringSoon()).count()
        ));
        expiredLabel.setText(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.isExpired()).count()
        ));
        outOfStockLabel.setText(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.getQuantity() == 0).count()
        ));
    }

    private JPanel createTopUsedPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(248, 249, 255));

        JPanel topUsedPanel = new JPanel();
        topUsedPanel.setLayout(new BoxLayout(topUsedPanel, BoxLayout.Y_AXIS));
        topUsedPanel.setBackground(Color.WHITE);
        topUsedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // FIXED HEIGHT and WIDTH like Reports.java
        topUsedPanel.setPreferredSize(new Dimension(400, 400));
        topUsedPanel.setMaximumSize(new Dimension(400, 400));

        JLabel titleLabel = new JLabel("Top Used Ingredients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Most frequently used items (This month)");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ChartPanel chartPanel = new ChartPanel(); // dual-bar chart

        topUsedPanel.add(titleLabel);
        topUsedPanel.add(Box.createVerticalStrut(5));
        topUsedPanel.add(subtitleLabel);
        topUsedPanel.add(Box.createVerticalStrut(10));
        topUsedPanel.add(chartPanel);

        wrapper.add(topUsedPanel, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel createInventoryPanel() {
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(Color.WHITE);
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inventoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel inventoryLabel = new JLabel("Complete Inventory Status");
        inventoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inventoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inventoryPanel.add(inventoryLabel);

        JLabel inventorySubtitle = new JLabel("All ingredients with current status");
        inventorySubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        inventorySubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        inventoryPanel.add(inventorySubtitle);

        // Combo boxes
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        comboBoxPanel.setBackground(Color.WHITE);
        comboBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] categoryOptions = {"All", "Alphabetical (A - Z)", "Alphabetical (Z - A)", "Quantity (Low - High)", "Quantity (High - Low)", "Exp. Date (Soonest First)", "Exp. Date (Latest First)"};
        String[] typeOptions = {"All", "Dairy", "Meat", "Poultry", "Vegetable"};
        String[] statusOptions = {"All", "Good Stock", "Low Stock", "Expiring Soon", "Out of Stock", "Expired"};

        JComboBox<String> sortComboBox = new JComboBox<>(categoryOptions);
        JComboBox<String> typeComboBox = new JComboBox<>(typeOptions);
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);

        comboBoxPanel.add(sortComboBox);
        comboBoxPanel.add(typeComboBox);
        comboBoxPanel.add(statusComboBox);

        inventoryPanel.add(Box.createVerticalStrut(10));
        inventoryPanel.add(comboBoxPanel);

        // Table setup
        String[] columnNames = {"Name", "Category", "Quantity", "Min. Level", "Unit", "Expiry Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setBackground(Color.WHITE);

        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setOpaque(false);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.BLACK);

                if (column == 6) {
                    switch (String.valueOf(value)) {
                        case "Expiring Soon" -> { label.setBackground(new Color(255, 204, 153)); label.setOpaque(true); }
                        case "Out of Stock", "Expired" -> { label.setBackground(new Color(255, 102, 102)); label.setOpaque(true); }
                        case "Low Stock" -> { label.setBackground(new Color(255, 255, 102)); label.setOpaque(true); }
                    }
                    label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                }
                return label;
            }
        };

        table.getColumnModel().getColumn(6).setCellRenderer(statusRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Set preferred size to show more rows
        scrollPane.setPreferredSize(new Dimension(700, 400)); // adjust width & height as needed

        inventoryPanel.add(Box.createVerticalStrut(10));
        inventoryPanel.add(scrollPane);

        refreshTable();

        // Filter action
        ActionListener filterAction = e -> refreshTable((String) sortComboBox.getSelectedItem(),
                                                        (String) typeComboBox.getSelectedItem(),
                                                        (String) statusComboBox.getSelectedItem());
        sortComboBox.addActionListener(filterAction);
        typeComboBox.addActionListener(filterAction);
        statusComboBox.addActionListener(filterAction);

        return inventoryPanel;
    }

    // Populate table from mainFrame.inventory
    public void refreshTable() {
        refreshTable("All", "All", "All");
    }

    private void refreshTable(String sortOption, String typeFilter, String statusFilter) {
        tableModel.setRowCount(0);

        List<Ingredient> filtered = mainFrame.inventory.stream().filter(i -> 
            (typeFilter.equals("All") || i.getType().equals(typeFilter)) &&
            (statusFilter.equals("All") || i.getStatus().equals(statusFilter))
        ).collect(Collectors.toList()); // <-- mutable list

        filtered.sort((a, b) -> {
            return switch (sortOption) {
                case "Alphabetical (A - Z)" -> a.getName().compareTo(b.getName());
                case "Alphabetical (Z - A)" -> b.getName().compareTo(a.getName());
                case "Quantity (Low - High)" -> Integer.compare(a.getQuantity(), b.getQuantity());
                case "Quantity (High - Low)" -> Integer.compare(b.getQuantity(), a.getQuantity());
                default -> 0;
            };
        });

        for (Ingredient i : filtered) {
            tableModel.addRow(new Object[]{
                    i.getName(), i.getType(), i.getQuantity(),
                    i.getMinLevel(), i.getUnit(), i.getExpiryDate(),
                    i.getStatus()
            });
        }
    }

    private JPanel createStatusRow(String label, String value, Color valueColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.BOLD, 14));
        valueComponent.setForeground(valueColor);

        row.add(labelComponent, BorderLayout.WEST);
        row.add(valueComponent, BorderLayout.EAST);

        return row;
    }


static class ChartPanel extends JPanel {
    private final String[] labels = {"Eggs", "Fresh Pork", "Tomatoes"};
    private final int[] values1 = {60, 100, 30};
    private final int[] values2 = {40, 0, 70};

    public ChartPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 200)); // enough height for lines and labels
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int topPadding = 10;
        int bottomPadding = 5; // space for labels
        int leftPadding = 20;   // space for Y-axis labels

        int barWidth = 40;
        int gap = 15; // space between bars
        int startX = leftPadding + 10; // shift bars right

        // Drawable height for bars
        int drawableHeight = height - topPadding - bottomPadding;

        // Draw Y-axis percentage lines
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.setColor(Color.GRAY);

        int markerCount = 6; // 0%, 20%, 40%, 60%, 80%, 100%
        for (int i = 0; i <= markerCount; i++) {
            int y = height - bottomPadding - drawableHeight * i / markerCount;

            // even spacing up to 100%
            String yLabel = (i * 100 / markerCount) + "%";

            g2.drawString(yLabel, 10, y + 5);

            g2.setColor(new Color(220, 220, 220));
            g2.drawLine(leftPadding, y, width - 20, y);
            g2.setColor(Color.GRAY);
        }

        // Draw bars and labels
        for (int i = 0; i < labels.length; i++) {
            int x = startX + i * (barWidth + gap);

            // Primary bar
            int barHeight1 = drawableHeight * values1[i] / 100;
            g2.setColor(new Color(255, 99, 71));
            g2.fillRect(x, height - bottomPadding - barHeight1, barWidth, barHeight1);

            // Secondary bar (stacked on top of primary)
            if (values2[i] > 0) {
                int barHeight2 = drawableHeight * values2[i] / 100;
                g2.setColor(new Color(200, 200, 200));
                g2.fillRect(x, height - bottomPadding - barHeight1 - barHeight2, barWidth, barHeight2);
            }

            // Value above the bar
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 9));
            String valueText = String.valueOf(values1[i]);
            int stringWidth = g2.getFontMetrics().stringWidth(valueText);
            
            int valueY = height - bottomPadding - barHeight1 - 3;
            if (valueY < topPadding + 10) valueY = topPadding + 10; // prevent clipping at top

            g2.drawString(valueText,
                        x + (barWidth - stringWidth) / 2,
                        valueY);

            // Label below each bar
            String label = labels[i];
            int labelWidth = g2.getFontMetrics().stringWidth(label);
            g2.drawString(label, x + (barWidth - labelWidth) / 2, height - bottomPadding + 20);
        }
    }

}


}

