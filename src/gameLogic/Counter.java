package gameLogic;

/**
 * The gameLogic.Counter class represents a simple integer counter that can be incremented or decremented.
 * It maintains a count value that can be accessed or modified through provided methods.
 */
public class Counter {
    // The current count value
    private int count;

    /**
     * Constructs a new gameLogic.Counter with the specified initial count.
     *
     * @param initialCount the initial value for the counter
     */
    public Counter(int initialCount) {
        this.count = initialCount;
    }

    /**
     * Increases the counter's value by the specified number.
     *
     * @param number the amount to increase the counter by
     */
    public void increase(int number) {
        this.count += number; // Add the specified number to the current count
    }

    /**
     * Decreases the counter's value by the specified number.
     *
     * @param number the amount to decrease the counter by
     */
    public void decrease(int number) {
        this.count -= number; // Subtract the specified number from the current count
    }

    /**
     * Retrieves the current value of the counter.
     *
     * @return the current count value
     */
    public int getValue() {
        return this.count; // Return the current value of the counter
    }
}
