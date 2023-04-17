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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;
    private static final String URI_BASE = "assets/";


    public ArrayList<Hexagon> hexagons = new ArrayList<>();
    public class Hexagon extends Polygon {
        private double x;
        private double y;
        private double side;


        public ArrayList<Hexagon> hexagons = new ArrayList<>();
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
    public Hexagon cordToHexagon (int x, int y){
        for (Hexagon hexagon: hexagons){
            if ((Math.abs(hexagon.getLayoutX() - cordToXY(x,y)[0]) < 0.5)
                && (Math.abs(hexagon.getLayoutY() - cordToXY(x,y)[1]) < 0.5)){
                return hexagon;
            }
        }
        return hexagons.get(0);
    }

    public void drawTriangleText(double x, double y, String text, Paint paint){
        Triangle triangle = new Triangle(x,y,35);
        triangle.setFill(paint);
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setLayoutX(x-4);
        label.setLayoutY(y-4);

        root.getChildren().addAll(triangle, label);
    }


    // Given the coordinate, will return the absolute position on the viewer stage.
    // Can be used for drawing shapes or placing images.
    public double[] cordToXY (int x, int y){
        double outX = 0;
        double outY = 0;

        if (y % 2 == 0){
            outX = 25 * Math.sqrt(3) * (x + 1);
            outY = 25 * (1.5 * y +1);
        } else {
            outX = 25 * Math.sqrt(3) * (x + 0.5);
            outY = 25 * (1.5 * y +1);}

        double[] outXY = new double[]{outX, outY};
        return outXY;

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
            cordToHexagon(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY())
                    .setFill(Color.GREEN);
        }

        // Set Hexagon color to black if it's a stone
        for (Object cord: BlueLagoon.getAllStoneList(stateString)){
            cordToHexagon(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY())
                    .setFill(Color.BLACK);
        }

        // Draw Bamboo
        if (BlueLagoon.getBamboo(stateString).size() > 0){
            for (Object cord: BlueLagoon.getBamboo(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"B", Color.RED);
            }
        }

        // Draw Coconut
        if (BlueLagoon.getCoconutList(stateString).size() > 0){
            for (Object cord: BlueLagoon.getCoconutList(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"C", Color.DARKGREEN);
            }
        }

        // Draw Water
        if (BlueLagoon.getWater(stateString).size() > 0){
            for (Object cord: BlueLagoon.getWater(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"W", Color.DARKBLUE);
            }
        }

        // Draw Precious Stone
        if (BlueLagoon.getPreciousStone(stateString).size() > 0){
            for (Object cord: BlueLagoon.getPreciousStone(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"P", Color.ORANGE);
            }
        }

        // Draw statuette
        if (BlueLagoon.getStatuette(stateString).size() > 0){
            for (Object cord: BlueLagoon.getStatuette(stateString)){
                double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                double x = draw[0];
                double y = draw[1];
                drawTriangleText(x, y,"S", Color.PURPLE);
            }
        }

        // Draw Players
        if (BlueLagoon.getAllPlayers(stateString).size() > 0){
            for (Object cord: BlueLagoon.getAllPlayers(stateString)){
                String c = (String) cord;
                if (c.matches("-?\\d+(\\.\\d+)?")){
                    double[] draw = cordToXY(new Coordinate((String) cord).stringToX(), new Coordinate((String) cord).stringToY());
                    double x = draw[0];
                    double y = draw[1];
                    drawTriangleText(x, y,"Player", Color.YELLOW);
                }
            }
        }




        // FIXME Task 5
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Game State:");
        stateTextField = new TextField();
        stateTextField.setPrefWidth(200);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //System.out.println("refresh clicked");
                displayState(stateTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, stateTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blue Lagoon Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();


        double distX = 25; // the side length of the hexagon
        // Draw 13 rows of hexagons
        for (int row = 0; row < 13; row++){
            // Draw 12(or 13) columns of hexagons
            for (int col = 0; col < 13; col++){
                // Every 2 column the hexagon needs to be only 12
                if (row % 2 == 0){
                    if (col == 12){break;}
                    Hexagon tempHexagon = new Hexagon(distX * Math.sqrt(3) * (col + 1), distX * (1.5 * row +1), 24);
                    tempHexagon.setFill(Color.LIGHTGRAY);
                    hexagons.add(tempHexagon);
                } else {
                    Hexagon tempHexagon = new Hexagon(distX * Math.sqrt(3) * (col + 0.5), distX * (1.5 * row +1), 24);
                    tempHexagon.setFill(Color.LIGHTGRAY);
                    hexagons.add(tempHexagon);
                }
            }
        }

        // Add all 15 triangles to the group
        for (Hexagon fillHexagon: hexagons){
            root.getChildren().add(fillHexagon);
        }


        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
