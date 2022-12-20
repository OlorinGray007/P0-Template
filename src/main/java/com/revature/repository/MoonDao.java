package com.revature.repository;

import java.util.List;

import com.revature.models.Moon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() throws SQLException{
		try (Connection con = ConnectionUtil.createConnection()){
			String sql = "select * from moons";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			List<Moon> moons = new ArrayList();

			while (rs.next()) {
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName((rs.getString(2)));
				moon.setMyPlanetId((rs.getInt(3)));

				moons.add(moon);
			}
			return moons;
		}
	}

	public Moon getMoonByName(String username, String moonName) throws SQLException{
		try (Connection con = ConnectionUtil.createConnection()) {
			String sql = "select * from moons where name = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, moonName);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Moon moon = new Moon();

			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));

			return moon;
		}
	}

	public Moon getMoonById(String username, int moonId) throws SQLException{
		try (Connection con = ConnectionUtil.createConnection()) {
			String sql = "select * from moons where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, moonId);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Moon moon = new Moon();

			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));

			return moon;
		}
	}

	public Moon createMoon(String username, Moon m) throws SQLException{
		try (Connection con = ConnectionUtil.createConnection()){
			String sql = "insert into moons values (default, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();

			m.setId(rs.getInt(1));

			return m;
		}
	}

	public void deleteMoonById(int moonId) {
		try (Connection con = ConnectionUtil.createConnection()){
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, moonId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected " + rowsAffected);

		}catch (SQLException e){
			System.out.println(e.getMessage());
		} 
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException{
		try (Connection con = ConnectionUtil.createConnection()){
			String sql = "select * from moons where myplanetid = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, planetId);

			ResultSet rs = ps.executeQuery();

			List<Moon> moons = new ArrayList();

			while (rs.next()) {
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName((rs.getString(2)));
				moon.setMyPlanetId((rs.getInt(3)));

				moons.add(moon);
			}
			return moons;
		}
	}

	public static void main(String[] args) {
		MoonDao moonDao = new MoonDao();
		try {
			System.out.println(moonDao.getAllMoons());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(moonDao.getMoonById("Jerry", 1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(moonDao.getMoonsFromPlanet(1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}