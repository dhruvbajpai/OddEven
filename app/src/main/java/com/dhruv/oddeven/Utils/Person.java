package com.dhruv.oddeven.Utils;

/**
 * Created by Dhruv on 24-Dec-15.
 */
public class Person {

    String name;
    String id;
    String email;
    String gender;
    String phone_no;
    String age;
    String carType; // 0 is even, 1 is odd
    String sourceAddress;
    String destinationAddress;
    String numberPlate;
    String gcm_regid;

    public Person(String name, String gender, String carType) {
        this.name = name;
        this.gender = gender;
        this.carType = carType;
    }

    public Person(String name, String id,String email,String phone_no, String gender,String age,String carType, String sourceAddress, String destinationAddress, String numberPlate,String gcm_regid) {
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.id = id;
        this.gcm_regid = gcm_regid;
        this.gender = gender;
        this.age = age;
        this.carType = carType;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.numberPlate = numberPlate;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getGcm_regid() {
        return gcm_regid;
    }

    public void setGcm_regid(String gcm_regid) {
        this.gcm_regid = gcm_regid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
