import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Class Game.
 *
 * @author Jonathan Fibush
 */
public class Game implements Animation {

    /** The x frame. */
    private int xFrame = 800;

    /** The y frame. */
    private int yFrame = 600;

    /** The animation runner. */
    private AnimationRunner runner;

    /** Indicates that the game should continue running. */
    private boolean running;

    /** The sprites collection. */
    private SpriteCollection sprites = new SpriteCollection();

    /** The game environment. */
    private GameEnvironment environment = new GameEnvironment();

    /** The counter of enemies. */
    private Counter enemiesCounter = new Counter();

    /** The counter of score. */
    private Counter score = new Counter();

    /** The number of lives left. */
    private Counter lives = new Counter();

    /** The keyboardsensor. */
    private KeyboardSensor keyboardSensor;

    /** The spaceship. */
    private Spaceship spaceship;

    /** The enemies formation. */
    private EnemiesFormation enemiesFormation;

    /** The original velocity of the enemies. */
    private Velocity originalVelocity;

    /** The shots that are currently presented on the screen. */
    private List<Shot> onScreenShots;

    /** The blocks. */
    private List<Block> blocks;

    /** The block remover. */
    private HitRemover blockRemover;

    /** The enemy remover. */
    private HitRemover enemyRemover;

    /** The shot remover. */
    private HitRemover shotRemover;

    /** The last time the spaceship shot. */
    private long lastTimeSpaceshipShot;

    /** The last time an enemy shot. */
    private long lastTimeEnemyShot;

    /** The battle number. */
    private int battleNumber;

    /**
     * The current accumulative duration in which the pause screen was presented
     * until the spaceship shoots.
     */
    private long durationPauseSpaceship = 0;

    /**
     * The current accumulative duration in which the pause screen was presented
     * until an enemy shoots.
     */
    private long durationPauseEnemy = 0;

    /** The dt. */
    private double dt;

    /**
     * Instantiates a new game.
     *
     * @param keyboardSensor
     *            the keyboard sensor
     * @param runner
     *            the animation runner
     * @param score
     *            the score
     * @param lives
     *            the lives left
     * @param enemiesCounter
     *            the enemies counter
     * @param originalVelocity
     *            the original velocity
     * @param battleNumber
     *            the battle number
     */
    public Game(KeyboardSensor keyboardSensor, AnimationRunner runner, Counter score, Counter lives,
            Counter enemiesCounter, Velocity originalVelocity, int battleNumber) {
        this.keyboardSensor = keyboardSensor;
        this.runner = runner;
        this.score = score;
        this.lives = lives;
        this.enemiesCounter = enemiesCounter;
        this.onScreenShots = new ArrayList<>();
        this.blockRemover = new HitRemover(this, new Counter(), "block");
        this.originalVelocity = originalVelocity;
        this.enemyRemover = new HitRemover(this, this.enemiesCounter, "enemy");
        this.shotRemover = new HitRemover(this, new Counter(), "shot");
        this.battleNumber = battleNumber;
        this.dt = 1 / 60.0;
    }

