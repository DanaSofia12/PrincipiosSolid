package repositories;
import java.util.List;

public interface RepositoryInterface<T> {
    List<T> getAll(int skip, int limit);
    T getByPublicId(String publicId);
    T getByEmail(String email);
    T create(T obj);
    T update(T dbObj, T data);
    boolean delete(String id);
    int count();
}