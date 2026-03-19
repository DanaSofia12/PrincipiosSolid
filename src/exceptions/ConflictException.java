package exceptions;

public class ConflictException extends HttpException {
    public ConflictException(String detail) {
        super(409, detail);
    }
}