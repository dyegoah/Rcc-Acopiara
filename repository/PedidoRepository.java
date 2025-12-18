package br.com.higitech.rccAcopiara.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higitech.rccAcopiara.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Ordenar pedidos do mais recente para o mais antigo
    List<Pedido> findAllByOrderByDataPedidoDesc();
}