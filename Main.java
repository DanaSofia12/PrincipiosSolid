import controllers.UserController;
import repositories.UserRepository;
import services.UserService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inyección de dependencias
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService, scanner);
        
        userController.iniciar();
        
        scanner.close();
    }
}