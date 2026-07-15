
package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.DispensedDrugs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DispensedDrugsRepositoryTest {

    private DispensedDrugsRepository dispensedDrugsRepository;

    @BeforeEach
    void setUp() {
        dispensedDrugsRepository = new DispensedDrugsImpl();
    }

    @Test
    void newRepositoryCountIsZero() {
        assertEquals(0, dispensedDrugsRepository.count());
    }

    @Test
    void saveRecordCountIsOne() {
        dispensedDrugsRepository.save(new DispensedDrugs());
        assertEquals(1, dispensedDrugsRepository.count());
    }

    @Test
    void saveRecordIsAssignedIdStartingFromOne() {
        DispensedDrugs record = dispensedDrugsRepository.save(new DispensedDrugs());
        assertEquals(1, record.getId());
    }

    @Test
    void saveTwoRecordsIdsIncrementCorrectly() {
        DispensedDrugs first = dispensedDrugsRepository.save(new DispensedDrugs());
        DispensedDrugs second = dispensedDrugsRepository.save(new DispensedDrugs());
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void saveTwoRecordsCountIsTwo() {
        dispensedDrugsRepository.save(new DispensedDrugs());
        dispensedDrugsRepository.save(new DispensedDrugs());
        assertEquals(2, dispensedDrugsRepository.count());
    }

    @Test
    void findByIdReturnsCorrectRecord() {
        DispensedDrugs record = dispensedDrugsRepository.save(new DispensedDrugs());
        DispensedDrugs found = dispensedDrugsRepository.findById(record.getId());
        assertEquals(record.getId(), found.getId());
    }

    @Test
    void findByIdReturnsNullWhenNotFound() {
        assertNull(dispensedDrugsRepository.findById(99));
    }

    @Test
    void findAllReturnsAllSavedRecords() {
        dispensedDrugsRepository.save(new DispensedDrugs());
        dispensedDrugsRepository.save(new DispensedDrugs());
        assertEquals(2, dispensedDrugsRepository.findAll().size());
    }

    @Test
    void findAllOnEmptyRepositoryReturnsEmptyList() {
        assertTrue(dispensedDrugsRepository.findAll().isEmpty());
    }
}
