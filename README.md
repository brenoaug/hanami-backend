# Hanami - API de Análise de Vendas

![Java](https://img.shields.io/badge/Java-21-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue?logo=apachemaven)

## Sobre o Projeto

O **Hanami** é uma API REST desenvolvida com Spring Boot para processamento, análise e geração de relatórios de dados de vendas a partir de arquivos CSV. Este projeto de estudos foi desenvolvido em parceria com a **Recode** e o **Instituto Coca-Cola**, demonstrando conhecimentos sólidos em desenvolvimento backend, arquitetura de APIs, persistência de dados e boas práticas de programação.

O nome "Hanami" (花見) significa "observar as flores" em japonês, representando a ideia de analisar e extrair insights dos dados, assim como se aprecia a beleza das flores de cerejeira.

## Problema Identificado

Empresas e profissionais frequentemente precisam analisar grandes volumes de dados de vendas armazenados em planilhas CSV. O processo manual de importação, validação e cálculo de métricas é trabalhoso, propenso a erros e demorado.

## Funcionalidades Principais

- Upload de arquivos CSV de vendas, clientes, produtos e vendedores
- Validação automática da estrutura e dos dados do arquivo
- Persistência dos dados no banco H2 em memória
- Cálculo automático de receita líquida, custo total e lucro bruto
- Relatórios de vendas agregados por produto (quantidade e total arrecadado)
- Resumo executivo das vendas (número total, ticket médio, canais e formas de pagamento mais/menos usados)
- Documentação automática dos endpoints via Swagger
- Logs detalhados de operações e erros

## Organização de Pastas e Tecnologias Utilizadas

A aplicação está organizada em pastas que refletem as responsabilidades de cada parte do sistema:

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

Principais tecnologias utilizadas:
- **Java 21**
- **Spring Boot 3.4.4**
- **Maven**
- **H2 Database (memória)**
- **Spring Data JPA / Hibernate**
- **Jackson Dataformat CSV**
- **SpringDoc OpenAPI (Swagger)**
- **SLF4J / Logback**

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
- `GET /hanami/reports/financial-metrics` - Retorna receita líquida, custo total e lucro bruto.
- `GET /hanami/reports/product-analysis` - Retorna lista de produtos com quantidade vendida e total arrecadado.
- `GET /hanami/reports/sales-summary` - Retorna resumo com número de vendas, ticket médio, formas de pagamento e canais mais/menos usados.

## Detalhes dos Endpoints da API

### 1. Upload de Arquivo CSV
**Endpoint:** `POST /hanami/upload-file`

**Descrição:** Recebe um arquivo CSV, valida sua estrutura e persiste os dados no banco H2.

**Content-Type:** `multipart/form-data`

**Parâmetros:**
- `file` (form-data) - Arquivo CSV contendo dados de vendas

**Resposta de Sucesso (200):**
```json
{
  "status": "sucesso",
  "registrosProcessados": 10000
}
```

**Validações:**
- Verifica se o arquivo tem extensão `.csv`
- Valida se todas as colunas obrigatórias estão presentes
- Verifica integridade dos dados (tipos, formatos, valores nulos)

---

### 2. Métricas Financeiras
**Endpoint:** `GET /hanami/reports/financial-metrics`

**Descrição:** Retorna um resumo consolidado das principais métricas financeiras: receita líquida total, custo total operacional e lucro bruto.

**Resposta de Sucesso (200):**
```json
{
  "receita_liquida": 102614924.62,
  "custo_total": 86384699.09,
  "lucro_bruto": 16230225.53
}
```

**Cálculos:**
- `receita_liquida` = Soma de todos os `valor_final` das vendas
- `custo_total` = Soma dos custos estimados (baseado em `precoUnitario / (1 + margemLucro)`)
- `lucro_bruto` = `receita_liquida - custo_total` (calculado sem arredondamento intermediário)

**Nota Técnica:** O lucro bruto é calculado diretamente da diferença entre receita e custo total, evitando acúmulo de erros de arredondamento.

---

### 3. Análise por Produto (Agregada)
**Endpoint:** `GET /hanami/reports/product-analysis`

**Descrição:** Retorna uma análise agregada das vendas agrupadas por produto, somando a quantidade vendida e o total arrecadado de cada produto.

**Parâmetros de Query (opcionais):**
- `sort_by` (string): Critério de ordenação
  - `nome` (padrão) - Ordena alfabeticamente por nome do produto
  - `quantidade` - Ordena por quantidade total vendida (decrescente)
  - `total` - Ordena por receita total arrecadada (decrescente)

**Exemplo de Requisição:**
```
GET /hanami/reports/product-analysis?sort_by=total
```

**Resposta de Sucesso (200):**
```json
[
  {
    "nome_produto": "webcam hd",
    "quantidade_vendida": 450,
    "total_arrecadado": 125450.75
  },
  {
    "nome_produto": "mouse logitech",
    "quantidade_vendida": 1200,
    "total_arrecadado": 84000.00
  }
]
```

**Funcionalidade:** 
- Agrupa todas as vendas pelo nome do produto
- Soma as quantidades vendidas de cada produto
- Soma o valor total arrecadado por produto
- Retorna lista ordenada conforme parâmetro `sort_by`

---

### 4. Resumo de Vendas
**Endpoint:** `GET /hanami/reports/sales-summary`

**Descrição:** Retorna um resumo executivo das vendas com métricas estratégicas: número total de vendas, valor médio por transação e análise dos canais de venda e formas de pagamento mais e menos utilizados.

**Resposta de Sucesso (200):**
```json
{
  "numero_total_vendas": 10000,
  "valor_medio_por_transacao": 10261.49,
  "forma_pagamento_mais_utilizada": "Cartão de Crédito",
  "forma_pagamento_menos_utilizada": "Boleto",
  "canal_vendas_mais_utilizado": "E-commerce",
  "canal_vendas_menos_utilizado": "Telefone"
}
```

**Métricas Retornadas:**
- `numero_total_vendas` = Quantidade total de transações realizadas
- `valor_medio_por_transacao` = Ticket médio de venda (receita total / número de transações)
- `forma_pagamento_mais_utilizada` = Método de pagamento com maior número de transações
- `forma_pagamento_menos_utilizada` = Método de pagamento com menor número de transações
- `canal_vendas_mais_utilizado` = Canal que gerou mais vendas
- `canal_vendas_menos_utilizado` = Canal que gerou menos vendas

**Insights de Negócio:**
Este endpoint fornece informações estratégicas para:
- Entender o ticket médio das vendas
- Identificar preferências de pagamento dos clientes
- Descobrir quais canais de venda são mais eficientes
- Tomar decisões sobre investimento em canais menos utilizados

---

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
     "receita_liquida": 102614924.62,
     "custo_total": 86384699.09,
     "lucro_bruto": 16230225.53
   }
   ```
   
   **Nota:** O `lucro_bruto` é calculado como `receita_liquida - custo_total`, garantindo precisão matemática sem acúmulo de erros de arredondamento.

#### 3. Análise por Produto (Agregada)

1. Crie uma nova requisição:
   - **Método:** `GET`
   - **URL:** `http://localhost:8080/hanami/reports/product-analysis?sort_by=total`
   
