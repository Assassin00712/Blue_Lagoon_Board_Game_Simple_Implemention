package comp1110.ass2.gui;

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

    @Override
    public String toString() {
        return  row + " " + col;
    }

}
