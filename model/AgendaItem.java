package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AgendaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana; // Ex: "Sábado", "Domingo"
    private String data;      // Ex: "01/03"
    private String horario;   // Ex: "14:00"
    private String titulo;    // Ex: "Missa de Abertura"
    private String descricao; // Ex: "Pregador: Pe. João"
    
    private Integer ordem;    // Para ordenar a sequência (1, 2, 3...)

	public Long getId() {
		return id;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public String getData() {
		return data;
	}

	public String getHorario() {
		return horario;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
    
    
}