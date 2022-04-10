package project.uca.power4;

import project.uca.power4.components.Box;
import project.uca.power4.components.Direction;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.entity.BotPlayer;
import project.uca.power4.entity.LivingPlayer;
import project.uca.power4.entity.Player;
import project.uca.power4.ui.ConsoleUI;
import project.uca.power4.ui.GameInterface;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class Application {

    public final Logger log;

    public final Console console;

    public final PrintStream out;
    public final PrintStream err;
    public final InputStream in;

    private final boolean enableGui;
    private GameInterface gameInterface;
    private Grid gameGrid;

    Player player1, player2;

    /**
     * Create a new Application
     * @param gui If true, a JFrame will be displayed.
     * @see ConsoleUI
     * @see project.uca.power4.ui.FrameUI
     */
    public Application(boolean gui) {
        this.log = Logger.getLogger("Power4");
        this.out = System.out;
        this.err = System.err;
        this.in  = System.in;
        this.console = System.console();

        this.enableGui = gui;
    }

    public GameInterface ui() {
        return gameInterface;
    }

    /**
     * Save the grid state in a file designated by a Path object
     * @param path the file's path
     * @see Path
     * @see File#toPath()
     * @return True if the grid has been successfully saved, false otherwise
     */
    public boolean save(Path path) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(), WRITE, CREATE, TRUNCATE_EXISTING);

            writer.write(player1.getName() + '\n');
            if (!player2.isAI()) {
                writer.write(player2.getName());
            }
            writer.write("\n");
            writer.write(gameGrid.toString());
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load the grid state from a file designated by a Path object
     * @param path the file's path
     * @see Path
     * @see File#toPath()
     * @return Which player will play next (0 or 1), -1 on error
     */
    public int load(Path path) {
        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String player1 = reader.readLine();
            String player2 = reader.readLine();

            String[] rows = new String[6];
            for (int row = 5; row >= 0; row--) {
                rows[row] = reader.readLine();
            }

            Box lefty = gameGrid.getLinkedMatrix();
            int _row = 0;
            int filled = 0;
            while (lefty != null) {
                Box row = lefty;
                int col = 0;
                while (row != null) {
                    char c = rows[_row].charAt(col);
                    switch (c) {
                        case 'R':
                            gameGrid.putToken(Token.Red, col+1);
                            filled++;
                            break;
                        case 'Y':
                            gameGrid.putToken(Token.Yellow, col+1);
                            filled++;
                            break;
                    }
                    col++;
                    row = row.getNeighbour(Direction.RIGHT);
                }
                lefty = lefty.getNeighbour(Direction.UP);
                _row++;
            }

            this.player1 = new LivingPlayer(gameGrid, Token.Red, player1);
            if (player2.isEmpty()) {
                this.player2 = new BotPlayer(gameGrid, Token.Yellow);
            } else {
                this.player2 = new LivingPlayer(gameGrid, Token.Yellow, player2);
            }

            return filled%2;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Get if the application is opened with Gui enabled
     * @return if gui is enabled
     */
    public boolean isEnableGui() {
        return enableGui;
    }

    /**
     * Execute the program.
     * @return the exit status
     */
    public int exec() {
        this.gameGrid = new Grid();

        if (enableGui) {
            // Do if I got any time left
            // log.info("GUI Enable !");
            log.severe("Unable to enable GUI (not implemented yet !)");
            return 0x10;
        } else {
            log.info("Playing in mode console !");
            gameInterface = new ConsoleUI(this, gameGrid);
        }

        int turn=0;
        if (!gameInterface.requestLoadSave() || (turn=load(new File("save.txt").toPath())) == -1) {
            log.info("Create empty grid and request player names...");

            if (gameInterface.requestPlayers()) {
                player1 = new LivingPlayer(gameGrid, Token.Red, gameInterface.requestPlayerName("Player 1"));
                player2 = new LivingPlayer(gameGrid, Token.Yellow, gameInterface.requestPlayerName("Player 2"));
            } else {
                player1 = new LivingPlayer(gameGrid, Token.Red, gameInterface.requestPlayerName("Player 1"));
                player2 = new BotPlayer(gameGrid, Token.Yellow);
            }
        }

        return gameLoop(turn);
    }

    /**
     * The game loop
     * @return exit status
     */
    private int gameLoop(int turn) {
        Player[] players = {player1, player2};

        do {
            ui().updateGrid();

            Box box = players[turn].playTurn(ui());
            if (box == null) {
                if (save(new File("save.txt").toPath())) {
                    log.info("Grid state has been saved.");
                } else {
                    log.warning("Unable to save the grid !");
                }
                return 0;
            } else if (box.checkAlignment()) {
                ui().updateGrid();
                ui().displayWinner(players[turn]);
                return 0;
            } else if (gameGrid.isFull()) {
                out.println("The grid is full, TIE GAME!");
                return 0;
            }
            turn = (turn+1)%2;
        } while (true);

        // return -1; // an error occurred if this point is reached
    }
}
