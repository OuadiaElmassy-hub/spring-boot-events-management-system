package com.pfe.backend;

import com.pfe.backend.entities.*;
import com.pfe.backend.entities.*;
import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutEvenement;
import com.pfe.backend.entities.enums.StatutOrganisateur;
import com.pfe.backend.repositories.*;
import com.pfe.backend.repositories.*;
import com.pfe.backend.services.organisateur.OrganizerEventService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling  // nettoyage automatique Activer le scheduling dans la classe principale ───────────
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

    @Bean
    public CommandLineRunner start(OrganisateurRepository organisateurRepository,
                                   AdminRepository adminRepository,
                                   EventRepository eventRepository,
                                   CategorieRepository categorieRepository,
                                   ClientRepository clientRepository,
                                   AvisRepository avisRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder,
                                   OrganizerEventService eventService){
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

            String BASE_URL_CAT = "/uploads/categories/";
            String BASE_URL_Ev = "/uploads/evenements/";

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

            //c6 = Théâtre/Arts

            Categorie c1 = new Categorie();
            c1.setNom("Sports");
            c1.setDescription("Evenements sportifs");
            c1.setIconUrl(BASE_URL_CAT+"sport.svg");
            categorieRepository.save(c1);

            Categorie c2 = new Categorie();
            c2.setNom("Festivals");
            c2.setDescription("festivals and music");
            c2.setIconUrl(BASE_URL_CAT+"festival.svg");
            categorieRepository.save(c2);

            Categorie c3 = new Categorie();
            c3.setNom("Expositions");
            c3.setDescription("Local Products");
            c3.setIconUrl(BASE_URL_CAT+"exposition.svg");
            categorieRepository.save(c3);

            Categorie c4 = new Categorie();
            c4.setNom("Conferences");
            c4.setDescription("Conferences and courses");
            c4.setIconUrl(BASE_URL_CAT+"conference.svg");
            categorieRepository.save(c4);

            Categorie c5 = new Categorie();
            c5.setNom("Concerts");
            c5.setDescription("Concerts");
            c5.setIconUrl(BASE_URL_CAT+"celebration.svg");
            categorieRepository.save(c5);

            Categorie c6 = new Categorie();
            c6.setNom("Théâtre");
            c6.setDescription("Théâtre");
            c6.setIconUrl(BASE_URL_CAT+"theatre.svg");
            categorieRepository.save(c6);
//
//            Categorie c7 = new Categorie();
//            c7.setNom("Gastronomie");
//            c7.setDescription("Gastronomie");
//            categorieRepository.save(c7);
//
//            Categorie c8 = new Categorie();
//            c8.setNom("Randonnée");
//            c8.setDescription("Randonnée");
//            categorieRepository.save(c8);
//
//            Categorie c9 = new Categorie();
//            c9.setNom("Désert");
//            c9.setDescription("Désert");
//            categorieRepository.save(c9);
//            Categorie c11 = new Categorie();
//            c11.setNom("Excursion");
//            c11.setDescription("Circuit avec des amis");
//            categorieRepository.save(c11);

            Categorie c10 = new Categorie();
            c10.setNom("Culture");
            c10.setDescription("Local Culture");
            c10.setIconUrl(BASE_URL_CAT+"culture.svg");
            categorieRepository.save(c10);

            // Festivales O1
// e1 - Mawazine Rabat
            Evenement e1 = new Evenement();
            e1.setTitre("Mawazine Festival 2026");
            e1.setDescription("Plus grand festival musical d'Afrique avec artistes internationaux et marocains");
            e1.setCategorie(c2);
            e1.setStatutEvenement(StatutEvenement.APPROUVE);
            e1.setCapacite(20000);
            e1.setPlacesRestants(20000);
            e1.setNbPlacesVIP(500);
            e1.setPlacesVIPRestantes(500);
            e1.setPrixVIP(800.0);
            e1.setVille("Rabat");
            e1.setLieuSpecifique("Esplanade Salé - Boureg");
            e1.setDateCreation(LocalDateTime.now());
            e1.setOrganisateur(o1);
            e1.setDateDebut(LocalDateTime.of(2026, 6, 20, 18, 0));
            e1.setDateFin(LocalDateTime.of(2026, 6, 28, 2, 0));
            e1.setPrix(150.0);
            e1.setDateValidation(LocalDateTime.now());
            e1 = eventRepository.save(e1);
            eventService.storeEventImagesTeste(e1.getOrganisateur().getId(), e1.getId(), "mawazin1.png", "png", BASE_URL_Ev+"mawazin1.png");
            eventService.storeEventImagesTeste(e1.getOrganisateur().getId(), e1.getId(), "mawazin2.jpg", "jpg", BASE_URL_Ev+"mawazin2.jpg");

// e4 - Festival Gnaoua Essaouira
            Evenement e4 = new Evenement();
            e4.setTitre("Festival Gnaoua et Musiques du Monde");
            e4.setDescription("Musique gnaoua, jazz et musiques du monde sur les remparts d'Essaouira");
            e4.setCategorie(c2);
            e4.setStatutEvenement(StatutEvenement.APPROUVE);
            e4.setCapacite(8000);
            e4.setPlacesRestants(8000);
            e4.setNbPlacesVIP(300);
            e4.setPlacesVIPRestantes(300);
            e4.setPrixVIP(450.0);
            e4.setVille("Essaouira");
            e4.setLieuSpecifique("Place Moulay Hassan + Plage");
            e4.setDateCreation(LocalDateTime.now());
            e4.setOrganisateur(o1);
            e4.setDateDebut(LocalDateTime.of(2026, 6, 25, 19, 0));
            e4.setDateFin(LocalDateTime.of(2026, 6, 27, 1, 0));
            e4.setPrix(120.0);
            e4.setDateValidation(LocalDateTime.now());
            e4 = eventRepository.save(e4);
            eventService.storeEventImagesTeste(e4.getOrganisateur().getId(), e4.getId(), "gnawa1.png", "png", BASE_URL_Ev+"gnawa1.png");
            eventService.storeEventImagesTeste(e4.getOrganisateur().getId(), e4.getId(), "gnawa2.webp", "webp", BASE_URL_Ev+"gnawa2.webp");
            eventService.storeEventImagesTeste(e4.getOrganisateur().getId(), e4.getId(), "gnawa3.png", "jpg", BASE_URL_Ev+"gnawa3.jpg");

// e7 - Festival National des Arts Populaires Marrakech
            Evenement e7 = new Evenement();
            e7.setTitre("Festival National des Arts Populaires");
            e7.setDescription("Folklore marocain, troupes du monde entier et défilés à Marrakech");
            e7.setCategorie(c2);
            e7.setStatutEvenement(StatutEvenement.APPROUVE);
            e7.setCapacite(5000);
            e7.setPlacesRestants(5000);
            e7.setNbPlacesVIP(100);
            e7.setPlacesVIPRestantes(100);
            e7.setPrixVIP(200.0);
            e7.setVille("Marrakech");
            e7.setLieuSpecifique("Palais Badii + Place Jemaa el-Fna");
            e7.setDateCreation(LocalDateTime.now());
            e7.setOrganisateur(o1);
            e7.setDateDebut(LocalDateTime.of(2026, 7, 10, 20, 0));
            e7.setDateFin(LocalDateTime.of(2026, 7, 15, 23, 0));
            e7.setPrix(80.0);
            e7.setDateValidation(LocalDateTime.now());
            e7 = eventRepository.save(e7);
            eventService.storeEventImagesTeste(e7.getOrganisateur().getId(), e7.getId(), "artpop1.webp", "webp", BASE_URL_Ev+"artpop1.webp");
            eventService.storeEventImagesTeste(e7.getOrganisateur().getId(), e7.getId(), "pop2.jpeg", "jpeg", BASE_URL_Ev+"pop2.jpeg");


// e8 - Tanger Jazz Festival
            Evenement e8 = new Evenement();
            e8.setTitre("Tanjazz Festival 2026");
            e8.setDescription("Festival international de jazz et blues au détroit");
            e8.setCategorie(c2);
            e8.setStatutEvenement(StatutEvenement.APPROUVE);
            e8.setCapacite(2500);
            e8.setPlacesRestants(2500);
            e8.setNbPlacesVIP(80);
            e8.setPlacesVIPRestantes(80);
            e8.setPrixVIP(350.0);
            e8.setVille("Tanger");
            e8.setLieuSpecifique("Palais des Institutions Italiennes");
            e8.setDateCreation(LocalDateTime.now());
            e8.setOrganisateur(o1);
            e8.setDateDebut(LocalDateTime.of(2026, 9, 10, 20, 30));
            e8.setDateFin(LocalDateTime.of(2026, 9, 13, 0, 30));
            e8.setPrix(180.0);
            e8.setDateValidation(LocalDateTime.now());
            e8 = eventRepository.save(e8);
            eventService.storeEventImagesTeste(e8.getOrganisateur().getId(), e8.getId(), "tanjajazz1.webp", "webp", BASE_URL_Ev+"tanjajazz1.webp");
            eventService.storeEventImagesTeste(e8.getOrganisateur().getId(), e8.getId(), "tanjajazz2.webp", "webp", BASE_URL_Ev+"tanjajazz2.webp");

            // Expositions O2
            Evenement e14 = new Evenement();
            e14.setTitre("Salon National de l'Artisanat");
            e14.setDescription("Exposition des métiers d'art, produits du terroir et artisanat marocain");
            e14.setCategorie(c3);
            e14.setStatutEvenement(StatutEvenement.APPROUVE);
            e14.setCapacite(3000);
            e14.setPlacesRestants(3000);
            e14.setNbPlacesVIP(50);
            e14.setPlacesVIPRestantes(50);
            e14.setPrixVIP(150.0);
            e14.setVille("Fès");
            e14.setLieuSpecifique("Parc des Expositions");
            e14.setDateCreation(LocalDateTime.now());
            e14.setOrganisateur(o2);
            e14.setDateDebut(LocalDateTime.of(2026, 11, 12, 9, 0));
            e14.setDateFin(LocalDateTime.of(2026, 11, 16, 18, 0));
            e14.setPrix(20.0);
            e14.setDateValidation(LocalDateTime.now());
            e14 = eventRepository.save(e14);
            eventService.storeEventImagesTeste(e14.getOrganisateur().getId(), e14.getId(), "artisant1.webp", "webp", BASE_URL_Ev+"artisant1.webp");
            eventService.storeEventImagesTeste(e14.getOrganisateur().getId(), e14.getId(), "artisant2.jfif", "jfif", BASE_URL_Ev+"artisant2.jfif");

            // Sport O4

            Evenement e11 = new Evenement();
            e11.setTitre("Rallye du Maroc 2026 (Pro Dunes Version)");
            e11.setDescription("A high-profile professional rally covering challenging desert terrain and dunes, ideal for competitive drivers seeking adventure and off-road endurance.");
            e11.setCategorie(c1);
            e11.setStatutEvenement(StatutEvenement.APPROUVE);
            e11.setCapacite(100);
            e11.setPlacesRestants(100);
            e11.setNbPlacesVIP(0);
            e11.setPlacesVIPRestantes(0);
            e11.setPrixVIP(0);
            e11.setVille("Agadir");
            e11.setLieuSpecifique("Agadir");
            e11.setDateCreation(LocalDateTime.now());
            e11.setOrganisateur(o2);
            e11.setDateDebut(LocalDateTime.of(2026, 9, 26, 6, 0));
            e11.setDateFin(LocalDateTime.of(2026, 10, 3, 18, 0));
            e11.setPrix(1500.0);
            e11.setDateValidation(LocalDateTime.now());
            e11 = eventRepository.save(e11);
            eventService.storeEventImagesTeste(e11.getOrganisateur().getId(), e11.getId(), "rally1.png", "png", BASE_URL_Ev+"rally1.png");
            eventService.storeEventImagesTeste(e11.getOrganisateur().getId(), e11.getId(), "rally2.png", "png", BASE_URL_Ev+"rally2.png");


            Evenement e12 = new Evenement();
            e12.setTitre("Ultra Trail Atlas Toubkal");
            e12.setDescription("Course de montagne traversant les paysages du Haut Atlas");
            e12.setCategorie(c1);
            e12.setStatutEvenement(StatutEvenement.APPROUVE);
            e12.setCapacite(1500);
            e12.setPlacesRestants(1500);
            e12.setNbPlacesVIP(0);
            e12.setPlacesVIPRestantes(0);
            e12.setPrixVIP(0);
            e12.setVille("Imlil");
            e12.setLieuSpecifique("Parc National du Toubkal");
            e12.setDateCreation(LocalDateTime.now());
            e12.setOrganisateur(o2);
            e12.setDateDebut(LocalDateTime.of(2026, 10, 3, 6, 0));
            e12.setDateFin(LocalDateTime.of(2026, 10, 4, 18, 0));
            e12.setPrix(450.0);
            e12.setDateValidation(LocalDateTime.now());
            e12 = eventRepository.save(e12);
            eventService.storeEventImagesTeste(e12.getOrganisateur().getId(), e12.getId(), "tobkal1.jfif", "jfif", BASE_URL_Ev+"tobkal1.jfif");
            eventService.storeEventImagesTeste(e12.getOrganisateur().getId(), e12.getId(), "tobkal2.png", "png", BASE_URL_Ev+"tobkal2.png");


            // Conferences o3
            Evenement e15 = new Evenement();
            e15.setTitre("Africa Digital Summit");
            e15.setDescription("Marketing digital, innovation, IA et transformation numérique en Afrique");
            e15.setCategorie(c4);
            e15.setStatutEvenement(StatutEvenement.APPROUVE);
            e15.setCapacite(2500);
            e15.setPlacesRestants(2500);
            e15.setNbPlacesVIP(150);
            e15.setPlacesVIPRestantes(150);
            e15.setPrixVIP(900.0);
            e15.setVille("Casablanca");
            e15.setLieuSpecifique("Palais des Congrès");
            e15.setDateCreation(LocalDateTime.now());
            e15.setOrganisateur(o3);
            e15.setDateDebut(LocalDateTime.of(2026, 12, 3, 9, 0));
            e15.setDateFin(LocalDateTime.of(2026, 12, 4, 18, 0));
            e15.setPrix(300.0);
            e15.setDateValidation(LocalDateTime.now());
            e15 = eventRepository.save(e15);
            eventService.storeEventImagesTeste(e15.getOrganisateur().getId(), e15.getId(), "afrika2.png", "png", BASE_URL_Ev+"afrika2.png");
            eventService.storeEventImagesTeste(e15.getOrganisateur().getId(), e15.getId(), "afrika.jpg", "jpg", BASE_URL_Ev+"afrika.jpg");

            Evenement e13 = new Evenement();
            e13.setTitre("North Africa Dreamin’ 2026");
            e13.setDescription("Not able to attend Dreamforce? No problem!\n" +
                    "\n" +
                    "Thanks to North Africa Dreamin, we bring a little of the Ohana spirit to Casablanca for a whole day. This will be an opportunity for Salesforce professionals to gather and share their knowledge.\n" +
                    "\n" +
                    "You will be able to follow different sessions to train you and to stock up on knowledge: whether you are rather click or rather code, you will certainly find the theme that suits");
            e13.setCategorie(c4);
            e13.setStatutEvenement(StatutEvenement.APPROUVE);
            e13.setCapacite(250);
            e13.setPlacesRestants(250);
            e13.setNbPlacesVIP(50);
            e13.setPlacesVIPRestantes(50);
            e13.setPrixVIP(300.0);
            e13.setVille("Casablanca");
            e13.setLieuSpecifique("Barceló Anfa CasablancaCasablanca, Casablanca-Settat");
            e13.setDateCreation(LocalDateTime.now());
            e13.setOrganisateur(o3);
            e13.setDateDebut(LocalDateTime.of(2026, 10, 24, 8, 0));
            e13.setDateFin(LocalDateTime.of(2026, 10, 24, 19, 0));
            e13.setPrix(200.0);
            e13.setDateValidation(LocalDateTime.now());
            e13 = eventRepository.save(e13);
            eventService.storeEventImagesTeste(e13.getOrganisateur().getId(), e13.getId(), "dreem2.png", "png", BASE_URL_Ev+"dreem2.png");
            eventService.storeEventImagesTeste(e13.getOrganisateur().getId(), e13.getId(), "dreem.png", "png", BASE_URL_Ev+"dreem.png");

            Client cl1 = new Client();
            cl1.setUsername("client1");
            cl1.setEmail("karimalami@gmail.com");
            cl1.setPhone("0666755361");
            cl1.setMotDePasse(passwordEncoder.encode("12345"));
            cl1.setAdresse("Rabat");
            cl1.setStatutCompte(StatutCompte.ACTIF);
            cl1.setEnabled(true);
            cl1.setRoles(List.of(r2));
            cl1.setNom("ALAMI");
            cl1.setPrenom("Karim");
            cl1.setCreatedAt(LocalDateTime.now());
            cl1.setDateNaissance(LocalDate.of(2000, 6, 4));
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
            a2.setEvenement(e7);
            a2.setClient(cl1);
            avisRepository.save(a2);

            Avis a3 = new Avis();
            a3.setNote(4.5);
            a3.setComment("bon");
            a3.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a3.setEvenement(e8);
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
            a5.setEvenement(e15);
            a5.setClient(cl1);
            avisRepository.save(a5);

            Avis a6 = new Avis();
            a6.setNote(4.5);
            a6.setComment("bon");
            a6.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a6.setEvenement(e13);
            a6.setClient(cl1);
            avisRepository.save(a6);

            Avis a7 = new Avis();
            a7.setNote(4.5);
            a7.setComment("bon experience");
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
            a9.setEvenement(e14);
            a9.setClient(cl1);
            avisRepository.save(a9);

            Client cl2 = new Client();
            cl2.setUsername("client2");
            cl2.setEmail("mohammedkhalil@gmail.com");
            cl2.setPhone("0666990361");
            cl2.setMotDePasse(passwordEncoder.encode("12345"));
            cl2.setAdresse("Casablanca");
            cl2.setStatutCompte(StatutCompte.ACTIF);
            cl2.setEnabled(true);
            cl2.setRoles(List.of(r2));
            cl2.setNom("KHALIL");
            cl2.setPrenom("Mohammed");
            cl2.setCreatedAt(LocalDateTime.now());
            cl2.setDateNaissance(LocalDate.of(2000, 6, 4));
            clientRepository.save(cl2);

            Avis a10 = new Avis();
            a10.setNote(4.5);
            a10.setComment("bon experience");
            a10.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a10.setEvenement(e1);
            a10.setClient(cl2);
            avisRepository.save(a10);

            Avis a12 = new Avis();
            a12.setNote(4.5);
            a12.setComment("bon experience");
            a12.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a12.setEvenement(e7);
            a12.setClient(cl2);
            avisRepository.save(a12);

            Avis a13 = new Avis();
            a13.setNote(4.5);
            a13.setComment("bon experience");
            a13.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a13.setEvenement(e8);
            a13.setClient(cl2);
            avisRepository.save(a13);

            Avis a14 = new Avis();
            a14.setNote(4.5);
            a14.setComment("bon experience");
            a14.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a14.setEvenement(e4);
            a14.setClient(cl2);
            avisRepository.save(a14);

            Avis a15 = new Avis();
            a15.setNote(4.5);
            a15.setComment("bon experience");
            a15.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a15.setEvenement(e15);
            a15.setClient(cl2);
            avisRepository.save(a15);

            Avis a16 = new Avis();
            a16.setNote(4.5);
            a16.setComment("bon experience");
            a16.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a16.setEvenement(e13);
            a16.setClient(cl2);
            avisRepository.save(a16);

            Avis a17 = new Avis();
            a17.setNote(4.5);
            a17.setComment("bon experience");
            a17.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a17.setEvenement(e7);
            a17.setClient(cl2);
            avisRepository.save(a17);

            Avis a18 = new Avis();
            a18.setNote(4.5);
            a18.setComment("bon experience");
            a18.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a18.setEvenement(e8);
            a18.setClient(cl2);
            avisRepository.save(a18);

            Avis a19 = new Avis();
            a19.setNote(4.5);
            a19.setComment("bon experience");
            a19.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a19.setEvenement(e14);
            a19.setClient(cl2);
            avisRepository.save(a19);

            Avis a20 = new Avis();
            a20.setNote(4.5);
            a20.setComment("bon");
            a20.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a20.setEvenement(e12);
            a20.setClient(cl2);
            avisRepository.save(a20);

            Avis a21 = new Avis();
            a21.setNote(4.5);
            a21.setComment("bon experience");
            a21.setDateAvis(LocalDateTime.of(2026, 6, 4,22,33,0));
            a21.setEvenement(e12);
            a21.setClient(cl1);
            avisRepository.save(a21);

        };
    }
}