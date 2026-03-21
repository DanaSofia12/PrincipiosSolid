package exceptions;

public abstract class HttpException extends RuntimeException {
    protected int statusCode;
    protected String detail;
    protected Object errorData; 

    public HttpException(int statusCode, String detail) {
        super(detail);
        this.statusCode = statusCode;
        this.detail = detail;
        this.errorData = null;
    }

    public HttpException(int statusCode, String detail, Object errorData) {
        super(detail);
        this.statusCode = statusCode;
        this.detail = detail;
        this.errorData = errorData;
    }

    public int getStatusCode() { return statusCode; }
    public String getDetail() { return detail; }
    public Object getErrorData() { return errorData; }
}