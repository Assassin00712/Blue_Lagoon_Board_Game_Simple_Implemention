package comp1110.ass2.board;

import java.util.Objects;

public class Coordinate {

    // I changed Position into Coordinate since the format given in state strings are not a typical position format
    int row;
    int col;


    public Coordinate(int row,int col) {
        this.row = row;
        this.col = col;
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

    public void setCol(int col) {
        this.col = col;
    }

    public int hashCode() {
        return Objects.hash(row, col);
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

    /**
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
        }
        return false;
    }

    @Override
    public String toString() {
        return  row + " " + col;
    }

}
