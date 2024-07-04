public class Item {
    private String name;
    private double price;
    private String image_path;
    private int stocks;
    
    
    public Item(String name, double price, String imagePath, int stocks) {
        this.name = name;
        this.price = price;
        this.image_path = imagePath;
        this.stocks = stocks;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    public String getImagePath() {
        return image_path;
    }
    
    public int getStocks() {
    	return stocks;
    }
    
    public void setStocks(int stocks) {
        this.stocks = stocks;
    }
}
