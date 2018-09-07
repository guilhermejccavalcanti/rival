package net.recommenders.rival.evaluation.strategy;
import java.util.Set;
import net.recommenders.rival.core.DataModelIF;

/**
 * An evaluation strategy where only the items in training are used as
 * candidates.
 *
 * @author <a href="http://github.com/abellogin">Alejandro</a>
 */
public class TrainItems extends AbstractStrategy {
  /**
     * Default constructor.
     *
     * @see
     * AbstractStrategy#AbstractStrategy(net.recommenders.rival.core.DataModel,
     * net.recommenders.rival.core.DataModel, double)
     *
     * @param training The training set.
     * @param test The test set.
     * @param threshold The relevance threshold.
     */
  public TrainItems(final DataModelIF<Long, Long> training, final DataModelIF<Long, Long> test, final double threshold) {
    super(training, test, threshold);
  }

  /**
     * {@inheritDoc}
     */
  @Override public Set<Long> getCandidateItemsToRank(final Long user) {
    return getModelTrainingDifference(getTraining(), user);
  }

  /**
     * {@inheritDoc}
     */
  @Override public String toString() {
    return "TrainItems_" + getThreshold();
  }
}