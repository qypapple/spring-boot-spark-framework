package hello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scala.Tuple2;

@Component
public class WorldCountService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  static final Pattern SPACE = Pattern.compile(" ");

	@Autowired
	private transient JavaSparkContext sc;
	
	public Map<String, Integer> run(){

		Map<String,Integer> result = new HashMap<String,Integer>();
		JavaRDD<String> lines = sc.textFile("/Users/yuqing/Downloads/blsmy.txt").cache();
		
		System.out.println(lines.count());
		 JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

	            public Iterator<String> call(String s) throws Exception {
					ArrayList<String> ar = new ArrayList();
					Collections.addAll(ar, s.split(" "));
	                return ar.iterator();
	            }

	            private static final long serialVersionUID = 1L;
	        });
		JavaPairRDD<String,Integer> ones = words.mapToPair(s->new Tuple2<String,Integer>(s,1));
		JavaPairRDD<String,Integer> counts = ones.reduceByKey((Integer i1, Integer i2)->(i1+i2));
		List<Tuple2<String,Integer>> outputs = counts.collect();
		
		outputs.forEach(item->result.put(item._1(), item._2()));
		
		return result;
		
		
	}

}
