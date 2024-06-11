package org.example.softunifinalproject.model.entity;

import jakarta.persistence.*;
import org.example.softunifinalproject.model.enums.RoleType;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
