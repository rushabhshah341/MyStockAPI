package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.login.DataConnect;

import model.Manager;
@ManagedBean
@SessionScoped

public class ManagerSelectionController {
	private ArrayList<Manager> managerList;


	public ArrayList<Manager> getManagerList() {
		return managerList;
	}


	public void setManagerList(ArrayList<Manager> managerList) {
		this.managerList = managerList;
	}
	
	
	
	
	public String getManagerListForUser() {
		managerList = (ArrayList<Manager>) AdminController.getManagers();
		return "managerSelection";	
	}
	
	public String updateUserManager() {
		Connection con = null;
        PreparedStatement ps = null;
        int userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	  String managerId = params.get("managerId");
	  
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("update user set managerId = ? where userId= ?");
            ps.setInt(1, Integer.parseInt(managerId));
            ps.setInt(2, userId);
          
            int i = ps.executeUpdate();
			if (i > 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Manager selection successfull", ""));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("managerId", managerId);
				return getManagerListForUser();
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Manager selection failed", ""));
				return getManagerListForUser();
			}
           

        } catch (SQLException ex) {
            System.out.println("Get Manager error -->" + ex.getMessage());
            return "";
        } finally {
        	try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
		
	}

}
