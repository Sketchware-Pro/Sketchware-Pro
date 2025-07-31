# 🤖 Groq API Integration in Sketchware-Pro

## 📋 Overview

This implementation adds intelligent error explanation functionality to Sketchware-Pro using the Groq API. Users can get detailed explanations and solutions for programming errors through AI.

## 🏗️ Implementation Architecture

### Main Classes

#### 1. **GroqConfig.java**
- **Location**: `app/src/main/java/pro/sketchware/utility/GroqConfig.java`
- **Function**: Groq API configuration management
- **Features**:
  - Save/retrieve API key
  - Enable/disable functionality
  - Check API availability
  - Environment variable fallback support
  - AI response language configuration
  - Support for 13 different languages

#### 2. **GroqErrorExplainer.java**
- **Location**: `app/src/main/java/pro/sketchware/utility/GroqErrorExplainer.java`
- **Function**: Communication with Groq API
- **Features**:
  - Send errors for AI analysis
  - Build custom prompts
  - Process API responses
  - Network error handling
  - Multi-language response support
  - Language-specific instructions

#### 3. **ErrorHelper.java**
- **Location**: `app/src/main/java/pro/sketchware/utility/ErrorHelper.java`
- **Function**: Error display interface with Markdown formatting
- **Features**:
  - Show AI error dialogs with Markwon formatting
  - Material Design integration
  - Confirmation options
  - Fallback to original errors
  - Markdown rendering for AI responses
  - Custom TextView with selectable text
  - Toggle between formatted and plain text views

#### 4. **ManageGroqActivity.java**
- **Location**: `app/src/main/java/pro/sketchware/activities/settings/ManageGroqActivity.java`
- **Function**: API configuration interface
- **Features**:
  - Configure API key
  - Select AI response language
  - Test connection
  - Open documentation
  - Input validation

#### 5. **GroqExampleUsage.java**
- **Location**: `app/src/main/java/pro/sketchware/utility/GroqExampleUsage.java`
- **Function**: Feature usage examples
- **Features**:
  - Demonstrations of different scenarios
  - Async task integration
  - Prompt customization
  - Error handling

### Interface Files

#### 1. **Configuration Layout**
- **File**: `app/src/main/res/layout/manage_library_manage_groq.xml`
- **Features**:
  - Material Design 3
  - Switch to enable/disable
  - Secure field for API key
  - Spinner for language selection
  - Test and documentation buttons
  - Explanatory information

#### 2. **Localization Strings**
- **File**: `app/src/main/res/values/strings.xml`
- **Additions**: 55 new strings for the feature
- **Languages**: Support for Portuguese and English
- **Categories**: Configuration, error messages, UI, language selection

## 🔧 Configuration and Usage

### 📚 Markdown Integration with Markwon

The AI responses are now formatted using the **Markwon** library (`io.noties.markwon:core:4.6.2`) to provide rich text formatting:

#### Features:
- **Markdown Rendering**: AI responses are rendered with proper markdown formatting
- **Rich Text Display**: Headers, lists, code blocks, and emphasis are properly displayed
- **Selectable Text**: Users can select and copy formatted content
- **Fallback Support**: If markdown rendering fails, content is displayed as plain text
- **HTML Support**: Includes HTML plugin for enhanced formatting

#### Implementation Details:
```java
// Initialize Markwon with HTML plugin
private static Markwon getMarkwon() {
    if (markwon == null) {
        markwon = Markwon.builder()
                .usePlugin(HtmlPlugin.create())
                .build();
    }
    return markwon;
}

// Render markdown content
private static Spanned renderMarkdown(String markdownContent) {
    try {
        return getMarkwon().toMarkdown(markdownContent);
    } catch (Exception e) {
        // Fallback to plain text
        return new SpannedString(markdownContent);
    }
}
```

