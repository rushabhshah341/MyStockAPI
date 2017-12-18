package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.login.DataConnect;

import model.Manager;

public class AdminController {
	 public static List<Manager> getManagers(){
	        Connection con = null;
	        PreparedStatement ps = null;
	        ArrayList<Manager> managerList= new ArrayList<Manager>();
	        try {
	            con = DataConnect.getConnection();
	            ps = con.prepareStatement("select * from manager  ");
	               

	            ResultSet rs = ps.executeQuery();
	           
	            while (rs.next()) {
	               Manager manager= new Manager();
	            	manager.setFirstname(rs.getString("mfirstName"));
	            	manager.setLastname(rs.getString("mlastName"));
	            	manager.setEmailId(rs.getString("memailId"));
	            	manager.setUsername(rs.getString("muserName"));
	            	manager.setPhonenumber(rs.getString("mphoneNumber"));
	            	manager.setAddress(rs.getString("maddress"));
	            	manager.setManagerId(rs.getString("mmanagerId"));
	            	manager.setIsActive(rs.getString("misActive"));
	            	manager.setMtotalCommission(rs.getDouble("mtotalCommission"));
	                managerList.add(manager);
	                
	            }

	        } catch (SQLException ex) {
	            System.out.println("Get Manager error -->" + ex.getMessage());
	            return null;
	        } finally {

	        }
	        return managerList;
	    }
	 public static boolean approveManager(String managerID ){
	        Connection con = null;
	        PreparedStatement ps = null;
	        
	        try {
	            con = DataConnect.getConnection();
	            ps = con.prepareStatement("update manager set  misActive = ? where mmanagerId= ?");
	            ps.setString(1, "1");
	            ps.setInt(2, Integer.parseInt(managerID));
	            int i=ps.executeUpdate();
	           if(i>0){
	        	   con.close();
	        	   return true;
	           }
	           

	        } catch (SQLException ex) {
	            System.out.println("Get Manager error -->" + ex.getMessage());
	            return false;
	        } finally {
	        	try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	        return false;
	    }

}
