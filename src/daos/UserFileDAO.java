package daos;

import models.User;
import repositories.RepositoryInterface;
import utils.FileManager;
import java.util.ArrayList;
import java.util.List;

public class UserFileDAO implements RepositoryInterface<User> {
    // Composición: El DAO usa el FileManager
    private final FileManager fileManager;

    public UserFileDAO() {
        this.fileManager = new FileManager("database_users.txt");
    }

    // Método privado traducir las líneas de texto a Objetos
    private List<User> readAll() {
        List<User> users = new ArrayList<>();
        List<String> lineas = fileManager.leerLineas(); 
        
        for (String linea : lineas) {
            String[] data = linea.split(",");
            if (data.length == 5) {
                boolean isActivo = Boolean.parseBoolean(data[4]);
                users.add(new User(data[0], data[1], data[2], data[3], isActivo));
            }
        }
        return users;
    }

    // Método privado traducir los Objetos User a líneas de texto
    private void saveAll(List<User> users) {
        List<String> lineas = new ArrayList<>();
        for (User user : users) {
            lineas.add(String.format("%s,%s,%s,%s,%b", 
                user.getPublicId(), user.getNombre(), user.getEmail(), user.getRol(), user.isActivo()));
        }
        fileManager.guardarLineas(lineas); 
    }

    @Override
    public List<User> getAll(int skip, int limit) {
        List<User> db = readAll();
        int size = db.size();
        if (skip >= size) return new ArrayList<>();
        int end = Math.min(skip + limit, size);
        return new ArrayList<>(db.subList(skip, end));
    }

    @Override
    public User getByPublicId(String publicId) {
        return readAll().stream().filter(u -> u.getPublicId().equals(publicId)).findFirst().orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return readAll().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public User create(User user) {
        List<User> db = readAll();
        db.add(user);
        saveAll(db);
        return user;
    }

    @Override
    public User update(User dbObj, User data) {
        List<User> db = readAll();
        for (int i = 0; i < db.size(); i++) {
            if (db.get(i).getPublicId().equals(dbObj.getPublicId())) {
                db.set(i, data);
                break;
            }
        }
        saveAll(db);
        return data;
    }

    @Override
    public boolean delete(String id) {
        List<User> db = readAll();
        boolean removed = db.removeIf(u -> u.getPublicId().equals(id));
        if (removed) {
            saveAll(db);
        }
        return removed;
    }

    @Override
    public int count() {
        return readAll().size();
    }
}