package br.com.higitech.rccAcopiara.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.higitech.rccAcopiara.model.Patrocinador;
import br.com.higitech.rccAcopiara.repository.PatrocinadorRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/patrocinadores")
@CrossOrigin("*")
public class PatrocinadorController {

    @Autowired private PatrocinadorRepository repository;
    @Autowired private CloudinaryService cloudinaryService;

    @GetMapping
    public List<Patrocinador> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Patrocinador> salvar(
            @RequestParam("nome") String nome,
            @RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(file);
            Patrocinador p = new Patrocinador();
            p.setNome(nome);
            p.setMediaUrl((String) uploadResult.get("url"));
            p.setPublicId((String) uploadResult.get("public_id"));
            
            String mimeType = file.getContentType();
            p.setTipoMedia((mimeType != null && mimeType.startsWith("video")) ? "VIDEO" : "IMAGEM");

            return ResponseEntity.ok(repository.save(p));
        } catch (IOException e) { return ResponseEntity.badRequest().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Patrocinador p = repository.findById(id).orElse(null);
        if (p != null) {
            try {
                if(p.getPublicId() != null) cloudinaryService.deleteFile(p.getPublicId());
                repository.delete(p);
                return ResponseEntity.ok().build();
            } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
        }
        return ResponseEntity.notFound().build();
    }
}