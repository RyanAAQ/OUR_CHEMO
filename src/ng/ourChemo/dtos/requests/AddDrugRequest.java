package ng.ourChemo.dtos.requests;

import java.time.YearMonth;

public class AddDrugRequest {
    private String name;
    private String brand;
    private int price;
    private int purchaseQuantity;
    private YearMonth expiryDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }
}
