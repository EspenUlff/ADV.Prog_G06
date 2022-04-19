package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

public class AppController {

    RoboRally roboRally;

    public AppController(RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    // TODO most methods missing here! - low priority
    // isGameRunning
    // newGame
    // stopGame
    // saveGame
    // loadGame

    public void exit() {
        System.exit(0);
    }


}
