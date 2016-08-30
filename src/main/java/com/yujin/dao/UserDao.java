package com.yujin.dao;
import com.yujin.model.User;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public interface UserDao {

    void save(User user);

    List<User> listUsers();
}
