package ng.ourChemo.services;

import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrugInventoryServiceImplTest {

    private DrugInventoryServices chemistService;

    @BeforeEach
    void setUp() {
        chemistService = new DrugInventoryServicesImpl();
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

        assertThrows(IllegalArgumentException.class, () -> chemistService.deleteDrug(deleteRequest));
    }

    @Test
    void addDrugWithPurchaseQuantityCreatesBatchDetails() {
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Acme");
        request.setPrice(500);
        request.setPurchaseQuantity(12);

        AddDrugResponse response = chemistService.addDrug(request);

        assertNotNull(response.getDrugBatch());
        assertEquals(1, response.getDrugBatch().size());
    }

    @Test
    void addDrugWithSameNameAndBrandReusesExistingDrug() {
        AddDrugRequest firstRequest = new AddDrugRequest();
        firstRequest.setName("Paracetamol");
        firstRequest.setBrand("Acme");
        firstRequest.setPrice(500);
        AddDrugResponse firstResponse = chemistService.addDrug(firstRequest);

        AddDrugRequest secondRequest = new AddDrugRequest();
        secondRequest.setName("Paracetamol");
        secondRequest.setBrand("Acme");
        secondRequest.setPrice(750);
        secondRequest.setPurchaseQuantity(5);
        AddDrugResponse secondResponse = chemistService.addDrug(secondRequest);

        assertEquals(firstResponse.getId(), secondResponse.getId());
        assertEquals(1, secondResponse.getDrugBatch().size());
    }

    @Test
    void addDrugWithInvalidDataThrowsException() {
        AddDrugRequest request = new AddDrugRequest();
        request.setBrand("ryan");
        request.setPrice(500);

        assertThrows(IllegalArgumentException.class, () -> chemistService.addDrug(request));
    }

    @Test
    void deleteDrugWithNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> chemistService.deleteDrug(null));
    }

    @Test
    void deleteDrugWithNonExistentIdThrowsException() {
        DeleteDrugRequest request = new DeleteDrugRequest();
        request.setId(999);

        assertThrows(IllegalArgumentException.class, () -> chemistService.deleteDrug(request));
    }

    @Test
    void updateDrugWithNonExistentIdThrowsException() {
        UpdateDrugRequest request = new UpdateDrugRequest();
        request.setId(999);
        request.setName("Ibuprofen");
        request.setBrand("Beta");
        request.setPrice(700);

        assertThrows(IllegalArgumentException.class, () -> chemistService.updateDrug(request));
    }
}
