1. Hash键值结构
    - key
    - field
    - value
2. 命令hget、hset、hdel ，O(1)级别
    1. hget key field #获取hash key对应的field value
    2. hset key field value #设置hash key对应的field value
    3. hdel key field #删除hash key对应的field value
3. 命令hexists、hlen，O(1)级别
    1. hexists key field #判断hash key是否有field
    2. hlen key #获取hash key field的数量
4. 命令hmget、hmset ，O(n)级别
    1. hmget key field1 field2… #批量获取hash key的一批field对应的值
    2. hmset key field1 value1 field2 value2… #批量设置hash key的一批field value
5. 命令hincrby，hincrbyfloat、hsetnx、O(1)级别
    1. hincrby key field count #hash自增
    2. hincrbyfloat key field floatCount #hincrby浮点数版 
    3. hsetnx key field value #设置hash key对应field的value（如field已经存在则失败）
6. 命令hgetall、hvals、hkeys，O(n)级别
    1. hgetall key #返回hash key对应所有的field和value，小心使用
    2. hvals key #返回hash key对应所有field的value
    3. hkeys key #返回hash key对应所有field
7. string vs hash
    1. get  vs  hget
    2. set/setnx  vs  hset/hsetnx
    3. del  vs hdel
    4. incr/incrby/decr/decrby  vs  hincrby 
    5. mset  vs  hmset
    6. mget  vs  hmget

| 命令                | 复杂度 |
| ------------------- | ------ |
| hget hset hdel      | O(1)   |
| hexists             | O(1)   |
| hincrby             | O(1)   |
| hgetall hvals hkeys | O(n)   |
| hmget hmset         | O(n)   |