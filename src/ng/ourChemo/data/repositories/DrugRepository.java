package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Drug;

import java.util.List;

public interface DrugRepository {

    Drug save(Drug drug);
    void delete(Drug drug);
    void deleteAll();
    long count();
    Drug findById(long id);
    Drug findByName(String name);
    List<Drug> search(String word);
    int size();
}
