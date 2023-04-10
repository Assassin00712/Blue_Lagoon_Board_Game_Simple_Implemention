package comp1110.ass2.player;

import comp1110.ass2.board.Coordinate;

public class Player {

    int playId;
    public Player(
            int playId,
            int score,
            int[] resources,
            Coordinate[] settlers,
            Coordinate[] villages
    )
    {
        this.playId=playId;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }
}
