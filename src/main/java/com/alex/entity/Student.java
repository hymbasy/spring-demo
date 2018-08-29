package com.alex.entity;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 5277326116504926418L;
    private Integer id;
    private String name;
    private String email;
    private String telephone;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Student() {
    }

    public Student(Integer id, String name, String email, String telephone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
    }
}
