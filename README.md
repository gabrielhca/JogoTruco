# Framework de Jogos de Cartas: Da Refatoração ao Design

> "Navegar sobre o caos com estratégia."

Este repositório documenta a jornada de transformação de um sistema legado de Truco em um **Framework de Jogos de Cartas** reutilizável, aplicando Princípios de Projeto (SOLID) e Padrões de Projeto (Design Patterns).

## O Objetivo
O projeto consiste na refatoração de uma implementação monolítica e intencionalmente problemática (com *bad smells*) do jogo de Truco, visando desacoplar a lógica de regras da interface e da mecânica, resultando na criação do pacote `cartas.framework`.

Para provar a extensibilidade do framework criado, serão implementados dois jogos distintos:
1.  **Truco** (Reimplementação limpa sobre o framework)
2.  **Uno** (Novo jogo para validar a reutilização)

## Estado Atual: Legacy Code
**Nota:** O código presente no *commit* inicial trata-se de uma base de código fornecida academicamente para a disciplina de **Princípios e Padrões de Projeto** da UFU.

Esta versão base:
* Contém violações propositais de design (Acoplamento alto, "God Classes", Hardcoding).
* Funciona apenas para Truco.
* Serve como o **"Caos Inicial"** sobre o qual aplicaremos a arquitetura.

**Não reflete a qualidade final esperada do projeto.**

## Roadmap de Evolução
- [x] Importação do Código Legado (Base de Estudos).
- [ ] **Fase 1: Saneamento:** Identificação de *Bad Smells* e documentação.
- [ ] **Fase 2: Extração do Framework:** Criação de abstrações para `Baralho`, `Mao`, `Jogador` e `Turno`.
- [ ] **Fase 3: Implementação do Truco:** Adaptação das regras de Truco ao novo Framework.
- [ ] **Fase 4: Implementação do Uno:** Implementação do jogo Uno utilizando exclusivamente o Framework.
- [ ] **Fase 5: Expansão:** Adição de persistência, menus e melhorias na interface gráfica.

## Tecnologias
* **Linguagem:** Java (JDK 17+)
* **Interface:** Java Swing
* **Conceitos:** OOP, Design Patterns (Strategy, Observer, Template Method, Factory), Refactoring.

## Autores
Projeto desenvolvido em modelo de **co-autoria integral** e *colaboração ativa* como requisito avaliativo na Universidade Federal de Uberlândia (UFU).

* **[Gabriel Amorim](https://github.com/gabrielhca)**
* **[Gabriel Moutinho](https://github.com/IMoutinho)**

---
