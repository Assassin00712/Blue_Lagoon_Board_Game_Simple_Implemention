package comp1110.ass2.board;

public class Board {

    public static int BOARD_HEIGHT = 12;

    static Spot[][] board = new Spot[BOARD_HEIGHT][BOARD_HEIGHT];

    // initialize a board by creating an array/list of spots with every coordinates within bound
    public static void initialize (){
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





}
