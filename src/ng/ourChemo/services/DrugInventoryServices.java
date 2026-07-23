package ng.ourChemo.services;

import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;

public interface DrugInventoryServices {

    AddDrugResponse addDrug(AddDrugRequest request);
    UpdateDrugResponse updateDrug(UpdateDrugRequest request);
    DeleteDrugResponse deleteDrug(DeleteDrugRequest  request);
    DispenseDrugsResponse dispenseDrugs(DispenseDrugsRequest  request);
}

