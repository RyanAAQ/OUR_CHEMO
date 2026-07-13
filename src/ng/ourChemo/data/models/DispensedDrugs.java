package ng.ourChemo.data.models;

import java.time.LocalDateTime;
import java.util.List;

public class DispensedDrugs {
    private int id;
    private List<DispensedDrug> dispensedDrugs;
    private LocalDateTime saleDateTime;
    private User dispenseBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DispensedDrug> getDispensedDrugs() {
        return dispensedDrugs;
    }

    public void setDispensedDrugs(List<DispensedDrug> dispensedDrugs) {
        this.dispensedDrugs = dispensedDrugs;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
    }

    public User getDispenseBy() {
        return dispenseBy;
    }

    public void setDispenseBy(User dispenseBy) {
        this.dispenseBy = dispenseBy;
    }

}
