package de.worketplace.team06.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import de.worketplace.team06.shared.bo.*;
	/**
	 * 
	 */
	public class TeamMapper {
	    /**
	     * Default constructor
	     */
		private static TeamMapper teamMapper = null;
		 /**
		   * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
		   * neue Instanzen dieser Klasse zu erzeugen.
		   */
		protected TeamMapper(){
			
		}
		
		public static TeamMapper teamMapper(){
			if (teamMapper == null){
				teamMapper = new TeamMapper();
			}
			return teamMapper; 
		}
	    /**
	     * @param orgaUnit 
	     * @return
	     * @throws  
	     */
	    public Team insert (Team t) {
	    	Connection con = DBConnection.connection();
			
			
			try {
				
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM orgaunit ");			
				if (rs.next()) {
					
					t.setID(rs.getInt("maxid") + 1);
			
					con.setAutoCommit(false);
					stmt = con.createStatement();
					stmt.executeUpdate("INSERT INTO orgaunit (id, created, googleID, description, type) " + "VALUES (" + t.getID() + ",'" + t.getCreated() + "','" + t.getGoogleID() +  "','" + t.getDescription() +  "','" + t.getType() + "')");
					stmt.executeUpdate("INSERT INTO team (id, created, teamName, membercount) " + "VALUES (" + t.getID() + ",'" + t.getCreated() + "','" + t.getName() + "','" + t.getMembercount() + "')");
					con.commit();
				}
			}
			
			catch (SQLException e2) {
				try {
					System.out.println("Die SQL Transaktion konnte nicht vollständig ausgeführt werden. Es wird versucht die Transaktion rückgängig zu machen!");
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  finally {
					  e2.printStackTrace();
				}	
			}
			return t;
	    }
	    
		/**
		 * Auslesen eines Teams mithilfe einer GoogleID.
		 */
		public Team findByGoogleID(String googleID) {
			Connection con = DBConnection.connection();
			
			try {						
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orgaunit INNER JOIN team ON orgaunit.id = team.id WHERE orgaunit.googleID = '" + googleID + "'");		
				
				if (rs.next()) {
					Team t = new Team();
					t.setID(rs.getInt("id"));
					t.setCreated(rs.getTimestamp("created"));
					t.setGoogleID(googleID);
					t.setDescription(rs.getString("description"));
					t.setPartnerProfileID(rs.getInt("partnerprofileID"));
					t.setType(rs.getString("type"));
					
					t.setName(rs.getString("teamName"));
					t.setMembercount(rs.getInt("membercount"));
					return t;
				}			
			}
			catch (SQLException e2) {
				e2.printStackTrace();
				return null;
			}
			return null;
		}	
		
	    
	    

	    /**
	     * @param orgaUnit 
	     * @return
	     */
	    public Team update(Team t) {
	        // TODO implement here
	        return null;
	    }

	    /**
	     * @param orgaUnit
	     */
	    public Team findById (int id){
	    	return null;
	    }
	    
	    
	    public void delete(Team t) {
	        // TODO implement here
	    }

	    /**
	     * @return
	     */
	    public Vector<Team> findAll() {
	        // TODO implement here
	        return null;
	    }



	    /**
	     * @param orgaUnit 
	     * @return
	     */
	  
}
	
