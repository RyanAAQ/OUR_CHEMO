package ng.ourChemo.dtos.requests;

import ng.ourChemo.data.models.DispensedDrug;

import java.util.List;

public class DispenseDrugsRequest {
    private String username;
    private List<DispensedDrug> items;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DispensedDrug> getItems() {
        return items;
    }

    public void setItems(List<DispensedDrug> items) {
        this.items = items;
    }
}
