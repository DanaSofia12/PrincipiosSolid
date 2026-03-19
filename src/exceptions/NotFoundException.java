package exceptions;

public class NotFoundException extends HTTPException {
    public NotFoundException(String detail) {
        super(404, detail);
    }
}