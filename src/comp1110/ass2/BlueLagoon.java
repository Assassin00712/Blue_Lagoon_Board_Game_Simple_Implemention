package comp1110.ass2;

import comp1110.ass2.board.Board;
import comp1110.ass2.board.Coordinate;
import comp1110.ass2.board.Spot;
import comp1110.ass2.board.Coordinate;
import comp1110.ass2.board.Island;
import comp1110.ass2.board.Player;
import comp1110.ass2.gui.Viewer;
import gittest.C;

import java.util.*;

import static comp1110.ass2.board.Island.getIslandScore;
import static comp1110.ass2.board.Player.allSettlersVillages;
import static comp1110.ass2.board.Player.playerFromString;

public class BlueLagoon {
    // The Game Strings for five maps have been created for you.
    // They have only been encoded for two players. However, they are
    // easily extendable to more by adding additional player statements.
    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    // this is a State String created for test of the classes in the board
    // with some resources distributed and some settlers and villages placed by player 1
    //r C 0,0 B 0,5 W 0,9 P 1,4 S 1,8;
    //p 0 0 0 0 0 0 0 S 0,3 0,4 T 0,5;
    public static final String DEFAULT_TEST = "a 13 2;c 0 E;i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1;i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8;i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11;i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12;i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5;i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5;i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8;s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11;p 0 0 0 0 0 0 0 S 0,3 0,4 T 0,5;p 1 0 0 0 0 0 0 S T;";
    public static final String WHEELS_GAME = "a 13 2; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME =  "a 7 2; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME = "a 23 2; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

    public static int numberOfPlayers = 0;
    /** Split the StateString into different parts
     * @param stateString a string representing a game state
     * @return Different statements eg: Game Arrangement Statement and so on
     * **/


    // Return  Game Arrangement Statement
    public static String getArrangementStatement(String stateString){
        String[] s1 = stateString.split(";");
        return s1[0];
    }

    //Return Current State Statement
    public static String getCurrentStateStatement(String stateString){
        String[] s1 = stateString.split(";");
        return s1[1];
    }

    //Return Current State Statement
    public static String getIslandStatement(String stateString){
        String[] s1 = stateString.split(";");
        String islandStatement = "";
        int i = 2;
        int islandLength = 0;
        while (s1[i].charAt(1) == 'i') {
            String a = s1[i];
            islandStatement = islandStatement + a + ";";
            islandLength = islandLength + 1;
            i++;
        }
        return islandStatement;
    }

    //Return all island coordinates as a list to solve task 5
        public static List getAllIslandStatementList(String stateString){
        List<String> AllIslandStatementList = new ArrayList<>();
        String IslandStatement = getIslandStatement(stateString);
        String[] CoordinatesList = IslandStatement.split(" ");
        boolean isPosition = false;
        for (int i =0;i<=CoordinatesList.length - 1;i++){
            if (CoordinatesList[i].length() > 1){
                for (int j = 0;j<= CoordinatesList[i].length() - 1;j++){
                    if (CoordinatesList[i].charAt(j) == ','){
                        isPosition = true;
                    }
                }
            if (isPosition){
                AllIslandStatementList.add(CoordinatesList[i]);
            }
        }
            isPosition = false;
        }
        return AllIslandStatementList;
    }

    //Return how many lands there are
    public static int getIslandLength(String stateString){
        String[] s1 = stateString.split(";");
        String islandStatement = "";
        int i = 2;
        int islandLength = 0;
        while (s1[i].charAt(1) == 'i') {
            islandLength = islandLength + 1;
            i++;
        }
        return islandLength;
    }


    //Return Stones Statement
    public static String getStoneStatement(String stateString){
        String[] s1 = stateString.split(";");
        return s1[getIslandLength(stateString) + 2];
    }

    //Return all stone coordinates as a list to solve task 5
    public static List getAllStoneList(String stateString){
        List<String> AllstoneList = new ArrayList<>();
        String StoneStatement = getStoneStatement(stateString);
        String[] CoordinatesList = StoneStatement.split(" ");
        boolean isPosition = false;
        for (int i =0;i<=CoordinatesList.length - 1;i++){
            if (CoordinatesList[i].length() > 1){
                for (int j = 0;j<= CoordinatesList[i].length() - 1;j++){
                    if (CoordinatesList[i].charAt(j) == ','){
                        isPosition = true;
                    }
                }
                if (isPosition){
                    AllstoneList.add(CoordinatesList[i]);
                }
            }
            isPosition = false;
        }
        return AllstoneList;
    }

    //Return Stones Statement
    public static String getUnclaimedResourcesandStatuettesStatement(String stateString){
        String[] s1 = stateString.split(";");
        return s1[getIslandLength(stateString) + 3];
    }

