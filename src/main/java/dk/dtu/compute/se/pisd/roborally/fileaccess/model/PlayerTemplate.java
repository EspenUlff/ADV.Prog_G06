package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

public class PlayerTemplate {
    public String name;
    public String color;
    public int x;
    public int y;
    public Heading heading = SOUTH;

    public PlayerTemplate(String name, String color, int x, int y, Heading heading) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }
}

