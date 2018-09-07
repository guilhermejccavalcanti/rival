package net.recommenders.rival.split.splitter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.recommenders.rival.core.DataModelFactory;
import net.recommenders.rival.core.DataModelIF;
import net.recommenders.rival.core.TemporalDataModel;
import net.recommenders.rival.core.TemporalDataModelIF;

/**
 * Class that splits a dataset using a cross validation technique (every
 * interaction in the data only appears once in each test split).
 *
 * @author <a href="http://github.com/abellogin">Alejandro</a>
 *
 * @param <U> type of users
 * @param <I> type of items
 */
public class CrossValidationSplitter<U extends java.lang.Object, I extends java.lang.Object> implements Splitter<U, I> {
  /**
     * The number of folds that the data will be split into.
     */
  protected int nFolds;

  /**
     * The flag that indicates if the split should be done in a per user basis.
     */
  protected boolean perUser;

  /**
     * An instance of a Random class.
     */
  protected Random rnd;

  /**
     * Constructor.
     *
     * @param nFold number of folds that the data will be split into
     * @param perUsers flag to do the split in a per user basis
     * @param seed value to initialize a Random class
     */
  public CrossValidationSplitter(final int nFold, final boolean perUsers, final long seed) {
    this.nFolds = nFold;
    this.perUser = perUsers;
    rnd = new Random(seed);
  }

  /**
     * {@inheritDoc}
     */
  @Override public DataModelIF<U, I>[] split(final DataModelIF<U, I> data) {
    @SuppressWarnings(value = { "unchecked" }) final DataModelIF<U, I>[] splits = new DataModelIF[2 * nFolds];
    for (int i = 0; i < nFolds; i++) {
      splits[2 * i] = DataModelFactory.getDefaultModel();
      splits[2 * i + 1] = DataModelFactory.getDefaultModel();
    }
    if (perUser) {
      int n = 0;
      for (U user : data.getUsers()) {
        List<I> items = new ArrayList<>();
        for (I i : data.getUserItems(user)) {
          items.add(i);
        }
        Collections.shuffle(items, rnd);
        for (I item : items) {
          Double pref = data.getUserItemPreference(user, item);
          int curFold = n % nFolds;
          for (int i = 0; i < nFolds; i++) {
            DataModelIF<U, I> datamodel = splits[2 * i];
            if (i == curFold) {
              datamodel = splits[2 * i + 1];
            }
            if (pref != null) {
              datamodel.addPreference(user, item, pref);
            }
          }
          n++;
        }
      }
    } else {
      List<U> users = new ArrayList<>();
      for (U u : data.getUsers()) {
        users.add(u);
      }
      Collections.shuffle(users, rnd);
      int n = 0;
      for (U user : users) {
        List<I> items = new ArrayList<>();
        for (I i : data.getUserItems(user)) {
          items.add(i);
        }
        Collections.shuffle(items, rnd);
        for (I item : items) {
          Double pref = data.getUserItemPreference(user, item);
          int curFold = n % nFolds;
          for (int i = 0; i < nFolds; i++) {
            DataModelIF<U, I> datamodel = splits[2 * i];
            if (i == curFold) {
              datamodel = splits[2 * i + 1];
            }
            if (pref != null) {
              datamodel.addPreference(user, item, pref);
            }
          }
          n++;
        }
      }
    }
    return splits;
  }

  /**
     * {@inheritDoc}
     */
  @Override public TemporalDataModelIF<U, I>[] split(final TemporalDataModelIF<U, I> data) {
    @SuppressWarnings(value = { "unchecked" }) final TemporalDataModelIF<U, I>[] splits = new TemporalDataModelIF[2 * nFolds];
    for (int i = 0; i < nFolds; i++) {
      splits[2 * i] = DataModelFactory.getDefaultTemporalModel();
      splits[2 * i + 1] = DataModelFactory.getDefaultTemporalModel();
    }
    if (perUser) {
      int n = 0;
      for (U user : data.getUsers()) {
        List<I> items = new ArrayList<>();
        for (I i : data.getUserItems(user)) {
          items.add(i);
        }
        Collections.shuffle(items, rnd);
        for (I item : items) {
          Double pref = data.getUserItemPreference(user, item);
          Iterable<Long> time = data.getUserItemTimestamps(user, item);
          int curFold = n % nFolds;
          for (int i = 0; i < nFolds; i++) {
            TemporalDataModelIF<U, I> datamodel = splits[2 * i];
            if (i == curFold) {
              datamodel = splits[2 * i + 1];
            }
            if (pref != null) {
              datamodel.addPreference(user, item, pref);
            }
            if (time != null) {
              for (Long t : time) {
                datamodel.addTimestamp(user, item, t);
              }
            }
          }
          n++;
        }
      }
    } else {
      List<U> users = new ArrayList<>();
      for (U u : data.getUsers()) {
        users.add(u);
      }
      Collections.shuffle(users, rnd);
      int n = 0;
      for (U user : users) {
        List<I> items = new ArrayList<>();
        for (I i : data.getUserItems(user)) {
          items.add(i);
        }
        Collections.shuffle(items, rnd);
        for (I item : items) {
          Double pref = data.getUserItemPreference(user, item);
          Iterable<Long> time = data.getUserItemTimestamps(user, item);
          int curFold = n % nFolds;
          for (int i = 0; i < nFolds; i++) {
            TemporalDataModelIF<U, I> datamodel = splits[2 * i];
            if (i == curFold) {
              datamodel = splits[2 * i + 1];
            }
            if (pref != null) {
              datamodel.addPreference(user, item, pref);
            }
            if (time != null) {
              for (Long t : time) {
                datamodel.addTimestamp(user, item, t);
              }
            }
          }
          n++;
        }
      }
    }
    return splits;
  }
}