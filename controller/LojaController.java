package br.com.higitech.rccAcopiara.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.higitech.rccAcopiara.model.Pedido;
import br.com.higitech.rccAcopiara.model.Produto;
import br.com.higitech.rccAcopiara.repository.PedidoRepository;
import br.com.higitech.rccAcopiara.repository.ProdutoRepository;
import br.com.higitech.rccAcopiara.service.CloudinaryService;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin("*")
public class LojaController {

    @Autowired private ProdutoRepository produtoRepo;
    @Autowired private PedidoRepository pedidoRepo;
    @Autowired private CloudinaryService cloudinaryService;

    // --- PRODUTOS ---

    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return produtoRepo.findAll();
    }

    @PostMapping("/produtos")
    public ResponseEntity<Produto> adicionarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") Double preco,
            @RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(file);
            Produto p = new Produto();
            p.setNome(nome);
            p.setDescricao(descricao);
            p.setPreco(BigDecimal.valueOf(preco));
            p.setImagemUrl((String) uploadResult.get("url"));
            p.setPublicId((String) uploadResult.get("public_id"));
            return ResponseEntity.ok(produtoRepo.save(p));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        Produto p = produtoRepo.findById(id).orElse(null);
        if (p != null) {
            try {
                if (p.getPublicId() != null) cloudinaryService.deleteFile(p.getPublicId());
                produtoRepo.delete(p);
                return ResponseEntity.ok().build();
            } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
        }
        return ResponseEntity.notFound().build();
    }

    // --- PEDIDOS ---

    @GetMapping("/pedidos")
    public List<Pedido> listarPedidos() {
        return pedidoRepo.findAllByOrderByDataPedidoDesc();
    }

    @PostMapping("/pedidos")
    public Pedido salvarPedido(@RequestBody Pedido pedido) {
        pedido.setStatus("PENDENTE");
        return pedidoRepo.save(pedido);
    }

    @DeleteMapping("/pedidos/{id}")
    public void deletarPedido(@PathVariable Long id) {
        pedidoRepo.deleteById(id);
    }
    
    // Marcar como entregue
    @PutMapping("/pedidos/{id}/entregar")
    public Pedido marcarEntregue(@PathVariable Long id) {
        Pedido p = pedidoRepo.findById(id).get();
        p.setStatus("ENTREGUE");
        return pedidoRepo.save(p);
    }
}