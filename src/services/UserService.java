package services;

import dtos.UserDTO;
import exceptions.BadRequestException;
import exceptions.ConflictException;
import exceptions.NotFoundException;
import models.User;
import repositories.RepositoryInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService implements ServiceInterface<UserDTO> {
    private final RepositoryInterface<User> repository;
    private final Map<String, Integer> registroActualizaciones; // Límite en memoria

    public UserService(RepositoryInterface<User> repository) {
        this.repository = repository;
        this.registroActualizaciones = new HashMap<>();
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getPublicId(), user.getNombre(), user.getEmail(), user.getRol(), user.isActivo());
    }

    @Override
    public List<UserDTO> getAll(int skip, int limit) {
        if (skip < 0 || limit <= 0) throw new BadRequestException("Parámetros de paginación inválidos.");
        return repository.getAll(skip, limit).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getByPublicId(String publicId) {
        User user = repository.getByPublicId(publicId);
        if (user == null) throw new NotFoundException("Usuario no encontrado.");
        return toDTO(user);
    }

    @Override
    public UserDTO create(UserDTO data) {
        if (data.getPublicId() == null || data.getPublicId().trim().isEmpty()) {
            throw new BadRequestException("El ID del usuario no puede estar vacío.", data);
        }
        if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del usuario no puede estar vacío.", data);
        }
        if (data.getRol() == null || data.getRol().trim().isEmpty()) {
            throw new BadRequestException("El rol del usuario no puede estar vacío.", data);
        }
        if (data.getEmail() == null || data.getEmail().trim().isEmpty() || !data.getEmail().contains("@")) {
            throw new BadRequestException("El correo electrónico es inválido.", data);
        }
        if (repository.getByPublicId(data.getPublicId()) != null) {
            throw new ConflictException("El ID ya está registrado.", data);
        }
        if (repository.getByEmail(data.getEmail()) != null) {
            throw new ConflictException("El correo ya está en uso.", data);
        }

        User newUser = new User(data.getPublicId(), data.getNombre(), data.getEmail(), data.getRol(), data.isActivo());
        return toDTO(repository.create(newUser));
    }

    @Override
    public UserDTO update(String publicId, UserDTO data) {
        if (!publicId.equals(data.getPublicId())) {
            throw new BadRequestException("No está permitido modificar el ID.", data);
        }
        if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre no puede ser nulo ni estar vacío.", data);
        }
        if (data.getRol() == null || data.getRol().trim().isEmpty()) {
            throw new BadRequestException("El rol no puede ser nulo ni estar vacío.", data);
        }
        if (data.getEmail() == null || data.getEmail().trim().isEmpty() || !data.getEmail().contains("@")) {
            throw new BadRequestException("El correo electrónico es inválido.", data);
        }

        int vecesActualizado = registroActualizaciones.getOrDefault(publicId, 0);
        if (vecesActualizado >= 2) {
            throw new ConflictException("Límite alcanzado: Este usuario ya ha sido actualizado 2 veces en esta sesión.", data);
        }

        User original = repository.getByPublicId(publicId);
        if (original == null) {
            throw new NotFoundException("Usuario no encontrado para actualizar.", publicId);
        }

        User modificado = new User(publicId, data.getNombre(), data.getEmail(), data.getRol(), data.isActivo());

        boolean sinCambios = original.getNombre().equals(modificado.getNombre()) &&
                             original.getEmail().equals(modificado.getEmail()) &&
                             original.getRol().equals(modificado.getRol()) &&
                             original.isActivo() == modificado.isActivo();

        if (sinCambios) {
            throw new BadRequestException("No se detectaron cambios reales. La actualización fue cancelada.", data);
        }

        if (!original.getEmail().equals(modificado.getEmail())) {
            User emailCheck = repository.getByEmail(modificado.getEmail());
            if (emailCheck != null) {
                throw new ConflictException("El nuevo correo ya está en uso por otro usuario.", data);
            }
        }

        repository.update(original, modificado);
        registroActualizaciones.put(publicId, vecesActualizado + 1);

        return toDTO(modificado);
    }

    @Override
    public boolean delete(String publicId) {
        User existingUser = repository.getByPublicId(publicId);
        if (existingUser == null) throw new NotFoundException("Usuario no encontrado.");
        return repository.delete(publicId);
    }

    @Override
    public int count() {
        return repository.count();
    }
}