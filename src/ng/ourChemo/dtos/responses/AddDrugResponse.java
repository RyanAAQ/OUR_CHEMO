package ng.ourChemo.dtos.responses;

import ng.ourChemo.data.models.Batch;

import java.util.List;

public class AddDrugResponse {
    private int id;
    private String name;
    private String brand;
    private int price;
    private List<Batch> drugBatch;
    private int totalDrugs;
    private String message;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Batch> getDrugBatch() {
        return drugBatch;
    }

    public void setDrugBatch(List<Batch> drugBatch) {
        this.drugBatch = drugBatch;
    }

    public int getTotalDrugs() {
        return totalDrugs;
    }

    public void setTotalDrugs(int totalDrugs) {
        this.totalDrugs = totalDrugs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
