package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
/**
 * (继承datasupport是为了有增删改查功能)
 * 如果一个model的某个字段不想要被映射
 * 则只需要定义成非private的
 * 这个model中的id字段可以定义也可以不定义
 * 因为数据库默认会生成一个id字段当做为主键
 * */
public class News extends DataSupport{
    private int id;  
    
    private String title;  
      
    private String content;  
      
    private Date publishDate;  
      
    private int commentCount;
    
    private Introduction introduction;	//news和introduction是一对一的关系
    
    private List<Comment> comments = new ArrayList<Comment>(); //与comment是多对一的关系
    
    private List<Category> categories = new ArrayList<Category>();//与category是多对多的关系

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }  
    
}
