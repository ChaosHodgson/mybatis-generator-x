package com.loess.dao.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/13
 * @since 1.0
 */
public class BaseTest {

    String resource = "configuration.xml";
    Reader reader = Resources.getResourceAsReader(resource);
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    public BaseTest() throws IOException {
    }

    @Test
    public void testAll() {
        SqlSession readSqlSession = sessionFactory.openSession();
        int x = readSqlSession.selectOne("com.loess.domain.XXX.count");


        System.out.print(x + "=========");
    }


}
