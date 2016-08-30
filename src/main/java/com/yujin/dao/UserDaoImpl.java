package com.yujin.dao;
import com.yujin.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
@Repository
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        return null;
    }
}
