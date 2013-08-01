package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import com.kamhoops.data.domain.enums.UserRole;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "users_sequence")
public class UserAccount extends AbstractEntity {

    @Column(unique = true)
    @Index(name = "username")
    @NotNull
    private String username;

    @Column(unique = true)
    @Index(name = "email")
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(max = 60)
    private String password;

    private boolean passwordChangeRequired = false;

    private Date lastLoginDate;

    private UserRole userRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Transient
    public boolean canDeleteEntities() {
        return getUserRole() == UserRole.ROLE_ADMIN;
    }
}
