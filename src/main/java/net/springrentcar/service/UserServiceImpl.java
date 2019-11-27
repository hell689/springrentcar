package net.springrentcar.service;

import net.springrentcar.repo.UserRepo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder encoder;
    private UserRepo userRepo;

    public UserServiceImpl(BCryptPasswordEncoder encoder, UserRepo userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @Override
    public void createUser(String username, String password, String role) throws DuplicateKeyException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        UserDetails user = new User(username, encoder.encode(password), authorities);
        userRepo.createUser(user);
    }
}
