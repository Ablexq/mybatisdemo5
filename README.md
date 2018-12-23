
# mybatis原生集成（注解版）




# pom.xml添加依赖：

``` 
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
<!--mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

```

# 创建数据库并配置

``` 

use mydb;

DROP TABLE IF EXISTS `book`;
SET character_set_client = utf8mb4 ;
CREATE TABLE `book` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `publish` varchar(20) DEFAULT NULL,
  `pages` int(10) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL,
  `bookcaseid` int(10) DEFAULT NULL,
  `abled` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

INSERT INTO `book` VALUES
(1,'解忧杂货店','东野圭吾','南海出版公司',102,27.30,2,0),
(2,'追风筝的人','卡勒德·胡赛尼','上海人民出版社',230,33.50,3,1),
(3,'人间失格','太宰治','作家出版社',150,17.30,1,1),
(4,'这就是二十四节气','高春香','海豚出版社',220,59.00,3,1),
(5,'白夜行','东野圭吾','南海出版公司',300,27.30,4,1),
(6,'摆渡人','克莱儿·麦克福尔','百花洲文艺出版社',225,22.80,1,1),
(7,'暖暖心绘本','米拦弗特毕','湖南少儿出版社',168,131.60,5,1),
(8,'天才在左疯子在右','高铭','北京联合出版公司',330,27.50,6,1),
(9,'我们仨','杨绛','生活.读书.新知三联书店',89,17.20,7,1),
(10,'活着','余华','作家出版社',100,13.00,1,1),
(11,'水浒传','施耐庵','三联出版社',300,50.00,1,1),
(12,'三国演义','罗贯中','三联出版社',300,50.00,2,1),
(13,'红楼梦','曹雪芹','三联出版社',300,50.00,5,1),
(14,'西游记','吴承恩','三联出版社',300,60.00,3,1);


```
application.yml配置数据库:
``` 
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT
    username: root
    password: admin
```

# entity实体类：
``` 
@Data
@ToString
public class Book {
    private int id;
    private String name;
    private String author;
    private String publish;
    private int pages;
    private double price;
}
```
# dao层mapper

``` 
@Mapper
public interface BookDao {
    @Select("select name,author,price from book where price = #{price}")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class)
    })
    List<Book> getAll(double price);
}

```
@Select ：
sql语句

@Results 与 @Result ：
约定返回结果的类型，entity与数据库的映射
@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。

注意：dao层要添加@Mapper注解或者在启动类添加@MapperScan注解

# 测试

``` 
@Autowired
BookDao bookDao;

@Test
public void test1() {
    List<Book> all = bookDao.getAll(50D);
    for (int i = 0; i < all.size(); i++) {
        System.out.println(i + "======" + all.get(i).toString());
    }
}
```
打印结果：

``` 
0======Book(id=0, name=水浒传, author=施耐庵, publish=null, pages=0, price=50.0)
1======Book(id=0, name=三国演义, author=罗贯中, publish=null, pages=0, price=50.0)
2======Book(id=0, name=红楼梦, author=曹雪芹, publish=null, pages=0, price=50.0)
```


# mybatis中#{}与${}的区别：

``` 
@Mapper
public interface BookDao {
    @Select("select name,author,price from book where price = #{price}")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class)
    })
    List<Book> getAll(double price);

    @Select("select name,author,price from book where price = ${50}")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "price", column = "price", javaType = Double.class)
    })
    List<Book> getAll2();
}
```
测试：

``` 
 @Autowired
    BookDao bookDao;

    @Test
    public void test1() {
        List<Book> all = bookDao.getAll(50D);
        for (int i = 0; i < all.size(); i++) {
            System.out.println(i + "======" + all.get(i).toString());
        }
    }

    @Test
    public void test2() {
        List<Book> all = bookDao.getAll2();
        for (int i = 0; i < all.size(); i++) {
            System.out.println(i + "======" + all.get(i).toString());
        }
    }
```
打印结果相同。

说明：
``` 
//意同：select name,author,price from book where price = 50
select name,author,price from book where price = ${50}

//意同：select name,author,price from book where price = ?
select name,author,price from book where price = #{price}
```

注意：推荐使用#{} 






