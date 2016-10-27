package com.zmap.config;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zmap.util.DatabaseType;
import com.zmap.util.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MyBatisConfig implements EnvironmentAware{

	private Environment environment;
	
	@Override	
	public void setEnvironment(Environment arg0) {
		// TODO Auto-generated method stub
        this.environment = environment;
	}
	
	@Bean
	public DataSource mysqlDataSource() throws Exception {
		 Properties props = new Properties();
	     props.put("driverClassName", environment.getProperty("spring.mysql.driver-class-name"));
	     props.put("url", environment.getProperty("pring.mysql.url"));
	     props.put("username", environment.getProperty("pring.mysql.username"));
	     props.put("password", environment.getProperty("pring.mysql.password"));
	     return DruidDataSourceFactory.createDataSource(props);
	}
	
	@Bean
    public DataSource sqlserverDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("spring.sqlserver.driver-class-name"));
        props.put("url", environment.getProperty("spring.sqlserver.url"));
        props.put("username", environment.getProperty("spring.sqlserver.username"));
        props.put("password", environment.getProperty("spring.sqlserver.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }
	
	 /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                        @Qualifier("sqlserverDataSource") DataSource sqlserverDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.sqlserver, mysqlDataSource);
        targetDataSources.put(DatabaseType.mysql, sqlserverDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(sqlserverDataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }
	
	/**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        fb.setTypeAliasesPackage("com.huan.model");// 指定基包
        fb.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));//

        return fb.getObject();
    }
    

	 /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
