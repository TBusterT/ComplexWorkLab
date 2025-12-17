package main.java.model;

public class Accessory {
    private String name;
    private double price;
    private String materialOrColor;

    public Accessory() {}

    public Accessory(String name, double price, String materialOrColor) {
        this.name = name;
        this.price = price;
        this.materialOrColor = materialOrColor;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getMaterialOrColor() { return materialOrColor; }
    public void setMaterialOrColor(String materialOrColor) { this.materialOrColor = materialOrColor; }

    @Override
    public String toString() {
        return "Accessory{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", materialOrColor='" + materialOrColor + '\'' +
                '}';
    }
}
