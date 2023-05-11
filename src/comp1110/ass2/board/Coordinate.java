package comp1110.ass2.board;

import gittest.C;


import java.util.*;

import static comp1110.ass2.board.Player.isChained;

public class Coordinate implements Comparable<Coordinate>{

    // I changed Position into Coordinate since the format given in state strings are not a typical position format
    public int row; // Row is from up-down
    public int col; // Column is from left-right


    String[] cord;


    public Coordinate(int row,int col) {
        this.row = row;
        this.col = col;
    }
    public Coordinate(){
        this.row = 0;
        this.col = 0;
    }

    public Coordinate(String cordString){

        this.cord = cordString.replace(';',' ').strip().split(",");
        this.row = Integer.parseInt(this.cord[0]);
        this.col = Integer.parseInt(this.cord[1]);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int stringToRow(){
        return Integer.parseInt(cord[0]);
    }
    public int stringToCol(){
        return Integer.parseInt(cord[1]);
    }

    public void setCol(int col) {
        this.col = col;
    }

//    public int hashCode() {
//        return Objects.hash(row, col);
//    }

    /**
     * test if a coordinate is in bound
     * @param bound board bound detected
     * @param cor cor input
     * @return false if coordinate is out of bound, true otherwise
     */
    public static boolean isCorValid (int bound, Coordinate cor){
        return (bound >= cor.getRow()) && (bound >= cor.getCol());
    }

    /** not fixed yet
     * test if two Coordinates are linked
     * @param cor1 the coordinate a play attempted to occupy
     * @param cor2 the coordinate to compare, taken from the occupied coordinates of the player
     * @return true if coordinates are linked, false otherwise
     * test 6,1 and 7,2
     */
    public static boolean isLinked (Coordinate cor1, Coordinate cor2) {
        int row1 = cor1.row;
        int row2 = cor2.row;
        int col1= cor1.col;
        int col2 = cor2.col;

        if (row1 == row2) {
            return ((col1 == col2 + 1) || (col1 == col2 - 1));
        } else if (row2 % 2 == 0) {
            boolean condition1 = (row2 == row1 - 1) || (row2 == row1 + 1);
            boolean condition2 = (col2 == col1 - 1) || (col2 == col1);
            return (condition1 && condition2);
        } else {
            boolean condition1 = (row2 == row1 - 1) || (row2 == row1 + 1);
            boolean condition2 = (col2 == col1 + 1) || (col2 == col1);
            return (condition1 && condition2);
        }
    }

    /** Return all island coordinates when input a islandstatement **/





    /**
    Return the coordinates set that adjacent the input coordinate **/
    public static Set<Coordinate> adjacentCord(Coordinate cor){
        Set<Coordinate> adjacent = new HashSet<>();
        int x = cor.row;
        int y = cor.col;
        int boardHeight = Board.BOARD_HEIGHT;
        int boardWidth = Board.BOARD_WIDTH;

        //Normal situation
        if (x % 2 == 0) {
            if (x > 0 && x < boardHeight - 1 && y > 0 && y < boardWidth - 2) {
                Coordinate cor1 = new Coordinate(x - 1, y);
                Coordinate cor2 = new Coordinate(x - 1, y + 1);
                Coordinate cor3 = new Coordinate(x, y - 1);
                Coordinate cor4 = new Coordinate(x, y + 1);
                Coordinate cor5 = new Coordinate(x + 1, y);
                Coordinate cor6 = new Coordinate(x + 1, y + 1);
                adjacent.add(cor1);
                adjacent.add(cor2);
                adjacent.add(cor3);
                adjacent.add(cor4);
                adjacent.add(cor5);
                adjacent.add(cor6);
                return adjacent;
            } else if (x == boardHeight - 1) {
                if (y == boardWidth - 2) {
                    Coordinate cor1 = new Coordinate(x - 1, y);
                    Coordinate cor2 = new Coordinate(x - 1, y + 1);
                    Coordinate cor3 = new Coordinate(x, y - 1);
                    adjacent.add(cor1);
                    adjacent.add(cor2);
                    adjacent.add(cor3);
                    return adjacent;
                } else {
                    if (y == 0) {
                        Coordinate cor1 = new Coordinate(x - 1, 0);
                        Coordinate cor2 = new Coordinate(x, 1);
                        Coordinate cor3 = new Coordinate(x - 1, 1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        return adjacent;
                    } else {
                        Coordinate cor1 = new Coordinate(x - 1, y);
                        Coordinate cor2 = new Coordinate(x - 1, y + 1);
                        Coordinate cor3 = new Coordinate(x, y - 1);
                        Coordinate cor4 = new Coordinate(x, y + 1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        adjacent.add(cor4);
                        return adjacent;
                    }
                }
            } else //First row situation
                if (x == 0) {
                    if (y == 0) {
                        Coordinate cor1 = new Coordinate(0, 1);
                        Coordinate cor2 = new Coordinate(1, 0);
                        Coordinate cor3 = new Coordinate(1, 1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        return adjacent;
                    } else if (y == boardWidth - 2) {
                        Coordinate cor1 = new Coordinate(0, y-1);
                        Coordinate cor2 = new Coordinate(1, y);
                        Coordinate cor3 = new Coordinate(1, y+1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        return adjacent;
                    } else {
                        Coordinate cor1 = new Coordinate(0, y + 1);
                        Coordinate cor2 = new Coordinate(0, y - 1);
                        Coordinate cor3 = new Coordinate(1, y+1);
                        Coordinate cor4 = new Coordinate(1, y);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        adjacent.add(cor4);
                        return adjacent;
                    }
                } else {
                    //When in the first column
                    if (y == 0) {
                            Coordinate cor1 = new Coordinate(x-1, y);
                            Coordinate cor2 = new Coordinate(x - 1, y+1);
                            Coordinate cor3 = new Coordinate(x, y+1);
                            Coordinate cor4 = new Coordinate(x+1, y);
                            Coordinate cor5 = new Coordinate(x+1, y+1);
                            adjacent.add(cor1);
                            adjacent.add(cor2);
                            adjacent.add(cor3);
                            adjacent.add(cor4);
                            adjacent.add(cor5);
                            return adjacent;
                    }else if (y==boardWidth-2){
                        Coordinate cor1 = new Coordinate(x-1, y);
                        Coordinate cor2 = new Coordinate(x-1, y+1);
                        Coordinate cor3 = new Coordinate(x, y-1);
                        Coordinate cor4 = new Coordinate(x+1, y);
                        Coordinate cor5 = new Coordinate(x+1, y+1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        adjacent.add(cor4);
                        adjacent.add(cor5);
                        return adjacent;
                    }
                }
            //odd row
        }else if (x % 2 == 1) {
            if (x > 0 && x < boardHeight-1 && y > 0 && y < boardWidth - 1) {
                Coordinate cor1 = new Coordinate(x - 1, y - 1);
                Coordinate cor2 = new Coordinate(x - 1, y);
                Coordinate cor3 = new Coordinate(x, y - 1);
                Coordinate cor4 = new Coordinate(x, y + 1);
                Coordinate cor5 = new Coordinate(x + 1, y - 1);
                Coordinate cor6 = new Coordinate(x + 1, y);
                adjacent.add(cor1);
                adjacent.add(cor2);
                adjacent.add(cor3);
                adjacent.add(cor4);
                adjacent.add(cor5);
                adjacent.add(cor6);
                return adjacent;
            }else if (y==boardWidth-1){
                if (x==boardHeight-1){
                    Coordinate cor1 = new Coordinate(x,y-1);
                    Coordinate cor2 = new Coordinate(x-1,y-1);
                    adjacent.add(cor1);
                    adjacent.add(cor2);
                    return adjacent;
                }else{
                    Coordinate cor1 = new Coordinate(x-1,y-1);
                    Coordinate cor2 = new Coordinate(x,y-1);
                    Coordinate cor3 = new Coordinate(x+1,y-1);
                    adjacent.add(cor1);
                    adjacent.add(cor2);
                    adjacent.add(cor3);
                    return adjacent;
                }
            }else{
                //When in the first column
                if (y==0){
                    if (x==boardHeight-1){
                        //Because we set boardheight=13
                        Coordinate cor1 = new Coordinate(x,1);
                        Coordinate cor2 = new Coordinate(x-1,0);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        return adjacent;
                    }else{
                        Coordinate cor1 = new Coordinate(x,1);
                        Coordinate cor2 = new Coordinate(x-1,0);
                        Coordinate cor3 = new Coordinate(x+1,0);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        return adjacent;
                    }
                }
            }
                 if (x==boardHeight-1){
                    Coordinate cor1 = new Coordinate(x,y-1);
                    Coordinate cor2 = new Coordinate(x,y+1);
                    Coordinate cor3 = new Coordinate(x-1,y-1);
                    Coordinate cor4 = new Coordinate(x-1,y);
                    adjacent.add(cor1);
                    adjacent.add(cor2);
                    adjacent.add(cor3);
                    adjacent.add(cor4);
                    return adjacent;
                }

        }

        //First row situation
        if (x==0){
            if (y==0){
            Coordinate cor1 = new Coordinate(0,1);
            Coordinate cor2 = new Coordinate(1,0);
            Coordinate cor3 = new Coordinate(1,1);
            adjacent.add(cor1);
            adjacent.add(cor2);
            adjacent.add(cor3);
            return adjacent;
            }else if (y==boardWidth-1){
                Coordinate cor1 = new Coordinate(0,boardWidth-1);
                Coordinate cor2 = new Coordinate(1,boardWidth);
                Coordinate cor3 = new Coordinate(1,boardWidth+1);
                adjacent.add(cor1);
                adjacent.add(cor2);
                adjacent.add(cor3);
                return adjacent;
            }else{
                Coordinate cor1 = new Coordinate(0,y+1);
                Coordinate cor2 = new Coordinate(0,y-1);
                Coordinate cor3 = new Coordinate(1,boardWidth);
                Coordinate cor4 = new Coordinate(1,boardWidth+1);
                adjacent.add(cor1);
                adjacent.add(cor2);
                adjacent.add(cor3);
                adjacent.add(cor4);
                return adjacent;
            }
        }else {
            //When in the first column
            if (y==0){
                if (x==boardHeight-1){
                    //Because we set boardheight=13
                    if (x % 2 == 0){
                        Coordinate cor1 = new Coordinate(x-1,0);
                        Coordinate cor2 = new Coordinate(x,1);
                        Coordinate cor3 = new Coordinate(x-1,1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);}else {
                        Coordinate cor1 = new Coordinate(x,1);
                        Coordinate cor2 = new Coordinate(x-1,0);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        return adjacent;
                    }
                }else{
                    if (x % 2==0){
                        Coordinate cor1 = new Coordinate(x,1);
                        Coordinate cor2 = new Coordinate(x-1,0);
                        Coordinate cor3 = new Coordinate(x-1,1);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        adjacent.add(cor3);
                        return adjacent;
                    }else{
                        Coordinate cor1 = new Coordinate(x,1);
                        Coordinate cor2 = new Coordinate(x-1,0);
                        adjacent.add(cor1);
                        adjacent.add(cor2);
                        return adjacent;
                    }
                }
            }
        }
        if (y == boardWidth - 1) {
            Coordinate cor1 = new Coordinate(x - 1, y - 1);
            Coordinate cor2 = new Coordinate(x, y - 1);
            Coordinate cor3 = new Coordinate(x + 1, y - 1);
            adjacent.add(cor1);
            adjacent.add(cor2);
            adjacent.add(cor3);
            return adjacent;
        }
        return adjacent;
    }




    @Override
    public String toString() {
        return  row + "," + col;
    }

    /**
     * get the coordinate from a string
    @param s a string represent a coordinate, must be with length 3
     */

    public static Coordinate corFromString(String s) {
        Coordinate c = new Coordinate();
        if (s.charAt(s.length()-1) == ';'){
            s = s.substring(0, s.length()-1);
        }
        String[] tmp = s.split(",");

        int row = Integer.parseInt(tmp[0].strip());
        //System.out.println("the row of the Coordinate dealing with is " + row);
        int col = Integer.parseInt(tmp[1].strip());
        //System.out.println("the column of the Coordinate dealing with is " + col);
        c.row = row;
        c.col = col;
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Coordinate) &&
                row == ((Coordinate) other).row &&
                col == ((Coordinate) other).col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this.row == o.row) {
            return this.col - o.col; // If row is equal, sort by col in ascending order
        } else {
            return this.row - o.row; // If row is not equal, sort by row in ascending order
        }
    }


    public static Coordinate randomCord(){
        int x = (int) (Math.random() * 12);
        int y = (int) (Math.random() * 12);

        return new Coordinate(x, y);
    }

    public static void main(String[] args) {
        Coordinate cor1 = new Coordinate(9,5);
        Coordinate cor2 = new Coordinate(7,3);
        Coordinate cor3 = new Coordinate(8,5);
        Coordinate cor4 = new Coordinate(8,2);
        Coordinate cor5 = new Coordinate(6,11);
        Coordinate cor6 = new Coordinate("6,12");
        System.out.println(isLinked(cor1,cor3));
        System.out.println(isLinked(cor2,cor3));


    }

}
