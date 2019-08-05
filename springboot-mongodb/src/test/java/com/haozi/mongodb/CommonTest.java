package com.haozi.mongodb;

import com.haozi.mongodb.config.ConstantConfig;
import com.haozi.mongodb.model.ChargeInfo;
import com.haozi.mongodb.model.Curve;
import com.haozi.mongodb.utils.JsonUtil;
import com.mongodb.*;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询
     */
    @Test
    public void queryDataBaseName() {
        Mongo mongo = new Mongo();

        /**
         * 1. 查询所有数据库
         * */
        for (String name : mongo.getDatabaseNames()) {
            log.info("mongo name:{}", name);
        }

        /**
         * 2. 查询所有集合
         * */
        DB db = mongo.getDB("chargeGraph");
        for (String name : db.getCollectionNames()) {
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
     */
    @Test
    public void testCRUD() {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("chargeGraph");
        DBCollection users = db.getCollection("users");

        /**
         *  1. 查询集合users所有数据
         * */
        DBCursor dbCursor = users.find();
        log.info("[users]:{}", JSON.serialize(dbCursor));

        /**
         *  2. 增加数据
         * */
        addData(users);


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
     * 增加数据
     */
    public void addData(DBCollection users) {
        // 创建list集合
        List<DBObject> lists = new ArrayList<>();
        DBObject user1 = new BasicDBObject();
        user1.put("username", "c");
        user1.put("age", 29);
        user1.put("sex", "男");
        log.info("添加用户1:{}", JsonUtil.bean2Json(user1));
        DBObject user2 = new BasicDBObject();
        user2.put("username", "d");
        user2.put("age", 29);
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
     */
    public void deleteData(DBCollection users) {
        users.remove(new BasicDBObject("age", new BasicDBObject("$gte", 88)));
        log.info("删除mongo数据");
    }

    /**
     * 修改数据
     */
    public void modifyData(DBCollection users) {
        users.update(new BasicDBObject("_id", new ObjectId("5d4151d125c508a29ce4a558")), new BasicDBObject("age", 24));
        log.info("修改id=5d4151d125c508a29ce4a558的数据为：{}", JSON.serialize(users.find()));
    }

    /**
     * 根据订单查询充电数据
     *
     * */
    @Test
    public void queryByOrderNumber() {
        DBObject orderObject = new BasicDBObject();
        orderObject.put("orderNumber", "654321");

        // 拿到桩号，查询数据库状态是否在充电中（考虑一点）
        DBObject field = new BasicDBObject();
        field.put("startChargeSeq", true);
        field.put("startTime", true);
        field.put("soc", true);
        field.put("totalPower", true);
        field.put("operationType", true);
        field.put("connectorId", true);

        DBObject sort = new BasicDBObject();
        sort.put("startTime", 1);
        BasicQuery query = new BasicQuery(orderObject.toString(), field.toString());
        query.with(new Sort(Sort.Direction.ASC, "startTime"));

        List<Curve> lists = mongoTemplate.find(query, Curve.class, ConstantConfig.MONGO_CHARGE_GRAPE_DATA);
        log.info("查询数据为：{}", JsonUtil.list2Json(lists));
    }

    @Test
    public void queryTime1ToTime2() {
        log.info("query data for 10 min");
        int now = (int) System.currentTimeMillis();
        int oldTime = (int) (System.currentTimeMillis() - 600000);
        Criteria criteria = Criteria.where("startTime").gte(oldTime).lt(now);
        CriteriaDefinition cDefinition = new Criteria().andOperator(criteria) ;
//        Query query = new Query();
//        query.addCriteria(criteria);
        List<ChargeInfo> chargeInfos = mongoTemplate.find(Query.query(cDefinition), ChargeInfo.class);
        log.info("chargeInfo:{}", chargeInfos);
    }



}
