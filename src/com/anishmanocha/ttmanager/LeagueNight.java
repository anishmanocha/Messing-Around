package com.anishmanocha.ttmanager;

import java.util.Date;
import java.util.List;

public class LeagueNight {
	
	private int leagueNightId;
	
	private Date leagueDate;

	private List<Player> leaguePlayers;
		
	public LeagueNight(int leagueNightId, Date leagueDate) {
		
		this.leagueNightId=leagueNightId;
		
		this.leagueDate= leagueDate;
		
		
	}


	public int getLeagueNightId() {
		return leagueNightId;
	}


	public void setLeagueNightId(int leagueNightId) {
		this.leagueNightId = leagueNightId;
	}


	public Date getLeagueDate() {
		return leagueDate;
	}


	public void setLeagueDate(Date leagueDate) {
		this.leagueDate = leagueDate;
	}


	public List<Player> getLeaguePlayers() {
		return leaguePlayers;
	}


	public void setLeaguePlayers(List<Player> leaguePlayers) {
		this.leaguePlayers = leaguePlayers;
	}
	
	
}
