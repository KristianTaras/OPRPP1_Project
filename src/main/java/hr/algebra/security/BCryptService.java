package hr.algebra.security;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptService {

    private BCryptService(){}

    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String passwordHash){
        try{
            return BCrypt.checkpw(plainPassword, passwordHash);
        } catch(Exception e){
            return false;
        }
    }
}
