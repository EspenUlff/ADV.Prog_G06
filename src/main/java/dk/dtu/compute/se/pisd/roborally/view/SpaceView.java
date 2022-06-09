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
import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
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


        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }
    // TODO FIX THIS METHOD CHRIS  - conveyor
    private void updateBelt() {
        // 17-east,27-east,37-east,47-east,57-east,67-north
        // 32-west,42-west,52-west,53-north,54-north
        // 01-south,02-south,03-east,13-south,14-east,24-south,25-east
        if (space.x == 1 && space.y == 7) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 2 && space.y == 7) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 3 && space.y == 7) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 4 && space.y == 7) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 5 && space.y == 7) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 6 && space.y == 7) {
            Polygon fig = Conveyorfigure("NORTH");
            this.getChildren().add(fig);
        }
        //--
        if (space.x == 3 && space.y == 2) {
            Polygon fig = Conveyorfigure("WEST");
            this.getChildren().add(fig);
        }
        if (space.x == 4 && space.y == 2) {
            Polygon fig = Conveyorfigure("WEST");
            this.getChildren().add(fig);
        }
        if (space.x == 5 && space.y == 2) {
            Polygon fig = Conveyorfigure("WEST");
            this.getChildren().add(fig);
        }
        if (space.x == 5 && space.y == 3) {
            Polygon fig = Conveyorfigure("NORTH");
            this.getChildren().add(fig);
        }
        if (space.x == 5 && space.y == 4) {
            Polygon fig = Conveyorfigure("NORTH");
            this.getChildren().add(fig);
        }
        //--
        if (space.x == 0 && space.y == 1) {
            Polygon fig = Conveyorfigure("SOUTH");
            this.getChildren().add(fig);
        }
        if (space.x == 0 && space.y == 2) {
            Polygon fig = Conveyorfigure("SOUTH");
            this.getChildren().add(fig);
        }
        if (space.x == 0 && space.y == 3) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 1 && space.y == 3) {
            Polygon fig = Conveyorfigure("SOUTH");
            this.getChildren().add(fig);
        }
        if (space.x == 1 && space.y == 4) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
        if (space.x == 2 && space.y == 4) {
            Polygon fig = Conveyorfigure("SOUTH");
            this.getChildren().add(fig);
        }
        if (space.x == 2 && space.y == 5) {
            Polygon fig = Conveyorfigure("EAST");
            this.getChildren().add(fig);
        }
    }

    private Polygon Conveyorfigure(String DIRECTION){
        Polygon fig = new Polygon(0.0, 5.0,
                55.0, 5.0,
                30.0, 55.0);
        fig.setFill(Color.YELLOW);
        String north = "NORTH";
        String east = "EAST";
        String south = "SOUTH";
        String west = "WEST";
        if(DIRECTION.equals(north)){
            fig.setRotate(180);
        }
        if(DIRECTION.equals(east)){
            fig.setRotate(270);
        }
        if(DIRECTION.equals(south)){
            fig.setRotate(0);
        }
        if(DIRECTION.equals(west)){
            fig.setRotate(90);
        }
        return fig;
    }

    // makes walls using javaFX and the strokeline method.
    private void UpdateWalls() {
        if (space.x == 4 && space.y == 0) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 4 && space.y == 1) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 5 && space.y == 1) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
            this.getChildren().add(canvas);
        }
        if (space.x == 6 && space.y == 2) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 6 && space.y == 3) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 6 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 6 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //vertical left side line//
            gc.strokeLine(2, SPACE_HEIGHT - 400, SPACE_WIDTH - 58, SPACE_HEIGHT );
            this.getChildren().add(canvas);
        }
        if (space.x == 5 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 4 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 3 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 2 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 1 && space.y == 6) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
            this.getChildren().add(canvas);
        }
        if (space.x == 1 && space.y == 5) {
            Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(6);
            gc.setLineCap(StrokeLineCap.ROUND);
            //horizontal buttom side line//
            gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT-2 );
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
    private void updateCP() {


        if (space.x == 6 && space.y == 6) {
            this.setStyle("-fx-background-color: Orange");
            Label cp1 = new Label("1");
            cp1.setFont(Font.font("Tahoma",40));
            cp1.setTextFill(Color.AQUA);
            this.getChildren().add(cp1);
        }
        if (space.x == 1 && space.y == 5) {
            this.setStyle("-fx-background-color: Orange");
            Label cp2 = new Label("2");
            cp2.setFont(Font.font("Tahoma",40));
            cp2.setTextFill(Color.AQUA);
            this.getChildren().add(cp2);
        }
        if (space.x == 5 && space.y == 0) {
            this.setStyle("-fx-background-color: Orange");
            Label cp3 = new Label("3");
            cp3.setFont(Font.font("Tahoma",40));
            cp3.setTextFill(Color.AQUA);
            this.getChildren().add(cp3);
        }
        if (space.x == 4 && space.y == 3) {
            this.setStyle("-fx-background-color: Orange");
            Label cp4 = new Label("4");
            cp4.setFont(Font.font("Tahoma",40));
            cp4.setTextFill(Color.AQUA);
            this.getChildren().add(cp4);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().clear();
            updatePlayer();
        }
        UpdateWalls();
        updateBelt();
        updateCP();

    }


}