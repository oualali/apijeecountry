package com.example.pulco.DAO;

import com.example.pulco.Model.Assessment;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class AssessmentDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource()
    UserTransaction userTransaction;

    public List<Assessment> getAssessments() {
        return entityManager.createNativeQuery("select * from assessments", Assessment.class).getResultList();
    }

    public Assessment getAssessment(Integer id) {
        return entityManager.find(Assessment.class, id);
    }

    public boolean addAssessment(Assessment assessment) {
        try {
            userTransaction.begin();
            entityManager.persist(assessment);
            userTransaction.commit();

            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }

    public boolean updateAssessment(Assessment assessment) {
        try {
            userTransaction.begin();
            entityManager.merge(assessment);
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
            entityManager.remove(entityManager.find(Assessment.class, id));
            userTransaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "DAO ERROR :" + e.getMessage());
            return false;
        }
    }
}
