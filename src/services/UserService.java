package services;

import exceptions.BadRequestException;
import exceptions.ConflictException;
import exceptions.NotFoundException;
import models.User;
import repositories.RepositoryInterface;
import java.util.List;

public class UserService implements ServiceInterface<User> {
    private final RepositoryInterface<User> repository;

    public UserService(RepositoryInterface<User> repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll(int skip, int limit) {
        if (skip < 0 || limit <= 0) {
            throw new BadRequestException("Los parámetros de paginación no son válidos.");
        }
        return repository.getAll(skip, limit);
    }

    @Override
    public User getByPublicId(String publicId) {
        User user = repository.getByPublicId(publicId);
        if (user == null) {
            throw new NotFoundException("Usuario con ID " + publicId + " no encontrado.");
        }
        return user;
    }

    @Override
    public User create(User data) {
        if (data.getPublicId() == null || data.getPublicId().trim().isEmpty()) {
            throw new BadRequestException("El ID del usuario no puede estar vacío.");
        }
        if (data.getEmail() == null || !data.getEmail().contains("@") || !data.getEmail().contains(".")) {
            throw new BadRequestException("El formato del correo electrónico es inválido.");
        }
        
        //  Evitar ID duplicado
        if (repository.getByPublicId(data.getPublicId()) != null) {
            throw new ConflictException("El ID " + data.getPublicId() + " ya está registrado en el sistema.");
        }
        
        //  Evitar Correo duplicado
        if (repository.getByEmail(data.getEmail()) != null) {
            throw new ConflictException("El correo " + data.getEmail() + " ya está en uso por otro usuario.");
        }
        
        return repository.create(data);
    }

    public int countUsers() {
        return repository.count();
    }
}