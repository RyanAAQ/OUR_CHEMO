package ng.ourChemo.services;

import ng.ourChemo.dtos.requests.*;
import ng.ourChemo.dtos.responses.AddDrugResponse;
import ng.ourChemo.dtos.responses.SearchDrugResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrugInventoryServicesImplTest {

    private AuthService authService;
    private DrugInventoryServices drugService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl();
        drugService = new DrugInventoryServicesImpl();
    }

    private void register() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("johndoe");
        request.setPassword("password");
        request.setFullName("John Doe");
        authService.registerChemist(request);
    }

    private void login() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("johndoe");
        request.setPassword("password");
        assertTrue(authService.login(request).isLoggedIn());
    }

    private void registerAndLogin() {
        register();
        login();
    }

    @Test
    void addDrugBatchCountIsOne() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Emzor");
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        AddDrugResponse response = drugService.addDrug(request);
        assertEquals(1, response.getDrugBatch().size());
    }

    @Test
    void addDrugQuantityMatchesPurchaseQuantity() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Emzor");
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        AddDrugResponse response = drugService.addDrug(request);
        assertEquals(100, response.getTotalQuantity());
    }

    @Test
    void addSameDrugTwiceAddsBatchAndIncreasesQuantity() {
        registerAndLogin();
        AddDrugRequest request1 = new AddDrugRequest();
        request1.setName("Paracetamol");
        request1.setBrand("Emzor");
        request1.setPrice(500);
        request1.setPurchaseQuantity(100);
        drugService.addDrug(request1);

        AddDrugRequest request2 = new AddDrugRequest();
        request2.setName("Paracetamol");
        request2.setBrand("Emzor");
        request2.setPrice(500);
        request2.setPurchaseQuantity(50);
        AddDrugResponse response = drugService.addDrug(request2);

        assertEquals(2, response.getDrugBatch().size());
        assertEquals(150, response.getTotalQuantity());
    }

    @Test
    void addDrugWithoutPurchaseQuantityHasNoBatch() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Vitamin C");
        request.setBrand("Emzor");
        request.setPrice(300);
        request.setPurchaseQuantity(0);
        AddDrugResponse response = drugService.addDrug(request);
        assertNull(response.getDrugBatch());
    }

    @Test
    void addDrugWithNullNameThrowsException() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName(null);
        request.setBrand("Emzor");
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        assertThrows(IllegalArgumentException.class, () -> drugService.addDrug(request));
    }

    @Test
    void addDrugWithNullBrandThrowsException() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand(null);
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        assertThrows(IllegalArgumentException.class, () -> drugService.addDrug(request));
    }

    @Test
    void addDrugWithZeroPriceThrowsException() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Emzor");
        request.setPrice(0);
        request.setPurchaseQuantity(100);
        assertThrows(IllegalArgumentException.class, () -> drugService.addDrug(request));
    }

    @Test
    void addDrugResponseContainsCorrectNameAndBrand() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Amoxicillin");
        request.setBrand("GSK");
        request.setPrice(800);
        request.setPurchaseQuantity(60);
        AddDrugResponse response = drugService.addDrug(request);
        assertEquals("Amoxicillin", response.getName());
        assertEquals("GSK", response.getBrand());
    }

    @Test
    void updateDrugWithNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> drugService.updateDrug(null));
    }

    @Test
    void updateDrugWithNonExistentIdThrowsException() {
        registerAndLogin();
        UpdateDrugRequest request = new UpdateDrugRequest();
        request.setId(999);
        request.setName("Ibuprofen");
        assertThrows(IllegalArgumentException.class, () -> drugService.updateDrug(request));
    }

    @Test
    void deleteDrugWithNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> drugService.deleteDrug(null));
    }

    @Test
    void deleteDrugWithNonExistentIdThrowsException() {
        registerAndLogin();
        DeleteDrugRequest request = new DeleteDrugRequest();
        request.setId(999);
        assertThrows(IllegalArgumentException.class, () -> drugService.deleteDrug(request));
    }

    @Test
    void dispenseWithNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> drugService.dispenseDrugs(null));
    }

    @Test
    void dispenseWithNullUsernameThrowsException() {
        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setUsername(null);
        assertThrows(IllegalArgumentException.class, () -> drugService.dispenseDrugs(request));
    }

    @Test
    void dispenseWithNonExistentUserThrowsException() {
        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setUsername("nobody");
        assertThrows(IllegalArgumentException.class, () -> drugService.dispenseDrugs(request));
    }

    @Test
    void dispenseWithUserNotLoggedInThrowsException() {
        register();
        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setUsername("johndoe");
        assertThrows(IllegalArgumentException.class, () -> drugService.dispenseDrugs(request));
    }

    @Test
    void dispenseWithEmptyItemsThrowsException() {
        registerAndLogin();
        DispenseDrugsRequest request = new DispenseDrugsRequest();
        request.setUsername("johndoe");
        request.setItems(new java.util.ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> drugService.dispenseDrugs(request));
    }

    @Test
    void searchWithNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> drugService.searchDrug(null));
    }

    @Test
    void searchWithEmptyQueryThrowsException() {
        SearchDrugRequest request = new SearchDrugRequest();
        request.setWord("");
        assertThrows(IllegalArgumentException.class, () -> drugService.searchDrug(request));
    }

    @Test
    void addOneDrugTotalDrugsIsOne() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Paracetamol");
        request.setBrand("Emzor");
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        AddDrugResponse response = drugService.addDrug(request);
        assertEquals(1, response.getTotalDrugs());
    }

    @Test
    void addTwoDifferentDrugsTotalDrugsIsTwo() {
        registerAndLogin();
        AddDrugRequest request1 = new AddDrugRequest();
        request1.setName("Paracetamol");
        request1.setBrand("Emzor");
        request1.setPrice(500);
        request1.setPurchaseQuantity(100);
        drugService.addDrug(request1);

        AddDrugRequest request2 = new AddDrugRequest();
        request2.setName("Amoxicillin");
        request2.setBrand("GSK");
        request2.setPrice(800);
        request2.setPurchaseQuantity(60);
        AddDrugResponse response = drugService.addDrug(request2);
        assertEquals(2, response.getTotalDrugs());
    }

    @Test
    void addSameDrugTwiceTotalDrugsRemainsOneAndBatchIsTwo() {
        registerAndLogin();
        AddDrugRequest request1 = new AddDrugRequest();
        request1.setName("Paracetamol");
        request1.setBrand("Emzor");
        request1.setPrice(500);
        request1.setPurchaseQuantity(100);
        drugService.addDrug(request1);

        AddDrugRequest request2 = new AddDrugRequest();
        request2.setName("Paracetamol");
        request2.setBrand("Emzor");
        request2.setPrice(500);
        request2.setPurchaseQuantity(50);
        AddDrugResponse response = drugService.addDrug(request2);
        assertEquals(1, response.getTotalDrugs());
        assertEquals(2, response.getDrugBatch().size());
    }

    @Test
    void twoChemistsAddSameDrugAndBrandResultsInOneDrugTwoBatchesAndCombinedQuantity() {        registerAndLogin();
        AddDrugRequest first = new AddDrugRequest();
        first.setName("Paracetamol");
        first.setBrand("Emzor");
        first.setPrice(500);
        first.setPurchaseQuantity(100);
        drugService.addDrug(first);

        RegisterUserRequest second = new RegisterUserRequest();
        second.setUsername("ryanaA9");
        second.setPassword("password");
        second.setFullName("Ryan Ariyo");
        authService.registerChemist(second);

        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("ryanaA9");
        request.setPassword("password");
        assertTrue(authService.login(request).isLoggedIn());

        AddDrugRequest request2 = new AddDrugRequest();
        request2.setName("Paracetamol");
        request2.setBrand("Emzor");
        request2.setPrice(500);
        request2.setPurchaseQuantity(50);
        AddDrugResponse response = drugService.addDrug(request2);

        assertEquals(1, response.getTotalDrugs());
        assertEquals(2, response.getDrugBatch().size());
        assertEquals(150, response.getTotalQuantity());
    }

    @Test
    void searchGenericNameOfDrugReturnsTheCorrectDrugName() {
        registerAndLogin();
        AddDrugRequest request = new AddDrugRequest();
        request.setName("Emzor Paracetamol");
        request.setGenericName("Paracetamol");
        request.setBrand("Emzor");
        request.setPrice(500);
        request.setPurchaseQuantity(100);
        drugService.addDrug(request);

        SearchDrugRequest searchRequest = new SearchDrugRequest();
        searchRequest.setWord("Kara");
        searchRequest.setWord("Para");
        SearchDrugResponse response = drugService.searchDrug(searchRequest);

        assertEquals(1, response.getDrugs().size());
        assertEquals("Paracetamol", response.getDrugs().getFirst().getGenericName());
    }
}
