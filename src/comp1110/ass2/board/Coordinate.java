package comp1110.ass2.board;

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
    public static boolean isLinked (Coordinate cor1, Coordinate cor2){
        int row1 = cor1.getRow();
        int row2 = cor2.getRow();
        int col1 = cor1.getCol();
        int col2 = cor2.getCol();
        if (row1 == row2){
            return ((col1 == col2 + 1) || (col1 == col2 - 1));
        }else if ((row1 == row2 - 1) || (row1 == row2 + 1)){
            return (col1 == col2);
        }else if ((col1 == col2 - 1) || (col1 == col2 + 1)){
            return (row1 == row2);}
        return false;
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
        int col = Integer.parseInt(tmp[1].strip());
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
}
