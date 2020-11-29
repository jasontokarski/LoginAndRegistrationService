package com.loginform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUid = -6295869253134269670L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private RoleName name;

    public RoleEntity() {

    }

    public RoleEntity(RoleName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
