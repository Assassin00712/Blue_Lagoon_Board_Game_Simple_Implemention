package comp1110.ass2.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Island {
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

    @Override
    public String toString() {
        return "Island{" +
                "islandNum=" + islandNum +
                ", islandCor=" + Arrays.toString(islandCor) +
                '}';
    }
}
