package com.anishmanocha.ttmanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/RoundRobinControllerServlet")
public class RoundRobinControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/ttmanager")
	DataSource datasource;

	Connection connection;

	PreparedStatement statement;

	ResultSet results;

	public RoundRobinControllerServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("command");

		if (command == null) {

			command = "List";
		}

		switch (command) {

		case "generateRoundRobinPools":
			generateRoundRobinPools(request, response);
			break;

		case "redirectToRoundRobinPool":
			redirectToRoundRobinPools(request, response);
			break;

		case "addRoundRobinPool":
			addRoundRobinPools(request, response);
			break;
		
		case "generatePairings":
			generatePairings(request,response);
			break;

		default:
			getListOfRoundRobinPools(request, response);
			break;

		}

	}

	private void generatePairings(HttpServletRequest request, HttpServletResponse response) {
		
		int leagueNightId =Integer.parseInt(request.getParameter("leagueNightId"));
		
		ArrayList<Integer>listOfRoundRobinPoolsId= new ArrayList<Integer>();
		
		try {
			
			String sql= "Select idRoundRobinPool from RoundRobinPool where FKRRLeagueNight= ?";
			
			connection=datasource.getConnection();
			
			statement=connection.prepareStatement(sql);
			
			statement.setInt(1, leagueNightId);
			
			results= statement.executeQuery();
			
			while (results.next()) {
				
				listOfRoundRobinPoolsId.add(results.getInt(1));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
		for (int i=0; i<listOfRoundRobinPoolsId.size(); i++) {
			try {
				String sql= "Select FKidPlayer from RoundRobinPoolPlayer where FKidRoundRobinPool= ?";
				
				connection= datasource.getConnection();
				
				statement=connection.prepareStatement(sql);
				
				statement.setInt(1, listOfRoundRobinPoolsId.get(i));
				
				results= statement.executeQuery();
				
				List<Integer> listOfPlayerIds= new ArrayList<Integer>();
				
				while (results.next()) {
					
					//Makes pair from the list you have, create a match, insert into player match (what players in each match)
					
					listOfPlayerIds.add(results.getInt(1));
					
				}
				
				
				for (int j=0; j<listOfPlayerIds.size(); j++) {
					
					for (int k=j+1; k<listOfPlayerIds.size(); k++) {
						
						try {
							String queryToCreateMatches= "Insert into ttmanager.match (FKRRPool) values(?)";
							
							connection=datasource.getConnection();
							
							statement= connection.prepareStatement(queryToCreateMatches);
							
							statement.setInt(1, listOfRoundRobinPoolsId.get(i));
							
							statement.execute();
							
							
						}
						catch(Exception e) {
							
							e.printStackTrace();
						}
						
						finally {
							
							close(connection, statement, results);
						}
						
					}
				}
				
			}
			
			catch(Exception e) {
				
				e.printStackTrace();
			}
			
			finally {
				
				close(connection, statement, results);
			}
			
		}
		
	}

	private void redirectToRoundRobinPools(HttpServletRequest request, HttpServletResponse response) {
		String leagueNightId = request.getParameter("leagueNightId");

		request.setAttribute("leagueNightId", request.getParameter("leagueNightId"));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/add-round-robin-pool.jsp");

		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		

	}

	private void addRoundRobinPools(HttpServletRequest request, HttpServletResponse response) {

		String leagueNightId = request.getParameter("leagueNightId");

		String numberOfRoundRobinPoolsString = request.getParameter("numberOfRoundRobinPools");

		int numberOfRoundRobinPools = Integer.parseInt(numberOfRoundRobinPoolsString);

		for (int i = 0; i < numberOfRoundRobinPools; i++) {

			String sql = "Insert into RoundRobinPool (FKRRLeagueNight) values (?)";

			try {
				connection = datasource.getConnection();

				statement = connection.prepareStatement(sql);

				statement.setInt(1, Integer.parseInt(leagueNightId));

				statement.execute();

			}

			catch (Exception e) {

				e.printStackTrace();
			}

			finally {

				close(connection, statement, results);
			}

		}

		String otherSqlStatement = "Select * from RoundRobinPool where FKRRLeagueNight= ?";

		try {
			connection = datasource.getConnection();

			statement = connection.prepareStatement(otherSqlStatement);

			statement.setInt(1, Integer.parseInt(leagueNightId));

			ResultSet results = statement.executeQuery();

			List<RoundRobinPool> listOfRoundRobinPools = new ArrayList<RoundRobinPool>();

			while (results.next()) {

				results.getInt("idRoundRobinPool");

				RoundRobinPool myRoundRobinPool = new RoundRobinPool(results.getInt("idRoundRobinPool"),
						Integer.parseInt(leagueNightId));

				listOfRoundRobinPools.add(myRoundRobinPool);

			}

			request.setAttribute("listOfRoundRobinPools", listOfRoundRobinPools);

			request.setAttribute("leagueNightId", request.getParameter("leagueNightId"));

			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-round-robin-pools.jsp");

			dispatcher.forward(request, response);

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void generateRoundRobinPools(HttpServletRequest request, HttpServletResponse response) {
		
		//We start with league night ID/number of players we want to add from each pool
		String leagueNightId = request.getParameter("leagueNightId");

		String numberOfIndividualsPerPool = request.getParameter("playersPerPool");
		
		System.out.println("League Night ID " + leagueNightId + " players per round robin pool" + numberOfIndividualsPerPool);
		
		//Converting strings to integers

		int numericalNumberOfIndividualsPerPool = Integer.parseInt(numberOfIndividualsPerPool);
		
		int indexNumberOfIndividualsPerPool= numericalNumberOfIndividualsPerPool-1;
		
		int parsedLeagueNightId= Integer.parseInt(leagueNightId);
		
		//Lists for use later
		
		ArrayList<Integer> listOfRoundRobinPoolsId= new ArrayList<Integer>();

		ArrayList<Integer> listOfPlayerIdsInLeagueNight = new ArrayList<Integer>();

		ArrayList<Player> listOfPlayersInLeagueNight = new ArrayList<Player>();
		
		ArrayList<Player> listOfPlayersToSendToFrontEnd= new ArrayList<Player>();
		
		ArrayList<Integer> listOfPlayerIdsToSendToFrontEnd= new ArrayList<Integer>();
		
		ArrayList<RoundRobinPool> listOfRoundRobinPools= new ArrayList<RoundRobinPool>();
		
		
		//For later use 
		
		int numberOfRoundRobinPools= 0;
		
		
		//Get list of all player IDs in a league night
		try {
			
			String sql = "Select lnPlayerFK from LeagueNightPlayer where lnLeagueNightFK= ?";

			connection = datasource.getConnection();

			statement = connection.prepareStatement(sql);

			statement.setInt(1, parsedLeagueNightId);

			ResultSet results = statement.executeQuery();

			while (results.next()) {

				listOfPlayerIdsInLeagueNight.add(results.getInt("lnPlayerFK"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {

			close(connection, statement, results);
		}
		
		
		
		//With all those player ids in league nights, use them to add players to a list of players in league night
		
		try {
			
			for (int i = 0; i < listOfPlayerIdsInLeagueNight.size(); i++) {

				String sql = "Select * from Player where idPlayer= ?";
				
				connection = datasource.getConnection();

				statement = connection.prepareStatement(sql);

				statement.setInt(1, listOfPlayerIdsInLeagueNight.get(i));

				results = statement.executeQuery();

				while (results.next()) {

					int playerId = results.getInt("idPlayer");

					String firstName = results.getString("firstName");

					String lastName = results.getString("lastName");

					int rating = results.getInt("rating");

					String email = results.getString("email");

					Player player = new Player(playerId, firstName, lastName, rating, email);

					listOfPlayersInLeagueNight.add(player);

				}

			}
				
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
		
	

		//After this query, we have all the players in a league night and the number of pools in a league night
		try {
			
			String sqlForNumberOfPools= "Select count(idRoundRobinPool) from RoundRobinPool where FKRRLeagueNight= ?";
			
			connection = datasource.getConnection();
			
			statement= connection.prepareStatement(sqlForNumberOfPools);
			
			statement.setInt(1, parsedLeagueNightId);
			
			results= statement.executeQuery();
			
			while (results.next()) {
				
				numberOfRoundRobinPools= results.getInt(1);
				
			}
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		finally {

			close(connection, statement, results);
		}
		
		
		//Now, we have list of players in league night, number of round robin pools, and every round robin pool id
		
		
		try {
			
			String sqlForNumberOfPools= "Select * from RoundRobinPool where FKRRLeagueNight= ?";
			
			connection = datasource.getConnection();
			
			statement= connection.prepareStatement(sqlForNumberOfPools);
			
			statement.setInt(1, parsedLeagueNightId);
			
			results= statement.executeQuery();
			
			
			
			while (results.next()) {
				
				listOfRoundRobinPoolsId.add(results.getInt("idRoundRobinPool"));
				
				listOfRoundRobinPools.add(new RoundRobinPool(results.getInt("idRoundRobinPool"), results.getInt("FKRRLeagueNight")));
				
			}
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		finally {

			close(connection, statement, results);
		}
		
		
		//Sorting list of players in league Night by rating
		
		listOfPlayersInLeagueNight.sort(Comparator.comparing(Player::getRating));
		System.out.println("Pre Insertion");
		for (int i=0; i<listOfPlayersInLeagueNight.size(); i++) {
		
		System.out.println(listOfPlayersInLeagueNight.get(i));
	}
		
		
		
		//Now, we have list of players in league night, number of round robin pools, and every round robin pool id
		
		int startingPerson=0;
		
		int endingPerson=0;
		
		boolean flag= true;
			
			for (int i=0; i<numberOfRoundRobinPools; i++) {

					for (int k=0; k<listOfPlayersInLeagueNight.size(); k++) {
						
						if (i==0) {
							
							startingPerson=0;
							
							endingPerson=numericalNumberOfIndividualsPerPool-1;
							
						}
						
						else if (flag && k+numericalNumberOfIndividualsPerPool < listOfPlayersInLeagueNight.size()) {
							
							k=k+numericalNumberOfIndividualsPerPool;
						}
						
						
						
				
						try {
							
							String sqlToInsertPlayerInRRPool= "Insert into RoundRobinPoolPlayer (FKidRoundRobinPool, FKidPlayer) values (?, ?)";
							
							connection = datasource.getConnection();
							
							statement= connection.prepareStatement(sqlToInsertPlayerInRRPool);
							
							statement.setInt(1,listOfRoundRobinPoolsId.get(i));
							
							statement.setInt(2, listOfPlayersInLeagueNight.get(k).getPlayerId());
														
							statement.execute();
							
						}
						
						catch(Exception e) {
							
							e.printStackTrace();
						}
						
						finally {

							close(connection, statement, results);
						}
				
						if (k==endingPerson) {
							
							startingPerson=endingPerson+1;
							
							endingPerson= endingPerson + numericalNumberOfIndividualsPerPool;
							
							flag=true;
							
							break;
							
						}	
						
						flag= false;
						
					}
					
			

				
			
			}
			
			for (int i=0; i<listOfRoundRobinPoolsId.size(); i++) {
			
			try {
				
				String sqlForInsertingPlayerIntoRRPool= "Select FKidPlayer from RoundRobinPoolPlayer where FKidRoundRobinPool=? ORDER BY FKidRoundRobinPool";
				
				connection = datasource.getConnection();
				
				statement= connection.prepareStatement(sqlForInsertingPlayerIntoRRPool);
				
				statement.setInt(1, listOfRoundRobinPoolsId.get(i));
				
				results= statement.executeQuery();
				
				while (results.next()) {
					
					int playerId= results.getInt(1);
					
					listOfPlayerIdsToSendToFrontEnd.add(playerId);
					
					
					
				}
				
			}
			
			catch(Exception e) {
				
				e.printStackTrace();
			}
			
			finally {

				close(connection, statement, results);
			}
			
			
			
			
			
			/* String followUpQuery= "Select * from Player where idPlayer= ?";
					
					statement= connection.prepareStatement(followUpQuery);
					
					statement.setInt(1, playerId);
					
					results= statement.executeQuery();
					
					while (results.next()) {
						
						int idPlayer= results.getInt("idPlayer");
						
						String firstName= results.getString("firstName");
						
						String lastName= results.getString("lastName");
						
						int playerRating= results.getInt("rating");
						
						String email= results.getString("email");
						
						Player tempPlayer= new Player(idPlayer, firstName, lastName, playerRating, email);
						
						listOfPlayersToSendToFrontEnd.add(tempPlayer);
							
					}*/
			
			
						
				
		}
			
			for (int i=0; i<listOfPlayerIdsInLeagueNight.size(); i++) {
				
				try {
					
					String followUpQuery= "Select * from Player where idPlayer= ?";
					
					connection = datasource.getConnection();
					
					statement= connection.prepareStatement(followUpQuery);
					
					statement.setInt(1, listOfPlayerIdsToSendToFrontEnd.get(i));
					
					results= statement.executeQuery();
					
					while (results.next()) {
						
						int idPlayer= results.getInt("idPlayer");
						
						String firstName= results.getString("firstName");
						
						String lastName= results.getString("lastName");
						
						int playerRating= results.getInt("rating");
						
						String email= results.getString("email");
						
						Player tempPlayer= new Player(idPlayer, firstName, lastName, playerRating, email);
						
						listOfPlayersToSendToFrontEnd.add(tempPlayer);
						
						
						
					}
					
				}
				
				catch(Exception e) {
					
					e.printStackTrace();
				}
				
				finally {

					close(connection, statement, results);
				}
				
			}
			
			System.out.println("Post Insertion");
			
			listOfPlayersToSendToFrontEnd.sort(Comparator.comparing(Player::getRating));
			
			for (int i=0; i<listOfPlayersToSendToFrontEnd.size(); i++) {
				
				System.out.println(listOfPlayersToSendToFrontEnd.get(i));
			}
			
			
			request.setAttribute("listOfRoundRobinPools", listOfRoundRobinPools);
			
			request.setAttribute("listOfPlayersToSendToFrontEnd", listOfPlayersToSendToFrontEnd);
			
			request.setAttribute("playersPerGroup", numericalNumberOfIndividualsPerPool);
			
			request.setAttribute("indexPlayersPerGroup", indexNumberOfIndividualsPerPool);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-round-robin-pools.jsp");

			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				
				e.printStackTrace();
			}
			
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private void getListOfRoundRobinPools(HttpServletRequest request, HttpServletResponse response) {

		String leagueNightId = request.getParameter("leagueNightId");

		List<RoundRobinPool> listOfRoundRobinPools = new ArrayList<RoundRobinPool>();
		
		List<Integer> listOfRoundRobinPoolsId= new ArrayList<Integer>();
		
		List<Integer> listOfPlayersIdsInRoundRobinPool= new ArrayList<Integer>();
		
		List<Player> listOfPlayersToSendToFrontEnd= new ArrayList<Player>();
		
		int indexPlayersPerGroup=0;
		
		int playersPerGroup=0;
		
	
		try {
			
			String sql = "Select idRoundRobinPool from RoundRobinPool where FKRRLeagueNight= ?";

			connection = datasource.getConnection();

			statement = connection.prepareStatement(sql);

			statement.setInt(1, Integer.parseInt(leagueNightId));

			ResultSet results = statement.executeQuery();

			while (results.next()) {

				int roundRobinPoolId = results.getInt("idRoundRobinPool");
				
				RoundRobinPool poolToBeAdded = new RoundRobinPool(roundRobinPoolId);

				listOfRoundRobinPools.add(poolToBeAdded);

			}
			
			for (RoundRobinPool roundRobinPool: listOfRoundRobinPools) {
				
				System.out.println("We should be getting 1 and 2: " + roundRobinPool.getIdRoundRobinPool());
			}
			

			

		} catch (SQLException e) {
		
			e.printStackTrace();
		}

		finally {

			close(connection, statement, results);
		}
		
		
		
		
		try {

			for (RoundRobinPool roundRobinPool : listOfRoundRobinPools) {

				String followUpQuery = "Select FKidPlayer from RoundRobinPoolPlayer where FKidRoundRobinPool= ?";

				connection = datasource.getConnection();

				statement = connection.prepareStatement(followUpQuery);

				statement.setInt(1, roundRobinPool.getIdRoundRobinPool());

				ResultSet results = statement.executeQuery();

				while (results.next()) {

					listOfPlayersIdsInRoundRobinPool.add(results.getInt("FKidPlayer"));

				}
				
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			close(connection, statement, results);
		}
		
		for (int i=0; i<listOfPlayersIdsInRoundRobinPool.size(); i++) {
			
			System.out.println("Player in RR Pool " + listOfPlayersIdsInRoundRobinPool.get(i));
		}
		
		
		try {

			for (int i=0; i< listOfPlayersIdsInRoundRobinPool.size(); i++) {

				String query = "Select * from Player where idPlayer = ?";

				connection = datasource.getConnection();

				statement = connection.prepareStatement(query);

				statement.setInt(1, listOfPlayersIdsInRoundRobinPool.get(i));

				ResultSet results = statement.executeQuery();

				while (results.next()) {
					
					int idPlayer= results.getInt("idPlayer");
					
					String firstName= results.getString("firstName");
					
					String lastName= results.getString("lastName");
					
					int rating= results.getInt("rating");
					
					String email= results.getString("email");
					
					Player player= new Player(idPlayer, firstName, lastName, rating, email);

					listOfPlayersToSendToFrontEnd.add(player);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			close(connection, statement, results);
		}
		
		try {

			for (RoundRobinPool roundRobinPool : listOfRoundRobinPools) {

				String query = "Select count(FKidPlayer) from RoundRobinPoolPlayer where FKidRoundRobinPool = ?";

				connection = datasource.getConnection();

				statement = connection.prepareStatement(query);

				statement.setInt(1, roundRobinPool.getIdRoundRobinPool());

				ResultSet results = statement.executeQuery();

				while (results.next()) {
					
					int numberOfPlayers= results.getInt(1);
					
					if(indexPlayersPerGroup<numberOfPlayers) {
						
						playersPerGroup=numberOfPlayers;
						
						indexPlayersPerGroup= numberOfPlayers-1;
					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			close(connection, statement, results);
		}
		
		for (RoundRobinPool roundRobinPool: listOfRoundRobinPools) {
			
			listOfRoundRobinPoolsId.add(roundRobinPool.getIdRoundRobinPool());
		}
		
			
		if (listOfPlayersToSendToFrontEnd.size() >0) {
		
			listOfPlayersToSendToFrontEnd.sort(Comparator.comparing(Player::getRating));
		
		}
		
		if (listOfRoundRobinPools.size() > 0) {
			
			request.setAttribute("listOfRoundRobinPools", listOfRoundRobinPools);
			
		}
		
		if (listOfRoundRobinPoolsId.size() > 0) {
			
			request.setAttribute("listOfRoundRobinPoolsId", listOfRoundRobinPoolsId);
			
		}
		
		if (leagueNightId != null) {
			
			request.setAttribute("leagueNightId", leagueNightId);
			
		}
		
		if (listOfPlayersToSendToFrontEnd != null) {
			
			request.setAttribute("listOfPlayersToSendToFrontEnd", listOfPlayersToSendToFrontEnd);
			
		}

		if (indexPlayersPerGroup != 0) {
			
			request.setAttribute("indexPlayersPerGroup", indexPlayersPerGroup);
		}
		
		if (playersPerGroup != 0) {
			
			request.setAttribute("playersPerGroup", playersPerGroup);
		}


		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-round-robin-pools.jsp");

		try {
			dispatcher.forward(request, response);
			
		} catch (ServletException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {

		try {

			if (resultSet != null) {

				resultSet.close();
			}

			if (statement != null) {

				statement.close();
			}

			if (connection != null) {

				connection.close();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
