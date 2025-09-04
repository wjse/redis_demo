package com.krr.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class JedisTest {

    public static void main(String[] args) {
        JedisTest jt = new JedisTest();
        Jedis jedis = new Jedis("127.0.0.1" , 6379);
        jt.batchAddData(jedis , 5000000);
        jedis.close();
//        jt.jedisString(jedis);
//        jt.jedisHash(jedis);
//        jt.jedisList(jedis);
//        jt.jedisSet(jedis);
//        jt.jedisZSet(jedis);
//        jedis.close();

//        System.out.println("Use Jedis Pool");
//        JedisPool pool = new JedisPool("127.0.0.1" , 6379);
//        jt.jedisString(pool.getResource());
//        jt.jedisHash(pool.getResource());
//        jt.jedisList(pool.getResource());
//        jt.jedisSet(pool.getResource());
//        jt.jedisZSet(pool.getResource());

//        jt.jedisSubscribe(pool.getResource());
//        pool.close();
    }

    private void batchAddData(Jedis jedis , int size){
        Random random = new Random(size);
        for(int i = 0 ; i < size; i++){
            String key = String.valueOf(i + 1);
            jedis.set(key , String.valueOf(random.nextInt()));
        }
    }

    private void jedisString(Jedis jedis){
        String r = jedis.set("hello" , "world");
        System.out.println(r);

        String v = jedis.get("hello");
        System.out.println(v);

        jedis.del("counter");
        for(int i = 0 ; i < 5 ; i++){
            long count = jedis.incr("counter");
            System.out.println(count);
        }
    }

    private void jedisHash(Jedis jedis){
        jedis.hset("myhash" , "f1" , "v1");
        jedis.hset("myhash" , "f2" , "v2");

        Map<String , String> myhash = jedis.hgetAll("myhash");
        System.out.println(myhash);
    }

    private void jedisList(Jedis jedis){
        jedis.del("mylist");
        jedis.rpush("mylist" , "1");
        jedis.rpush("mylist" , "2");
        jedis.rpush("mylist" , "3");

        List<String> list = jedis.lrange("mylist" , 0 , -1);
        System.out.println(list);
    }

    private void jedisSet(Jedis jedis){
        jedis.del("myset");
        jedis.sadd("myset" , "a");
        jedis.sadd("myset" , "b");
        jedis.sadd("myset" , "c");

        Set<String> set = jedis.smembers("myset");
        System.out.println(set);
    }

    private void jedisZSet(Jedis jedis){
        jedis.del("myzset");
        jedis.zadd("myzset" , 99 , "tom");
        jedis.zadd("myzset" , 66 , "peter");
        jedis.zadd("myzset" , 33 , "james");

        Set<Tuple> set = jedis.zrangeWithScores("myzset",0 , -1);
        System.out.println(set);
    }

    private void jedisSubscribe(Jedis jedis){
        jedis.subscribe(new MyJedisPubSub() , "chat");
    }
}

class MyJedisPubSub extends JedisPubSub{

    private static final Random random = new Random();

    private volatile String msg;

    @Override
    public void onMessage(String channel, String message) {
        System.out.printf("%s receive message : %s%n" , channel , message);
        this.msg = message;
        try {
            Thread.sleep(3000);
            jedisPublish();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void jedisPublish(){
        Jedis jedis = new Jedis("127.0.0.1" , 6379);
        jedis.publish("nextChat" , String.format("%s-%s" , random.nextInt() , this.msg));
        jedis.close();
    }
}
