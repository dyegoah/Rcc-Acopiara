package br.com.higitech.rccAcopiara.controller;

import br.com.higitech.rccAcopiara.model.AgendaItem;
import br.com.higitech.rccAcopiara.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agenda")
@CrossOrigin("*")
public class AgendaController {

    @Autowired
    private AgendaRepository repository;

    @GetMapping
    public List<AgendaItem> listar() {
        return repository.findAllByOrderByOrdemAscHorarioAsc();
    }

    @PostMapping
    public AgendaItem salvar(@RequestBody AgendaItem item) {
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}