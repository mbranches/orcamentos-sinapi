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
* ✅ **Gestão de clientes:** Permite gerenciar clientes, bem como associá-los a um orçamento.
---

## 🛠️ Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-21-blue?logo=java) - Linguagem de programação utilizada.
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-brightgreen?logo=spring) - Framework para criação de APIs REST em Java.
- ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker) - Conteinerização da aplicação.
- ![MySQL](https://img.shields.io/badge/MySQL-black?logo=mysql) - Banco de dados utilizado para persistência de dados.
- ![JavaFX](https://img.shields.io/badge/JavaFX-OpenJFX-orange) - Interface interativa e de fácil utilização.
- ![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven) - Gerencia dependências e automação de builds para projetos Java.

---

## 📸 Demonstração

### Interface do App Desktop

#### **Operações com Orçamentos**
![Apresentação Orçamentos](assets/apresentation-orcamento.gif)

- Criação rápido e fácil de orçamentos
- Atualização de dados de um orçamento
- Navegação entre orçamentos 
- Visualização dos orçamentos criados em ordem decrescente de criação
- Associação opcional com cliente

#### **Operações com Clientes**

![Apresentação Clientes](assets/apresentation-cliente.gif)

- Criação de clientes
- Atualização de dados de clientes
- Definição do tipo de cliente
- Visualização de cliente

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

## 🧪 Testes

A API possui testes automatizados para garantir a qualidade do código e o funcionamento correto da aplicação. A mesma está coberta por dois tipos de testes:

### Testes Unitários
- Testes da camada de serviço, que garantem que a lógica de negócio funcione corretamente.
- Utilizam `JUnit` para estruturação dos testes, `Mockito` para mockar dependências e `AssertJ` para garantir que as funcionalidades funcionem como o esperado.

### Testes de Controller (Camada Web)
- Validam o comportamento dos endpoints REST da aplicação em isolamento.
- Configurados com `@WebMvcTest` inicializam apenas os beans da camada de controller sem carregar todo o contexto do Spring.
- Utilizam `MockMVC` para simular requisições HTTP.
- Testam os status HTTP e os payloads das requisições e respostas.

### Para rodar os testes, a partir da raíz do repositório, execute:
```
   cd backend
   mvn test
```