package br.com.higitech.rccAcopiara.repository;

import br.com.higitech.rccAcopiara.model.AgendaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgendaRepository extends JpaRepository<AgendaItem, Long> {
    // Busca ordenando pela ordem definida manualmente e depois pelo horário
    List<AgendaItem> findAllByOrderByOrdemAscHorarioAsc();
}