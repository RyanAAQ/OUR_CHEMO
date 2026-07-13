package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Drug;

public interface DrugRepository {

    Drug save(Drug drug);
    void delete(Drug drug);
    void deleteAll();
    long count();
    Drug findById(long id);
}
