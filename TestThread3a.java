/*
 * @file    TestThread3a.java
 * @brief   This class is a test thread that is CPU-intensive. It uses the Java
 *           BigInteger class to store a factorial. It also converts the value
 *           to a string for extra CPU consumption.
 * @author  Brendan Sweeney, SID 1161836
 * @date    November 14, 2012
 */
import java.math.BigInteger;


public class TestThread3a extends Thread {
    // size of factorial to calculate
    final static BigInteger MAX_FACT = new BigInteger("3000");
    BigInteger fact;            // container for calculated value
    
    
    /**
     * Sets the starting factorial to 1.
     * @pre    None.
     * @post   The starting factorial is set to 1.
     */
    public TestThread3a () {
        fact = BigInteger.ONE;
    } // end default constructor
    
    
    /**
     * Performs a number of factorial calculations, converting the calculated
     *  value to a string for extra busy work.
     * @pre    MAX_FACT is not large enough that the calculated factorial will
     *          overflow a BigInteger.
     * @post   None.
     */
    @Override
    public void run() {
        // int and BigInteger are not compatible; besides, BigInteger probably
        // requires more CPU
        for (BigInteger i = BigInteger.ONE;
                i.compareTo(MAX_FACT) <= 0;
                i = i.add(BigInteger.ONE)) {
            fact = fact.multiply(i);
            fact.toString();    // extra busy work
        } // end for (; i.compareTo(MAX_FACT) <= 0; )
        
        SysLib.exit();
    } // end run()
} // end class TestThread3a
