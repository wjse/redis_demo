1. 特点
    1. 无序
    2. 无重复元素
    3. 集合间的操作（交集，并集，差集）
2. API
    1. sadd key element #向集合key添加element（如element已存在则添加失败），O(1)
    2. srem key element #将集合key中的element删除，O(1)
    3. scard key #计算集合大小
    4. sismember key element #判断element是否在集合中
    5. srandmember key count #从集合中随机挑count个元素
    6. spop key #从集合中随机弹出一个元素
    7. smembers key #获取所有元素，小心使用（元素过多情况）
    8. sscan key cursor match pattern #通过游标扫描元素，如：sscan user:2:follow 0 match h*
    9. 标签Tags:
        1. sadd key:tags tag1 tag2… #给key添加标签
        2. sadd tag1:key1 key2 key3…#给指定标签添加集合
3. 集合间API
    1. sdiff key1 key2 key3...#算出集合间的差集
    2. sinter key1 key2 key3...#算出集合间的交集
    3. sunion key1 key2 key3...#算出集合间的并集
    4. sdiffstore destkey #将差集结果保存在destkey中
    5. sinterstore destkey #将交集结果保存在destkey中
    6. sunionstore destkey #将并集结果保存在destkey中
4. 提示
    1. sadd = tagging
    2. spop/srandmember = random item
    3. sadd + sinter = Social Graph