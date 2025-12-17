package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco; // Ideal para dinheiro
    private String imagemUrl; // Cloudinary
    private String publicId;
    
    // Status para saber se ainda tem estoque ou não (opcional, vamos deixar simples)
    private boolean disponivel = true;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public String getPublicId() {
		return publicId;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	} 
        
}