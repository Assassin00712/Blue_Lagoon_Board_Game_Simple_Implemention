package comp1110.ass2.board;

import comp1110.ass2.BlueLagoon;
import gittest.C;

import static comp1110.ass2.board.Coordinate.isLinked;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {


    public int playId;
    public int score;
    public List<Integer> resources;
    public List<Coordinate> settlers;
    public List<Coordinate> villages;
    public int statuette;


    public Player(int playId) {
        this.playId = playId;

    }


    public Player() {
        this.playId = 0;
        this.score = 0;
        this.resources = new ArrayList<>();
        // {coconut, bamboo, water, preciousStone, statuette}
        this.settlers = new ArrayList<>();
        this.villages = new ArrayList<>();
    }
        public void setResources (List<Integer> resources) {
            this.resources = resources;
        }

    @Override
    public String toString() {
        return "Player{" +
                "playId=" + playId +
                ", score=" + score +
                ", resources=" + resources +
                ", settlers=" + settlers +
                ", villages=" + villages +
                '}';
    }

    public static Player playerFromString (String playerStates){
        Player player = new Player();
        List<Coordinate> corS = new ArrayList<>();
        List<Coordinate> corV = new ArrayList<>();
        if (playerStates.length() >=19){
            //System.out.println("the playerString is valid");
        String[] players = playerStates.strip().split(" ");
        player.playId = Integer.parseInt(players[1]);
        player.score = Integer.parseInt(players[2]);
        List<Integer> resources = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resources.add(i, Integer.parseInt(players[i + 3]));
        }
        player.resources = resources;
        int settlerStarts = 0;
        int villageStarts = 0;
        for (int j = 5; j < players.length; j++) {
            if (players[j].equals("S")) {
                // actual settler locate at j+1
                settlerStarts = j;
            }
            else if (players[j].startsWith("T")) {
                villageStarts = j;}
        }
        // test if there is settler for a player, if string "S" is followed by "T"
        // then the player has no settler, the corS will remain blank
            if (!(settlerStarts == villageStarts-1)){
            for (int k = settlerStarts + 1; k < villageStarts; k++) {
                corS.add(new Coordinate(players[k]));
            }}
            // test if there is village for a player, if player array ends with "T"
        // then the player has no villages, the corV will remain blank
            if (!(villageStarts == players.length-1)) {
            for (int l = villageStarts + 1; l < players.length; l++) {
                corV.add(new Coordinate(players[l]));
                }
            }
        }
        player.settlers = corS;
        player.villages = corV;
        player.statuette = player.resources.get(player.resources.size()-1);

        return player;
    }

    /** Turn a stateString into a list of player strings
     * each output starts with "p";
     * ex: players.get(0) == "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2";
     */

    public static List<String> extractPlayers (String stateString){
        String[] states = stateString.split("; ");
        List<String> playerStates = new ArrayList<>();
        for (String state : states) {
            if (state.startsWith("p")) {
                playerStates.add(state.strip());
            }
        }
        return playerStates;
    }

    /** Turn a stateString into an array of players
     */

    public static List<Player> playersFromString (String gameStates){
        List<String> playerStrings = extractPlayers(gameStates);
        List<Player> players = new ArrayList<>();
        for (String playerString : playerStrings) {
            players.add(playerFromString(playerString.strip()));
        }
        return players;
    }

    /**
     * get all coordinates occupied by a player regardless if it is a village or settler
     * @param playerStates String represents player
     * @return array of all coordinates occupied by a player
     */
    public static List<Coordinate> combineSettlersVillages (String playerStates){
        Player player = playerFromString(playerStates);
        List<Coordinate> settlers = player.settlers;
        List<Coordinate> villages = player.villages;
        List<Coordinate> combined = new ArrayList<>();
        for (Coordinate settler : settlers){
            combined.add(settler);
        }
        for (Coordinate village : villages){
            combined.add(village);
        }
        return combined;
    }

    /**
     * check if a coordinate is chained to a list of Coordinate
     * it is chained with the list if it is contained in the list
     * it is not chained if it is not linked in any entries in the list
     * @param cor the coordinate to check
     * @param compare the coordinate to be checked
     * @return true if the cor is chained with the list, false otherwise
     */
    public static boolean isChained (Coordinate cor, List<Coordinate> compare){
        for (Coordinate c : compare){
            if (isLinked(cor,c)||cor.equals(c)){
                return true;
            }}
        return false;
    }

    /**
     * a method to get the chained settlers and villages
     * @param accumulated the list of lists of coordinates that have already passed the test
     *                    starts at blank
     * @param all all coordinates occupied by a player
     * @return a list of lists of Coordinates that are chained
     * lenght of 1 is allowed for each list
     */

    public static List<List<Coordinate>> getChainedOccupier (List<List<Coordinate>> accumulated, List<Coordinate> all){
        if (all.size()==0){
            return accumulated;
        }
        List<Coordinate> toCheck = new ArrayList<>(all);
        List<Coordinate> thrown = new ArrayList<>();
        // for every entry in all, check if they are linked with every other entries
        // if any of them are not linked, throw them to the next list in accumulate
        for (Coordinate c : toCheck) {
            if (!isChained(c, all)) {
                System.out.println(c.toString());
                all.remove(c);
                thrown.add(c);
            }
        }
        accumulated.add(all);
        System.out.println("accumulated is " + accumulated.toString());
        System.out.println("all is " + all.toString());
        return getChainedOccupier(accumulated,thrown);
    }

    /**
     * get all coordinates occupied by all players
     * @param stateString String represents current states
     * @return array of Coordinates occupied by all players
     */

    public static List<Coordinate>[] allSettlersVillages (String stateString){
        List<String> players = extractPlayers(stateString);
        List<Coordinate>[] all = new ArrayList[players.size()];
        for (int i = 0; i < all.length; i++) {
            List<Coordinate> each = combineSettlersVillages(players.get(i));
            all[i] = each;
        }
        return all;
    }


    /**
     * Transform players back to statement from
     *
     * @return String of one player's statement
     */
    public String toStateString () {
        String resourceState = "";
        String settlerState = "S ";
        String villageState = "T";

        for (int i : resources) {
            resourceState += i + " ";
        }

        for (Coordinate c : settlers) {
            settlerState += c + " ";
        }

        for (Coordinate c : villages) {
            villageState += " " + c;
        }

        return "p " + playId +
                " " + score +
                " " + resourceState +
                settlerState +
                villageState +
                ";";
    }

    public void clearResources () {
        for (int i = 0; i < resources.size(); i++) {
            resources.set(i, 0);
        }
    }

    public void clearSettlers () {
        settlers.clear();
    }

    // Remove village if it's on a stone
    public void clearVillages(){
        List<Coordinate> validVillages = new ArrayList<>();
        for (Coordinate coordinate: this.villages){
            if (!(BlueLagoon.board.spotMatrix[coordinate.row][coordinate.col].isStone)){
                validVillages.add(coordinate);
            }
        }
        this.villages = validVillages;
    }

    // Calculate player's score
    public int getResourcesAndStatuettesScore(){
        // score of every type of resources
        int score = 0;
        for (int numbers: resources.subList(0, resources.size()-1)){
            if (numbers >= 4){
                score += 20;
            } else if (numbers == 3) {
                score += 10;
            } else if (numbers == 2) {
                score += 5;
            }
        }

        // score of different type of resources
        if (resources.get(0) > 0 &&
            resources.get(1) > 0 &&
            resources.get(2) > 0 &&
            resources.get(3) > 0 ){
            score += 10;
        }

        score += resources.get(4) * 4;


        return score;

    }

    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; " +
            "p 0 0 0 0 0 0 0 S T; p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
    public static final String WHEELS_GAME = "a 13 2; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; " +
            "p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; " +
            "p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME =  "a 7 2; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; " +
            "p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME = "a 23 2; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; " +
            "p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";


    public static void main(String[] args) {
        String testPlayer = "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
        Player player0 = playerFromString(testPlayer);
        System.out.println(player0);
        System.out.println(player0.toStateString());
    }
    }














