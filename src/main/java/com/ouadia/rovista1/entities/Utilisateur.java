package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.StatutCompte;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String motDePasse;
    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;
    private String phone;
    private String adresse;

    @OneToMany(mappedBy = "destinataire")
    private List<Notification> notifications;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public Utilisateur() {}

    public Utilisateur(Long id, String username, String email, String motDePasse,
                       StatutCompte statutCompte, String phone,
                       String adresse, List<Notification> notifications, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
        this.notifications = notifications;
        this.roles = roles;
    }

    public Utilisateur(Long id, String username, String email, String motDePasse,
                       StatutCompte statutCompte, String phone, String adresse) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutCompte = statutCompte;
        this.phone = phone;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public StatutCompte getStatutCompte() {
        return statutCompte;
    }

    public void setStatutCompte(StatutCompte statutCompte) {
        this.statutCompte = statutCompte;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
