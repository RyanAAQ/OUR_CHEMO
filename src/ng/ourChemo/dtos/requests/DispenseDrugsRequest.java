package ng.ourChemo.dtos.requests;

import ng.ourChemo.data.models.DispensedDrug;

import java.util.List;

public class DispenseDrugsRequest {
    private List<DispensedDrug> items;

    public List<DispensedDrug> getItems() {
        return items;
    }

    public void setItems(List<DispensedDrug> items) {
        this.items = items;
    }
}
