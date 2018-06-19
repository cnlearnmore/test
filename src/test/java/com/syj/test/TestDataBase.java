package com.syj.test;

import com.syj.test.dao.UserDao;
import com.syj.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")  //这行一定不要轻易加，可能会删除数据库数据
public class TestDataBase {
    @Autowired
    UserDao userDao;

    @Test
    public void testOne(){

        System.out.println("11111");
        User user = userDao.selectById(3);
        System.out.println("user.getName" + user.getName());
        System.out.println("2222");
        //System.out.println(user.getName());
    }

}
