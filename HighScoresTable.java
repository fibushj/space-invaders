import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class HighScoresTable.
 *
 * @author Jonathan Fibush
 */
public class HighScoresTable implements Serializable {

    /** The size. */
    private int size;

    /** The Constant DEFAULT_SIZE of the table. */
    private static final int DEFAULT_SIZE = 5;

    /** The scores. */
    private List<ScoreInfo> scores;

    /**
     * Instantiates a new high scores table.
     *
     * @param size
     *            the size
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<ScoreInfo>();
        this.size = size;
    }

    /**
     * Adds the score to the list.
     *
     * @param score
     *            the score
     */
    public void add(ScoreInfo score) {
        if (this.scores.size() < this.size) {
            this.scores.add(score);
        } else if (score.getScore() > scores.get(this.size - 1).getScore()) {
            this.scores.remove(this.size - 1);
            this.scores.add(score);
        }
        Collections.sort(this.scores, new ScoreCompare());
    }

    /**
     * Returns the size of the list.
     *
     * @return the size
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets the high scores list.
     *
     * @return the highscores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Gets the rank.
     *
     * @param score
     *            the score
     * @return the rank
     */
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.scores.size(); i++) {
            if (score > this.scores.get(i).getScore()) {
                return i + 1;
            }
        }
        return i + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        for (int i = 0; i < this.scores.size(); i++) {
            this.scores.remove(i);
        }
    }

    /**
     * Loads the table from file.
     *
     * @param filename
     *            the filename
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void load(File filename) throws IOException {
        this.clear();
        ObjectInputStream ois = null;
        HighScoresTable newScores = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            newScores = (HighScoresTable) ois.readObject();
            //setting the scores and the size
            this.scores = newScores.getHighScores();
            this.size = newScores.size;
        } catch (IOException exception) {
            throw new IOException(exception.getMessage());
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception.getMessage());

        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException exception) {
                throw exception;
            }
        }
        System.out.println("File was loaded successfully.");
    }

    /**
     * Saves the table.
     *
     * @param filename
     *            the filename
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
        } catch (IOException exception) {
            throw exception;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException exception) {
                throw exception;
            }
        }
        System.out.println("File was saved successfully.");
    }

    /**
     * Loads the table from file.
     *
     * @param filename
     *            the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(DEFAULT_SIZE);
        try {
            table.load(filename);
        } catch (IOException exception) {
            return table;
        }
        return table;
    }
}
