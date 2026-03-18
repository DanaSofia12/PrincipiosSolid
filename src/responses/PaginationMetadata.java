package responses;

public class PaginationMetadata {
    private int page;
    private int size;
    private int total;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginationMetadata(int page, int size, int total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
        this.hasNext = page < totalPages;
        this.hasPrevious = page > 1;
    }

    @Override
    public String toString() {
        return "Pagina " + page + " de " + totalPages + " (Total: " + total + ")";
    }
}