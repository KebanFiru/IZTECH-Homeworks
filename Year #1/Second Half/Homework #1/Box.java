public class Box extends Bag<QuestCard>
{

    public void initializedBox()
    {
        for (int i = 0; i < 3; i++)
        {
            add(new Spider());
            add(new Mummy());
            add(new Fire());
            add(new Goblin());
            add(new Snake());

        }

        int[] turquoiseValues = {33, 36, 39, 42, 45};
        for (int value: turquoiseValues)
        {
            add(new TreasureCard("Turquoise Card" , new Turquoise(), value));
        }

        int[] obsidianValues = {18, 21, 24, 27, 30};
        for (int value: obsidianValues)
        {
            add(new TreasureCard("Obsidian Card" , new Obsidian(), value));
        }

        int[] goldValues = {3, 6, 9, 12, 15};
        for (int value: goldValues)
        {
            add(new TreasureCard("Gold Card" , new Gold(), value));
        }
    }
}
