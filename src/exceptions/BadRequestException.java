package exceptions;

public class BadRequestException extends HttpException {
    public BadRequestException(String detail) {
        super(400, detail);
    }
}