package com.care.test.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String adminId;
    private String adminPw;

    public Admin() {
    }
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminpw() {
        return adminPw;
    }

    public void setAdminPw(String adminpw) {
        this.adminPw = adminPw;
    }

}