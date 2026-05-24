package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.CategorieMapper;
import com.ouadia.rovista1.Mapper.FavorieMapper;
import com.ouadia.rovista1.dtos.FavorieDto;
import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Favorie;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import com.ouadia.rovista1.repositories.ClientRepository;
import com.ouadia.rovista1.repositories.EventRepository;
import com.ouadia.rovista1.repositories.FavorieRepository;
import com.ouadia.rovista1.services.interfaces.IFavorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class FavorieServiceImpl implements IFavorieService {

    private FavorieRepository favorieRepository;
    private ClientRepository clientRepository;
    private EventRepository eventRepository;


    @Override
    public FavorieDto addFavorie(FavorieDto favorieDto, Long IdClient, Long IdEvent) throws FavorieNotFoundException, EventNotFoundException, ClientNotFoundException {
        Favorie favorie = FavorieMapper.mapToFavorie(favorieDto);
        Client client = clientRepository.findById(IdClient)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        Evenement ev = eventRepository.findById(IdEvent).
                orElseThrow(() -> new EventNotFoundException("Aucun événement introuvable pour les IDs fournis"));

        if (favorieRepository.existsByClientIdAndEvenementId(IdClient, IdEvent)) {
            throw new FavorieNotFoundException("Cet événement est déjà dans vos favoris");
        }

        Favorie favorie1 = Favorie.builder()
                .id(favorie.getId())
                .description(favorie.getDescription())
                .dateCreation(LocalDateTime.now())
                .client(client)
                .evenements(List.of(ev))
                .build();

        return FavorieMapper.mapToFavorieDto(favorieRepository.save(favorie1));
    }

    @Override
    public FavorieDto addEvenementAuFavorie(FavorieDto favorieDto, Long idEvent) throws FavorieNotFoundException, EventNotFoundException {


        Favorie favorie1=favorieRepository.findById(favorieDto.getId())
                    .orElseThrow(() -> new FavorieNotFoundException("Favori introuvable"));
            Evenement newEvent = eventRepository.findById(idEvent)
                    .orElseThrow(() -> new EventNotFoundException("Événement introuvable"));
            if (favorie1.getEvenements().contains(newEvent)) {
                throw new FavorieNotFoundException("Cet événement est déjà présent dans cette liste de favoris");
            }
            favorie1.getEvenements().add(newEvent);
            if (favorieDto.getDescription() != null) {
                favorie1.setDescription(favorie1.getDescription());
            }

            return FavorieMapper.mapToFavorieDto(favorieRepository.save(favorie1));
        }


    @Override
    public FavorieDto editFavorie(FavorieDto favorieDto, Long id) {
        Favorie favorie = FavorieMapper.mapToFavorie(favorieDto);
        if (favorie == null) return null;
        else {
            Favorie favorie1 = favorieRepository.findById(id).get();
            if (favorie1 == null) {
                return null;
            }
            favorie1.setDescription(favorie.getDescription());
            favorie1.setDateCreation(favorie.getDateCreation());
            favorie1.setClient(favorie.getClient());
            favorie1.setEvenements(favorie.getEvenements());
            return FavorieMapper.mapToFavorieDto(favorieRepository.save(favorie1));

        }


    }

    @Override
    public FavorieDto editFavorieMap(Long id, Map<String,Object> map) {
        if (map==null){return null;}
        Favorie favorie1 = favorieRepository.findById(id).get();
        if (favorie1 == null) {
            return null;
        }
        if (map.containsKey("description")) {
            favorie1.setDescription((String) map.get("description"));
        }
        if (map.containsKey("dateCreation")) {
            favorie1.setDateCreation((LocalDateTime) map.get("dateCreation"));
        }
        if (map.containsKey("client")) {
            favorie1.setClient((Client) map.get("client"));
        }
        if (map.containsKey("evenements")) {
            favorie1.setEvenements((List<Evenement>)map.get("evenements"));
        }
         return FavorieMapper.mapToFavorieDto(favorieRepository.save(favorie1));
    }

    @Override
    public FavorieDto getFavorieByIdClient(Long idClient) throws ClientNotFoundException {

        Client client = clientRepository.findById(idClient).orElseThrow(() -> new ClientNotFoundException("client not found"));
        return FavorieMapper.mapToFavorieDto(favorieRepository.findByClient(client));
    }

    @Override
    public List<FavorieDto> getAllFavories() {
        return favorieRepository.findAll().stream().map(favorie -> FavorieMapper.mapToFavorieDto(favorie)).toList();
    }

    @Override
    public void deleteFavorieById(Long id) {
        favorieRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteFavorieById(id);
        }
    }
}