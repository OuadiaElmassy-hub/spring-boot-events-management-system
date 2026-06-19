package com.pfe.backend.services.implementations;

import com.pfe.backend.dtos.favorie.FavorieRequestDto;
import com.pfe.backend.dtos.favorie.FavorieResponseDto;
import com.pfe.backend.dtos.favorie.HistoriqueFavorieDto;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.Favorie;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.exceptions.ClientNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.exceptions.FavorieNotFoundException;
import com.pfe.backend.mappers.FavorieMapper;
import com.pfe.backend.repositories.ClientRepository;
import com.pfe.backend.repositories.EventRepository;
import com.pfe.backend.repositories.FavorieRepository;
import com.pfe.backend.services.interfaces.IFavorieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class FavorieServiceImpl implements IFavorieService {

    private FavorieRepository favorieRepository;
    private FavorieMapper favorieMapper;
    private ClientRepository clientRepository;
    private EventRepository eventRepository;
    private FavorieMapper mapper;


    @Override
    public FavorieResponseDto addFavorie(Long idClient, Long idEvenement) throws FavorieNotFoundException, ClientNotFoundException, EventNotFoundException, BusinessException {

        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        Evenement ev = eventRepository.findById(idEvenement).
                orElseThrow(() -> new EventNotFoundException("Aucun événement introuvable pour les IDs fournis"));

        if (favorieRepository.existsByClientIdAndEvenementId(idClient, idEvenement)) {
            throw new BusinessException("Cet favorie est déjà existe");
        }

        return favorieMapper.mappingFavorieToFavorieDtoResponse(favorieRepository.save(Favorie.builder()
                .client(client)
                .evenement(ev)
                .build())
        );
    }

    @Override
    public FavorieResponseDto addEvenementAuFavorie(Long idFavorie, Long idEvenement, String newDescription)
            throws FavorieNotFoundException, EventNotFoundException {

        Favorie favorie = favorieRepository.findById(idFavorie)
                .orElseThrow(() -> new FavorieNotFoundException("Favori introuvable"));

        Evenement event = eventRepository.findById(idEvenement)
                .orElseThrow(() -> new EventNotFoundException("Événement introuvable"));

        if (favorie.getEvenement().getId().equals(event.getId())) {
            throw new RuntimeException("Cet événement est déjà présent dans cette liste de favoris");
        }

        if (newDescription != null && !newDescription.isBlank()) {
            favorie.setDescription(newDescription);
        }

        return favorieMapper.mappingFavorieToFavorieDtoResponse(
                favorieRepository.save(favorie)
        );
    }


    @Override
    public FavorieResponseDto editFavorie(FavorieRequestDto favorieDto, Long id) {
        Favorie favorie = mapper.mappingFavorieDtoRequestToFavorie(favorieDto);
        if (favorie == null) return null;
        else {
            Favorie favorie1 = favorieRepository.findById(id).get();
            if (favorie1 == null) {
                return null;
            }
            favorie1.setDescription(favorie.getDescription());
            favorie1.setDateCreation(favorie.getDateCreation());
            favorie1.setClient(favorie.getClient());
            favorie1.setEvenement(favorie.getEvenement());
            return favorieMapper.mappingFavorieToFavorieDtoResponse(favorieRepository.save(favorie1));

        }


    }

    @Override
    public FavorieResponseDto editFavorieMap(Long id, Map<String,Object> map) {
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
            favorie1.setEvenement((Evenement)map.get("evenements"));
        }
         return favorieMapper.mappingFavorieToFavorieDtoResponse(favorieRepository.save(favorie1));
    }

    @Override
    public FavorieResponseDto getFavorieByIdClient(Long idClient) throws ClientNotFoundException {

        Client client = clientRepository.findById(idClient).orElseThrow(() -> new ClientNotFoundException("client not found"));
        return favorieMapper.mappingFavorieToFavorieDtoResponse(favorieRepository.findByClient(client));
    }

    @Override
    public List<FavorieResponseDto> getAllFavories() {
        return favorieRepository.findAll().stream().map(favorie -> favorieMapper.mappingFavorieToFavorieDtoResponse(favorie)).toList();
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

    //_____________________________________________________

    @Override
    public Page<HistoriqueFavorieDto> getFavories(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return favorieRepository
                .findByClientIdOrderByDateCreationDesc(clientId, pageable)
                .map(f -> HistoriqueFavorieDto.builder()
                                .id(f.getEvenement().getId())
                                .titre(f.getEvenement().getTitre())
                                .date(f.getEvenement().getDateDebut().toString())
                                .lieu(f.getEvenement().getVille())
                                .prix(f.getEvenement().getPrix())
                        .categorie(f.getEvenement().getCategorie().getNom())
                        .build());
    }


    @Override
    public void removeFavorie(Long userId, Long eventId) {
        Favorie fav = favorieRepository.findByClientIdAndEvenementId(userId, eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Favori introuvable"));
        favorieRepository.delete(fav);
    }
}
