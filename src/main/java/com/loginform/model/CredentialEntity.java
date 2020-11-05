package com.loginform.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="credential_entity")
public class CredentialEntity implements Serializable {
    
    @Id
    @Column(name="user_guid")
    private Long userGuid;

    @OneToOne
    @JoinColumn(name="user_guid")
    @MapsId
    private UserEntity userEntity;

    
    @Column(name="user_name")
    private String userName;

    @Column(name="token")
    private String token;

    @Column(name="api_key")
    private String apiKey;

    private static final long serialVersionUID = -187829906843995622L;


    public CredentialEntity() {
    }

    public CredentialEntity(String userName, String token, String apiKey) {
        this.userName = userName;
        this.token = token;
        this.apiKey = apiKey;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public CredentialEntity userEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public CredentialEntity userName(String userName) {
        this.userName = userName;
        return this;
    }

    public CredentialEntity token(String token) {
        this.token = token;
        return this;
    }

    public CredentialEntity apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CredentialEntity)) {
            return false;
        }
        CredentialEntity credentialEntity = (CredentialEntity) o;
        return Objects.equals(userEntity, credentialEntity.userEntity) && Objects.equals(userName, credentialEntity.userName) && Objects.equals(token, credentialEntity.token) && Objects.equals(apiKey, credentialEntity.apiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEntity, userName, token, apiKey);
    }

    @Override
    public String toString() {
        return "{" +
            " userEntity='" + getUserEntity() + "'" +
            ", userName='" + getUserName() + "'" +
            ", token='" + getToken() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            "}";
    }

    public Long getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(Long userGuid) {
        this.userGuid = userGuid;
    }
 
}
