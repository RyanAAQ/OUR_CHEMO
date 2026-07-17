package ng.ourChemo.controllers;

import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import ng.ourChemo.services.ChemistService;
import ng.ourChemo.services.ChemistServiceImpl;

public class ChemistController {

    private final ChemistService chemistService = new ChemistServiceImpl();

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
