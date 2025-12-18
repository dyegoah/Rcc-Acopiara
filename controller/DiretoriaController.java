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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.higitech.rccAcopiara.model.DiretoriaMember;
import br.com.higitech.rccAcopiara.repository.DiretoriaRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/diretoria")
@CrossOrigin("*")
public class DiretoriaController {

    @Autowired private DiretoriaRepository repository;
    @Autowired private CloudinaryService cloudinaryService;

    @GetMapping
    public List<DiretoriaMember> listar() {
        return repository.findAllByOrderByOrdemAscNomeAsc();
    }

    @PostMapping
    public ResponseEntity<DiretoriaMember> salvar(
            @RequestParam("nome") String nome,
            @RequestParam("cargo") String cargo,
            @RequestParam("periodo") String periodo,
            @RequestParam("ordem") Integer ordem,
            @RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(file);
            DiretoriaMember m = new DiretoriaMember();
            m.setNome(nome);
            m.setCargo(cargo);
            m.setPeriodo(periodo);
            m.setOrdem(ordem);
            m.setAtivo(true); // Padrão: entra como ativo
            m.setFotoUrl((String) uploadResult.get("url"));
            m.setPublicId((String) uploadResult.get("public_id"));
            return ResponseEntity.ok(repository.save(m));
        } catch (IOException e) { return ResponseEntity.badRequest().build(); }
    }

    // Move para o histórico (Arquivar)
    @PutMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivar(@PathVariable Long id) {
        DiretoriaMember m = repository.findById(id).orElse(null);
        if (m != null) {
            m.setAtivo(false); // Vira histórico
            repository.save(m);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        DiretoriaMember m = repository.findById(id).orElse(null);
        if (m != null) {
            try {
                if(m.getPublicId() != null) cloudinaryService.deleteFile(m.getPublicId());
                repository.delete(m);
                return ResponseEntity.ok().build();
            } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
        }
        return ResponseEntity.notFound().build();
    }
}