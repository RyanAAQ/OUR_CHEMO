package ng.ourChemo.dtos.requests;

import ng.ourChemo.data.models.DispensedDrugs;

import java.util.List;

public class DispenseDrugsRequest {
    private List<DispensedDrugs> dispenses;

    public List<DispensedDrugs> getDispenses() {
        return dispenses;
    }

    public void setDispenses(List<DispensedDrugs> dispenses) {
        this.dispenses = dispenses;
    }
}
