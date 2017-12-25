package com.goldwind.data.analyse.controller;


import com.goldwind.data.analyse.bean.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.goldwind.data.analyse.service.WordCountService;

import java.util.Map;

@RestController
public class WordController {

    @Autowired
    WordCountService wcService;

    String path = "/Users/yuqing/Downloads/blsmy.txt";

    @GetMapping("/word/{name}")
    public Word getWordByName(@PathVariable String name){
        Word word = wcService.getWordCountByName(name,path);
        return word;

    }

    @GetMapping("/wordcount")
    public Map<String, Integer> worldCount()
    {
        return wcService.getAllFileCount(path);
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


}
