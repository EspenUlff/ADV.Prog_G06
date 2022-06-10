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
        // 17-east,27-east,37-east,47-east,57-east,67-north
        Space space_cb17 = new Space(board,1,7);Space space_cb27 = new Space(board,2,7);
        Space space_cb37 = new Space(board,3,7);Space space_cb47 = new Space(board,4,7);
        Space space_cb57 = new Space(board,5,7);Space space_cb67 = new Space(board,6,7);
        // 32-west,42-west,52-west,53-north,54-north
        Space space_cb32 = new Space(board,3,2);Space space_cb42 = new Space(board,4,2);
        Space space_cb52 = new Space(board,5,2);Space space_cb53 = new Space(board,5,3);
        Space space_cb54 = new Space(board,5,4);
        // 01-south,02-south,03-east,13-south,14-east,24-south,25-east
        Space space_cb01 = new Space(board,0,1);Space space_cb02 = new Space(board,0,2);
        Space space_cb03 = new Space(board,0,3);Space space_cb13 = new Space(board,1,3);
        Space space_cb14 = new Space(board,1,4);Space space_cb24 = new Space(board,2,4);
        Space space_cb25 = new Space(board,2,5);


        if(toStringCheck(space,space_cb17)){return Heading.EAST;}
        if(toStringCheck(space,space_cb27)){return Heading.EAST;}
        if(toStringCheck(space,space_cb37)){return Heading.EAST;}
        if(toStringCheck(space,space_cb47)){return Heading.EAST;}
        if(toStringCheck(space,space_cb57)){return Heading.EAST;}
        if(toStringCheck(space,space_cb67)){return Heading.NORTH;}
        //--
        if(toStringCheck(space,space_cb32)){return Heading.WEST;}
        if(toStringCheck(space,space_cb42)){return Heading.WEST;}
        if(toStringCheck(space,space_cb52)){return Heading.WEST;}
        if(toStringCheck(space,space_cb53)){return Heading.NORTH;}
        if(toStringCheck(space,space_cb54)){return Heading.NORTH;}
        //--
        if(toStringCheck(space,space_cb01)){return Heading.SOUTH;}
        if(toStringCheck(space,space_cb02)){return Heading.SOUTH;}
        if(toStringCheck(space,space_cb03)){return Heading.EAST;}
        if(toStringCheck(space,space_cb13)){return Heading.SOUTH;}
        if(toStringCheck(space,space_cb14)){return Heading.EAST;}
        if(toStringCheck(space,space_cb24)){return Heading.SOUTH;}
        if(toStringCheck(space,space_cb25)){return Heading.EAST;}

        return null;
    }

    private String toString(@NotNull Space space) {
        return  space.x+" "+space.y;
    }
    private boolean toStringCheck(@NotNull Space space1, @NotNull Space space2) {

        String space11 = toString(space1);
        String space22 = toString(space2);
        if(space11.equals(space22)) {
            return true;
        } else { return false; }
    }
    /** manually added conveyor belt . end  */

    // TODO This is wrong need to fix it - Jacob
    /** Attempt at manually adding checkpoints  */
    public int checkpoint(@NotNull Space space) {
        Space space_CP1 = new Space(board,6,6);
        Space space_CP2 = new Space(board,1,5);
        Space space_CP3 = new Space(board,5,0);
        Space space_CP4 = new Space(board,4,3);

        if(toStringCheck(space,space_CP1)){
            return 1;
        }
        if(toStringCheck(space,space_CP2)){
            return 2;
        }
        if(toStringCheck(space,space_CP3)){
            return 3;
        }
        if(toStringCheck(space,space_CP4)){
            return 4;
        } else {return 0;}
    }
    /** Attempt at manually adding BlockedWalls */
    public Boolean blockedWalls(@NotNull Player player) {
        Space player_space = player.getSpace();
        Heading player_heading = player.getHeading();

        Space wall_40 = new Space(board, 4, 0); Space wall_30 = new Space(board, 3, 0);
        Space wall_41 = new Space(board, 4, 1); Space wall_31 = new Space(board, 3, 1);
        Space wall_51 = new Space(board, 5, 1);
        Space wall_62 = new Space(board, 6, 2);
        Space wall_63 = new Space(board, 6, 3);
        Space wall_65 = new Space(board, 6, 5); Space wall_55 = new Space(board, 5, 5);
        Space wall_66 = new Space(board, 6, 6); Space wall_56 = new Space(board, 5, 6); // two different headings
        Space wall_46 = new Space(board, 4, 6);
        Space wall_36 = new Space(board, 3, 6);
        Space wall_26 = new Space(board, 2, 6);
        Space wall_16 = new Space(board, 1, 6); Space wall_15 = new Space(board, 1, 5); // two different headings

        if (toStringCheck(player_space, wall_40)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_30)) {
            if (player_heading == Heading.EAST) {return true;}
        }
        if (toStringCheck(player_space, wall_41)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_31)) {
            if (player_heading == Heading.EAST) {return true;}
        }
        if (toStringCheck(player_space, wall_51)) {
            if (player_heading == Heading.SOUTH) {return true;}
        }
        if (toStringCheck(player_space, wall_62)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_63)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_65)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_55)) {
            if (player_heading == Heading.EAST) {return true;}
        }
        if (toStringCheck(player_space, wall_66)) {
            if (player_heading == Heading.WEST) {return true;}
        }
        if (toStringCheck(player_space, wall_56)) {
            if (player_heading == Heading.EAST ) {return true;}
            if (player_heading == Heading.SOUTH) {return true;}
        }
        if (toStringCheck(player_space, wall_46)) {
            if (player_heading == Heading.SOUTH) {return true;}
        }
        if (toStringCheck(player_space, wall_36)) {
            if (player_heading == Heading.SOUTH) { return true;}
        }
        if (toStringCheck(player_space, wall_26)) {
            if (player_heading == Heading.SOUTH) { return true;}
        }
        if (toStringCheck(player_space, wall_16)) {
            if (player_heading == Heading.SOUTH) {return true;}
            if (player_heading == Heading.NORTH) {return true;}
        }
        if (toStringCheck(player_space, wall_15)) {
            if (player_heading == Heading.SOUTH) {return true;}
        }
        return false;
    }

    /** Attempt at manually adding Gear that rotates left */
    public Heading gear(@NotNull Player player) {
        Space player_space = player.getSpace();
        Heading player_heading = player.getHeading();
        Space gear_1 = new Space(board, 4, 5);

        if (toStringCheck(player_space, gear_1)) {
            if (player_heading == Heading.WEST)  {return  player_heading.SOUTH;}
            if (player_heading == Heading.SOUTH) {return  player_heading.EAST;}
            if (player_heading == Heading.EAST)  {return  player_heading.NORTH;}
            if (player_heading == Heading.NORTH) {return  player_heading.WEST;}
        }
        return null;
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
