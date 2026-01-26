# Hanami - API de Análise de Vendas

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9.6-blue?logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-85EA2D?logo=swagger&logoColor=black)
![License](https://img.shields.io/badge/License-MIT-green)

## Sobre o Projeto

O **Hanami** é uma API REST desenvolvida com Spring Boot para processamento, análise e geração de relatórios de dados de vendas a partir de arquivos CSV. Este projeto de estudos foi desenvolvido em parceria com a **Recode**, demonstrando conhecimentos sólidos em desenvolvimento backend, arquitetura de APIs, persistência de dados e boas práticas de programação.

O nome "Hanami" (花見) significa "observar as flores" em japonês, representando a ideia de analisar e extrair insights dos dados, assim como se aprecia a beleza das flores de cerejeira.

## Problema Identificado

Empresas e profissionais frequentemente precisam analisar grandes volumes de dados de vendas armazenados em planilhas CSV. O processo manual de importação, validação e cálculo de métricas é trabalhoso, propenso a erros e demorado.

## Funcionalidades Principais

- **Upload de arquivos CSV** com dados de vendas, clientes, produtos e vendedores
- **Validação automática** da estrutura e integridade dos dados do arquivo
- **Persistência de dados** em banco MySQL
- **Cálculo automático** de receita líquida, custo total e lucro bruto
- **Relatórios agregados** de vendas por produto (quantidade e total arrecadado)
- **Resumo executivo** das vendas (número total, ticket médio, canais e formas de pagamento)
- **Análise de desempenho** por região geográfica (estados e regiões brasileiras)
- **Perfil demográfico** dos clientes (gênero, faixa etária, cidade)
- **Download de relatórios completos** em formato JSON e PDF com gráficos
- **Documentação interativa** dos endpoints via Swagger UI
- **Containerização** com Docker e Docker Compose
- **Logs detalhados** de operações e erros com rotação automática

## Organização de Pastas e Tecnologias Utilizadas

A aplicação está organizada em pastas que refletem as responsabilidades de cada parte do sistema:

```
com.recode.hanami
├── config/              # Configurações da aplicação (ex: OpenAPI/Swagger)
│   └── OpenApiConfig.java
│
├── controller/          # Camada de apresentação (REST Controllers)
│   ├── docs/                       # Interfaces de documentação (OpenAPI)
│   │   ├── CsvControllerOpenApi.java
│   │   └── ReportsControllerOpenApi.java
│   ├── CsvController.java        # Upload de arquivos CSV
│   └── ReportsController.java    # Geração de relatórios
│
├── dto/                 # Data Transfer Objects (DTOs)
│   ├── DadosArquivoDTO.java
│   ├── ImportacaoResponseDTO.java
│   ├── MetricasFinanceirasDTO.java
│   ├── AnaliseProdutoDTO.java
│   ├── ResumoVendasDTO.java
│   ├── RelatorioCompletoDTO.java
│   ├── MetricasRegiaoDTO.java
│   ├── DistribuicaoClientesDTO.java
│   └── ItemDistribuicaoDTO.java
│
├── entities/            # Entidades JPA (modelo de dados)
│   ├── Venda.java
│   ├── Cliente.java
│   ├── Produto.java
│   └── Vendedor.java
│
├── exception/          # Exceções e tratamento de erros
│   ├── handler/                    # Global Exception Handler
│   │   ├── ErrorType.java
│   │   └── GlobalExceptionHandler.java
│   ├── ArquivoInvalidoException.java
│   └── DadosInvalidosException.java
│
├── repository/         # Camada de acesso a dados (JPA Repositories)
│   ├── VendaRepository.java
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   └── VendedorRepository.java
│
├── service/             # Camada de lógica de negócio
│   ├── CsvService.java                     # Conversão CSV → JSON
│   ├── ProcessamentoVendasService.java     # Processamento e persistência
│   ├── CalculadoraMetricasService.java     # Cálculos financeiros
│   ├── CalculosDemografiaRegiao.java       # Métricas regionais e demográficas
│   ├── RelatorioService.java               # Geração de relatórios completos
│   └── PdfService.java                     # Geração de PDFs com gráficos
│
├── util/               # Classes utilitárias
│   ├── DownloadArquivoUtil.java
│   └── TratamentoDadosUtil.java
│
└── validation/         # Validadores customizados
    ├── FormatoRelatorioValidator.java
    ├── SortByValidator.java
    └── UploadArquivoValidator.java
```

Principais tecnologias utilizadas:
- **Java 21**
- **Spring Boot 3.4.1**
- **Maven 3.9.6**
- **MySQL 8.0**
- **Spring Data JPA / Hibernate**
- **Jackson Dataformat CSV**
- **SpringDoc OpenAPI (Swagger)**
- **SLF4J / Logback**
- **OpenPDF 1.3.30** (geração de PDFs)
- **JFreeChart 1.5.3** (geração de gráficos)
- **Docker & Docker Compose**

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

### Opção 1: Rodar com Docker (Recomendado)

* **Docker Desktop** - [Download aqui](https://www.docker.com/products/docker-desktop/)
* **Docker Compose** (já incluído no Docker Desktop)

### Opção 2: Rodar localmente

* **Java 21 (JDK)** - [Download aqui](https://www.oracle.com/java/technologies/downloads/)
* **Maven 3.9+** - [Download aqui](https://maven.apache.org/download.cgi)
* **MySQL 8.0** - [Download aqui](https://dev.mysql.com/downloads/mysql/)
* **IDE de sua preferência:**
  * IntelliJ IDEA (recomendado) - [Download aqui](https://www.jetbrains.com/idea/download/)
  * Eclipse IDE - [Download aqui](https://www.eclipse.org/downloads/)

### Verificar Instalação:

```bash
# Docker
docker --version
docker compose version

# Ou para instalação local
java -version    # Deve retornar Java 21
mvn -version     # Deve retornar Maven 3.9 ou superior
mysql --version  # Deve retornar MySQL 8.0
```

## Como Rodar a Aplicação

### Opção 1: Usando Docker (Recomendado)

Esta é a forma mais rápida e simples de rodar a aplicação, pois o Docker cuida de todas as dependências.

#### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd hanami
```

#### 2. Configure as Variáveis de Ambiente

> **📝 Nota rápida:** O `docker-compose.yml` fornece valores padrão para todas as variáveis importantes. Você pode rodar a aplicação sem criar um arquivo `.env` — execute `docker compose up -d` e os valores padrão serão usados automaticamente.

---

Se você quiser personalizar as credenciais, crie um arquivo `.env` na raiz do projeto (mesmo nível do `docker-compose.yml`) com o conteúdo abaixo:

```env
# Configurações personalizáveis do MySQL (opcional)
MYSQL_ROOT_PASSWORD=root_password
MYSQL_DATABASE=hanami_db
MYSQL_USER=hanami_user
MYSQL_PASSWORD=hanami_password
```

**Exemplo de estrutura de pastas:**
```
hanami/
├── .env                    ← (opcional) Crie este arquivo aqui para personalizar
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── src/
```

**Importante:**
- O arquivo `.env` não deve ser commitado no Git (já está no `.gitignore`).
- Se não criar o `.env`, a aplicação usará os valores padrão definidos em `docker-compose.yml`.

---

**Para rodar com Docker**, crie um arquivo `.env` na raiz do projeto:

**Passo 1:** Crie o arquivo `.env` na pasta raiz do projeto (mesmo nível do `docker-compose.yml`)

**Passo 2:** Copie e cole o seguinte conteúdo no arquivo `.env`:

```env
# Configurações do MySQL para Docker
MYSQL_ROOT_PASSWORD=root_password
MYSQL_DATABASE=hanami_db
MYSQL_USER=hanami_user
MYSQL_PASSWORD=hanami_pass
```

**Exemplo de estrutura de pastas:**
```
hanami/
├── .env                    ← Crie este arquivo aqui
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── src/
```

**Importante:**
- Você pode alterar esses valores conforme necessário
- O arquivo `.env` não deve ser commitado no Git (já está no `.gitignore`)
- Estas variáveis são usadas apenas pelo Docker Compose

---

**Para rodar localmente (sem Docker)**, você tem três opções:

**Opção A - Usando arquivo `.env` com IntelliJ IDEA:**

1. Instale o plugin **EnvFile** no IntelliJ:
   - Vá em `File` → `Settings` → `Plugins`
   - Busque por "EnvFile"
   - Clique em `Install` e reinicie a IDE

2. Crie o arquivo `.env` na raiz do projeto com:
   ```env
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/hanami_db
   SPRING_DATASOURCE_USERNAME=hanami_user
   SPRING_DATASOURCE_PASSWORD=hanami_pass
   ```

3. Configure a Run Configuration:
   - Clique em `Run` → `Edit Configurations`
   - Selecione a configuração `ApiVendasApplication`
   - Na aba `EnvFile`, clique no `+` e adicione o arquivo `.env`
   - Marque a opção `Enable EnvFile`
   - Clique em `Apply` e `OK`

**Opção B - Configurando diretamente no IntelliJ (sem plugin):**

1. Vá em `Run` → `Edit Configurations`
2. Selecione a configuração `ApiVendasApplication`
3. Na seção `Environment variables`, clique no ícone de pasta (ou no botão `...`)
4. Adicione as variáveis uma por uma:
   - **Nome:** `SPRING_DATASOURCE_URL`
     **Valor:** `jdbc:mysql://localhost:3306/hanami_db`

   - **Nome:** `SPRING_DATASOURCE_USERNAME`
     **Valor:** `hanami_user`

   - **Nome:** `SPRING_DATASOURCE_PASSWORD`
     **Valor:** `hanami_pass`

5. Clique em `Apply` e `OK`

**Dica:** Você pode copiar e colar todas de uma vez separadas por ponto e vírgula:
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/hanami_db;SPRING_DATASOURCE_USERNAME=hanami_user;SPRING_DATASOURCE_PASSWORD=hanami_pass
```

**Opção C - Editando o `application.properties` diretamente:**

Edite o arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hanami_db
spring.datasource.username=hanami_user
spring.datasource.password=hanami_pass
```

**⚠️ Atenção:** Se usar a Opção C, não commite o arquivo com suas credenciais!

#### 3. Inicie os Containers

```bash
docker compose up -d
```

Este comando irá:
- Baixar as imagens do MySQL e construir a imagem da aplicação
- Criar e iniciar os containers da aplicação e do banco de dados
- Configurar automaticamente a rede entre os containers
- Persistir os dados do MySQL em um volume Docker

---

#### 4. Usando a imagem publicada no Docker Hub (Alternativa)

Se preferir usar a imagem publicada ao invés de construir localmente, você pode puxá-la diretamente:

**Link da imagem:** https://hub.docker.com/repository/docker/brenoaug/hanami/general

```bash
docker pull brenoaug/hanami:latest
```

Após o pull, você pode usar `image: brenoaug/hanami:latest` no seu `docker-compose.yml` ao invés da seção `build:`.

---

### Opção 2: Rodando Localmente (Sem Docker)

#### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd hanami
```

#### 2. Configure o Banco de Dados MySQL

Crie o banco de dados no MySQL:

```sql
CREATE DATABASE hanami_db;
CREATE USER 'hanami_user'@'localhost' IDENTIFIED BY 'hanami_pass';
GRANT ALL PRIVILEGES ON hanami_db.* TO 'hanami_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 3. Configure o application.properties

Edite o arquivo `src/main/resources/application.properties` ou use variáveis de ambiente:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hanami_db
spring.datasource.username=hanami_user
spring.datasource.password=hanami_pass
```

#### 4. Instale as Dependências

```bash
mvn clean install
```

Este comando irá:
- Baixar todas as dependências do projeto
- Compilar o código-fonte
- Executar os testes
- Gerar o arquivo `.jar` executável

#### 5. Execute a Aplicação

**Usando Maven:**

```bash
mvn spring-boot:run
```

**Ou executando o JAR gerado:**

```bash
mvn -DskipTests package
java -jar target/hanami-0.0.1-SNAPSHOT.jar
```

#### 6. Verifique se a Aplicação Está Rodando

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
   - Defina o **SDK** como **Java 21**
   
3. **Aguarde a importação do Maven:**
   - O IntelliJ irá detectar automaticamente o `pom.xml`
   - Aguarde o download das dependências (barra inferior da IDE)

4. **Configure as Variáveis de Ambiente (se necessário):**
   - Veja a seção "Configure as Variáveis de Ambiente" acima
   - Use o plugin EnvFile ou configure manualmente na Run Configuration
   
5. **Execute a aplicação:**
   - Navegue até: `src/main/java/com/recode/hanami/ApiVendasApplication.java`
   - Clique com o botão direito no arquivo
   - Selecione `Run 'ApiVendasApplication'`
   
6. **Acesse os endpoints:**
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
   - Certifique-se de que o JRE System Library está configurado para **Java 21**

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

A API possui documentação interativa gerada automaticamente pelo **SpringDoc OpenAPI (Swagger)**.

### Como Acessar:

1. **Certifique-se de que a aplicação está rodando** (via Docker ou localmente)
   
2. **Acesse o Swagger UI no navegador:**
   ```
   http://localhost:8080/swagger-ui.html
   ```
   
3. **Explore os endpoints disponíveis:**
   - Você verá todos os endpoints organizados por controllers
   - Cada endpoint mostra os parâmetros, tipos de dados e exemplos de resposta
   - Documentação completa com descrições, schemas e códigos de status HTTP
   
4. **Teste diretamente pelo Swagger:**
   - Clique em um endpoint para expandir
   - Clique no botão **"Try it out"**
   - Preencha os parâmetros necessários
   - Clique em **"Execute"**
   - Veja a resposta em tempo real

**Dica:** O Swagger UI substitui completamente a necessidade de ferramentas como Postman ou Insomnia para testes da API!

### Endpoints Documentados:

#### CSV Controller
- `POST /hanami/upload-file` - Upload e processamento de arquivo CSV

#### Reports Controller
- `GET /hanami/reports/financial-metrics` - Métricas financeiras consolidadas
- `GET /hanami/reports/product-analysis` - Análise agregada por produto
- `GET /hanami/reports/sales-summary` - Resumo executivo de vendas
- `GET /hanami/reports/regional-performance` - Desempenho por região geográfica
- `GET /hanami/reports/customer-profile` - Perfil demográfico dos clientes
- `GET /hanami/reports/download` - Download de relatório completo (JSON/PDF)

## Detalhes dos Endpoints da API

### 1. Upload de Arquivo CSV
**Endpoint:** `POST /hanami/upload-file`

**Descrição:** Recebe um arquivo CSV, valida sua estrutura e persiste os dados no banco MySQL.

**Content-Type:** `multipart/form-data`

**Parâmetros:**
- `file` (form-data) - Arquivo CSV contendo dados de vendas

**Exemplo de Requisição (curl):**
```bash
curl -X POST http://localhost:8080/hanami/upload-file \
  -F "file=@/caminho/para/arquivo.csv"
```

**Exemplo de Requisição (Swagger UI):**
1. Acesse http://localhost:8080/swagger-ui.html
2. Expanda `POST /hanami/upload-file`
3. Clique em "Try it out"
4. Clique em "Choose File" e selecione seu CSV
5. Clique em "Execute"

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

**Possíveis Erros:**
- **400 Bad Request** - Arquivo inválido ou dados inconsistentes
- **500 Internal Server Error** - Erro no processamento

---

### 2. Métricas Financeiras
**Endpoint:** `GET /hanami/reports/financial-metrics`

**Descrição:** Retorna um resumo consolidado das principais métricas financeiras: receita líquida total, custo total operacional e lucro bruto.

**Exemplo de Requisição (curl):**
```bash
curl -X GET http://localhost:8080/hanami/reports/financial-metrics
```

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

**Exemplo de Requisição (curl):**
```bash
# Ordenar por total arrecadado
curl -X GET "http://localhost:8080/hanami/reports/product-analysis?sort_by=total"

# Ordenar por quantidade vendida
curl -X GET "http://localhost:8080/hanami/reports/product-analysis?sort_by=quantidade"

# Ordenar por nome (padrão)
curl -X GET "http://localhost:8080/hanami/reports/product-analysis"
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

**Parâmetros de Query (opcionais):**
- `start_date` (LocalDate) - Data inicial do período (formato: YYYY-MM-DD)
- `end_date` (LocalDate) - Data final do período (formato: YYYY-MM-DD)

**Exemplo de Requisição (curl):**
```bash
# Resumo completo (todas as vendas)
curl -X GET http://localhost:8080/hanami/reports/sales-summary

# Resumo com filtro de período
curl -X GET "http://localhost:8080/hanami/reports/sales-summary?start_date=2024-01-01&end_date=2024-12-31"
```

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

### 5. Desempenho por Região
**Endpoint:** `GET /hanami/reports/regional-performance`

**Descrição:** Retorna métricas de vendas agrupadas por região geográfica (regiões brasileiras) ou filtradas por estado específico. Para cada região/estado, são calculados: total de transações, receita total, quantidade de produtos vendidos e valor médio por transação.

**Parâmetros de Query (opcionais):**
- `estado` (string) - Sigla do estado para filtrar (ex: SP, RJ, MG)

**Exemplo de Requisição (curl):**
```bash
# Desempenho por todas as regiões
curl -X GET http://localhost:8080/hanami/reports/regional-performance

# Desempenho apenas de São Paulo
curl -X GET "http://localhost:8080/hanami/reports/regional-performance?estado=SP"
```

**Resposta de Sucesso (200):**
```json
{
  "Sudeste": {
    "totalTransacoes": 4523,
    "receitaTotal": 1250300.50,
    "quantidadeVendida": 8900,
    "mediaValorTransacao": 276.42
  },
  "Sul": {
    "totalTransacoes": 2156,
    "receitaTotal": 680200.00,
    "quantidadeVendida": 4350,
    "mediaValorTransacao": 315.52
  }
}
```

**Regiões Brasileiras Suportadas:**
- Norte
- Nordeste
- Centro-Oeste
- Sudeste
- Sul

---

### 6. Perfil Demográfico dos Clientes
**Endpoint:** `GET /hanami/reports/customer-profile`

**Descrição:** Retorna a distribuição dos clientes por gênero, faixa etária e cidade. Para cada categoria, são apresentadas a contagem e o percentual do total.

**Exemplo de Requisição (curl):**
```bash
curl -X GET http://localhost:8080/hanami/reports/customer-profile
```

**Resposta de Sucesso (200):**
```json
{
  "por_genero": {
    "M": {
      "contagem": 5230,
      "percentual": 52.30
    },
    "F": {
      "contagem": 4770,
      "percentual": 47.70
    }
  },
  "por_faixa_etaria": {
    "26-35": {
      "contagem": 3200,
      "percentual": 32.00
    },
    "36-45": {
      "contagem": 2800,
      "percentual": 28.00
    }
  },
  "por_cidade": {
    "São Paulo": {
      "contagem": 2500,
      "percentual": 25.00
    }
  }
}
```

**Faixas Etárias:**
- 18-25, 26-35, 36-45, 46-55, 56-65, 66+

---

### 7. Download de Relatório Completo
**Endpoint:** `GET /hanami/reports/download?format={json|pdf}`

**Descrição:** Faz o download de um relatório completo de análise de vendas nos formatos JSON ou PDF. O relatório inclui todas as métricas financeiras, análise de produtos, resumo de vendas e desempenho regional consolidados em um único arquivo.

**Parâmetros de Query (obrigatório):**
- `format` (string): Formato do relatório
  - `json` - Retorna arquivo `report.json` para download
  - `pdf` - Retorna arquivo `report.pdf` com tabelas e gráficos

**Exemplos de Requisição (curl):**
```bash
# Download em JSON
curl -X GET "http://localhost:8080/hanami/reports/download?format=json" \
  -o report.json

# Download em PDF
curl -X GET "http://localhost:8080/hanami/reports/download?format=pdf" \
  -o report.pdf
```

**Exemplo de Requisição (Navegador):**
```
http://localhost:8080/hanami/reports/download?format=json
http://localhost:8080/hanami/reports/download?format=pdf
```

**Resposta de Sucesso (200) - JSON:**
```
Content-Type: application/json
Content-Disposition: attachment; filename="report.json"
```
```json
{
  "data_geracao": "2026-01-26T10:30:00",
  "metricas_financeiras": {
    "receita_liquida": 458900.75,
    "custo_total": 321230.50,
    "lucro_bruto": 137670.25
  },
  "analise_produtos": [
    {
      "nome_produto": "Notebook Dell",
      "quantidade_vendida": 125,
      "total_arrecadado": 400000.00
    }
  ],
  "resumo_vendas": {
    "numero_total_vendas": 356,
    "valor_medio_por_transacao": 690.45,
    "forma_pagamento_mais_utilizada": "Cartão de Crédito",
    "canal_vendas_mais_utilizado": "E-commerce"
  },
  "desempenho_regional": {
    "Sudeste": {
      "totalTransacoes": 4523,
      "receitaTotal": 1250300.50,
      "quantidadeVendida": 8900,
      "mediaValorTransacao": 276.42
    }
  }
}
```

**Resposta de Sucesso (200) - PDF:**
```
Content-Type: application/pdf
Content-Disposition: attachment; filename="report.pdf"
```

O PDF contém:
- **Título** com data e hora de geração
- **Tabela de Métricas Financeiras** (receita, custos, lucro)
- **Gráfico de Barras** com receita total por região (gerado com JFreeChart)
- **Tabela de Análise de Produtos** (top 10 produtos)
- **Tabela de Resumo de Vendas**
- **Tabela de Desempenho Regional Detalhado**

**Características do PDF:**
- Design profissional com cabeçalhos coloridos
- Formatação brasileira (R$ e dd/MM/yyyy HH:mm:ss)
- Gráfico visual de barras mostrando receita por região
- Tabelas bem formatadas e organizadas
- Gerado usando OpenPDF e JFreeChart

**Casos de Uso:**
- Exportar dados para análise offline
- Compartilhar relatórios com stakeholders
- Manter histórico de análises
- Apresentações executivas (formato PDF)
- Integração com outros sistemas (formato JSON)

---

## Exemplo de Arquivo CSV

Seu arquivo CSV deve ter a seguinte estrutura com as colunas obrigatórias:

```csv
id_transacao,data_venda,valor_final,subtotal,desconto_percent,canal_venda,forma_pagamento,cliente_id,nome_cliente,idade_cliente,genero_cliente,cidade_cliente,estado_cliente,renda_estimada,produto_id,nome_produto,categoria,marca,preco_unitario,quantidade,margem_lucro,regiao,status_entrega,tempo_entrega_dias,vendedor_id
TRX001,2024-01-15,2500.00,2800.00,10.71,Online,Cartão de Crédito,CLI001,João Silva,35,M,São Paulo,SP,5000.00,PRD001,Notebook Dell,Eletrônicos,Dell,2500.00,1,25.5,Sudeste,Entregue,5,VND001
TRX002,2024-01-16,1200.00,1200.00,0.00,Loja Física,Dinheiro,CLI002,Maria Santos,28,F,Rio de Janeiro,RJ,4500.00,PRD002,Mouse Gamer,Periféricos,Logitech,1200.00,1,30.0,Sudeste,Entregue,3,VND002
```

**Colunas Obrigatórias:**
- `id_transacao` - Identificador único da transação
- `data_venda` - Data da venda (formato: YYYY-MM-DD)
- `valor_final` - Valor final da venda
- `subtotal` - Subtotal antes de descontos
- `desconto_percent` - Percentual de desconto aplicado
- `canal_venda` - Canal de vendas (Online, Loja Física, Telefone, etc.)
- `forma_pagamento` - Forma de pagamento (Cartão de Crédito, Boleto, PIX, etc.)
- `cliente_id` - ID do cliente
- `nome_cliente` - Nome completo do cliente
- `idade_cliente` - Idade do cliente
- `genero_cliente` - Gênero (M/F)
- `cidade_cliente` - Cidade do cliente
- `estado_cliente` - Estado (sigla: SP, RJ, MG, etc.)
- `renda_estimada` - Renda estimada do cliente
- `produto_id` - ID do produto
- `nome_produto` - Nome do produto
- `categoria` - Categoria do produto
- `marca` - Marca do produto
- `preco_unitario` - Preço unitário do produto
- `quantidade` - Quantidade vendida
- `margem_lucro` - Margem de lucro em percentual
- `regiao` - Região geográfica
- `status_entrega` - Status da entrega
- `tempo_entrega_dias` - Tempo de entrega em dias
- `vendedor_id` - ID do vendedor

---

## Acessando o Banco de Dados MySQL

### Usando Docker

Quando rodando com Docker, você pode acessar o MySQL diretamente:

```bash
# Acessar o MySQL no container
docker exec -it hanami-db-container mysql -u hanami_user -p

# Quando solicitado, digite a senha: hanami_pass
```

**Comandos úteis SQL:**
```sql
USE hanami_db;

SHOW TABLES;

SELECT COUNT(*) FROM vendas;
SELECT COUNT(*) FROM clientes;
SELECT COUNT(*) FROM produtos;
SELECT COUNT(*) FROM vendedores;

-- Ver primeiras 10 vendas
SELECT * FROM vendas LIMIT 10;
```

### Usando Cliente MySQL Local

Se você instalou o MySQL localmente, pode usar qualquer cliente MySQL:

- **MySQL Workbench** - Interface gráfica
- **DBeaver** - Cliente universal
- **Linha de comando:**
  ```bash
  mysql -h localhost -P 3306 -u hanami_user -p hanami_db
  ```

**Informações de Conexão:**
- **Host:** localhost
- **Porta:** 3306
- **Banco de Dados:** hanami_db
- **Usuário:** hanami_user
- **Senha:** hanami_pass

### Tabelas Disponíveis:
- `vendas` - Dados de transações de vendas
- `clientes` - Informações dos clientes
- `produtos` - Catálogo de produtos
- `vendedores` - Dados dos vendedores

---

## Troubleshooting (Solução de Problemas)

### Problemas com Docker

#### Arquivo .env não está sendo lido
```bash
# Certifique-se de que o arquivo .env está na raiz do projeto
ls -la .env  # Linux/Mac
dir .env     # Windows

# Verifique se as variáveis estão corretas
cat .env     # Linux/Mac
type .env    # Windows

# Recrie os containers para aplicar as mudanças
docker compose down
docker compose up -d
```

#### A aplicação não inicia
```bash
# Verifique os logs
docker compose logs hanami

# Certifique-se de que o MySQL está saudável
docker compose ps
```

#### Porta 8080 já em uso
```bash
# Encontre o processo usando a porta (Windows)
netstat -ano | findstr :8080

# Mate o processo (substitua PID pelo número encontrado)
taskkill /PID <PID> /F

# Ou altere a porta no docker-compose.yml
ports:
  - "8081:8080"  # Acesse via localhost:8081
```

#### Porta 3306 (MySQL) já em uso
Se você já tem MySQL instalado localmente:
```yaml
# Altere no docker-compose.yml
ports:
  - "3307:3306"  # Use porta 3307 externamente
```

#### Rebuild após mudanças no código
```bash
docker compose down
docker compose up -d --build
```

#### Limpar completamente e recomeçar
```bash
# Remove containers, volumes e imagens
docker compose down -v
docker rmi hanami
docker compose up -d --build
```

### Problemas ao Rodar Localmente

#### Variáveis de ambiente não estão sendo lidas no IntelliJ
1. **Verifique se o plugin EnvFile está instalado e ativado:**
   - `File` → `Settings` → `Plugins` → Busque "EnvFile"
   
2. **Verifique a Run Configuration:**
   - `Run` → `Edit Configurations`
   - Na aba `EnvFile`, certifique-se de que o arquivo `.env` está marcado
   - Marque a opção `Enable EnvFile`

3. **Se não usar o plugin, configure manualmente:**
   - `Run` → `Edit Configurations`
   - Em `Environment variables`, adicione cada variável separadamente

4. **Alternativa - Use variáveis do sistema (Windows):**
   ```powershell
   # PowerShell (temporário - apenas para a sessão atual)
   $env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/hanami_db"
   $env:SPRING_DATASOURCE_USERNAME="hanami_user"
   $env:SPRING_DATASOURCE_PASSWORD="hanami_pass"
   ```

5. **Verifique se as variáveis estão sendo carregadas:**
   - Você pode adicionar um log simples no método `main` (ou usar um breakpoint) para imprimir as env vars durante a inicialização da aplicação, por exemplo: System.out.println("Database URL: " + System.getenv("SPRING_DATASOURCE_URL"));

#### Erro de conexão com MySQL
- Verifique se o MySQL está rodando
- Confirme usuário e senha no `application.properties`
- Teste a conexão:
  ```bash
  mysql -h localhost -u hanami_user -p
  ```

#### Erro "Port 8080 is already in use"
- Altere a porta no `application.properties`:
  ```properties
  server.port=8081
  ```

#### Dependências não baixadas
```bash
# Limpe e reinstale
mvn clean install -U
```

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

Projeto desenvolvido por **Breno Augusto** como parte do portfólio técnico em parceria com a **Recode**.

---

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

Este projeto foi desenvolvido para fins educacionais e de demonstração de habilidades técnicas.
