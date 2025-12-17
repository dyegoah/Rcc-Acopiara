package br.com.higitech.rccAcopiara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.higitech.rccAcopiara.model.DoacaoInfo;
import br.com.higitech.rccAcopiara.repository.DoacaoInfoRepository;

@RestController
@RequestMapping("/api/doacao")
@CrossOrigin("*")
public class DoacaoController {

    @Autowired
    private DoacaoInfoRepository repository;

    // Pega as informações atuais (se não existir, cria uma padrão)
    @GetMapping
    public DoacaoInfo getInfo() {
        return repository.findById(1L).orElseGet(() -> {
            DoacaoInfo info = new DoacaoInfo();
            info.setId(1L);
            info.setChavePix("seu@email.com");
            info.setTextoDoacao("Ajude nossa obra a continuar evangelizando!");
            info.setContadorCopias(0);
            return repository.save(info);
        });
    }

    // Atualiza Texto e Chave (Admin)
    @PostMapping
    public DoacaoInfo salvar(@RequestBody DoacaoInfo novaInfo) {
        DoacaoInfo info = getInfo(); // Carrega o existente
        info.setChavePix(novaInfo.getChavePix());
        info.setTextoDoacao(novaInfo.getTextoDoacao());
        return repository.save(info);
    }

    // Incrementa o contador de cópias (Quando o usuário clica no site)
    @PostMapping("/contar")
    public ResponseEntity<Void> contarCopia() {
        DoacaoInfo info = getInfo();
        info.setContadorCopias(info.getContadorCopias() + 1);
        repository.save(info);
        return ResponseEntity.ok().build();
    }
}