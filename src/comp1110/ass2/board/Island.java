package comp1110.ass2.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Island {
    /**
     * arrange each island with a distinct number
     */
    int islandNum;
    Coordinate[] islandCor;

    public Island(int islandNum, Coordinate[] islandCor) {
        this.islandNum = islandNum;
        this.islandCor = islandCor;
    }

    public Island() {
        this.islandNum = 0;
        this.islandCor = new Coordinate[0];
    }

    public int getIslandNum() {
        return islandNum;
    }

    public void setIslandNum(int islandNum) {
        this.islandNum = islandNum;
    }

    public Coordinate[] getIslandCor() {
        return islandCor;
    }

    public void setIslandCor(Coordinate[] islandCor) {
        this.islandCor = islandCor;
    }

    /**
     *
     * @param islandString eg.  "i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11"
     * @param number the number arranged to each island
     *               the default nunber is 0 which represents no island
     *               starts with 1
     * @return to an island with arranged number
     */

    public static Island getIslandFromString (String islandString, int number) {
        Island island = new Island();
        island.setIslandNum(number);
        String islandCoordinates = islandString.substring(2,islandString.length());
        String[] coordinates = islandCoordinates.split(" ");
        Coordinate[] islandCords = new Coordinate[coordinates.length];
        for (int i = 0; i < islandCords.length; i++){
            islandCords[i] = Coordinate.corFromString(coordinates[i]);
        }
        island.setIslandCor(islandCords);
        return island;
    }

    public static List<Island> getIslands (String gameState){
        String[] gameStates = gameState.split(";");
        List<String> islandStrings = new ArrayList<>();
        for (String state : gameStates){
            if (state.startsWith("i")) islandStrings.add(state);
        }
        List<Island> islands = new ArrayList<>();
        for (int i = 0; i < islandStrings.size(); i++){
            int islandNum = i + 1;
            Island island = getIslandFromString(islandStrings.get(i),islandNum);
            islands.add(island);
        }
        return islands;
    }

    /**
     *  get the island number where a coordinate is in
     * @param cor the Coordinate to be detected
     * @param islands extracted from stateString
     * @return islandNum of the island cor is belong to
     *          0 if cor does not belong to any islands
     */

    public static int getIslandNumber (Coordinate cor, Island[] islands){
        int output = 0;
        for (Island island : islands){
            List<Coordinate> cors = Arrays.asList(island.getIslandCor());
            if (cors.contains(cor)){
                output = island.getIslandNum();
                break;
            }
        }
        return output;
    }

    @Override
    public String toString() {
        return "Island{" +
                "islandNum=" + islandNum +
                ", islandCor=" + Arrays.toString(islandCor) +
                '}';
    }
}
