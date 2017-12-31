package com.anishmanocha.ttmanager;

public class RoundRobinPool {
	
	private int idRoundRobinPool;
	
	private int foreignKeyLeagueId;
	
	public RoundRobinPool(int idRoundRobinPool) {
		
		this.idRoundRobinPool= idRoundRobinPool;
		
	}
	
public RoundRobinPool(int roundRobinPoolId, int foreignKeyLeagueId) {
		
		this.idRoundRobinPool= roundRobinPoolId;
		
		this.foreignKeyLeagueId= foreignKeyLeagueId;
		
	}

public int getIdRoundRobinPool() {
	return idRoundRobinPool;
}

public void setIdRoundRobinPool(int idRoundRobinPool) {
	this.idRoundRobinPool = idRoundRobinPool;
}

public int getForeignKeyLeagueId() {
	return foreignKeyLeagueId;
}

public void setForeignKeyLeagueId(int foreignKeyLeagueId) {
	this.foreignKeyLeagueId = foreignKeyLeagueId;
}




}
