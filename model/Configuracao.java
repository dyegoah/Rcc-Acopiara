package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Configuracao {
    @Id
    private Long id; // Vamos usar sempre o ID 1
    private boolean inscricoesAbertas;
	public Long getId() {
		return id;
	}
	public boolean isInscricoesAbertas() {
		return inscricoesAbertas;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setInscricoesAbertas(boolean inscricoesAbertas) {
		this.inscricoesAbertas = inscricoesAbertas;
	}
    
    
}