package controllers;

import exceptions.HttpException;
import models.User;
import responses.PaginatedResponse;
import responses.PaginationMetadata;
import services.UserService;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final Scanner scanner;

    public UserController(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== GESTIÓN DE USUARIOS (CLEAN CODE) ===");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Listar usuarios (Paginado)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        registrarUsuario();
                        break;
                    case "2":
                        listarUsuarios();
                        break;
                    case "3":
                        salir = true;
                        System.out.println("Cerrando el sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (HttpException e) {
                // Principio de Sustitución de Liskov
                System.err.println("\n[ERROR " + e.getStatusCode() + "] " + e.getDetail());
            } catch (Exception e) {
                System.err.println("\n[ERROR INESPERADO] " + e.getMessage());
            }
        }
    }

    private void registrarUsuario() {
        System.out.print("Ingrese el ID del usuario: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String email = scanner.nextLine();

        User nuevoUsuario = new User(id, email);
        userService.create(nuevoUsuario);
        System.out.println("-> Usuario registrado exitosamente.");
    }

    private void listarUsuarios() {
        System.out.print("Ingrese el número de página (ej. 1): ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese la cantidad de registros por página (ej. 5): ");
        int size = Integer.parseInt(scanner.nextLine());

        int skip = (page - 1) * size;
        int totalUsers = userService.countUsers();
        
        List<User> userList = userService.getAll(skip, size);
        
        PaginationMetadata meta = new PaginationMetadata(page, size, totalUsers);
        PaginatedResponse<User> response = new PaginatedResponse<>(
            "success", 
            "Listado de usuarios", 
            userList, 
            meta
        );
        
        System.out.println("\n--- RESULTADO ---");
        response.print();
        for (User u : userList) {
            System.out.println("- ID: " + u.getPublicId() + " | Email: " + u.getEmail());
        }
    }
}