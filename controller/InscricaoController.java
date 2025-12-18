package br.com.higitech.rccAcopiara.controller;

import java.time.LocalDate;
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

import br.com.higitech.rccAcopiara.model.Configuracao;
import br.com.higitech.rccAcopiara.model.Inscricao;
import br.com.higitech.rccAcopiara.repository.ConfiguracaoRepository;
import br.com.higitech.rccAcopiara.repository.InscricaoRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/inscricoes")
@CrossOrigin("*")
public class InscricaoController {

    @Autowired private InscricaoRepository repository;
    @Autowired private ConfiguracaoRepository configRepo;
    @Autowired private CloudinaryService cloudinaryService;

    // --- GERENCIAMENTO DE STATUS (ABRIR/FECHAR) ---
    
    @GetMapping("/status")
    public boolean getStatus() {
        return getConfig().isInscricoesAbertas();
    }

    @PostMapping("/status")
    public boolean toggleStatus() {
        Configuracao config = getConfig();
        config.setInscricoesAbertas(!config.isInscricoesAbertas());
        configRepo.save(config);
        return config.isInscricoesAbertas();
    }

    private Configuracao getConfig() {
        return configRepo.findById(1L).orElseGet(() -> {
            Configuracao c = new Configuracao();
            c.setId(1L);
            c.setInscricoesAbertas(true); // Padrão: Abertas
            return configRepo.save(c);
        });
    }

    // --- CRUD DE INSCRIÇÕES ---

    @GetMapping
    public List<Inscricao> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestParam("nome") String nome,
            @RequestParam("nascimento") String nascimento,
            @RequestParam("mae") String mae,
            @RequestParam("cidade") String cidade,
            @RequestParam("telefone") String telefone,
            @RequestParam(value = "paroquia", required = false) String paroquia,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        if (!getStatus()) {
            return ResponseEntity.badRequest().body("Inscrições Encerradas.");
        }

        try {
            Inscricao inscricao = new Inscricao();
            inscricao.setNomeCompleto(nome);
            inscricao.setDataNascimento(LocalDate.parse(nascimento));
            inscricao.setNomeMae(mae);
            inscricao.setCidade(cidade);
            inscricao.setTelefone(telefone);
            inscricao.setParoquia(paroquia);
            inscricao.setEmail(email);

            // Upload de Foto (Se enviada)
            if (foto != null && !foto.isEmpty()) {
                Map uploadResult = cloudinaryService.uploadFile(foto);
                inscricao.setFotoUrl((String) uploadResult.get("url"));
            }

            return ResponseEntity.ok(repository.save(inscricao));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}