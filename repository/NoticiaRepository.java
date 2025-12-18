package br.com.higitech.rccAcopiara.repository;

import br.com.higitech.rccAcopiara.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    // Busca as notícias mais recentes primeiro
    List<Noticia> findAllByOrderByDataPublicacaoDesc();
}