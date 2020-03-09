# 问题总结

有些是偷懒导致的隐藏问题，有些是确实不知道导致的，但都是自身的原因，如果不吸取教训，那么将会越错越多。

# mybatis相关

## 关于mybatis的驼峰字段映射

如果开启了驼峰与下划线的自动转换：

```conf
mybatis.configuration.map-underscore-to-camel-case=true
```

例如xml文件：
```xml
    <select id="getByUserName" resultType="com.jimo.AuthUserPO">
        select *
        from auth_user
        where user_name = #{username}
    </select>
```

那么如果映射实体`AuthUserPO`里有下划线的字段，和数据库表里的下划线字段一样：
```java
@Data
public class AuthUserPO {
    private Integer id;
    private String user_name;
    private String user_password;
}
```
那么这个字段无法映射上，推荐java实体类都用驼峰命名规范，或者xml里写好映射关系：

```xml
    <resultMap id="userMap" type="com.jimo.AuthUserPO">
        <result column="user_name" property="user_name" jdbcType="VARCHAR"/>
        <result column="user_password" property="user_password" jdbcType="VARCHAR"/>
	</resultMap>
```

## 关于xml里的foreach

比如 一个in的字段

```xml
    <select id="queryUser" resultMap="userMap">
        select * from auth_user
		 where user_name in
		<foreach collection="names" item="n" close=")" open="(" separator=",">
			#{n}
		</foreach>
    </select>
```
但是当names为空时会报语法错误，作为一个好习惯，虽然我知道会这样，但是偷懒没有做，最后总会忘掉外面的判断，所以总会还的，所以需要验证：

```xml
    <select id="queryUser" resultMap="userMap">
        select * from auth_user
        <choose>
            <when test="names!=null and names.size > 0">
                where user_name in
				<foreach collection="names" item="n" close=")" open="(" separator=",">
					#{n}
				</foreach>
            </when>
            <otherwise>
                where 1=2
            </otherwise>
        </choose>
    </select>
```

# opencsv乱码问题

使用opencsv读取文件时，如果没指定编码，可能会中文乱码（仅仅是可能），但指定了之后才是安全的：

```java
// 指定为UTF-8编码
final InputStreamReader in = new InputStreamReader(new FileInputStream(path),
		StandardCharsets.UTF_8);
// 未指定编码的方式，可能乱码
// final CsvToBeanBuilder<User> builder = new CsvToBeanBuilder<>(new FileReader(path));
final CsvToBeanBuilder<User> builder = new CsvToBeanBuilder<>(in);
final CsvToBean<User> csv = builder.withType(User.class).build();
// 一次插一千条
final int size = 1000;
List<User> buf = new ArrayList<>(size);
final Iterator<User> it = csv.iterator();

while (it.hasNext()) {
	buf.add(it.next());
	cnt++;
	if (cnt % size == 0) {
		// flush
		final int c = userDao.insert(buf);
		log.info("insert into t_user,size={}", c);
		buf.clear();
	}
}
if (buf.size() > 0) {
	// flush
	final int c = userDao.insert(buf);
	log.info("insert into t_user,size={}", c);
}
```
