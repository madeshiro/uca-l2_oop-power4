package project.uca.power4.ui;

import project.uca.power4.Application;
import project.uca.power4.components.Box;
import project.uca.power4.components.Direction;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.entity.LivingPlayer;
import project.uca.power4.entity.Player;

import java.io.File;
import java.util.Locale;

public class ConsoleUI implements GameInterface {

    private final Application app;
    private final Grid gameGrid;

    private static final String RED    = "\u001b[31m";
    private static final String YELLOW = "\u001b[33m";
    private static final String RESET  = "\u001b[0m";

    private String printToken(Token token) {
        String color;
        switch (token) {
            case Red:
                color = RED + "◙";
                break;
            case Yellow:
                color = YELLOW + "◙";
                break;
            default:
                color = RESET + "○";
                break;
        }

        return color + RESET;
    }

    public ConsoleUI(Application parent, Grid gameGrid) {
        this.app = parent;
        this.gameGrid = gameGrid;
    }

    @Override
    public boolean requestLoadSave() {
        File save = new File("save.txt");
        if (save.exists()) {
            app.out.print("Do you want to load your previous game ? (y/n) ");
            return "y".equals(app.console.readLine().toLowerCase(Locale.ROOT));
        }
        return false;
    }

    @Override
    public void updateGrid() {
        StringBuilder buffer = new StringBuilder();
        Box lefty = gameGrid.getLinkedMatrix();
        while (lefty != null) {
            Box row = lefty;
            StringBuilder rowBuffer = new StringBuilder();
            while (row != null) {
                rowBuffer.append(printToken(row.getToken())).append(" ");
                row = row.getNeighbour(Direction.RIGHT);
            }
            rowBuffer.append('\n');
            buffer.insert(0, rowBuffer);
            lefty = lefty.getNeighbour(Direction.UP);
        }

        app.out.print(buffer);
    }

    @Override
    public Action waitPlayer(LivingPlayer player) {
        while (true) {
            try {
                app.out.printf("It's the turn of %s !\n", player.getName());
                app.out.print("Type the column where you want to put your token (1 to 7) or 'q' to save&quit\n$ ");
                String choice = app.console.readLine();
                if (choice.equals("q")) {
                    return Action.Quit;
                }
                return Action.values()[Integer.parseInt(choice)];
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                app.out.println("Invalid choice ! (expect number between 1 and 7 or 'q')");
            }
        }
    }

    @Override
    public void displayWinner(Player player) {
        app.out.printf("Player %s win !\n", player.getName());
    }

    @Override
    public boolean requestPlayers() {
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                app.out.print("How many players ?\n\t1- one player\n\t2- two players\n");
                choice = Integer.parseInt(app.console.readLine());
                validChoice = (choice == 1 || choice == 2);
            } catch (NumberFormatException e) {
                app.out.println("Invalid number ! (expect 1 or 2)");
            }
        }

        return choice == 2;
    }

    @Override
    public String requestPlayerName(String prompt) {
        app.out.printf("Name of player %s: ", prompt);
        return app.console.readLine();
    }
}
