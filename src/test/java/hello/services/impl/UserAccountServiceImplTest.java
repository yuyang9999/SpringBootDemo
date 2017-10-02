package hello.services.impl;

/**
 * Created by yangyu on 2/10/17.
 */
public class UserAccountServiceImplTest {
    @org.junit.Test
    public void testUserAccount() throws Exception {
        UserAccountServiceImpl impl = new UserAccountServiceImpl();
        impl.registerUser("test1", "test2", "test3");

        System.out.println(impl.getUserByUserName("test1"));
        System.out.println(impl.getUserByEmail("test2"));
    }

}