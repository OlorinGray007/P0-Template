package com.revature.controller;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.MoonService;

import io.javalin.http.Context;

public class MoonController {
	
	private MoonService mService = new MoonService();

	public void getAllMoons(Context ctx) {
		
		try {
		ctx.json(mService.getAllMoons()).status(200);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void getMoonByName(Context ctx) {
		
		try {
		User u = ctx.sessionAttribute("user");
		String moonName = ctx.pathParam("name");
		
		Moon m = mService.getMoonByName(u.getUsername(), moonName);
		
		ctx.json(m).status(200);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void getMoonById(Context ctx) {
		
		try {
		User u = ctx.sessionAttribute("user");
		int moonId = ctx.pathParamAsClass("id", Integer.class).get();
		
		Moon m = mService.getMoonById(u.getUsername(), moonId);
		
		ctx.json(m).status(200);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createMoon(Context ctx) {
		
		try {
		Moon m = ctx.bodyAsClass(Moon.class);
		User u = ctx.sessionAttribute("user");
		
		Moon outGoingMoon = mService.createMoon(u.getUsername(),m);
		
		ctx.json(outGoingMoon).status(201);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteMoon(Context ctx) {
		
		int moonId = ctx.pathParamAsClass("id", Integer.class).get();
		
		mService.deleteMoonById(moonId);
		
		ctx.json("Moon successfully deleted").status(202);
	}
	
	public void getPlanetMoons(Context ctx) {
		
		try {
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		List<Moon> moonList = mService.getMoonsFromPlanet(planetId);
		
		ctx.json(moonList).status(200);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
