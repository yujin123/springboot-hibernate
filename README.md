## springboot整合hibernate的方式总结
### 方式一：利用jpa
#### 步骤一：编写properties文件
```properties
# 数据库配置（jpa）
spring.datasource.url=jdbc:mysql://127.0.0.1/db_test
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driverClassName=com.mysql.jdbc.Driver


spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jpa.properties.hibernate.current_session_context_class=org.springframework.orm.hibernate4.SpringSessionContext
```
#### 步骤二：在dao层注入sessionFactory
```java
package com.yujin.dao;
import com.yujin.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
@Repository
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        return null;
    }
}

```
#### 步骤三：测试
```java
package com.yujin;
import com.yujin.model.User;
import com.yujin.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2016/8/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserTestService {

    @Autowired
    private UserService userService;

    @Test
    public void addUser(){
        User user = new User();
        user.setName("zs");
        user.setEmail("zs@sina.com");
        user.setPhone("15210461200");
        user.setDescription("测试方法");
        userService.saveUser(user);
    }
}

```
### 方式二：利用LocalSessionFactoryBuilder
#### 步骤一：配置properties配置文件
```properties

# 配置数据源
spring.db.driverClassName=com.mysql.jdbc.Driver
spring.db.url=jdbc:mysql://127.0.0.1/db_test?useUnicode=true&characterEncoding=UTF-8&useCursorFetch=true
spring.db.username=root
spring.db.password=123
spring.db.testOnBorrow=true
spring.db.validationQuery=SELECT 1

# 配置hibernate
## 配置实体类
hibernate.base.packages=com.yujin.model
##配置别名
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.show_sql=true
hibernate.cache.provider_class=org.hibernate.cache.EhCacheProvider
hibernate.cache.use_second_level_cache=false
hibernate.cache.use_query_cache=false
hibernate.current_session_context_class=org.springframework.orm.hibernate4.SpringSessionContext
```
#### 步骤二：将sessionFactory注入到spring容器中
```java
package com.yujin.config;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import javax.sql.DataSource;

/**
 * 数据库设置
 * @author yujin
 * @email yujin7@staff.sina.com.cn
 * @create 2016-09-28 13:32
 */
@Configuration
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.db")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SessionFactory sessionFactory(@Qualifier("dataSource")DataSource dataSource,
                                         @Value("${hibernate.base.packages}") String modelBasePackage,
                                         @Value("${hibernate.dialect}") String dialect,
                                         @Value("${hibernate.show_sql}") String showSql,
                                         @Value("${hibernate.cache.provider_class}") String cacheProviderClass,
                                         @Value("${hibernate.cache.use_second_level_cache}") String useSecondLevelCache,
                                         @Value("${hibernate.cache.use_query_cache}") String useQueryCache,
                                         @Value("${hibernate.current_session_context_class}") String currentSessionContextClass){

        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource).scanPackages(modelBasePackage);

        builder.setProperty("hibernate.dialect", dialect)
                .setProperty("hibernate.show_sql", showSql)
                .setProperty("hibernate.cache.provider_class", cacheProviderClass)
                .setProperty("hibernate.cache.use_second_level_cache", useSecondLevelCache)
                .setProperty("hibernate.cache.use_query_cache", useQueryCache)
                .setProperty("hibernate.current_session_context_class", currentSessionContextClass);
        return builder.buildSessionFactory();
    }
}
```
#### 步骤三：在dao层注入
```java
package com.yujin.dao;
import com.yujin.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        return null;
    }
}
```