package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

import gittest.A;
import gittest.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

public class Player {


    public int playId;
    public int score;
    public List<Integer> resources;
    public List<Coordinate> settlers;
    public List<Coordinate> villages;


    public Player(int playId) {
        this.playId = playId;
    }



    public Player() {
        this.playId = 0;
        this.score = 0;
        this.resources = null;
        // {coconut, bamboo, water, preciousStone, statuette}
        this.settlers = null;
        this.villages = null;

    }




    public void addSettlers(Coordinate settler) {
        this.settlers.add(settler);
    }



    public static Player playerFromString(String playerStates) {
        Player player = new Player();
        String[] players = playerStates.split(" ");
        player.playId = Integer.parseInt(players[1]);
        player.score = Integer.parseInt(players[2]);
        List<Integer> resources = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resources.add(i, Integer.parseInt(players[i + 3]));
        }
//        resources[0] = Integer.parseInt(players[3]);
//        resources[1] = Integer.parseInt(players[4]);
//        resources[2] = Integer.parseInt(players[5]);
//        resources[3] = Integer.parseInt(players[6]);
//        resources[4] = Integer.parseInt(players[7]);
        player.resources = resources;
        int settlerStarts = 0;
        int villageStarts = 0;
        for (int j = 5; j < players.length; j++) {
            if (players[j].equals("S")) {
                settlerStarts = j;
            }
            if (players[j].equals("T")) {
                villageStarts = j;
            } }
            List<Coordinate> corS = new ArrayList<>();
            for (int k = 0; k < corS.size(); k++) {
             corS.add(k, corFromString(players[settlerStarts + k + 1]));
             player.settlers = corS;
            }

            List<Coordinate> corV = new ArrayList<>();
            for (int l = 0; l < corV.size(); l++) {
              corV.add(l, corFromString(players[villageStarts + l + 1]));
              player.villages = corV;
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
        String[] players = new String[playerStates.size()];
        for(int i = 0; i < players.length; i++){
            players[i] = playerStates.get(i);
        }
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

    /**
     * get all coordinates occupied by a player regardless if it is a village or settler
     * @param playerStates String represents player
     * @return array of all coordinates occupied by a player
     */
    public static List<Coordinate> combineSettlersVillages (String playerStates) {
        Player player = playerFromString(playerStates);
        List<Coordinate> settlers = player.settlers;
        List<Coordinate> villages = player.villages;
        List<Coordinate> combined = new ArrayList<>();
        System.arraycopy(settlers,0, combined,0,settlers.size());
        System.arraycopy(villages,0, combined,settlers.size()-1,villages.size());
        return combined;
    }

    /**
     * get all coordinates occupied by all players
     * @param stateString String represents current states
     * @return array of Coordinates occupied by all players
     */

    public static Coordinate[][] allSettlersVillages (String stateString){
        String[] players = extractPlayers(stateString);
        List<Coordinate> all = new ArrayList<>();
        for (String player: players){
            List<Coordinate> each = combineSettlersVillages(player);
            all.add((Coordinate) each);
        }
        return all.toArray(new Coordinate[0][0]);
    }


    public static void main(String[] args) {
        String stringPLAYER1 = "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
        Player player1 = playerFromString(stringPLAYER1);
        System.out.println(player1.playId);
        System.out.println(player1.score);
    }

}













