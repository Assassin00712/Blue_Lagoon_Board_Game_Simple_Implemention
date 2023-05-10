package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.board.Coordinate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private final Group controls = new Group();

    // This is to indicate what the player can currently do.
    private Label stateText;
    private String stateTextForPlayer;

    // Groups of the player's manipulable objects.
    private Group p0S;
    private Group p0T;
    private Group p1S;
    private Group p1T;

    private String stateString;


    public List<Hexagon> hexagons = new ArrayList<>();
    public class Hexagon extends Polygon {
        private double x;
        private double y;
        private double side;


        public ArrayList<Viewer.Hexagon> hexagons = new ArrayList<>();
        public Hexagon(double x, double y, double side) {
            this.x = x;
            this.y = y;
            this.side = side;
            this.getPoints().addAll(
                    0.0, side,
                    side * Math.sqrt(3) / 2, side / 2,
                    side * Math.sqrt(3) / 2, - side / 2,
                    0.0, -side,
                    -side * Math.sqrt(3) / 2, -side / 2,
                    - side * Math.sqrt(3) / 2, side / 2);

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

    public class Triangle extends Polygon{
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
    }

    // Return the exact hexagon by the given coordinate
    // The coordinate is from range (0,0) to (12,11)
    public Hexagon cordToHexagon (Coordinate coordinate){
        for (Hexagon hexagon: hexagons){
            if ((Math.abs(hexagon.getLayoutX() - cordToXY(coordinate)[0]) < 0.1)
                    && (Math.abs(hexagon.getLayoutY() - cordToXY(coordinate)[1]) < 0.1)){
                return hexagon;
            }
        }
        return findNearestHexagon(cordToXY(coordinate)[0],cordToXY(coordinate)[1]);
    }

    // Draw a triangle with text on it
    public void drawTriangleText(double x, double y, String text, Paint paint){
        Triangle triangle = new Triangle(x,y,35);
        triangle.setFill(paint);
        triangle.setStroke(Color.BLACK);
        triangle.setStrokeWidth(0.8);

        Text textString = new Text(text);
        // Set the text color to white
        textString.setFill(Color.WHITE);
        textString.setStroke(Color.BLACK);
        textString.setFont(Font.font("Arial",FontWeight.BOLD,16));
        textString.setStrokeWidth(0.8);
        // To set the text at the exact middle of the circle
        double textWidth = textString.getBoundsInLocal().getWidth();
        double textHeight = textString.getBoundsInLocal().getHeight();
        textString.setX(x - textWidth / 2);
        textString.setY(y + textHeight / 2);


        root.getChildren().addAll(triangle, textString);
    }

    public Group drawDragCircleText(double x, double y, String text, Paint paint){
        Circle circle = new Circle(x,y,20);
        circle.setFill(paint);

        Text textString = new Text(text);
        // To set the text at the exact middle of the circle
        double textWidth = textString.getBoundsInLocal().getWidth();
        double textHeight = textString.getBoundsInLocal().getHeight();
        textString.setX(x - textWidth / 2);
        textString.setY(y + textHeight / 4);
        // Set the text color to white
        textString.setFill(Color.WHITE);

        Group group = new Group(circle, textString);

        final double[] initialOffset = new double[2];
        final double initialLayoutX = group.getLayoutX();
        final double initialLayoutY = group.getLayoutY();
        group.setOnMousePressed(e -> {
            group.toFront();
            initialOffset[0] = e.getSceneX() - group.getLayoutX();
            initialOffset[1] = e.getSceneY() - group.getLayoutY();
        });

        group.setOnMouseDragged(e -> {
            group.setLayoutX(e.getSceneX() - initialOffset[0]);
            group.setLayoutY(e.getSceneY() - initialOffset[1]);
        });

        group.setOnMouseReleased(e -> {
            group.setLayoutX(initialLayoutX);
            group.setLayoutY(initialLayoutY);
        });

        return group;
    }


    public Hexagon findNearestHexagon(double x, double y){
        double nearestDist = Integer.MAX_VALUE;
        Hexagon nearestHexagon = null;
        for (Hexagon hexagon: hexagons){
            if (hexagon.distance(x,y) < nearestDist){
                nearestDist = hexagon.distance(x,y);
                nearestHexagon = hexagon;
            }
        }
        return nearestHexagon;
    }


    // Given the coordinate, will return the absolute position on the viewer stage.
    // Can be used for drawing shapes or placing images.
    public static double[] cordToXY (Coordinate coordinate){
        double outX = 0;
        double outY = 0;

        if (coordinate.row % 2 == 0){
            outX = 25 * Math.sqrt(3) * (coordinate.col + 1);
            outY = 25 * (1.5 * coordinate.row +1);
        } else {
            outX = 25 * Math.sqrt(3) * (coordinate.col + 0.5);
            outY = 25 * (1.5 * coordinate.row +1);}

        double[] outXY = new double[]{outX, outY};
        return outXY;

    }

    // Given the absolute position on the viewer stage, will return the coordinate.
    public Coordinate xYToCord(double x, double y){
        Hexagon hexagon = findNearestHexagon(x,y);
        x = hexagon.getLayoutX();
        y = hexagon.getLayoutY();
        int col = 0;
        int row = 0;
        row = (int) ((y / 25 -1 ) / 1.5);

        if (y % 2 == 0){
            col = (int) ((x / 25) / Math.sqrt(3) -1);

        } else {
            col = (int) ((x / 25) / Math.sqrt(3) -0.5);
        }


        return new Coordinate(row, col);
    }




    /**
     * Given a state string, draw a representation of the state
     * on the screen.
     * <p>
     * This may prove useful for debugging complex states.
     * referred to makeBoard in Game.java in Assignment1
     * @param stateString a string representing a game state
     */
    void displayState(String stateString) {

        /* This part is an example of putting image.


        String cName = "Stone".toLowerCase(); // the asset names are lower-case
        String path = URI_BASE + "stone/" + cName + ".png";

            //NB: if you want to use assets in your own GUI, this is useful code to remember!

        Image image = new Image(Game.class.getResource(path).toString());
        // These two lines of code load images from a provided path - very useful if you're making your own GUI!
        Image appleImage = new Image(Game.class.getResource(path).toString());
        ImageView apple = new ImageView(appleImage);
        Image stoneImage = new Image(Game.class.getResource(path).toString());
        ImageView stone = new ImageView(stoneImage);

        // These two lines set the location of the image in Cartesian
        // coordinates - except y moves from top to bottom, not bottom to top.
        stone.setFitWidth(36);
        stone.setFitHeight(36);
        stone.setLayoutX(cordToXY(0,2)[0]-18);
        stone.setLayoutY(cordToXY(0,2)[1]-18);

            //Add the apple segment to our board Group, and by extension, our root Group.
        root.getChildren().add(stone);

        */


        // Set Hexagon color to green if it's an island
        for (Object cord: BlueLagoon.getAllIslandStatementList(stateString)){
            cordToHexagon(new Coordinate((String) cord))
                    .setFill(Color.GREEN);
        }

        // Set Hexagon color to radial-gradient green-gray if it's a stone
        for (Object cord: BlueLagoon.getAllStoneList(stateString)){
            Stop[] stops = new Stop[] { new Stop(0, Color.GREEN), new Stop(1, Color.GRAY)};
            cordToHexagon(new Coordinate((String) cord))
                    .setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops));

        }

        // Draw Bamboo
        if (BlueLagoon.getBamboo(stateString).size() > 0){
            for (Object cord: BlueLagoon.getBamboo(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"B", Color.YELLOW);
            }
        }

        // Draw Coconut
        if (BlueLagoon.getCoconutList(stateString).size() > 0){
            for (Object cord: BlueLagoon.getCoconutList(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"C", Color.WHITESMOKE);
            }
        }

        // Draw Water
        if (BlueLagoon.getWater(stateString).size() > 0){
            for (Object cord: BlueLagoon.getWater(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"W", Color.DARKBLUE);
            }
        }

        // Draw Precious Stone
        if (BlueLagoon.getPreciousStone(stateString).size() > 0){
            for (Object cord: BlueLagoon.getPreciousStone(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"P", Color.LAWNGREEN);
            }
        }

        // Draw statuette
        if (BlueLagoon.getStatuette(stateString).size() > 0){
            for (Object cord: BlueLagoon.getStatuette(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"S", Color.BROWN);
            }
        }

        // Draw Players
        if (BlueLagoon.getAllPlayers(stateString).size() > 0){
            int playerNumber = 0;
            int playerState = 0;
            for (int i = 0; i < BlueLagoon.getAllPlayers(stateString).size(); i++){
                String c = (String) BlueLagoon.getAllPlayers(stateString).get(i);
                if (c.equals("p")){playerNumber = Integer.parseInt(BlueLagoon.getAllPlayers(stateString).get(i+1));}
                if (c.equals("S")){playerState = 0;}
                if (c.equals("T")){playerState = 1;}

                // If playerState==0, draw a settler
                if (c.matches("\\d+,\\d+") && playerState == 0){
                    double[] draw = cordToXY(new Coordinate((String) c));
                    double x = draw[0];
                    double y = draw[1];
                    drawTriangleText(x, y,"Se" + playerNumber, Color.DARKCYAN);
                }

                // If playerState==0, draw a village
                if (c.matches("\\d+,\\d+") && playerState == 1){
                    double[] draw = cordToXY(new Coordinate((String) c));
                    double x = draw[0];
                    double y = draw[1];
                    drawTriangleText(x, y,"Vi" + playerNumber, Color.DARKORANGE);
                }

            }
        }




        // FIXME Task 5
    }


    /**
     * Create a basic selection function to start the game.
     */
    private void makeControls() {
        Label gameLabel = new Label("Choose the map of the game:");
        //stateTextField = new TextField();
       // stateTextField.setPrefWidth(200);
        Button defaultButton = new Button("Default GAME");
        Button wheelsButton = new Button("WHEELS GAME");
        Button faceButton = new Button("FACE GAME");
        Button sidesButton = new Button("SIDES GAME");
        Button spaceButton = new Button("SPACE INVADERS");

        Button startButton = new Button("Start Game!");
        startButton.setPrefWidth(150);
        startButton.setPrefHeight(40);
        startButton.setVisible(false);

        defaultButton.setOnAction(e -> {
            drawMap();
            stateString = BlueLagoon.DEFAULT_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        wheelsButton.setOnAction(e -> {
            drawMap();
            stateString = BlueLagoon.WHEELS_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        faceButton.setOnAction(e -> {
            drawMap();
            stateString = BlueLagoon.FACE_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        sidesButton.setOnAction(e -> {
            drawMap();
            stateString = BlueLagoon.SIDES_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        spaceButton.setOnAction(e -> {
            drawMap();
            stateString = BlueLagoon.SPACE_INVADERS_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });


        startButton.setOnAction(e -> {
            defaultButton.setVisible(false);
            defaultButton.setManaged(false);

            wheelsButton.setVisible(false);
            wheelsButton.setManaged(false);

            faceButton.setVisible(false);
            faceButton.setManaged(false);

            sidesButton.setVisible(false);
            sidesButton.setManaged(false);

            spaceButton.setVisible(false);
            spaceButton.setManaged(false);

            gameLabel.setVisible(false);
            gameLabel.setManaged(false);

            startButton.setVisible(false);
            startButton.setManaged(false);

            update();

            if (BlueLagoon.isStateStringWellFormed(stateString)) {
                stateString = stateString.replaceAll("r C B W P S", BlueLagoon.distributeResources(stateString));
                displayState(stateString);
            }
        });



        HBox hb = new HBox();
        hb.getChildren().addAll(gameLabel);
        hb.getChildren().addAll(defaultButton, wheelsButton, faceButton, sidesButton,spaceButton, startButton);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(WINDOW_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    private void drawMap(){
        this.hexagons.clear();
        double distX = 25; // the side length of the hexagon
        // Draw 13 rows of hexagons
        for (int row = 0; row < 13; row++){
            // Draw 12(or 13) columns of hexagons
            for (int col = 0; col < 13; col++){
                // Every 2 column the hexagon needs to be only 12
                if (row % 2 == 0){
                    if (col == 12){break;}
                    Hexagon tempHexagon = new Hexagon(distX * Math.sqrt(3) * (col + 1), distX * (1.5 * row +1), 24);
                    tempHexagon.setFill(Color.LIGHTBLUE);
                    hexagons.add(tempHexagon);
                } else {
                    Hexagon tempHexagon = new Hexagon(distX * Math.sqrt(3) * (col + 0.5), distX * (1.5 * row +1), 24);
                    tempHexagon.setFill(Color.LIGHTBLUE);
                    hexagons.add(tempHexagon);
                }
            }
        }
        // Add all hexagons to the group
        for (Hexagon fillHexagon: hexagons){
            root.getChildren().add(fillHexagon);
        }

    }

    /**
     * Draw the player's settler and village as a manipulable object
     */
    private void drawPlayer(){

        p0S = drawDragCircleText(800,400,"P0S",Color.PURPLE);
        p0T = drawDragCircleText(850,400,"P0T",Color.PURPLE);
        p1S = drawDragCircleText(800,450,"P1S",Color.RED);
        p1T = drawDragCircleText(850,450,"P1T",Color.RED);
        root.getChildren().addAll(p0S, p0T, p1S, p1T);
    }

    /**
     * Set the stateText that will show the current state and update within the game.
     */
    private void setStateText(String text){
        // Set state text
        stateText = new Label("  " + text + "  ");
        stateText.setTextFill(Color.WHITE);
        stateText.setStyle("-fx-background-color: black;");
        // To set the text at the exact middle of the circle
        double textWidth = stateText.getBoundsInLocal().getWidth();
        double textHeight = stateText.getBoundsInLocal().getHeight();
        stateText.setLayoutX(800 - textWidth / 2);
        stateText.setLayoutY(200 + textHeight / 2);
        root.getChildren().addAll(stateText);

    }

    /**
     * Set a group to be draggable
     */
    private void setDragOn(Group group){
        final double[] initialOffset = new double[2];
        final double initialLayoutX = group.getLayoutX();
        final double initialLayoutY = group.getLayoutY();
        group.setOnMousePressed(e -> {
            group.toFront();
            initialOffset[0] = e.getSceneX() - group.getLayoutX();
            initialOffset[1] = e.getSceneY() - group.getLayoutY();
        });

        group.setOnMouseDragged(e -> {
            group.setLayoutX(e.getSceneX() - initialOffset[0]);
            group.setLayoutY(e.getSceneY() - initialOffset[1]);
        });

        group.setOnMouseReleased(e -> {
            group.setLayoutX(initialLayoutX);
            group.setLayoutY(initialLayoutY);
        });
    }

    /**
     * Set a group to be NOT draggable
     */
    private void setDragOff(Group group){
        group.setOnMousePressed(null);
        group.setOnMouseDragged(null);
        group.setOnMouseReleased(null);
    }

    /**
     * Determine whose turn it is now based on stateString.
     */
    private void takeTurns(){
        if (BlueLagoon.isStateStringWellFormed(stateString)){
            String playerTurn = BlueLagoon.getCurrentPlayerNumber(stateString);
            if (playerTurn.equals("0")){
                setDragOn(p0S);
                setDragOn(p0T);
                setDragOff(p1S);
                setDragOff(p1T);
            } else if (playerTurn.equals("1")) {
                if (playerTurn.equals("0")){
                    setDragOff(p0S);
                    setDragOff(p0T);
                    setDragOn(p1S);
                    setDragOn(p1T);
                }
            }
        }

    }

    private void update(){
        if (BlueLagoon.isStateStringWellFormed(stateString)){
            takeTurns();
            displayState(stateString);
        }

    }


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(controls);
        makeControls();
        drawPlayer();
        setStateText("Game Start");
        takeTurns();


        scene.setOnMouseClicked(event -> {
            update();
        });
        root.setOnMouseClicked(event -> {
            update();
        });



        stage.setScene(scene);
        stage.show();
    }
}
