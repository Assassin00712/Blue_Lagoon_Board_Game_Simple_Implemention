package gittest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board {package comp1110.lab6;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;

    public class Board extends Application {
        public static void main(String[] args) { launch(args);}

        @Override
        public void start(Stage stage) {
            stage.setTitle("Board");

            // Create the root stack pane
            Group root = new Group();

        /*
        // Create new Polygon
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -86.6,
                100.0, 86.6,
                -100.0, 86.6);

        // set the layout coordinates of the triangle to center it in the window
        triangle.setLayoutX(200.0);
        triangle.setLayoutY(259.5);
        triangle.setFill(Color.LIGHTGRAY);


        Triangle triangle = new Triangle(300.0,259.5,200);
        triangle.setFill(Color.LIGHTGRAY);
        // add the triangle to the root node of the Scene
        root.getChildren().add(triangle);
        */


            // Create an ArrayList to store 15 triangles

            double distX = 200; // the side length of the triangle
            double distY = Math.sqrt(3) * distX /2; // the height of the triangle

            // Draw 3 rows of triangles
            for (int row = 0; row < 3; row++){
                // Draw 5 columns of triangles
                for (int col = 0; col < 5; col++){
                    // Every 2 column the triangle needs to be rotated
                    if (col % 2 == 0){
                        Triangle tempTriangle = new Triangle(distX * (col +1)/2, distY * (2 * row +1)/2, 196);
                        tempTriangle.setFill(Color.LIGHTGRAY);
                        triangles.add(tempTriangle);
                    } else {
                        Triangle tempTriangle = new Triangle(distX * (col +1)/2, distY * (2 * row +1)/2, 196);
                        tempTriangle.setRotate(180);
                        tempTriangle.setFill(Color.LIGHTGRAY);
                        triangles.add(tempTriangle);
                    }
                }
            }

            // Add all 15 triangles to the group
            for (Triangle fillTriangle: triangles){
                root.getChildren().add(fillTriangle);
            }


            // Draggable Triangle
            DraggableTriangle draggableTriangle = new DraggableTriangle(300,260,200, this);
            root.getChildren().add(draggableTriangle);



            // Set scene of stage with size 600x519 and show the stage
            stage.setScene(new Scene(root, 600, 519));
            stage.show();

        }

        public class Triangle extends Polygon {
            private double x;
            private double y;
            private double side;


            public Triangle(double x, double y, double side) {
                this.x = x;
                this.y = y;
                this.side = side;
                this.getPoints().addAll(
                        0.0, -(Math.sqrt(3) * side / 4),
                        side / 2, (Math.sqrt(3) * side / 4),
                        -(side / 2), (Math.sqrt(3) * side / 4));

                // set the layout coordinates of the triangle
                this.setLayoutX(x);
                this.setLayoutY(y);
            }

            private double distance(double x, double y){
                double distX = this.getLayoutX() - x;
                double distY = this.getLayoutY() - y;
                return Math.sqrt(distX * distX + distY * distY);
            }



        }

        public class DraggableTriangle extends Triangle{
            private Board board;
            private double mousex;
            private double mousey;

            public DraggableTriangle(double x, double y, double side, Board board) {
                super(x, y, side);
                this.board = board;
                this.setFill(Color.RED);


                this.mousex = mousex;
                this.mousey = mousey;

                setOnMousePressed(event -> {
                    mousex = event.getSceneX();
                    mousey = event.getSceneY();
                    this.toFront();
                });

                setOnMouseDragged(event -> {
                    double deltaX = event.getSceneX() - mousex;
                    double deltaY = event.getSceneY() - mousey;
                    setLayoutX(getLayoutX() + deltaX);
                    setLayoutY(getLayoutY() + deltaY);
                    mousex = event.getSceneX();
                    mousey = event.getSceneY();
                    board.highlightNearestTriangle(getLayoutX(), getLayoutY());
                });

                setOnMouseReleased(event -> {
                    Triangle nearestTriangle = board.findNearestTriangle(getLayoutX(), getLayoutY());
                    setLayoutX(nearestTriangle.getLayoutX());
                    setLayoutY(nearestTriangle.getLayoutY());
                    setRotate(nearestTriangle.getRotate());
                });

            }
        }

        public Triangle findNearestTriangle(double x, double y){
            double nearestDist = 9999;
            Triangle nearestTriangle = null;
            for (Triangle triangle: triangles){
                if (triangle.distance(x,y) < nearestDist){
                    nearestDist = triangle.distance(x,y);
                    nearestTriangle = triangle;
                }
            }
            return nearestTriangle;
        }

        public ArrayList<Triangle> triangles = new ArrayList<>();
        public Triangle highlighted = null;

        public void highlightNearestTriangle(double x, double y){
            if (highlighted != null){highlighted.setFill(Color.LIGHTGRAY);}
            highlighted = findNearestTriangle(x,y);
            highlighted.setFill(Color.GREEN);
        }



    }

}
