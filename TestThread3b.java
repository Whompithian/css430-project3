/*
 * @file    TestThread3b.java
 * @brief   This class is a test thread that is I/O-intensive. It uses rawread
 *           and rawwrite calls to a virtual disk to keep busy waitin for I/O
 *           availability. Operations are designed so that each access is made
 *           in the bounds of a different simulated track than any adjacent
 *           access to maximize seek time.
 * @author  Brendan Sweeney, SID 1161836
 * @date    November 14, 2012
 */
public class TestThread3b extends Thread {
    private final static int BLOCKS     = 1000;     // number of blocks on disk
    private final static int BLOCK_SIZE = 512;      // size of blocks, in bytes
    private final static int TRACKS     = 10;       // simulated track count
    private final static int REPS       = 80;       // amount of work to do
    private byte[] buffer;          // data buffer for disk reads and writes
    
    
    /**
     * Initializes a read/write buffer to the block size of the simulated disk.
     * @pre    None.
     * @post   buffer can hold one simulated disk block worth of data.
     */
    public TestThread3b () {
        buffer = new byte[BLOCK_SIZE];
    } // end default constructor
    
    
    /**
     * Performs a number of read and write operations to the simulated disk.
     * @pre    buffer has been initialized to the block size of the disk.
     * @post   All reads and writes have been performed and the disk has been
     *          synchronized.
     */
    @Override
    public void run() {
        for (int i = 0; i < REPS; ++i) {
            // set the buffer to an obvious pattern
            fillBuffer();
            SysLib.rawwrite((i + i * (BLOCKS / TRACKS)) % BLOCKS, buffer);
            SysLib.rawread(((i + i) * (BLOCKS / TRACKS)) % BLOCKS, buffer);
        } // end for (; i < REPS; )
        
        // write all outstanding data to disk before exit
        SysLib.sync();
        SysLib.exit();
    } // end run()
    
    
    /**
     * Initializes the data buffer with an obvious pattern.
     * @pre    None.
     * @post   buffer contains a pattern that can be easily verified.
     */
    private void fillBuffer() {
        for (int i = 0; i < BLOCK_SIZE; ++i) {
            buffer[i] = (byte)(i % 128);
        } // end for (; i < BLOCK_SIZE; )
    } // end fillBuffer()
} // end class TestThread3b
