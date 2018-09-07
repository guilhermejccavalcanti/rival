package net.recommenders.rival.evaluation.strategy;
import java.util.HashSet;
import java.util.Set;
import net.recommenders.rival.core.DataModelIF;

/**
 * An evaluation strategy where only the items in the user's test are used as
 * candidates.
 *
 * @author <a href="http://github.com/abellogin">Alejandro</a>
 */
public class UserTest extends AbstractStrategy {
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
  public UserTest(final DataModelIF<Long, Long> training, final DataModelIF<Long, Long> test, final double threshold) {
    super(training, test, threshold);
  }

  /**
     * {@inheritDoc}
     */
  @Override public Set<Long> getCandidateItemsToRank(final Long user) {
    Set<Long> items = new HashSet<>();
    for (Long i : getTest().getUserItems(user)) {
      items.add(i);
    }
    return items;
  }

  /**
     * {@inheritDoc}
     */
  @Override public String toString() {
    return "UserTest_" + getThreshold();
  }
}