package fetch.receipt.dto;

// DTO for wrapping the Receipt ID response from the /process API
public class IdResponse {
    private String id;

    public IdResponse() {
    }

    public IdResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}