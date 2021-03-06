package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import com.kamhoops.data.domain.enums.UserRole;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(indexes = {@Index(name = "user_account_username_index", columnList = "username"),
        @Index(name = "user_account_email_index", columnList = "email")})
@SequenceGenerator(name = "id_gen", sequenceName = "users_sequence")
public class UserAccount extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String username;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @NotBlank
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
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
