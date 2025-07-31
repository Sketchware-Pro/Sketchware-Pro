# 🤖 Integração da API Groq no Sketchware-Pro

## 📋 Visão Geral

Esta implementação adiciona funcionalidade de explicação de erros inteligente ao Sketchware-Pro usando a API da Groq. Os usuários podem obter explicações detalhadas e soluções para erros de programação através de IA.

## 🏗️ Arquitetura da Implementação

### Classes Principais

#### 1. **GroqConfig.java**
- **Localização**: `app/src/main/java/pro/sketchware/utility/GroqConfig.java`
- **Função**: Gerenciamento de configurações da API da Groq
- **Funcionalidades**:
  - Salvar/recuperar chave da API
  - Habilitar/desabilitar funcionalidade
  - Verificar disponibilidade da API
  - Suporte a variável de ambiente como fallback
  - Configuração de idioma de resposta da IA
  - Suporte a 13 idiomas diferentes

#### 2. **GroqErrorExplainer.java**
- **Localização**: `app/src/main/java/pro/sketchware/utility/GroqErrorExplainer.java`
- **Função**: Comunicação com a API da Groq
- **Funcionalidades**:
  - Enviar erros para análise da IA
  - Construir prompts personalizados
  - Processar respostas da API
  - Tratamento de erros de rede
  - Suporte a múltiplos idiomas de resposta
  - Instruções específicas por idioma

#### 3. **ErrorHelper.java**
- **Localização**: `app/src/main/java/pro/sketchware/utility/ErrorHelper.java`
- **Função**: Interface para exibição de erros
- **Funcionalidades**:
  - Mostrar dialogs de erro com IA
  - Integração com Material Design
  - Opções de confirmação
  - Fallback para erros originais

#### 4. **ManageGroqActivity.java**
- **Localização**: `app/src/main/java/pro/sketchware/activities/settings/ManageGroqActivity.java`
- **Função**: Interface de configuração da API
- **Funcionalidades**:
  - Configurar chave da API
  - Selecionar idioma de resposta da IA
  - Testar conexão
  - Abrir documentação
  - Validação de entrada

#### 5. **GroqExampleUsage.java**
- **Localização**: `app/src/main/java/pro/sketchware/utility/GroqExampleUsage.java`
- **Função**: Exemplos de uso da funcionalidade
- **Funcionalidades**:
  - Demonstrações de diferentes cenários
  - Integração com tarefas assíncronas
  - Personalização de prompts
  - Tratamento de erros

### Arquivos de Interface

#### 1. **Layout da Configuração**
- **Arquivo**: `app/src/main/res/layout/manage_library_manage_groq.xml`
- **Características**:
  - Material Design 3
  - Switch para habilitar/desabilitar
  - Campo seguro para API key
  - Spinner para seleção de idioma
  - Botões de teste e documentação
  - Informações explicativas

#### 2. **Strings de Localização**
- **Arquivo**: `app/src/main/res/values/strings.xml`
- **Adições**: 55 novas strings para a funcionalidade
- **Idiomas**: Suporte a português e inglês
- **Categorias**: Configuração, mensagens de erro, UI, seleção de idioma

## 🔧 Configuração e Uso

### 1. Configuração Inicial

1. **Acessar Configurações**:
   - Menu principal → Settings → Groq AI Settings

