package org.sid.secservice.Repository;


import org.sid.secservice.Entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long>
{
        AppRole findByRoleName(String roleName);
}
