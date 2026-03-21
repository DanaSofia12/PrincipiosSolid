package services;
import dtos.UserDTO;
import java.util.List;

public interface ServiceInterface<T> {
    List<T> getAll(int skip, int limit);
    T getByPublicId(String publicId);
    T create(T data);
    T update(String publicId, T data);
    boolean delete(String publicId);
    int count();
}