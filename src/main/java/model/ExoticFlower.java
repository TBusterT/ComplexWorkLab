package main.java.model;

public class ExoticFlower extends Flower {
    private String originCountry;
    private String careComplexity;

    public ExoticFlower() {}

    public ExoticFlower(String name, String color, double stemLength, double price, int freshness, int lifespan,
                        String originCountry, String careComplexity) {
        super(name, color, stemLength, price, freshness, lifespan);
        this.originCountry = originCountry;
        this.careComplexity = careComplexity;
    }

    public String getOriginCountry() { return originCountry; }
    public void setOriginCountry(String originCountry) { this.originCountry = originCountry; }

    public String getCareComplexity() { return careComplexity; }
    public void setCareComplexity(String careComplexity) { this.careComplexity = careComplexity; }

    @Override
    public String toString() {
        return "ExoticFlower{" +
                "originCountry='" + originCountry + '\'' +
                ", careComplexity='" + careComplexity + '\'' +
                "} " + super.toString();
    }
}
