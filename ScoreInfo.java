import java.io.Serializable;

/**
 * The Class ScoreInfo.
 *
 * @author Jonathan Fibush
 */
public class ScoreInfo implements Serializable {

    /** The name. */
    private String name;

    /** The score. */
    private int score;

    /**
     * Instantiates a new score info.
     *
     * @param name
     *            the name
     * @param score
     *            the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }
}