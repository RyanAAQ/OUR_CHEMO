package ng.ourChemo.services;

import ng.ourChemo.data.models.Batch;
import ng.ourChemo.data.models.DispensedDrug;
import ng.ourChemo.data.models.Drug;
import ng.ourChemo.data.repositories.DispensedDrugsImpl;
import ng.ourChemo.data.repositories.DispensedDrugsRepository;
import ng.ourChemo.data.repositories.DrugRepository;
import ng.ourChemo.data.repositories.DrugRepositoryImpl;
import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import ng.ourChemo.utils.Mapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DrugInventoryServicesImpl implements DrugInventoryServices {
    private final DrugRepository drugRepository = new DrugRepositoryImpl();
    private final DispensedDrugsRepository dispensedDrugsRepository = new DispensedDrugsImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

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

        Drug drugToSave = Mapper.mapToDrug(request);
        Drug existingDrug = drugRepository.findByName(request.getName());
        if (existingDrug != null && existingDrug.getBrand() != null && existingDrug.getBrand().equalsIgnoreCase(request.getBrand())) {
            drugToSave = existingDrug;
        }

        if (request.getPurchaseQuantity() > 0) {
            List<Batch> batches = drugToSave.getBatches();
            if (batches == null) {
                batches = new ArrayList<>();
                drugToSave.setBatches(batches);
            }

            Batch batch = new Batch();
            batch.setId(batches.size() + 1);
            batch.setPurchaseQuantity(request.getPurchaseQuantity());
            batch.setQuantityLeft(request.getPurchaseQuantity());
            batch.setPurchaseDate(LocalDate.now());
            batch.setExpiryDate(request.getExpiryDate());
            batches.add(batch);
            drugToSave.setQuantity(drugToSave.getQuantity() + request.getPurchaseQuantity());
        }

        Drug savedDrug = drugRepository.save(drugToSave);
        AddDrugResponse response = new AddDrugResponse();
        response.setId(savedDrug.getId());
        response.setName(savedDrug.getName());
        response.setBrand(savedDrug.getBrand());
        response.setPrice(savedDrug.getPrice());
        response.setDrugBatch(savedDrug.getBatches());
        response.setTotalDrugs((int) drugRepository.count());
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
        List<DispensedDrug> dispenses = request.getItems();
        if (dispenses == null || dispenses.isEmpty()) {
            throw new IllegalArgumentException("Dispensed drug list cannot be empty");
        }

        int savedCount = 0;
        for (DispensedDrug dispense : dispenses) {
            if (dispense == null) {
                throw new IllegalArgumentException("Dispensed drug entry cannot be null");
            }
            savedCount++;
        }
        DispenseDrugsResponse response = new DispenseDrugsResponse();
        response.setSavedCount(savedCount);
        response.setMessage("Dispensed " + savedCount + " record(s) successfully");
        return response;
    }
}
