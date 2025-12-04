package Model;

public class GardenFlower extends Flower {
    private String variety;
    private String fragranceLevel;

    public GardenFlower() {}

    public GardenFlower(String name, String color, double stemLength, double price, int freshness, int lifespan,
                        String variety, String fragranceLevel) {
        super(name, color, stemLength, price, freshness, lifespan);
        this.variety = variety;
        this.fragranceLevel = fragranceLevel;
    }

    public String getVariety() { return variety; }
    public void setVariety(String variety) { this.variety = variety; }

    public String getFragranceLevel() { return fragranceLevel; }
    public void setFragranceLevel(String fragranceLevel) { this.fragranceLevel = fragranceLevel; }

    @Override
    public String toString() {
        return "GardenFlower{" +
                "variety='" + variety + '\'' +
                ", fragranceLevel='" + fragranceLevel + '\'' +
                "} " + super.toString();
    }
}
