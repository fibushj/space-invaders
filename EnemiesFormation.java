import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Class EnemiesFormation.
 *
 * @author Jonathan Fibush
 */
public class EnemiesFormation {

    /** The enemies. */
    private Enemy[][] enemies;

    /** The velocity. */
    private Velocity velocity;

    /** The original velocity. */
    private Velocity originalVelocity;

    /** The velocity considering the dt. */
    private Velocity velocityWithDt;

    /** The rows. */
    private final int rows = 5;

    /** The columns. */
    private final int columns = 10;

    /** The most upper left x. */
    private final int mostUpperLeftX = 60;

    /** The most upper left y. */
    private final int mostUpperLeftY = 60;

    /** The enemy width. */
    private final int enemyWidth = 40;

    /** The enemy height. */
    private final int enemyHeight = 30;

    /** The gap between rows and between columns. */
    private final int gap = 10;

    /** The x frame. */
    private final int xFrame = 800;

    /** The y value of shields. */
    private final int yShields = 485;

    /** The dt. */
    private double dt;

    /**
     * Instantiates a new enemies formation.
     *
     * @param velocity
     *            the velocity
     * @param scoreTrackingListener
     *            the score tracking listener
     * @param enemyRemover
     *            the enemy remover
     * @param game
     *            the game
     * @param dt
     *            the dt
     */
    public EnemiesFormation(Velocity velocity, ScoreTrackingListener scoreTrackingListener, HitListener enemyRemover,
            Game game, double dt) {
        this.dt = dt;
        this.velocity = velocity;
        this.originalVelocity = this.velocity;
        this.velocityWithDt = new Velocity(velocity.getdx() * dt, 0);
        this.enemies = new Enemy[rows][columns];
        // creating the enemies
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // the position
                int x = mostUpperLeftX + j * (enemyWidth + gap);
                int y = mostUpperLeftY + i * (enemyHeight + gap);
                enemies[i][j] = new Enemy(new Point(x, y), this.velocity);
                enemies[i][j].addHitListener(scoreTrackingListener);
                enemies[i][j].addHitListener(enemyRemover);
                enemies[i][j].addToGame(game);
            }
        }
    }

    /**
     * Moves the formation one step.
     *
     * @param dtParam
     *            the dt
     */
    public void moveOneStep(double dtParam) {
        if (shouldChangeDirection()) {
            // updating the velocity of the formation
            this.velocity = new Velocity(-1.1 * this.velocity.getdx(), 0);
            this.velocityWithDt = new Velocity(this.velocity.getdx() * this.dt, 0);
            // updating the position of the enemies in the formation
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Enemy currEnemy = this.enemies[i][j];
                    if (currEnemy != null) {
                        currEnemy.setVelocity(new Velocity(this.velocity.getdx(), 0));
                        Point newUpperLeft = currEnemy.getCollisionRectangle().getUpperLeft();
                        newUpperLeft.setY(newUpperLeft.getY() + enemyHeight);
                        currEnemy.setPosition(newUpperLeft);
                    }
                }
            }
        }
    }

    /**
     * Indicates if the formation should change its direction.
     *
     * @return true, if should, false otherwise
     */
    public boolean shouldChangeDirection() {
        // if the formation is heading leftward
        if (this.velocity.getdx() < 0) {
            Point mostLeft = new Point(mostLeftCollumnPosition(), 0);
            mostLeft = this.velocityWithDt.applyToPoint(mostLeft);
            if (mostLeft.getX() < 0) {
                return true;
            }
            // if the formation is heading rightward
        } else {
            Point mostRight = new Point(mostRightCollumnPosition(), 0);
            mostRight = this.velocityWithDt.applyToPoint(mostRight);
            if (mostRight.getX() > xFrame) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the enemy from the formation.
     *
     * @param enemy
     *            the enemy
     */
    public void removeEnemy(Enemy enemy) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (this.enemies[i][j] == enemy) {
                    this.enemies[i][j] = null;
                }
            }
        }
    }

    /**
     * Indicates if the formation has reached the shields.
     *
     * @return true, if reached, false otherwise
     */
    public boolean didReachShields() {
        if (lowestRowPosition() >= yShields) {
            return true;
        }
        return false;
    }

    /**
     * Returns the lowest row position (y value).
     *
     * @return the y value
     */
    public int lowestRowPosition() {
        int lowest = 0;
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (this.enemies[i][j] != null) {
                    return (int) (this.enemies[i][j].getCollisionRectangle().getUpperLeft().getY()) + enemyHeight;
                }
            }
        }
        return lowest;
    }

    /**
     * Returns the most left column position (x value).
     *
     * @return the x value
     */
    public int mostLeftCollumnPosition() {
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                if (this.enemies[i][j] != null) {
                    return (int) (this.enemies[i][j].getCollisionRectangle().getUpperLeft().getX());
                }
            }
        }
        return xFrame;
    }

    /**
     * Returns the most right column position (x value).
     *
     * @return the x value
     */
    public int mostRightCollumnPosition() {
        int mostRight = 0;
        for (int j = columns - 1; j >= 0; j--) {
            for (int i = 0; i < rows; i++) {
                if (this.enemies[i][j] != null) {
                    return (int) (this.enemies[i][j].getCollisionRectangle().getUpperLeft().getX()) + enemyWidth;
                }
            }
        }
        return mostRight;
    }

    /**
     * Resets the formation's position.
     */
    public void resetFormationPoisition() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (enemies[i][j] != null) {
                    int x = mostUpperLeftX + j * (enemyWidth + gap);
                    int y = mostUpperLeftY + i * (enemyHeight + gap);
                    enemies[i][j].setPosition(new Point(x, y));
                }
            }
        }
    }

    /**
     * Resets the formation's velocity.
     */
    public void resetVelocity() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (enemies[i][j] != null) {
                    enemies[i][j].setVelocity(this.originalVelocity);
                    this.velocity = this.originalVelocity;
                    this.velocityWithDt = new Velocity(this.velocity.getdx() * this.dt, 0);
                }
            }
        }
    }

    /**
     * Returns a list of the non empty columns.
     *
     * @return the list
     */
    public List<Integer> nonEmptyCollumns() {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                if (this.enemies[i][j] != null) {
                    list.add(j);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Generates shot position.
     *
     * @return the point
     */
    public Point generateShotPosition() {
        List<Integer> nonEmptyCollumns = nonEmptyCollumns();
        if (nonEmptyCollumns == null) {
            return null;
        }
        Random randomizer = new Random();
        // choosing random column among the non-empty ones
        int randomCollumn = nonEmptyCollumns.get(randomizer.nextInt(nonEmptyCollumns.size()));
        Enemy shootingEnemy = null;
        // finding the lowest enemy in the chosen column that will shoot
        for (int i = rows - 1; i >= 0; i--) {
            if (enemies[i][randomCollumn] != null) {
                shootingEnemy = enemies[i][randomCollumn];
                break;
            }
        }
        return new Point(shootingEnemy.getCollisionRectangle().getDownRight().getX() - 20,
                shootingEnemy.getCollisionRectangle().getDownRight().getY() + 10);
    }

    /**
     * Sets the image of the enemy.
     *
     * @param enemyImage
     *            the image
     */
    public void setImage(Image enemyImage) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                enemies[i][j].setImage(enemyImage);
            }
        }
    }
}
