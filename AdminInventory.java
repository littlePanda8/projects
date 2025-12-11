import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.List;


        public class AdminInventory extends JPanel {

            private AdminMainFrame adminMainFrame; 
            private List<Ingredient> inventory;
            private JTable table;
            private DefaultTableModel model;
            private JLabel totalIngredientsLabel;

            public AdminInventory(AdminMainFrame adminMainFrame, List<Ingredient> inventory) {
                this.adminMainFrame = adminMainFrame;
                this.inventory = inventory;

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

                // 1. Define column names
                String[] columnNames = {"Name", "Category", "Quantity", "Min. Level", "Unit", "Expiry Date", "Status", "Actions"};

                // 2. Initialize the model
                model = new DefaultTableModel(new Object[0][0], columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 7; // Only "Actions" column editable
                    }
                };

                // Custom renderer for the "Status" column
                DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                        boolean hasFocus, int row, int column) {

                        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        label.setOpaque(false);
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setForeground(Color.BLACK);

                        if (column == 6) {
                            if ("Low Stock".equals(value)) {
                                label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                                label.setBackground(new Color(255, 204, 153));
                                label.setOpaque(true);
                            } else if ("Out of Stock".equals(value)) {
                                label.setBackground(new Color(255, 102, 102));
                                label.setOpaque(true);
                                label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                            } else if ("Expiring Soon".equals(value)) {
                                label.setBackground(new Color(255, 255, 102));
                                label.setOpaque(true);
                                label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                            } else if ("Expired".equals(value)) {
                                label.setBackground(new Color(255, 255, 102));
                                label.setOpaque(true);
                                label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                            } 
                        }
                        return label;
                    }
                };

                table = new JTable(model);
                table.setFont(new Font("Arial", Font.PLAIN, 14));
                table.setRowHeight(table.getRowHeight() + 10);
                table.setBackground(Color.WHITE);

                table.getColumnModel().getColumn(6).setCellRenderer(statusRenderer);

                JTableHeader header = table.getTableHeader();
                DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
                headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                header.setFont(new Font("Arial", Font.BOLD, 14));
                header.setPreferredSize(new Dimension(header.getWidth(), table.getRowHeight()));

                table.setAutoCreateRowSorter(false);

                // COLUMN WIDTHS
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(2).setPreferredWidth(120);
                table.getColumnModel().getColumn(3).setPreferredWidth(100);
                table.getColumnModel().getColumn(4).setPreferredWidth(50);
                table.getColumnModel().getColumn(5).setPreferredWidth(100);
                table.getColumnModel().getColumn(6).setPreferredWidth(100);
                table.getColumnModel().getColumn(7).setPreferredWidth(130);

                // ========== ACTION BUTTON COLUMN ===============
                table.getColumn("Actions").setCellRenderer(new ActionRenderer());
                table.getColumn("Actions").setCellEditor(new ActionEditor(new JCheckBox()));

                // ----------- Search Panel (unchanged) --------------
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

                JPanel searchPanel = new JPanel(new BorderLayout());
                searchPanel.setBackground(Color.WHITE);
                searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 40));

                JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
                leftPanel.setOpaque(false);

                JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 3));
                rightPanel.setOpaque(false);

                IconTextField searchField = new IconTextField();
                searchField.setPreferredSize(new Dimension(350, 30));
                searchField.setFont(new Font("Arial", Font.PLAIN, 14));
                String placeholder = "Search ingredient here...";
                searchField.setText(placeholder);
                searchField.setForeground(Color.GRAY);

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                table.setRowSorter(sorter);

                // ------------------- SEARCH BAR FUNCTIONALITY-----------------------------
                searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    private void filter() {
                        String text = searchField.getText();
                        if (text.trim().length() == 0 || text.equals(placeholder)) {
                            sorter.setRowFilter(null); // Show all rows
                        } else {
                            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text)); // Case-insensitive
                        }
                    }

                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }

                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }

                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
                });

                // ComboBox options
                String[] comboBoxOptions = {
                    "Alphabetical (A - Z)",
                    "Alphabetical (Z - A)",
                    "Quantity (Low - High)",
                    "Quantity (High - Low)",
                    "Exp. Date (Soonest First)",
                    "Exp. Date (Latest First)"
                };

                JComboBox<String> comboBox = new JComboBox<>(comboBoxOptions);
                comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
                comboBox.setBackground(Color.WHITE);
                comboBox.setPreferredSize(new Dimension(200, 30));

                // ------------------ COMBO BOX FUNCTIONALITY ------------------------------
                comboBox.addActionListener(e -> {
                    int selectedIndex = comboBox.getSelectedIndex();

                    switch (selectedIndex) {
                        case 0: // Alphabetical A-Z
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                            break;
                        case 1: // Alphabetical Z-A
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
                            break;
                        case 2: // Quantity Low-High
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
                            break;
                        case 3: // Quantity High-Low
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING)));
                            break;
                        case 4: // Expiry Soonest First
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(5, SortOrder.ASCENDING)));
                            break;
                        case 5: // Expiry Latest First
                            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(5, SortOrder.DESCENDING)));
                            break;
                        default:
                            sorter.setSortKeys(null);
                    }
                });


                leftPanel.add(searchField);
                leftPanel.add(comboBox);

                JButton addButton = new JButton("+ Add New Ingredient");
                addButton.setBackground(Color.BLACK);
                addButton.setForeground(Color.WHITE);
                addButton.addActionListener(e -> showAddIngredientDialog());
                
                rightPanel.add(addButton);
                searchPanel.add(rightPanel, BorderLayout.EAST);
                searchPanel.add(leftPanel, BorderLayout.WEST);

                totalIngredientsLabel = new JLabel("Total Ingredients: " + model.getRowCount());
                totalIngredientsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                totalIngredientsLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 20));

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

            // ------------ADD INGREDIENT FUNCTIONALITY------------------------
        private void showAddIngredientDialog() {

                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Add New Ingredient", true);
                dialog.setSize(430, 500);
                dialog.setResizable(false);
                dialog.setLayout(new BorderLayout());

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
                inputPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

                /* ================== NAME ================== */
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(nameLabel);

                JTextField nameField = new JTextField();
                nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
                nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(nameField);
                inputPanel.add(Box.createVerticalStrut(12));

                /* ================== CATEGORY ================== */
                JLabel categoryLabel = new JLabel("Category:");
                categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(categoryLabel);

                JComboBox<String> categoryDropdown = new JComboBox<>(new String[]{"Meat", "Vegetable", "Dairy", "Poultry", "Others"});
                categoryDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
                categoryDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(categoryDropdown);
                inputPanel.add(Box.createVerticalStrut(12));

                /* ================== QUANTITY + MIN STOCK (INLINE) ================== */

                JPanel qtyPanel = new JPanel(new GridBagLayout());
                qtyPanel.setOpaque(false);
                qtyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 0, 0, 10); // Reduced vertical inset
                gbc.anchor = GridBagConstraints.WEST;
                gbc.weightx = 0.5;  // Distribute space equally
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Quantity Label and Field
                gbc.gridx = 0;
                gbc.gridy = 0;
                qtyPanel.add(new JLabel("Quantity:"), gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JTextField quantityField = new JTextField(10);
                qtyPanel.add(quantityField, gbc);

                // Min Stock Label and Field
                gbc.gridx = 1;
                gbc.gridy = 0;
                qtyPanel.add(new JLabel("Min. Stock:"), gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                JTextField minStockField = new JTextField(10);
                qtyPanel.add(minStockField, gbc);

                inputPanel.add(qtyPanel);
                inputPanel.add(Box.createVerticalStrut(12));

                /* ================== UNIT ================== */
                JLabel unitLabel = new JLabel("Unit:");
                unitLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(unitLabel);

                JComboBox<String> unitDropdown = new JComboBox<>(new String[]{"pcs", "kg", "L", "g", "mL"});
                unitDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
                unitDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(unitDropdown);
                inputPanel.add(Box.createVerticalStrut(12));

                /* ================== EXPIRATION DATE ================== */
                JLabel expiryLabel = new JLabel("Expiration Date (MM/DD/YYYY):");
                expiryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(expiryLabel);

                MaskFormatter dateMask = null;
                try {
                    dateMask = new MaskFormatter("##/##/####");
                    dateMask.setPlaceholderCharacter('_');
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JFormattedTextField expiryField = new JFormattedTextField(dateMask);
                expiryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
                expiryField.setAlignmentX(Component.LEFT_ALIGNMENT);
                inputPanel.add(expiryField);

                /* ================== BUTTONS ( SAVE AND RESET ALL) ================== */
                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
                JButton saveBtn = new JButton("SAVE");
                saveBtn.setBackground(Color.BLACK);
                saveBtn.setForeground(Color.WHITE);

                JButton clearBtn = new JButton("CLEAR ALL");
                clearBtn.setBackground(Color.BLACK);
                clearBtn.setForeground(Color.WHITE);

                btnPanel.add(saveBtn);
                btnPanel.add(clearBtn);

                dialog.add(inputPanel, BorderLayout.CENTER);
                dialog.add(btnPanel, BorderLayout.SOUTH);

                // --- SAVE BUTTON ACTION ---
                saveBtn.addActionListener(e -> {
                    String name = nameField.getText().trim();
                    String category = (String) categoryDropdown.getSelectedItem();
                    String quantityText = quantityField.getText().trim();
                    String minStockText = minStockField.getText().trim();
                    String unit = (String) unitDropdown.getSelectedItem();
                    String expiryText = expiryField.getText().trim();

                    // Basic validation
                    if (name.isEmpty() || quantityText.isEmpty() || minStockText.isEmpty() || unit.isEmpty() || expiryText.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int quantity, minStock;
                    try {
                        quantity = Integer.parseInt(quantityText);
                        minStock = Integer.parseInt(minStockText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Quantity and Min Stock must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Parse expiry date
                    java.time.LocalDate expiryDate;
                    try {
                        expiryDate = java.time.LocalDate.parse(expiryText, java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    } catch (java.time.format.DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(dialog, "Invalid date format. Use MM/DD/YYYY", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create Ingredient
                    Ingredient newIngredient = new Ingredient(name, category, quantity, minStock, unit, expiryText);

                    // Add to shared inventory in AdminMainFrame
                    adminMainFrame.inventory.add(newIngredient);

                    // Add to table
                    model.addRow(new Object[]{
                        name, category, quantity, minStock, unit, newIngredient.getExpiryDate(), newIngredient.getStatus(), ""
                    });

                    // Update total ingredients
                    totalIngredientsLabel.setText("Total Ingredients: " + model.getRowCount());

                    // Refresh Overview panel
                    adminMainFrame.adminOverview.refreshOverview(adminMainFrame.inventory);
                    adminMainFrame.adminReports.refreshTable(); 
                    adminMainFrame.adminReports.refreshSummary(); 
                    adminMainFrame.adminOverview.revalidate();
                    adminMainFrame.adminOverview.repaint();

                    JOptionPane.showMessageDialog(dialog, "Ingredient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                });

            // --- RESET BUTTON ACTION ---
            clearBtn.addActionListener(e -> {
                nameField.setText("");
                categoryDropdown.setSelectedIndex(0);
                quantityField.setText("");
                minStockField.setText("");   // Clear Min Stock
                unitDropdown.setSelectedIndex(0);
                expiryField.setText("");
            });

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        }


            // ---------- ACTION BUTTON RENDERER -------------
            class ActionRenderer extends JPanel implements TableCellRenderer {
                JButton editBtn = new JButton("Edit");
                JButton deleteBtn = new JButton("Delete");

                public ActionRenderer() {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    editBtn.setFocusable(false);
                    deleteBtn.setFocusable(false);
                    add(editBtn);
                    add(deleteBtn);
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                            boolean hasFocus, int row, int column) {
                    return this;
                }
            }

            // ---------- ACTION BUTTON EDITOR -------------
            class ActionEditor extends AbstractCellEditor implements TableCellEditor {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                JButton editBtn = new JButton("Edit");
                JButton deleteBtn = new JButton("Delete");

                public ActionEditor(JCheckBox checkBox) {
                    panel.add(editBtn);
                    panel.add(deleteBtn);

                    editBtn.addActionListener(e -> editRow());
                    deleteBtn.addActionListener(e -> deleteRow());
                }

                private void editRow() {
                    int row = table.getSelectedRow();
                    if (row < 0) return;

                    String name = (String) model.getValueAt(row, 0);
                    String newQty = JOptionPane.showInputDialog(AdminInventory.this,
                            "Enter new quantity for " + name + ":",
                            "Update Quantity", JOptionPane.PLAIN_MESSAGE);

                    if (newQty != null && !newQty.isEmpty()) {
                        try {
                            int qty = Integer.parseInt(newQty);
                            model.setValueAt(qty, row, 2);

                            int min = (int) model.getValueAt(row, 3);
                            String status = qty == 0 ? "Out of Stock" :
                                            qty <= min ? "Low Stock" : "Good Stock";
                            model.setValueAt(status, row, 6);

                            // ===== Update the shared inventory list =====
                            Ingredient ing = adminMainFrame.inventory.get(row);
                            ing.setQuantity(qty);

                            // Refresh Overview
                            adminMainFrame.adminOverview.refreshOverview(adminMainFrame.inventory);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(AdminInventory.this, "Invalid quantity.");
                        }
                    }

                    stopCellEditing();
                }

                private void deleteRow() {
                    int row = table.getSelectedRow();
                    if (row < 0) return;

                    int confirm = JOptionPane.showConfirmDialog(AdminInventory.this,
                            "Delete this ingredient?", "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Remove from shared inventory
                        adminMainFrame.inventory.remove(row);

                        // Remove from table
                        model.removeRow(row);

                        totalIngredientsLabel.setText("Total Ingredients: " + model.getRowCount());

                        // Refresh Overview
                        adminMainFrame.adminOverview.refreshOverview(adminMainFrame.inventory);
                    }

                    stopCellEditing();
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value,
                                                            boolean isSelected, int row, int column) {
                    return panel;
                }

                @Override
                public Object getCellEditorValue() {
                    return "";
                }
            }
        }