package controllers;

import dtos.UserDTO;
import exceptions.HttpException;
import responses.PaginatedResponse;
import responses.PaginationMetadata;
import services.ServiceInterface;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final ServiceInterface<UserDTO> userService;
    private final Scanner scanner;

    public UserController(ServiceInterface<UserDTO> userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- SISTEMA---");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Actualizar información de usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1": registrarUsuario(); break;
                    case "2": listarUsuarios(); break;
                    case "3": actualizarUsuario(); break;
                    case "4": eliminarUsuario(); break;
                    case "5": 
                        salir = true; 
                        System.out.println("Cerrando el sistema...");
                        break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (HttpException e) {
                System.err.println("\n[ERROR " + e.getStatusCode() + "] " + e.getDetail());
                utils.ErrorLogger.log(e);
            } catch (Exception e) {
                System.err.println("\n[ERROR INESPERADO] " + e.getMessage());
            }
        }
    }

    private void registrarUsuario() {
        System.out.print("Ingrese el ID del usuario: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese el rol (ej. Admin, Dev): ");
        String rol = scanner.nextLine();

        UserDTO dto = new UserDTO(id, nombre, email, rol, true);
        userService.create(dto);
        System.out.println("-> Usuario guardado exitosamente.");
    }

    private void listarUsuarios() {
        System.out.print("Ingrese el número de página: ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese registros por página: ");
        int size = Integer.parseInt(scanner.nextLine());

        int skip = (page - 1) * size;
        int totalUsers = userService.count();
        List<UserDTO> userList = userService.getAll(skip, size);
        
        PaginationMetadata meta = new PaginationMetadata(page, size, totalUsers);
        PaginatedResponse<UserDTO> response = new PaginatedResponse<>(
            "success", "Listado de usuarios", userList, meta
        );
        
        System.out.println("\n--- RESULTADO ---");
        response.print();
        for (UserDTO u : userList) {
            String estado = u.isActivo() ? "Activo" : "Inactivo";
            System.out.println("- [" + u.getPublicId() + "] " + u.getNombre() + " | " + u.getEmail() + " | Rol: " + u.getRol() + " | Estado: " + estado);
        }
    }

    private void actualizarUsuario() {
        System.out.print("Ingrese el ID del usuario a actualizar: ");
        String id = scanner.nextLine();
        
        UserDTO actual = userService.getByPublicId(id);
        
        System.out.println("\n-- VALORES ACTUALES --");
        
        System.out.print("Nombre actual [" + actual.getNombre() + "]: ");
        String inputNombre = scanner.nextLine();
        String nuevoNombre = inputNombre.trim().isEmpty() ? actual.getNombre() : inputNombre;

        System.out.print("Correo actual [" + actual.getEmail() + "]: ");
        String inputEmail = scanner.nextLine();
        String nuevoEmail = inputEmail.trim().isEmpty() ? actual.getEmail() : inputEmail;

        System.out.print("Rol actual [" + actual.getRol() + "]: ");
        String inputRol = scanner.nextLine();
        String nuevoRol = inputRol.trim().isEmpty() ? actual.getRol() : inputRol;

        System.out.print("Estado actual activo? [" + actual.isActivo() + "] (true/false): ");
        String inputEstado = scanner.nextLine();
        boolean nuevoEstado = inputEstado.trim().isEmpty() ? actual.isActivo() : Boolean.parseBoolean(inputEstado);

        UserDTO dtoActualizado = new UserDTO(id, nuevoNombre, nuevoEmail, nuevoRol, nuevoEstado);
        userService.update(id, dtoActualizado);
        
        System.out.println("-> Usuario actualizado correctamente.");
    }

    private void eliminarUsuario() {
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        String id = scanner.nextLine();
        
        userService.delete(id);
        System.out.println("-> Usuario eliminado definitivamente.");
    }
}