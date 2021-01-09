package org.sid.secservice.Web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.secservice.Entities.AppRole;
import org.sid.secservice.Entities.AppUser;
import org.sid.secservice.Entities.JwtUtil;
import org.sid.secservice.Entities.RoleUserForm;
import org.sid.secservice.Services.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController
{
    private AccountService accountService;

     public AccountRestController(AccountService accountService)
     {
         this.accountService = accountService;
     }
    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers()
    {
        return accountService.listUsers();
    }
    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }
    @PostMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }
    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
         accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());
    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
      String authToken= request.getHeader(JwtUtil.AUTH_HEADER);
      if(authToken!=null && authToken.startsWith(JwtUtil.PREFIX)){
          try {
              String Jwt=authToken.substring(JwtUtil.PREFIX.length());
              Algorithm algorithm=Algorithm.HMAC256(JwtUtil.SECRET);
              JWTVerifier jwtVerifier= JWT.require(algorithm).build();
              DecodedJWT decodedJWT=jwtVerifier.verify(Jwt);
              String username=decodedJWT.getSubject();
              AppUser appUser=accountService.loadUserByUsername(username);
              String jwtAccesToken= JWT.create()
                      .withSubject(appUser.getUsername())
                      .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.EXPIRE_ACCESS_TOKEN))
                      .withIssuer(request.getRequestURL().toString())
                      .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                      .sign(algorithm);
              Map<String,String> idToken=new HashMap<>();
              idToken.put("acces-token",jwtAccesToken);
              idToken.put("refresh-token",Jwt);
              response.setContentType("application/json");
              new ObjectMapper().writeValue(response.getOutputStream(),idToken);

          }
          catch (Exception e){
            throw e;
          }
      }
      else{
          throw new RuntimeException("Refresh token required!");
      }
    }
    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal)
    {
        return accountService.loadUserByUsername(principal.getName());
    }
}
