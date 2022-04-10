package project.uca.power4.ui;

import project.uca.power4.entity.LivingPlayer;
import project.uca.power4.entity.Player;

import javax.swing.*;

public class FrameUI extends JFrame implements GameInterface {

    @Override
    public boolean requestLoadSave() {
        return false;
    }

    @Override
    public void updateGrid() {

    }

    @Override
    public Action waitPlayer(LivingPlayer player) {
        return Action.Quit;
    }

    @Override
    public void displayWinner(Player player) {

    }

    @Override
    public boolean requestPlayers() {
        return false;
    }

    @Override
    public String requestPlayerName(String prompt) {
        return null;
    }
}
