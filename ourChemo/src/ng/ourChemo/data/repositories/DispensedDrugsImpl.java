package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.DispensedDrugs;

import java.util.ArrayList;
import java.util.List;

public class DispensedDrugsImpl implements DispensedDrugsRepository {
    private int count;
    private List<DispensedDrugs> dispensedDrugsList = new ArrayList<>();

    @Override
    public DispensedDrugs save(DispensedDrugs dispensedDrugs) {
        dispensedDrugsList.add(dispensedDrugs);
        count++;
        return dispensedDrugs;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public DispensedDrugs findById(long id) {
        for (DispensedDrugs dispensedDrugs : dispensedDrugsList) {
            if (dispensedDrugs.getId() == id) return dispensedDrugs;
        }
        return null;
    }

    @Override
    public List<DispensedDrugs> findAll() {
        return dispensedDrugsList;
    }
}
