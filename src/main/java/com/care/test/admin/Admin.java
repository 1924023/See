package com.care.test.admin;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String adminid;
    private String adminpw;

    public Admin() {
    }
    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminpw() {
        return adminpw;
    }

    public void setAdminpw(String adminpw) {
        this.adminpw = adminpw;
    }

}



