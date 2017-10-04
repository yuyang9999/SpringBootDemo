package hello.services.impl;


import hello.models.DataSourcesUtility;
import hello.models.inter.*;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangyu on 3/10/17.
 */

public class BatisMappers {

    static public ProfileMapper profileMapper;

    static public ProfileStockMapper profileStockMapper;

    static public UserAccountMapper userAccountMapper;

    static public UserRoleMapper userRoleMapper;

    static public StockHistoryMapper stockHistoryMapper;

    static public StockSymbolMapper stockSymbolMapper;

    static {
        //invoke the datasource initializer
        DataSource ds = DataSourcesUtility.userDataSource;

        String resource = "/DataBaseGenerator/mybatis-config.xml";

        InputStream inputStream = BatisMappers.class.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession(true);

        profileMapper = session.getMapper(ProfileMapper.class);
        profileStockMapper = session.getMapper(ProfileStockMapper.class);
        userAccountMapper = session.getMapper(UserAccountMapper.class);
        userRoleMapper = session.getMapper(UserRoleMapper.class);
        stockHistoryMapper = session.getMapper(StockHistoryMapper.class);
        stockSymbolMapper = session.getMapper(StockSymbolMapper.class);
    }
}
