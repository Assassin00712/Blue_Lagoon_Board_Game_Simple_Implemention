package comp1110.ass2.board;

import comp1110.ass2.BlueLagoon;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

import static comp1110.ass2.BlueLagoon.getArrangementStatement;
import static comp1110.ass2.BlueLagoon.getBoardHeight;


public class Board{

    public int getBoardHeight(String stateString){
        return BlueLagoon.getBoardHeight(stateString);
    }

    // The width of the board (left to right)
    public static int BOARD_WIDTH = 24;
    // The height of the board (top to bottom)
    public static int BOARD_HEIGHT = BOARD_WIDTH - 1;


    // The matrix of spots representing the board
    // For spotMatrix[row][col]:
    //   row corresponds to the row, working top to bottom, and
    //   col corresponds to the column, working left to right.
    // Values default to null where there are no spots
    public Spot[][] spotMatrix = new Spot[BOARD_WIDTH][BOARD_HEIGHT];


    // initialize a board by creating an array/list of spots
    public void initialize(String gameString) {
        for (int row = 0; row < BlueLagoon.getBoardHeight(gameString); row++) {
            for (int col = 0; col < BlueLagoon.getBoardHeight(gameString); col++) {
                spotMatrix[row][col] = new Spot();
            }
        }
    }

    public Spot spotFromCor(Coordinate cor) {
        Spot spot = spotMatrix[cor.row][cor.col];
        return spot;
    }



    public static Board fromStateString(String stateString) {
        Board board = new Board();
        board.initialize(stateString);

        // Set the spot isIsland if it's an island
        for (var cord: BlueLagoon.getAllIslandStatementList(stateString)){
            board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                    .isIsland = true;
        }

        // Set the spot isStone if it's a stone
        for (var cord: BlueLagoon.getAllStoneList(stateString)){
            board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                    .isStone = true;
        }

        // Set Bamboo
        if (BlueLagoon.getBamboo(stateString).size() > 0){
            for (var cord: BlueLagoon.getBamboo(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                        .resources = Resources.BAMBOO;
            }
        }

        // Set Coconut
        if (BlueLagoon.getCoconutList(stateString).size() > 0){
            for (var cord: BlueLagoon.getCoconutList(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                        .resources = Resources.COCONUTS;
            }
        }

        // Set Water
        if (BlueLagoon.getWater(stateString).size() > 0){
            for (var cord: BlueLagoon.getWater(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                        .resources = Resources.WATER;
            }
        }

        // Set Precious Stone
        if (BlueLagoon.getPreciousStone(stateString).size() > 0){
            for (var cord: BlueLagoon.getPreciousStone(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                        .resources = Resources.PRECIOUS_STONE;
            }
        }

        // Draw statuette
        if (BlueLagoon.getStatuette(stateString).size() > 0){
            for (var cord: BlueLagoon.getStatuette(stateString)){
                board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
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
                        board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                                .settler = playerNumber;
                    }
                }

                // If playerState==1, set the village
                if (c.matches("\\d+,\\d+") && playerState == 1){
                    for (var cord: BlueLagoon.getBamboo(stateString)){
                        board.spotMatrix[new Coordinate((String) cord).stringToRow()][new Coordinate((String) cord).stringToCol()]
                                .village = playerNumber;
                    }
                }

            }
        }


    return board;
    }


}


