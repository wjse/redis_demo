# 2 Redis API介绍

### 2.1通用命令

1. **keys** [pattern]
    - 遍历所有**keys *** ; 遍历所有a开头**keys a*** ; 遍历所有a开头包含b的**keys a[b]** ; 遍历所有指定长度的 **keys ab?**
    - 避免在生产环境使用，O(n)级别
    - 通过热备从节点或scan命令使用
2. **dbsize**
    - 计算key总数，Redis内置计数器实现
3. **exists** key
    - key是否存在
4. **del** key [key...] 
    - 删除指定key-value，可删除多个
5. **expire** key [seconds]
    - key在seconds秒后过期
    - **ttl** key 查看key的过期时间，-1表示key存在但没有设置过期；-2表示key不存在
    - **persist** key 去除key过期时间
6. **type** key 返回key类型

### 2.2 数据结构和内部编码