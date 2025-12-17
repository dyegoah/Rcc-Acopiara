package br.com.higitech.rccAcopiara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higitech.rccAcopiara.model.DiretoriaMember;

public interface DiretoriaRepository extends JpaRepository<DiretoriaMember, Long> {
    // Busca ordenando pela hierarquia (ordem) e depois pelo nome
    List<DiretoriaMember> findAllByOrderByOrdemAscNomeAsc();
}