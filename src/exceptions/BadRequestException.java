package exceptions;

public class BadRequestException extends HttpException {
    public BadRequestException(String detail) { super(400, detail); }
    public BadRequestException(String detail, Object data) { super(400, detail, data); }
}