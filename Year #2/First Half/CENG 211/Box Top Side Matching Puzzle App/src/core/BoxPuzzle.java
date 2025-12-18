package core;

import model.box.*;
import model.tools.SpecialTool;
import core.exceptions.*;
import java.util.Scanner;

public class BoxPuzzle {
    private BoxGrid grid;
    private char targetLetter;
    private int turns = 5; //

    public BoxPuzzle() {
        this.grid = new BoxGrid();
        this.targetLetter = (char) ('A' + new java.util.Random().nextInt(8));
        System.out.println("Target Letter: " + targetLetter);
        new MenuHandler().play();
    }

    public <T extends SpecialTool> void useTool(T tool, int r, int c) {
        if (tool != null) {
            tool.useTool(this.grid, r, c);
            System.out.println(tool.getName() + " applied to Row " + (r+1) + " Col " + (c+1));
        }
    }

    private class MenuHandler {
        private Scanner sc = new Scanner(System.in);

        public void play() {
            for (int i = 1; i <= turns; i++) {
                System.out.println("\n--- TURN " + i + " ---");
                grid.displayGrid();

                try {
                    promptEdgeRoll();
                    grid.displayGrid();
                    promptOpenBox();
                } catch (UnmovableFixedBoxException | EmptyBoxException e) {
                    System.out.println("\n>>> " + e.getMessage() + " Turn wasted! <<<");
                } catch (Exception e) {
                    System.out.println("Input error, try again.");
                }
            }
            showFinalScore();
        }

        public void promptEdgeRoll() throws UnmovableFixedBoxException {
            System.out.println("\n[STAGE 1] Roll a box from the edges.");
            System.out.print("Location (e.g., R1-C4): ");
            String loc = sc.nextLine().toUpperCase();

            int r = parseRow(loc);
            int c = parseCol(loc);

            if (r == 0 || r == 7 || c == 0 || c == 7) {
                System.out.print("Direction (1: RIGHT/DOWN, 2: LEFT/UP): ");
                int choice = Integer.parseInt(sc.nextLine());
                Direction dir = (choice == 1) ? Direction.RIGHT : Direction.DOWN;
                grid.rollEdgeBox(r, c, dir);
            } else {
                System.out.println("INCORRECT INPUT: Selected box is not on the edge.");
                promptEdgeRoll(); // Tekrar sorar
            }
        }

        public void promptOpenBox() throws EmptyBoxException {
            System.out.println("\n[STAGE 2] Open a box that was moved.");
            System.out.print("Location to open: ");
            String loc = sc.nextLine().toUpperCase();

            int r = parseRow(loc);
            int c = parseCol(loc);

            Box selectedBox = grid.getBox(r, c);

            if (selectedBox.isMovable()) {
                SpecialTool tool = selectedBox.open();
                if (tool != null) {
                    useTool(tool, r, c);
                } else {
                    throw new EmptyBoxException("This box is empty!");
                }
            } else {
                System.out.println("This box cannot be opened because it didn't move.");
            }
        }

        private int parseRow(String loc) {
            return Integer.parseInt(loc.substring(loc.indexOf("R") + 1, loc.indexOf("-"))) - 1;
        }
        private int parseCol(String loc) {
            return Integer.parseInt(loc.substring(loc.indexOf("C") + 1)) - 1;
        }

        private void showFinalScore() {
            int result = grid.countTargetLetterOnTop(targetLetter);
            System.out.println("\nFINAL SCORE for '" + targetLetter + "': " + result);
            System.out.println(result > 0 ? "SUCCESS!" : "FAILURE!");
        }
    }
}
