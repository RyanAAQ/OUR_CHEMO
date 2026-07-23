package ng.ourChemo.data.models;

import java.time.LocalDate;
import java.time.YearMonth;

public class Batch { 

    private int costPrice;
    private int quantityLeft;
    private int id;
    private int purchaseQuantity;
    private int drugId;
    private LocalDate purchaseDate;
    private YearMonth expiryDate;

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }
}
