    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.util.ArrayList;

/**
 *
 * @author 20114
 */

public class AuthService {
    private ArrayList<User> users;
    private Databasef db; 
    
    public AuthService(Databasef db)
    {
        this.db=db;
        users = db.readUsers();
    }
    
    public User login(String email, String password, String role)
    {
        String hashed = HashUtil.hashPassword(password);
        
        for(User u : users)
        {
            if(u.getEmail().equalsIgnoreCase(email) && u.getPasswordHash().equals(hashed) && u.getRole().equalsIgnoreCase(role))
                return u;
        }
        return null;
    }
    
    public boolean signup(String id, String name, String email, String password, String role)
    {
        if (db.idExists(id)) return false;

        if (emailExists(email)) return false;
        
        if (!db.isValidUsername(name) || !db.isValidEmail(email) || !db.isValidPassword(password)) {
              throw new IllegalArgumentException("Invalid input: Check username, email, or password.");
          }
        
        String hashed = HashUtil.hashPassword(password);
        
        User newUser;
        if(role.equalsIgnoreCase("student"))
        {
            newUser = new Student(id, name, email, hashed);
        }
        else
        {
            newUser = new Instructor(id, name, email, hashed);
        }
        
        users.add(newUser);
        
        db.writeUsers(users);
        
        return true;
    }
    
    private boolean emailExists(String email)
    {
        for(User u : users)
        {
            if(u.getEmail().equalsIgnoreCase(email))
            {
                return true;
            }
        }
        return false;
    }

}
