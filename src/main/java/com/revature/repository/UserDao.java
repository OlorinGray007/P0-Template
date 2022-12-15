package com.revature.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username){
        return null;
    }

    public User createUser(UsernamePasswordAuthentication registerRequest){
        try(Connection con = ConnectionUtil.createConnection()) {

            String sql = "insert into users values (default, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            User newUser = new User();
            rs.next();

            int newId = rs.getInt("id");
            newUser.setId(newId);
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            return newUser;

        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();

        UsernamePasswordAuthentication newUser = new UsernamePasswordAuthentication();
        newUser.setUsername("lombok");
        newUser.setPassword("pass0word");
        System.out.println(dao.createUser(newUser).getId());
    }

}
