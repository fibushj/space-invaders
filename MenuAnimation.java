import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Class MenuAnimation.
 *
 * @author Jonathan Fibush
 *
 * @param <T>
 *            the generic type
 */
public class MenuAnimation<T> implements Menu<T> {

    /** The title. */
    private String title;

    /** The keys. */
    private List<String> keys;

    /** The messages. */
    private List<String> messages;

    /** The return values. */
    private List<T> returnValues;

    /** The background. */
    private Sprite background = null;

    /** The action. */
    private T action;

    /** The keyboard sensor. */
    private KeyboardSensor keyboardSensor;

    /**
     * Instantiates a new menu animation.
     *
     * @param title
     *            the title
     * @param keyboardSensor
     *            the keyboard sensor
     * @param animationRunner
     *            the animation runner
     */
    public MenuAnimation(String title, KeyboardSensor keyboardSensor, AnimationRunner animationRunner) {
        this.title = title;
        this.keyboardSensor = keyboardSensor;
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        this.returnValues = new ArrayList<T>();
        this.action = null;

    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnValues.add(returnVal);
    }

    @Override
    public boolean shouldStop() {
        if (this.action == null) {
            return false;
        }
        return true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(Color.lightGray);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
        d.setColor(Color.YELLOW);
        d.drawText(100, 40, this.title, 30);
        d.setColor(Color.GREEN);
        // printing the menu's content
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawText(150, 100 + 40 * i, "(" + this.keys.get(i) + ")   " + this.messages.get(i), 20);
        }
        // checking if one of the possible keys is pressed in order to advance from the
        // menu
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keyboardSensor.isPressed(this.keys.get(i))) {
                this.action = this.returnValues.get(i);
                break;
            }
        }
    }

    @Override
    public T getStatus() {
        return this.action;
    }

    /**
     * Resets the chosen action.
     */
    public void resetAction() {
        this.action = null;
    }
}
