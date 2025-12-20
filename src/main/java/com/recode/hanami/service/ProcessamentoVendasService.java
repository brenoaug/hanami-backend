package com.recode.hanami.service;

import com.recode.hanami.dto.DadosArquivoDTO;
import com.recode.hanami.entities.*;
import com.recode.hanami.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProcessamentoVendasService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendedorRepository vendedorRepository;
    private final VendaRepository vendaRepository;

    public ProcessamentoVendasService(ClienteRepository clienteRepository,
                                      ProdutoRepository produtoRepository,
                                      VendedorRepository vendedorRepository,
                                      VendaRepository vendaRepository) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.vendedorRepository = vendedorRepository;
        this.vendaRepository = vendaRepository;
    }

    @Transactional
    public void salvarDadosDoArquivo(List<DadosArquivoDTO> listaDtos) {

        for (DadosArquivoDTO dto : listaDtos) {
            // 1. Salvar ou Atualizar CLIENTE
            Cliente cliente = converterParaCliente(dto);
            clienteRepository.save(cliente);

            // 2. Salvar ou Atualizar PRODUTO
            Produto produto = converterParaProduto(dto);
            produtoRepository.save(produto);

            // 3. Salvar ou Atualizar VENDEDOR
            Vendedor vendedor = converterParaVendedor(dto);
            vendedorRepository.save(vendedor);

            // 4. Salvar VENDA (Amarrando tudo)
            Venda venda = converterParaVenda(dto, cliente, produto, vendedor);
            vendaRepository.save(venda);
        }
    }

    // --- MÉTODOS AUXILIARES DE CONVERSÃO (MAPPERS) ---

    private Cliente converterParaCliente(DadosArquivoDTO dto) {
        Cliente c = new Cliente();
        c.setId(dto.getClienteId());
        c.setNomeCliente(dto.getNomeCliente());
        c.setIdadeCliente(dto.getIdadeCliente());
        c.setGeneroCliente(dto.getGeneroCliente());
        c.setCidadeCliente(dto.getCidadeCliente());
        c.setEstadoCliente(dto.getEstadoCliente());

        // Passagem direta (sem conversão)
        c.setRendaEstimada(dto.getRendaEstimada());

        return c;
    }

    private Produto converterParaProduto(DadosArquivoDTO dto) {
        Produto p = new Produto();
        p.setId(dto.getProdutoId());
        p.setNomeProduto(dto.getNomeProduto());
        p.setCategoria(dto.getCategoria());
        p.setMarca(dto.getMarca());
        p.setMargemLucro(dto.getMargemLucro());
        p.setPrecoUnitario(dto.getPrecoUnitario());

        return p;
    }

    private Vendedor converterParaVendedor(DadosArquivoDTO dto) {
        Vendedor v = new Vendedor();
        v.setId(dto.getVendedorId());
        return v;
    }

    private Venda converterParaVenda(DadosArquivoDTO dto, Cliente cliente, Produto produto, Vendedor vendedor) {
        Venda v = new Venda();
        v.setId(dto.getIdTransacao());
        v.setDataVenda(dto.getDataVenda());

        v.setValorFinal(dto.getValorFinal());
        v.setSubtotal(dto.getSubtotal());

        v.setDescontoPercent(dto.getDescontoPercent());
        v.setQuantidade(dto.getQuantidade());
        v.setCanalVenda(dto.getCanalVenda());
        v.setFormaPagamento(dto.getFormaPagamento());

        v.setRegiao(dto.getRegiao());
        v.setStatusEntrega(dto.getStatusEntrega());
        v.setTempoEntregaDias(dto.getTempoEntregaDias());

        v.setCliente(cliente);
        v.setProduto(produto);
        v.setVendedor(vendedor);

        return v;
    }
}