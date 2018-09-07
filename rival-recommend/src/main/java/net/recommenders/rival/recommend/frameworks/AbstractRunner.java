package net.recommenders.rival.recommend.frameworks;
import java.io.File;
import java.util.Map.Entry;
import java.util.Properties;
import net.recommenders.rival.core.DataModelIF;
import net.recommenders.rival.core.TemporalDataModelIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract recommender runner.
 *
 * @author <a href="http://github.com/alansaid">Alan</a>
 *
 * @param <U> generic type for users
 * @param <I> generic type for items
 */
public abstract class AbstractRunner<U extends java.lang.Object, I extends java.lang.Object> {
  public enum RUN_OPTIONS {
    RETURN_RECS,
    OUTPUT_RECS,
    RETURN_AND_OUTPUT_RECS
  }

  /**
     * Logger.
     */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractRunner.class);

  /**
     * Default number of iterations.
     */
  public static final int DEFAULT_ITERATIONS = 50;

  /**
     * The properties.
     */
  private Properties properties;

  /**
     * The file name where the properties live.
     */
  private String fileName;

  /**
     * The path where output is written to.
     */
  private String path;

  /**
     * True if this recommender has already been issued and output files exist.
     */
  private boolean alreadyRecommended;

  /**
     * Default constructor.
     *
     * @param props The properties.
     */
  public AbstractRunner(final Properties props) {
    this.properties = new Properties();
    for (Entry e : props.entrySet()) {
      properties.put(e.getKey(), e.getValue());
    }
    setFileName();
    String filePath = properties.getProperty(RecommendationRunner.OUTPUT, "") + "/" + fileName;
    alreadyRecommended = new File(filePath).exists();
    if (alreadyRecommended) {
      System.out.println("File exists: " + filePath);
    }
    path = properties.getProperty(RecommendationRunner.OUTPUT, "");
  }

  /**
     * Create the file name of the output file.
     */
  public void setFileName() {
    String type = "";
    if (properties.containsKey(RecommendationRunner.FACTORIZER) || properties.containsKey(RecommendationRunner.SIMILARITY)) {
      if (properties.containsKey(RecommendationRunner.FACTORIZER)) {
        type = properties.getProperty(RecommendationRunner.FACTORIZER);
      } else {
        type = properties.getProperty(RecommendationRunner.SIMILARITY);
      }
      type = type.substring(type.lastIndexOf(".") + 1) + ".";
    }
    String num = "";
    if (properties.containsKey(RecommendationRunner.FACTORS) || properties.containsKey(RecommendationRunner.NEIGHBORHOOD)) {
      if (properties.containsKey(RecommendationRunner.FACTORS)) {
        num = properties.getProperty(RecommendationRunner.FACTORS);
      } else {
        num = properties.getProperty(RecommendationRunner.NEIGHBORHOOD);
      }
      num += ".";
    }
    String trainingSet = properties.getProperty(RecommendationRunner.TRAINING_SET);
    trainingSet = trainingSet.substring(trainingSet.lastIndexOf("/") + 1, trainingSet.lastIndexOf("_train"));
    fileName = trainingSet + "." + properties.getProperty(RecommendationRunner.FRAMEWORK) + "." + properties.getProperty(RecommendationRunner.RECOMMENDER).substring(properties.getProperty(RecommendationRunner.RECOMMENDER).lastIndexOf(".") + 1) + "." + type + num + "tsv";
    System.out.println(fileName);
  }

  /**
     * Get file name with canonical path.
     *
     * @return the file name and path.
     */
  public String getCanonicalFileName() {
    return path + "/" + fileName + ".stats";
  }

  /**
     * Checks if recommendations have already been generated.
     *
     * @return true if recommendations have already been generated
     */
  public boolean isAlreadyRecommended() {
    return alreadyRecommended;
  }

  /**
     * Gets the properties used in this recommender.
     *
     * @return the property mapping
     */
  protected Properties getProperties() {
    return properties;
  }

  /**
     * Gets the file name. See {@link #setFileName()}.
     *
     * @return the file name
     */
  protected String getFileName() {
    return fileName;
  }

  /**
     * Gets the path.
     *
     * @return the path
     */
  protected String getPath() {
    return path;
  }

  /**
     * Runs the recommender. Training and test models will be read from file.
     *
     * @param opts options to run this recommender. See {@link RUN_OPTIONS}
     * enum.
     * @return see {@link #run(net.recommenders.rival.recommend.frameworks.AbstractRunner.RUN_OPTIONS, net.recommenders.rival.core.DataModel, net.recommenders.rival.core.DataModel)}
     * @throws Exception when the recommender cannot be run. See implementations
     * for more information on possible exceptions.
     */
  public abstract DataModelIF<U, I> run(RUN_OPTIONS opts) throws Exception;

  /**
     * Runs the recommender using the provided training and test models.
     *
     * @param opts options to run this recommender. See {@link RUN_OPTIONS}
     * enum.
     * @param trainingModel Model to train the recommender.
     * @param testModel Model from where users to generate recommendations to
     * will be considered.
     * @return nothing when opts is {@link net.recommenders.rival.recommend.frameworks.AbstractRunner.RUN_OPTIONS#OUTPUT_RECS},
     * otherwise, when opts is {@link net.recommenders.rival.recommend.frameworks.AbstractRunner.RUN_OPTIONS#RETURN_RECS}
     * or {@link net.recommenders.rival.recommend.frameworks.AbstractRunner.RUN_OPTIONS#RETURN_AND_OUTPUT_RECS}
     * it returns the predictions
     * @throws Exception when the recommender cannot be run. See implementations
     * for more information on possible exceptions.
     */
  public abstract DataModelIF<U, I> run(RUN_OPTIONS opts, TemporalDataModelIF<U, I> trainingModel, TemporalDataModelIF<U, I> testModel) throws Exception;
}