    /**
     * Adds the collidable to the game environment.
     *
     * @param c
     *            the collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds the sprite to the sprite collection.
     *
     * @param s
     *            the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes a new game: creates the blocks, enemies and spaceship, and adds
     * them to the game.
     */
    public void initialize() {
        // creating the blocks
        createBlocks();
        // setting the background
        this.addSprite(new Sprite() {

            @Override
            public void timePassed(double dtParam) {
            }

            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.black);
                d.fillRectangle(0, 0, xFrame, yFrame);

            }
        });
        // the x value of the left corner of the spaceship
        double xStartSpaceship = this.xFrame / 2 - 30;
        // creating the spaceship
        this.spaceship = new Spaceship(new Rectangle(new Point(xStartSpaceship, 560), 60, 18), this.xFrame,
                this.keyboardSensor, 300);
        this.spaceship.addToGame(this);
        // the horizontal borders, represented by blocks outside the screen that detect
        // shots which exit the screen
        Block bottom = new Block(new Rectangle(new Point(0, this.yFrame), this.xFrame, 1));
        Block top = new Block(new Rectangle(new Point(0, 0), this.xFrame, 20));
        bottom.addToGame(this);
        top.addToGame(this);
        bottom.addHitListener(this.shotRemover);
        top.addHitListener(this.shotRemover);
        // creating the scoreTrackingListener
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        // creating the scoreIndicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        addSprite(scoreIndicator);
        // creating the livesIndicator
        LivesIndicator livesIndicator = new LivesIndicator(this.lives);
        addSprite(livesIndicator);
        // creating the levelIndicator
        LevelIndicator levelIndicator = new LevelIndicator("Battle no. " + this.battleNumber);
        addSprite(levelIndicator);
        // adding all the blocks to the game
        for (Block block : this.blocks) {
            block.addToGame(this);
            block.addHitListener(this.blockRemover);
        }
        this.enemiesFormation = new EnemiesFormation(this.originalVelocity, scoreTrackingListener, enemyRemover, this,
                this.dt);
        Image enemyImage = null;
        InputStream is = null;
        // loading the enemy image
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy.png");
            enemyImage = ImageIO.read(is);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException exception) {
                    throw new RuntimeException(exception.getMessage());
                }
            }
        }
        // setting the image
        this.enemiesFormation.setImage(enemyImage);
    }

    /**
     * Plays one turn in the game. The function returns when either the player loses
     * a life or destroys all the enemies
     */
    public void playOneTurn() {
        // starting the countdown
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // running the turn
        this.runner.run(this);
        // reseting the game
        this.resetGame();

    }

    /**
     * Removes the collidable from the environment.
     *
     * @param c
     *            the collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes the sprite from the collection.
     *
     * @param s
     *            the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    @Override
    public void doOneFrame(DrawSurface d, double dtParam) {
        // if the spaceship was hit by a shot
        if (this.spaceship.isSpaceshipHit()) {
            this.lives.decrease(1);
            this.running = false;
            return;
        }
        // if the enemies reached the shields level
        if (this.enemiesFormation.didReachShields()) {
            this.lives.decrease(1);
            this.running = false;
            return;
        }
        // if all the enemies were destroyed
        if (this.enemiesCounter.getValue() == 0) {
            this.running = false;
            return;
        }
        // pause
        if (this.keyboardSensor.isPressed("p")) {
            long startCount = System.currentTimeMillis();
            // running the pause screen
            this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", new PauseScreen()));
            // updating the following durations
            this.durationPauseSpaceship += System.currentTimeMillis() - startCount;
            this.durationPauseEnemy += System.currentTimeMillis() - startCount;
        } else {
            if (this.keyboardSensor.isPressed("space")) {
                // creating a new shot of the spaceship if enough time has passed since the last
                // shot
                // creation
                if (System.currentTimeMillis() - this.lastTimeSpaceshipShot - this.durationPauseSpaceship >= 350
                        || this.lastTimeSpaceshipShot == 0) {
                    Point upperLeft = this.spaceship.getCollisionRectangle().getUpperLeft();
                    Point center = new Point(upperLeft.getX() + 20, upperLeft.getY() - 3);
                    Shot shot = new Shot(center, this.environment, true);
                    shot.addToGame(this);
                    // updating the list
                    this.onScreenShots.add(shot);
                    // updating the time
                    this.lastTimeSpaceshipShot = System.currentTimeMillis();
                    // reseting the duration
                    this.durationPauseSpaceship = 0;
                }

            }
            // creating a new shot of an enemy only if enough time has passed since the last
            // shot creation
            if (System.currentTimeMillis() - this.lastTimeEnemyShot - this.durationPauseEnemy >= 500
                    || this.lastTimeEnemyShot == 0) {
                Point center = this.enemiesFormation.generateShotPosition();
                Shot shot = new Shot(center, this.environment, false);
                shot.addToGame(this);
                // updating the list
                this.onScreenShots.add(shot);
                // updating the time
                this.lastTimeEnemyShot = System.currentTimeMillis();
                // reseting the duration
                this.durationPauseEnemy = 0;
            }
        }
        this.enemiesFormation.moveOneStep(this.dt);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(this.dt);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Resets the game for the upcoming turn.
     */
    public void resetGame() {
        // reseting the enemies formation position and velocity
        this.enemiesFormation.resetFormationPoisition();
        this.enemiesFormation.resetVelocity();
        // removing the shots that still appear on the screen
        for (int i = 0; i < this.onScreenShots.size(); i++) {
            removeSprite(this.onScreenShots.get(i));
        }
        this.onScreenShots.clear();
        // reseting the times
        this.lastTimeSpaceshipShot = 0;
        this.lastTimeEnemyShot = 0;
    }

    /**
     * Gets the lives left.
     *
     * @return the lives
     */
    public Counter getLives() {
        return this.lives;
    }

    /**
     * Gets the enemies counter.
     *
     * @return the enemies counter
     */
    public Counter getEnemiesCounter() {
        return this.enemiesCounter;
    }

    /**
     * Creates the small blocks that constitute 3 big groups, representing the 3
     * shields.
     */
    public void createBlocks() {
        int xStart = 60;
        int yStart = 500;
        int gap = 270;
        int width = 7;
        int height = 5;
        this.blocks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                // creating the blocks and adding them to the list
                this.blocks.add(
                        new Block(new Rectangle(new Point(xStart + j * width, yStart + i * height), width, height)));
                this.blocks.add(new Block(
                        new Rectangle(new Point(xStart + gap + j * width, yStart + i * height), width, height)));
                this.blocks.add(new Block(
                        new Rectangle(new Point(xStart + 2 * gap + j * width, yStart + i * height), width, height)));
            }
        }
    }

    /**
     * Removes the enemy from the game.
     *
     * @param enemy
     *            the enemy
     */
    public void removeEnemy(Enemy enemy) {
        this.enemiesFormation.removeEnemy(enemy);
    }

    /**
     * Removes the shot from the shot list.
     *
     * @param shot
     *            the shot
     */
    public void removeFromShotList(Shot shot) {
        this.onScreenShots.remove(shot);
    }
}