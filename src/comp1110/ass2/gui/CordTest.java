package comp1110.ass2.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CordTest {

    @Test
    public void cordToXTest(){
        double result = 86.60254037844386;
        Assertions.assertEquals(result, Viewer.cordToXY(1,2)[0], "Coordinates are converted to incorrect X-axis values.");

        double result2 = 259.8076211353316;
        Assertions.assertEquals(result2, Viewer.cordToXY(5,4)[0], "Coordinates are converted to incorrect X-axis values.");

        double result3 = 476.31397208144125;
        Assertions.assertEquals(result3, Viewer.cordToXY(10,10)[0], "Coordinates are converted to incorrect X-axis values.");

    }

    @Test
    public void cordToYTest(){
        double result = 100.0;
        Assertions.assertEquals(result, Viewer.cordToXY(1,2)[1], "Coordinates are converted to incorrect Y-axis values.");

        double result2 = 175.0;
        Assertions.assertEquals(result2, Viewer.cordToXY(5,4)[1], "Coordinates are converted to incorrect Y-axis values.");

        double result3 = 400.0;
        Assertions.assertEquals(result3, Viewer.cordToXY(10,10)[1], "Coordinates are converted to incorrect Y-axis values.");

    }
}
