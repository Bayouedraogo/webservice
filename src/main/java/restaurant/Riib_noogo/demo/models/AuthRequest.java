package restaurant.Riib_noogo.demo.models;



public class AuthRequest {

    private String username;  // Champ pour le nom d'utilisateur
    private String password;  // Champ pour le mot de passe

    // Constructeur
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter pour username
    public String getUsername() {
        return username;
    }

    // Setter pour username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter pour password
    public String getPassword() {
        return password;
    }

    // Setter pour password
    public void setPassword(String password) {
        this.password = password;
    }
}


