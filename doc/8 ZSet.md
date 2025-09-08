1. 特点
    1. 结构：key-score-value
2. 重要API
    1. zadd key score element #添加score和element，可以是多对score element，O(logN)
    2. zrem key elemet #删除元素，O(1)
    3. zscore key element #返回元素的分数，O(1)
    4. zincrby key increScore element #增加或减少元素的分数
    5. zcard key #返回元素个数，O(1)
    6. zrank key elemet #获取元素排名，类似index
    7. zrange key start end withscores #遍历所有元素和分数（升序），O(logN + M)，N为元素个数，M为索引范围个数
    8. zrangebyscore key minScore maxScore #获取分值范围内的升序元素，O(logN + M)，N为元素个数，M为分值范围个数
    9. zcount key minScore maxScore #获取分值范围内的元素个数，O(logN + M)，N为元素个数，M为分值范围个数
    10. zremrangebyrank key start end #删除指定排名内的升序元素，O(logN + M)，N为元素个数，M为索引范围个数
    11. zremrangebyscore key minScore maxScore #删除指定分数内的升序元素，O(logN + M)，N为元素个数，M为分数范围个数
3. 反转API
    1. zrevrank key element #获取指定元素的排名，O(1)
    2. zrevrange key start end #遍历所有元素和分数（降序），O(logN + M)，N为元素个数，M为索引范围个数
    3. zrevrangebyscore key minScore maxScore #获取分值范围内的降序元素，O(logN + M)，N为元素个数，M为分值范围个数
4. 集合操作
    1. zinterstore destkey numbers key1 key2… #将numbers个数的集合的交集存储到destkey新集合中
    2. zunionstore destkey numbers key1 key2… #将numbers个数的集合的并集存储到destkey新集合中