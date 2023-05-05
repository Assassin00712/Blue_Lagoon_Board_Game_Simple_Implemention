package comp1110.ass2.board;

import gittest.C;

import java.util.Objects;

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

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public int stringToRow(){
        return Integer.parseInt(cord[0]);
    }
    public int stringToCol(){
        return Integer.parseInt(cord[1]);
    }


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
        int x1 = cor1.col;
        int x = cor2.col;
        int y1 = cor1.row;
        int y = cor2.row;
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
        Coordinate cor1 = new Coordinate(0,5);
        Coordinate cor2 = new Coordinate(3,7);
        Coordinate cor3 = new Coordinate(7,11);
        Coordinate cor4 = new Coordinate(6,10);
        Coordinate cor5 = new Coordinate(6,11);
        Coordinate cor6 = new Coordinate("6,12");
        System.out.println(cor6.row);


    }

}
