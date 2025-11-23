/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

/**
 *
 * @author 20114
 */

public class Admin extends User{
    public Admin(String id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash, "admin");
    }
}
