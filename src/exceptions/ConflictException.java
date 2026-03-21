package exceptions;
public class ConflictException extends HttpException {
    public ConflictException(String detail) { super(409, detail); }
    public ConflictException(String detail, Object data) { super(409, detail, data); }
}