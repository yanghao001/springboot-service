package com.haozi.mongodb;

import com.haozi.mongodb.utils.JsonUtil;
import com.mongodb.*;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo study
 *
 * @author hao.yang
 * @date 2019/7/31
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    /**
     *  查询
     * */
    @Test
    public void queryDataBaseName() {
        Mongo mongo = new Mongo();

        /**
         * 1. 查询所有数据库
         * */
        for (String name: mongo.getDatabaseNames()) {
            log.info("mongo name:{}", name);
        }

        /**
         * 2. 查询所有集合
         * */
        DB db = mongo.getDB("chargeGraph");
        for (String name: db.getCollectionNames()) {
            log.info("collections name:{}", name);
        }

        /**
         * 3. 查询集合数据
         * */
        DBCollection dbCollection = db.getCollection("users");
        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext()) {
            log.info("info:{}", dbCursor.next());
        }
        log.info("[users]:{}", JSON.serialize(dbCursor));

    }

    /**
     * Mongo 增删改查操作
     *
     * */
    @Test
    public void testCRUD() {
        Mongo mongo = new Mongo();
        DB db  = mongo.getDB("chargeGraph");
        DBCollection users = db.getCollection("users");

        /**
         *  1. 查询集合users所有数据
         * */
        DBCursor dbCursor = users.find();
        log.info("[users]:{}", JSON.serialize(dbCursor));

        /**
         *  2. 增加数据
         * */
//        addData(users);


        /**
         * 3. 删除数据
         * */
//        deleteData(users);

        /**
         * 修改数据
         * */
//        modifyData(users);

    }


    /**
     *  增加数据
     * */
    public void addData(DBCollection users) {
        // 创建list集合
        List<DBObject> lists = new ArrayList<>();
        DBObject user1 = new BasicDBObject();
        user1.put("username", "dingyan");
        user1.put("age", 18);
        user1.put("sex", "女");
        log.info("添加用户1:{}", JsonUtil.bean2Json(user1));
        DBObject user2 = new BasicDBObject();
        user2.put("username", "aiming");
        user2.put("age", 88);
        user2.put("sex", "男");
        log.info("添加用户2:{}", JsonUtil.bean2Json(user2));
        lists.add(user1);
        lists.add(user2);
        // 插入mongo
        users.insert(lists);
        log.info("插入mongo");
    }

    /**
     * 删除数据
     * */
    public void deleteData(DBCollection users) {
        users.remove(new BasicDBObject("age", new BasicDBObject("$gte", 88)));
        log.info("删除mongo数据");
    }

    /**
     * 修改数据
     * */
    public void modifyData(DBCollection users) {
        users.update(new BasicDBObject("_id", new ObjectId("5d4151d125c508a29ce4a558")), new BasicDBObject("age", 24));
        log.info("修改id=5d4151d125c508a29ce4a558的数据为：{}", JSON.serialize(users.find()));
    }


}
