package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Checkpoint extends FieldAction {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
    // TODO Create method - Jacob
        Player player = space.getPlayer();
        if (player.getProgress() == value - 1) {
            gameController.Scoring(player,space);
        }
        return true;

    }

}
