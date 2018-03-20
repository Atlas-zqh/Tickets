package com.keenan.Tickets.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author keenan on 09/02/2018
 */
@Entity
@Table(name = "roles")
public class SysRole {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
