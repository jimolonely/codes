mybatis多表关联查询分页。

# mapper

带一个Page参数
```java
List<UserPermission> getUserPermissions(Page<UserPermission> page, @Param("up") WebUserPageParam param);
```

重写关联查询：
```xml
    <select id="getUserPermissions" resultType="com.jimo.model.vo.UserPermission">
        select u.*, p.created_time as activated_time
        from t_user u
        join t_permission p on u.user_name = p.user_name
        where 1=1
        <if test="up.createdTime != null">
            and date(u.created_time) = #{up.createdTime}
        </if>
        <if test="up.activatedTime != null">
            and date(p.created_time) = #{up.activatedTime}
        </if>
        <if test="up.userName != null">
            and u.user_name like concat('%', #{up.userName}, '%')
        </if>
        <if test="up.email != null">
            and u.email like concat('%', #{up.email}, '%')
        </if>
        group by u.id
    </select>
```

# service

```java
    /**
     * 分页查询
     */
    public Page<UserPermission> getUserPermission(Page<UserPermission> page, WebUserPageParam param) {
        return page.setRecords(this.baseMapper.getUserPermissions(page, param));
    }
```

# 本质

也就是`limit offset, size`

# 参考
https://www.jianshu.com/p/759b6430ed5b

