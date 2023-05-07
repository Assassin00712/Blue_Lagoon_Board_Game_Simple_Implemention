package comp1110.ass2.board;

import gittest.C;

import java.util.Objects;

public class Coordinate {

    // I changed Position into Coordinate since the format given in state strings are not a typical position format
    public int row;
    public int col;
    String[] cord;


    public Coordinate(int row,int col) {
        this.row = row;
        this.col = col;
    }
    public Coordinate(){
        this.row = 0;
        this.col = 0;
    }

    public Coordinate(String cord){
        this.cord = cord.split(",");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int stringToX(){
        return Integer.parseInt(cord[0]);
    }

    public int stringToY(){
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
     */
    public static boolean isLinked (Coordinate cor1, Coordinate cor2) {
        int x1 = cor1.getRow();
        int x = cor2.getRow();
        int y1 = cor1.getCol();
        int y = cor2.getCol();
        if (x == x1) {
            return ((y1 == y + 1) || (y1 == y - 1));
        } else if (x % 2 == 0) {
            return ((x == x1 - 1) || (x == x1 + 1)
                    && (y == y1 - 1) || (y == y1));
        } else {
            return ((x == x1 - 1) || (x == x1 + 1)
                    && (y == y1 + 1) || (y == y1));
        }
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
        c.col = col;
        c.row = row;
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


    public static Coordinate randomCord(){
        int x = (int) (Math.random() * 12);
        int y = (int) (Math.random() * 12);

        return new Coordinate(x, y);
    }

    public static void main(String[] args) {
        Coordinate cor1 = new Coordinate(0,5);
        Coordinate cor2 = new Coordinate(3,7);
        Coordinate cor3 = new Coordinate(7,11);
        Coordinate cor4 = new Coordinate(6,10);
        Coordinate cor5 = new Coordinate(6,11);
        Coordinate cor6 = new Coordinate(0,4);
        Coordinate cor7 = new Coordinate(0,8);
        System.out.println(isLinked(cor1,cor2) + " should be false");
        System.out.println(isLinked(cor2,cor3)+ " should be false");
        System.out.println(isLinked(cor3,cor4)+ " should be true");
        System.out.println(isLinked(cor4,cor5)+ " should be true");
        System.out.println(isLinked(cor5,cor1)+ " should be false");
        System.out.println(isLinked(cor6,cor7)+ " should be false");


    }

}
