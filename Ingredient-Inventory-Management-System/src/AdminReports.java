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
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(248, 249, 255));

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 15));
        statusPanel.setPreferredSize(new Dimension(400, 270));
        statusPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        statusPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel statusLabel = new JLabel("Stock Status Summary");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        statusPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(5, 2, 20, 5));
        grid.setBackground(Color.WHITE);

        // Total Ingredients
        JLabel t1 = new JLabel("Total Ingredients");
        totalIngredientsLabel = new JLabel(String.valueOf(mainFrame.inventory.size()));

        // Low Stock
        JLabel t2 = new JLabel("Low Stock Items");
        lowStockLabel = new JLabel(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.getQuantity() < i.getMinLevel() && i.getQuantity() > 0).count()
        ));
        t2.setForeground(Color.decode("#FFB823")); 
        lowStockLabel.setForeground(Color.decode("#FFD700"));

        // Expiring Soon
        JLabel t3 = new JLabel("Expiring Items");
        expiringLabel = new JLabel(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.isExpiringSoon()).count()
        ));
        t3.setForeground(Color.decode("#DC6B19")); 
        expiringLabel.setForeground(Color.decode("#DC6B19"));

        // Expired
        JLabel t4 = new JLabel("Expired Items");
        expiredLabel = new JLabel(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.isExpired()).count()
        ));
        t4.setForeground(Color.decode("#BF1A1A")); 
        expiredLabel.setForeground(Color.decode("#BF1A1A"));

        // Out of Stock
        JLabel t5 = new JLabel("Out of Stock");
        outOfStockLabel = new JLabel(String.valueOf(
                mainFrame.inventory.stream().filter(i -> i.getQuantity() == 0).count()
        ));
        t5.setForeground(Color.decode("#BF1A1A")); 
        outOfStockLabel.setForeground(Color.decode("#BF1A1A"));

        // Align left
        t1.setHorizontalAlignment(SwingConstants.LEFT);
        t2.setHorizontalAlignment(SwingConstants.LEFT);
        t3.setHorizontalAlignment(SwingConstants.LEFT);
        t4.setHorizontalAlignment(SwingConstants.LEFT);
        t5.setHorizontalAlignment(SwingConstants.LEFT);

        // Add to grid
        grid.add(t1); grid.add(totalIngredientsLabel);
        grid.add(t2); grid.add(lowStockLabel);
        grid.add(t3); grid.add(expiringLabel);
        grid.add(t4); grid.add(expiredLabel);
        grid.add(t5); grid.add(outOfStockLabel);

        statusPanel.add(grid, BorderLayout.CENTER);
        leftPanel.add(statusPanel, BorderLayout.NORTH);

        return leftPanel;
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
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(248, 249, 255));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel topUsedPanel = new JPanel();
        topUsedPanel.setLayout(new BoxLayout(topUsedPanel, BoxLayout.Y_AXIS));
        topUsedPanel.setBackground(Color.WHITE);
        topUsedPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 15));
        topUsedPanel.setPreferredSize(new Dimension(400, 270));
        topUsedPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        topUsedPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel topUsedLabel = new JLabel("Top Used Ingredients");
        topUsedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topUsedLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topUsedPanel.add(topUsedLabel);

        JLabel topUsedSubtitle = new JLabel("Most frequently used items (This month)");
        topUsedSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        topUsedSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topUsedPanel.add(topUsedSubtitle);

        int[] usageValues = {8, 5, 3, 6, 2};
        String[] ingredientNames = {"Tomato", "Cheese", "Onion", "Lettuce", "Pepper"};

        BarChartPanel chartPanel = new BarChartPanel(usageValues, ingredientNames);

        JPanel chartWrapper = new JPanel(new BorderLayout());
        chartWrapper.setBackground(Color.WHITE);
        chartWrapper.add(chartPanel, BorderLayout.CENTER);
        chartWrapper.setPreferredSize(new Dimension(400, 270));

        topUsedPanel.add(chartWrapper);

        rightPanel.add(topUsedPanel, BorderLayout.NORTH);
        return rightPanel;
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

    static class BarChartPanel extends JPanel {
        private int[] values;
        private String[] labels;

        public BarChartPanel(int[] values, String[] labels) {
            this.values = values;
            this.labels = labels;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int leftPadding = 50;
            int bottomPadding = 100;
            int topPadding = 20;

            int maxValue = 1;
            for (int val : values) if (val > maxValue) maxValue = val;

            int barAreaWidth = width - leftPadding - 20;
            int barWidth = barAreaWidth / (values.length * 2);

            g2.setColor(Color.BLACK);
            g2.drawLine(leftPadding, topPadding, leftPadding, height - bottomPadding);

            int markerCount = 5;
            for (int i = 0; i <= markerCount; i++) {
                int y = height - bottomPadding - (height - bottomPadding - topPadding) * i / markerCount;
                int valueLabel = maxValue * i / markerCount;
                g2.setColor(Color.GRAY);
                g2.drawLine(leftPadding - 5, y, width - 20, y);
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(valueLabel), 10, y + 5);
            }

            for (int i = 0; i < values.length; i++) {
                int barHeight = (height - bottomPadding - topPadding) * values[i] / maxValue;
                int x = leftPadding + (i * 2 + 1) * barWidth;
                int y = height - bottomPadding - barHeight;

                g2.setColor(new Color(100, 149, 237));
                g2.fillRect(x, y, barWidth, barHeight);

                g2.setColor(Color.BLACK);
                String valueText = String.valueOf(values[i]);
                int stringWidth = g2.getFontMetrics().stringWidth(valueText);
                g2.drawString(valueText, x + (barWidth - stringWidth) / 2, y - 5);

                String label = labels[i];
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x + (barWidth - labelWidth) / 2, height - bottomPadding + 20);
            }
        }
    }
}      