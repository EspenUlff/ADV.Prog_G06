/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {

    private Player player;

    private List<Heading> walls = new ArrayList<>();
    private List<FieldAction> actions = new ArrayList<>();

    public final Board board;

    public final int x;
    public final int y;

    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }
    /** manually added conveyor belt  */
    public Heading conveyorBelt(@NotNull Space space){
        // cb 23=north,cb 24=north,cb 25=north,cb 26=north,
        Space space_cb23 = new Space(board,2,3);
        Space space_cb24 = new Space(board,2,4);
        Space space_cb25 = new Space(board,2,5);
        Space space_cb26 = new Space(board,2,6);

        if(toStringcheck(space,space_cb23)){
            return Heading.NORTH;
        }
        if(toStringcheck(space,space_cb24)){
            return Heading.NORTH;
        }
        if(toStringcheck(space,space_cb25)){
            return Heading.NORTH;
        }
        if(toStringcheck(space,space_cb26)){
            return Heading.NORTH;
        }
        return null;
    }

    private String toString(@NotNull Space space) {
        return  space.x+" "+space.y;
    }
    private boolean toStringcheck(@NotNull Space space1,@NotNull Space space2) {

        String space11 = toString(space1);
        String space22 = toString(space2);
        if(space11.equals(space22)) {
            return true;
        } else { return false; }
    }
    /** manually added conveyor belt . end  */

    // TODO lave metode for CP - Jacob
    public Checkpoint insertCheckpoint(){
        Checkpoint CP = null;

        for (FieldAction action : actions) {
            if (action instanceof Checkpoint && CP == null) {
                CP = (Checkpoint) action;
            }
        }
        return CP;
    }


    public List<Heading> getWalls() {
        return walls;
    }

    public List<FieldAction> getActions() {
        return actions;
    }

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

}
