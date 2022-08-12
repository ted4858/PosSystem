package project03;

public interface PointOfSales {
	public boolean checkLogin(String userName, String passWord);
	public void incoming();
	public void outgoing();
	public void outgoingItemNameOrBarcode(boolean ItemNameOrBarcode);
	public void checkStock();
	public String salaryCalculation(long startTime);
	public void changePassword();
	public void changeUserName();
	public void selectChangePasswordOrUserName();
	public void itemSearch();
	public void deleteItem();
}
