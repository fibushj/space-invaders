import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Class KeyPressStoppableAnimation.
 *
 * @author Jonathan Fibush
 */
public class KeyPressStoppableAnimation implements Animation {

    /** The keyboard sensor. */
    private KeyboardSensor keyboardSensor;

    /** The key. */
    private String key;

    /** The animation. */
    private Animation animation;

    /** Stop. */
    private boolean stop;

    /** The key is already pressed. */
    private boolean isAlreadyPressed;

    /** The is first time. */
    private boolean isFirstTime;

    /**
     * Instantiates a new key press stoppable animation.
     *
     * @param keyboardSensor
     *            the keyboard sensor
     * @param key
     *            the key
     * @param animation
     *            the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboardSensor, String key, Animation animation) {
        this.keyboardSensor = keyboardSensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = false;
        this.isFirstTime = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        Boolean isPressedNow = this.keyboardSensor.isPressed(this.key);
        if (this.isFirstTime) {
            if (isPressedNow) {
                this.isAlreadyPressed = true;
            } else {
                this.isAlreadyPressed = false;
            }
            this.isFirstTime = false;
        }
        if (isPressedNow) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}