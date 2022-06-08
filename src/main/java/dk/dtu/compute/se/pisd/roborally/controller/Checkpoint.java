package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Checkpoint extends FieldAction {

    private int value;

    public int getValue() {
        return value;
    }



    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
// TODO Create method - Jacob
        // If player prog = value - 1 set player prog to value
        return true;

    }

}
