package hello.services.impl;


import hello.models.inter.ProfileMapper;
import hello.models.inter.ProfileStockMapper;
import hello.models.inter.UserAccountMapper;
import hello.models.inter.UserRoleMapper;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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



    static {
        String resource = "/DataBaseGenerator/mybatis-config.xml";

        InputStream inputStream = BatisMappers.class.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();

        profileMapper = session.getMapper(ProfileMapper.class);
        profileStockMapper = session.getMapper(ProfileStockMapper.class);
        userAccountMapper = session.getMapper(UserAccountMapper.class);
        userRoleMapper = session.getMapper(UserRoleMapper.class);

    }
}
