// StaffMainFrame.java
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffMainFrame extends JFrame {

    public StaffOverview staffOverview;
    public StaffInventory staffInventory;
    public StaffSettings staffSettings;

    public List<Ingredient> inventory; // Shared inventory
    private JPanel mainPanel;

    public StaffMainFrame() {
        setTitle("Ingredient Inventory Management System - Staff");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // ===================== Initialize Inventory =====================
        inventory = new ArrayList<>();
        inventory.add(new Ingredient("Eggs", "Poultry", 6, 10, "pcs", "12/01/2025"));
         inventory.add(new Ingredient("Milk", "Dairy", 12, 10, "L", "12/05/2025"));
         inventory.add(new Ingredient("Tomatoes", "Vegetable", 5, 10, "kg", "11/25/2025"));
         inventory.add(new Ingredient("Fresh Chicken", "Meat", 3, 5, "pcs", "11/23/2025"));
         inventory.add(new Ingredient("Fresh Pork", "Meat", 0, 5, "pcs", "11/20/2025"));

        // ===================== Top Navigation =====================
        StaffTopNavBar staffTopNavBar = new StaffTopNavBar(this);
        add(staffTopNavBar, BorderLayout.NORTH);

        // ===================== Main Panels =====================
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        
        staffOverview = new StaffOverview(this, inventory);
        staffInventory = new StaffInventory(this, inventory);
        staffSettings = new StaffSettings(this);

        // ===================== Show Default Panel =====================
        showPanel(staffOverview);

        setVisible(true);
    }

    // ===================== Panel Switcher =====================
    public void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StaffMainFrame::new);
    }
}
