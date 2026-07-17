package ng.ourChemo.services;

import ng.ourChemo.data.models.DispensedDrugs;
import ng.ourChemo.data.models.Drug;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChemistServiceImplTest {

    private ChemistService chemistService;

    @BeforeEach
    void setUp() {
        chemistService = new ChemistServiceImpl();
    }

    @Test
    void addDrugReturnsSavedDrugResponse() {
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Acme");
        request.setPrice(500);
        AddDrugResponse response = chemistService.addDrug(request);

        assertEquals(1, response.getId());
        assertEquals("Paracetamol", response.getName());
        assertEquals("Acme", response.getBrand());
        assertEquals(500, response.getPrice());
        assertEquals("Drug added successfully", response.getMessage());
    }

    @Test
    void updateDrugChangesExistingDrugAndReturnsUpdatedResponse() {
        AddDrugRequest addRequest = new AddDrugRequest();
        addRequest.setName("Paracetamol");
        addRequest.setBrand("Acme");
        addRequest.setPrice(500);
        AddDrugResponse addResponse = chemistService.addDrug(addRequest);

        UpdateDrugRequest updateRequest = new UpdateDrugRequest();
        updateRequest.setId(addResponse.getId());
        updateRequest.setName("Ibuprofen");
        updateRequest.setBrand("Beta");
        updateRequest.setPrice(700);

        UpdateDrugResponse updateResponse = chemistService.updateDrug(updateRequest);

        assertEquals(addResponse.getId(), updateResponse.getId());
        assertEquals("Ibuprofen", updateResponse.getName());
        assertEquals("Beta", updateResponse.getBrand());
        assertEquals(700, updateResponse.getPrice());
        assertEquals("Drug updated successfully", updateResponse.getMessage());
    }

    @Test
    void deleteDrugRemovesDrugFromRepository() {
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Acme");
        request.setPrice(500);
        AddDrugResponse response = chemistService.addDrug(request);

        DeleteDrugRequest deleteRequest = new DeleteDrugRequest();
        deleteRequest.setId(response.getId());
        DeleteDrugResponse result = chemistService.deleteDrug(deleteRequest);
        assertEquals("Drug deleted successfully", result.getMessage());
        
        assertThrows(IllegalArgumentException.class, () -> {chemistService.deleteDrug(deleteRequest);
        });
    }

    @Test
    void dispenseDrugsSavesRecordsSuccessfully() {
        DispensedDrugs record = new DispensedDrugs();
        List<DispensedDrugs> dispenses = new ArrayList<>();
        dispenses.add(record);

        DispenseDrugsRequest dispenseRequest = new DispenseDrugsRequest();
        dispenseRequest.setDispenses(dispenses);
        DispenseDrugsResponse result = chemistService.dispenseDrugs(dispenseRequest);

        assertEquals("Dispensed 1 record(s) successfully", result.getMessage());
        assertEquals(1, result.getSavedCount());
    }

    @Test
    void addDrugWithInvalidDataThrowsException() {
        AddDrugRequest request = new AddDrugRequest();
        request.setBrand("ryan");
        request.setPrice(500);

        assertThrows(IllegalArgumentException.class, () -> chemistService.addDrug(request));
    }

    @Test
    void deleteDrugWithNonExistentIdThrowsException() {
        DeleteDrugRequest request = new DeleteDrugRequest();
        request.setId(999);

        assertThrows(IllegalArgumentException.class, () -> chemistService.deleteDrug(request));
    }

    @Test
    void dispenseDrugsWithEmptyListThrowsException() {
        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setDispenses(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> chemistService.dispenseDrugs(request));
    }

    @Test
    void updateDrugWithNonExistentIdThrowsException() {
        UpdateDrugRequest request = new UpdateDrugRequest();
        request.setId(999);
        request.setName("Ibuprofen");
        request.setBrand("nigger");
        request.setPrice(700);

        assertThrows(IllegalArgumentException.class, () -> chemistService.updateDrug(request));
    }
}
