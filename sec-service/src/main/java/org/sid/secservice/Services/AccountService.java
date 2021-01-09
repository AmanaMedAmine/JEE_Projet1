package org.sid.secservice.Services;

import org.sid.secservice.Entities.AppRole;
import org.sid.secservice.Entities.AppUser;
import java.util.List;
public interface AccountService
{
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
