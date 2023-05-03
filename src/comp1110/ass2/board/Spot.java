package comp1110.ass2.board;


import java.util.Objects;

import static comp1110.ass2.board.Board.board;
import static comp1110.ass2.board.Coordinate.corFromString;

public class Spot {


    // In format of (Coordinate (col row), isIsland, isStone, Resources, occupier)

    public Coordinate sCor;
    public Boolean isIsland;
    public Boolean isStone;
    public Resources resources;
    public int occupier;
    //1101 if the spot is occupied by first settler of player 1
    //2101 if the spot is occupied by first settler of player 2
    //1101 if the spot is occupied by first village of player 1
    //2101 if the spot is occupied by first village of player 2
    // 0 if not occupied
    // must not be greater than number of players


    public Spot(Coordinate sCor) {
        this.sCor = sCor;
    }


    public int getsCorX() {
        return sCor.col;
    }
    public int getsCorY() {
        return sCor.row;
    }


    public static String[] splitStatement (String s){
        String[] gameStates = s.split("; ");
        return gameStates;
    }

    public static String[] splitStates (String s){
        String[] coordinates = s.split(" ");
        return coordinates;
    }

    public boolean isOccupied (){
        if (this.occupier > 0){
            return true;
        }
        return false;
    }





    @Override
    public String toString() {
        return "Spot{" +
                "sCor=" + sCor +
                ", isIsland=" + isIsland +
                ", isStone=" + isStone +
                ", resources=" + resources +
                ", occupier=" + occupier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot = (Spot) o;
        return Objects.equals(sCor, spot.sCor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sCor);
    }
}
