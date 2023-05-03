package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

import gittest.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

public class Player {


    int playId;
    int score;
    int[] resources;
    Coordinate[] settlers;
    Coordinate[] villages;


    public Player(int playId,
                  int score,
                  int[] resources,
                  Coordinate[] settlers,
                  Coordinate[] villages) {
        this.playId = playId;
        this.score = score;
        this.resources = resources;
        // {coconut, bamboo, water, preciousStone, statuette}
        this.settlers = settlers;
        this.villages = villages;
    }


    public final Coordinate[] blankC = new Coordinate[0];
    public final int[] blankI = new int[0];

    public Player() {
        this.playId = 0;
        this.score = 0;
        this.resources = blankI;
        // {coconut, bamboo, water, preciousStone, statuette}
        this.settlers = blankC;
        this.villages = blankC;

    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[] getResources() {
        return resources;
    }

    public void setResources(int[] resources) {
        this.resources = resources;
    }

    public Coordinate[] getSettlers() {
        return settlers;
    }



    public void setSettlers(Coordinate[] settlers) {
        this.settlers = settlers;
    }

    public Coordinate[] getVillages() {
        return villages;
    }



    public void setVillages(Coordinate[] villages) {
        this.villages = villages;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playId=" + playId +
                ", score=" + score +
                ", resources=" + Arrays.toString(resources) +
                ", settlers=" + Arrays.toString(settlers) +
                ", villages=" + Arrays.toString(villages) +
                '}';
    }

    public static Player playerFromString(String playerStates) {
        Player player = new Player();
        String[] players = playerStates.split(" ");
        player.setPlayId(Integer.parseInt(players[1]));
        player.setScore(Integer.parseInt(players[2]));
        int[] resources = new int[5];
        for (int i = 0; i < 5; i++) {
            resources[i] = Integer.parseInt(players[i + 3]);
        }
//        resources[0] = Integer.parseInt(players[3]);
//        resources[1] = Integer.parseInt(players[4]);
//        resources[2] = Integer.parseInt(players[5]);
//        resources[3] = Integer.parseInt(players[6]);
//        resources[4] = Integer.parseInt(players[7]);
        player.setResources(resources);
        int settlerStarts = 0;
        int villageStarts = 0;
        for (int j = 5; j < players.length; j++) {
            if (players[j].equals("S")) {
                settlerStarts = j;
            }
            if (players[j].equals("T")) {
                villageStarts = j;
            } }
            Coordinate[] corS = new Coordinate[villageStarts - settlerStarts-1];
            for (int k = 0; k < corS.length; k++) {
             corS[k] = corFromString(players[settlerStarts + k + 1]);
             player.setSettlers(corS);
            }

            Coordinate[] corV = new Coordinate[players.length - villageStarts-1];
            for (int l = 0; l < corV.length; l++) {
              corV[l] = corFromString(players[villageStarts + l + 1]);
              player.setVillages(corV);
            }

        return player;
    }

    /** Turn a stateString into an array of player strings
     * output starts with "p";
     * ex: players[0] == "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2";
     */

    public static String[] extractPlayers(String stateString){
        String[] states = stateString.split(";");
        List<String> playerStates = new ArrayList<>();
        for (String state : states){
            if (state.charAt(1)=='p') playerStates.add(state);
        }
        String[] players = new String[playerStates.size()];
        for(int i = 0; i < players.length; i++){
            players[i] = playerStates.get(i);
        }
        return players;
    }

    /** Turn a stateString into an array of players
     */

    public static Player[] playersFromString (String stateString){
        // first exact the player statements from the stateString
        String[] playerStrings = extractPlayers(stateString);
        // create a new empty players array
        Player[] players = new Player[playerStrings.length];
        // give each player in players array values given the player statements
        for (int i = 0; i < players.length; i++){
            String playerString = playerStrings[i];
            System.out.println(playerString);
            players[i] = playerFromString(playerString);
        }
        return players;
    }

    /**
     * get all coordinates occupied by a player regardless if it is a village or settler
     * @param playerStates String represents player
     * @return array of all coordinates occupied by a player
     */
    public static Coordinate[] combineSettlersVillages (String playerStates) {
        Player player = playerFromString(playerStates);
        Coordinate[] settlers = player.getSettlers();
        Coordinate[] villages = player.getVillages();
        Coordinate[] combined = new Coordinate[settlers.length+villages.length];
        System.arraycopy(settlers,0, combined,0,settlers.length);
        System.arraycopy(villages,0, combined,settlers.length-1,villages.length);
        return combined;
    }

    /**
     * get all coordinates occupied by all players
     * @param stateString String represents current states
     * @return array of Coordinates occupied by all players
     */

    public static Coordinate[][] allSettlersVillages (String stateString){
        String[] players = extractPlayers(stateString);
        List<Coordinate[]> all = new ArrayList<>();
        for (String player: players){
            Coordinate[] each = combineSettlersVillages(player);
            all.add(each);
        }
        return all.toArray(new Coordinate[0][0]);
    }

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


    public static void main(String[] args) {
        System.out.println("Player.java is running");
        String[] players1 = extractPlayers(FACE_GAME);
        for (String player : players1){
            System.out.println("extractPlayers is executing...");
            System.out.println(player);
        }
        Player test = playerFromString("p 0 0 0 0 0 0 0 S 0,3 0,4 T 0,5");
        System.out.println(test.toString());
        Player[] players = playersFromString(DEFAULT_TEST);
        System.out.println(Arrays.toString(players));
        for (Player player : players){
            System.out.println("playersFromString is executing...");
            System.out.println(player.toString());
        }
    }

}













