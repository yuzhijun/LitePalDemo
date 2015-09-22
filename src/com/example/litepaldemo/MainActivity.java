package com.example.litepaldemo;

import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.example.model.Comment;
import com.example.model.News;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
/**
 * @author yuzhijun
 * @version 2015-08-28
 * */


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	SQLiteDatabase db = Connector.getDatabase();

//	save();
//	update();
//	delete();
    }
    
    /**
     * ����Ǳ���һ�����ϵĻ���DataSupport.saveAll(List<T>)
     * */
    public void save() {
	Comment comment1 = new Comment();  
	comment1.setContent("������");  
	comment1.setPublishDate(new Date());  
	comment1.save();  
	
	Comment comment2 = new Comment();  
	comment2.setContent("��һ��");  
	comment2.setPublishDate(new Date());  
	comment2.save();  
	
	News news = new News();  
	news.getComments().add(comment1);  //���ö��һ��ϵ
	news.getComments().add(comment2);  
	news.setTitle("�ڶ������ű���");  
	news.setContent("�ڶ�����������");  
	news.setPublishDate(new Date());  
	news.setCommentCount(news.getComments().size());  
	 
	if (news.save()) {  
	    Toast.makeText(this, "�洢�ɹ�", Toast.LENGTH_SHORT).show();  
	} else {  
	    Toast.makeText(this, "�洢ʧ��", Toast.LENGTH_SHORT).show();  
	}  
    }
    
    /**
     *	1. public static int update(Class<?> modelClass, ContentValues values, long id)  
     *  2. public static int updateAll(Class<?> modelClass, ContentValues values, String... conditions) 
     *  3. ...
     * */
    public void update() {
	//1.�޸�ĳ��id��һ����¼
	ContentValues values = new ContentValues();  
	values.put("title", "����iPhone6����");  
	DataSupport.update(News.class, values, 2);  
	
	//2.�޸ķ���ĳ��Լ�������ļ�¼���޸�titleΪ"����iPhone6����"��commentcount>0�ļ�¼title�ֶ�Ϊ"����iPhone6 Plus����"��
	ContentValues values1 = new ContentValues();  
	values1.put("title", "����iPhone6 Plus����");  
	DataSupport.updateAll(News.class, values1, "title = ? and commentcount > ?", "����iPhone6����", "0");
	
	//3.�޸ķ���Լ�������ļ�¼(�޸����е�title��Ϊ"����iPhone6 Plus����")
	ContentValues values2 = new ContentValues();  
	values2.put("title", "����iPhone6 Plus����");  
	DataSupport.updateAll(News.class, values2); 
	
	//4.��id=2�ļ�¼title�޸�Ϊ"����iPhone6����"
	News updateNews = new News();  
	updateNews.setTitle("����iPhone6����");  
	updateNews.update(2);
	
	//5.��news���б���Ϊ������iPhone6��������������������0���������ŵı���ĳɡ�����iPhone6 Plus������
	News updateNews1 = new News();  
	updateNews1.setTitle("����iPhone6����");  
	updateNews1.updateAll("title = ? and commentcount > ?", "����iPhone6����", "0");  
	
	/**
	 * 6.��������������ĳһ�������޸ĳ�Ĭ��ֵ������˵���������޸ĳ�0��
	 * ֻ�ǵ���updateNews.setCommentCount(0)�����ǲ����޸ĳɹ��ģ�
	 * ��Ϊ��ʹ���������д��룬commentCount��ֵҲĬ����0�����������Ҫ��
	 * ĳһ�е������޸ĳ�Ĭ��ֵ�Ļ�������Ҫ����setToDefault()����
	 * */
	News updateNews2 = new News();  
	updateNews2.setToDefault("commentCount");  
	updateNews2.updateAll();  
    }
    
    /**
     * 1. public static int delete(Class<?> modelClass, long id)
     * 2. public static int deleteAll(Class<?> modelClass, String... conditions)
     * */
    public void delete() {
	/*
	 * 1.ɾ��idΪ2�ļ�¼
	 * �ⲻ�����Ὣnews����idΪ2�ļ�¼ɾ����ͬʱ���Ὣ����������
	 * news idΪ2��������¼��Ϊ���������һ��ɾ��������Ϊ�����
	 * Ȼ�������ˣ���ô��ô����Ҳ��û�б�����������
	 * */
	int deleteCount =  DataSupport.delete(News.class, 2);
	Log.i("TAG", deleteCount +"");
	
	//2.news���б���Ϊ������iPhone6������������������0���������Ŷ�ɾ����
	DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "����iPhone6����", "0");
	
	//3.��ѱ��е�����news����ɾ����
	DataSupport.deleteAll(News.class);
	
	/*
	 * 4.����DataSupport�����ṩ�ľ�̬ɾ������֮�⣬����һ��ɾ��
	 * �����������ڶ����ϵģ����κ�һ���̳���DataSupport���ʵ��
	 * ������ͨ������delete()���ʵ��������ɾ�����ݡ���ǰ�������
	 * ����һ����Ҫ�־û�֮��ģ�һ���ǳ־û��Ķ������������delete()�����򲻻�����κ�Ч��
	 * */
	//����Ϊ�־û���ɾ�����ɹ���
	News news0 = new News();  
	news0.delete();
	//�����ǳ־û����ģ�ps������save��֮���ǳ־û��ģ������ݿ���ҳ����Ķ���Ҳ�ǳ־û����ģ�����Ҫɾ����ʱ������Ȳ�ѯ��
	News news = new News();  
	news.setTitle("����һ�����ű���");  
	news.setContent("����һ����������");  
	news.save();  
	news.delete(); 
    }
    
    /**
     * 
     * */
    public void select() {
	//-----------------------------------�����ز�ѯ---------------------------------------------------
	//1.��ѯnews����idΪ1��������¼
	News news = DataSupport.find(News.class, 1); 
	
	//2.��ȡnews���еĵ�һ������,���һ������
	News firstNews = DataSupport.findFirst(News.class); 
	News lastNews = DataSupport.findLast(News.class); 
	
	//3.news����idΪ1��3��5��7�����ݶ������
	List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7); 
	
	long[] ids = new long[] { 1, 3, 5, 7 };  
	List<News> newsList1 = DataSupport.findAll(News.class, ids);
	
	//4.��ѯnews�������������������������
	List<News> newsList2 = DataSupport.where("commentcount > ?", "0").find(News.class);  
	
	//5.Ҳ���㲢����Ҫ��ô������ݣ�����ֻҪtitle��content���������ݡ���ôҲ�ܼ򵥣�����ֻҪ������һ����׺������
	List<News> newsList3 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0").find(News.class); 
	
	//6.����ѯ�������Ű��շ�����ʱ�䵹�����У������·��������ŷ�����ǰ��
	List<News> newsList4 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").find(News.class);
	
	//7.Ҳ���㲢��ϣ������������ƥ��Ľ��һ����ȫ����ѯ��������Ϊ�������������ܻ��е�̫���ˣ�����ϣ��ֻ��ѯ��ǰ10������
	List<News> newsList5 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").limit(10).find(News.class);  
	
	//8.�ղ����ǲ�ѯ����������ƥ��������ǰ10�����ţ���ô������������Ž��з�ҳչʾ�������ڶ�ҳʱ��չʾ��11����20������
	List<News> newsList6 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").limit(10).offset(10)  
	        .find(News.class);  
	/**
	 * �������ǵ������÷��У���ֻ���ǲ�ѯ��ָ�����е����ݶ��ѣ����������������޷��鵽�ģ�
	 * ��ΪLitePalĬ�ϵ�ģʽ��������ѯ����Ȼ��Ҳ���Ƽ��Ĳ�ѯ��ʽ����ô���������ķ�
	 * ����Ҫһ���Խ��������е�����Ҳһ���ѯ��������ȻҲ�ǿ��Եģ�LitePal��Ҳ֧�ּ�����ѯ�ķ�ʽ
	 * */
	//------------------------------------������ѯ-----------------------------------------------------
	/**
	 * �ղ�������ѧ��ÿһ�����͵�find()����������Ӧ��һ������isEager�����ķ������أ�
	 * ����������Ŵ��һ����������ʲô��˼�ˣ����ó�true�ͱ�ʾ������ѯ�������ͻ�ѹ���
	 * ���е�����һ���ѯ�����ˡ�
	 * */
	//1.������Ҫ��ѯnews����idΪ1�����ţ����Ұ�������������Ӧ������Ҳһ���ѯ����
	News news1 = DataSupport.find(News.class, 1, true);  
	List<Comment> commentList = news1.getComments();  
	/**
	 * �����ҽ����һ���ʹ��Ĭ�ϵ������ظ��Ӻ��ʣ�������β�ѯ���������е����ݣ�
	 * ��ʵֻ��Ҫ��ģ��������һ��С�޸ľͿ�����
	 * public class News extends DataSupport{  
    		public List<Comment> getComments() {  
        		return DataSupport.where("news_id = ?", String.valueOf(id)).find(Comment.class);  
    		}  
           }  
	 * */
	
	//-----------------------------------ԭ����ѯ-------------------------------------------------------
	/**
	 * Ҳ�����ܻ�����һЩǧ��ٹֵ����󣬿���ʹ��LitePal�ṩ�Ĳ�ѯAPI�޷������Щ
	 * ����û�й�ϵ����Ϊ��ʹʹ����LitePal������Ȼ����ʹ��ԭ���Ĳ�ѯ��ʽ(SQL���)
	 * ��ȥ��ѯ���ݡ�DataSuppport���л��ṩ��һ��findBySQL()������ʹ���������
	 * ����ͨ��ԭ����SQL��䷽ʽ����ѯ������
	 * */
	Cursor cursor = DataSupport.findBySQL("select * from news where commentcount>?", "0");
    }
    
    /**
     * LitePal��һ���ṩ��count()��sum()��average()��max()��min()�����־ۺϺ���
     * */
    public void aggresiveFunction() {
	//1.countͨ��SQL�����ͳ��news����һ���ж�����
	int result = DataSupport.count(News.class);
	//LitePal�����еľۺϺ�������֧����׺�ģ�Ҳ����˵���ǿ�����ͳ�Ƶ�ʱ�����������䡣����˵��Ҫͳ��һ���ж����������������۵�
	int result1 = DataSupport.where("commentcount = ?", "0").count(News.class);
	
	//2.sum()������Ҫ�����ڶԽ��������ϵģ�����˵������Ҫͳ��news�������۵�������
	int result2 = DataSupport.sum(News.class, "commentcount", int.class); 
	
	//3.average()������Ҫ������ͳ��ƽ�����ģ�����˵������Ҫͳ��news����ƽ��ÿ�������ж�������
	double result3 = DataSupport.average(News.class, "commentcount");
	
	//4.max()������Ҫ�������ĳ������������ֵ������������Ҫ֪��news������������������ߵ��������Ƕ���
	int result4 = DataSupport.max(News.class, "commentcount", int.class); 
	
	//5.min()������Ҫ�������ĳ��������С����ֵ������������Ҫ֪��news�������������������ٵ��������Ƕ���
	int result5 = DataSupport.min(News.class, "commentcount", int.class);
    }
}
