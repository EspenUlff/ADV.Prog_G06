package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;


public class AppController {

    RoboRally roboRally;

    public AppController(RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    // TODO most methods missing here! - low priority
    public boolean isGameRunning() {
            return true;
    }
    // isGameRunning
    public void newGame() {}
    // newGame
    public boolean stopGame() {
        return false;
    }
    // stopGame
    public void saveGame() {}
    // saveGame
    public void loadGame() {}
    // loadGame

    public void exit() {
        System.exit(0);
    }

}
