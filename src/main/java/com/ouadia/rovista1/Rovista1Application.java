package com.ouadia.rovista1;

import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.enums.StatutCompte;
import com.ouadia.rovista1.entities.enums.StatutEvenement;
import com.ouadia.rovista1.entities.enums.StatutOrganisateur;
import com.ouadia.rovista1.repositories.*;
import com.ouadia.rovista1.services.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling  // nettoyage automatique Activer le scheduling dans la classe principale ───────────
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class Rovista1Application {

	public static void main(String[] args) {
		SpringApplication.run(Rovista1Application.class, args);
	}

    @Bean
    public CommandLineRunner start(OrganisateurRepository organisateurRepository,
                                   AdminRepository adminRepository,
                                   EventRepository eventRepository,
                                   CategorieRepository categorieRepository,
                                   ClientRepository clientRepository,
                                   AvisRepository avisRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder){
        return args -> {

            Role r3 = new Role();
            r3.setRoleName("ADMIN");
            roleRepository.save(r3);

            Role r1 = new Role();
            r1.setRoleName("ORGANISATEUR");
            roleRepository.save(r1);

            Role r2 = new Role();
            r2.setRoleName("CLIENT");
            roleRepository.save(r2);

            Admin a = new Admin();
            a.setUsername("admin1");
            a.setEmail("admin1@gmail.com");
            a.setPhone("0788394921");
            a.setMotDePasse(passwordEncoder.encode("12345"));
            a.setAdresse("Casa");
            a.setRoles(List.of(r3, r1, r2));
            a.setCreatedAt(LocalDateTime.now());
            a.setEnabled(true);
            a.setAvatar("");
            a.setStatutCompte(StatutCompte.ACTIF);
            a.setNom("SLAMI");
            a.setPrenom("Amin");

            adminRepository.save(a);


            Organisateur o1 = new Organisateur();
                    o1.setUsername("organisateur1");
                    o1.setEmail("organisateur1@gmail.com");
                    o1.setPhone("0666754921");
                    o1.setMotDePasse(passwordEncoder.encode("12345"));
                    o1.setAdresse("Beni Mellal");
                    o1.setStatutCompte(StatutCompte.ACTIF);
                    o1.setStatutOrganisateur(StatutOrganisateur.ACTIF);
                    o1.setEnabled(true);
                    o1.setVerified(true);
                    o1.setRoles(List.of(r1, r2));
                    o1.setNumRegistre(667899799L);
                    o1.setSiret("667899799L");
                    o1.setNomOrganisation("orgnaisateur1");
                    o1.setPrenom("Mohamed");
                    o1.setNom("Zahi");
                    o1.setDateValidation(LocalDateTime.now());
                    o1.setAvatar("../assets/image1");
            o1.setCreatedAt(LocalDateTime.now());


            organisateurRepository.save(o1);

            Organisateur o2 = new Organisateur();
            o2.setUsername("organisateur2");
            o2.setEmail("organisateur3@gmail.com");
            o2.setPhone("0788394921");
            o2.setMotDePasse(passwordEncoder.encode("12345"));
            o2.setAdresse("Casa");
            o2.setSiret("667899799L");
            o2.setRoles(List.of(r1, r2));
            o2.setCreatedAt(LocalDateTime.now());

            o2.setEnabled(true);
            o2.setStatutCompte(StatutCompte.ACTIF);
            o2.setStatutOrganisateur(StatutOrganisateur.ACTIF);
            o2.setVerified(true);
            o2.setNumRegistre(622399799L);
            o2.setNomOrganisation("orgnaisateur2");
            o2.setPrenom("Hajar");
            o2.setNom("El Alami");
            o2.setDateValidation(LocalDateTime.now());
            o2.setAvatar("../assets/image2");

            organisateurRepository.save(o2);

            Organisateur o3 = new Organisateur();
            o3.setUsername("organisateur3");
            o3.setEmail("organisateur3@gmail.com");
            o3.setPhone("0666755361");
            o3.setSiret("667899799L");
            o3.setRoles(List.of(r1, r2));
            o3.setMotDePasse(passwordEncoder.encode("12345"));
            o3.setAdresse("Rabat");
            o3.setEnabled(true);
            o3.setCreatedAt(LocalDateTime.now());

            o3.setStatutCompte(StatutCompte.ACTIF);
            o3.setVerified(true);
            o3.setStatutOrganisateur(StatutOrganisateur.ACTIF);
            o3.setNumRegistre(667835499L);
            o3.setNomOrganisation("orgnaisateur3");
            o3.setPrenom("Ahmed");
            o3.setNom("Ben Ali");
            o3.setDateValidation(LocalDateTime.now());
            o3.setAvatar("../assets/image3");


            organisateurRepository.save(o3);

            Categorie c1 = new Categorie();
            c1.setNom("Sports");
            c1.setDescription("Evenements sportifs");
            categorieRepository.save(c1);

            Categorie c2 = new Categorie();
            c2.setNom("Festivals");
            c2.setDescription("festivals and music");
            categorieRepository.save(c2);

            Categorie c3 = new Categorie();
            c3.setNom("Expositions");
            c3.setDescription("Local Products");
            categorieRepository.save(c3);

            Categorie c4 = new Categorie();
            c4.setNom("Conferences");
            c4.setDescription("Conferences and courses");
            categorieRepository.save(c4);

            Categorie c5 = new Categorie();
            c5.setNom("Concerts");
            c5.setDescription("Conserts");
            categorieRepository.save(c5);

            Categorie c6 = new Categorie();
            c6.setNom("Théâtre");
            c6.setDescription("Théâtre");
            categorieRepository.save(c6);

            Categorie c7 = new Categorie();
            c7.setNom("Gastronomie");
            c7.setDescription("Gastronomie");
            categorieRepository.save(c7);

            Categorie c8 = new Categorie();
            c8.setNom("Randonnée");
            c8.setDescription("Randonnée");
            categorieRepository.save(c8);

            Categorie c9 = new Categorie();
            c9.setNom("Désert");
            c9.setDescription("Désert");
            categorieRepository.save(c9);

            Categorie c10 = new Categorie();
            c10.setNom("Culture");
            c10.setDescription("Local Culture");
            categorieRepository.save(c10);


            Categorie c11 = new Categorie();
            c11.setNom("Excursion");
            c11.setDescription("Circuit avec des amis");
            categorieRepository.save(c11);

            Evenement e1 = new Evenement();
            e1.setTitre("Festival Casa 3rd Edition");
            e1.setDescription("festival a casa");
            e1.setCategorie(c2);
            e1.setStatutEvenement(StatutEvenement.APPROUVE);
            e1.setCapacite(500);
            e1.setVille("Casablanca");
            e1.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e1.setDateCreation(LocalDateTime.now());
            e1.setOrganisateur(o1);
            e1.setDateDebut(LocalDateTime.of(2026, 6, 2, 10, 0));
            e1.setDateFin(LocalDateTime.of(2026, 6, 4, 20, 0));
            e1.setPrix(100.0);
            e1.setDateValidation(LocalDateTime.now());
            eventRepository.save(e1);

            Evenement e2 = new Evenement();
            e2.setTitre("Marathon Marrakech");
            e2.setDescription("Big marathon de marrakech");
            e2.setCategorie(c1);
            e2.setStatutEvenement(StatutEvenement.APPROUVE);
            e2.setCapacite(500);
            e2.setVille("Marrakech");
            e2.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e2.setDateCreation(LocalDateTime.now());
            e2.setOrganisateur(o2);
            e2.setDateDebut(LocalDateTime.of(2026, 6, 2, 10, 0));
            e2.setDateFin(LocalDateTime.of(2026, 6, 4, 20, 0));
            e2.setPrix(100.0);
            e2.setDateValidation(LocalDateTime.now());
            eventRepository.save(e2);

            Evenement e3 = new Evenement();
            e3.setTitre("Conference sur l' AI");
            e3.setDescription("Big Conference sur l'AI et Tech");
            e3.setCategorie(c4);
            e3.setStatutEvenement(StatutEvenement.APPROUVE);
            e3.setCapacite(100);
            e3.setVille("Rabat");
            e3.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e3.setDateCreation(LocalDateTime.now());
            e3.setOrganisateur(o3);
            e3.setDateDebut(LocalDateTime.of(2026, 6, 2, 10, 0));
            e3.setDateFin(LocalDateTime.of(2026, 6, 4, 20, 0));
            e3.setPrix(150.0);
            e3.setDateValidation(LocalDateTime.now());
            eventRepository.save(e3);

            Evenement e4 = new Evenement();
            e4.setTitre("Exposition international du Maroc ");
            e4.setDescription("Salon des produits marocaines traditionnel");
            e4.setCategorie(c3);
            e4.setStatutEvenement(StatutEvenement.APPROUVE);
            e4.setCapacite(150);
            e4.setVille("Essaouira");
            e4.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e4.setDateCreation(LocalDateTime.now());
            e4.setOrganisateur(o1);
            e4.setDateDebut(LocalDateTime.of(2026, 6, 2, 10, 0));
            e4.setDateFin(LocalDateTime.of(2026, 6, 4, 20, 0));
            e4.setPrix(50.99);
            e4.setDateValidation(LocalDateTime.now());
            eventRepository.save(e4);

            Evenement e5 = new Evenement();
            e5.setTitre("Concert Rock Legends");
            e5.setDescription("Concert Rock Legends");
            e5.setCategorie(c5);
            e5.setStatutEvenement(StatutEvenement.APPROUVE);
            e5.setCapacite(150);
            e5.setVille("Casablanca");
            e5.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e5.setDateCreation(LocalDateTime.now());
            e5.setOrganisateur(o1);
            e5.setDateDebut(LocalDateTime.of(2026, 6, 4, 10, 0));
            e5.setDateFin(LocalDateTime.of(2026, 6, 6, 20, 0));
            e5.setPrix(99.99);
            e5.setDateValidation(LocalDateTime.now());
            eventRepository.save(e5);

            Evenement e6 = new Evenement();
            e6.setTitre("Festival Jazz & Blues");
            e6.setDescription("Festival Jazz & Blues");
            e6.setCategorie(c2);
            e6.setStatutEvenement(StatutEvenement.APPROUVE);
            e6.setCapacite(150);
            e6.setVille("Casablanca");
            e6.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e6.setDateCreation(LocalDateTime.now());
            e6.setOrganisateur(o2);
            e6.setDateDebut(LocalDateTime.of(2026, 6, 5, 10, 0));
            e6.setDateFin(LocalDateTime.of(2026, 6, 6, 20, 0));
            e6.setPrix(109.99);
            e6.setDateValidation(LocalDateTime.now());
            eventRepository.save(e6);

            Evenement e7 = new Evenement();
            e7.setTitre("Conférence Tech 2026");
            e7.setDescription("Conférence Tech 2026");
            e7.setCategorie(c4);
            e7.setStatutEvenement(StatutEvenement.APPROUVE);
            e7.setCapacite(150);
            e7.setVille("Rabat");
            e7.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e7.setDateCreation(LocalDateTime.now());
            e7.setOrganisateur(o3);
            e7.setDateDebut(LocalDateTime.of(2026, 6, 3, 10, 0));
            e7.setDateFin(LocalDateTime.of(2026, 6, 6, 20, 0));
            e7.setPrix(109.99);
            e7.setDateValidation(LocalDateTime.now());
            eventRepository.save(e7);

            Evenement e8 = new Evenement();
            e8.setTitre("Exposition Art Moderne");
            e8.setDescription("Exposition Art Moderne");
            e8.setCategorie(c3);
            e8.setStatutEvenement(StatutEvenement.APPROUVE);
            e8.setCapacite(150);
            e8.setVille("Marrakech");
            e8.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e8.setDateCreation(LocalDateTime.now());
            e8.setOrganisateur(o3);
            e8.setDateDebut(LocalDateTime.of(2026, 6, 3, 10, 0));
            e8.setDateFin(LocalDateTime.of(2026, 6, 5, 20, 0));
            e8.setPrix(80.99);
            e8.setDateValidation(LocalDateTime.now());
            eventRepository.save(e8);

            Evenement e9 = new Evenement();
            e9.setTitre("Théâtre: Le Malade Imaginaire");
            e9.setDescription("Exposition Art Moderne");
            e9.setCategorie(c6);
            e9.setStatutEvenement(StatutEvenement.APPROUVE);
            e9.setCapacite(150);
            e9.setVille("Marrakech");
            e9.setLieuSpecifique("Rue 1, Drb Rlf, Emeuble 45");
            e9.setDateCreation(LocalDateTime.now());
            e9.setOrganisateur(o2);
            e9.setDateDebut(LocalDateTime.of(2026, 6, 4, 10, 0));
            e9.setDateFin(LocalDateTime.of(2026, 6, 6, 20, 0));
            e9.setPrix(160.99);
            e9.setDateValidation(LocalDateTime.now());
            eventRepository.save(e9);

            Client cl1 = new Client();
            cl1.setUsername("client1");
            cl1.setEmail("client1@gmail.com");
            cl1.setPhone("0666755361");
            cl1.setMotDePasse(passwordEncoder.encode("12345"));
            cl1.setAdresse("Rabat");
            cl1.setStatutCompte(StatutCompte.ACTIF);
            cl1.setEnabled(true);
            cl1.setRoles(List.of(r2));
            cl1.setNom("Ahmed");
            cl1.setPrenom("Ahmed");
            cl1.setCreatedAt(LocalDateTime.now());
            cl1.setDateNaissance(LocalDate.of(2026, 6, 4));
            clientRepository.save(cl1);

            Avis a1 = new Avis();
            a1.setNote(4.5);
            a1.setComment("bon");
            a1.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a1.setEvenement(e1);
            a1.setClient(cl1);
            avisRepository.save(a1);

            Avis a2 = new Avis();
            a2.setNote(4.5);
            a2.setComment("bon");
            a2.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a2.setEvenement(e2);
            a2.setClient(cl1);
            avisRepository.save(a2);

            Avis a3 = new Avis();
            a3.setNote(4.5);
            a3.setComment("bon");
            a3.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a3.setEvenement(e3);
            a3.setClient(cl1);
            avisRepository.save(a3);

            Avis a4 = new Avis();
            a4.setNote(4.5);
            a4.setComment("bon");
            a4.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a4.setEvenement(e4);
            a4.setClient(cl1);
            avisRepository.save(a4);

            Avis a5 = new Avis();
            a5.setNote(4.5);
            a5.setComment("bon");
            a5.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a5.setEvenement(e5);
            a5.setClient(cl1);
            avisRepository.save(a5);

            Avis a6 = new Avis();
            a6.setNote(4.5);
            a6.setComment("bon");
            a6.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a6.setEvenement(e6);
            a6.setClient(cl1);
            avisRepository.save(a6);

            Avis a7 = new Avis();
            a7.setNote(4.5);
            a7.setComment("bon");
            a7.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a7.setEvenement(e7);
            a7.setClient(cl1);
            avisRepository.save(a7);

            Avis a8 = new Avis();
            a8.setNote(4.5);
            a8.setComment("bon");
            a8.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a8.setEvenement(e8);
            a8.setClient(cl1);
            avisRepository.save(a8);

            Avis a9 = new Avis();
            a9.setNote(4.5);
            a9.setComment("bon");
            a9.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a9.setEvenement(e9);
            a9.setClient(cl1);
            avisRepository.save(a9);

        };
    }
}