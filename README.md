# spring-demo
1.首先集成spring配置 spring.xml 配置如下：

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 自动扫描的包 -->
    <context:component-scan base-package="com.alex.*"/>

    <!-- 引入配置文件 -->
    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations" value="classpath:db.properties"/>
    </bean>

    <!-- 配置dataSource -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driverClass}"></property>
        <property name="jdbcUrl" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>

    <!-- spring的mybatis工厂类 -->
    <bean id="sqlSessionFactory" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置事务管理器 -->
    <bean id="txManage" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--开启了对注解的解析-->
    <tx:annotation-driven transaction-manager="txManage" proxy-target-class="true"/>

    <!-- 配置事务传播特性 -->
    <tx:advice id="txAdvice" transaction-manager="txManage">
        <tx:attributes>
            <tx:method name="get" propagation="REQUIRED"/>
            <tx:method name="update" propagation="REQUIRED"/>
            <tx:method name="delete" propagation="REQUIRED"/>
            <tx:method name="insert" propagation="REQUIRED"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>


    <!--编织事务切面-->
    <aop:config>
        <aop:pointcut expression="execution(* com.alex.service.*.*(..))" id="pointcut"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
    </aop:config>

    <!--jdbcTemplate 配置-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>


    <!-- 连接池配置 -->
    <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <!-- 最大连接数 -->
        <property name="maxTotal" value="30"/>
        <!-- 最大空闲连接数 -->
        <property name="maxIdle" value="10"/>
        <!-- 每次释放连接的最大数目 -->
        <property name="numTestsPerEvictionRun" value="1024"/>
        <!-- 释放连接的扫描间隔（毫秒） -->
        <property name="timeBetweenEvictionRunsMillis" value="30000"/>
        <!-- 连接最小空闲时间 -->
        <property name="minEvictableIdleTimeMillis" value="1800000"/>
        <!-- 连接空闲多久后释放, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放 -->
        <property name="softMinEvictableIdleTimeMillis" value="10000"/>
        <!-- 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1 -->
        <property name="maxWaitMillis" value="1500"/>
        <!-- 在获取连接的时候检查有效性, 默认false -->
        <property name="testOnBorrow" value="true"/>
        <!-- 在空闲时检查有效性, 默认false -->
        <property name="testWhileIdle" value="true"/>
        <!-- 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true -->
        <property name="blockWhenExhausted" value="false"/>
    </bean>

    <!--jedis 客户端单机版 -->
    <bean id="redisClient" class="redis.clients.jedis.JedisPool">
        <constructor-arg index="0" ref="jedisPoolConfig"></constructor-arg>
        <constructor-arg index="1" value="10.10.1.30" type="java.lang.String"></constructor-arg>
        <constructor-arg index="2" value="6379" type="int"></constructor-arg>
    </bean>

</beans>

注意：在配置jedis客户端单机的时候，原来配置为<constructor-arg name=“host” value="10.10.1.30" ></constructor-arg>.新版本Jedispool里面没有name参数，改成了index参数，通过指定参数的index，来匹配JedisPool的构造函数.注意后面要加上type=“xxx”，不然的话会报错.

2.接下来集成springmvc配置，springmvc.xml配置如下：
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--开启注解-->
    <mvc:annotation-driven/>
    <!-- 配置支持标注 -->
    <context:annotation-config/>

    <!-- 设置使用注解的类所在位置 -->
    <context:component-scan base-package="com.alex.controller"/>

    <!-- 对模型视图名称的解析，在请求时模型视图名称添加前后缀 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" p:prefix="/WEB-INF/page/"
          p:suffix=".jsp"/>


</beans>
