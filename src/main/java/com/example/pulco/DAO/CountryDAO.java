package com.example.pulco.DAO;


import com.example.pulco.Model.Country;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource()
    UserTransaction userTransaction;

    public List<Country> getAssessments() {
        return entityManager.createNativeQuery("select * from countries", Country.class).getResultList();
    }

    public Country getAssessment(Integer id) {
        return entityManager.find(Country.class, id);
    }

    public boolean addAssessment(Country Country) {
        try {
            userTransaction.begin();
            entityManager.persist(Country);
            userTransaction.commit();

            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }

    public boolean updateAssessment(Country Country) {
        try {
            userTransaction.begin();
            entityManager.merge(Country);
            userTransaction.commit();

            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }

    public boolean deleteAssessment(int id) {
        try {
            userTransaction.begin();
            entityManager.remove(entityManager.find(Country.class, id));
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }
}
