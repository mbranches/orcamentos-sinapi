## Orçamentos Sinapi

> Sistema desktop para criação e gestão de orçamentos de insumos da construção civil, com base na tabela SINAPI.

Este repositório contém o **Backend** e o **Frontend** do projeto.

---

## 📁 Estrutura do Repositório
*  [`backend`](./backend): Módulo da API RestFul, desenvolvida com **Spring Boot**.
*  [`frontend`](./frontend): Front-End em **JavaFX**.

---

## 🎯 Funcionalidades

* ✅ **Cadastro e Gestão de Insumos:** Permite o cadastro, edição e exclusão de insumos utilizados na construção civil, com base na tabela SINAPI.
* ✅ **Criação de Orçamentos:** Criação de orçamentos com cálculo automático do custo total, baseado nos insumos selecionados.
* ✅ **Visualização de Orçamentos:** Visualização de orçamentos com detalhes dos insumos e custos envolvidos.
---

## 🛠️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-17-blue?logo=java) - Linguagem de programação utilizada.
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-brightgreen?logo=spring) - Framework para criação de APIs REST em Java.
- ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker) - Conteinerização da aplicação.
- ![MySQL](https://img.shields.io/badge/MySQL-black?logo=mysql) - Banco de dados utilizado para persistência de dados.
- ![JavaFX](https://img.shields.io/badge/JavaFX-OpenJFX-orange) - Interface interativa e de fácil utilização.
- ![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven) - Gerencia dependências e automação de builds para projetos Java.

---

## 📸 Demonstração

### Interface do App Desktop
<div style="text-align: center;">
  <img src="assets/tela-inicial.png" width="700"/>
</div>
<div style="text-align: center;">
  <img src="./assets/tela-orcamento.png" width="700"/>
</div>
<div style="text-align: center;">
  <img src="./assets/tela-orcamentos.png" width="700"/>
</div>

### Modelagem do Banco de Dados
<div style="text-align: center;">
  <img src="./assets/modelo-bd.png" width="700"/>
</div>

---

## 🚀  Como Rodar a API

### 1. Configuração das Variáveis de Ambiente

Antes de iniciar a aplicação, é necessário configurar as variáveis de ambiente para garantir que todos os módulos funcionem corretamente.

#### a. **Variável Docker (`backend`)**
- Navegue até o diretório do módulo `backend`.
- Renomeie o arquivo `.envTemplate` para `.env` e preencha a variável com o valor desejado para o seu ambiente de desenvolvimento.

#### b. **Variável Spring Boot (`backend/src/main/resources`)**
- Navegue até o diretório `src/main/resources` do módulo `backend`.
- Renomeie o arquivo `.envTemplate` para `.env` e configure `ENV_ROOT_PASSWORD` igual o definido no `.env` da raíz do projeto.

### 2. Rodar Docker
- A partir da raíz do repositório rodar o seguinte comando:
    ```
      cd backend
      docker-compose up -d
    ```
  
### 3. Inicializar Spring Boot
- De dentro do módulo backend rodar os seguintes comandos:
    ```
      mvn spring-boot:run
    ```
Obs: Ao inicializar a API é inserido os insumos lidos tabela SINAPI automaticamente.
  
---
  
## 🖥️ Como Inicializar o App Desktop
- Para inicializar a interface, a partir da raíz do repositório, com o banco de dados e a API rodando, basta executar os seguintes comandos:
  ```
    cd frontend
    mvn javafx:run
  ```
