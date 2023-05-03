package comp1110.ass2.board;

import org.junit.jupiter.api.Test;
import comp1110.ass2.board.Player;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static comp1110.ass2.board.Player.playerFromString;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)

public class PlayerTest {

        public static final String DEFAULT_PLAYER1 = "p 0 0 0 0 0 0 0 S 0,3 0,4 T 0,5; ";
        public static final String DEFAULT_PLAYER2 = "p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;";

        @Test
        void testPlayerFromString() {
            int[] resources1 = {0, 0, 0, 0, 0};
            int[] resources2 = {1, 2, 3, 4, 5};
            Coordinate[] settler1 = {new Coordinate(0, 3),
                    new Coordinate(0, 4)};
            Coordinate[] settler2 = {new Coordinate(5, 6),
                    new Coordinate(8, 7)};
            Coordinate[] village1 = {new Coordinate(0, 5)};
            Coordinate[] village2 = {new Coordinate(1, 2)};
            assertEquals(0, playerFromString(DEFAULT_PLAYER1).playId);
            assertEquals(1, playerFromString(DEFAULT_PLAYER2).playId);
            assertEquals(42, playerFromString(DEFAULT_PLAYER2).score);
            /*
            assertArrayEquals(resources1, playerFromString(DEFAULT_PLAYER1).resources);
            assertArrayEquals(resources2, playerFromString(DEFAULT_PLAYER2).resources);
            //System.out.println(Arrays.toString(playerFromString(DEFAULT_PLAYER1).getSettlers()));
            assertArrayEquals(settler1, playerFromString(DEFAULT_PLAYER1).settlers);
            assertArrayEquals(settler2, playerFromString(DEFAULT_PLAYER2).settlers);
            assertArrayEquals(village1, playerFromString(DEFAULT_PLAYER1).villages);
            assertArrayEquals(village2, playerFromString(DEFAULT_PLAYER2).villages);
            */
        }




}

