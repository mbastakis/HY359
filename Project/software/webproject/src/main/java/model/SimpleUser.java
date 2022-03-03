/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.servlet.http.HttpServletRequest;
import model.User;

/**
 *
 * @author Mike
 */
public class SimpleUser extends User{

    int user_id;

    public SimpleUser(HttpServletRequest request) {
        username = request.getParameter("username");
        email = request.getParameter("email");
        password = request.getParameter("psw");
        firstname = request.getParameter("name");
        lastname = request.getParameter("surname");
        birthdate = request.getParameter("birthday");
        gender = request.getParameter("gender");
        amka = request.getParameter("amka");
        country = request.getParameter("country");
        city = request.getParameter("city");
        address = request.getParameter("address");
        lat = Double.parseDouble(request.getParameter("lat"));
        lon = Double.parseDouble(request.getParameter("lon"));
        telephone = request.getParameter("phone");
        height = request.getParameter("height").equals("") ? 0 : Integer.parseInt(request.getParameter("height"));
        weight = request.getParameter("weight").equals("") ? 0 : Double.parseDouble(request.getParameter("weight"));
        blooddonor = request.getParameter("blood-donor").equals("yes") ? 1 : 0;
        bloodtype = request.getParameter("blood-type");
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    
}
