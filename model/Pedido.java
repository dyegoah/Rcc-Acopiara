package br.com.higitech.rccAcopiara.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados do Comprador
    private String nomeComprador;
    private String telefone; // WhatsApp
    private String formaPagamento; // PIX, Dinheiro, Cartão

    // Dados do Item
    private String nomeProduto;
    private String valor; // Vamos salvar como String formatada para facilitar
    
    private LocalDateTime dataPedido = LocalDateTime.now();
    private String status; // "Pendente", "Entregue"
	public Long getId() {
		return id;
	}
	public String getNomeComprador() {
		return nomeComprador;
	}
	public String getTelefone() {
		return telefone;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public String getValor() {
		return valor;
	}
	public LocalDateTime getDataPedido() {
		return dataPedido;
	}
	public String getStatus() {
		return status;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}