#### AI Prompt Enhancement:
The AI is instructed to format responses using markdown syntax:
- **Headers** (`## 📋 Explanation`)
- **Lists** (`- Item 1`, `1. Step 1`)
- **Code blocks** (```code```)
- **Emphasis** (`**bold**`, `*italic*`)
- **Emojis** for visual appeal

### 1. Initial Configuration

1. **Access Settings**:
   - Main menu → Settings → Groq AI Settings

2. **Get API Key**:
   - Visit [console.groq.com/keys](https://console.groq.com/keys)
   - Create free account
   - Generate API key

3. **Configure in App**:
   - Enable "Enable Groq AI" switch
   - Enter API key
   - Select AI response language
   - Test connection

### 2. Feature Usage

#### Simple Method with Markdown Formatting
```java
// Basic usage with automatic markdown formatting
ErrorHelper.showError(context, errorMessage, "Error Analysis", true);

// Show custom markdown content
ErrorHelper.showAIExplanation(context, markdownContent, "Custom Title");

// Advanced usage with context
GroqErrorExplainer explainer = new GroqErrorExplainer(context);
explainer.explainError(errorMessage, additionalContext, explanation -> {
    // The explanation will be automatically formatted with markdown
    ErrorHelper.showAIExplanation(context, explanation, "AI Explanation");
});
```
// Show error with AI explanation
ErrorHelper.showError(context, errorMessage, "Error", true);
```

#### Confirmation Method
```java
// Show error with AI option
ErrorHelper.showErrorWithConfirmation(context, errorMessage, "Error", () -> {
    // Callback when user confirms
});
```

#### Direct API Usage
```java
GroqErrorExplainer explainer = new GroqErrorExplainer(context);
explainer.explainError(errorMessage, explanation -> {
    // Process received explanation
});
```

## 🌍 Language Configuration

### Supported Languages
The Groq AI feature supports 13 different languages for AI responses:

1. **Portuguese (Brazil)** (Default) - `pt-BR`
2. **English** - `en`
3. **Spanish** - `es`
4. **French** - `fr`
5. **German** - `de`
6. **Italian** - `it`
7. **Japanese** - `ja`
8. **Korean** - `ko`
9. **Chinese (Simplified)** - `zh-CN`
10. **Chinese (Traditional)** - `zh-TW`
11. **Russian** - `ru`
12. **Arabic** - `ar`
13. **Hindi** - `hi`

### How to Configure Language
1. Go to **Settings → Groq AI Settings**
2. In the "Response Language" section, use the spinner to select the desired language
3. The selected language will be used for all AI error explanations
4. The configuration is saved automatically

### Default Behavior
- **Default language**: Portuguese (Brazil)
- **Persistence**: Selection is maintained between sessions
- **Fallback**: If no configuration, uses Portuguese (Brazil)

## 🔒 Security and Privacy

### Secure Storage
- API key stored in private SharedPreferences
- Environment variable support as alternative
- User input validation

### Secure Communication
- HTTPS requests to Groq API
- Appropriate authorization headers
- Configured timeout to prevent hangs

### Privacy
- Error data sent only to Groq API
- No personal data collection
- User controls when to use the feature

## 🌐 System Integration

### Integration Points
1. **Compilation System**: Compilation errors can be explained
2. **Runtime Errors**: Errors during app execution
3. **Settings**: Dedicated interface for configuration
4. **Error Logs**: Integration with existing logging system

### Settings Menu
- Added "Groq AI Settings" item in main menu
- Icon: `ic_mtrl_settings_applications`
- Description: "Configure Groq AI for intelligent error explanations"

## 📱 User Interface

### Configuration Screen
- **Design**: Material Design 3
- **Elements**:
  - Groq logo with emoji 🤖
  - Switch to enable/disable
  - Secure text field for API key
  - Spinner for response language selection
  - "Open Documentation" button
  - "Test Connection" button
  - Informative section

### Error Dialogs
- **Types**:
  - Simple dialog (without AI)
  - Dialog with AI explanation
  - Confirmation dialog with AI option
- **Features**:
  - Loading state during analysis
  - "Show Original" button to see original error
  - Messages in Portuguese/English

## 🚀 Advanced Features

### 1. Custom Context
```java
String context = "This error occurred during Android project compilation.";
explainer.explainError(errorMessage, context, callback);
```

### 2. Availability Check
```java
if (ErrorHelper.isGroqAvailable(context)) {
    // Use AI explanation
} else {
    // Show original error
}
```

### 3. Error Handling
- Automatic fallback to original error
- Informative messages when API not available
- Detailed logs for debugging

## 📊 Usage Examples

### Example 1: Compilation Error
```java
try {
    // Code that may fail
} catch (Exception e) {
    ErrorHelper.showError(this, e.getMessage(), "Compilation Error", true);
}
```

### Example 2: Runtime Error
```java
if (ErrorHelper.isGroqAvailable(this)) {
    ErrorHelper.showError(this, errorMessage, "Error", true);
} else {
    ErrorHelper.showError(this, errorMessage, "Error");
}
```

### Example 3: Async Integration
```java
new Thread(() -> {
    try {
        // Async task
    } catch (Exception e) {
        runOnUiThread(() -> {
            ErrorHelper.showError(this, e.getMessage(), "Task Error", true);
        });
    }
}).start();
```

### Example 4: Usage with Different Languages
```java
// The AI will respond in the language configured by the user
// If the user configured English, the response will be in English
// If configured Portuguese, the response will be in Portuguese
ErrorHelper.showError(this, errorMessage, "Error", true);
```

## 🔧 Technical Configuration

### Dependencies
- **OkHttp**: For HTTP requests
- **Gson**: For JSON parsing
- **Material Design**: For interface

### Permissions
- `INTERNET`: For API communication
- `ACCESS_NETWORK_STATE`: To check connectivity

### Settings
- **API URL**: `https://api.groq.com/openai/v1/chat/completions`
- **Model**: `llama-3.3-70b-versatile`
- **Timeout**: Configured in OkHttpClient

## 🐛 Error Handling

### Error Scenarios
1. **API not configured**: Informative message
2. **No connection**: Fallback to original error
3. **Invalid API key**: Specific error dialog
4. **Timeout**: Timeout message
5. **Parsing error**: Fallback to original error

### Logs
- Detailed logs for debugging
- Specific tags for each class
- Error information without sensitive data

## 🔮 Future Improvements

### Planned Features
1. **Error history**: Save previous explanations
2. **Prompt customization**: Allow custom prompts
3. **Integration with more APIs**: Support for other AIs
4. **Code analysis**: Code improvement suggestions
5. **Automatic language detection**: Automatically detect error language

### Optimizations
1. **Explanation cache**: Avoid repeated requests
2. **Data compression**: Reduce bandwidth usage
3. **Offline analysis**: Pre-defined explanations
4. **Economy mode**: Limit API usage

## 📝 API Documentation

### Used Endpoints
- **POST** `/openai/v1/chat/completions`
- **Headers**: `Content-Type: application/json`, `Authorization: Bearer {api_key}`
- **Body**: JSON with model, messages and parameters

### Response Structure
```json
{
  "choices": [
    {
      "message": {
        "content": "Error explanation..."
      }
    }
  ]
}
```

### Limitations
- **Rate limiting**: Respect API limits
- **Prompt size**: Maximum tokens
- **Timeout**: 30 seconds per request

## 🤝 Contribution

### How to Contribute
1. Fork the repository
2. Create branch for feature
3. Implement functionality
4. Test extensively
5. Submit pull request

### Code Standards
- Follow project Java conventions
- Document public methods
- Use localization strings
- Implement error handling

## 📄 License

This implementation follows the same license as the Sketchware-Pro project.

---

**Developed with ❤️ for the Sketchware-Pro community** 