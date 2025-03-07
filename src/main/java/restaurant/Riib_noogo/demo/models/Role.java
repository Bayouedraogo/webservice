package restaurant.Riib_noogo.demo.models;

public enum Role {
    ROLE_ADMIN,
    ROLE_CUSTOMER;

    // Cette méthode renvoie le nom du rôle en tant que chaîne
    public String getRoleName() {
        return this.name(); // Utilisation de name() pour récupérer le nom de l'élément de l'énumération
    }
}
