package com.example.pulco.DAO;


import com.example.pulco.Model.User;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource()
    UserTransaction userTransaction;

    public List<User> getAll(){
        return entityManager.createNativeQuery("select * from users", User.class).getResultList();
    }

    public User get(Integer id){
        return entityManager.find(User.class, id);
    }

    public List<User> findByEmail(String email){
        return entityManager
                        .createQuery("FROM User WHERE email = :email", User.class)
                        .setParameter("email", email)
                        .getResultList();
    }

    public boolean add(User user){
        try {
            userTransaction.begin();
            entityManager.persist(user);
            userTransaction.commit();

            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }

    public boolean update(User user){
        try {
            userTransaction.begin();
            entityManager.merge(user);
            userTransaction.commit();

            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }
    public boolean delete(int id){
        try {
            userTransaction.begin();
            entityManager.remove(entityManager.find(User.class, id));
            userTransaction.commit();
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }
}
