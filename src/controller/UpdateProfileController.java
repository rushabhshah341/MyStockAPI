package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.login.DataConnect;

import model.Manager;

@ManagedBean
public class UpdateProfileController {

	private int id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String userName;
	private String password;
	private String phoneNumber;
	private String address;
	private double balance;
	private double mtotalCommission;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getMtotalCommission() {
		return mtotalCommission;
	}
	public void setMtotalCommission(double mtotalCommission) {
		this.mtotalCommission = mtotalCommission;
	}
	public String getUpdateProfile() {

		Connection con = null;
		PreparedStatement ps = null;

		int userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from user where userId = ? ");
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				emailId = rs.getString("emailId");
				password = rs.getString("password");
				userName = rs.getString("userName");
				phoneNumber = rs.getString("phoneNumber");
				address = rs.getString("address");
				balance = rs.getDouble("balance");
			}
			System.out.println(balance + "hello");
			return "updateProfile";
		} catch (SQLException ex) {
			System.out.println("Get single user error -->" + ex.getMessage());
			return null;
		} finally {

		}

	}
	
	//Get Manager Update Profile
	public String getManagerUpdateProfile() {

		Connection con = null;
		PreparedStatement ps = null;

		int mmanagerId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("managerId");
		System.out.println(mmanagerId);
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from manager where mmanagerId = ? ");
			ps.setInt(1, mmanagerId);
			Manager mm = new Manager();
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				mm.setFirstname(rs.getString("mfirstName"));
				
				firstName = rs.getString("mfirstName");
				lastName = rs.getString("mlastName");
				emailId = rs.getString("memailId");
				password = rs.getString("mpassword");
				userName = rs.getString("muserName");
				phoneNumber = rs.getString("mphoneNumber");
				address = rs.getString("maddress");
				mtotalCommission = rs.getDouble("mtotalCommission");
			}
			//System.out.println(mtotalCommission + "hello");
			return "updateManagerProfile";
		} catch (SQLException ex) {
			System.out.println("Get single user error -->" + ex.getMessage());
			return null;
		} finally {

		}

	}


	public String updateManagerProfile() {
		Connection con = null;
		PreparedStatement ps = null;
		int mmanagerId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("managerId");

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement(
					"update manager set mfirstName=?, mlastName=?, memailId=?, mpassword=?, muserName=?, mphoneNumber=?, maddress=? where mmanagerId = ?");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, emailId);
			ps.setString(4, password);
			ps.setString(5, userName);
			ps.setString(6, phoneNumber);
			ps.setString(7, address);
			ps.setInt(8, mmanagerId);

			int i = ps.executeUpdate();
			if (i > 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Manager profile Updated successfully", ""));
				return "managerhome";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Manager profile update fail", ""));
				return "updateManagerProfile";
			}

		} catch (SQLException ex) {
			System.out.println("Update Manager error -->" + ex.getMessage());
			return null;
		} finally {

		}

	}
	
	
	public String updateUserProfile() {
		Connection con = null;
		PreparedStatement ps = null;
		int userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement(
					"update user set firstName=?, lastName=?, emailId=?, password=?, userName=?, phoneNumber=?, address=? where userId = ?");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, emailId);
			ps.setString(4, password);
			ps.setString(5, userName);
			ps.setString(6, phoneNumber);
			ps.setString(7, address);
			ps.setInt(8, userId);

			int i = ps.executeUpdate();
			if (i > 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "profile Updated successfully", ""));
				return "userhome";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "profile update fail", ""));
				return "updateProfile";
			}

		} catch (SQLException ex) {
			System.out.println("Update user error -->" + ex.getMessage());
			return null;
		} finally {

		}

	}

	public UpdateProfileController() {

	}

}
