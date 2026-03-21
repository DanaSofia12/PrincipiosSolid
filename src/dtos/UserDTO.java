package dtos;

public class UserDTO {
    private final String publicId;
    private String nombre;
    private String email;
    private String rol;
    private boolean activo;

    public UserDTO(String publicId, String nombre, String email, String rol, boolean activo) {
        this.publicId = publicId;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    public String getPublicId() { return publicId; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    
    @Override
    public String toString() {
        return String.format("UserDTO{id='%s', nombre='%s', email='%s', rol='%s', activo=%b}", 
            publicId, nombre, email, rol, activo);
    }
}