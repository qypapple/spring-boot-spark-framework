package com.goldwind.data.analyse.service;

import com.goldwind.data.analyse.bean.Word;
import java.util.Map;

public interface WordCountService {
    Word getWordCountByName(String name,String path);
    Map<String,Integer> getAllFileCount(String path);
}
