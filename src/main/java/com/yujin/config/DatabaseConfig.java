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