# 🤖 AI Layout Generator - Sketchware-Pro

## 📋 Overview

This new feature integrates Groq AI into the Sketchware-Pro XML editor, allowing users to generate Android XML layouts through natural language descriptions. Instead of manually editing XML, you can simply describe the desired layout and the AI will generate the code automatically.

## ✨ Features

### 🎯 Intelligent Layout Generation
- **Natural Language Description**: Describe the layout you want to create
- **Automatic Generation**: The AI generates complete XML code
- **Validation**: Automatic circular dependency checking
- **Perfect Integration**: Directly replaces editor content

### 🌍 Multi-language Support
- **13 Languages**: Portuguese, English, Spanish, French, German, Italian, Japanese, Korean, Chinese (Simplified and Traditional), Russian, Arabic and Hindi
- **Configurable**: Choose the AI response language in settings
- **Smart Prompts**: Language-specific instructions

### 🔧 Technical Features
- **Responsive Layouts**: Generates layouts that follow Android best practices
- **Material Design**: Support for Material Design components
- **Unique IDs**: Automatically generates unique IDs for all elements
- **Accessibility**: Includes accessibility attributes when appropriate

## 🚀 How to Use

### 1. Access the Generator
1. Open the **Direct XML Editor**
2. Click the **🤖 Generate with AI** icon in the toolbar
3. The generation dialog will open

### 2. Describe the Layout
Type a detailed description of the layout you want to create. Examples:

```
• Login screen with email and password fields
• Product list with images and prices
• Registration form with validation
• Dashboard with statistics cards
• Photo gallery in grid
```

### 3. Generate and Apply
1. Click **"Generate Layout"**
2. Wait a few seconds while the AI processes
3. The XML code will be automatically inserted into the editor
4. Review and save the changes

## ⚙️ Configuration

### Prerequisites
1. **Groq API Key**: Get a free key at [console.groq.com/keys](https://console.groq.com/keys)
2. **Configuration**: Go to Settings → Groq AI Settings
3. **Enable**: Activate the feature and enter your API key

### Language Configuration
1. Go to **Settings → Groq AI Settings**
2. Select the desired language in the "Response Language" spinner
3. The AI will respond in the chosen language

## 📱 User Interface

### Generation Dialog
- **Material Design 3**: Modern and intuitive interface
- **Prompt Field**: Text area for layout description
- **Examples**: Prompt suggestions to facilitate use
- **Progress**: Visual indicator during generation
- **Buttons**: Generate and Cancel

### Editor Integration
- **Toolbar Icon**: "Generate with AI" button with 🤖 icon
- **Automatic Replacement**: Generated XML replaces current content
- **Feedback**: Success and error messages
- **Validation**: Verification before saving

## 🔧 Technical Architecture

### Main Classes

#### 1. **GroqLayoutGenerator.java**
- **Location**: `app/src/main/java/pro/sketchware/utility/GroqLayoutGenerator.java`
- **Function**: Communication with Groq API for layout generation
- **Features**:
  - Building specific prompts for layouts
  - HTTP communication with Groq API
  - Parsing and cleaning XML responses
  - Multi-language support

#### 2. **AiLayoutGeneratorDialog.java**
- **Location**: `app/src/main/java/pro/sketchware/dialogs/AiLayoutGeneratorDialog.java`
- **Function**: User interface for layout generation
- **Features**:
  - Material Design 3 dialog
  - Input validation
  - Integration with GroqLayoutGenerator
  - Error handling and feedback

#### 3. **ViewCodeEditorActivity.java** (Modified)
- **Location**: `app/src/main/java/pro/sketchware/activities/editor/view/ViewCodeEditorActivity.java`
- **Modifications**:
  - Added "Generate with AI" button to toolbar
  - Integration with AiLayoutGeneratorDialog
  - Automatic replacement of editor content

### Interface Files

#### 1. **Dialog Layout**
- **File**: `app/src/main/res/layout/dialog_ai_layout_generator.xml`
- **Features**:
  - Material Design 3
  - Text field for prompt
  - Prompt examples
  - Progress indicator
  - Action buttons

#### 2. **AI Icon**
- **File**: `app/src/main/res/drawable/ic_mtrl_ai.xml`
- **Design**: Vector icon representing AI

#### 3. **Localization Strings**
- **File**: `app/src/main/res/values/strings.xml`
- **Additions**: 15 new strings for the feature
- **Categories**: Titles, messages, buttons, errors

## 🎯 Usage Examples

### Example 1: Login Screen
**Prompt**: "Create a login screen with email and password fields, login button and password recovery link"

**Result**: XML layout with:
- Vertical LinearLayout as container
- TextInputLayout for email
- TextInputLayout for password (with passwordToggleEnabled)
- MaterialButton for login
- TextView for recovery link

### Example 2: Product List
**Prompt**: "Create a product list with image, title, price and purchase button"

**Result**: XML layout with:
- RecyclerView or ScrollView
- CardView for each product
- ImageView for product image
- TextView for title and price
- Button for purchase

### Example 3: Dashboard
**Prompt**: "Create a dashboard with statistics cards in 2x2 grid"

**Result**: XML layout with:
- GridLayout or ConstraintLayout
- CardView for each statistic
- Icons and numbers
- Material Design colors

## 🔒 Security and Privacy

### Data Sent
- **Only the prompt**: The layout description is sent to the API
- **No personal data**: No personal information is collected
- **No existing code**: Current code is not sent

### Storage
- **Local**: Settings saved locally on device
- **API Key**: Stored securely in SharedPreferences
- **No cache**: No storage of generated layouts

## 🐛 Error Handling

### Error Scenarios
1. **API not configured**: Dialog to configure
2. **No connection**: Specific error message
3. **Invalid API key**: Error dialog
4. **Timeout**: Timeout message
5. **Empty prompt**: Input validation

### Fallbacks
- **API error**: Keeps current editor content
- **No configuration**: Redirects to settings
- **Network failure**: Informative message

## 🔮 Future Improvements

### Planned Features
1. **Prompt History**: Save previous prompts
2. **Templates**: Pre-defined prompts for common layouts
3. **Real-time Preview**: Preview layout before applying
4. **Smart Editing**: Modify existing layouts
5. **Context Analysis**: Consider current layout

### Optimizations
1. **Response Cache**: Avoid repeated requests
2. **Compression**: Reduce bandwidth usage
3. **Offline Mode**: Basic prompts without internet
4. **Customization**: Custom prompts per user

## 📊 Metrics and Analytics

### Data Collected (Optional)
- **Feature usage**: Number of layouts generated
- **Layout types**: Prompt categorization
- **Success rate**: Generated vs. applied layouts
- **Generation time**: API performance

### Privacy
- **Anonymous**: No personal identification
- **Optional**: User can disable
- **Local**: Data processed locally

## 🤝 Contribution

### How to Contribute
1. **Fork** the repository
2. **Create branch** for feature
3. **Implement** functionality
4. **Test** extensively
5. **Submit** pull request

### Code Standards
- Follow project Java conventions
- Document public methods
- Use localization strings
- Implement error handling
- Test in different languages

## 📄 License

This implementation follows the same license as the Sketchware-Pro project.

---

**Developed with ❤️ for the Sketchware-Pro community**

*Transform your ideas into Android layouts with the power of AI!* 🤖✨ 