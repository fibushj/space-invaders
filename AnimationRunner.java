
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The Class AnimationRunner.
 *
 * @author Jonathan Fibush
 */
public class AnimationRunner {

    /** The gui. */
    private GUI gui;
    /** The frames per second. */
    private int framesPerSecond;
    /** The sleeper. */
    private Sleeper sleeper = new Sleeper();

    /**
     * Instantiates a new animation runner with 60 frames per second.
     *
     * @param gui the gui
     */
    public AnimationRunner(GUI gui) {
        this.framesPerSecond = 60;
        this.gui = gui;
    }

    /**
     * Runs the animation.
     *
     * @param animation
     *            the animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // the loop runs until the stopping condition of the animation
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            // doing one frame of the animation
            animation.doOneFrame(d, 1.0 / this.framesPerSecond);
            if (animation.shouldStop()) {
                break;
            }
            //showing the gui
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}