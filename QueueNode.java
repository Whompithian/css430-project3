/*
 * @file    QueueNode.java
 * @brief   This class is a node for a SyncQueue. It holds a Vector of TIDs
 *           that are waiting on some condition. Threads placed in a QueueNode
 *           may be put to sleep while they wait on a condition and they may be
 *           woken up by another thread once that condition is met.
 * @author  Brendan Sweeney, SID 1161836
 * @date    November 14, 2012
 */
import java.util.Vector;


public class QueueNode {
    private Vector<Integer> tids;   // IDs of threads meeting a condition
    
    
    /**
     * Creates a TID Vector with no elements.
     * @pre    None.
     * @post   This QueueNode is ready to accept waiting threads.
     */
    public QueueNode() {
        tids = new Vector<Integer>();
    } // end default constructor
    
    
    /**
     * Puts a thread to sleep with wait() until another thread wakes it up.
     * @pre    The calling thread has a child that will wake it up.
     * @post   The calling thread has waited for a child to wake it up.
     * @return The TID of the child that woke up the calling thread.
     */
    public synchronized int sleep() {
        // sleep until another thread wakes us up
        if (tids.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // end try wait();
        } // end if (threads.isEmpty())
        
        // return the first waiting TID
        int test = tids.remove(0).intValue();
        return test;
    } // end sleep()
    
    
    /**
     * Wakes up a waiting thread with notify().
     * @param  tid  The thread ID of the awakening thread.
     * @pre    There is a thread waiting on this condition.
     * @post   The waiting thread has been woken up and can find the TID of the
     *          awakening thread in the TID Vector.
     */
    public synchronized void wakeup(int tid) {
        // add to list of waking thread IDs
        tids.add(tid);
        
        // wakeup the parent waiting on this condition
        try {
            notify();
        } catch(IllegalMonitorStateException e) {
            e.printStackTrace();
        } // end try notify();
    } // end wakeup(int)
} // end class QueueNode
