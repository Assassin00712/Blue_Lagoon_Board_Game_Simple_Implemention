package comp1110.ass2.board;

import static comp1110.ass2.board.Coordinate.corFromString;

import gittest.A;
import gittest.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Override
    public String toString() {
        return "Player" + playId +
                "{score=" + score +
                ", resources=" + resources +
                ", settlers=" + settlers +
                ", villages=" + villages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playId == player.playId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playId);
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
            }
        }
        List<Coordinate> corS = new ArrayList<>();
        for (int k = 0; k < villageStarts - settlerStarts-1; k++) {
            corS.add(k, corFromString(players[settlerStarts + k + 1]));
            player.settlers = corS;
        }

        List<Coordinate> corV = new ArrayList<>();
        for (int l = 0; l < players.length - villageStarts-1; l++) {
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

    public static List<Player> playersFromString (String gameStates){
       String[] playerStrings = extractPlayers(gameStates);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerStrings.length; i ++){
            String playerString = playerStrings[i];
            players.add(i, playerFromString(playerString));
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


    /**
     * Transform players back to statement from
     *
     * @return String of one player's statement
     */
    public String toStateString(){
        String resourceState = "";
        String settlerState = "S ";
        String villageState = "T";

        for (int i : resources){
            resourceState += i + " ";
        }

        for (Coordinate c : settlers){
            settlerState += c + " ";
        }

        for (Coordinate c : villages){
            villageState += " " + c;
        }

        return "p " + playId +
                " " + score +
                " " + resourceState +
                settlerState +
                villageState +
                ";";
    }

    public void clearResources(){
        for (int i = 0; i < resources.size(); i++){
            resources.set(i, 0);
        }
    }

    public void clearSettlers(){
        settlers.clear();
    }


    public static void main(String[] args) {
        String stringPlayer1 = "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";
        Player player1 = playerFromString(stringPlayer1);
        System.out.println(stringPlayer1);
        System.out.println(player1.toStateString());

        String string2 = "p 0 0 1 2 1 0 1 S 0,3 0,4 0,8 1,4 2,5 2,9 3,4 3,5 3,9 3,11 4,10 5,7 5,10 5,11 6,9 7,1 7,3 7,4 7,11 8,2 8,3 8,6 8,7 9,3 9,4 9,6 9,8 10,2 10,3 11,2 T 0,6 0,7 1,6 4,5 8,1;";
        Player player2 = playerFromString(string2);
        System.out.println(player2.toStateString());
        System.out.println(player2);
    }

}













