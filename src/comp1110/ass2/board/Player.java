package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<String> playerStates = new ArrayList<String>();
        for (String state : states){
            if (state.startsWith("p")) playerStates.add(state);
        }
        String[] players = (String[]) playerStates.toArray();
        return players;
    }

    /** Turn a stateString into an array of players
     */

    public static Player[] playersFromString (String gameStates){
       String[] playerStrings = extractPlayers(gameStates);
        Player[] players = new Player[playerStrings.length];
        for (int i = 0; i < players.length; i ++){
             String playerString = playerStrings[i];
            players[i] = playerFromString(playerString);
        }
        return players;
    }


    public static void main(String[] args) {
        String stringPLAYER1 = "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
        Player player1 = playerFromString(stringPLAYER1);
        System.out.println(player1.playId);
        System.out.println(player1.getScore());
    }

}













