import java.io.File;
import java.io.IOException;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * The Class GameFlow.
 *
 * @author Jonathan Fibush
 */
public class GameFlow {

    /** The runner. */
    private AnimationRunner runner;

    /** The counter of score. */
    private Counter score;

    /** The number of lives left. */
    private Counter lives;

    /** The enemies. */
    private Counter enemies;

    /** The high scores table. */
    private HighScoresTable table;

    /** The gui. */
    private GUI gui;

    /** The keyboard sensor. */
    private KeyboardSensor keyboardSensor;

    /** The filename. */
    private File filename;

    /**
     * Instantiates a new game flow.
     *
     * @param gui
     *            the gui
     * @param keyboardSensor
     *            the keyboard sensor
     * @param ar
     *            the animation runner
     * @param table
     *            the table
     * @param filename
     *            the filename
     */
    public GameFlow(GUI gui, KeyboardSensor keyboardSensor, AnimationRunner ar, HighScoresTable table, File filename) {
        this.gui = gui;
        this.runner = ar;
        this.score = new Counter();
        this.lives = new Counter();
        // setting the lives of the player
        this.lives.increase(3);
        this.enemies = new Counter();
        // setting the number of enemies
        this.enemies.increase(50);
        this.table = table;
        this.keyboardSensor = keyboardSensor;
        this.filename = filename;
    }

    /**
     * Runs the game.
     */
    public void runGame() {
        // setting the initial velocity of the enemies
        Velocity velocity = new Velocity(55, 0);
        // indicates the current battle number
        int battleNumber = 1;
        Game game = null;
        // indicates if its the first run of the game
        boolean isFirstRun = true;
        // running the game until the player ultimately loses - when there're no more
        // lives
        while (this.lives.getValue() > 0) {
            if (this.enemies.getValue() == 0 || isFirstRun) {
                // increasing the velocity of the enemies to make each turn more challenging
                // than the previous one
                velocity = new Velocity(velocity.getdx() * 1.25, 0);
                // if the player destroyed all the enemies in the current turn
                if (this.enemies.getValue() == 0) {
                    this.enemies.increase(50);
                    battleNumber++;
                }
                // creating a new game
                game = new Game(this.keyboardSensor, this.runner, this.score, this.lives, this.enemies, velocity,
                        battleNumber);
                // initializing the game
                game.initialize();
                isFirstRun = false;
            }
            // playing one turn of the game
            game.playOneTurn();
        }
        // updating the highscores table if needed and saving it to the file
        if (this.table.getRank(this.score.getValue()) <= this.table.size()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.table.add(new ScoreInfo(name, this.score.getValue()));
            try {
                table.save(filename);
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        // running the end screen
        this.runner.run(
                new KeyPressStoppableAnimation(this.keyboardSensor, "space", new EndScreen(this.score.getValue())));
        // running the high scores screen
        this.runner
                .run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", new HighScoresAnimation(this.table)));
    }

}
