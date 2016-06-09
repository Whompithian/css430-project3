/*
 * @file    SyncQueue.java
 * @brief   This class is a Java monitor for queueing threads that are waiting
 *           on some event. A parent may place itself into the queue while it
 *           waits for a child to complete some task. The child may then notify
 *           the parent when it is finished and pull it out of the queue.
 * @author  Brendan Sweeney, SID 1161836
 * @date    November 14, 2012
 */
public class SyncQueue {
    private final static int COND_DEF = 10;     // default number of conditions
    private QueueNode[] queue;                  // queue for sleeping threads
    
    
    /**
     * Creates a queue and allows threads to wait for a COND_DEF number of
     *  condition/event types.
     * @pre    None.
     * @post   This SyncQueue supports COND_DEF number of conditions.
     */
    public SyncQueue() {
        queue = new QueueNode[COND_DEF];
    } // end default constructor
    
    
    /**
     * Creates a queue and allows threads to wait for a condMax number of
     *  condition/event types (default number is COND_DEF).
     * @param  condMax  The number of different conditions for this queue to
     *                   hold.
     * @pre    condMax is a positive number.
     * @post   This SyncQueue supports condMax number of conditions.
     */
    public SyncQueue(int condMax) {
        // ensure sane queue size
        if (condMax > 0) {
            queue = new QueueNode[condMax];
        } // end if (condMax > 0)
        else {
            queue = new QueueNode[COND_DEF];
        } // end else (condMax <= 0)
    } // end constructor
    
    
    /**
     * Enqueues the calling thread and puts it to sleep until a given condition
     *  is satisfied, returning the ID of a child thread that has woken up the
     *  calling thread.
     * @param  condition  The condition (index) on which the calling thread
     *                     will wait.
     * @pre    condition is within the bounds of the queue.
     * @post   The calling thread has slept on the specified condition.
     * @return The TID of the child thread that woke up the calling thread.
     */
    public int enqueueAndSleep(int condition) {
        // ensure condition is within queue bounds
        condition = condition % queue.length;
        
        if (queue[condition] == null) {
            queue[condition] = new QueueNode();
        } // end if (queue[condition] == null)
        
        return queue[condition].sleep();
    } // end enqueueAndSleep(int)
    
    
    /**
     * Dequeues and wakes up a thread waiting for a given condition. If there
     *  are two or more threads waiting for the same condition, only one thread
     *  is dequeued and resumed. The FCFS (first-come-first-served) order does
     *  not matter.
     * @param  condition  The condition (index) on which a waiting thread will
     *                     be woke up.
     * @pre    condition is within the bounds of the queue; there is a thread
     *          waiting on the specified condition.
     * @post   A thread waiting on the specified condition has woken up, with 0
     *          reported as the awakening thread ID.
     */
    public void dequeueAndWakeup(int condition) {
        // ensure condition is within queue bounds
        condition = condition % queue.length;
        
        // ensure node exists for specified condition
        if (queue[condition] != null) {
            queue[condition].wakeup(0);
        } // end if (queue[condition] != null)
    } // end dequeueAndWakeup(int)
    
    
    /**
     * Dequeues and wakes up a thread waiting for a given condition. If there
     *  are two or more threads waiting for the same condition, only one thread
     *  is dequeued and resumed. The FCFS (first-come-first-served) order does
     *  not matter. Receives the calling thread's TID, which will will be
     *  passed to the thread that has been woken up from enqueueAndSleep().
     * @param  condition  The condition (index) on which a waiting thread will
     *                     be woke up.
     * @param  tid  The thread ID to be reported as the awakening thread ID.
     * @pre    condition is within the bounds of the queue; there is a thread
     *          waiting on the specified condition.
     * @post   A thread waiting on the specified condition has woken up, with
     *          tid reported as the awakening thread ID.
     */
    public void dequeueAndWakeup(int condition, int tid) {
        // ensure condition is within queue bounds
        condition = condition % queue.length;
        
        // ensure node exists for specified condition
        if (queue[condition] != null) {
            queue[condition].wakeup(tid);
        } // end if (queue[condition] != null)
    } // end dequeueAndWakeup(int)
} // end class SyncQueue
