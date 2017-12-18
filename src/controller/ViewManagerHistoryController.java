package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.login.DataConnect;

import model.UserPurchase;

@ManagedBean
@SessionScoped


public class ViewManagerHistoryController {
	
private List<UserPurchase> managerPurchase;
	
	public String getManagerHistory(){
		
		 Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		  String managerId = params.get("managerId");
		  setManagerPurchase(getManagerPurchase(Integer.parseInt(managerId)));
		return "viewManagerHistory";
		
		
	}
	public List<UserPurchase> getManagerPurchase(int managerId){
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<UserPurchase> managerPurchaseList= new ArrayList<UserPurchase>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * from purchasedStock where managerId = ?  ");
            ps.setInt(1, managerId);
            
            System.out.println("rushabh this is manager id"+managerId);
            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {
               UserPurchase managerPurchase= new UserPurchase();
               managerPurchase.setManagerId(rs.getInt("managerId"));
               managerPurchase.setStockSymbol(rs.getString("stockSymbol"));
               managerPurchase.setPrice(rs.getDouble("price"));
               managerPurchase.setQuantity(rs.getInt("quantity"));	
               managerPurchase.setTotalAmount(rs.getDouble("totalAmount"));
               managerPurchase.setPurchaseDate(rs.getDate("purchaseDate"));
               managerPurchase.setManagerfees(rs.getDouble("managerfees"));
               managerPurchase.setStockName(rs.getString("stockName"));
               managerPurchaseList.add(managerPurchase);
                System.out.println(managerPurchaseList.size());
            }

        } catch (SQLException ex) {
            System.out.println("Get Manager Purchase error -->" + ex.getMessage());
            return null;
        } finally {

        }
        return managerPurchaseList;
    }
	public List<UserPurchase> getManagerPurchase() {
		return managerPurchase;
	}
	public void setManagerPurchase(List<UserPurchase> managerPurchase) {
		this.managerPurchase = managerPurchase;
	}
	

}
