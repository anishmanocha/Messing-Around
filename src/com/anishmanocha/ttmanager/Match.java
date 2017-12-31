package com.anishmanocha.ttmanager;

import java.util.List;

public class Match {
	
	private int matchId;
	
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	private Player playerOne;
	
	private Player playerTwo;
	
	private List<Integer> resultingScore;
	
	private int idOfWinner;
	
	private int idOfLoser;
	
	public Match(Player playerOne, Player playerTwo, List<Integer> resultingScore, int idOfWinner, int idOfLoser) {
		
		this.playerOne= playerOne;
		
		this.playerTwo= playerTwo;
		
		this.resultingScore= resultingScore;
		
		this.idOfWinner= idOfWinner;
		
	}
	
	public Match(Player playerOne, Player playerTwo) {
		
		this.playerOne= playerOne;
		
		this.playerTwo= playerTwo;
	}
	
	//Getters and Setters

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public List<Integer> getResultingScore() {
		return resultingScore;
	}

	public void setResultingScore(List<Integer> resultingScore) {
		this.resultingScore = resultingScore;
	}

	public int getIdOfWinner() {
		return idOfWinner;
	}

	public void setIdOfWinner(int idOfWinner) {
		this.idOfWinner = idOfWinner;
	}
	
	public int getIdOfLoser() {
		return idOfWinner;
	}

	public void setIdOfLoser(int idOfWinner) {
		this.idOfWinner = idOfWinner;
	}

	@Override
	public String toString() {
		return "Match [playerOne=" + playerOne + ", playerTwo=" + playerTwo + ", resultingScore=" + resultingScore
				+ ", idOfWinner=" + idOfWinner + ", idOfLoser=" + idOfLoser + "]";
	}
	
	
}
