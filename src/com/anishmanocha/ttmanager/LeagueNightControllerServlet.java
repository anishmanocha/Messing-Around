package com.anishmanocha.ttmanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/LeagueNightControllerServlet")
public class LeagueNightControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@Resource(name= "jdbc/ttmanager")
	DataSource datasource;
	
	Connection connection;
	
	PreparedStatement statement;
	
	ResultSet results;

    public LeagueNightControllerServlet() {
        
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command= request.getParameter("command");
		
		if (command== null) {
			
			command= "List";
		}
		
		
		switch(command) {
		
		case "add":
			try {
				addLeagueNight(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case "delete":
			deleteLeagueNight(request, response);
			
		case "Load":
			loadLeagueNight(request, response);
			break;
		
		case "update":
			updateLeagueNight(request, response);
			break;
			
		case "addPlayersToLeagueNight":
			try {
				addPlayersToLeagueNight(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		
		case "getListOfPlayersInLeagueNight":
			viewPlayersInLeagueNight(request, response);
			break;
			
			
		default:
			getListOfLeagueNights(request, response);
			break;
				
		}
		
	}




	private void viewPlayersInLeagueNight(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Yay, we're hitting this method");
		
		String leagueNightId= request.getParameter("leagueNightId");
		
		System.out.println("The league night ID is " + request.getParameter("leagueNightId"));
		
		System.out.println(leagueNightId);
		
		String sql= "Select lnPlayerFK from LeagueNightPlayer where lnLeagueNightFK= ?";
		
		List<Integer> listOfPlayerIdsInLeagueNight= new ArrayList<Integer>();
		
		List<Player> listOfPlayersInLeagueNight= new ArrayList<Player>();
		
		try {
			
			connection=datasource.getConnection();
			
			statement= connection.prepareStatement(sql);
			
			statement.setInt(1, Integer.parseInt(leagueNightId));
			
			ResultSet results= statement.executeQuery();
			
			System.out.println(results.toString());
			
			
			
			while (results.next()) {
				
				listOfPlayerIdsInLeagueNight.add(results.getInt("lnPlayerFK"));
				
				System.out.println(results.getInt("lnPlayerFK") + " has been added");
	
			}
			
			for (int i=0; i<listOfPlayerIdsInLeagueNight.size(); i++) {
				
				sql= "Select * from Player where idPlayer= ?";
				
				statement= connection.prepareStatement(sql);
				
				statement.setInt(1, listOfPlayerIdsInLeagueNight.get(i));
				
				results= statement.executeQuery();
				
				//I can create a list of players, create new player objects, and display them on page
				//I don't have a problem with doing this or anything, but it confuses me a bit I guess
				//It's not like I'm creating "New Players" since they already exist in the league, 
				//I should be able to retrieve all of these IDs, loop through a list of players,
				//Based on the IDs, display them on appropriate page
				//I can't seem to get list of players though
				//For now, I'm going to just do what I know I can
				
				while (results.next()) {
				
				int playerId= results.getInt("idPlayer");
				
				String firstName= results.getString("firstName");
				
				String lastName= results.getString("lastName");
				
				int rating= results.getInt("rating");
				
				String email= results.getString("email");
				
				Player player= new Player(playerId, firstName, lastName, rating, email);
					
				listOfPlayersInLeagueNight.add(player);
					
				}
				
				request.setAttribute("listOfPlayersInLeagueNight", listOfPlayersInLeagueNight);
	
			}
			
			//I have all of the player IDs that are participating in a given league night
			
			
			
		
			
			RequestDispatcher dispatcher= request.getRequestDispatcher("/list-players-in-league-night.jsp");
			
			dispatcher.forward(request, response);
			
		
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
		
		
	}


	private void addPlayersToLeagueNight(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
		String[] listOfPlayerIds= request.getParameterValues("selectedPlayer");
		
		for (String element: listOfPlayerIds) {
			
			System.out.println(element);
		}
		
	
		String dateOfLeagueNight= request.getParameter("dateOfLeagueNight");
		
		System.out.println(dateOfLeagueNight);
		
		//Converting league date String into a date
		
		//Based on the league night date, gather the leagueNightID
		
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date dt=d.parse(dateOfLeagueNight);
		
		java.sql.Date sqlDate = new java.sql.Date(dt.getTime());
		
		int leagueNightId = 0;
		
		
		//So what we're doing here is we're getting the leagueNightID based on the date that was entered
		//Might not have needed to make a backend query here
		try {
			connection= datasource.getConnection();
				
			String query= "Select idLeagueNight from LeagueNight where Date = ?";
				
			statement= connection.prepareStatement(query);
			
			statement.setDate(1, sqlDate);
					
			results= statement.executeQuery();
				
			while(results.next()) {
					
				leagueNightId= results.getInt("idLeagueNight");
				
					
				}
				
			request.setAttribute("idOfLeagueNight", leagueNightId);
				
				
				
			} 
			catch (SQLException e) {
				
				System.out.println("Something went wrong");
				
				e.printStackTrace();
			}
		
		finally {
			
			close(connection, statement, results);
		}
			
			
			System.out.println("The league night ID is " + leagueNightId);
			
			
			//Now, we have the playerID and leagueNight ID
			//We can now perform an insertion to the backend table, leagueNightPlayer
			
			
			try {
					
				connection= datasource.getConnection();
				
				for (String playerId: listOfPlayerIds) {
				
				String query= "Insert into LeagueNightPlayer (lnLeagueNightFK, lnPlayerFK) values (?, ?)";
				
				statement= connection.prepareStatement(query);
				
				statement.setInt(1, leagueNightId);
				
				statement.setInt(2, Integer.parseInt(playerId));
				
				statement.execute();
				
				System.out.println("There was no error that occured: check to see if insertion worked correctly");
				
				System.out.println(statement.toString());
				
				}
			}
			
			catch(Exception e) {
				
				System.out.println("You have encountered an error");
			}
					
			RequestDispatcher dispatcher= request.getRequestDispatcher("/add-players-to-league-night.jsp");
			
			
			
			try {
				dispatcher.forward(request, response);
			} 
			catch (ServletException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			finally {
				
				close(connection, statement, results);
			}
			
			
		
		
		
	}


	private void getListOfLeagueNights(HttpServletRequest request, HttpServletResponse response) {
		//Place this in a utility class, otherwise, we're blending controller with model-like code
		
			Date date;
				
			int leagueNightId;
				
			LeagueNight leagueNight;
			
			List<LeagueNight> listOfLeagueNights= new ArrayList<LeagueNight>();
				
				
			try {
				connection= datasource.getConnection();
					
				String query= "Select * from LeagueNight";
					
				statement= connection.prepareStatement(query);
					
				results= statement.executeQuery();
					
				while(results.next()) {
						
					leagueNightId= results.getInt("idLeagueNight");
					
					System.out.println("The id of the league night is " + results.getInt("idLeagueNight"));
					
					date= results.getDate("Date");
					
					listOfLeagueNights.add(new LeagueNight(leagueNightId, date));
					
						
					}
					
				request.setAttribute("listOfLeagueNights", listOfLeagueNights);
					
					
					
				} 
				catch (SQLException e) {
					
					System.out.println("Something went wrong");
					
					e.printStackTrace();
				}
			
			
				
				
				RequestDispatcher dispatcher= request.getRequestDispatcher("/list-league-nights.jsp");
				
				try {
					dispatcher.forward(request, response);
				} 
				catch (ServletException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				
				finally {
					
					close(connection, statement, results);
				}
				
			
	}


	private void updateLeagueNight(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


	private void loadLeagueNight(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


	private void deleteLeagueNight(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


	private void addLeagueNight(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String req=request.getParameter("date");
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt=d.parse(req);          
		java.sql.Date sqlDate = new java.sql.Date(dt.getTime()); 
		
				
		String sql= "insert into LeagueNight (Date) values (?)";	
	
		try {
			connection= datasource.getConnection();
			
			statement= connection.prepareStatement(sql);
			
			statement.setDate(1, sqlDate);
			
			statement.execute();
			
			
			System.out.println("The league has been added to the database!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			
			close(connection, statement, results);
		}
		
		
		
		getListOfLeagueNights(request, response);
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
