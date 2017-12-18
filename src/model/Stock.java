package model;

public class Stock {
private int stockId;
private String stockName;
private String stockSymbol;
private double price;
public int getStockId() {
	return stockId;
}
public void setStockId(int stockId) {
	this.stockId = stockId;
}
public String getStockName() {
	return stockName;
}
public void setStockName(String stockName) {
	this.stockName = stockName;
}
public String getStockSymbol() {
	return stockSymbol;
}
public void setStockSymbol(String stockSymbol) {
	this.stockSymbol = stockSymbol;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
}
