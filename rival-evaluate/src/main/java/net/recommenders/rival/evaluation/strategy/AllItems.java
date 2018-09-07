package net.recommenders.rival.evaluation.strategy;
import java.util.Set;
import net.recommenders.rival.core.DataModelIF;

/**
 * An evaluation strategy where all the items are used as candidates.
 *
 * @author <a href="http://github.com/abellogin">Alejandro</a>
 */
public class AllItems extends AbstractStrategy {
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
  public AllItems(final DataModelIF<Long, Long> training, final DataModelIF<Long, Long> test, final double threshold) {
    super(training, test, threshold);
  }

  /**
     * {@inheritDoc}
     */
  @Override public Set<Long> getCandidateItemsToRank(final Long user) {
    final Set<Long> items = getModelTrainingDifference(getTraining(), user);
    items.addAll(getModelTrainingDifference(getTest(), user));
    return items;
  }

  /**
     * {@inheritDoc}
     */
  @Override public String toString() {
    return "AllItems_" + getThreshold();
  }
}