    //Return all resource names and relative coordinates as a list to solve task 5
    public static List<String> getAllResourcesList(String stateString){
        List<String> AllResourcesList = new ArrayList<>();
        String ResourcesList = getUnclaimedResourcesandStatuettesStatement(stateString);
        String[] CoordinatesList = ResourcesList.split(" ");

        for (int i =0;i<=CoordinatesList.length - 1;i++) {
            if (!CoordinatesList[i].equals("r")){
                AllResourcesList.add(CoordinatesList[i]);
            }
        }
        return AllResourcesList;
    }
    //Return Coconut coordinates
    public static List<String> getCoconutList(String stateString){
        List<String> AllResourcesList = getAllResourcesList(stateString);
        int BPosition = 0;

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("B")){
                BPosition = i;
            }
        }
        return AllResourcesList.subList(2,BPosition);
    }

    //Return Bamboo coordinates
    public static List<String> getBamboo(String stateString){
        List<String> AllResourcesList = getAllResourcesList(stateString);
        int WPosition = 0;
        int BPosition = 0;

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("B")){
                BPosition = i+1;
            }
        }

        for (int i = 0; i<= AllResourcesList.size() - 1; i++){
            if (AllResourcesList.get(i).equals("W")){
                WPosition = i;
            }
        }
        return AllResourcesList.subList(BPosition,WPosition);
    }

    //Return Water coordinates
    public static List<String> getWater(String stateString){
        List<String> AllResourcesList = getAllResourcesList(stateString);
        int WPosition = 0;
        int PPosition = 0;

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("W")){
                WPosition = i+1;
            }
        }

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("P")){
                PPosition = i;
            }
        }
        return AllResourcesList.subList(WPosition,PPosition);
    }

    //Return Precious stone coordinates
    public static List<String> getPreciousStone(String stateString){
        List<String> AllResourcesList = getAllResourcesList(stateString);
        int PPosition = 0;
        int SPosition = 0;

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("P")){
                PPosition = i+1;
            }
        }

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("S")){
                SPosition = i;
            }
        }
        return AllResourcesList.subList(PPosition,SPosition);
    }

    //Return Statuette coordinates
    public static List<String> getStatuette(String stateString){
        List<String> AllResourcesList = getAllResourcesList(stateString);
        int SPosition = 0;

        for (int i = 0;i<= AllResourcesList.size() - 1;i++){
            if (AllResourcesList.get(i).equals("S")){
                SPosition = i+1;
            }
        }
        return AllResourcesList.subList(SPosition, AllResourcesList.size());
    }

    //Return all players' settlers and villages coordinates as a list to solve task 5
    public static List<String> getAllPlayers(String stateString){
        List<String> AllPlayers = new ArrayList<>();
        String PlayersList = getPlayerStatement(stateString);
        String[] CoordinatesList = PlayersList.split(";");

        int pnum = -1;
        String a;
        for (int i =0;i<=CoordinatesList.length - 1;i++) {
            String[] playerSplit = CoordinatesList[i].split(" ");
            boolean isPosition = false;
            int SPosition = 100000;
            for (int j = 0;j<=playerSplit.length - 1;j++){
                if (playerSplit[j].equals("p")){
                    pnum = pnum + 1;
                    a = "p";
                    AllPlayers.add(a);
                    AllPlayers.add(Integer.toString(pnum));
                }
                if (playerSplit[j].equals("S")){
                    SPosition = j;
                    AllPlayers.add(playerSplit[j]);
                }
                if (playerSplit[j].equals("T")){
                    AllPlayers.add(playerSplit[j]);
                }
                for (int m = 0;m <= playerSplit[j].length() - 1;m++){
                    if (playerSplit[j].charAt(m) == ','){
                        isPosition = true;
                    }
                }
                if (playerSplit[j].length() > 1 && j >= SPosition && isPosition){
                    AllPlayers.add(playerSplit[j]);
                }
            }
        }
        return AllPlayers;
        }


    //Return Player Statement
    public static String getPlayerStatement(String stateString){
        String[] s1 = stateString.split(";");
        String[] s2 = Arrays.copyOfRange(s1,getIslandLength(stateString) + 4,s1.length);

        String a = "";
        for (String containers:s2){
            a = a + containers + ";";
        }
        return a;
    }


    /**
     * Check if the string encoding of the game state is well-formed.
     * Note that this does not mean checking that the state is valid
     * (represents a state that players could reach in game play),
     * only that the string representation is syntactically well-formed.
     * <p>
     * A description of the state string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param stateString a string representing a game state
     * @return true if stateString is well-formed and false otherwise
     */
    public static boolean isStateStringWellFormed(String stateString){
        numberOfPlayers = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == 'p') {
                numberOfPlayers++;
            }
        }

        if (stateString == null || stateString.isEmpty()) {return false;}
        String[] gameStates = stateString.split("; ");

        //// Check if gameArrangementStatement is valid ////
        String[] gameArrangementStatement = gameStates[0].split("\\s");
        if (gameArrangementStatement.length != 3) {return false;}
        if (!(gameArrangementStatement [0].equals("a"))){ return false;}
        if (!(gameArrangementStatement [1].matches("-?\\d+(\\.\\d+)?"))){ return false;}
        if (!(gameArrangementStatement [2].matches("-?\\d+(\\.\\d+)?"))){ return false;}

        //// Check if currentStateStatement is valid ////
        String[] currentStateStatement = gameStates[1].split("\\s");
        if (currentStateStatement.length != 3) {return false;}
        if (!(currentStateStatement [0].equals("c"))){ return false;}
        if (!(currentStateStatement [1].matches("-?\\d+(\\.\\d+)?"))){ return false;}
        if (!(currentStateStatement [2].equals("E") | currentStateStatement [2].equals("S"))){ return false;}

        //// Check if island and stone Statements are valid ////
        for (int i = 2; i < gameStates.length -numberOfPlayers -1 ; i++) {
            String[] tempStatement = gameStates[i].split("\\s");
            if (tempStatement [0].equals("i")) {
                if (!(tempStatement[1].matches("-?\\d+(\\.\\d+)?"))) {
                    System.out.println("3" + tempStatement[1]); return false;
                }
                for (int j = 2; j < tempStatement.length; j++) {
                    if (!(tempStatement[j].matches("\\d+,\\d+"))) {
                        System.out.println("f" + tempStatement[j]);
                        return false;
                    }
                }

            } else if(tempStatement [0].equals("s")){
                for (int j = 1; j < tempStatement.length; j++) {
                    if (!(tempStatement[j].matches("\\d+,\\d+"))) {
                        System.out.println("f" + tempStatement[j]);
                        return false;
                    }
                }

            } else return false;
        }


        //// Check if Resources and Statuettes Statements are valid ////
        String[] resourcesStatement = gameStates[gameStates.length -numberOfPlayers -1].split("\\s");
        // "r C" must be the first character
        if (!(resourcesStatement [0].equals("r"))){ return false;}
        if (!(resourcesStatement [1].equals("C"))){ return false;}

        // every character should be valid and not a space
        for (int i = 0; i < resourcesStatement.length; i++){
            if (!(
                    (resourcesStatement[i].equals("r")) ||
                    (resourcesStatement[i].equals("C")) ||
                    (resourcesStatement[i].equals("B")) ||
                    (resourcesStatement[i].equals("W")) ||
                    (resourcesStatement[i].equals("P")) ||
                    (resourcesStatement[i].equals("S")) ||
                    (resourcesStatement[i].matches("\\d+,\\d+"))
                )){
                return false;
            }
        }

        //"r, C, B, W, P, S" should be appeared one time and one time only
        Set<String> elementResource = new HashSet<>();
        for (String element : resourcesStatement) {
            if (elementResource.contains(element)) {
                return false;
            }
            if (!(element.matches("\\d+,\\d+"))){
                elementResource.add(element);
            }
        }
        if (elementResource.size() != 6){return false;}



        //// Check if Player Statements are valid
        for (int i = gameStates.length-numberOfPlayers; i < gameStates.length ; i++) {
            String[] playerStatement = new String[]{};
            if (i == gameStates.length-1 && gameStates[i].charAt(gameStates[i].length()-1) == ';'){
                playerStatement = gameStates[i].substring(0, gameStates[i].length()-1).split("\\s");
            }else if (i != gameStates.length-1){
                playerStatement = gameStates[i].split("\\s");
            }else return false;

            // Player should have "p", score and resources, and then "S" and "T"
            if (!(playerStatement[8].equals("S"))) {return false;}
            for (int j = 0; j < playerStatement.length; j++){
                if (!(
                        (playerStatement[j].equals("p")) ||
                        (playerStatement[j].equals("S")) ||
                        (playerStatement[j].equals("T")) ||
                        (playerStatement[j].matches("\\d+"))||
                        (playerStatement[j].matches("\\d+,\\d+"))
                )){
                    return false;
                }
            }
            //"p S T" should be appeared one time and one time only
            Set<String> elementPlayer = new HashSet<>();
            for (String element : playerStatement) {
                if (elementPlayer.contains(element)) {
                    return false;
                }
                if (!((element.matches("\\d+"))||
                        (element.matches("\\d+,\\d+")))){
                    elementPlayer.add(element);
                }
            }
            if (elementPlayer.size() != 3){return false;}

        }


        return true; // FIXME Task 3

    }

    /**
     * Check if the string encoding of the move is syntactically well-formed.
     * <p>
     * A description of the move string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param moveString a string representing a player's move
     * @return true if moveString is well-formed and false otherwise
     */
    public static boolean isMoveStringWellFormed(String moveString){

        String[] move = moveString.split(" ");
        if (move.length != 2) {return false;}
        if (!(move [0].equals("S") || move [0].equals("T"))){return false;}
        if (!(move [1].matches("\\d+,\\d+"))){return false;}


        return true; // FIXME Task 4
    }

    /**
     * A helper method to take an element away from an array
     */
    public static String[] abandonAt (String[] strings, int index) {
        String[] outPut = new String[strings.length-1];
        if (index == 0){
            System.arraycopy(strings, 1, outPut, 0, strings.length-1);
        }else if (index == strings.length-1){
            System.arraycopy(strings, 0, outPut, 0, index);
        }
        System.arraycopy(strings, 0, outPut, 0, index);
        System.arraycopy(strings, index+1, outPut, index, strings.length-index-1);

        return outPut;

    }

    /** A helper method to combine two Arrays
     * this can help distribute resources.
     * @param letters a String Array with only letters
     * @param coords a String Array with only coordinates
     * @param digits an int Array with the digits where the letters will be inserted in the output
     */
    public static String[] combineAt (String[] letters, String[] coords, int[] digits) {
        String[] outPut = new String[letters.length+ coords.length];
        //outPut[0] = letters[0];
        // j represents digit of digits
        int j = 0;
        // k represents digit of letters, which is same as j as accumulating
        // thus I only implement j
        //int k = 0;
        // l represents digit of coords
        int l = 0;
        int i = 0;
            while (i < outPut.length){
                if (i == digits[j]){
                    outPut[i] = letters[j];
                    if (j < digits.length-1){j++;}
                    i++;
                }else{outPut[i] = coords[l];
                    if (l < coords.length-1){l++;}
                i++;}
            }
        return outPut;
    }

    /**
     * Given a state string which is yet to have resources distributed amongst the stone circles,
     * randomly distribute the resources and statuettes between all the stone circles.
     * <p>
     * There will always be exactly 32 stone circles.
     * <p>
     * The resources and statuettes to be distributed are:
     * - 6 coconuts
     * - 6 bamboo
     * - 6 water
     * - 6 precious stones
     * - 8 statuettes
     * <p>
     * The distribution must be random.
     *
     * @param stateString a string representing a game state without resources distributed
     * @return a string of the game state with resources randomly distributed
     */

    public static String distributeResources(String stateString){
        Random rand = new Random();
        String[] randomStones = new String[32];
        String[] s1 = stateString.split("; ");
        String[] s2 = new String[s1.length];
        for (int i = 0; i < s1.length; i++){
            if (s1[i].startsWith("s")){
                String[] stones = s1[i].split(" ");
                String[] orderedStones;
                orderedStones = Arrays.copyOfRange(stones, 1,(stones.length));
                int digitTaken;
                for (int j = 0; j < 32; j++){
                    if (j < 30){digitTaken = rand.nextInt(0,32-j);
                    randomStones[j] = orderedStones[digitTaken];
                    orderedStones = abandonAt(orderedStones,digitTaken);}
                    else {randomStones[j] = orderedStones[0];
                        orderedStones = abandonAt(orderedStones,0);}
                }
            }
            }
        String[] resources = {"r","C","B","W","P","S"};
        int[] digits = {0,1,8,15,22,29};
        String[] resourcesDistributed = combineAt(resources,randomStones,digits);
        String resourcesFinal = "";


        for (int l = 0; l < resourcesDistributed.length; l++){
            resourcesFinal = String.join(" ",resourcesFinal,resourcesDistributed[l]);
        }
       // System.out.println(resourcesFinal);
         return resourcesFinal.substring(1); // FIXME Task 6
    }

    /**
     * Given a state string and a move string, determine if the move is
     * valid for the current player.
     * <p>
     * For a move to be valid, the player must have enough pieces left to
     * play the move. The following conditions for each phase must also
     * be held.
     * <p>
     * In the Exploration Phase, the move must either be:
     * - A settler placed on any unoccupied sea space
     * - A settler or a village placed on any unoccupied land space
     *   adjacent to one of the player's pieces.
     * <p>
     * In the Settlement Phase, the move must be:
     * - Only a settler placed on an unoccupied space adjacent to
     *   one of the player's pieces.
     * Importantly, players can now only play on the sea if it is
     * adjacent to a piece they already own.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return true if the current player can make the move and false otherwise
     */
    public static boolean isMoveValid(String stateString, String moveString) {


        /** Split stateString into relative parts.
         * Note: every split starts with " "**/

        //Split original statement into a string set
        String[] s1 = stateString.split(";");

        //Split Arrangement Statement and Current State Statement parts
        String gameArrangementStatement = " " + s1[0];
        String currentStateStatement = s1[1];

        //Split island Statement part
        String islandStatement = "";
        int i = 2;
        int islandLength = 0;
        while (s1[i].charAt(1) == 'i') {
            String a = s1[i];
            islandStatement = islandStatement + a;
            islandLength = islandLength + 1;
            i++;
        }

        //Split the stonesStatement and unclaimedResourcesAndStatuettesStatement parts
        String stonesStatement = s1[islandLength + 2];
        String unclaimedResourcesAndStatuettesStatement = s1[islandLength + 3];

        //Split player statement according to the number of players
        String playerStatement = "";
        for (int a = 1; a <= Integer.parseInt(gameArrangementStatement.substring(6, 7)); a++) {
            playerStatement = playerStatement + s1[islandLength + 3 + a] + ";";
        }


        /** Check if move and statement are valid (eg: no -1,-1) **/
        if (!isMoveStringWellFormed(moveString)) {
            return false;
        }


        /** Check if move and statement are out of range because 12 13 12.... **/
        //Get the position of the move
        String movePosition = moveString.substring(2);
        String[] moveList = movePosition.split(",");
        int moveleft = Integer.parseInt(moveList[0]);
        int moveright = Integer.parseInt(moveList[1]);

        //Get the board height of game and compare with move position
        String[] GASsplit = gameArrangementStatement.split(" ");
        int boardHeight = Integer.parseInt(GASsplit[2]);

        if (moveleft < 0 || moveright < 0 || moveright >= boardHeight || moveleft >= boardHeight) {
            return false;
        }
        //Because there are 12 13 12 13 in each row, to control the odd and even lines to make sure move is in map
        if (moveleft % 2 == 0) {
            if (moveright > boardHeight - 2) {
                return false;
            }
        }
        if (moveleft % 2 != 0) {
            if (moveright > boardHeight -1) {
                return false;
            }
        }


        /**Check the number of settlers and villages placed,
         * return false if players don't have enough settlers or villages **/
        //Create a string list to contain all players' settlers and villages
        String[] s2 = playerStatement.split(";");
        //Put settlers and villages of all players in s2
        for (int j = 0; j <= s2.length - 1; j++) {
            for (int a = 0; a <= s2[j].length() - 1; a++) {
                if (s2[j].charAt(a) == 'S') {
                    s2[j] = s2[j].substring(a);
                }
            }
        }
        //Get current player's settlers and villages
        String currentPlayerID = currentStateStatement.substring(3, 4);
        String currentSettlersAndVillages = s2[Integer.parseInt(currentPlayerID)];

        //Get current player's settlers and villages separately
        int TPosition = 0;
        String currentSettlers = "";
        String currentVillages = "";
        for (int a = 0; a <= currentSettlersAndVillages.length() - 1; a++) {
            if (currentSettlersAndVillages.charAt(a) == 'T') {
                TPosition = a;
            }
            currentSettlers = currentSettlersAndVillages.substring(0, TPosition);
            currentVillages = currentSettlersAndVillages.substring(TPosition);
        }
        //When players don't have enough settlers and want to place settlers, we can't make the move
        String[] currentSettlersSplit = currentSettlers.split(" ");
        String[] currentVillagesSplit = currentVillages.split(" ");

        if (moveString.charAt(0) == 'S') {

            if (currentSettlersSplit.length >= 31) {
                return false;
            }
        }
        //When players don't have enough villages and want to place villages, the move can't be made
        if (moveString.charAt(0) == 'T') {

            if (currentVillagesSplit.length >= 6) {
                return false;
            }
        }

        /** Check if the move position is occupied by players **/
        String[] currentSettlerAndVillageSplit = playerStatement.split(" ");



        for (int q = 0; q <= currentSettlerAndVillageSplit.length - 1; q++) {
            for (int a = 0; a<= currentSettlerAndVillageSplit[q].length() - 1;a++){
                if (currentSettlerAndVillageSplit[q].charAt(a) == ';'){
                    currentSettlerAndVillageSplit[q] = currentSettlerAndVillageSplit[q].substring(0,a);
                }
            }
            if (movePosition.equals(currentSettlerAndVillageSplit[q])) {
                return false;
            }
        }


        /** Villages should not be put on the sea **/
        //Compare move position with every position in islandStatement
        movePosition = moveString.substring(2);
        String[] islandStatementList = islandStatement.split(" ");
        boolean isVillagesOntheSea = false;
        if (moveString.charAt(0) == 'T') {
            for (int a = 0; a <= islandStatementList.length - 1; a++) {
                if (islandStatementList[a].equals(movePosition)) {
                    isVillagesOntheSea = true;
                }
            }
            if (!isVillagesOntheSea){
                return false;
            }
        }

        /** When it's in exploration phase **/
        if (currentStateStatement.charAt(5) == 'E') {

        /** Settlers or villages should not be put on the area not adjacent to the settlers or village **/

            //Check if settlers are on land
            boolean SisOnLand = false;
            for (int a = 0; a <= islandStatementList.length - 1; a++) {
                //Check if settler put is on land
                if (islandStatementList[a].equals(movePosition)) {
                    SisOnLand = true;
                }
            }
            //If S is on land, it should be adjacent to the settlers or villages, if it's, return true or return false
            if (SisOnLand) {

                String currentSettlersNoS = currentSettlers.substring(1);
                String[] currentSettlersList = currentSettlersNoS.split(" ");
                String currentVillageNoT = currentVillages.substring(1);
                String[] currentVillageList = currentVillageNoT.split(" ");

//                    //Add two string arrays to one, after this currentSettltersList is the combined array
//                    int arryLen1 = currentSettlersList.length;
//                    int arryLen2 = currentVillageList.length;
//
//                    currentSettlersList = Arrays.copyOf(currentSettlersList, arryLen1 + arryLen2);
//                    System.arraycopy(currentVillageList, 0, currentSettlersList, arryLen1, arryLen2);

                //currentSettlersNoS have an empty in the beginning
                //Check if move position is adjacent to the settlers
                String moveLeftnum = "";
                String moveRightnum = "";
                //Check if the move position is adjacent to villages
                if (currentVillageNoT.length() > 1) {
                    for (int a = 0; a <= movePosition.length() - 1; a++) {
                        if (movePosition.charAt(a) == ',') {
                            moveLeftnum = movePosition.substring(0, a);
                            moveRightnum = movePosition.substring(a + 1);
                        }
                    }
                    for (int q = 0; q <= currentVillageList.length - 1; q++) {
                        if (!currentVillageList[q].equals("") && !currentVillageList[q].equals(" ")) {
                            String leftnum = "";
                            String rightnum = "";
                            for (int a = 0; a <= currentVillageList[q].length() - 1; a++) {
                                if (currentVillageList[q].charAt(a) == ',') {
                                    leftnum = currentVillageList[q].substring(0, a);
                                    rightnum = currentVillageList[q].substring(a + 1);
                                }
                            }
                            //Check 6 situations of adjacent
                            int moveleftI = Integer.parseInt(moveLeftnum);
                            int moveRightI = Integer.parseInt(moveRightnum);
                            int leftI = Integer.parseInt(leftnum);
                            int rightI = Integer.parseInt(rightnum);

                            if (moveleftI % 2 != 0) {

                                if (Math.abs(leftI - moveleftI) == 1 && (rightI == moveRightI - 1
                                        || rightI == moveRightI) || (leftI == moveleftI &&
                                        Math.abs(rightI - moveRightI) == 1)) {
                                    return true;
                                }
                            }

                            if (moveleftI % 2 == 0) {
                                if (leftI == moveleftI - 1 && rightI == moveRightI) {
                                    return true;
                                }
                                if (leftI == moveleftI - 1 && rightI == moveRightI + 1) {
                                    return true;
                                }
                                if (leftI == moveleftI && rightI == moveRightI - 1) {
                                    return true;
                                }
                                if (leftI == moveleftI && rightI == moveRightI + 1) {
                                    return true;
                                }
                                if (leftI == moveleftI + 1 && rightI == moveRightI) {
                                    return true;
                                }
                                if (leftI == moveleftI + 1 && rightI == moveRightI + 1) {
                                    return true;
                                }
                            }
                        }
                    }
                }

                if (currentSettlersNoS.length() > 1) {
                    for (int a = 0; a <= movePosition.length() - 1; a++) {

                        if (movePosition.charAt(a) == ',') {
                            moveLeftnum = movePosition.substring(0, a);
                            moveRightnum = movePosition.substring(a + 1);
                        }
                    }
                    for (int q = 0; q <= currentSettlersList.length - 1; q++) {
                        if (!currentSettlersList[q].equals("") && !currentSettlersList[q].equals(" ")) {
                            String leftnum = "";
                            String rightnum = "";
                            for (int a = 0; a <= currentSettlersList[q].length() - 1; a++) {
                                if (currentSettlersList[q].charAt(a) == ',') {
                                    leftnum = currentSettlersList[q].substring(0, a);
                                    rightnum = currentSettlersList[q].substring(a + 1);
                                }
                            }
                            //Check 6 situations of adjacent
                            int moveleftnumI = Integer.parseInt(moveLeftnum);
                            int moveRightnumI = Integer.parseInt(moveRightnum);
                            int leftnumI = Integer.parseInt(leftnum);
                            int rightnumI = Integer.parseInt(rightnum);

                            if (moveleftnumI % 2 != 0) {
                                if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI - 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {

                                    return true;
                                }
                                if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI - 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                    return true;
                                }
                            }

                            if (moveleftnumI % 2 == 0) {
                                if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI + 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                    return true;
                                }
                                if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI + 1) {
                                    return true;
                                }
                            }
                        }
                    }
                    //If it's the move position is not near the pieces put before
                    return false;
                }
                //Settlers are put on the land but there is no pieces around
                return false;
            }
            //If settlers are put on the ocean, return true
            else {
                return true;
            }
        }

        if (currentStateStatement.charAt(5) == 'S'){
            if(moveString.charAt(0) == 'S'){

                //If S is on land, it should be adjacent to the settlers or villages, if it's, return true or return false

                    String currentSettlersNoS = currentSettlers.substring(1);
                    String[] currentSettlersList = currentSettlersNoS.split(" ");
                    String currentVillageNoT = currentVillages.substring(1);
                    String[] currentVillageList = currentVillageNoT.split(" ");

                    //currentSettlersNoS have an empty in the beginning
                    //Check if move position is adjacent to the settlers
                    String moveLeftnum = "";
                    String moveRightnum = "";
                    //Check if the move position is adjacent to villages
                    if (currentVillageNoT.length() > 1) {
                        for (int a = 0; a <= movePosition.length() - 1; a++) {
                            if (movePosition.charAt(a) == ',') {
                                moveLeftnum = movePosition.substring(0, a);
                                moveRightnum = movePosition.substring(a + 1);
                            }
                        }
                        for (int q = 0; q <= currentVillageList.length - 1; q++) {
                            if (!currentVillageList[q].equals("") && !currentVillageList[q].equals(" ")) {
                                String leftnum = "";
                                String rightnum = "";
                                for (int a = 0; a <= currentVillageList[q].length() - 1; a++) {
                                    if (currentVillageList[q].charAt(a) == ',') {
                                        leftnum = currentVillageList[q].substring(0, a);
                                        rightnum = currentVillageList[q].substring(a + 1);
                                    }
                                }
                                //Check 6 situations of adjacent
                                int moveleftnumI = Integer.parseInt(moveLeftnum);
                                int moveRightnumI = Integer.parseInt(moveRightnum);
                                int leftnumI = Integer.parseInt(leftnum);
                                int rightnumI = Integer.parseInt(rightnum);

                                if (moveleftnumI % 2 != 0) {

                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                }

                                if (moveleftnumI % 2 == 0) {
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }

                    if (currentSettlersNoS.length() > 1) {
                        for (int a = 0; a <= movePosition.length() - 1; a++) {

                            if (movePosition.charAt(a) == ',') {
                                moveLeftnum = movePosition.substring(0, a);
                                moveRightnum = movePosition.substring(a + 1);
                            }
                        }
                        for (int q = 0; q <= currentSettlersList.length - 1; q++) {
                            if (!currentSettlersList[q].equals("") && !currentSettlersList[q].equals(" ")) {
                                String leftnum = "";
                                String rightnum = "";
                                for (int a = 0; a <= currentSettlersList[q].length() - 1; a++) {
                                    if (currentSettlersList[q].charAt(a) == ',') {
                                        leftnum = currentSettlersList[q].substring(0, a);
                                        rightnum = currentSettlersList[q].substring(a + 1);
                                    }
                                }
                                //Check 6 situations of adjacent
                                int moveleftnumI = Integer.parseInt(moveLeftnum);
                                int moveRightnumI = Integer.parseInt(moveRightnum);
                                int leftnumI = Integer.parseInt(leftnum);
                                int rightnumI = Integer.parseInt(rightnum);

                                if (moveleftnumI % 2 != 0) {
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {

                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                }

                                if (moveleftnumI % 2 == 0) {
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI - 1 && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI - 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI) {
                                        return true;
                                    }
                                    if (leftnumI == moveleftnumI + 1 && rightnumI == moveRightnumI + 1) {
                                        return true;
                                    }
                                }
                            }
                        }
                        //If it's the move position is not near the pieces put before
                        return false;
                    }
                    //Settlers are put on the land but there is no pieces around
                    return false;

            }else {
                //S phase is not allowed to put villages
                return false;
            }
        }
        return true; // FIXME Task 7
    }

    /**
     * Given a state string, generate a set containing all move strings playable
     * by the current player.
     * <p>
     * A move is playable if it is valid.
     *
     * @param stateString a string representing a game state
     * @return a set of strings representing all moves the current player can play
     */
    public static Set<String> generateAllValidMoves(String stateString) {
        Set<String> validSet = new HashSet<>();

        //Check every block if it's valid and add to the set if it's valid
        for (int row = 0; row <= Board.BOARD_HEIGHT - 1; row++) {
            for (int column = 0; column <= Board.BOARD_HEIGHT - 1; column++) {
                //Because the board is 12 13 12 13, to eliminate the 0,13 2,13......
                if (row % 2 == 0 && column == Board.BOARD_HEIGHT - 1){
                    continue;
                }else {
                String coordinate = row + "," + column;

                String settler = "S " + coordinate;
                if (isMoveValid(stateString, settler)) {
                    validSet.add(settler);
                }

                String village = "T " + coordinate;
                if (isMoveValid(stateString, village)) {
                    validSet.add(village);
                }
            }
            }
        }
        return validSet; // FIXME Task 8
    }

    /**
     * Given a state string, determine whether it represents an end of phase state.
     * <p>
     * A phase is over when either of the following conditions hold:
     * - All resources (not including statuettes) have been collected.
     * - No player has any remaining valid moves.
     *
     * @param stateString a string representing a game state
     * @return true if the state is at the end of either phase and false otherwise
     */
    public static boolean isPhaseOver(String stateString){
         return false; // FIXME Task 9
    }

    /**
     * Given a state string and a move string, place the piece associated with the
     * move on the board. Ensure the player collects any corresponding resource or
     * statuettes.
     * <p>
     * Do not handle switching to the next player here.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a new state string achieved by placing the move on the board
     */
    public static String placePiece(String stateString, String moveString){
         return ""; // FIXME Task 10
    }

    /**
     * a helper for task 11
     * get the sum of scores from two scores array
     * @param a first int array
     * @param b second int array, must have same length as a
     * @return c third array, must have same length as a and b
     */
    public static int[] sumScores (int[] a, int[] b){
        int[] c = new int[a.length];
        for (int i = 0; i < c.length; i++){
            c[i] = a[i] + b[i];
        }
        return c;
    }

    /**
     * Given a state string, calculate the "Islands" portion of the score for
     *      * each player as if it were the end of a phase. The return value is an
     *      * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Islands" portion is calculated for each player as follows:
     * - If the player has pieces on 8 or more islands, they score 20 points.
     * - If the player has pieces on 7 islands, they score 10 points.
     * - No points are scored otherwise.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Islands" portion of
     * the score for each player
     */
    public static int[] calculateTotalIslandsScore(String stateString){
         List<Player> players = Player.playersFromString(stateString);
         for (Player player:players){
             System.out.println(player.toString());
         }
         int[] totalIslandScore = new int[players.size()];
         Island[] islands = Island.getIslands(stateString);
        Coordinate[][] occupied = allSettlersVillages(stateString);
        for (int i = 0; i < players.size(); i++){
            Coordinate[] cors = occupied[i];
            int scoreAtI = getIslandScore(cors,islands);
            totalIslandScore[i] = scoreAtI;
        }
        return totalIslandScore; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Links" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * Players earn points for their chain of pieces that links the most
     * islands. For each island linked by this chain, they score 5 points.
     * <p>
     * Note the chain needn't be a single path. For instance, if the chain
     * splits into three or more sections, all of those sections are counted
     * towards the total.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Links" portion of
     * the score for each player
     */
    public static int[] calculateIslandLinksScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Majorities" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Majorities" portion is calculated for each island as follows:
     * - The player with the most pieces on the island scores the number
     *   of points that island is worth.
     * - In the event of a tie for pieces on an island, those points are
     *   divided evenly between those players rounding down. For example,
     *   if two players tied for an island worth 7 points, they would
     *   receive 3 points each.
     * - No points are awarded for islands without any pieces.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Majorities" portion
     * of the score for each player
     */
    public static int[] calculateIslandMajoritiesScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Resources" and "Statuettes" portions
     * of the score for each player as if it were the end of a phase. The return
     * value is an integer array sorted by player number containing the calculated
     * score for the respective player.
     * <p>
     * Note that statuettes are not resources.
     * <p>
     * In the below "matching" means a set of the same resources.
     * <p>
     * The "Resources" portion is calculated for each player as follows:
     * - For each set of 4+ matching resources, 20 points are scored.
     * - For each set of exactly 3 matching resources, 10 points are scored.
     * - For each set of exactly 2 matching resources, 5 points are scored.
     * - If they have all four resource types, 10 points are scored.
     * <p>
     * The "Statuettes" portion is calculated for each player as follows:
     * - A player is awarded 4 points per statuette in their possession.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Resources" and "Statuettes"
     * portions of the score for each player
     */
    public static int[] calculateResourcesAndStatuettesScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the scores for each player as if it were
     * the end of a phase. The return value is an integer array sorted by player
     * number containing the calculated score for the respective player.
     * <p>
     * It is recommended to use the other scoring functions to assist with this
     * task.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated scores for each player
     */
    public static int[] calculateScores(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string representing an end of phase state, return a new state
     * achieved by following the end of phase rules. Do not move to the next player
     * here.
     * <p>
     * In the Exploration Phase, this means:
     * - The score is tallied for each player.
     * - All pieces are removed from the board excluding villages not on stone circles.
     * - All resources and statuettes remaining on the board are removed. All resources are then
     *   randomly redistributed between the stone circles.
     * <p>
     * In the Settlement Phase, this means:
     * - Only the score is tallied and added on for each player.
     *
     * @param stateString a string representing a game state at the end of a phase
     * @return a string representing the new state achieved by following the end of phase rules
     */
    public static String endPhase(String stateString){
        String endPhaseString = "";

        endPhaseString += getArrangementStatement(stateString) +";";
        endPhaseString += getCurrentStateStatement(stateString).replace("E","S") + ";";
        endPhaseString += getIslandStatement(stateString);
        endPhaseString += getStoneStatement(stateString) + "; ";

        // Fill the resources again
        String resourceString = getUnclaimedResourcesandStatuettesStatement(stateString) +";";

        // Refill the coconuts to 6
        List<String> newCoconutList = getCoconutList(stateString);
        if (newCoconutList.size() < 6){
            for (int i = 0 ; i < 6 - getCoconutList(stateString).size(); i++){
                newCoconutList.add(Coordinate.randomCord().toString());
            }
        }
        resourceString = "r C";
        for (String c: newCoconutList){
            resourceString += " " + c;
        }


        // Refill the bamboo to 6
        List<String> newBambooList = getBamboo(stateString);
        if (newBambooList.size() < 6){
            for (int i = 0 ; i < 6 - getBamboo(stateString).size(); i++){
                newBambooList.add(Coordinate.randomCord().toString());
            }
        }
        resourceString += " B";
        for (String b: newBambooList){
            resourceString += " " + b;
        }

        // Refill the water to 6
        List<String> newWaterList = getWater(stateString);
        if (newWaterList.size() < 6){
            for (int i = 0 ; i < 6 - getWater(stateString).size(); i++){
                newWaterList.add(Coordinate.randomCord().toString());
            }
        }
        resourceString += " W";
        for (String w: newWaterList){
            resourceString += " " + w;
        }


        // Refill the precious stone to 6
        List<String> newStoneList = getPreciousStone(stateString);
        if (newStoneList.size() < 6){
            for (int i = 0 ; i < 6 - getPreciousStone(stateString).size(); i++){
                newStoneList.add(Coordinate.randomCord().toString());
            }
        }
        resourceString += " P";
        for (String p: newStoneList){
            resourceString += " " + p;
        }

        // Refill the Statuette to 8
        List<String> newStatuetteList = getStatuette(stateString);
        if (newStatuetteList.size() < 8){
            for (int i = 0 ; i < 8 - getStatuette(stateString).size(); i++){
                newStoneList.add(Coordinate.randomCord().toString());
            }
        }
        resourceString += " S";
        for (String s: newStoneList){
            resourceString += " " + s;
        }
        System.out.println(resourceString);
        endPhaseString += resourceString + "; ";


        // Calculate Player Score
        List<Player> players = new ArrayList<>();
        String[] playersStatement = getPlayerStatement(stateString).split(";");
        for (String s : playersStatement){
            players.add(playerFromString(s.strip()));
        }

        for (Player player: players) {
            player.clearResources();
            player.clearSettlers();
            endPhaseString += player.toStateString() + " ";
        }
        System.out.println(stateString);
        System.out.println(endPhaseString);

        return endPhaseString.strip(); // FIXME Task 12
    }

    /**
     * Given a state string and a move string, apply the move to the board.
     * <p>
     * If the move ends the phase, apply the end of phase rules.
     * <p>
     * Advance current player to the next player in turn order that has a valid
     * move they can make.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a string representing the new state after the move is applied to the board
     */
    public static String applyMove(String stateString, String moveString){
         return stateString; // FIXME Task 13
    }

    /**
     * Given a state string, returns a valid move generated by your AI.
     * <p>
     * As a hint, generateAllValidMoves() may prove a useful starting point,
     * maybe if you could use some form of heuristic to see which of these
     * moves is best?
     * <p>
     * Your AI should perform better than randomly generating moves,
     * see how good you can make it!
     *
     * @param stateString a string representing a game state
     * @return a move string generated by an AI
     */
    public static String generateAIMove(String stateString){
         return ""; // FIXME Task 16
    }

    public static void main(String[] args) {
        String stateString = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C 1,1 B 5,2 W P 1,4 S; p 0 0 0 0 0 0 0 S T; p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
        System.out.println(getAllPlayers(stateString));
        System.out.println(isStateStringWellFormed(stateString));


    }

}
