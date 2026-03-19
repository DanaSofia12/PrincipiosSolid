package services;
import java.util.List;

public interface ServiceInterface<T> {
    List<T> getAll(int skip, int limit);
    T getByPublicId(String publicId);
    T create(T data);
}