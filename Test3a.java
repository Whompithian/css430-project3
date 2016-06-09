/*
 * @file    Test3a.java
 * @brief   This class is a test case for checking if threads waiting on I/O
 *           block threads that only need to utilize the CPU. It spawns threads
 *           in pairs, one I/O bound and one CPU-intensive thread in each pair.
 *           The completion time of each thread, given in milliseconds from the
 *           start of the test, is printed to standard out. At the end, the
 *           total elapsed time of the test, in milliseconds, is printed to
 *           standard out.
 * @author  Brendan Sweeney, SID 1161836
 * @date    November 14, 2012
 */
import java.util.Date;


public class Test3a extends Thread {
    private final static String cpuTest = "TestThread3a";
    private final static String ioTest  = "TestThread3b";
    private int pairs;
    
    
    /**
     * Creates a new test that is ready to run a single pair of threads.
     * @pre    None.
     * @post   This test is ready to be run with a single pair of threads.
     */
    public Test3a() {
        pairs = 1;
    } // end default constructor
    
    
    /**
     * Creates a new test that is ready to run a specified number of pairs of
     *  thread.
     * @param  args  The list of arguments for this test. Currently, it is only
     *                expected to contain a single positive integer in the
     *                first element.
     * @pre    args[0] can be parsed into a positive integer.
     * @post   This test is ready to be run with a specified number of pairs of
     *          threads.
     */
    public Test3a(String[] args) {
        pairs = Integer.parseInt(args[0]);
    } // end constructor
    
    
    /**
     * Creates threads in pairs to utilize the CPU and disk I/O, respectively.
     *  Upon completion of each thread, the ID of that thread and the time, in
     *  milliseconds since the start of the test, is printed to standard out.
     *  Total elapsed time, in milliseconds, is printed to standard out upon
     *  completion of the test.
     * @pre    TestThread3a and TestThread3b are available to be run and they
     *          don't require any parameters.
     * @post   The test has completed, all threads have returned from execution
     *          and time statistics have been printed to standard out.
     */
    @Override
    public void run() {
        // start timer
        Date stop, start = new Date();
        int  tid;           // returning thread ID
        
        // create pairs of test threads
        for (int i = 0; i < pairs; ++i) {
            SysLib.exec(SysLib.stringToArgs(cpuTest));
            SysLib.exec(SysLib.stringToArgs(ioTest));
        } // end for (; i < pairs; )
        
        // wait on individual threads
        for (int i = 0; i < pairs * 2; ++i) {
            tid  = SysLib.join();
            stop = new Date();
            SysLib.cout(" thread " + tid + " done at " +
                    (stop.getTime() - start.getTime()) + " msec\n");
        } // end for (; i < pairs * 2; )
        
        // stop timer
        stop = new Date();
        
        SysLib.cout("\nelapsed time = " +
                    (stop.getTime() - start.getTime()) + " msec\n");
        SysLib.exit();
    } // end run()
} // end class Test3a
