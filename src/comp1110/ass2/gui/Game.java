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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static int MAP_WIDTH = 563;
    private static int MAP_HEIGHT = 500;


    private final Group controls = new Group();
    private final Group disposableDrawing = new Group();

    // This is to indicate what the player can currently do.
    private Label stateText;
    private Boolean isGameStart = false;

    // Groups of the player's manipulable objects.
    private Group p0S;
    private Group p0T;
    private Group p1S;
    private Group p1T;
    // Player's remaining settlers and villages count
    private int p0SCount;
    private int p0TCount;
    private int p1SCount;
    private int p1TCount;
    private Text p0SText = new Text("x" + p0SCount);
    private Text p0TText = new Text("x" + p0TCount);
    private Text p1SText = new Text("x" + p1SCount);
    private Text p1TText = new Text("x" + p1TCount);

    // Player's score
    private Label player0ScoreText = new Label();
    private Label player1ScoreText = new Label();
    private int player0Score = 0;
    private int player1Score = 0;

    private Label phaseLabel = new Label();

    // The stateString
    private String stateString;


    public List<Hexagon> hexagons = new ArrayList<>();
    private Group hexagonsGroup = new Group();
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
    public Group drawTriangleText(double x, double y, String text, Paint paint){
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

        Group group = new Group(triangle, textString);


        return group;
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

        if (row % 2 == 0){
            col = (int) ((x / 25) / Math.sqrt(3) -0.8);

        } else {
            col = (int) ((x / 25) / Math.sqrt(3) -0.3);
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


        disposableDrawing.getChildren().clear();
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
                Group tmpBamboo = drawTriangleText(x, y,"B", Color.YELLOW);
                disposableDrawing.getChildren().add(tmpBamboo);
            }
        }

        // Draw Coconut
        if (BlueLagoon.getCoconutList(stateString).size() > 0){
            for (Object cord: BlueLagoon.getCoconutList(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                Group tmpCoconut = drawTriangleText(x, y,"C", Color.WHITESMOKE);
                disposableDrawing.getChildren().add(tmpCoconut);
            }
        }

        // Draw Water
        if (BlueLagoon.getWater(stateString).size() > 0){
            for (Object cord: BlueLagoon.getWater(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                Group tmpWater = drawTriangleText(x, y,"W", Color.DARKBLUE);
                disposableDrawing.getChildren().add(tmpWater);
            }
        }

        // Draw Precious Stone
        if (BlueLagoon.getPreciousStone(stateString).size() > 0){
            for (Object cord: BlueLagoon.getPreciousStone(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                Group tmpPreciousStone = drawTriangleText(x, y,"P", Color.LAWNGREEN);
                disposableDrawing.getChildren().add(tmpPreciousStone);
            }
        }

        // Draw statuette
        if (BlueLagoon.getStatuette(stateString).size() > 0){
            for (Object cord: BlueLagoon.getStatuette(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord));
                double x = draw[0];
                double y = draw[1];
                Group tmpStatuette = drawTriangleText(x, y,"S", Color.BROWN);
                disposableDrawing.getChildren().add(tmpStatuette);
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
                    Group tmpS = new Group();
                    if (playerNumber == 0) {
                        tmpS = drawDragCircleText(x, y, "P0S" , Color.PURPLE);
                    } else if (playerNumber == 1) {
                        tmpS = drawDragCircleText(x, y, "P1S" , Color.RED);
                    }
                    setDragOff(tmpS);
                    disposableDrawing.getChildren().addAll(tmpS);
                }

                // If playerState==0, draw a village
                if (c.matches("\\d+,\\d+") && playerState == 1){
                    double[] draw = cordToXY(new Coordinate((String) c));
                    double x = draw[0];
                    double y = draw[1];
                    Group tmpT = new Group();
                    if (playerNumber == 0) {
                        tmpT = drawDragCircleText(x, y, "P0T" , Color.PURPLE);
                    } else if (playerNumber == 1) {
                        tmpT = drawDragCircleText(x, y, "P1T" , Color.RED);
                    }
                    setDragOff(tmpT);
                    disposableDrawing.getChildren().addAll(tmpT);
                }

            }
        }




    }


    /**
     * Create a basic selection function to start the game.
     */
    private void makeControls() {
        controls.getChildren().clear();
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

        // Set moves when click on every button
        defaultButton.setOnAction(e -> {
            drawMap(13);
            stateString = BlueLagoon.DEFAULT_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        wheelsButton.setOnAction(e -> {
            drawMap(13);
            stateString = BlueLagoon.WHEELS_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        faceButton.setOnAction(e -> {
            drawMap(13);
            stateString = BlueLagoon.FACE_GAME;
            displayState(stateString);
            startButton.setVisible(true);

        });
        sidesButton.setOnAction(e -> {
            drawMap(7);
            stateString = BlueLagoon.SIDES_GAME;
            displayState(stateString);
            startButton.setVisible(true);
        });
        spaceButton.setOnAction(e -> {
            drawMap(23);
            MAP_WIDTH = 1000;
            MAP_HEIGHT = 870;
            stateString = BlueLagoon.SPACE_INVADERS_GAME;
            displayState(stateString);
            startButton.setVisible(true);
            controls.setLayoutY(200);
        });


        // Start the game when click on game start
        startButton.setOnAction(e -> {
            isGameStart = true;
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
        hb.setLayoutY(WINDOW_HEIGHT - 20);
        controls.getChildren().add(hb);
    }

    /**
     * Draw the map according to the size.
     */
    private void drawMap(int i){
        this.hexagons.clear();
        this.hexagonsGroup.getChildren().clear();
        double distX = 25; // the side length of the hexagon
        // Draw i rows of hexagons
        for (int row = 0; row < i; row++){
            // Draw i-1(or i) columns of hexagons
            for (int col = 0; col < i; col++){
                // Every 2 column the hexagon needs to be only 12
                if (row % 2 == 0){
                    if (col == i-1){break;}
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
            hexagonsGroup.getChildren().add(fillHexagon);
        }

    }

    /**
     * Draw the player's settler and village as a manipulable object
     */
    private void drawPlayer(){

        p0S = drawDragCircleText(1000,400,"P0S",Color.PURPLE);
        p0T = drawDragCircleText(1080,400,"P0T",Color.PURPLE);
        p1S = drawDragCircleText(1000,460,"P1S",Color.RED);
        p1T = drawDragCircleText(1080,460,"P1T",Color.RED);

        // Set remaining count
        p0SText.setLayoutX(1020);
        p0SText.setLayoutY(420);
        p0TText.setLayoutX(1100);
        p0TText.setLayoutY(420);
        p1SText.setLayoutX(1020);
        p1SText.setLayoutY(480);
        p1TText.setLayoutX(1100);
        p1TText.setLayoutY(480);
        root.getChildren().addAll(p0S, p0T, p1S, p1T);
        root.getChildren().addAll(p0SText, p0TText, p1SText, p1TText);
    }

    /**
     * Set the stateText that will show the current state and update within the game.
     */
    private void setStateText(String text){
        // Set state text
        stateText = new Label("  " + text + "  ");
        stateText.setTextFill(Color.WHITE);
        stateText.setFont(new Font(40));
        stateText.setStyle("-fx-background-color: black;");
        // To set the text at the exact middle of the circle
        double textWidth = stateText.getBoundsInLocal().getWidth();
        double textHeight = stateText.getBoundsInLocal().getHeight();
        stateText.setLayoutX(980 - textWidth / 2);
        stateText.setLayoutY(200 + textHeight / 2);
        root.getChildren().addAll(stateText);

    }


    /**
     * Set the playerText that will show the player's score.
     */
    private void setScoreText(){
        // Set score text

        player0ScoreText.setText("Player 0's Score: " + player0Score);
        player1ScoreText.setText("Player 1's Score: " + player1Score);

        // Set player 0 Label
        player0ScoreText.setTextFill(Color.BLACK);
        // To set the text at the exact middle of the circle
        double textWidth0 = player0ScoreText.getBoundsInLocal().getWidth();
        double textHeight0 = player0ScoreText.getBoundsInLocal().getHeight();
        player0ScoreText.setLayoutX(1130 - textWidth0 / 2);
        player0ScoreText.setLayoutY(400 + textHeight0 / 2);

        // Set player 1 Label
        player1ScoreText.setTextFill(Color.BLACK);
        // To set the text at the exact middle of the circle
        double textWidth1 = player1ScoreText.getBoundsInLocal().getWidth();
        double textHeight1 = player1ScoreText.getBoundsInLocal().getHeight();
        player1ScoreText.setLayoutX(1130 - textWidth1 / 2);
        player1ScoreText.setLayoutY(460 + textHeight1 / 2);



        phaseLabel.setText("Exploration Phase");
        phaseLabel.setTextFill(Color.BLACK);
        phaseLabel.setFont(new Font(24));
        // To set the text at the exact middle of the circle
        double textWidthP = phaseLabel.getBoundsInLocal().getWidth();
        double textHeightP = phaseLabel.getBoundsInLocal().getHeight();
        phaseLabel.setLayoutX(980 - textWidthP / 2);
        phaseLabel.setLayoutY(300 + textHeightP / 2);

        root.getChildren().addAll(player0ScoreText, player1ScoreText, phaseLabel);


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
        if (isGameStart){
            if (stateString != null && BlueLagoon.isStateStringWellFormed(stateString)){
            String playerTurn = BlueLagoon.getCurrentPlayerNumber(stateString);
            if (playerTurn.equals("0")){
                stateText.setText("Player 0's turn");
                // Set p0S to be Draggable
                setDragOn(p0S);
                // Try to apply the move when mouse released
                p0S.setOnMouseReleased(e -> {
                    Coordinate cord = xYToCord(e.getSceneX(),e.getSceneY());
                    String move = "S " + cord.toString();
                    System.out.println(move);
                    System.out.println(BlueLagoon.isMoveValid(stateString, move));
                    if (BlueLagoon.isMoveValid(stateString, move)){
                        stateString = BlueLagoon.applyMove(stateString, move);
                        update();
                    }
                    p0S.setLayoutX(0);
                    p0S.setLayoutY(0);
                });

                // Set p0T to be Draggable
                setDragOn(p0T);
                // Try to apply the move when mouse released
                p0T.setOnMouseReleased(e -> {
                    Coordinate cord = xYToCord(e.getSceneX(),e.getSceneY());
                    String move = "T " + cord.toString();
                    System.out.println(move);
                    System.out.println(BlueLagoon.isMoveValid(stateString, move));
                    if (BlueLagoon.isMoveValid(stateString, move)){
                        stateString = BlueLagoon.applyMove(stateString, move);
                        update();
                    }
                    p0T.setLayoutX(0);
                    p0T.setLayoutY(0);
                });
                setDragOff(p1S);
                setDragOff(p1T);

            } else if (playerTurn.equals("1")) {
                stateText.setText("Player 1's turn");
                setDragOff(p0S);
                setDragOff(p0T);
                // Set p1S to be Draggable
                setDragOn(p1S);
                // Try to apply the move when mouse released
                p1S.setOnMouseReleased(e -> {
                    Coordinate cord = xYToCord(e.getSceneX(),e.getSceneY());
                    String move = "S " + cord.toString();
                    System.out.println(move);
                    System.out.println(BlueLagoon.isMoveValid(stateString, move));
                    if (BlueLagoon.isMoveValid(stateString, move)){
                        stateString = BlueLagoon.applyMove(stateString, move);
                        if (!BlueLagoon.isPhaseOver(stateString)) {
                            update();
                        }
                    }
                    p1S.setLayoutX(0);
                    p1S.setLayoutY(0);
                });

                // Set p1T to be Draggable
                setDragOn(p1T);
                // Try to apply the move when mouse released
                p1T.setOnMouseReleased(e -> {
                    Coordinate cord = xYToCord(e.getSceneX(),e.getSceneY());
                    String move = "T " + cord.toString();
                    System.out.println(move);
                    System.out.println(BlueLagoon.isMoveValid(stateString, move));
                    if (BlueLagoon.isMoveValid(stateString, move)){
                        stateString = BlueLagoon.applyMove(stateString, move);
                        update();
                    }
                    p1T.setLayoutX(0);
                    p1T.setLayoutY(0);
                });

            }
            p0SCount = BlueLagoon.getUnplacedSettlers(stateString)[0];
            p0TCount = BlueLagoon.getUnplacedVillages(stateString)[0];
            p1SCount = BlueLagoon.getUnplacedSettlers(stateString)[1];
            p1TCount = BlueLagoon.getUnplacedVillages(stateString)[1];
            p0SText.setText("x" + p0SCount);
            p0TText.setText("x" + p0TCount);
            p1SText.setText("x" + p1SCount);
            p1TText.setText("x" + p1TCount);
        }
        }

    }

    /**
     * Check if phase change and calculate score
     */
    private void checkPhaseChange(){
        String currentState = BlueLagoon.getCurrentStateStatement(stateString);

        if (currentState.contains("S") && !BlueLagoon.isPhaseOver(stateString)){
            player0Score = BlueLagoon.calculateScores(stateString)[0];
            player1Score = BlueLagoon.calculateScores(stateString)[1];
            player0ScoreText.setText("Player 0's Score: " + player0Score);
            player1ScoreText.setText("Player 1's Score: " + player1Score);

            // Set phase Label
            phaseLabel.setText("Settlement Phase");

        } else if (currentState.contains("S") && BlueLagoon.isPhaseOver(stateString)) {
            player0ScoreText.setText("Player 0's Score: " + player0Score);
            player1ScoreText.setText("Player 1's Score: " + player1Score);
            System.out.println(stateString);
            stateText.setText("Game Over!");
            isGameStart = false;
        }
    }

    /**
     * Update the game using multiple methods
     */
    private void update(){
        disposableDrawing.toFront();
        if (isGameStart) {
            if (stateString != null && !stateText.getText().equals("Game Over!")) {
                checkPhaseChange();
                takeTurns();
                displayState(stateString);
                disposableDrawing.toFront();
            } else if (stateText.getText().equals("Game Over!")) {
                setDragOff(p0S);
                setDragOff(p0T);
                setDragOff(p1S);
                setDragOff(p1T);
            }
        }

    }

    private void restartGame(){
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(WINDOW_WIDTH - 20);
        restartButton.setLayoutY(20);
        restartButton.setOnAction(e -> {
            isGameStart = false;
            // Reset all variables
            stateString = "";
            player0Score = 0;
            player1Score = 0;
            player0ScoreText.setText("Player 0's Score: " + player0Score);
            player1ScoreText.setText("Player 1's Score: " + player1Score);
            p0SCount = 0;
            p0TCount = 0;
            p1SCount = 0;
            p1TCount = 0;
            stateText.setText("Game Start");
            phaseLabel.setText("Exploration Phase");
            disposableDrawing.getChildren().clear();

            // Show the menu
            setDragOff(p0S);
            setDragOff(p0T);
            setDragOff(p1S);
            setDragOff(p1T);
            makeControls();

            //restartButton.setVisible(false);
        });
        root.getChildren().add(restartButton);
    }


    @Override
    public void start(Stage stage) throws Exception {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.root);
        Scene scene = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);



        root.getChildren().add(controls);
        root.getChildren().addAll(disposableDrawing);
        root.getChildren().add(hexagonsGroup);


        makeControls();
        drawPlayer();
        setStateText("Game Start");
        takeTurns();
        setScoreText();
        restartGame();




        scene.setOnMouseClicked(event -> {
            System.out.println(isGameStart);
            if (isGameStart){
                update();
            }
        });
        root.setOnMouseClicked(event -> {
            if (isGameStart){
                update();
            }
        });



        stage.setScene(scene);
        stage.show();
    }
}
