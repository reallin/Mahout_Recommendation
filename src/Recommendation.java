import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommendation {

	private static final String SOURCE_1 = "F:\\MyEclipse\\Workspaces\\test\\src\\source\\test_1.csv";
	private static final String SOURCE_2 = "F:\\MyEclipse\\Workspaces\\test\\src\\source\\test_2.csv";
	
	//运行推荐算法
	public static List<RecommendedItem> work(long userId) throws Exception{
		DataModel model = new FileDataModel(new File(SOURCE_2));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        return recommender.recommend(userId, 5);
	}
	
	//评估查准率和查全率
	public static void evaluateRate() throws Exception{
		DataModel model = new FileDataModel(new File(SOURCE_2));
	    RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
	    RecommenderBuilder builder = new RecommenderBuilder() {
	      @Override
	      public Recommender buildRecommender(DataModel model) throws TasteException {
	        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	        UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
	        return new GenericUserBasedRecommender(model, neighborhood, similarity);
	      }
	    };
	    IRStatistics stats = evaluator.evaluate(builder, null, model, null, 5,
	                                            GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
	                                            1);
	    System.out.println(stats.getPrecision());
	    System.out.println(stats.getRecall());
	    System.out.println(stats.getF1Measure());
	}
	
	//数据处理
	public static Set<String> exchange() throws Exception{
		Set<String> userSet = new HashSet<String>();
		List<Object[]> sourceData = CsvTool.readCsv(SOURCE_1);
		List<String[]> dataList = new ArrayList<String[]>(110000);
		for(Object[] o:sourceData){
			userSet.add((String)o[0]);
			String[] data = new String[]{(String)o[0],(String)o[1],(String)o[2] + "0"};
			dataList.add(data);
		}
		CsvTool.writeCsv(SOURCE_2, null, dataList);
		return userSet;
	}
	
	public static void main(String[] args){
//		try {
//			evaluateRate();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try {
//			DataModel model = new FileDataModel(new File(SOURCE_2));
//			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//			for(Object userId:IteratorUtils.toArray(model.getUserIDs())){
//				int i=1;
//				while(i<200){
//					UserNeighborhood neighborhood = new NearestNUserNeighborhood(i, similarity, model);
//					long[] theNeighborhood = neighborhood.getUserNeighborhood(new Long(userId.toString()));
//					if(theNeighborhood.length>0){
//						System.out.println(userId + "有邻居" + theNeighborhood + ", 此时i的值为" + i);
//					}
//					i*=10;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			List<RecommendedItem> recommendedList = work(new Long(74356194));
			for (RecommendedItem item : recommendedList) {
				System.out.printf("(%s,%f)", item.getItemID(), item.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}