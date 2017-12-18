package com.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controller.AdminController;
import controller.RegisterController;
import controller.UserController;
import model.Manager;
import model.Stock;


@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{Manager}")
	private Manager manager;

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	private int userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String role;
	private String address;
	private double balance;
	private double mtotalCommission;
	private int managerId;
	
	private List<Manager> managerList;

	private List<Stock> stockList;

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

	public List<Manager> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<Manager> managerList) {
		this.managerList = managerList;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String validateUser() {
		boolean valid = LoginDAO.validate(username, password);
		String sessionRole = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
		Object obj=	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("balance");
		if(obj!=null){
			balance=(Double)obj;
		}
		Object objuserId =	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		if(objuserId!= null){
			setUserId((Integer)objuserId);
		}
		
		Object objManagerId =	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("managerId");
		if(objManagerId!= null){
			setManagerId((Integer)objManagerId);
		}
		if (valid && sessionRole.equals("admin")) {
			managerList = AdminController.getManagers();
			return "admin?faces-redirect=true";
		}
		if (valid && sessionRole.equals("user")) {
			stockList=UserController.getAllStocks();
			return "userhome?faces-redirect=true";
		} 
		if( !valid && LoginDAO.validateManager(username, password)){
			Object objManager =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mtotalCommission");
			if(objManager != null)
			{
				mtotalCommission = (Double)objManager;
				//System.out.println(mtotalCommission);
				Manager mm = new Manager();
				mm.setMtotalCommission(mtotalCommission);
				System.out.println(mtotalCommission);

			}
			return "managerhome?faces-redirect=true";
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Passowrd. Please enter correct username and Password", ""));
			return "index";
		}
	}

	public void clear() {
		setAddress(null);
		setFirstName(null);
		setLastName(null);
		setEmail(null);
		setRole(null);
		setUsername(null);
		setPhoneNumber(null);
		setPassword(null);
	}

	public String register() {
		if (role.equals("manager")) {
			return registerManager();
		} else {
			return registerUser();
		}
	}

	public String sendApprovalManager(){
		
		String managerID  =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("managerId");
		boolean approved=AdminController.approveManager(managerID);
		if(approved){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Approved successfully", ""));
			managerList = AdminController.getManagers();
			return "admin";
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Approval not successfull", ""));
			return "admin";
		}
		
	  
	}
	public String registerUser() {
		boolean valid = RegisterController.registerUser(firstName, lastName, email, password, username, phoneNumber,
				"user", 10000, "1",address);
		clear();
		if (valid == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "User Registration successfull", ""));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "index?faces-redirect=true";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Manager Registration failed", ""));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "register";
		}
	}

	public String registerManager() {
		boolean valid = RegisterController.registerManager(firstName, lastName, email, password, username, phoneNumber,
				2, address);
		clear();
		if (valid == true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, " Manager Registration successfull", ""));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "index?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Manager Registration failed", ""));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "register";
		}
	}

	// logout event, invalidate session
	public String logout() throws IOException {
		clear();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index?faces-redirect=true";
		//FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		
	}

}
