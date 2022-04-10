package project.uca.power4.ui;

import project.uca.power4.entity.*;

public interface GameInterface {

    public enum Action {
        Quit(0),
        Put1(1),
        Put2(2),
        Put3(3),
        Put4(4),
        Put5(5),
        Put6(6),
        Put7(7);


        private final int column;

        Action(int i) {
            this.column = i;
        }

        public int getColumn() {
            return column;
        }
    }

    boolean requestLoadSave();
    void updateGrid();
    Action waitPlayer(LivingPlayer player);
    void displayWinner(Player player);

    /**
     * Make UI request to user(s) the amount of players:
     * <ul>
     *     <li>1 player: 1 living player VS 1 AI</li>
     *     <li>2 players: living player vs living player</li>
     * </ul>
     * @return True if the user requests 2 living players.
     */
    boolean requestPlayers();

    /**
     *
     * @param prompt
     * @return
     */
    String requestPlayerName(String prompt);
}
