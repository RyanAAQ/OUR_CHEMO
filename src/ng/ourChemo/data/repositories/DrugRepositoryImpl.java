package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugRepositoryImpl implements DrugRepository {
    private int count;
    private List<Drug> drugs = new ArrayList<>();

    @Override
    public Drug save(Drug drug) {
        drug.setId(++count);
        drugs.add(drug);
        return drug;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public void delete(Drug drug) {
        drugs.remove(drug);
        count--;
    }

    @Override
    public void deleteAll() {
        drugs.clear();
        count = 0;
    }

    @Override
    public Drug findById(long id) {
        for (var drug : drugs) {
            if (drug.getId() == id) return drug;
        }
        return null;
    }

    @Override
    public int size() {
        return drugs.size();
    }
}
