package ng.ourChemo.services;

import ng.ourChemo.data.models.Batch;
import ng.ourChemo.data.models.DispensedDrug;
import ng.ourChemo.data.models.DispensedDrugs;
import ng.ourChemo.data.models.Drug;
import ng.ourChemo.data.models.User;
import ng.ourChemo.data.repositories.BatchRepository;
import ng.ourChemo.data.repositories.BatchRepositoryImpl;
import ng.ourChemo.data.repositories.DispensedDrugsImpl;
import ng.ourChemo.data.repositories.DispensedDrugsRepository;
import ng.ourChemo.data.repositories.DrugRepository;
import ng.ourChemo.data.repositories.DrugRepositoryImpl;
import ng.ourChemo.data.repositories.UserRepository;
import ng.ourChemo.data.repositories.UserRepositoryImpl;
import ng.ourChemo.dtos.requests.AddDrugRequest;
import ng.ourChemo.dtos.requests.DeleteDrugRequest;
import ng.ourChemo.dtos.requests.DispenseDrugsRequest;
import ng.ourChemo.dtos.requests.SearchDrugRequest;
import ng.ourChemo.dtos.requests.UpdateDrugRequest;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.DeleteDrugResponse;
import ng.ourChemo.dtos.responses.DispenseDrugsResponse;
import ng.ourChemo.dtos.responses.SearchDrugResponse;
import ng.ourChemo.dtos.responses.UpdateDrugResponse;
import ng.ourChemo.exceptions.InsufficientStockException;
import ng.ourChemo.exceptions.MedicineNotFoundException;
import ng.ourChemo.exceptions.ValidationException;
import ng.ourChemo.utils.Mapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class DrugInventoryServicesImpl implements DrugInventoryServices {
    private final DrugRepository drugRepository = DrugRepositoryImpl.getInstance();
    private final BatchRepository batchRepository = new BatchRepositoryImpl();
    private final DispensedDrugsRepository dispensedDrugsRepository = new DispensedDrugsImpl();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public AddDrugResponse addDrug(AddDrugRequest request) {
        if (request == null)
            throw new ValidationException("Add drug request cannot be null");
        if (request.getName() == null || request.getName().isEmpty())
            throw new ValidationException("Drug name is required");
        if (request.getBrand() == null || request.getBrand().isEmpty())
            throw new ValidationException("Drug brand is required");
        if (request.getUnitPrice() <= 0)
            throw new ValidationException("Drug price must be greater than zero");

        Drug drugToSave = Mapper.mapToDrug(request);
        Drug existingDrug = drugRepository.findByName(request.getName());
        if (existingDrug != null && existingDrug.getBrand() != null &&
                existingDrug.getBrand().equalsIgnoreCase(request.getBrand())) {
            drugToSave = existingDrug;
        }

        Drug savedDrug = drugRepository.save(drugToSave);
        YearMonth expiryDate;

        if (request.getPurchaseQuantity() > 0) {
            Batch batch = new Batch();
            batch.setDrugId(savedDrug.getId());
            batch.setCostPrice(request.getCostPrice());
            batch.setPurchaseQuantity(request.getPurchaseQuantity());
            batch.setQuantityLeft(request.getPurchaseQuantity());
            batch.setPurchaseDate(LocalDate.now());
            if(request.getExpiryDate() != null){
                expiryDate = request.getExpiryDate();
            }
            else{
                expiryDate = YearMonth.now().plusMonths(1);
            }
            batch.setExpiryDate(expiryDate);
            batchRepository.save(batch);

            savedDrug.setQuantity(savedDrug.getQuantity() + request.getPurchaseQuantity());

            List<Batch> batches = savedDrug.getBatches();
            if (batches == null) {
                batches = new ArrayList<>();
                savedDrug.setBatches(batches);
            }
            batches.add(batch);
            drugRepository.save(savedDrug);
        }

        AddDrugResponse response = new AddDrugResponse();
        response.setId(savedDrug.getId());
        response.setName(savedDrug.getName());
        response.setGenericName(savedDrug.getGenericName());
        response.setBrand(savedDrug.getBrand());
        response.setUnitPrice(savedDrug.getUnitPrice());
        response.setDrugBatch(savedDrug.getBatches());
        response.setTotalDrugs((int) drugRepository.count());
        response.setTotalQuantity(savedDrug.getQuantity());
        response.setMessage("Drug added successfully");
        return response;
    }

    @Override
    public UpdateDrugResponse updateDrug(UpdateDrugRequest request) {
        if (request == null)
            throw new MedicineNotFoundException("Update request cannot be null");

        Drug savedDrug = drugRepository.findById(request.getId());
        if (savedDrug == null)
            throw new MedicineNotFoundException("Drug with id " + request.getId() + " not found");

        if (request.getName() != null && !request.getName().isEmpty())
            savedDrug.setName(request.getName());
        if (request.getBrand() != null && !request.getBrand().isEmpty())
            savedDrug.setBrand(request.getBrand());
        if (request.getPrice() > 0)
            savedDrug.setUnitPrice(request.getPrice());

        UpdateDrugResponse response = new UpdateDrugResponse();
        response.setId(savedDrug.getId());
        response.setName(savedDrug.getName());
        response.setBrand(savedDrug.getBrand());
        response.setPrice(savedDrug.getUnitPrice());
        response.setMessage("Drug updated successfully");
        return response;
    }

    @Override
    public DeleteDrugResponse deleteDrug(DeleteDrugRequest request) {
        if (request == null)
            throw new MedicineNotFoundException("Delete drug request cannot be null");

        Drug drug = drugRepository.findById(request.getId());
        if (drug == null)
            throw new MedicineNotFoundException("Drug with id " + request.getId() + " not found");

        drugRepository.delete(drug);

        DeleteDrugResponse response = new DeleteDrugResponse();
        response.setId(request.getId());
        response.setMessage("Drug deleted successfully");
        return response;
    }

    @Override
    public DispenseDrugsResponse dispenseDrugs(DispenseDrugsRequest request) {
        if (request == null)
            throw new ValidationException("Dispense request cannot be null");
        if (request.getUsername() == null || request.getUsername().isEmpty())
            throw new ValidationException("Username is required");

        User user = userRepository.findByUsername(request.getUsername());
        if (user == null)
            throw new ValidationException("User not found");
        if (!user.isLoggedIn())
            throw new ValidationException("User is not logged in");

        List<DispensedDrug> items = request.getItems();
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Dispensed drug list cannot be empty");

        int totalAmount = 0;

        for (DispensedDrug item : items) {
            if (item == null)
                throw new IllegalArgumentException("Dispensed drug entry cannot be null");

            Drug drug = drugRepository.findById(item.getDrug().getId());
            if (drug == null)
                throw new MedicineNotFoundException("Drug with id " + item.getDrug().getId() + " not found");
            if (drug.getQuantity() < item.getQuantity())
                throw new InsufficientStockException("Insufficient stock");

            Batch batch = null;
            for (Batch batch2 : batchRepository.findByDrugId(drug.getId())) {
                if (batch2.getId() == item.getBatchId()) {
                    batch = batch2;
                    break;
                }
            }
            if (batch != null) {
                batch.setQuantityLeft(batch.getQuantityLeft() - item.getQuantity());
                batchRepository.save(batch);
            }

            item.setTotalPrice(item.getQuantity() * drug.getUnitPrice());
            totalAmount += item.getTotalPrice();

            drug.setQuantity(drug.getQuantity() - item.getQuantity());
            drugRepository.save(drug);
        }

        DispensedDrugs record = new DispensedDrugs();
        record.setDispensedDrugs(items);
        record.setSaleDateTime(LocalDateTime.now());
        record.setDispenseBy(user);
        dispensedDrugsRepository.save(record);

        DispenseDrugsResponse response = new DispenseDrugsResponse();
        response.setSavedCount(items.size());
        response.setTotalAmount(totalAmount);
        response.setMessage("Dispensed " + items.size() + " item(s) successfully. Total: " + totalAmount);
        return response;
    }

    @Override
    public SearchDrugResponse searchDrug(SearchDrugRequest request) {
        if (request == null || request.getWord() == null || request.getWord().isEmpty())
            throw new IllegalArgumentException("Search word is required");
        List<Drug> results = drugRepository.search(request.getWord());
        if (results.isEmpty())
            throw new MedicineNotFoundException("No medicines found matching: " + request.getWord());
        SearchDrugResponse response = new SearchDrugResponse();
        response.setDrugs(results);
        return response;
    }
}
