package ng.ourChemo.data.models;

import java.util.List;

public class Drug {
    private int price;
    private String name;
    private List<Batch> batches;
    private int id;
    private int quantity;
    private String brand;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

}
