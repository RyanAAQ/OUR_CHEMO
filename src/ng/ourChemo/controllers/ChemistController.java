package ng.ourChemo.controllers;

import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import ng.ourChemo.services.DrugInventoryServices;
import ng.ourChemo.services.DrugInventoryServicesImpl;

public class ChemistController {

    private final DrugInventoryServices chemistService = new DrugInventoryServicesImpl();

    public AddDrugResponse addDrug(AddDrugRequest request) {
        return chemistService.addDrug(request);
    }

    public UpdateDrugResponse updateDrug(UpdateDrugRequest request) {
        return chemistService.updateDrug(request);
    }

    public DeleteDrugResponse deleteDrug(DeleteDrugRequest request) {
        return chemistService.deleteDrug(request);
    }

    public DispenseDrugsResponse dispenseDrugs(DispenseDrugsRequest request) {
        return chemistService.dispenseDrugs(request);
    }
}
