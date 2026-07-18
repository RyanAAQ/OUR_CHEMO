package ng.ourChemo.services;

import ng.ourChemo.data.models.DispensedDrugs;
import ng.ourChemo.data.models.Drug;
import ng.ourChemo.data.repositories.DispensedDrugsImpl;
import ng.ourChemo.data.repositories.DispensedDrugsRepository;
import ng.ourChemo.data.repositories.DrugRepository;
import ng.ourChemo.data.repositories.DrugRepositoryImpl;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import ng.ourChemo.utils.Mapper;

import java.util.List;

public class ChemistServiceImpl implements ChemistService {
    private final DrugRepository drugRepository = new DrugRepositoryImpl();
    private final DispensedDrugsRepository dispensedDrugsRepository = new DispensedDrugsImpl();

    @Override
    public AddDrugResponse addDrug(AddDrugRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Add drug request cannot be null");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Drug name is required");
        }
        if (request.getBrand() == null || request.getBrand().isEmpty()) {
            throw new IllegalArgumentException("Drug brand is required");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Drug price must be greater than zero");
        }

        Drug savedDrug = drugRepository.save(Mapper.mapToDrug(request));
        AddDrugResponse response = new AddDrugResponse();
        response.setId(savedDrug.getId());
        response.setName(savedDrug.getName());
        response.setBrand(savedDrug.getBrand());
        response.setPrice(savedDrug.getPrice());
        response.setMessage("Drug added successfully");
        return response;
    }

    @Override
    public UpdateDrugResponse updateDrug(UpdateDrugRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Update request cannot be null");
        }

        Drug savedDrug = drugRepository.findById(request.getId());
        if (savedDrug == null) {
            throw new IllegalArgumentException("Drug with id " + request.getId() + " not found");
        }

        if (request.getName() != null && !request.getName().isEmpty()) {
            savedDrug.setName(request.getName());
        }
        if (request.getBrand() != null && !request.getBrand().isEmpty()) {
            savedDrug.setBrand(request.getBrand());
        }
        if (request.getPrice() > 0) {
            savedDrug.setPrice(request.getPrice());
        }

        UpdateDrugResponse response = new UpdateDrugResponse();
        response.setId(savedDrug.getId());
        response.setName(savedDrug.getName());
        response.setBrand(savedDrug.getBrand());
        response.setPrice(savedDrug.getPrice());
        response.setMessage("Drug updated successfully");
        return response;
    }

    @Override
    public DeleteDrugResponse deleteDrug(DeleteDrugRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Delete drug request cannot be null");
        }
        Drug drug = drugRepository.findById(request.getId());
        if (drug == null) {
            throw new IllegalArgumentException("Drug with id " + request.getId() + " not found");
        }
        drugRepository.delete(drug);
        DeleteDrugResponse response = new DeleteDrugResponse();
        response.setId(request.getId());
        response.setMessage("Drug deleted successfully");
        return response;
    }

    @Override
    public DispenseDrugsResponse dispenseDrugs(DispenseDrugsRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Dispense request cannot be null");
        }
        List<DispensedDrugs> dispenses = request.getDispenses();
        if (dispenses == null || dispenses.isEmpty()) {
            throw new IllegalArgumentException("Dispensed drug list cannot be empty");
        }

        int savedCount = 0;
        for (DispensedDrugs dispense : dispenses) {
            if (dispense == null) {
                throw new IllegalArgumentException("Dispensed drug entry cannot be null");
            }
            dispensedDrugsRepository.save(dispense);
            savedCount++;
        }
        DispenseDrugsResponse response = new DispenseDrugsResponse();
        response.setSavedCount(savedCount);
        response.setMessage("Dispensed " + savedCount + " record(s) successfully");
        return response;
    }
}
