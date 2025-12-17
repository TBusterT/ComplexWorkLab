package main.java.model;

public class Flower {
    private String name;
    private String color;
    private double stemLength;
    private double price;
    private int freshness;
    private int lifespan;

    public Flower() {}

    public Flower(String name, String color, double stemLength, double price, int freshness, int lifespan) {
        this.name = name;
        this.color = color;
        this.stemLength = stemLength;
        this.price = price;
        this.freshness = freshness;
        this.lifespan = lifespan;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getStemLength() { return stemLength; }
    public void setStemLength(double stemLength) { this.stemLength = stemLength; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getFreshness() { return freshness; }
    public void setFreshness(int freshness) { this.freshness = freshness; }

    public int getLifespan() { return lifespan; }
    public void setLifespan(int lifespan) { this.lifespan = lifespan; }

    @Override
    public String toString() {
        return "Flower{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", stemLength=" + stemLength +
                ", price=" + price +
                ", freshness=" + freshness +
                ", lifespan=" + lifespan +
                '}';
    }
}
