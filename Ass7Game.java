import java.io.File;
import java.io.IOException;

import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * The Class Ass7Game.
 *
 * @author Jonathan Fibush
 */
public class Ass7Game {

    /**
     * The main method.
     *
     * @param args array of strings
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {

        /** The gui. */
        GUI gui = new GUI("Arkanoid", 800, 600);
        //declaring and instansiating variables
        AnimationRunner animationRunner = new AnimationRunner(gui);
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        File filename = new File("highscores");
        // loading the table from the file
        HighScoresTable table = HighScoresTable.loadFromFile(filename);
        if (table.getHighScores().isEmpty()) {
            try {
                table.save(filename);
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }


        // the menu
        MenuAnimation<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>("Arkanoid", keyboardSensor, animationRunner);
        //adding the selection of starting a new game
        mainMenu.addSelection("s", "New Game", new Task<Void>() {
            @Override
            public Void run() {
                GameFlow gameFlow = new GameFlow(gui, keyboardSensor, animationRunner, table, filename);
                gameFlow.runGame();
                return null;
            }
        });
        // adding the selection of showing the high scores
        mainMenu.addSelection("h", "High Scores", new Task<Void>() {
            @Override
            public Void run() {
                animationRunner
                        .run(new KeyPressStoppableAnimation(keyboardSensor, "space", new HighScoresAnimation(table)));
                return null;
            }
        });
        // adding the selection of quitting the game
        mainMenu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                try {
                    //saving the table
                    table.save(filename);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
                System.exit(0);
                return null;
            }
        });

        while (true) {
            //presenting the menu with the possible selections
            animationRunner.run(mainMenu);
            //running the chosen selection
            mainMenu.getStatus().run();
            //reseting the chosen selection
            mainMenu.resetAction();
        }
    }

}
