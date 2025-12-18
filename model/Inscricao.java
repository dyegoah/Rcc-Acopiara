package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String nomeMae;
    private String cidade;
    private String paroquia; // Optativo
    private String telefone;
    private String email;    // Optativo
    private String fotoUrl;  // Optativo (Cloudinary)
	public Long getId() {
		return id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public String getCidade() {
		return cidade;
	}
	public String getParoquia() {
		return paroquia;
	}
	public String getTelefone() {
		return telefone;
	}
	public String getEmail() {
		return email;
	}
	public String getFotoUrl() {
		return fotoUrl;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public void setParoquia(String paroquia) {
		this.paroquia = paroquia;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}
    
    
}