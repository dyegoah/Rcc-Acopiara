package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patrocinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Nome da empresa/parceiro
    private String mediaUrl; // Imagem ou Vídeo (Cloudinary)
    private String publicId;
    private String tipoMedia; // "IMAGEM" ou "VIDEO"
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    public String getPublicId() { return publicId; }
    public void setPublicId(String publicId) { this.publicId = publicId; }
    public String getTipoMedia() { return tipoMedia; }
    public void setTipoMedia(String tipoMedia) { this.tipoMedia = tipoMedia; }
}