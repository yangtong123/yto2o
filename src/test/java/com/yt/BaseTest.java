package com.yt;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:20
 */

@RunWith(SpringJUnit4ClassRunner.class)
// 告诉 junit spring 配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {

}
