package Model;

public class DecorativePlant extends Flower {
    private String type;
    private String texture;

    public DecorativePlant() {}

    public DecorativePlant(String name, String color, double stemLength, double price, int freshness, int lifespan,
                           String type, String texture) {
        super(name, color, stemLength, price, freshness, lifespan);
        this.type = type;
        this.texture = texture;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTexture() { return texture; }
    public void setTexture(String texture) { this.texture = texture; }

    @Override
    public String toString() {
        return "Models.DecorativePlant{" +
                "type='" + type + '\'' +
                ", texture='" + texture + '\'' +
                "} " + super.toString();
    }
}
