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

import br.com.higitech.rccAcopiara.model.Noticia;
import br.com.higitech.rccAcopiara.repository.NoticiaRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin("*")
public class NoticiaController {

    @Autowired private NoticiaRepository repository;
    @Autowired private CloudinaryService cloudinaryService;

    @GetMapping
    public List<Noticia> listar() {
        return repository.findAllByOrderByDataPublicacaoDesc();
    }

    @PostMapping
    public ResponseEntity<Noticia> salvar(
            @RequestParam("titulo") String titulo,
            @RequestParam("conteudo") String conteudo,
            @RequestParam("file") MultipartFile file) {
        try {
            // Upload no Cloudinary (aceita vídeo e imagem)
            Map uploadResult = cloudinaryService.uploadFile(file);
            
            Noticia n = new Noticia();
            n.setTitulo(titulo);
            n.setConteudo(conteudo);
            n.setMediaUrl((String) uploadResult.get("url"));
            n.setPublicId((String) uploadResult.get("public_id"));
            
            // Define o tipo baseado no arquivo enviado
            String mimeType = file.getContentType();
            if (mimeType != null && mimeType.startsWith("video")) {
                n.setTipoMedia("VIDEO");
            } else {
                n.setTipoMedia("IMAGEM");
            }

            return ResponseEntity.ok(repository.save(n));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Noticia n = repository.findById(id).orElse(null);
        if (n != null) {
            try {
                if (n.getPublicId() != null) cloudinaryService.deleteFile(n.getPublicId());
                repository.delete(n);
                return ResponseEntity.ok().build();
            } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
        }
        return ResponseEntity.notFound().build();
    }
}