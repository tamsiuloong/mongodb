package test;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * 佛祖保佑       永无BUG
 * Created by DELL on 2016/12/2.
 */
public class MongoTest {

    public static Mongo mongo ;
    static {
        try {
            mongo= new Mongo("localhost",27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void find() throws UnknownHostException {
        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");
        DBCursor cursor = c1.find();
        while(cursor.hasNext())
        {
            DBObject dbObject = cursor.next();
            String name = (String) dbObject.get("name");
            Double age  = (Double)dbObject.get("age");
            ObjectId id = (ObjectId) dbObject.get("_id");

            System.out.println("id:"+id+";name:"+name+";age:"+age);
        }
        cursor.close();
    }


    @Test
    public void delete(){
        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");

        //模拟一个html参数 5840ca0f2af2f88ab440fab5
        BasicDBObject dbObject = new BasicDBObject("_id",new ObjectId("5840ca0f2af2f88ab440fab5"));
        c1.remove(dbObject);
    }


    @Test
    public void insert(){
        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");

        DBObject dbObject = new BasicDBObject();
        dbObject.put("name","jolin");
        dbObject.put("age",18d);

        c1.insert(dbObject);


    }

    @Test
    public void update(){

        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");

        DBObject query = new BasicDBObject("_id",new ObjectId("5840cde1faf33ba9ddaec03a"));

        DBObject object = c1.findOne(query);
        object.put("name","蔡依林");

        c1.update(query,object);
    }


    @Test
    public void insertJson(){
        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");


        //模拟web前端  json 数据
        String json = "{\"name\": \"jolin\",\"age\": 18}";

        DBObject dbObject = (DBObject) JSON.parse(json);
        c1.insert(dbObject);
    }

    @Test
    public void findByQuery(){


        DB db = mongo.getDB("test");
        DBCollection c1 = db.getCollection("c1");

        DBObject query = new BasicDBObject();
        query.put("age",new BasicDBObject("$gt",1));
        query.put("name","jolin");

        DBCursor cursor = c1.find(query);
        while(cursor.hasNext()){
            DBObject object = cursor.next();
            System.out.println(JSON.serialize(object));
        }
    }
}
