package com.yujin.service;
import com.yujin.dao.UserDao;
import com.yujin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * Created by Administrator on 2016/8/30.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }
}
