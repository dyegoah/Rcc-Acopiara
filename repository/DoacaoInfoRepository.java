package br.com.higitech.rccAcopiara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higitech.rccAcopiara.model.DoacaoInfo;

public interface DoacaoInfoRepository extends JpaRepository<DoacaoInfo, Long> {
}