package com.anishmanocha.ttmanager;

import java.util.List;

public class League {
	
	private int leagueId;
	
	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	private String leagueName;
	
	private List<Player> listOfPlayers; 
	
	private List<Match> listOfMatches;
	
	public League(String leagueName, List<Player> listOfPlayers, List<Match> listOfMatches) {
		
		this.leagueName= leagueName;
		
		this.listOfPlayers= listOfPlayers;
		
		this.listOfMatches=listOfMatches;
	}

	//Methods
	
	public void addPlayer(Player player) {
		
		this.listOfPlayers.add(player);
		
		System.out.println(player + " has been added");
		
	}
	
	public void removePlayer(Player player) {
		
		this.listOfPlayers.remove(player);
		
		System.out.println(player + " has been removed");
	}
	
	public void addMatch(Match match) {
		
		this.listOfMatches.add(match);
		
		System.out.println(match + " has been added");
	}
	
	public void removeMatch(Match match) {
		
		this.listOfMatches.remove(match);
		
		System.out.println(match + " has been removed");
	}
	
	
	//Getters and Setters

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(List<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public List<Match> getListOfMatches() {
		return listOfMatches;
	}

	public void setListOfMatches(List<Match> listOfMatches) {
		this.listOfMatches = listOfMatches;
	}

	@Override
	public String toString() {
		return "League [leagueName=" + leagueName + ", listOfPlayers=" + listOfPlayers + ", listOfMatches="
				+ listOfMatches + "]";
	}
	
	
}
