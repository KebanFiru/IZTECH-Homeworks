package penguins;

public enum PenguinType {
    KING,
    EMPEROR,
    ROYAL,
    ROCKHOPPER;

    public static PenguinType getRandom() {
        PenguinType[] values = PenguinType.values();
        int index = (int)(Math.random() * values.length);
        return values[index];
    }
}