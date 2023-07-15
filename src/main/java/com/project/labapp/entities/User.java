package com.project.labapp.entities;

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
    Long id;

    String userFirstName;

    String userLastName;

    String userName;

    String userImage;

    String userTC;

    String email;

    String password;

    Long roleId;





}
