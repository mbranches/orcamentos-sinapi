## Orçamentos Sinapi

---

Sistema desktop para criação e gestão de orçamentos de insumos da construção civil, com base na tabela SINAPI.

Este repositório contém o **Backend** e o **Frontend** do projeto.

---

## 📁 Estrutura do Repositório
*  **orcamentos-sinapi-api:** Módulo onde está localizada API RestFul, desenvolvida com **Spring Boot**.
*  **orcamentos-sinapi-application:**  Módulo responsável pelo Front-End, desenvolvido com JavaFX.
*  **orcamentos-sinapi-script-insercao-insumos:** Módulo que contém um script que faz requisição POST na API com insumos lidos da tabela SINAPI.

---

## 🎯 Funcionalidades

* ✅ **Cadastro e Gestão de Insumos:** Permite o cadastro, edição e exclusão de insumos utilizados na construção civil, com base na tabela SINAPI.
* ✅ **Criação de Orçamentos:** Criação de orçamentos com cálculo automático do custo total, baseado nos insumos selecionados.
* ✅ **Visualização de Orçamentos:** Visualização de orçamentos com detalhes dos insumos e custos envolvidos.
---

## 🛠️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-orange) - Linguagem de programação utilizada.
- ![Spring Boot](https://img.shields.io/badge/Spring-Boot-grey) - Framework para criação de APIs REST em Java.
- ![Docker](https://img.shields.io/badge/Docker-blue) - Conteinerização da aplicação.
- ![JavaFX](https://img.shields.io/badge/JavaFX-orange) - Interface interativa e de fácil utilização.

---

## 🚀  Como Rodar a API

### 1. Configuração das Variáveis de Ambiente

Antes de iniciar a aplicação, é necessário configurar as variáveis de ambiente para garantir que todos os módulos funcionem corretamente.

#### a. **Variáveis Docker (`orcamentos-sinapi-api`)**
- Navegue até o diretório do módulo `orcamentos-sinapi-api`.
- Renomeie o arquivo `.envTemplate` para `.env` e preencha as variáveis com os valores apropriados para o seu ambiente de desenvolvimento.

#### b. **Variáveis Spring Boot (`src/main/resources`)**
- Navegue até o diretório `src/main/resources` do módulo `orcamentos-sinapi-api`.
- Copie o arquivo `.envTemplate` para `.env` e configure as variáveis conforme necessário para o Spring Boot.

### 2. Rodar Docker
- A partir da raíz do repositório rodar o seguinte comando:
    ```
      cd orcamentos-sinapi-api
      docker-compose up -d
    ```
  
### 3. Inicializar Spring Boot
- Da raíz do projeto rodar os seguintes comandos:
    ```
      cd orcamentos-sinapi-api
      mvn spring-boot:run
    ```

### 4. Rode o Script de Inserção de Insumos
- Para uma melhor experiência é fundamental ter o banco de dados povoado. Para isso, a partir da raíz do repositório, com a **API inicializada** rode os seguintes comandos:
    ```
      cd orcamentos-sinapi-script-insercao-insumos
      mvn spring-boot:run
    ```
  
---
  
## 🖥️ Como Inicializar o App Desktop
- Para inicializar a interface, a partir da raíz do repositório, basta executar os seguintes comandos:
  ```
    cd orcamentos-sinapi-application
    mvn javafx:run
  ```
