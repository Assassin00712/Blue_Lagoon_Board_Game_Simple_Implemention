package comp1110.ass2.board;

import comp1110.ass2.board.Coordinate;

import static comp1110.ass2.board.Coordinate.corFromString;
import comp1110.ass2.board.Spot;

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

    public void playerFromString(String[] gameStates){
        for (int i = 0; i < gameStates.length; i++) {
            if (gameStates[i].startsWith("p")){
                String[] players = gameStates[i].split(" ");
                setPlayId(Integer.parseInt(players[1]));
                setScore(Integer.parseInt(players[2]));
                int[] resources = new int[5];
                resources[0] = Integer.parseInt(players[3]);
                resources[1] = Integer.parseInt(players[4]);
                resources[2] = Integer.parseInt(players[5]);
                resources[3] = Integer.parseInt(players[6]);
                resources[4] = Integer.parseInt(players[7]);
                setResources(resources);
                for (int j = 0; j < players.length; j++){
                    if (players[j].startsWith("S")){
                        String[] settlers = players[j].split(" ");
                        Coordinate[] cors = new Coordinate[settlers.length];
                        for (int k = 0; k < settlers.length; k++) {
                            cors[k] = corFromString(settlers[k]);
                        }
                        setSettlers(cors);
                        }
                    if (players[j].startsWith("T")){
                        String[] villages = players[j].split(" ");
                        Coordinate[] cors = new Coordinate[villages.length];
                        for (int k = 0; k < villages.length; k++) {
                            cors[k] = corFromString(villages[k]);
                        }
                        setVillages(cors);
                    }
                    }
                }

            }
        }
    }


}
