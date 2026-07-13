package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.Drug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrugRepositoryTest {

    private DrugRepository drugRepository;

    @BeforeEach
    void setUp() {
        drugRepository = new DrugRepositoryImpl();
    }

    @Test
    void newRepositoryCountIsZero() {
        assertEquals(0, drugRepository.count());
    }

    @Test
    void saveDrugCountIsOne() {
        drugRepository.save(new Drug());
        assertEquals(1, drugRepository.count());
    }

    @Test
    void saveDrugIsAssignedIdStartingFromOne() {
        Drug drug = drugRepository.save(new Drug());
        assertEquals(1, drug.getId());
    }

    @Test
    void saveTwoDrugsIdsIncrementCorrectly() {
        Drug first = drugRepository.save(new Drug());
        Drug second = drugRepository.save(new Drug());
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void saveTwoDrugsCountIsTwo() {
        drugRepository.save(new Drug());
        drugRepository.save(new Drug());
        assertEquals(2, drugRepository.count());
    }

    @Test
    void deleteDrugCountDecreases() {
        Drug drug = drugRepository.save(new Drug());
        drugRepository.save(new Drug());
        drugRepository.delete(drug);
        assertEquals(1, drugRepository.count());
    }

    @Test
    void deleteAllCountIsZero() {
        drugRepository.save(new Drug());
        drugRepository.save(new Drug());
        drugRepository.deleteAll();
        assertEquals(0, drugRepository.size());
    }

    @Test
    void findByIdReturnsCorrectDrug() {
        Drug drug = drugRepository.save(new Drug());
        Drug found = drugRepository.findById(drug.getId());
        assertEquals(drug.getId(), found.getId());
    }

    @Test
    void findByIdReturnsNullWhenNotFound() {
        assertNull(drugRepository.findById(99));
    }
}
