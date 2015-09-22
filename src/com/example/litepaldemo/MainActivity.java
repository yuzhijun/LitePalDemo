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
     * 如果是保存一个集合的话是DataSupport.saveAll(List<T>)
     * */
    public void save() {
	Comment comment1 = new Comment();  
	comment1.setContent("好评！");  
	comment1.setPublishDate(new Date());  
	comment1.save();  
	
	Comment comment2 = new Comment();  
	comment2.setContent("赞一个");  
	comment2.setPublishDate(new Date());  
	comment2.save();  
	
	News news = new News();  
	news.getComments().add(comment1);  //设置多对一关系
	news.getComments().add(comment2);  
	news.setTitle("第二条新闻标题");  
	news.setContent("第二条新闻内容");  
	news.setPublishDate(new Date());  
	news.setCommentCount(news.getComments().size());  
	 
	if (news.save()) {  
	    Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();  
	} else {  
	    Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();  
	}  
    }
    
    /**
     *	1. public static int update(Class<?> modelClass, ContentValues values, long id)  
     *  2. public static int updateAll(Class<?> modelClass, ContentValues values, String... conditions) 
     *  3. ...
     * */
    public void update() {
	//1.修改某个id的一条记录
	ContentValues values = new ContentValues();  
	values.put("title", "今日iPhone6发布");  
	DataSupport.update(News.class, values, 2);  
	
	//2.修改符合某个约束条件的记录（修改title为"今日iPhone6发布"，commentcount>0的记录title字段为"今日iPhone6 Plus发布"）
	ContentValues values1 = new ContentValues();  
	values1.put("title", "今日iPhone6 Plus发布");  
	DataSupport.updateAll(News.class, values1, "title = ? and commentcount > ?", "今日iPhone6发布", "0");
	
	//3.修改符合约束条件的记录(修改所有的title都为"今日iPhone6 Plus发布")
	ContentValues values2 = new ContentValues();  
	values2.put("title", "今日iPhone6 Plus发布");  
	DataSupport.updateAll(News.class, values2); 
	
	//4.将id=2的记录title修改为"今日iPhone6发布"
	News updateNews = new News();  
	updateNews.setTitle("今日iPhone6发布");  
	updateNews.update(2);
	
	//5.把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
	News updateNews1 = new News();  
	updateNews1.setTitle("今日iPhone6发布");  
	updateNews1.updateAll("title = ? and commentcount > ?", "今日iPhone6发布", "0");  
	
	/**
	 * 6.就是如果我们想把某一条数据修改成默认值，比如说将评论数修改成0，
	 * 只是调用updateNews.setCommentCount(0)这样是不能修改成功的，
	 * 因为即使不调用这行代码，commentCount的值也默认是0。所以如果想要将
	 * 某一列的数据修改成默认值的话，还需要借助setToDefault()方法
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
	 * 1.删除id为2的记录
	 * 这不仅仅会将news表中id为2的记录删除，同时还会将其它表中以
	 * news id为2的这条记录作为外键的数据一起删除掉，因为外键既
	 * 然不存在了，那么这么数据也就没有保留的意义了
	 * */
	int deleteCount =  DataSupport.delete(News.class, 2);
	Log.i("TAG", deleteCount +"");
	
	//2.news表中标题为“今日iPhone6发布”且评论数等于0的所有新闻都删除掉
	DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");
	
	//3.想把表中的所有news数据删除掉
	DataSupport.deleteAll(News.class);
	
	/*
	 * 4.除了DataSupport类中提供的静态删除方法之外，还有一个删除
	 * 方法是作用于对象上的，即任何一个继承自DataSupport类的实例
	 * 都可以通过调用delete()这个实例方法来删除数据。但前提是这个
	 * 对象一定是要持久化之后的，一个非持久化的对象如果调用了delete()方法则不会产生任何效果
	 * */
	//这条为持久化是删除不成功的
	News news0 = new News();  
	news0.delete();
	//这条是持久化过的（ps：除了save完之后是持久化的，从数据库查找出来的对象也是持久化过的，所以要删除的时候可以先查询）
	News news = new News();  
	news.setTitle("这是一条新闻标题");  
	news.setContent("这是一条新闻内容");  
	news.save();  
	news.delete(); 
    }
    
    /**
     * 
     * */
    public void select() {
	//-----------------------------------懒加载查询---------------------------------------------------
	//1.查询news表中id为1的这条记录
	News news = DataSupport.find(News.class, 1); 
	
	//2.获取news表中的第一条数据,最后一条数据
	News firstNews = DataSupport.findFirst(News.class); 
	News lastNews = DataSupport.findLast(News.class); 
	
	//3.news表中id为1、3、5、7的数据都查出来
	List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7); 
	
	long[] ids = new long[] { 1, 3, 5, 7 };  
	List<News> newsList1 = DataSupport.findAll(News.class, ids);
	
	//4.查询news表中所有评论数大于零的新闻
	List<News> newsList2 = DataSupport.where("commentcount > ?", "0").find(News.class);  
	
	//5.也许你并不需要那么多的数据，而是只要title和content这两列数据。那么也很简单，我们只要再增加一个连缀就行了
	List<News> newsList3 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0").find(News.class); 
	
	//6.将查询出的新闻按照发布的时间倒序排列，即最新发布的新闻放在最前面
	List<News> newsList4 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").find(News.class);
	
	//7.也许你并不希望将所有条件匹配的结果一次性全部查询出来，因为这样数据量可能会有点太大了，而是希望只查询出前10条数据
	List<News> newsList5 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").limit(10).find(News.class);  
	
	//8.刚才我们查询到的是所有匹配条件的前10条新闻，那么现在我想对新闻进行分页展示，翻到第二页时，展示第11到第20条新闻
	List<News> newsList6 = DataSupport.select("title", "content")  
	        .where("commentcount > ?", "0")  
	        .order("publishdate desc").limit(10).offset(10)  
	        .find(News.class);  
	/**
	 * 上述我们的所有用法中，都只能是查询到指定表中的数据而已，关联表中数据是无法查到的，
	 * 因为LitePal默认的模式就是懒查询，当然这也是推荐的查询方式。那么，如果你真的非
	 * 常想要一次性将关联表中的数据也一起查询出来，当然也是可以的，LitePal中也支持激进查询的方式
	 * */
	//------------------------------------激进查询-----------------------------------------------------
	/**
	 * 刚才我们所学的每一个类型的find()方法，都对应了一个带有isEager参数的方法重载，
	 * 这个参数相信大家一看就明白是什么意思了，设置成true就表示激进查询，这样就会把关联
	 * 表中的数据一起查询出来了。
	 * */
	//1.我们想要查询news表中id为1的新闻，并且把这条新闻所对应的评论也一起查询出来
	News news1 = DataSupport.find(News.class, 1, true);  
	List<Comment> commentList = news1.getComments();  
	/**
	 * 这里我建议大家还是使用默认的懒加载更加合适，至于如何查询出关联表中的数据，
	 * 其实只需要在模型类中做一点小修改就可以了
	 * public class News extends DataSupport{  
    		public List<Comment> getComments() {  
        		return DataSupport.where("news_id = ?", String.valueOf(id)).find(Comment.class);  
    		}  
           }  
	 * */
	
	//-----------------------------------原生查询-------------------------------------------------------
	/**
	 * 也许你总会遇到一些千奇百怪的需求，可能使用LitePal提供的查询API无法完成这些
	 * 需求。没有关系，因为即使使用了LitePal，你仍然可以使用原生的查询方式(SQL语句)
	 * 来去查询数据。DataSuppport类中还提供了一个findBySQL()方法，使用这个方法
	 * 就能通过原生的SQL语句方式来查询数据了
	 * */
	Cursor cursor = DataSupport.findBySQL("select * from news where commentcount>?", "0");
    }
    
    /**
     * LitePal中一共提供了count()、sum()、average()、max()和min()这五种聚合函数
     * */
    public void aggresiveFunction() {
	//1.count通过SQL语句来统计news表中一共有多少行
	int result = DataSupport.count(News.class);
	//LitePal中所有的聚合函数都是支持连缀的，也就是说我们可以在统计的时候加入条件语句。比如说想要统计一共有多少条新闻是零评论的
	int result1 = DataSupport.where("commentcount = ?", "0").count(News.class);
	
	//2.sum()方法主要是用于对结果进行求合的，比如说我们想要统计news表中评论的总数量
	int result2 = DataSupport.sum(News.class, "commentcount", int.class); 
	
	//3.average()方法主要是用于统计平均数的，比如说我们想要统计news表中平均每条新闻有多少评论
	double result3 = DataSupport.average(News.class, "commentcount");
	
	//4.max()方法主要用于求出某个列中最大的数值，比如我们想要知道news表中所有新闻里面最高的评论数是多少
	int result4 = DataSupport.max(News.class, "commentcount", int.class); 
	
	//5.min()方法主要用于求出某个列中最小的数值，比如我们想要知道news表中所有新闻里面最少的评论数是多少
	int result5 = DataSupport.min(News.class, "commentcount", int.class);
    }
}
