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
package dk.dtu.compute.se.pisd.roborally.controller;


import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

import static java.lang.System.exit;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController  {

    final public Board board;

    public GameController(Board board) {
        this.board = board;
    }

    // TODO lot of stuff missing here
    // moveCurrentPlayerToSpace (BoardView)
    // finish/execute/step button actions (PlayerView)
    // moveCards (CardFieldView)


/** moveCurrentPlayerToSpace Added -chvi */
//moves the current player and switches to the next player after this move.
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        Player player = board.getCurrentPlayer();
        if (space.getPlayer() == null) {
            player.setSpace(space);
            board.NEXTPLAYER();
        }
    }

    /** -------------------------------- */
    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();

        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;

                    /** interactive card */

                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }

                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case TRIPLE_FORWARD:
                    this.moveForward(player);
                    this.fastForward(player);
                    break;
                case U_TURN:
                    this.turnaround(player);
                    break;
                case BACKUP:
                    this.turnaround(player);
                    this.moveForward(player);
                    this.turnaround(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /** card option */
    public void cardOption(Command option) {
        Player currentPlayer = board.getCurrentPlayer();

        if (board.getPhase() == Phase.PLAYER_INTERACTION && currentPlayer != null) {
            int step = board.getStep();

            if (step >= 0 && step < Player.NO_REGISTERS) {
                executeCommand(currentPlayer, option);
                board.setPhase(Phase.ACTIVATION);
            }

            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            } else {
                step++;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }
    }/** card options slut. */

    // TODO: V2
    public void moveForward(@NotNull Player player) {
        Space space = player.getSpace();

        if (player != null && player.board == board && space != null) {
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(space, heading);

            if (target != null) {
                // XXX note that this removes an other player from the space, when there
                //     is another player on the target. Eventually, this needs to be
                //     implemented in a way so that other players are pushed away!
                // -- this should be fixed now by pushing the player towards the space they are 'heading'

                if(target.getPlayer() != null){
                    Player targetplayer = target.getPlayer();
                    moveForward(targetplayer);
                }
                target.setPlayer(player);


                // dont know what happens with obstacles
                while(checkConveyerbelt(player.getSpace())) { //checks if there is a conveyorbelt
                    conveyor(player, player.getSpace());


                }
            }
        }
    }
    /** manually added conveyor belt  */
    public boolean checkConveyerbelt(@NotNull Space space){
        Space space_23 = new Space(board,2,3);
        Space space_24 = new Space(board,2,4);
        Space space_25 = new Space(board,2,5);
        Space space_26 = new Space(board,2,6);

        if(toStringcheck(space,space_23)){
            return true;
        }
        if(toStringcheck(space,space_24)){
            return true;
        }
        if(toStringcheck(space,space_25)){
            return true;
        }
        if(toStringcheck(space,space_26)){
            return true;
        }
        return false;
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

    public void conveyor (@NotNull Player player, @NotNull Space space){
        Space player_space = player.getSpace();
        if(checkConveyerbelt(player_space) == true){
            Heading BELT = space.conveyorBelt(player_space);
            Heading player_heading = player.getHeading();
            player.setHeading(BELT);

            if (player != null && player.board == board && space != null) {
                Heading heading = player.getHeading();
                Space target = board.getNeighbour(space, heading);

                if (target != null) {
                    if (target.getPlayer() != null) {
                        Player targetplayer = target.getPlayer();
                        moveForward(targetplayer);
                    }
                    target.setPlayer(player);       // using forward method here caused problems so, its copied.
                }
                player.setHeading(player_heading); //puts player heading back to were it was
            }
        }
    }
    /** manually added conveyor belt . end  */

    public void turnaround(@NotNull Player player){
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
    }

    // TODO: V2
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    // TODO: V2
    public void turnRight(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
    }

    // TODO: V2
    public void turnLeft(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().prev());
        }
    }

    public boolean checkCheckpoint(@NotNull Space space){
        Space space_66 = new Space(board,6,6);
        Space space_15 = new Space(board,1,5);
        Space space_50 = new Space(board,5,0);
        Space space_43 = new Space(board,4,3);

        if(toStringcheck(space,space_66)){
            return true;
        }
        if(toStringcheck(space,space_15)){
            return true;
        }
        if(toStringcheck(space,space_50)){
            return true;
        }
        if(toStringcheck(space,space_43)){
            return true;
        }
        return false;
    }
    // TODO Not working properly - Jacob
    public void Scoring(@NotNull Player player, @NotNull Space space) {
        Space player_space = player.getSpace();

        if (checkCheckpoint(player_space)) {
            int value = space.checkpoint(player_space);

            if (player.getProgress() == value - 1) {
                player.setProgress(value);
            }
        }
        // TODO Lave en måde at gå tilbage til menuen - Jacob
        if (player.getProgress() == 4) {
            //Something something
        }
    }

    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                // XXX Note that there might be additional problems with
                //     infinite recursion here (in some special cases)!
                //     We will come back to that!
                moveToSpace(other, target, heading);

                // Note that we do NOT embed the above statement in a try catch block, since
                // the thrown exception is supposed to be passed on to the caller

                assert target.getPlayer() == null : target; // make sure target is free now
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
    }



    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    class ImpossibleMoveException extends Exception {

        private Player player;
        private Space space;
        private Heading heading;

        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }

}
