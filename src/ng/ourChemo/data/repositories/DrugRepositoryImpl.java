package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugRepositoryImpl implements DrugRepository {
    private static final DrugRepositoryImpl INSTANCE = new DrugRepositoryImpl();
    private int nextId;
    private final List<Drug> drugs = new ArrayList<>();

    private DrugRepositoryImpl() {}

    public static DrugRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Drug save(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null");

        if (drug.getId() == 0) {
            drug.setId(++nextId);
            drugs.add(drug);
            return drug;
        }

        for (int i = 0; i < drugs.size(); i++) {
            if (drugs.get(i).getId() == drug.getId()) {
                drugs.set(i, drug);
                return drug;
            }
        }

        drug.setId(++nextId);
        drugs.add(drug);
        return drug;
    }

    @Override
    public long count() {
        return drugs.size();
    }

    @Override
    public void delete(Drug drug) {
        if (drug == null) return;
        for (int i = 0; i < drugs.size(); i++) {
            if (drugs.get(i).getId() == drug.getId()) {
                drugs.remove(i);
                return;
            }
        }
    }

    @Override
    public void deleteAll() {
        drugs.clear();
        nextId = 0;
    }

    @Override
    public Drug findById(long id) {
        for (var drug : drugs) {
            if (drug.getId() == id) return drug;
        }
        return null;
    }

    @Override
    public Drug findByName(String name) {
        if (name == null || name.isEmpty()) return null;
        String normalized = name.trim().toLowerCase();
        for (var drug : drugs) {
            if (drug.getName() != null && drug.getName().trim().toLowerCase().equals(normalized)) {
                return drug;
            }
        }
        return null;
    }

    @Override
    public List<Drug> search(String word) {
        List<Drug> results = new ArrayList<>();
        if (word == null || word.isEmpty()) return results;
        String w = word.trim().toLowerCase();
        for (Drug drug : drugs) {
            boolean matchesName = drug.getName() != null && drug.getName().toLowerCase().contains(w);
            boolean matchesGeneric = drug.getGenericName() != null && drug.getGenericName().toLowerCase().contains(w);
            boolean matchesBrand = drug.getBrand() != null && drug.getBrand().toLowerCase().contains(w);
            if (matchesName || matchesGeneric || matchesBrand) results.add(drug);
        }
        return results;
    }

    @Override
    public int size() {
        return drugs.size();
    }
}
