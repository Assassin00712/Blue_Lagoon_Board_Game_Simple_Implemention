package comp1110.ass2.board;

public class Board {
    public final static int BOARD_HEIGHT = 13;

    public final static int BOARD_WIDTH = 13;
    // The height of the board (top to bottom)no usages
    final Spot[][] spotMatrix =new Spot[BOARD_HEIGHT][BOARD_WIDTH];


    private Board() {}
        private void initializeResources () {
        }

    }

