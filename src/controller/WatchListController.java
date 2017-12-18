package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.login.DataConnect;

import model.Stock;

public class WatchListController {
	static final String API_KEY = "AF93E6L5I01EA39O";
	public static boolean addStockRecordtoWatch(int userId, SelectItem selectedItem){
		 Connection con = null;
		 
		 String query = "insert into watchlist(userId,stockSymbol,stockName) values (?,?,?)";
	    
		

		      // create the mysql insert preparedstatement
			try{ 
				 
			  con = DataConnect.getConnection();
			  PreparedStatement preparedStmt =con.prepareStatement(query);
		      preparedStmt.setInt (1, userId);
		      preparedStmt.setString (2, selectedItem.getLabel());
		      preparedStmt.setString (3, selectedItem.getDescription());
		      preparedStmt.execute();
			}
			catch (SQLException ex) {
	            System.out.println("Watch List insert error -->" + ex.getMessage());
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
	
	
	 public static List<Stock> getallWatchStock(){
	        Connection con = null;
	        PreparedStatement ps = null;
	        List<Stock> stockList= new ArrayList<Stock>();
	        try {
	        	con = DataConnect.getConnection();
	            ps = con.prepareStatement("select * from watchlist where userId = ? ");
	            int userId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	    				.get("userId");
	    	        ps.setInt(1, userId); 

	            ResultSet rs = ps.executeQuery();
	           
	            while (rs.next()) {
	            	Stock stock= new Stock();
	            	stock.setStockName(rs.getString("stockName"));
	            	stock.setStockSymbol(rs.getString("stockSymbol"));
	            	stock.setPrice(getPrice(rs.getString("stockSymbol")));
	            	stockList.add(stock);
	                
	            }

	        } catch (SQLException ex) {
	            System.out.println("Get watch list stock error -->" + ex.getMessage());
	            return null;
	        } finally {

	        }
	        return stockList;
	    }
	 
	 
	 public static boolean addStockRecordtoWatchformanager(int managerId, SelectItem selectedItem){
		 Connection con = null;
		 
		 String query = "insert into mwatchlist(managerId,mstockSymbol,mstockName) values (?,?,?)";
	    
		

		      // create the mysql insert preparedstatement
			try{ 
				 
			  con = DataConnect.getConnection();
			  PreparedStatement preparedStmt =con.prepareStatement(query);
		      preparedStmt.setInt (1, managerId);
		      preparedStmt.setString (2, selectedItem.getLabel());
		      preparedStmt.setString (3, selectedItem.getDescription());
		      preparedStmt.execute();
			}
			catch (SQLException ex) {
	            System.out.println("manager Watch List insert error -->" + ex.getMessage());
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
	 	
	 public static List<Stock> getallWatchStockformanager(){
	        Connection con = null;
	        PreparedStatement ps = null;
	        List<Stock> mstockList= new ArrayList<Stock>();
	        try {
	        	con = DataConnect.getConnection();
	            ps = con.prepareStatement("select * from mwatchlist where managerId = ? ");
	            int managerId = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	    				.get("managerId");
	    	        ps.setInt(1, managerId); 
	    	     System.out.println(managerId);
	            ResultSet rs = ps.executeQuery();
	           
	            while (rs.next()) {
	            	Stock stock= new Stock();
	            	stock.setStockName(rs.getString("mstockName"));
	            	stock.setStockSymbol(rs.getString("mstockSymbol"));
	            	stock.setPrice(getPrice(rs.getString("mstockSymbol")));
	            	mstockList.add(stock);
	                
	            }

	        } catch (SQLException ex) {
	            System.out.println("Get watch list stock error -->" + ex.getMessage());
	            return null;
	        } finally {

	        }
	        return mstockList;
	    }
	 private static double getPrice(String symbol){
			
		 double price = 0;	  
		  String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min" + "&apikey=" + API_KEY;
		  InputStream inputStream;
		try {
			inputStream = new URL(url).openStream();
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
		      return price;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return price;
	 }
}
