# 🤖 Gerador de Layout com IA - Sketchware-Pro

## 📋 Visão Geral

Esta nova funcionalidade integra a Groq AI ao editor XML do Sketchware-Pro, permitindo que os usuários gerem layouts Android XML através de descrições em linguagem natural. Em vez de editar XML manualmente, você pode simplesmente descrever o layout desejado e a IA irá gerar o código automaticamente.

## ✨ Funcionalidades

### 🎯 Geração Inteligente de Layouts
- **Descrição em Linguagem Natural**: Descreva o layout que você quer criar
- **Geração Automática**: A IA gera o código XML completo
- **Validação**: Verificação automática de dependências circulares
- **Integração Perfeita**: Substitui diretamente o conteúdo do editor

### 🌍 Suporte Multi-idioma
- **13 Idiomas**: Português, Inglês, Espanhol, Francês, Alemão, Italiano, Japonês, Coreano, Chinês (Simplificado e Tradicional), Russo, Árabe e Hindi
- **Configurável**: Escolha o idioma de resposta da IA nas configurações
- **Prompts Inteligentes**: Instruções específicas por idioma

### 🔧 Recursos Técnicos
- **Layouts Responsivos**: Gera layouts que seguem as melhores práticas Android
- **Material Design**: Suporte a componentes do Material Design
- **IDs Únicos**: Gera automaticamente IDs únicos para todos os elementos
- **Acessibilidade**: Inclui atributos de acessibilidade quando apropriado

## 🚀 Como Usar

### 1. Acessar o Gerador
1. Abra o **Direct XML Editor** (Editor XML Direto)
2. Clique no ícone **🤖 Generate with AI** na barra de ferramentas
3. O dialog de geração será aberto

### 2. Descrever o Layout
Digite uma descrição detalhada do layout que você deseja criar. Exemplos:

```
• Tela de login com campos de email e senha
• Lista de produtos com imagens e preços
• Formulário de cadastro com validação
• Dashboard com cards de estatísticas
• Galeria de fotos em grid
```

### 3. Gerar e Aplicar
1. Clique em **"Gerar Layout"**
2. Aguarde alguns segundos enquanto a IA processa
3. O código XML será automaticamente inserido no editor
4. Revise e salve as alterações

## ⚙️ Configuração

