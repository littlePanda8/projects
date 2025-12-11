import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ingredient {
    private String name;
    private String type;
    private int quantity;
    private int minLevel;
    private String unit;
    private String expiryDate; // stored as "MM/dd/yyyy"

    // constructor, getters, setters
    public Ingredient(String name, String type, int quantity, int minLevel, String unit, String expiryDate) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.minLevel = minLevel;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public int getMinLevel() { return minLevel; }
    public String getUnit() { return unit; }
    public String getExpiryDate() { return expiryDate; }

    public String getStatus() {
        if (quantity == 0) return "Out of Stock";
        LocalDate exp = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate now = LocalDate.now();
        if (exp.isBefore(now)) return "Expired";
        else if (!exp.isBefore(now) && exp.isBefore(now.plusDays(7))) return "Expiring Soon";
        else if (quantity < minLevel) return "Low Stock";
        else return "Good Stock";
    }public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public void setMinLevel(int minLevel) {
    this.minLevel = minLevel;
}

public void setName(String name) {
    this.name = name;
}

public void setType(String type) {
    this.type = type;
}

public void setUnit(String unit) {
    this.unit = unit;
}

public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
}


    // Add these for AdminReports usage
    public boolean isExpired() {
        LocalDate exp = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return exp.isBefore(LocalDate.now());
    }

    public boolean isExpiringSoon() {
        LocalDate exp = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate now = LocalDate.now();
        return !exp.isBefore(now) && exp.isBefore(now.plusDays(7));
    }
}
