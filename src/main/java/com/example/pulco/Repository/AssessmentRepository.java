package com.example.pulco.Repository;

import com.example.pulco.DAO.AssessmentDAO;
import com.example.pulco.Model.Assessment;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AssessmentRepository {

    @Inject
    private AssessmentDAO assessmentDAO;

    public List<Assessment> findAll(){
        return assessmentDAO.getAssessments();
    }

    public Assessment findById(Integer id){
        return assessmentDAO.getAssessment(id);
    }
    public boolean add(Assessment assessment){
        return  assessmentDAO.addAssessment(assessment);
    }

    public boolean update(Assessment newAssessment){

        if (assessmentDAO.getAssessment(newAssessment.getId()) != null){
            return assessmentDAO.updateAssessment(newAssessment);
        }

        return false;
    }
    public boolean delete(Integer id){
        Assessment assessment = assessmentDAO.getAssessment(id);

        if(assessment != null){
            return assessmentDAO.deleteAssessment(assessment.getId());
        }

        return false;
    }
}
