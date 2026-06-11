package com.ouadia.rovista1.services;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.repositories.*;
import com.ouadia.rovista1.services.interfaces.IRovisitaAssistantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RovistaAssistantService implements IRovisitaAssistantService {
    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final BilletRepository billetRepository;


    public String getApplicationContext() {
       try {
           List<Evenement> events = eventRepository.findAll();

           StringBuilder context = new StringBuilder();

           context.append("Informations Rovista :\n\n");

           context.append("Nombre d'événements : ")
                   .append(eventRepository.count())
                   .append("\n");

           context.append("Nombre de réservations : ")
                   .append(reservationRepository.count())
                   .append("\n");

           context.append("Nombre de billets : ")
                   .append(billetRepository.count())
                   .append("\n\n");

           context.append("Liste des événements :\n");

           for (Evenement e : events) {

               context.append("Titre : ")
                       .append(e.getTitre())
                       .append("\n");

               context.append("Description : ")
                       .append(e.getDescription())
                       .append("\n");

               context.append("Ville : ")
                       .append(e.getVille())
                       .append("\n");

               context.append("Lieu : ")
                       .append(e.getLieuSpecifique())
                       .append("\n");

               context.append("Date début : ")
                       .append(e.getDateDebut())
                       .append("\n");

               context.append("Date fin : ")
                       .append(e.getDateFin())
                       .append("\n");

               context.append("Prix : ")
                       .append(e.getPrix())
                       .append("\n");

               context.append("Places restantes : ")
                       .append(e.getPlacesRestants())
                       .append("\n\n");
           }

           return context.toString();
       }
  catch (Exception e) {
               // إذا فشل — ارجع context ثابت
               return """
                Rovista est une plateforme de réservation d'événements en ligne.
                Elle permet aux utilisateurs de réserver des billets,
                gérer leurs favoris et profiter de promotions exclusives.
                """;
           }
    }
    public String getClientProfile(Client client) {

        return """
                Informations du client :

                Nom : %s
                Prénom : %s
                Username : %s
                Email : %s
                Téléphone : %s
                Adresse : %s
                Date de naissance : %s
                """
                .formatted(
                        client.getNom(),
                        client.getPrenom(),
                        client.getUsername(),
                        client.getEmail(),
                        client.getPhone(),
                        client.getAdresse(),
                        client.getDateNaissance()
                );
    }

    public String getClientReservations(Client client) throws ReservationNotFoundException {

        if (client.getReservations() == null
                || client.getReservations().isEmpty()) {
            throw new ReservationNotFoundException("Vous n'avez aucune réservation.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Vos réservations :\n\n");

        client.getReservations().forEach(r -> {

            sb.append("Réservation N° ")
                    .append(r.getId())
                    .append("\n");

            sb.append("Date : ")
                    .append(r.getDateReservation())
                    .append("\n");

            sb.append("Places : ")
                    .append(r.getNombrePlaces())
                    .append("\n");

            sb.append("Montant : ")
                    .append(r.getMontant())
                    .append("\n\n");
        });

        return sb.toString();
    }

    public String getClientBillets(Client client) throws BilletNotFoundException {

        StringBuilder sb = new StringBuilder();

        client.getReservations().forEach(reservation -> {

            if (reservation.getBillets() != null) {

                reservation.getBillets().forEach(billet -> {

                    sb.append("Billet N° ")
                            .append(billet.getId())
                            .append("\n");

                    sb.append("Code : ")
                            .append(billet.getCode())
                            .append("\n");

                    sb.append("Type : ")
                            .append(billet.getType())
                            .append("\n\n");
                });
            }
        });

        if (sb.isEmpty()) {
            throw new BilletNotFoundException("Vous n'avez aucun billet. ");
        }

        return sb.toString();
    }

    public String getClientFavoris(Client client) throws FavorieNotFoundException {

        if (client.getFavories() == null
                || client.getFavories().isEmpty()) {

            throw new FavorieNotFoundException("Vous n'avez aucun favori.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Vos favoris :\n\n");

        client.getFavories().forEach(f -> {

            sb.append("Liste : ")
                    .append(f.getDescription())
                    .append("\n");

            if (f.getEvenements() != null) {

                f.getEvenements().forEach(event -> {

                    sb.append(" - ")
                            .append(event.getTitre())
                            .append("\n");
                });
            }

            sb.append("\n");
        });

        return sb.toString();
    }

    public String getClientPromotions(Client client) throws PromotionNotFoundException {

        if (client.getPromotions() == null
                || client.getPromotions().isEmpty()) {

            throw new PromotionNotFoundException( "Vous n'avez aucune promotion.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Vos promotions :\n\n");

        client.getPromotions().forEach(p -> {

            sb.append("Promotion : ")
                    .append(p.getTitre())
                    .append("\n");

            sb.append("Type : ")
                    .append(p.getType())
                    .append("\n\n");
        });

        return sb.toString();
    }

    public String getClientContext(Client client) {

        return """
                Client :
                Nom : %s
                Prénom : %s
                Email : %s

                Nombre de réservations : %d
                Nombre de favoris : %d
                Nombre de promotions : %d
                """
                .formatted(
                        client.getNom(),
                        client.getPrenom(),
                        client.getEmail(),
                        client.getReservations().size(),
                        client.getFavories().size(),
                        client.getPromotions().size()
                );
    }

}

