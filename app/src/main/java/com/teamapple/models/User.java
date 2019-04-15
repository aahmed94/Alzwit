package com.teamapple.models;

import java.util.Date;

public class User {
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private Date birthday;
    private String phoneNumber;
    private EmergencyUser emergencyUser;

    public User(String firstName, String middleName, String lastName, String address, Date birthday, String phoneNumber, EmergencyUser emergencyUser) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.emergencyUser = emergencyUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirtday() {
        return birthday;
    }

    public void setBirtday(Date birtday) {
        this.birthday = birtday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmergencyUser getEmergencyUser() {
        return emergencyUser;
    }

    public void setEmergencyUser(EmergencyUser emergencyUser) {
        this.emergencyUser = emergencyUser;
    }

    public String getFullName(){
        String middleName = this.middleName.isEmpty()? " " : " "+this.middleName+" ";
        return this.firstName+ middleName +this.lastName;
    }
}
