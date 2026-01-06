package com.recode.hanami.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hanami - API de Análise de Vendas",
                version = "1.0.0",
                description = """
                        API REST para processamento, análise e geração de relatórios de dados de vendas a partir de arquivos CSV.
                        
                        ## Funcionalidades:
                        - Upload e processamento de arquivos CSV com dados de vendas
                        - Validação automática de dados e estrutura do arquivo
                        - Cálculo de métricas financeiras (receita, custos, lucros, margens)
                        - Geração de relatórios analíticos detalhados
                        - Persistência de dados em banco H2
                        
                        ## Formato CSV Esperado:
                        O arquivo deve conter as seguintes colunas:
                        - Dados da Transação: id_transacao, data_venda, valor_final, subtotal, desconto_percent, canal_venda, forma_pagamento
                        - Dados do Cliente: cliente_id, nome_cliente, idade_cliente, genero_cliente, cidade_cliente, estado_cliente, renda_estimada
                        - Dados do Produto: produto_id, nome_produto, categoria, marca, preco_unitario, quantidade, margem_lucro
                        - Dados Logísticos: regiao, status_entrega, tempo_entrega_dias, vendedor_id
                        
                        ## Projeto desenvolvido em parceria com:
                        - Recode Pro
                        - Instituto Coca-Cola
                        """,
                contact = @Contact(
                        name = "Breno Augusto",
                        email = "breaugustocp@outlook.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor Local",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}

