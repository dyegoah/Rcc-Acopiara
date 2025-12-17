package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // <--- A CORREÇÃO ESTÁ AQUI
import lombok.Data;

@Entity
@Data // O Lombok já gera Getters, Setters, toString, etc.
public class CarrosselItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String imagemUrl; // URL do Cloudinary
    private String publicId;  // ID no Cloudinary
    
    private Integer ordem;

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public String getPublicId() {
		return publicId;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
        
}