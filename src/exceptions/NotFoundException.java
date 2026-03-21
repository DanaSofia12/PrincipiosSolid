package exceptions;
public class NotFoundException extends HttpException {
    public NotFoundException(String detail) { super(404, detail); }
    public NotFoundException(String detail, Object data) { super(404, detail, data); }
}