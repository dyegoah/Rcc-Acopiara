package br.com.higitech.rccAcopiara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higitech.rccAcopiara.model.CarrosselItem;

public interface CarrosselRepository extends JpaRepository<CarrosselItem, Long> {
    // Busca ordenado por ID (ou poderia ser por campo 'ordem')
    List<CarrosselItem> findAllByOrderByIdAsc();
}