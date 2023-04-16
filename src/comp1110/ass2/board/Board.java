package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

public class Board {
    public final static int BOARD_HEIGHT = 13;

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

    public Spot spotFromCor(Coordinate cor) {
        Spot spot = board[cor.getRow()][cor.getCol()];
        return spot;
    }

    public void spotFromPlayer(Player player){
        int playId = player.getPlayId();
        Coordinate[] settlers = player.getSettlers();
        Coordinate[] villages = player.getVillages();
        for (int i = 0; i < settlers.length; i++){
            Spot spot = spotFromCor(settlers[i]);
            spot.setOccupier(1000+(playId)*100+i+1);
        }
        for (int j = 0; j < villages.length; j++){
            Spot spot = spotFromCor(settlers[j]);
            spot.setOccupier(2000+(playId)*100+j+1);
        }
    }



    public void spotsFromString(String[] gameStates){
        int j = 1;
        for (int i = 0; i< gameStates.length; i++){
            String[] coordinates = gameStates[i].split(" ");

            while ( j < coordinates.length){
                if (coordinates[0].equals("i")){
                    Coordinate c = corFromString(coordinates[j]);
                    board[c.getRow()][c.getCol()].setIsland(true);
                    j++;
                }
                if (coordinates[0].equals("s")){
                    Coordinate c = corFromString(coordinates[j]);
                    board[c.getRow()][c.getCol()].setStone(true);
                    j++;
                }if (coordinates[0].equals("r")){
                    if (coordinates[0].equals("C")){
                        j++;
                        Coordinate c = corFromString(coordinates[j]);
                        board[c.getRow()][c.getCol()].setResources(Resources.COCONUTS);
                    }if (coordinates[0].equals("W")){
                        j++;
                        Coordinate c = corFromString(coordinates[j]);
                        board[c.getRow()][c.getCol()].setResources(Resources.WATER);
                    }if (coordinates[0].equals("P")){
                        j++;
                        Coordinate c = corFromString(coordinates[j]);
                        board[c.getRow()][c.getCol()].setResources(Resources.PRECIOUS_STONE);
                    }if (coordinates[0].equals("B")){
                        j++;
                        Coordinate c = corFromString(coordinates[j]);
                        board[c.getRow()][c.getCol()].setResources(Resources.BAMBOO);
                    }if (coordinates[0].equals("S")){
                        j++;
                        Coordinate c = corFromString(coordinates[j]);
                        board[c.getRow()][c.getCol()].setResources(Resources.STATUETTS);
                    }
                }
            }
            if (gameStates[i].startsWith("p")){
                Coordinate[] blankC = new Coordinate[0];
                int[] blankI = new int[0];
                Player player = new Player(0,0,blankI,blankC,blankC);
                player.playerFromString(gameStates[i]);
                spotFromPlayer(player);
            }
        }

    }


    private Board() {}
        private void initializeResources () {
        }

    }

