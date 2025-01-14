package comp1110.ass2.board;


import java.util.Objects;


public class Spot {


    // In format of (Coordinate (col row), isIsland, isStone, Resources, occupier)


    // island can be changed to an int
    public Boolean isIsland = false;
    public Boolean isStone = false;
    public Resources resources = null;

    // -1 if not occupied
    public int settler = -1;
    public int village = -1;



    /**
     * @return true if spot is occupied with a player
     */
    public boolean isOccupied() {
        if (this.settler > -1 || this.village > -1) {
            return true;
        }
        return false;
    }

    /**
     * @return occupying player, else null
     */
    public int getOccupier() {
        return Math.max(this.settler , this.village);
    }

    /**
     * Clear / remove any caterpillars occupying spot
     */
    public void unoccupy() {
        this.settler = -1;
        this.village = -1;
    }


    @Override
    public String toString() {
        return "Spot{" +
                " isIsland=" + isIsland +
                ", isStone=" + isStone +
                ", resources=" + resources +
                ", occupier=" + Math.max(this.settler , this.village) +
                '}';
    }

}
