import controllers.UserController;
import daos.UserFileDAO;
import dtos.UserDTO;
import models.User;
import repositories.RepositoryInterface;
import services.ServiceInterface;
import services.UserService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //instanciar DAO que guarda en archivo de texto
        RepositoryInterface<User> fileDAO = new UserFileDAO();
        
        // Inyectar la dependencia en el servicio
        ServiceInterface<UserDTO> userService = new UserService(fileDAO);
        
        // Inyectar el servicio en el controlador
        UserController userController = new UserController(userService, scanner);
        
        userController.iniciar();
        
        scanner.close();
    }
}