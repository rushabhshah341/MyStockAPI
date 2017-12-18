package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.login.DataConnect;

import model.RegisterModel;

public class RegisterController {

	
	private RegisterModel rm = new RegisterModel();
	
	public RegisterModel getRm() {
		
		return rm;
	}
	
	public void setRm(RegisterModel rm) {
		this.rm = rm;
	}
	
	public RegisterController() {
		
		
	}
	
	public String registercontroller(){
		
		return "Rushabh is in";
	}

	public static boolean  registerManager(String firstName, String lastName, String email, String password, String username,String phoneNumber, int commission,String address ){
		 Connection con = null;
		 String query = "insert into manager(mfirstName,mlastName,memailId,muserName,mpassword,mphoneNumber,mfees,misActive,maddress,mtotalCommission)"
			        + " values (?, ?, ?, ?,?,?,?,?,?,?)";
	    
		

		      // create the mysql insert preparedstatement
			try{ 
				 
			  con = DataConnect.getConnection();
			  PreparedStatement preparedStmt =con.prepareStatement(query);
		      preparedStmt.setString (1, firstName);
		      preparedStmt.setString (2, lastName);
		      preparedStmt.setString (3,email);
		      preparedStmt.setString (4,username);
		      preparedStmt.setString (5,password);
		      preparedStmt.setString (6,phoneNumber);
		      preparedStmt.setInt(7,commission);
		      preparedStmt.setString(8, "0");
		      preparedStmt.setString(9,address);
		      preparedStmt.setInt(10,0);
		      preparedStmt.execute();
			}
			catch (SQLException ex) {
	            System.out.println("Register error -->" + ex.getMessage());
	            return false;
	        } finally {
	        	try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
		return true;
	}
	
	public static boolean  registerUser(String firstName, String lastName, String email, String password, String username,String phoneNumber, String role, int balance, String isActive, String address ){
		 Connection con = null;
		 String query = " insert into user(firstName,lastName,emailId,password,userName,phoneNumber,role,balance,isActive,address)"
			        + " values (?, ?, ?, ?, ?,?,?,?,?,?)";
	    
		

		      // create the mysql insert preparedstatement
			try{ 
				con = DataConnect.getConnection(); 
				PreparedStatement preparedStmt =con.prepareStatement(query);
		
		      preparedStmt.setString (1, firstName);
		      preparedStmt.setString (2, lastName);
		      preparedStmt.setString (3,email);
		      preparedStmt.setString (4,password);
		      preparedStmt.setString (5,username);
		      preparedStmt.setString (6,phoneNumber);
		      preparedStmt.setString(7,role);
		      preparedStmt.setInt(8, balance);
		      preparedStmt.setString(9, isActive);
		      preparedStmt.setString(10, address);
		      preparedStmt.execute();
			}
			catch (SQLException ex) {
	            System.out.println("Register error -->" + ex.getMessage());
	            return false;
	        } finally {

	        }
		return true;
	}

}
