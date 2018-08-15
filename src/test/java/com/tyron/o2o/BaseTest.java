package com.tyron.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: 测试基类
 *	配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml" })
public class BaseTest {

}
