package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
/**
 * (�̳�datasupport��Ϊ������ɾ�Ĳ鹦��)
 * ���һ��model��ĳ���ֶβ���Ҫ��ӳ��
 * ��ֻ��Ҫ����ɷ�private��
 * ���model�е�id�ֶο��Զ���Ҳ���Բ�����
 * ��Ϊ���ݿ�Ĭ�ϻ�����һ��id�ֶε���Ϊ����
 * */
public class News extends DataSupport{
    private int id;  
    
    private String title;  
      
    private String content;  
      
    private Date publishDate;  
      
    private int commentCount;
    
    private Introduction introduction;	//news��introduction��һ��һ�Ĺ�ϵ
    
    private List<Comment> comments = new ArrayList<Comment>(); //��comment�Ƕ��һ�Ĺ�ϵ
    
    private List<Category> categories = new ArrayList<Category>();//��category�Ƕ�Զ�Ĺ�ϵ

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
