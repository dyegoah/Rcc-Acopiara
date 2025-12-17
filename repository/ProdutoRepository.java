package br.com.higitech.rccAcopiara.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.higitech.rccAcopiara.model.Produto;
public interface ProdutoRepository extends JpaRepository<Produto, Long> {}