### Pré-requisitos
1. **API Key da Groq**: Obtenha uma chave gratuita em [console.groq.com/keys](https://console.groq.com/keys)
2. **Configuração**: Acesse Settings → Groq AI Settings
3. **Habilitar**: Ative a funcionalidade e insira sua API key

### Configuração de Idioma
1. Acesse **Settings → Groq AI Settings**
2. Selecione o idioma desejado no spinner "Idioma da Resposta"
3. A IA responderá no idioma escolhido

## 📱 Interface do Usuário

### Dialog de Geração
- **Design Material 3**: Interface moderna e intuitiva
- **Campo de Prompt**: Área de texto para descrição do layout
- **Exemplos**: Sugestões de prompts para facilitar o uso
- **Progresso**: Indicador visual durante a geração
- **Botões**: Gerar e Cancelar

### Integração no Editor
- **Ícone na Toolbar**: Botão "Generate with AI" com ícone 🤖
- **Substituição Automática**: O XML gerado substitui o conteúdo atual
- **Feedback**: Mensagens de sucesso e erro
- **Validação**: Verificação antes de salvar

## 🔧 Arquitetura Técnica

### Classes Principais

#### 1. **GroqLayoutGenerator.java**
- **Localização**: `app/src/main/java/pro/sketchware/utility/GroqLayoutGenerator.java`
- **Função**: Comunicação com a API da Groq para geração de layouts
- **Funcionalidades**:
  - Construção de prompts específicos para layouts
  - Comunicação HTTP com a API da Groq
  - Parsing e limpeza das respostas XML
  - Suporte a múltiplos idiomas

#### 2. **AiLayoutGeneratorDialog.java**
- **Localização**: `app/src/main/java/pro/sketchware/dialogs/AiLayoutGeneratorDialog.java`
- **Função**: Interface do usuário para geração de layouts
- **Funcionalidades**:
  - Dialog Material Design 3
  - Validação de entrada
  - Integração com GroqLayoutGenerator
  - Tratamento de erros e feedback

#### 3. **ViewCodeEditorActivity.java** (Modificado)
- **Localização**: `app/src/main/java/pro/sketchware/activities/editor/view/ViewCodeEditorActivity.java`
- **Modificações**:
  - Adicionado botão "Generate with AI" na toolbar
  - Integração com AiLayoutGeneratorDialog
  - Substituição automática do conteúdo do editor

### Arquivos de Interface

#### 1. **Layout do Dialog**
- **Arquivo**: `app/src/main/res/layout/dialog_ai_layout_generator.xml`
- **Características**:
  - Material Design 3
  - Campo de texto para prompt
  - Exemplos de prompts
  - Indicador de progresso
  - Botões de ação

#### 2. **Ícone da IA**
- **Arquivo**: `app/src/main/res/drawable/ic_mtrl_ai.xml`
- **Design**: Ícone vetorial representando IA

#### 3. **Strings de Localização**
- **Arquivo**: `app/src/main/res/values/strings.xml`
- **Adições**: 15 novas strings para a funcionalidade
- **Categorias**: Títulos, mensagens, botões, erros

## 🎯 Exemplos de Uso

### Exemplo 1: Tela de Login
**Prompt**: "Crie uma tela de login com campos de email e senha, botão de login e link para recuperar senha"

**Resultado**: Layout XML com:
- LinearLayout vertical como container
- TextInputLayout para email
- TextInputLayout para senha (com passwordToggleEnabled)
- MaterialButton para login
- TextView para link de recuperação

### Exemplo 2: Lista de Produtos
**Prompt**: "Crie uma lista de produtos com imagem, título, preço e botão de compra"

**Resultado**: Layout XML com:
- RecyclerView ou ScrollView
- CardView para cada produto
- ImageView para imagem do produto
- TextView para título e preço
- Button para compra

### Exemplo 3: Dashboard
**Prompt**: "Crie um dashboard com cards de estatísticas em grid 2x2"

**Resultado**: Layout XML com:
- GridLayout ou ConstraintLayout
- CardView para cada estatística
- Ícones e números
- Cores do Material Design

## 🔒 Segurança e Privacidade

### Dados Enviados
- **Apenas o prompt**: A descrição do layout é enviada para a API
- **Sem dados pessoais**: Nenhuma informação pessoal é coletada
- **Sem código existente**: O código atual não é enviado

### Armazenamento
- **Local**: Configurações salvas localmente no dispositivo
- **API Key**: Armazenada de forma segura em SharedPreferences
- **Sem cache**: Não há armazenamento de layouts gerados

## 🐛 Tratamento de Erros

### Cenários de Erro
1. **API não configurada**: Dialog para configurar
2. **Sem conexão**: Mensagem de erro específica
3. **API key inválida**: Dialog de erro
4. **Timeout**: Mensagem de timeout
5. **Prompt vazio**: Validação de entrada

### Fallbacks
- **Erro de API**: Mantém o conteúdo atual do editor
- **Sem configuração**: Redireciona para configurações
- **Falha de rede**: Mensagem informativa

## 🔮 Melhorias Futuras

### Funcionalidades Planejadas
1. **Histórico de Prompts**: Salvar prompts anteriores
2. **Templates**: Prompts pré-definidos para layouts comuns
3. **Preview em Tempo Real**: Visualizar layout antes de aplicar
4. **Edição Inteligente**: Modificar layouts existentes
5. **Análise de Contexto**: Considerar o layout atual

### Otimizações
1. **Cache de Respostas**: Evitar requisições repetidas
2. **Compressão**: Reduzir uso de banda
3. **Modo Offline**: Prompts básicos sem internet
4. **Personalização**: Prompts customizados por usuário

## 📊 Métricas e Analytics

### Dados Coletados (Opcional)
- **Uso da funcionalidade**: Quantidade de layouts gerados
- **Tipos de layouts**: Categorização dos prompts
- **Taxa de sucesso**: Layouts gerados vs. aplicados
- **Tempo de geração**: Performance da API

### Privacidade
- **Anônimo**: Sem identificação pessoal
- **Opcional**: Usuário pode desabilitar
- **Local**: Dados processados localmente

## 🤝 Contribuição

### Como Contribuir
1. **Fork** do repositório
2. **Criar branch** para feature
3. **Implementar** funcionalidade
4. **Testar** extensivamente
5. **Submeter** pull request

### Padrões de Código
- Seguir convenções Java do projeto
- Documentar métodos públicos
- Usar strings de localização
- Implementar tratamento de erros
- Testar em diferentes idiomas

## 📄 Licença

Esta implementação segue a mesma licença do projeto Sketchware-Pro.

---

**Desenvolvido com ❤️ para a comunidade Sketchware-Pro**

*Transforme suas ideias em layouts Android com o poder da IA!* 🤖✨ 