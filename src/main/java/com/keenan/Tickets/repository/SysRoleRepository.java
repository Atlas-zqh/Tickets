package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 10/02/2018
 */
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
    SysRole findSysRoleByName(String name);
}
