package com.example.pulco.Repository;


import com.example.pulco.DAO.CountryDAO;
import com.example.pulco.Model.Assessment;
import com.example.pulco.Model.Country;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CountryRepository {

    @Inject
    private CountryDAO countryDAO;

    public List<Country> findAll(){
        return countryDAO.getAssessments();
    }

    public Country findById(Integer id){
        return countryDAO.getAssessment(id);
    }
    public boolean add(Country Country){
        return  countryDAO.addAssessment(Country);
    }

    public boolean update(Country Country){

        if (countryDAO.getAssessment(Country.getId()) != null){
            return countryDAO.updateAssessment(Country);
        }

        return false;
    }
    public boolean delete(Integer id){
        Country country= countryDAO.getAssessment(id);

        if(country != null){
            return countryDAO.deleteAssessment(country.getId());
        }

        return false;
    }
}
