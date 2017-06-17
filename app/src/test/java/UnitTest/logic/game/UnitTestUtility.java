package UnitTest.logic.game;

import com.example.popina.projekat.logic.game.utility.Utility;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by popina on 17.06.2017..
 */

public class UnitTestUtility
{
    @Test
    public void degToRadianTest()
    {
        Assert.assertTrue(Utility.isDimBetweenDims(2 * (float)Math.PI, 2 * (float)Math.PI, (float)Utility.degToRadian(360)));
        Assert.assertTrue(Utility.isDimBetweenDims((float)Math.PI, (float)Math.PI, (float)Utility.degToRadian(180)));
        Assert.assertTrue(Utility.isDimBetweenDims( (float)Math.PI /2,  (float)Math.PI/2, (float)Utility.degToRadian(90)));
        Assert.assertTrue(Utility.isDimBetweenDims(3 * (float)Math.PI / 2, 3 * (float)Math.PI / 2, (float)Utility.degToRadian(270)));
    }

    @Test
    public void radianToDegTest()
    {
        Assert.assertTrue(Utility.isDimBetweenDims(360, 360, (float)Utility.radianToDeg(2 * (float)Math.PI)));
        Assert.assertTrue(Utility.isDimBetweenDims(180, 180, (float)Utility.radianToDeg((float)Math.PI)));
        Assert.assertTrue(Utility.isDimBetweenDims( 90,  90, (float)Utility.radianToDeg((float)Math.PI /2)));
        Assert.assertTrue(Utility.isDimBetweenDims(270, 270, (float)Utility.radianToDeg(3 * (float)Math.PI / 2)));
    }

    @Test
    public void testConvertRadianAngleTo2PiRange()
    {
        Assert.assertTrue(Utility.isDimBetweenDims(0, 0, (float)Utility.convertRadianAngleTo2PiRange(2 * (float)Math.PI)));
        Assert.assertTrue(Utility.isDimBetweenDims(0.78539816339f, 0.78539816339f, (float)Utility.convertRadianAngleTo2PiRange(9 * (float)Math.PI/4)));
        Assert.assertTrue(Utility.isDimBetweenDims(5.49778714378f, 5.49778714378f, (float)Utility.convertRadianAngleTo2PiRange(7 * (float)Math.PI/4)));
    }
}
