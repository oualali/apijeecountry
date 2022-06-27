package com.example.pulco.Repository;

import com.example.pulco.DAO.UserDAO;
import com.example.pulco.Model.Credentials;
import com.example.pulco.Model.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserRepository {
    @Inject
    private UserDAO userDAO;

    public List<User> findAll(){
        return userDAO.getAll();
    }

    public User findById(Integer id){
        return userDAO.get(id);
    }

    private static final String HASH_ALGO = "SHA-256";
    public boolean add(User user){
        if(userDAO.findByEmail(user.getEmail()).isEmpty()){
            String hashedPassword = getHashedPassword(user);

            if (hashedPassword != null){
                user.setPassword(hashedPassword);
                user.setUpdatedAt(LocalDateTime.now());
                return userDAO.add(user);
            }
        }
        return  false;
    }

    public boolean update(User newUser){

        if (userDAO.get(newUser.getId()) != null){

            if (!newUser.getPassword().equals("")){
                newUser.setPassword(getHashedPassword(newUser));
                newUser.setUpdatedAt(LocalDateTime.now());
            }

            return userDAO.update(newUser);
        }

        return false;
    }

    private String getSalt(User user){
        return user.getCreatedAt().toString() + user.getEmail();
    }

    private String getHashedPassword(User user){
        return hash(user.getPassword(), getSalt(user));
    }

    private boolean checkPassword(User user, String password){
        return user.getPassword().equals(hash(password, getSalt(user)));
    }
    private String hash(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
            md.update(salt.getBytes());

            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }

            return sb.toString();
        }catch (Exception e){
            return null;
        }
    }

    public Credentials authenticate(Credentials credentials){

        List<User> userList = userDAO.findByEmail(credentials.getEmail());

        if(!userList.isEmpty()){
            User user = userList.get(0);

            if(checkPassword(user, credentials.getPassword())){
                credentials.setUserId(user.getId());
                credentials.setValid(true);
            }else {
                credentials.setValid(false);
                credentials.setError("Wrong password");
            }
        }else {
            credentials.setValid(false);
            credentials.setError("User not found");
        }

        return credentials;
    }
}
