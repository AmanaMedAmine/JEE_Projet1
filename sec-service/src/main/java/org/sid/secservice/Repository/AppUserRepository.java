package org.sid.secservice.Repository;

import org.sid.secservice.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long>
{
    AppUser findByUsername(String username);
}
