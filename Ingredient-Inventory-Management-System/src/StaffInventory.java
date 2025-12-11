import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class StaffInventory extends JPanel {

    private StaffMainFrame staffMainFrame;
    private JTable table;
    private DefaultTableModel model;
    private JLabel totalIngredientsLabel;

    public StaffInventory(StaffMainFrame staffMainFrame, List<Ingredient> inventory) {
        this.staffMainFrame = staffMainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 255));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(248, 249, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel overviewLabel = new JLabel("Ingredient Inventory");
        overviewLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel subtitleLabel = new JLabel("Monitor critical inventory issues");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(overviewLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitleLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        
        // --- generate table data from inventory ---
        String[] columnNames = {"Name", "Category", "Quantity", "Min. Level", "Unit", "Expiry Date", "Status"};
        Object[][] data = new Object[inventory.size()][7];

        java.time.LocalDate today = java.time.LocalDate.now();

        for (int i = 0; i < inventory.size(); i++) {
            Ingredient ing = inventory.get(i);
            data[i][0] = ing.getName();
            data[i][1] = ing.getCategory();
            data[i][2] = ing.getQuantity();
            data[i][3] = ing.getMinLevel();
            data[i][4] = ing.getUnit();
            data[i][5] = ing.getExpiryDate().toString();

            // Determine status
            if (ing.getQuantity() == 0) data[i][6] = "Out of Stock";
            else if (ing.getExpiryDate().isBefore(today)) data[i][6] = "Expired";
            else if (!ing.getExpiryDate().isBefore(today) && ing.getExpiryDate().isBefore(today.plusDays(7))) data[i][6] = "Expiring Soon";
            else if (ing.getQuantity() <= ing.getMinLevel()) data[i][6] = "Low Stock";
            else data[i][6] = "Good Stock";
        }

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return c;
            }
        };

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        // Set the height of the header
        header.setPreferredSize(new Dimension(header.getWidth(), table.getRowHeight()));

        // Remove sorting
        table.setAutoCreateRowSorter(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        
table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // reset background always
        c.setBackground(Color.WHITE);

        // Apply color ONLY to STATUS column (column 6)
        if (column == 6) {
            String status = value.toString();

            switch (status) {
                case "Out of Stock":
                    c.setBackground(new Color(255, 127, 127)); // red
                    break;

                case "Low Stock":
                    c.setBackground(new Color(255, 229, 153)); // yellow
                    break;

                case "Expiring Soon":
                    c.setBackground(new Color(255, 179, 102)); // orange
                    break;

                case "Expired":
                    c.setBackground(new Color(180, 180, 180)); // gray
                    break;

                default:
                    c.setBackground(Color.WHITE);
            }
        }

        // Keep blue highlight when selecting ANY cell
        if (isSelected) c.setBackground(new Color(150, 200, 255));

        return c;
    }
});

        class IconTextField extends JTextField {

            private int cornerRadius = 15;
            private String placeholder = "Search ingredient here...";

            public IconTextField() {
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(5, 28, 5, 5));
                setText(placeholder);
                setForeground(Color.GRAY);

                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (getText().equals(placeholder)) {
                            setText("");
                            setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (getText().trim().isEmpty()) {
                            setText(placeholder);
                            setForeground(Color.GRAY);
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
                g2.dispose();
            }
        }

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout()); // Use BorderLayout
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 40));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3)); 
        leftPanel.setOpaque(false); // Make it transparent
        IconTextField searchField = new IconTextField();
        searchField.setPreferredSize(new Dimension(350, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        String placeholder = "Search ingredient here...";
        searchField.setText(placeholder);
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                Component opposite = e.getOppositeComponent();
                if ((searchField.getText().isEmpty()) &&
                    (opposite == null || !SwingUtilities.isDescendingFrom(opposite, searchPanel))) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        String[] comboBoxOptions = {"Alphabetical (A - Z)", "Alphabetical (Z - A)", "Quantity (Low - High)", "Quantity (High - Low)", "Exp. Date (Soonest First)", "Exp. Date (Latest First)"};

        JComboBox<String> comboBox = new JComboBox<>(comboBoxOptions);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(200, 30)); // set preferred size for combobox

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                System.out.println("Selected option: " + selectedOption);
            }
        });

        leftPanel.add(searchField);
        leftPanel.add(comboBox);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 3));
        rightPanel.setOpaque(false); 

        rightPanel.add(Box.createHorizontalStrut(-20)); 

        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(rightPanel, BorderLayout.EAST);

        totalIngredientsLabel = new JLabel("Total Ingredients: " + model.getRowCount());
        totalIngredientsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalIngredientsLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 20));
        totalIngredientsLabel.setBackground(Color.WHITE);

        JPanel searchAndTotalPanel = new JPanel(new BorderLayout());
        searchAndTotalPanel.setBackground(Color.WHITE);
        searchAndTotalPanel.add(searchPanel, BorderLayout.NORTH);
        searchAndTotalPanel.add(totalIngredientsLabel, BorderLayout.SOUTH);

        contentPanel.add(searchAndTotalPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setOpaque(false);

        outerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 20, 40, 20));
        outerWrapper.add(contentPanel, BorderLayout.CENTER);

        add(outerWrapper, BorderLayout.CENTER);
    }
}