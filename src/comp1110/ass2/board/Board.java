package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

public class Board {

    public static int BOARD_HEIGHT;

    static Spot[][] board = new Spot[BOARD_HEIGHT][BOARD_HEIGHT];

    // initialize a board by creating an array/list of spots with every coordinates within bound
    public static void initialize(){
        int col = 0;
        int row = 0;
        Coordinate cor = new Coordinate(0,0);
        while (row < BOARD_HEIGHT){
            cor.setRow(row);
            while (col < BOARD_HEIGHT) {
                cor.setCol(col);
                board[row][col].setsCor(cor);
                col++;
            }
            col = 0;
            row++;
        }
    }
    public void spotsFromString(String[] gameStates){
        for (int i = 0; i< gameStates.length; i++){
            String[] coordinates = gameStates[i].split(" ");
            for (int j = 1; j < coordinates.length;j++){
                if (coordinates[0].equals("i")){
                    Coordinate c = corFromString(coordinates[j]);
                    board[c.getRow()][c.getCol()].setIsland(true);
                }
                if (coordinates[0].equals("s")){
                    Coordinate c = corFromString(coordinates[j]);
                    board[c.getRow()][c.getCol()].setIsland(true);
                }if (coordinates[0].equals("r")){
                    Coordinate c = corFromString(coordinates[j]);
                    board[c.getRow()][c.getCol()].setIsland(true);
                }
            }
        }

    }


}
