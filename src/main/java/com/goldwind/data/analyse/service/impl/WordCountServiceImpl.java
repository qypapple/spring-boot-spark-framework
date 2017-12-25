package com.goldwind.data.analyse.service.impl;

import com.goldwind.data.analyse.bean.Word;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import com.goldwind.data.analyse.service.WordCountService;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class WordCountServiceImpl implements WordCountService,Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private  static final Pattern SPACE = Pattern.compile(" ");

    @Autowired
    private transient JavaSparkContext sc;

    @Override
    public Word getWordCountByName(String name,String path) {
        Map<String,Integer> map = getWholeTextFile(path);
        Word word = new Word();
        word.setName(name);
        Integer c = map.get(name);
        word.setCount(c==null?"0":c.toString());
        return word;
    }
    @Override
    public Map<String,Integer> getAllFileCount(String path){
        //"/Users/yuqing/Downloads/blsmy.txt"
        return this.getWholeTextFile(path);
    }


    public  Map<String, Integer> getWholeTextFile(String path){

        java.util.Map<java.lang.String,java.lang.Integer> result = new HashMap<java.lang.String,java.lang.Integer>();
        JavaRDD<java.lang.String> lines = sc.textFile(path).cache();

        System.out.println(lines.count());
        JavaRDD<java.lang.String> words = lines.flatMap(new FlatMapFunction<java.lang.String, java.lang.String>() {

            public Iterator<java.lang.String> call(java.lang.String s) throws Exception {
                ArrayList<java.lang.String> ar = new ArrayList();
                Collections.addAll(ar, s.split(" "));
                return ar.iterator();
            }

            private static final long serialVersionUID = 1L;
        });
        JavaPairRDD<java.lang.String,java.lang.Integer> ones = words.mapToPair(s->new Tuple2<java.lang.String,java.lang.Integer>(s,1));
        JavaPairRDD<java.lang.String, java.lang.Integer> counts = ones.reduceByKey((java.lang.Integer i1, java.lang.Integer i2)->(i1+i2));
        List<Tuple2<java.lang.String, java.lang.Integer>> outputs = counts.collect();

        outputs.forEach(item->result.put(item._1(), item._2()));

        return result;
    }
}
