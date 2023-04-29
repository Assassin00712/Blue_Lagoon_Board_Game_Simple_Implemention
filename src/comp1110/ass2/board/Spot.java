package comp1110.ass2.board;


import static comp1110.ass2.board.Board.board;
import static comp1110.ass2.board.Coordinate.corFromString;

public class Spot {


    // In format of (Coordinate (col row), isIsland, isStone, Resources, occupier)

    Coordinate sCor;
    Boolean isIsland;
    Boolean isStone;
    Resources resources;
    int occupier;
    //1101 if the spot is occupied by first settler of player 1
    //2101 if the spot is occupied by first settler of player 2
    //1101 if the spot is occupied by first village of player 1
    //2101 if the spot is occupied by first village of player 2
    // 0 if not occupied
    // must not be greater than number of players


    public Spot(Coordinate sCor,
                Boolean isIsland,
                Boolean isStone,
                Resources resources,
                int occupier) {
        this.sCor = sCor;
        this.isIsland = isIsland;
        this.isStone = isStone;
        this.resources = resources;
        this.occupier = occupier;
    }


    public Coordinate getsCor() {
        return sCor;
    }

    public void setsCor(Coordinate sCor) {
        this.sCor = sCor;
    }

    public Boolean getIsland() {
        return isIsland;
    }

    public void setIsland(Boolean island) {
        isIsland = island;
    }

    public Boolean getStone() {
        return isStone;
    }

    public void setStone(Boolean stone) {
        isStone = stone;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public int getOccupier() {
        return occupier;
    }

    public void setOccupier(int occupier) {
        this.occupier = occupier;
    }


    public static String[] splitStatement (String s){
        String[] gameStates = s.split("; ");
        return gameStates;
    }

    public static String[] splitStates (String s){
        String[] coordinates = s.split(" ");
        return coordinates;
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
}
