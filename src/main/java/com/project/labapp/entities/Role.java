package com.project.labapp.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
@Entity
public class Role {

    @Id
    Long roleId;
    String roleName;

}
