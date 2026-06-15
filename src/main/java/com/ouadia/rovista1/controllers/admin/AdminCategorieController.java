package com.ouadia.rovista1.controllers.admin;

import com.ouadia.rovista1.dtos.admin.AdminCategorieDTO;
import com.ouadia.rovista1.dtos.admin.AdminEventDTO;
import com.ouadia.rovista1.dtos.admin.PatchEventStatusRequest;
import com.ouadia.rovista1.dtos.admin.PendingCountDTO;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.CategorieNotFoundException;
import com.ouadia.rovista1.services.admin.AdminCategorieService;
import com.ouadia.rovista1.services.admin.AdminEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategorieController {

    private final AdminCategorieService categorieService;

    //   GET    /admin/categories?search=&page=&size=10
    @GetMapping
    public ResponseEntity<Page<AdminCategorieDTO>> getEvents(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom,asc") String sort) {

        return ResponseEntity.ok(
                categorieService.searchCategories(search, page, size, sort));
    }

    // ═══════════════════════════════════════════════════════════════
    // CATÉGORIES
    // ═══════════════════════════════════════════════════════════════

    /** POST /admin/categories  (multipart/form-data) */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<AdminCategorieDTO> createCategorie(
            @RequestParam String  nom,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "#6366f1") String couleur,
            @RequestParam(required = false) MultipartFile icone) throws Exception {

        AdminCategorieDTO dto = categorieService.createCategorie(nom, description, couleur, icone);
        if (dto == null)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /** PUT /admin/categories/{id}  (multipart/form-data) */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<AdminCategorieDTO> updateCategorie(
            @PathVariable Long id,
            @RequestParam String  nom,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String couleur,
            @RequestParam(required = false) MultipartFile icone) throws Exception {

        return ResponseEntity.ok(categorieService.updateCategorie(id, nom, description, couleur, icone));
    }

    /** PATCH /admin/categories/{id}/toggle */
    @PatchMapping("/{id}/toggle")
    @Transactional
    public ResponseEntity<Void> toggleCategorie(@PathVariable Long id) {

        try {
            categorieService.toggleCategorie(id);
            return ResponseEntity.ok().build();
        } catch (CategorieNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /admin/categories/{id} */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        try {
            categorieService.deleteCategorie(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build();
        } catch (CategorieNotFoundException | BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).<Void>build();
        }
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategorieById(@PathVariable Long id) throws CategorieNotFoundException, BusinessException {
//        categorieService.deleteCategorie(id);
//        return ResponseEntity.noContent().build();
//    }
}
