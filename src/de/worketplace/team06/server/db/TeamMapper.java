package de.worketplace.team06.server.db;

import java.sql.Connection;
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
		   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
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
	     */
	    public Team insert (Team t) {
	        // TODO implement here
	    	Connection con = DBConnection.connection();
	    	
	    	try{
	    		Statement stmt = con.createStatement();
	    		
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
	
