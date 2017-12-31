package com.anishmanocha.ttmanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/PlayerControllerServlet")
public class PlayerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name= "jdbc/ttmanager")
	DataSource datasource;
	
	Connection connection;
	
	PreparedStatement statement;
	
	ResultSet results;
       

    public PlayerControllerServlet() {
    	
    	
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command= request.getParameter("command");
		
		if (command== null) {
			
			command= "List";
		}
		
		
		switch(command) {
		
		case "add":
			addPlayer(request, response);
			break;
		case "delete":
			deletePlayer(request, response);
			
		case "Load":
			loadPlayer(request, response);
			break;
		
		case "update":
			updatePlayer(request, response);
			break;
			
		case "redirect":
			redirectServlet(request, response);
			break;
			
		case "getListOfPlayers":
			getListOfPlayersForLeagueNight(request, response);
			break;
			
		
		default:
			getListOfPlayers(request, response);
				
		}
		
		
	}


	private void redirectServlet(HttpServletRequest request, HttpServletResponse response) {
		
		
			
			try {
				request.getRequestDispatcher("/LeagueNightControllerServlet").forward(request,response);
				
				return;
				
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
		
		
	}


	private void updatePlayer(HttpServletRequest request, HttpServletResponse response) {
		
		String firstName= request.getParameter("firstName");
		
		String lastName= request.getParameter("lastName");
		
		String rating= request.getParameter("rating");
		
		int parsedRating= Integer.parseInt(rating);
		
		String email= request.getParameter("email");
		
		String playerId= request.getParameter("id");
		
		int parsedPlayerId= Integer.parseInt(playerId);
		
		Player player;
		
		String query= "Update Player set firstName=?, lastName= ?, rating=?, email=? where idPlayer= ?";
		
		try {
			
			connection= datasource.getConnection();
		
			statement= connection.prepareStatement(query);
			
			statement.setString(1, firstName);
			
			statement.setString(2, lastName);
			
			statement.setInt(3, parsedRating);
			
			statement.setString(4, email);
			
			statement.setInt(5, parsedPlayerId);
			
			System.out.println(statement.toString());
			
			statement.execute();
			

		}
		
		catch(Exception e) {
			
			
			e.printStackTrace();
		}
		
		
		
		
		getListOfPlayers(request, response);
		
		
		
		
		
	}


	private void loadPlayer(HttpServletRequest request, HttpServletResponse response) {
		
		String playerId= request.getParameter("playerId");
		
		int parsedPlayerId= Integer.parseInt(playerId);
		
		String query= "Select * from Player where idPlayer= ?";
		
		Player player;
		
		String firstName;
		
		String lastName;
		
		int rating;
		
		String email;
		
		try {
			
			connection= datasource.getConnection();
		
			statement= connection.prepareStatement(query);
			
			statement.setInt(1, parsedPlayerId);
			
			System.out.println(statement.toString());
			
			results= statement.executeQuery();
			
			System.out.println(results.toString());
			
			while (results.next()) {
			
			firstName= results.getString("firstName");
			lastName= results.getString("lastName");
			rating= results.getInt("rating");
			email= results.getString("email");
			player= new Player(parsedPlayerId, firstName, lastName, rating, email);
			
			request.setAttribute("player", player);
			
			}
			
			
			
		}
		
		catch(Exception e) {
			
			
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/update-player.jsp");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
	}


	private void deletePlayer(HttpServletRequest request, HttpServletResponse response) {
		
		String playerId= request.getParameter("playerId");
		
		Integer parsedStudentId= Integer.parseInt(playerId);
		
		String query= "Delete from Player where idPlayer= ?";
		
		try {
			connection= datasource.getConnection();
			
			statement= connection.prepareStatement(query);
			
			statement.setInt(1, parsedStudentId);
			
			System.out.println(statement.toString());
			
			statement.execute();
			
						
		} catch (SQLException e) {
			
			System.out.println("Something went wrong");
			
			e.printStackTrace();
		}
		
		
		
		getListOfPlayers(request,response);
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void getListOfPlayers(HttpServletRequest request, HttpServletResponse response) {
		
		
		//Place this in a utility class, otherwise, we're blending controller with model-like code
		
		int id; 
		
		String firstName;
		
		String lastName;
		
		String email;
		
		int rating;
		
		Player tempPlayer;
		
		List<Player> listOfPlayers= new ArrayList<Player>();
		
		
		try {
			connection= datasource.getConnection();
			
			String query= "Select * from Player";
			
			statement= connection.prepareStatement(query);
			
			results= statement.executeQuery();
			
			
			while(results.next()) {
				
			id= results.getInt("idPlayer");
			
			firstName= results.getString("firstName");
			
			lastName= results.getString("lastName");
			
			rating= results.getInt("rating");
			
			email= results.getString("email");
			
			tempPlayer= new Player(id, firstName, lastName, rating, email);
			
			System.out.println(tempPlayer);
			
			listOfPlayers.add(tempPlayer);
			
			}
			
			request.setAttribute("listOfPlayers", listOfPlayers);
			
			
			
		} catch (SQLException e) {
			
			System.out.println("Something went wrong");
			
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/list-players.jsp");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
	}
	
private void getListOfPlayersForLeagueNight(HttpServletRequest request, HttpServletResponse response) {
		
		
		//Place this in a utility class, otherwise, we're blending controller with model-like code
	
		String dateForLeagueNight= request.getParameter("leagueNightDate");
		
		System.out.println(dateForLeagueNight);
		
		int id; 
		
		String firstName;
		
		String lastName;
		
		String email;
		
		int rating;
		
		Player tempPlayer;
		
		List<Player> listOfPlayers= new ArrayList<Player>();
		
		
		try {
			connection= datasource.getConnection();
			
			String query= "Select * from Player";
			
			statement= connection.prepareStatement(query);
			
			results= statement.executeQuery();
			
			
			while(results.next()) {
				
			id= results.getInt("idPlayer");
			
			firstName= results.getString("firstName");
			
			lastName= results.getString("lastName");
			
			rating= results.getInt("rating");
			
			email= results.getString("email");
			
			tempPlayer= new Player(id, firstName, lastName, rating, email);
			
			System.out.println(tempPlayer);
			
			listOfPlayers.add(tempPlayer);
			
			}
			
			request.setAttribute("listOfPlayers", listOfPlayers);
			
			request.setAttribute("dateForLeagueNight", dateForLeagueNight);
			
			
			
		} catch (SQLException e) {
			
			System.out.println("Something went wrong");
			
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("/add-players-to-league-night.jsp");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
	}
	

	
	private void addPlayer(HttpServletRequest request, HttpServletResponse response) {
		
		
		//Place this in a utility class, otherwise, we're blending controller with model-like code
		
				String firstName= request.getParameter("firstName");
				
				String lastName= request.getParameter("lastName");
				
				String email= request.getParameter("email");
				
				int rating= Integer.parseInt(request.getParameter("rating"));
				
				
				
				
				try {
					connection= datasource.getConnection();
					
					String query= "insert into Player (firstName, lastName, rating, email) values (?, ?, ?, ?)";
					
					statement= connection.prepareStatement(query);
					
					statement.setString(1, firstName);
					
					statement.setString(2, lastName);
					
					statement.setInt(3, rating);
					
					statement.setString(4, email);
					
					System.out.println(statement.toString());
				
					statement.execute();
					
					
				} catch (SQLException e) {
					
					System.out.println("Something went wrong");
					
					e.printStackTrace();
				}
				
				finally {
					
					close(connection, statement, results);
				}
				
				
				
				getListOfPlayers(request, response);
				
				
				
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
		
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}

}

