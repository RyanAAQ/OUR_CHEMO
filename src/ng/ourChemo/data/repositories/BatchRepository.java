package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Batch;

import java.util.List;

public interface BatchRepository {

    Batch save(Batch batch);
    long count();
    Batch findById(int id);
    List<Batch> findByDrugId(int drugId);
    List<Batch> findAll();
    void deleteAll();
}
