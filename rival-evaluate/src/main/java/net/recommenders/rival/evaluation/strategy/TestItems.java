package net.recommenders.rival.evaluation.strategy;
import java.util.Set;
import net.recommenders.rival.core.DataModelIF;

/**
 * An evaluation strategy where only the test items are used as candidates.
 *
 * @author <a href="http://github.com/abellogin">Alejandro</a>
 */
public class TestItems extends AbstractStrategy {
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
  public TestItems(final DataModelIF<Long, Long> training, final DataModelIF<Long, Long> test, final double threshold) {
    super(training, test, threshold);
  }

  /**
     * {@inheritDoc}
     */
  @Override public Set<Long> getCandidateItemsToRank(final Long user) {
    return getModelTrainingDifference(getTest(), user);
  }

  /**
     * {@inheritDoc}
     */
  @Override public String toString() {
    return "TestItems_" + getThreshold();
  }
}