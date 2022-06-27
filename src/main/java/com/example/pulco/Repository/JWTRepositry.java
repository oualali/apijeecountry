package com.example.pulco.Repository;

import com.example.pulco.DAO.JWTDAO;
import com.example.pulco.Model.TokenBlackList;
import com.example.pulco.Utils.JWT;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class JWTRepositry {

    @Inject
    private JWTDAO jwtDAO;

    public boolean exist(String token){
        System.out.println("looking for the token in the black list");
        return jwtDAO.findToken(token) != null ;
    }

    public boolean blackList(String token, LocalDateTime exp){
        return jwtDAO.add(token, exp);
    }
}
