package dartServer.networking.events;

/**
 * Enum for event priority. Event listeners are called in the order HIGHEST to LOWEST.
 */
public enum EventPriority {

    HIGHEST(100),
    HIGH(75),
    NORMAL(50),
    LOW(25),
    LOWEST(0);

    private int priority;

    EventPriority(int priority) {
        this.priority = priority;
    }

    public int getValue() {
        return priority;
    }
}
