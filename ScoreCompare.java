import java.util.Comparator;

/**
 * The Class ScoreCompare.
 *
 * @author Jonathan Fibush
 */
public class ScoreCompare implements Comparator<ScoreInfo> {

    @Override
    public int compare(ScoreInfo info1, ScoreInfo info2) {
        if (info1.getScore() > info2.getScore()) {
            return -1;
        }
        if (info1.getScore() < info2.getScore()) {
            return 1;
        }
        return 0;
    }
}
