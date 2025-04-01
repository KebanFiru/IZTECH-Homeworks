public class Chest extends Bag<Treasure> {
    public void initializeChest() {
        for (int i = 0; i < 195; i++) {
            add(new Turquoise(1));
        }

        for (int i = 0; i < 120; i++) {
            add(new Obsidian(5));
        }

        for (int i = 0; i < 45; i++) {
            add(new Gold(10));
        }
    }
}

