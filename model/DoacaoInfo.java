package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.*;

@Entity
public class DoacaoInfo {
    @Id
    private Long id = 1L; // Usaremos sempre o ID 1 (Singleton)

    private String chavePix;
    
    @Column(columnDefinition = "TEXT")
    private String textoDoacao;
    
    private int contadorCopias = 0; // Contador de cliques/cópias

    // --- GETTERS E SETTERS MANUAIS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getChavePix() { return chavePix; }
    public void setChavePix(String chavePix) { this.chavePix = chavePix; }

    public String getTextoDoacao() { return textoDoacao; }
    public void setTextoDoacao(String textoDoacao) { this.textoDoacao = textoDoacao; }

    public int getContadorCopias() { return contadorCopias; }
    public void setContadorCopias(int contadorCopias) { this.contadorCopias = contadorCopias; }
}