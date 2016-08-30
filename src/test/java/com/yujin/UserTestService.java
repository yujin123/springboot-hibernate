package com.yujin;
import com.yujin.model.User;
import com.yujin.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2016/8/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserTestService {

    @Autowired
    private UserService userService;

    @Test
    public void addUser(){
        User user = new User();
        user.setName("zs");
        user.setEmail("zs@sina.com");
        user.setPhone("15210461200");
        user.setDescription("测试方法");
        userService.saveUser(user);
    }

}
