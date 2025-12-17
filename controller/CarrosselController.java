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

import br.com.higitech.rccAcopiara.model.CarrosselItem;
import br.com.higitech.rccAcopiara.repository.CarrosselRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/carrossel")
@CrossOrigin("*") // Permite acesso do frontend
public class CarrosselController {

    @Autowired
    private CarrosselRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // LISTAR TODOS (Para o Index e Admin)
    @GetMapping
    public List<CarrosselItem> listar() {
        return repository.findAllByOrderByIdAsc();
    }

    // ADICIONAR NOVO (Para o Admin)
    @PostMapping
    public ResponseEntity<CarrosselItem> adicionar(
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("file") MultipartFile file) {
        try {
            // 1. Upload pro Cloudinary
            Map uploadResult = cloudinaryService.uploadFile(file);
            String url = (String) uploadResult.get("url");
            String publicId = (String) uploadResult.get("public_id");

            // 2. Salva no Banco
            CarrosselItem item = new CarrosselItem();
            item.setTitulo(titulo);
            item.setDescricao(descricao);
            item.setImagemUrl(url);
            item.setPublicId(publicId);
            
            return ResponseEntity.ok(repository.save(item));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETAR (Para o Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        CarrosselItem item = repository.findById(id).orElse(null);
        if (item != null) {
            try {
                // Deleta do Cloudinary para não gastar espaço à toa
                if(item.getPublicId() != null) {
                    cloudinaryService.deleteFile(item.getPublicId());
                }
                repository.delete(item);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}