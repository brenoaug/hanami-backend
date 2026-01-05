# Hanami - API de Análise de Vendas

![Java](https://img.shields.io/badge/Java-22-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue?logo=apachemaven)

## Sobre o Projeto

O **Hanami** é uma API REST desenvolvida com Spring Boot para processamento, análise e geração de relatórios de dados de vendas a partir de arquivos CSV. Este projeto de estudos foi desenvolvido em parceria com a **Recode** e o **Instituto Coca-Cola**, demonstrando conhecimentos sólidos em desenvolvimento backend, arquitetura de APIs, persistência de dados e boas práticas de programação.

O nome "Hanami" (花見) significa "observar as flores" em japonês, representando a ideia de analisar e extrair insights dos dados, assim como se aprecia a beleza das flores de cerejeira.

## Problema Identificado

Empresas e profissionais frequentemente precisam analisar grandes volumes de dados de vendas armazenados em planilhas CSV. O processo manual de importação, validação e cálculo de métricas é trabalhoso, propenso a erros e demorado.

O **Projeto Hanami** resolve esse problema ao oferecer uma solução automatizada que:
- Processa e valida arquivos CSV de forma rápida e confiável
- Calcula automaticamente métricas financeiras complexas
- Gera relatórios analíticos personalizados
- Persiste os dados de forma estruturada em banco de dados

## Funcionalidades Principais

### Upload e Processamento de CSV
- Recebe arquivos CSV contendo dados de vendas, clientes, produtos e vendedores
- Valida automaticamente a estrutura e integridade dos dados
- Converte e persiste as informações no banco de dados H2

### Cálculos Financeiros Automatizados
- **Receita Líquida:** Cálculo do faturamento total das vendas
- **Custo Total:** Estimativa dos custos baseada em margens de lucro
- **Lucro Bruto:** Diferença entre receita e custos
- **Margens de Lucro:** Cálculo percentual de rentabilidade

### Relatórios Analíticos
- Análise detalhada por produto com múltiplos critérios de ordenação
- Métricas financeiras consolidadas
- Exportação de dados estruturados em JSON

### Validação e Tratamento de Erros
- Validação de layout de arquivo
- Tratamento de dados inconsistentes
- Mensagens de erro descritivas e personalizadas

## Arquitetura e Tecnologias Utilizadas

A aplicação foi desenvolvida seguindo os princípios de **Clean Architecture** e **SOLID**, organizada em camadas bem definidas:

### Backend (API RESTful)

* **Linguagem:** Java 22
* **Framework:** Spring Boot 3.4.4
* **Ferramenta de Build:** Maven
* **Banco de Dados:** H2 Database (em memória)
* **Persistência:** Spring Data JPA / Hibernate
* **Processamento CSV:** Jackson Dataformat CSV
* **Documentação:** SpringDoc OpenAPI (Swagger)
* **Validação:** Spring Validation
* **Logging:** SLF4J / Logback

### Arquitetura em Camadas

```
com.recode.hanami
├── controller/          # Camada de apresentação (REST Controllers)
│   ├── CsvController.java        # Upload de arquivos CSV
│   └── ReportsController.java    # Geração de relatórios
│
├── service/             # Camada de lógica de negócio
│   ├── CsvService.java                     # Conversão CSV → JSON
│   ├── ProcessamentoVendasService.java     # Processamento e persistência
│   └── CalculadoraMetricasService.java     # Cálculos financeiros
│
├── repository/         # Camada de acesso a dados (JPA)
│   ├── VendaRepository.java
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   └── VendedorRepository.java
│
├── entities/            # Entidades JPA (modelo de dados)
│   ├── Venda.java
│   ├── Cliente.java
│   ├── Produto.java
│   └── Vendedor.java
│
├── dto/                 # Data Transfer Objects
│   ├── DadosArquivoDTO.java
│   └── ImportacaoResponseDTO.java
│
├── exceptions/          # Exceções personalizadas
│   ├── ArquivoInvalidoException.java
│   └── DadosInvalidosException.java
│
└── util/               # Classes utilitárias
    └── TratamentoDadosUtil.java
```

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

* **Java 22 (JDK)** - [Download aqui](https://www.oracle.com/java/technologies/downloads/)
* **Maven 3.8+** - [Download aqui](https://maven.apache.org/download.cgi)
* **IDE de sua preferência:**
  * IntelliJ IDEA (recomendado) - [Download aqui](https://www.jetbrains.com/idea/download/)
  * Eclipse IDE - [Download aqui](https://www.eclipse.org/downloads/)
* **Postman ou Insomnia** (opcional) - Para testar os endpoints
  * Postman - [Download aqui](https://www.postman.com/downloads/)
  * Insomnia - [Download aqui](https://insomnia.rest/download)

### Verificar Instalação:

```bash
java -version    # Deve retornar Java 22
mvn -version     # Deve retornar Maven 3.8 ou superior
```

## Como Rodar a Aplicação

### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd hanami
```

### 2. Instale as Dependências

```bash
mvn clean install
```

Este comando irá:
- Baixar todas as dependências do projeto
- Compilar o código-fonte
- Executar os testes
- Gerar o arquivo `.jar` executável

### 3. Execute a Aplicação

#### Opção A - Usando Maven (Recomendado para Desenvolvimento)

```bash
mvn spring-boot:run
```

#### Opção B - Executando o JAR Gerado

```bash
mvn -DskipTests package
java -jar target/hanami-0.0.1-SNAPSHOT.jar
```

### 4. Verifique se a Aplicação Está Rodando

Após iniciar, você verá no console:

```
Started ApiVendasApplication in X.XXX seconds
```

A aplicação estará disponível em: **`http://localhost:8080`**

## Rodando a Aplicação nas IDEs

### IntelliJ IDEA

1. **Abra o projeto:**
   - `File` → `Open` → Selecione a pasta do projeto
   
2. **Configure o JDK:**
   - `File` → `Project Structure` → `Project`
   - Defina o **SDK** como **Java 22**
   
3. **Aguarde a importação do Maven:**
   - O IntelliJ irá detectar automaticamente o `pom.xml`
   - Aguarde o download das dependências (barra inferior da IDE)
   
4. **Execute a aplicação:**
   - Navegue até: `src/main/java/com/recode/hanami/ApiVendasApplication.java`
   - Clique com o botão direito no arquivo
   - Selecione `Run 'ApiVendasApplication'`
   
5. **Acesse os endpoints:**
   - API: `http://localhost:8080`
   - Swagger: `http://localhost:8080/swagger-ui.html`

**Hot Reload:** O projeto inclui o Spring Boot DevTools, que reinicia automaticamente a aplicação ao detectar mudanças no código.

---

### Eclipse IDE

1. **Importe o projeto:**
   - `File` → `Import` → `Maven` → `Existing Maven Projects`
   - Selecione a pasta do projeto e clique em `Finish`
   
2. **Configure o JDK:**
   - Clique com o botão direito no projeto → `Properties`
   - Vá em `Java Build Path` → `Libraries`
   - Certifique-se de que o JRE System Library está configurado para **Java 22**
   
3. **Atualize as dependências Maven:**
   - Clique com o botão direito no projeto
   - Selecione `Maven` → `Update Project`
   - Marque `Force Update of Snapshots/Releases` → `OK`
   
4. **Execute a aplicação:**
   - Navegue até: `src/main/java/com/recode/hanami/ApiVendasApplication.java`
   - Clique com o botão direito no arquivo
   - Selecione `Run As` → `Java Application`
   
5. **Acesse os endpoints:**
   - API: `http://localhost:8080`
   - Swagger: `http://localhost:8080/swagger-ui.html`

## Acessando a Documentação da API (Swagger)

A API possui documentação interativa gerada automaticamente pelo **SpringDoc OpenAPI**.

### Como Acessar:

1. **Certifique-se de que a aplicação está rodando**
   
2. **Acesse o Swagger UI no navegador:**
   ```
   http://localhost:8080/swagger-ui.html
   ```
   
3. **Explore os endpoints disponíveis:**
   - Você verá todos os endpoints organizados por controllers
   - Cada endpoint mostra os parâmetros, tipos de dados e exemplos de resposta
   
4. **Teste diretamente pelo Swagger:**
   - Clique em um endpoint para expandir
   - Clique no botão **"Try it out"**
   - Preencha os parâmetros necessários
   - Clique em **"Execute"**
   - Veja a resposta em tempo real

### Endpoints Documentados:

#### CSV Controller
- `POST /hanami/upload-file` - Upload de arquivo CSV

#### Reports Controller
- `GET /hanami/reports/financial-metrics` - Métricas financeiras gerais
- `GET /hanami/reports/product-analysis` - Análise detalhada por produto

## Testando com Postman ou Insomnia

### Usando Postman

#### 1. Upload de Arquivo CSV

1. Abra o Postman
2. Crie uma nova requisição:
   - **Método:** `POST`
   - **URL:** `http://localhost:8080/hanami/upload-file`
   
3. Configure o Body:
   - Vá na aba **"Body"**
   - Selecione **"form-data"**
   - Adicione um campo com:
     - **Key:** `file` (mude o tipo para "File" no dropdown)
     - **Value:** Clique em "Select Files" e escolha seu arquivo CSV
   
4. Clique em **"Send"**

5. **Resposta esperada:**
   ```json
   {
     "status": "sucesso",
     "registrosProcessados": 100
   }
   ```

#### 2. Consultar Métricas Financeiras

1. Crie uma nova requisição:
   - **Método:** `GET`
   - **URL:** `http://localhost:8080/hanami/reports/financial-metrics`
   
2. Clique em **"Send"**

3. **Resposta esperada:**
   ```json
   {
     "receita_liquida": 150000.50,
     "custo_total": 90000.30,
     "lucro_bruto": 60000.20
   }
   ```

#### 3. Análise por Produto

1. Crie uma nova requisição:
   - **Método:** `GET`
   - **URL:** `http://localhost:8080/hanami/reports/product-analysis?sort_by=lucro`
   
2. **Parâmetros opcionais (Query Params):**
   - `sort_by`: `id`, `lucro`, `receita`, `margem`, `custo`, `quantidade`
   
3. Clique em **"Send"**

---

### Usando Insomnia

As instruções são praticamente idênticas ao Postman:

#### 1. Upload de Arquivo CSV

1. Crie uma nova requisição: `New Request`
2. Configure:
   - **Nome:** Upload CSV
   - **Método:** `POST`
   - **URL:** `http://localhost:8080/hanami/upload-file`
   
3. Vá na aba **"Body"**
4. Selecione **"Multipart Form"**
5. Adicione um campo:
   - **Name:** `file`
   - **Value:** Clique em "Choose File" e selecione seu CSV
   
6. Clique em **"Send"**

#### 2. Demais Endpoints

Siga o mesmo padrão do Postman, alterando apenas a interface.

### Exemplo de Arquivo CSV

Seu arquivo CSV deve ter a seguinte estrutura:

```csv
id_transacao,data_venda,valor_final,subtotal,desconto_percent,canal_venda,forma_pagamento,cliente_id,nome_cliente,idade_cliente,genero_cliente,cidade_cliente,estado_cliente,renda_estimada,produto_id,nome_produto,categoria,marca,preco_unitario,quantidade,margem_lucro,regiao,status_entrega,tempo_entrega_dias,vendedor_id
TRX001,2024-01-15,2500.00,2800.00,10.71,Online,Cartão de Crédito,CLI001,João Silva,35,M,São Paulo,SP,5000.00,PRD001,Notebook Dell,Eletrônicos,Dell,2500.00,1,25.5,Sudeste,Entregue,5,VND001
```

## Acessando o Banco de Dados H2

O projeto utiliza o **H2 Database**, um banco de dados em memória ideal para desenvolvimento e testes.

### Como Acessar o Console H2:

1. **Certifique-se de que a aplicação está rodando**

2. **Acesse o console no navegador:**
   ```
   http://localhost:8080/h2-console
   ```

3. **Configure a conexão:**
   - **Driver Class:** `org.h2.Driver` (já preenchido)
   - **JDBC URL:** `jdbc:h2:mem:hanami_db`
   - **User Name:** `sa`
   - **Password:** *(deixe em branco)*

4. **Clique em "Connect"**

5. **Explore as tabelas:**
   - `VENDAS` - Dados de transações de vendas
   - `CLIENTES` - Informações dos clientes
   - `PRODUTOS` - Catálogo de produtos
   - `VENDEDORES` - Dados dos vendedores

### Consultas SQL Úteis:

```sql
-- Ver todas as vendas
SELECT * FROM VENDAS;

-- Ver todos os clientes
SELECT * FROM CLIENTES;

-- Ver todos os produtos
SELECT * FROM PRODUTOS;

-- Análise de vendas por produto
SELECT p.NOME_PRODUTO, COUNT(*) as TOTAL_VENDAS, SUM(v.VALOR_FINAL) as RECEITA_TOTAL
FROM VENDAS v
JOIN PRODUTOS p ON v.PRODUTO_ID = p.PRODUTO_ID
GROUP BY p.NOME_PRODUTO
ORDER BY RECEITA_TOTAL DESC;
```

### Importante sobre o H2:

- **Banco de dados volátil:** Todos os dados são perdidos quando a aplicação é reiniciada
- **Ideal para desenvolvimento:** Não requer instalação de banco de dados externo
- **Para produção:** Substitua por MySQL, PostgreSQL ou outro banco persistente

## Conceitos e Boas Práticas Aplicadas

Este projeto demonstra conhecimento em:

- **Arquitetura em Camadas** - Separação clara de responsabilidades (Controller, Service, Repository, Entity)  
- **Injeção de Dependência** - Uso do Spring Framework para gerenciamento de beans  
- **REST API** - Endpoints seguindo padrões RESTful  
- **Persistência de Dados** - JPA/Hibernate com relacionamentos entre entidades  
- **DTOs** - Objetos de transferência de dados para desacoplar camadas  
- **Records do Java** - Uso de records para imutabilidade e código conciso  
- **Tratamento de Exceções** - Exceções personalizadas e tratamento centralizado  
- **Validação de Dados** - Validação em múltiplas camadas da aplicação  
- **Logging Estruturado** - Sistema de logs com diferentes níveis e rotação de arquivos  
- **Transações** - Uso de `@Transactional` para garantir integridade dos dados  
- **Documentação Automática** - Swagger/OpenAPI para documentação interativa  
- **Clean Code** - Código limpo, legível e bem organizado  

## Estrutura de Logs

Os logs da aplicação são configurados para facilitar debugging e monitoramento:

### Localização dos Logs:

- **Console:** Saída padrão durante a execução
- **Arquivo:** `logs/app.log`

### Características:

- **Rotação automática:** Arquivos são rotacionados a cada 100MB
- **Histórico:** Mantém os últimos 7 arquivos de log
- **Níveis configurados:**
  - `INFO` - Informações gerais da aplicação
  - `DEBUG` - Detalhes da camada de negócio (`com.recode.hanami`)
  - `DEBUG` - Queries SQL executadas

### Formato do Log:

```
2026-01-05 14:30:25 [main] INFO  c.r.h.ApiVendasApplication - Starting ApiVendasApplication
2026-01-05 14:30:26 [http-nio-8080-exec-1] DEBUG c.r.h.c.CsvController - Arquivo convertido com sucesso
```

## Notas Importantes

- **Projeto de Estudos:** Desenvolvido em parceria com a Recode e Instituto Coca-Cola para demonstrar habilidades técnicas
- **Banco em Memória:** O H2 é volátil - dados são perdidos ao reiniciar
- **Pronto para Produção:** Com ajustes mínimos pode ser adaptado para ambientes reais
- **Código Documentado:** Comentários e estrutura pensados para fácil compreensão
- **Boas Práticas:** Segue convenções do Spring Boot e padrões de mercado

## Possíveis Melhorias Futuras

- [ ] Implementação de testes unitários e de integração mais abrangentes
- [ ] Configuração de banco de dados persistente (MySQL/PostgreSQL)
- [ ] Adição de autenticação e autorização (Spring Security + JWT)
- [ ] Implementação de paginação nos relatórios
- [ ] Cache de resultados para otimização de performance
- [ ] API de exportação de relatórios em PDF/Excel
- [ ] Dashboard frontend com gráficos interativos
- [ ] Deploy em cloud (AWS, Azure, Render, etc.)

## Contato

Projeto desenvolvido por **Breno Augusto** como parte do portfólio técnico em parceria com a **Recode** e **Instituto Coca-Cola**.

---

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

Este projeto foi desenvolvido para fins educacionais e de demonstração de habilidades técnicas.
