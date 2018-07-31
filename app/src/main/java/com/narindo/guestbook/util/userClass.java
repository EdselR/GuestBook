package com.narindo.guestbook.util;


public class userClass {

    private String name;
    private String company;
    private String email;
    private String phone;
    private String type;
    private String purpose;
    private String signInTime;
    private String signOutTime;
    private String vkey = "";
    private String photoString = "";

    //constructor
    public userClass(){}

    public userClass(String name, String company, String email, String phone,String type, String purpose,
                     String signInTime, String signOutTime, String photoString){

        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.purpose = purpose;
        this.signInTime = signInTime;
        this.signOutTime = signOutTime;
        this.photoString = photoString;
    }


    //variable setters


    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public void setPhotoString(String photoString){this.photoString = photoString;}

    //variable getters
    public String getVkey(){
        return vkey;
    }

    public String getName(){
        return name;
    }

    public String getCompany(){
        return company;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getType(){
        return type;
    }

    public String getPurpose(){
        return purpose;
    }

    public String getSignInTime(){
        return signInTime;
    }

    public String getSignOutTime(){
        return signOutTime;
    }

    public String getPhotoString(){ return photoString; }
}
