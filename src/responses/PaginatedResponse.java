package responses;
import java.time.LocalDateTime;
import java.util.List;

public class PaginatedResponse<DataT> {
    private String status;
    private String message;
    private List<DataT> data;
    private PaginationMetadata metadata;
    private LocalDateTime timestamp;

    public PaginatedResponse(String status, String message, List<DataT> data, PaginationMetadata metadata) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.metadata = metadata;
        this.timestamp = LocalDateTime.now();
    }

    public void print() {
        System.out.println("[" + timestamp + "] " + status + ": " + message);
        System.out.println("Metadatos: " + metadata.toString());
        System.out.println("Datos: " + data.size() + " registros.");
    }
}