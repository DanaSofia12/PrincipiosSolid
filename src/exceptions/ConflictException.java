package exceptions;

public class ConflictException extends HTTPException {
    public ConflictException(String detail) {
        super(409, detail);
    }
}