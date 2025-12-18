package br.com.higitech.rccAcopiara.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.higitech.rccAcopiara.model.AdminUser;
import br.com.higitech.rccAcopiara.repository.AdminRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AdminRepository repository;

    // Cria o usuário padrão ao iniciar o sistema
    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            AdminUser admin = new AdminUser();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            repository.save(admin);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String user = data.get("username");
        String pass = data.get("password");

        return repository.findById(user)
                .filter(u -> u.getPassword().equals(pass))
                .map(u -> ResponseEntity.ok().body(Map.of("status", "ok")))
                .orElse(ResponseEntity.status(401).body(Map.of("status", "error")));
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody Map<String, String> data) {
        String user = data.get("username"); // Vamos assumir 'admin' ou pegar do login
        String oldPass = data.get("oldPass");
        String newPass = data.get("newPass");

        AdminUser admin = repository.findById(user).orElse(null);
        
        if (admin != null && admin.getPassword().equals(oldPass)) {
            admin.setPassword(newPass);
            repository.save(admin);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}