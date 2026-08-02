package ng.ourChemo.dtos.responses;

import ng.ourChemo.data.models.Drug;

import java.util.List;

public class SearchDrugResponse {
    private List<Drug> drugs;

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
