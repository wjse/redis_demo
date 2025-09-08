1. 特点
    - 有序
    - 可以重复
    - 左右两边插入弹出
2. 重要API
    1. 增
        1. lpush key value1 value2 value3… valueN#从列表左端插入值（1-N个），O(1-n)
        2. rpush key value1 value2 value3… valueN#从列表右端插入值（1-N个），O(1-n)
        3. linsert key before|after value newValue #在list指定的value前|后插入newValue，O(n)
    2. 删
        1. lpop key #从list左边弹出一个元素 ，O(1)
        2. rpop key #从list右边弹出一个元素 ，O(1)
        3. lrem key count value #根据count的值，从列表中删除所有value相等的项，O(n)
            - count > 0 ，从左到右，删除最多count个value相等的项
            - count < 0 ，从右到左，删除最多Math.abs(count)个value相等的项
            - count = 0 ，删除所有value相等的项
        4. ltrim key start end #按照索引范围修剪列表，O(n)
    3. 查
        1. lrange key start end #获取list指定索引范围start-end，包含end内所有元素，O(n)，start,end正数从左开始，负数从右开始，-1为正数最后一个
        2. lindex key index #获取list指定索引的元素，O(n)，index正数从左开始，负数从右开始，-1为正数最后一个
        3. llen key #列表长度，O(1)
    4. 改
        1. lset key index newValue #设置list指定索引值为newValue，O(n)
3. 其他API，blpop、brpop
    1. blpop key timeout #lpop阻塞版本，阻塞弹出，timeout是阻塞超时时间，timeout=0永远不阻塞，O(1)
    2. brpop key timeout #rpop阻塞版本，阻塞弹出，timeout是阻塞超时时间，timeout=0永远不阻塞，O(1)
4. 提示：
    1. lpush + lpop = Stack
    2. lpush + rpop = Queue
    3. lpush + ltrim = 定量的集合
    4. lpush + brpop = Message Queue