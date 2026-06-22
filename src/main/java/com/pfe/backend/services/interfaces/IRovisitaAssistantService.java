package com.pfe.backend.services.interfaces;

import com.pfe.backend.entities.Client;
import com.pfe.backend.exceptions.BilletNotFoundException;
import com.pfe.backend.exceptions.FavorieNotFoundException;
import com.pfe.backend.exceptions.PromotionNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;

public interface IRovisitaAssistantService {
    public String getApplicationContext();
    public String getClientProfile(Client client);
    public String getClientReservations(Client client) throws ReservationNotFoundException;
    public String getClientBillets(Client client) throws BilletNotFoundException;
    public String getClientFavoris(Client client) throws FavorieNotFoundException;
    public String getClientPromotions(Client client) throws PromotionNotFoundException;
    public String getClientContext(Client client);
}
