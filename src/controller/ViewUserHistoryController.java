package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.login.DataConnect;

import model.UserPurchase;

@ManagedBean
@SessionScoped

public class ViewUserHistoryController {
	private String stockName;
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	private List<UserPurchase> userPurchase;
	private int sellQuantity;
	public int getSellQuantity() {
		return sellQuantity;
	}
	public void setSellQuantity(int sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	private double price;
	private double amount;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	private String symbol;
	private int quantity;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	static final String API_KEY = "AF93E6L5I01EA39O";
	public String getUserHistory(){
		
		 Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		  String userId = params.get("userId");
		  userPurchase= getUserPurchase(Integer.parseInt(userId));
		  System.out.println(userPurchase);
		return "viewUserHistory";
		
		
	}
	public List<UserPurchase> getUserPurchase(int userId){
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<UserPurchase> userPurchaseList= new ArrayList<UserPurchase>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from purchasedStock where userId = ?  ");
            ps.setInt(1, userId);
            
            System.out.println(userId);
            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {
               UserPurchase userPurchase= new UserPurchase();
               userPurchase.setManagerId(rs.getInt("managerId"));
               userPurchase.setStockSymbol(rs.getString("stockSymbol"));
               userPurchase.setPrice(rs.getDouble("price"));
               userPurchase.setQuantity(rs.getInt("quantity"));	
               userPurchase.setTotalAmount(rs.getDouble("totalAmount"));
               userPurchase.setPurchaseDate(rs.getDate("purchaseDate"));
               userPurchase.setManagerfees(rs.getDouble("managerfees"));
               userPurchase.setStockName(rs.getString("stockName"));
               userPurchaseList.add(userPurchase);
                System.out.println(userPurchaseList.size());
            }

        } catch (SQLException ex) {
            System.out.println("Get UserPurchase error -->" + ex.getMessage());
            return null;
        } finally {

        }
        return userPurchaseList;
    }
	public List<UserPurchase> getUserPurchase() {
		return userPurchase;
	}
	public void setUserPurchase(List<UserPurchase> userPurchase) {
		this.userPurchase = userPurchase;
	}
	
	public String sellStock(){
	try {
		Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	  symbol = params.get("symbol");
	  stockName = params.get("stockName");
	  quantity=Integer.parseInt(params.get("quantity"));
	  String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min" + "&apikey=" + API_KEY;
	  InputStream inputStream = new URL(url).openStream();
	  JsonReader jsonReader = Json.createReader(inputStream);
      JsonObject mainJsonObj = jsonReader.readObject();
      for (String key : mainJsonObj.keySet()) {
          if (key.equals("Meta Data")) {
          }
          else{
        	  JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
        	  for (String subKey : dataJsonObj.keySet()) {
                  JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                  price=  Double.parseDouble(subJsonObj.getString("4. close"));
        	  }   
          }
      }
      
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      // convert the json string back to object
      
	  return "sell";
	}
	public String updateSellRecord(){
		try {
        	int userId=0;
        	Object user = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
            int managerId =(Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("managerId");
            if(user!=null){
            	userId=(Integer)user;
    		}
            Connection conn = DataConnect.getConnection();
            PreparedStatement   ps = conn.prepareStatement("INSERT INTO sellStock (userId,managerId, stockSymbol,price, quantity, totalAmount, sellDate,managerfees,stockName)  VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,userId );
            ps.setInt(2, managerId);
            ps.setString(3, symbol);
            ps.setDouble(4, price);
            ps.setInt(5, sellQuantity);
            ps.setDouble(6, amount);
            LocalDateTime ldt = LocalDateTime.now();
            ps.setTimestamp(7, Timestamp.valueOf(ldt));
            ps.setInt(8, 0);
            ps.setString(9, stockName);
            ps.execute();
            
            
            
            // update user balance
            double balance=(Double)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("balance");
            balance=balance+amount;
            
            PreparedStatement   ps1 = conn.prepareStatement("Update user set balance =? where userId= ?");
            ps1.setDouble(1,balance );
            ps1.setInt(2, userId);
            ps1.executeUpdate(); 
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("balance", balance);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully sold stock",""));
            return "userhome";
            
        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, " sell failed",""));
        }
        return "viewUserHistory";
	}


}
