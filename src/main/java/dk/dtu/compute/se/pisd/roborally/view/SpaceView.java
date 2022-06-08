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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 60; // 60; // 75;
    final public static int SPACE_WIDTH = 60;  // 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);



        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }
        if (space.x == 5 && space.y == 2){
            this.setStyle("-fx-background-color: red;");
            this.space.insertConveyerbelt();
        }
        if (space.x == 6 && space.y == 6){
            this.setStyle("-fx-background-color: yellow");
            // this.space.insertCheckpoint();
        }
        if (space.x == 1 && space.y == 5){
            this.setStyle("-fx-background-color: yellow");
            // this.space.insertCheckpoint();
        }
        if (space.x == 5 && space.y == 0){
            this.setStyle("-fx-background-color: yellow");
            // this.space.insertCheckpoint();
        }
        if (space.x == 4 && space.y == 3){
            this.setStyle("-fx-background-color: yellow");
            // this.space.insertCheckpoint();
        }




        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    // makes walls ! problem with a wall on every row
    private void UpdateWalls() {
        if (space.x == 2 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
            this.getChildren().add(canvas);
        }
        if (space.x == 2 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 3 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 4 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT);
            this.getChildren().add(canvas);
        }
    }

    private void updatePlayer() {
        this.getChildren().clear();

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);
        }
    }

    // TODO FIX THIS METHOD CHRIS  - conveyor
    private void updateBelt(){
        ConveyorBelt belt = space.insertConveyerbelt();
        if (belt != null) {

            Polygon fig = new Polygon(0.0, 0.0,
                    60.0, 0.0,
                    30.0, 60.0);

            fig.setFill(Color.BLUE);

            fig.setRotate((90*belt.getHeading().ordinal())%360);
            this.getChildren().add(fig);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updateBelt();
            updatePlayer();
            UpdateWalls();

        }
    }
}