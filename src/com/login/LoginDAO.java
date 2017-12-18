package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class LoginDAO {

    public static boolean validate(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from user where userName = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	/*System.out.println((rs.getString("role")+" hii"));*/
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("userName"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", rs.getString("role"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", rs.getInt("userId"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("balance", rs.getDouble("balance"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("managerId", rs.getInt("managerId"));
                //System.out.println("uid: " + rs.getString("uid"));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,object);
                DataConnect.close(con);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {

        }
        return false;
    }
    public static boolean validateManager(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from manager where muserName = ? and mpassword = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            

            ResultSet rs1 = ps.executeQuery();

            if (rs1.next()) {
            	
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs1.getString("muserName"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("managerId", rs1.getInt("mmanagerId"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mtotalCommission", rs1.getDouble("mtotalCommission"));
                
                //System.out.println("uid: " + rs.getString("uid"));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,object);
                DataConnect.close(con);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {

        }
        return false;
    }

}
