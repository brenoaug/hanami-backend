package com.recode.hanami.service;

import com.recode.hanami.dto.DadosArquivoDTO;
import com.recode.hanami.entities.Cliente;
import com.recode.hanami.entities.Produto;
import com.recode.hanami.entities.Venda;
import com.recode.hanami.entities.Vendedor;
import com.recode.hanami.exceptions.DadosInvalidosException;
import com.recode.hanami.repository.ClienteRepository;
import com.recode.hanami.repository.ProdutoRepository;
import com.recode.hanami.repository.VendaRepository;
import com.recode.hanami.repository.VendedorRepository;
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

            Cliente cliente = converterParaCliente(dto);
            clienteRepository.save(cliente);

            Produto produto = converterParaProduto(dto);
            produtoRepository.save(produto);

            Vendedor vendedor = converterParaVendedor(dto);
            vendedorRepository.save(vendedor);

            Venda venda = converterParaVenda(dto, cliente, produto, vendedor);
            vendaRepository.save(venda);
        }
    }

    private Cliente converterParaCliente(DadosArquivoDTO dto) {
        Cliente c = new Cliente();

        if (dto.getClienteId() == null || dto.getClienteId().trim().isEmpty()) {
            throw new DadosInvalidosException
                    ("ID do cliente não pode ser nulo ou vazio");
        }

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

        if (dto.getProdutoId() == null || dto.getProdutoId().trim().isEmpty()) {
            throw new DadosInvalidosException
                    ("ID do produto não pode ser nulo ou vazio");
        }

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

        if (dto.getVendedorId() == null || dto.getVendedorId().trim().isEmpty()) {
            throw new DadosInvalidosException
                    ("ID do vendedor não pode ser nulo ou vazio");
        }

        v.setId(dto.getVendedorId());
        return v;
    }

    private Venda converterParaVenda(DadosArquivoDTO dto, Cliente cliente, Produto produto, Vendedor vendedor) {
        Venda v = new Venda();

        if (dto.getIdTransacao() == null || dto.getIdTransacao().trim().isEmpty()) {
            throw new DadosInvalidosException
                    ("ID da transação não pode ser nulo ou vazio");
        }

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

        if (cliente == null || produto == null || vendedor == null) {
            throw new IllegalArgumentException("Cliente, Produto e Vendedor não podem ser nulos");
        }

        v.setCliente(cliente);
        v.setProduto(produto);
        v.setVendedor(vendedor);

        return v;
    }
}