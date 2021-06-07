import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The Class HighScoresAnimation.
 *
 * @author Jonathan Fibush
 */
public class HighScoresAnimation implements Animation {

    /** The scores. */
    private List<ScoreInfo> scores;

    /** Indicates if the animation should stop. */
    private boolean stop = false;

    /**
     * Instantiates a new high scores animation.
     *
     * @param scores
     *            the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores.getHighScores();
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.lightGray);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // printing the info
        d.setColor(Color.yellow);
        d.drawText(100, 40, "High Scores", 30);
        d.setColor(Color.green);
        d.drawText(200, 150, "Player Name", 25);
        d.drawText(400, 150, "Score", 25);
        d.setColor(Color.orange);
        // showing the highscores
        for (int i = 0; i < this.scores.size(); i++) {
            d.drawText(200, 200 + 40 * i, scores.get(i).getName(), 20);
            d.drawText(400, 200 + 40 * i, String.valueOf(this.scores.get(i).getScore()), 20);
        }
        d.setColor(Color.black);
        d.drawText(200, 450, "Press space to continue", 30);

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}