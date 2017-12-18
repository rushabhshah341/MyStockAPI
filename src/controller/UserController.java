package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.login.DataConnect;

import model.Stock;

public class UserController {
	public static List<Stock> getAllStocks() {
		 Connection con = null;
	        PreparedStatement ps = null;
	        ArrayList<Stock> stockList= new ArrayList<Stock>();
	        try {
	            con = DataConnect.getConnection();
	            ps = con.prepareStatement("select * from stocks  ");
	               

	            ResultSet rs = ps.executeQuery();
	           
	            while (rs.next()) {
	               Stock stock = new Stock();
	            	stock.setStockId(rs.getInt("stockId"));
	            	stock.setStockName(rs.getString("stockName"));
	            	stock.setStockSymbol(rs.getString("stockSymbol"));
	                stockList.add(stock);
	                
	            }

	        } catch (SQLException ex) {
	            System.out.println("Get Stocks error -->" + ex.getMessage());
	            return null;
	        } finally {

	        }
	        return stockList;

		
		
	}

}
