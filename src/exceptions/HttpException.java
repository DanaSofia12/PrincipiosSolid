package exceptions;

public abstract class HTTPException extends RuntimeException {
    protected int statusCode;
    protected String detail;

    public HTTPException(int statusCode, String detail) {
        super(detail);
        this.statusCode = statusCode;
        this.detail = detail;
    }

    public int getStatusCode() { return statusCode; }
    public String getDetail() { return detail; }
}