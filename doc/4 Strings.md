1. 字符串键值结构
    - key为字符串
    - value可以是字符串，数字，二进制，XML，JSON等
    - value不能大于512MB
2. 使用场景
    1. 缓存
    2. 计数器
    3. 分布式锁
3. 命令get，set，del，都是O(1)级别
    1. set key value #key存在与否，都设置
    2. setnx #key不存在，才设置
    3. set key value xx #key存在，才设置
4. 命令incr自增，decr自减，incrby自增指定，decrby自减指定，整型操作O(1)
5. 实战
    1. 用户访问量统计
    2. 缓存视频的基本信息
    3. 分布式id生成器
6. 命令mget、mset，O(n)级别，原子操作
    1. mget [key…] #批量获取key
    2. mset key1 value1 key2 value2 … #批量设置
7. 命令getset、append、strlen，O(1)级别
    1. getset key newvalue #set key newvalue并返回旧value 
    2. append key value #将value追加到旧的value
    3. strlen key #返回字符长度（注意中文）
8. 命令incrbyfloat、getrange、setrange，O(1)级别
    1. incrbyfloat key value #浮点数自增，没有提供自减操作
    2. getrange key start end #获取字符串指定下标的值
    3. setrange key index value #设置指定下标所有对应的值

| 命令                          | 复杂度 |
| ----------------------------- | ------ |
| get set del                   | O(1)   |
| incrbyfloat getrange setrange | O(1)   |
| incr decr incrby decrby       | O(1)   |
| getset append strlen          | O(1)   |
| mget mset                     | O(n)   |

​         