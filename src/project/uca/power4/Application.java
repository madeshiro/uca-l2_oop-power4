package project.uca.power4;

import project.uca.power4.components.Box;
import project.uca.power4.components.Direction;
import project.uca.power4.components.Grid;
import project.uca.power4.components.Token;
import project.uca.power4.entity.BotPlayer;
import project.uca.power4.entity.LivingPlayer;
import project.uca.power4.entity.Player;
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

    public boolean load(Path path) {
        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String player1 = reader.readLine();
            String player2 = reader.readLine();

            String[] rows = new String[6];
            for (int row = 5; row >= 0; row--) {
                rows[row] = reader.readLine();
            }

            gameGrid = new Grid();
            Box lefty = gameGrid.getLinkedMatrix();
            int _row = 0;
            while (lefty != null) {
                Box row = lefty;
                int col = 0;
                while (row != null) {
                    char c = rows[_row].charAt(col);
                    switch (c) {
                        case 'R':
                            gameGrid.putToken(Token.Red, col+1);
                            break;
                        case 'Y':
                            gameGrid.putToken(Token.Yellow, col+1);
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

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isEnableGui() {
        return enableGui;
    }

    public int exec() {
        if (enableGui) {
            // Do if I got any time left
            log.info("GUI Enable !");
        } else {
            log.info("Playing in mode console !");
        }

        if (gameInterface.requestPlayers()) {

        }

        return 0; // no error
    }

    private void gameLoop() {

    }
}
