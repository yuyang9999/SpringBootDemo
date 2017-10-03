package hello.services.impl;

import hello.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yangyu on 2/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserAccountServiceImplTest {
    @Test
    public void testUserAccount() throws Exception {
        UserAccountServiceImpl impl = new UserAccountServiceImpl();
        impl.registerUser("test1", "test2", "test3");
        System.out.println(impl.getUserByEmail("test2"));
        System.out.println(impl.getUserByUserName("test1"));
    }



}