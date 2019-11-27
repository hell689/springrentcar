package net.springrentcar.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcUserRepo implements UserRepo {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_USER = "INSERT INTO users(username,password,enabled) VALUES(?, ?, ?)";
    private static final String SQL_INSERT_ROLES = "INSERT INTO user_roles (username, role) VALUES(?, ?)";

    public JdbcUserRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createUser(UserDetails user) {
        jdbcTemplate.update(SQL_INSERT_USER, user.getUsername(), user.getPassword(), user.isEnabled());
        for (GrantedAuthority authority : user.getAuthorities()) {
            jdbcTemplate.update(SQL_INSERT_ROLES, user.getUsername(), authority.getAuthority());
        }
    }
}
