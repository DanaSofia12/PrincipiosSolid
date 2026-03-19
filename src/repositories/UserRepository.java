package repositories;

import models.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements RepositoryInterface<User> {
    private List<User> db = new ArrayList<>();

    @Override
    public List<User> getAll(int skip, int limit) {
        int size = db.size();
        if (skip >= size) {
            return new ArrayList<>();
        }
        int end = Math.min(skip + limit, size);
        return new ArrayList<>(db.subList(skip, end));
    }

    @Override
    public User getByPublicId(String publicId) {
        return db.stream().filter(u -> u.getPublicId().equals(publicId)).findFirst().orElse(null);
    }

    //búsqueda por correo
    @Override
    public User getByEmail(String email) {
        return db.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public User create(User user) {
        db.add(user);
        return user;
    }

    @Override
    public User update(User dbObj, User data) { return null; }

    @Override
    public boolean delete(String id) { return false; }

    @Override
    public int count() {
        return db.size();
    }
}