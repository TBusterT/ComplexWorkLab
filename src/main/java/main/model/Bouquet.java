package main.model;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Bouquet {
    private final List<Flower> flowers = new ArrayList<>();
    private final List<Accessory> accessories = new ArrayList<>();

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public boolean removeFlower(Flower flower) {
        return flowers.remove(flower);
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
    }

    public boolean removeAccessory(Accessory accessory) {
        return accessories.remove(accessory);
    }


    public List<Flower> getFlowers() {
        return flowers;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Flower f : flowers) total += f.getPrice();
        for (Accessory a : accessories) total += a.getPrice();
        return total;
    }

    public void sortByFreshnessDescending() {
        flowers.sort(Comparator.comparingInt(Flower::getFreshness).reversed());
    }

    public List<Flower> findByStemLengthRange(double min, double max) {
        return flowers.stream()
                .filter(f -> f.getStemLength() >= min && f.getStemLength() <= max)
                .collect(Collectors.toList());
    }


    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // Зберігаємо квітки
            for (Flower f : flowers) {
                String kind = f.getClass().getSimpleName(); // Models.Flower, Models.GardenFlower, Models.ExoticFlower
                String line = String.join("|",
                        "F",
                        kind,
                        safe(f.getName()),
                        safe(f.getColor()),
                        Double.toString(f.getStemLength()),
                        Double.toString(f.getPrice()),
                        Integer.toString(f.getFreshness()),
                        Integer.toString(f.getLifespan())
                );
                if (f instanceof GardenFlower) {
                    GardenFlower gf = (GardenFlower) f;
                    line += "|" + safe(gf.getVariety()) + "|" + safe(gf.getFragranceLevel());
                } else if (f instanceof ExoticFlower) {
                    ExoticFlower ef = (ExoticFlower) f;
                    line += "|" + safe(ef.getOriginCountry()) + "|" + safe(ef.getCareComplexity());
                }
                bw.write(line);
                bw.newLine();
            }

            // Зберігаємо аксесуари
            for (Accessory a : accessories) {
                String line = String.join("|",
                        "A",
                        safe(a.getName()),
                        Double.toString(a.getPrice()),
                        safe(a.getMaterialOrColor())
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        flowers.clear();
        accessories.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String ln;
            while ((ln = br.readLine()) != null) {
                String[] p = ln.split("\\|", -1);
                if (p.length == 0) continue;
                if ("F".equals(p[0])) {
                    String kind = p[1];
                    String name = unsafe(p[2]);
                    String color = unsafe(p[3]);
                    double stem = Double.parseDouble(p[4]);
                    double price = Double.parseDouble(p[5]);
                    int fresh = Integer.parseInt(p[6]);
                    int life = Integer.parseInt(p[7]);

                    if ("GardenFlower".equals(kind)) {
                        String variety = p.length > 8 ? unsafe(p[8]) : "";
                        String frag = p.length > 9 ? unsafe(p[9]) : "";
                        flowers.add(new GardenFlower(name, color, stem, price, fresh, life, variety, frag));
                    } else if ("ExoticFlower".equals(kind)) {
                        String origin = p.length > 8 ? unsafe(p[8]) : "";
                        String care = p.length > 9 ? unsafe(p[9]) : "";
                        flowers.add(new ExoticFlower(name, color, stem, price, fresh, life, origin, care));
                    } else {
                        flowers.add(new Flower(name, color, stem, price, fresh, life));
                    }
                } else if ("A".equals(p[0])) {
                    String name = unsafe(p[1]);
                    double price = Double.parseDouble(p[2]);
                    String mat = unsafe(p[3]);
                    accessories.add(new Accessory(name, price, mat));
                }
            }
        }
    }

    private String safe(String s) {
        return s == null ? "" : s.replace("|", "/");
    }

    private String unsafe(String s) {
        return s == null ? "" : s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bouquet:\n");
        for (int i = 0; i < flowers.size(); i++) {
            sb.append("  F").append(i + 1).append(": ").append(flowers.get(i)).append("\n");
        }
        for (int i = 0; i < accessories.size(); i++) {
            sb.append("  A").append(i + 1).append(": ").append(accessories.get(i)).append("\n");
        }
        sb.append(String.format("Total price: %.2f", getTotalPrice()));
        return sb.toString();
    }
}
