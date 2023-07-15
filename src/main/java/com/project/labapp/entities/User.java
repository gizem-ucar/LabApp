package com.project.labapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userFirstName;

    String userLastName;

    String userName;

    String userImage;

    String userTC;

    String email;

    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id", nullable = false)
    @JsonIgnore   //ignore etmek
    Role role;

    public Long getRoleId() {
        return role != null ? role.getRoleId() : null;
    }

    public void setRoleId(Long roleId) {
        if (role == null) {
            role = new Role();
        }
        role.setRoleId(roleId);
    }


}
