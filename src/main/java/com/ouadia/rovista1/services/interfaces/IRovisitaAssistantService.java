package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.exceptions.BilletNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import com.ouadia.rovista1.exceptions.PromotionNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;

public interface IRovisitaAssistantService {
    public String getApplicationContext();
    public String getClientProfile(Client client);
    public String getClientReservations(Client client) throws ReservationNotFoundException;
    public String getClientBillets(Client client) throws BilletNotFoundException;
    public String getClientFavoris(Client client) throws FavorieNotFoundException;
    public String getClientPromotions(Client client) throws PromotionNotFoundException;
    public String getClientContext(Client client);
}
