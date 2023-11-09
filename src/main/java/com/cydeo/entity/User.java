package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="users")

public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean enabled;
    private String phone;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
