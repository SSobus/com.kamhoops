package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "users_sequence")
public class Users extends AbstractEntity {

    @NotNull
    private String email;

    @NotNull
    @Size(max = 60)
    private String password;

    private boolean passwordChangeRequired = false;

    private Date lastLoginDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = Calendar.getInstance().getTime();
    }
}
