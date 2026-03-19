package exceptions;

public class NotFoundException extends HttpException {
    public NotFoundException(String detail) {
        super(404, detail);
    }
}