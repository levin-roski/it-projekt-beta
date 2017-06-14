package de.worketplace.team06.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import de.worketplace.team06.shared.bo.Rating;
import de.worketplace.team06.shared.bo.Project;
import de.worketplace.team06.shared.bo.Property;

/**
 * 
 */
public class PropertyMapper {

	private static PropertyMapper propertyMapper = null;
	 /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	protected PropertyMapper(){
		
	}
	
	public static PropertyMapper propertyMapper(){
		if (propertyMapper == null){
			propertyMapper = new PropertyMapper();
		}
		return propertyMapper; 
	}

	public Property findById (int id){
    	Connection con = DBConnection.connection();
    	
    	try{
    		Statement stmt= con.createStatement();
    		ResultSet rs = stmt.executeQuery
    				("SELECT id, created, name, value, FROM Property " + "WHERE id= " + id);
    		
    		if (rs.next()) {
    			Property prop = new Property();
    			prop.setID(rs.getInt("id"));
    			prop.setCreated(rs.getTimestamp("created"));
    			prop.setName(rs.getString("name"));
    			prop.setValue(rs.getDouble("value"));
    		}	
    	}
    	catch (SQLException e){
    		e.printStackTrace();
    	}
    	return null;
    }
  
    public Vector<Property> findAll() {
        Connection con = DBConnection.connection();
        Vector<Property> result = new Vector<Property>();
        
        try{
        	Statement stmt = con.createStatement();
        	
        	ResultSet rs = stmt.executeQuery("SELECT id, created, name, value "+ "FROM Property ");
        	
        	while (rs.next()){
        		Property prop = new Property();
        		prop.setID(rs.getInt("id"));
        		prop.setCreated(rs.getTimestamp("created"));
        		prop.setName(rs.getString("name"));
        		prop.setValue(rs.getDouble("value"));
        		
        		result.addElement(prop);
        	}
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
        return result ;
    }
    
	public Vector<Property> findByPartnerProfileID(int partnerProfileID) {
		
		Connection con = DBConnection.connection();
		Vector<Property> result = new Vector<Property>();
		
        try {
        	Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, created, name, value, partnerprofile_id "
					+ " FROM property WHERE partnerprofile_id ='" + partnerProfileID + "'ORDER BY id");
			
			while (rs.next()){
				Property prop = new Property();
				prop.setID(rs.getInt("id"));
				prop.setName(rs.getString("name"));
				prop.setCreated(rs.getTimestamp("created"));
        		prop.setValue(rs.getDouble("value"));
        		prop.setPartnerProfileID(rs.getInt("partnerprofile_id"));
        		
        		result.add(prop);	
			}
        }
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
    		return result;
	}
    
    public Property insert (Property prop) {
        Connection con = DBConnection.connection();
        
        try {
        	Statement stmt = con.createStatement();
        	
        	ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM property ");
        	
        	if (rs.next()){
        	prop.setID(rs.getInt("maxid") + 1);
        	stmt = con.createStatement();
        	
        	stmt.executeUpdate("INSERT INTO rating (id, created, name, value) " 
        	+ "VALUES (" 
        		+ prop.getID() + ", "
        		+ prop.getCreated() + ", " 
        		+ prop.getName() + ", " 
        		+ prop.getValue() 
        		+ "','" +"')");
        	}
        }
        catch (SQLException e){
    	e.printStackTrace();
        }
    
        return prop;
    } 
    
    public Property update(Property prop) {
        Connection con = DBConnection.connection();
        
        try{
        	Statement stmt = con.createStatement();
        	
        	stmt.executeUpdate("UPDATE property " 
        	+ "SET value=\"" + prop.getValue() + "\", "
        	+ "created=\"" +  prop.getCreated() + "\" " 
        	+ "name=\"" +  prop.getName() + "\" " 
        	+ "WHERE id=" + prop.getID());
        }
        
        catch (SQLException e){
        	e.printStackTrace();
        } 
        return prop;
    }

    public void delete(Property prop) {
        Connection con = DBConnection.connection();
        
        try {
        	Statement stmt = con.createStatement();
        	
        	stmt.executeUpdate("DELETE FROM property " + "WHERE id=" + prop.getID());
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
    }
    
    public void deleteByPartnerProfileID(int id) {
    	 Connection con = DBConnection.connection();
    	 
    	 try{
    		Statement stmt = con.createStatement();
         	
         	stmt.executeUpdate("DELETE FROM property " + "WHERE partnerprofile_id=" + id);
    	 }
    	 catch (SQLException e){
         	e.printStackTrace();
         }
    	
    }
}
