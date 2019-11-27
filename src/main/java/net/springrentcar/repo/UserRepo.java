package net.springrentcar.repo;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo {
    void createUser(UserDetails user);
}
