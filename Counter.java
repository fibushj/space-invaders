/**
 * The Class Counter.
 *
 * @author Jonathan Fibush
 */
public class Counter {

    /** The count. */
    private int count = 0;

    /**
     * Add number to current count.
     *
     * @param number
     *            the number
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number
     *            the number
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Get current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.count;
    }
}