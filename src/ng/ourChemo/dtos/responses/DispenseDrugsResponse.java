package ng.ourChemo.dtos.responses;

public class DispenseDrugsResponse {
    private int savedCount;
    private String message;

    public int getSavedCount() {
        return savedCount;
    }

    public void setSavedCount(int savedCount) {
        this.savedCount = savedCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
