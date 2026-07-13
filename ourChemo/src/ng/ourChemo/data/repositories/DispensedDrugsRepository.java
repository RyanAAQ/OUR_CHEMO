package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.DispensedDrugs;

import java.util.List;

public interface DispensedDrugsRepository {

    DispensedDrugs save(DispensedDrugs dispensedDrugs);
    long count();
    DispensedDrugs findById(long id);
    List<DispensedDrugs> findAll();
}
