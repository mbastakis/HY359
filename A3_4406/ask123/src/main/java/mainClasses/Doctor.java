/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainClasses;

import javax.servlet.http.HttpServletRequest;
import mainClasses.User;

/**
 *
 * @author Mike
 */
public class Doctor extends User{
    int doctor_id;
    String specialty, doctor_info;

    public Doctor(HttpServletRequest request) {
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
        specialty = request.getParameter("speciality");
        doctor_info = request.getParameter("doc-information");
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_info() {
        return doctor_info;
    }

    public void setDoctor_info(String doctor_info) {
        this.doctor_info = doctor_info;
    }

    
    
    int certified;

    public int getCertified() {
        return certified;
    }

    public void setCertified(int certified) {
        this.certified = certified;
    }

   

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

  
}