2. **Parâmetros opcionais (Query Params):**
   - `sort_by`: 
     - `nome` (padrão) - Ordena por nome do produto (A-Z)
     - `quantidade` - Ordena por quantidade total vendida (maior → menor)
     - `total` - Ordena por receita total arrecadada (maior → menor)
   
3. Clique em **"Send"**

4. **Resposta esperada:**
   ```json
   [
     {
       "nome_produto": "webcam hd",
       "quantidade_vendida": 450,
       "total_arrecadado": 125450.75
     },
     {
       "nome_produto": "mouse logitech",
       "quantidade_vendida": 1200,
       "total_arrecadado": 84000.00
     }
   ]
   ```
   
   **Funcionalidade:** Este endpoint agrupa todas as vendas por produto, somando as quantidades vendidas e o total de receita gerado por cada produto.

#### 4. Resumo de Vendas

1. Crie uma nova requisição:
   - **Método:** `GET`
   - **URL:** `http://localhost:8080/hanami/reports/sales-summary`
   
2. Clique em **"Send"**

3. **Resposta esperada:**
   ```json
   {
     "numero_total_vendas": 10000,
     "valor_medio_por_transacao": 10261.49,
     "forma_pagamento_mais_utilizada": "Cartão de Crédito",
     "forma_pagamento_menos_utilizada": "Boleto",
     "canal_vendas_mais_utilizado": "E-commerce",
     "canal_vendas_menos_utilizado": "Telefone"
   }
   ```
   
   **Análise dos Resultados:**
   - O endpoint retorna métricas estratégicas sobre as vendas
   - Identifica padrões de comportamento de compra dos clientes
   - Mostra quais canais e formas de pagamento precisam de mais atenção

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

## Contato

Projeto desenvolvido por **Breno Augusto** como parte do portfólio técnico em parceria com a **Recode** e **Instituto Coca-Cola**.

---

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

Este projeto foi desenvolvido para fins educacionais e de demonstração de habilidades técnicas.
