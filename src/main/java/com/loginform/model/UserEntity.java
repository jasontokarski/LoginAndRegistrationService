package com.loginform.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "user_entity", uniqueConstraints = @UniqueConstraint(columnNames = {"user_name", "email"}))
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -6295869253157269670L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_guid")
    private Long userGuid;
    
    @Column(name = "user_name", unique = true)
    private String userName;
    
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name="creation_date")
    private Date creationDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="userEntity")
    @PrimaryKeyJoinColumn
    private CredentialEntity credentialEntity;
    
    public UserEntity() {
    }

    public UserEntity(Long userGuid, String userName, String password, String firstName, String lastName, String email, Date creationDate) {
        this.userGuid = userGuid;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
    }

    public Long getUserGuid() {
        return this.userGuid;
    }

    public void setUserGuid(Long userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UserEntity userGuid(Long userGuid) {
        this.userGuid = userGuid;
        return this;
    }

    public UserEntity userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserEntity password(String password) {
        this.password = password;
        return this;
    }

    public UserEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntity email(String email) {
        this.email = email;
        return this;
    }

    public UserEntity creationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserEntity)) {
            return false;
        }
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(userGuid, userEntity.userGuid) && Objects.equals(userName, userEntity.userName) && Objects.equals(password, userEntity.password) && Objects.equals(firstName, userEntity.firstName) && Objects.equals(lastName, userEntity.lastName) && Objects.equals(email, userEntity.email) && Objects.equals(creationDate, userEntity.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGuid, userName, password, firstName, lastName, email, creationDate);
    }

    @Override
    public String toString() {
        return "{" +
            " userGuid='" + getUserGuid() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }

    public CredentialEntity getCredentialEntity() {
        return credentialEntity;
    }

    public void setCredentialEntity(CredentialEntity credentialEntity) {
        this.credentialEntity = credentialEntity;
    }
 

}
