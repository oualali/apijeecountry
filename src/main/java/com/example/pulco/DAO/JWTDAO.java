package com.example.pulco.DAO;

import com.example.pulco.Model.TokenBlackList;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.time.LocalDateTime;
import java.util.List;

public class JWTDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource()
    UserTransaction userTransaction;

    public TokenBlackList findToken(String token){
        List<TokenBlackList> tokenList = entityManager
                                                .createQuery("FROM TokenBlackList WHERE token = :token", TokenBlackList.class)
                                                .setParameter("token", token)
                                                .getResultList();

        if (!tokenList.isEmpty()){
            return tokenList.get(0);
        }

        return null;
    }

    public boolean add(String token, LocalDateTime exp){
        try{
            TokenBlackList JWT = new TokenBlackList(token, exp);

            userTransaction.begin();
            entityManager.persist(JWT);
            userTransaction.commit();

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
