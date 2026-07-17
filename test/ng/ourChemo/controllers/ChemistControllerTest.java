package ng.ourChemo.controllers;

import ng.ourChemo.data.models.DispensedDrugs;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChemistControllerTest {

    private final ChemistController controller = new ChemistController();

    @Test
    void addDrugDelegatesToServiceAndReturnsResponse() {
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Acme");
        request.setPrice(500);

        AddDrugResponse response = controller.addDrug(request);

        assertEquals("Paracetamol", response.getName());
        assertEquals("Acme", response.getBrand());
        assertEquals(500, response.getPrice());
        assertEquals("Drug added successfully", response.getMessage());
    }

    @Test
    void updateDrugDelegatesToServiceAndReturnsUpdatedResponse() {
        AddDrugRequest addRequest = new AddDrugRequest();
        addRequest.setName("Paracetamol");
        addRequest.setBrand("Acme");
        addRequest.setPrice(500);
        AddDrugResponse addResponse = controller.addDrug(addRequest);

        UpdateDrugRequest updateRequest = new UpdateDrugRequest();
        updateRequest.setId(addResponse.getId());
        updateRequest.setName("Ibuprofen");
        updateRequest.setBrand("Beta");
        updateRequest.setPrice(700);

        UpdateDrugResponse response = controller.updateDrug(updateRequest);

        assertEquals("Ibuprofen", response.getName());
        assertEquals("Beta", response.getBrand());
        assertEquals(700, response.getPrice());
        assertEquals("Drug updated successfully", response.getMessage());
    }

    @Test
    void deleteDrugDelegatesToServiceAndReturnsSuccessMessage() {
        AddDrugRequest addRequest = new AddDrugRequest();
        addRequest.setName("Paracetamol");
        addRequest.setBrand("Acme");
        addRequest.setPrice(500);
        AddDrugResponse response = controller.addDrug(addRequest);

        DeleteDrugRequest deleteRequest = new DeleteDrugRequest();
        deleteRequest.setId(response.getId());
        DeleteDrugResponse deleteResponse = controller.deleteDrug(deleteRequest);

        assertEquals(response.getId(), deleteResponse.getId());
        assertEquals("Drug deleted successfully", deleteResponse.getMessage());
    }

    @Test
    void dispenseDrugsDelegatesToService() {
        DispensedDrugs record = new DispensedDrugs();
        List<DispensedDrugs> dispenses = new ArrayList<>();
        dispenses.add(record);

        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setDispenses(dispenses);
        DispenseDrugsResponse response = controller.dispenseDrugs(request);

        assertEquals(1, response.getSavedCount());
        assertEquals("Dispensed 1 record(s) successfully", response.getMessage());
    }
}
