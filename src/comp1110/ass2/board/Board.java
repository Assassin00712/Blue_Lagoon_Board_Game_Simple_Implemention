package comp1110.ass2.board;

import comp1110.ass2.BlueLagoon;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

import static comp1110.ass2.board.Coordinate.corFromString;

public class Board {
    // The width of the board (left to right)
    public final static int BOARD_WIDTH = 13;
    // The height of the board (top to bottom)
    public final static int BOARD_HEIGHT = 13;

    // The matrix of spots representing the board
    // For spotMatrix[x][y]:
    //   x corresponds to the column, working left to right, and
    //   y corresponds to the row, working top to bottom.
    // Values default to null where there are no spots
    public static Spot[][] spotMatrix = new Spot[BOARD_WIDTH][BOARD_HEIGHT];


    // initialize a board by creating an array/list of spots
    public void initialize() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                spotMatrix[x][y] = new Spot();
            }
        }
    }

    public Spot spotFromCor(Coordinate cor) {
        Spot spot = spotMatrix[cor.row][cor.col];
        return spot;
    }



    public static Board fromStateString(String stateString) {
        Board board = new Board();

        // Set the spot isIsland if it's an island
        for (var cord: BlueLagoon.getAllIslandStatementList(stateString)){
            board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                    .isIsland = true;
        }

        // Set the spot isStone if it's a stone
        for (var cord: BlueLagoon.getAllIslandStatementList(stateString)){
            board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                    .isStone = true;
        }

        // Set Bamboo
        if (BlueLagoon.getBamboo(stateString).size() > 0){
            for (var cord: BlueLagoon.getBamboo(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                        .resources = Resources.BAMBOO;
            }
        }

        // Set Coconut
        if (BlueLagoon.getCoconutList(stateString).size() > 0){
            for (var cord: BlueLagoon.getCoconutList(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                        .resources = Resources.COCONUTS;
            }
        }

        // Set Water
        if (BlueLagoon.getWater(stateString).size() > 0){
            for (var cord: BlueLagoon.getWater(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                        .resources = Resources.WATER;
            }
        }

        // Set Precious Stone
        if (BlueLagoon.getPreciousStone(stateString).size() > 0){
            for (var cord: BlueLagoon.getPreciousStone(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                        .resources = Resources.PRECIOUS_STONE;
            }
        }

        // Draw statuette
        if (BlueLagoon.getStatuette(stateString).size() > 0){
            for (var cord: BlueLagoon.getStatuette(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                        .resources = Resources.STATUETTE;
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

                // If playerState==0, set the settler
                if (c.matches("\\d+,\\d+") && playerState == 0){
                    for (var cord: BlueLagoon.getBamboo(stateString)){
                        board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                                .settler = playerNumber;
                    }
                }

                // If playerState==1, set the village
                if (c.matches("\\d+,\\d+") && playerState == 1){
                    for (var cord: BlueLagoon.getBamboo(stateString)){
                        board.spotMatrix[new Coordinate((String) cord).stringToX()][new Coordinate((String) cord).stringToY()]
                                .village = playerNumber;
                    }
                }

            }
        }


    return board;
    }


}


