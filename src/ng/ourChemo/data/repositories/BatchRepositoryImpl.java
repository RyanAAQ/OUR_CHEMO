package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Batch;

import java.util.ArrayList;
import java.util.List;

public class BatchRepositoryImpl implements BatchRepository {
    private int nextId;
    private final List<Batch> batches = new ArrayList<>();

    @Override
    public Batch save(Batch batch) {
        if (batch == null) {
            throw new IllegalArgumentException("Batch cannot be null");
        }

        if (batch.getId() == 0) {
            batch.setId(++nextId);
            batches.add(batch);
            return batch;
        }

        for (int i = 0; i < batches.size(); i++) {
            if (batches.get(i).getId() == batch.getId()) {
                batches.set(i, batch);
                return batch;
            }
        }

        batch.setId(++nextId);
        batches.add(batch);
        return batch;
    }

    @Override
    public long count() {
        return batches.size();
    }

    @Override
    public Batch findById(int id) {
        for (Batch batch : batches) {
            if (batch.getId() == id) {
                return batch;
            }
        }
        return null;
    }

    @Override
    public List<Batch> findByDrugId(int drugId) {
        List<Batch> result = new ArrayList<>();
        for (Batch batch : batches) {
            if (batch.getDrugId() == drugId) {
                result.add(batch);
            }
        }
        return result;
    }

    @Override
    public List<Batch> findAll() {
        return batches;
    }

    @Override
    public void deleteAll() {
        batches.clear();
        nextId = 0;
    }
}