2. **Obter Chave da API**:
   - Visitar [console.groq.com/keys](https://console.groq.com/keys)
   - Criar conta gratuita
   - Gerar chave da API

3. **Configurar no App**:
   - Habilitar switch "Enable Groq AI"
   - Inserir chave da API
   - Selecionar idioma de resposta da IA
   - Testar conexão

### 2. Uso da Funcionalidade

#### Método Simples
```java
// Mostrar erro com explicação da IA
ErrorHelper.showError(context, errorMessage, "Erro", true);
```

#### Método com Confirmação
```java
// Mostrar erro com opção de IA
ErrorHelper.showErrorWithConfirmation(context, errorMessage, "Erro", () -> {
    // Callback quando usuário confirma
});
```

#### Uso Direto da API
```java
GroqErrorExplainer explainer = new GroqErrorExplainer(context);
explainer.explainError(errorMessage, explanation -> {
    // Processar explicação recebida
});
```

## 🌍 Configuração de Idioma

### Idiomas Suportados
A funcionalidade Groq AI suporta 13 idiomas diferentes para respostas da IA:

1. **Português do Brasil** (Padrão) - `pt-BR`
2. **English** - `en`
3. **Español** - `es`
4. **Français** - `fr`
5. **Deutsch** - `de`
6. **Italiano** - `it`
7. **日本語** - `ja`
8. **한국어** - `ko`
9. **中文 (简体)** - `zh-CN`
10. **中文 (繁體)** - `zh-TW`
11. **Русский** - `ru`
12. **العربية** - `ar`
13. **हिन्दी** - `hi`

### Como Configurar o Idioma
1. Acesse **Settings → Groq AI Settings**
2. Na seção "Idioma da Resposta", use o spinner para selecionar o idioma desejado
3. O idioma selecionado será usado para todas as explicações de erro da IA
4. A configuração é salva automaticamente

### Comportamento Padrão
- **Idioma padrão**: Português do Brasil
- **Persistência**: A seleção é mantida entre sessões
- **Fallback**: Se não houver configuração, usa português do Brasil

## 🔒 Segurança e Privacidade

### Armazenamento Seguro
- Chave da API armazenada em SharedPreferences privado
- Suporte a variável de ambiente como alternativa
- Validação de entrada do usuário

### Comunicação Segura
- Requisições HTTPS para API da Groq
- Headers de autorização apropriados
- Timeout configurado para evitar travamentos

### Privacidade
- Dados de erro enviados apenas para API da Groq
- Não há coleta de dados pessoais
- Usuário controla quando usar a funcionalidade

## 🌐 Integração com o Sistema

### Pontos de Integração
1. **Sistema de Compilação**: Erros de compilação podem ser explicados
2. **Runtime Errors**: Erros durante execução do app
3. **Configurações**: Interface dedicada para configuração
4. **Logs de Erro**: Integração com sistema de logs existente

### Menu de Configurações
- Adicionado item "Groq AI Settings" no menu principal
- Ícone: `ic_mtrl_settings_applications`
- Descrição: "Configure Groq AI for intelligent error explanations"

## 📱 Interface do Usuário

### Tela de Configuração
- **Design**: Material Design 3
- **Elementos**:
  - Logo da Groq com emoji 🤖
  - Switch para habilitar/desabilitar
  - Campo de texto seguro para API key
  - Spinner para seleção de idioma de resposta
  - Botão "Open Documentation"
  - Botão "Test Connection"
  - Seção informativa

### Dialogs de Erro
- **Tipos**:
  - Dialog simples (sem IA)
  - Dialog com explicação da IA
  - Dialog de confirmação com opção de IA
- **Características**:
  - Loading state durante análise
  - Botão "Show Original" para ver erro original
  - Mensagens em português/inglês

## 🚀 Funcionalidades Avançadas

### 1. Contexto Personalizado
```java
String contexto = "Este erro ocorreu durante a compilação de um projeto Android.";
explainer.explainError(errorMessage, contexto, callback);
```

### 2. Verificação de Disponibilidade
```java
if (ErrorHelper.isGroqAvailable(context)) {
    // Usar explicação da IA
} else {
    // Mostrar erro original
}
```

### 3. Tratamento de Erros
- Fallback automático para erro original
- Mensagens informativas quando API não disponível
- Logs detalhados para debugging

## 📊 Exemplos de Uso

### Exemplo 1: Erro de Compilação
```java
try {
    // Código que pode falhar
} catch (Exception e) {
    ErrorHelper.showError(this, e.getMessage(), "Erro de Compilação", true);
}
```

### Exemplo 2: Erro de Runtime
```java
if (ErrorHelper.isGroqAvailable(this)) {
    ErrorHelper.showError(this, errorMessage, "Erro", true);
} else {
    ErrorHelper.showError(this, errorMessage, "Erro");
}
```

### Exemplo 3: Integração Assíncrona
```java
new Thread(() -> {
    try {
        // Tarefa assíncrona
    } catch (Exception e) {
        runOnUiThread(() -> {
            ErrorHelper.showError(this, e.getMessage(), "Erro na Tarefa", true);
        });
    }
}).start();
```

### Exemplo 4: Uso com Diferentes Idiomas
```java
// A IA responderá no idioma configurado pelo usuário
// Se o usuário configurou inglês, a resposta será em inglês
// Se configurou português, a resposta será em português
ErrorHelper.showError(this, errorMessage, "Erro", true);
```

## 🔧 Configuração Técnica

### Dependências
- **OkHttp**: Para requisições HTTP
- **Gson**: Para parsing JSON
- **Material Design**: Para interface

### Permissões
- `INTERNET`: Para comunicação com API
- `ACCESS_NETWORK_STATE`: Para verificar conectividade

### Configurações
- **API URL**: `https://api.groq.com/openai/v1/chat/completions`
- **Modelo**: `llama-3.3-70b-versatile`
- **Timeout**: Configurado no OkHttpClient

## 🐛 Tratamento de Erros

### Cenários de Erro
1. **API não configurada**: Mensagem informativa
2. **Sem conexão**: Fallback para erro original
3. **API key inválida**: Dialog de erro específico
4. **Timeout**: Mensagem de timeout
5. **Erro de parsing**: Fallback para erro original

### Logs
- Logs detalhados para debugging
- Tags específicos para cada classe
- Informações de erro sem dados sensíveis

## 🔮 Melhorias Futuras

### Funcionalidades Planejadas
1. **Histórico de erros**: Salvar explicações anteriores
2. **Personalização de prompts**: Permitir prompts customizados
3. **Integração com mais APIs**: Suporte a outras IAs
4. **Análise de código**: Sugestões de melhoria de código
5. **Detecção automática de idioma**: Detectar idioma do erro automaticamente

### Otimizações
1. **Cache de explicações**: Evitar requisições repetidas
2. **Compressão de dados**: Reduzir uso de banda
3. **Análise offline**: Explicações pré-definidas
4. **Modo econômico**: Limitar uso da API

## 📝 Documentação da API

### Endpoints Utilizados
- **POST** `/openai/v1/chat/completions`
- **Headers**: `Content-Type: application/json`, `Authorization: Bearer {api_key}`
- **Body**: JSON com modelo, mensagens e parâmetros

### Estrutura da Resposta
```json
{
  "choices": [
    {
      "message": {
        "content": "Explicação do erro..."
      }
    }
  ]
}
```

### Limitações
- **Rate limiting**: Respeitar limites da API
- **Tamanho do prompt**: Máximo de tokens
- **Timeout**: 30 segundos por requisição

## 🤝 Contribuição

### Como Contribuir
1. Fork do repositório
2. Criar branch para feature
3. Implementar funcionalidade
4. Testar extensivamente
5. Submeter pull request

### Padrões de Código
- Seguir convenções Java do projeto
- Documentar métodos públicos
- Usar strings de localização
- Implementar tratamento de erros

## 📄 Licença

Esta implementação segue a mesma licença do projeto Sketchware-Pro.

---

**Desenvolvido com ❤️ para a comunidade Sketchware-Pro** 