package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport{
    private int id;  
    
    private String name;
    
    private List<News> news = new ArrayList<News>();//与news是多对多的关系

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }  
}
