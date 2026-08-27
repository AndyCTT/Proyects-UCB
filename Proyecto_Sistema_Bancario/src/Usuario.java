public abstract class Usuario {
 
    private String nombre;
    private String apellido;
    private String identificacion;
    private String correo;
    private String telefono;
    private String contrasena;
 
    public Usuario(String nombre, String apellido, String identificacion, String correo,
    String telefono, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getApellido() {
        return apellido;
    }
 
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
 
    public String getIdentificacion() {
        return identificacion;
    }
 
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
 
    public String getCorreo() {
        return correo;
    }
 
    public void setCorreo(String correo) {
        this.correo = correo;
    }
 
    public String getTelefono() {
        return telefono;
    }
 
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 
    public String getContrasena() {
        return contrasena;
    }
 
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
 
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
 
    public abstract String getRol();
 
    public abstract void mostrarInfo();
 
    @Override
    public String toString() {
        return identificacion + " / " + nombre + " " + apellido + " / " + correo + " / " + getRol();
    }
}