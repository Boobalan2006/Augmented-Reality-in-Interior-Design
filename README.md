# ARID - Augmented Reality Furniture Visualization

A modern Android mobile application that brings furniture shopping to life with Augmented Reality (AR) technology. Visualize furniture pieces in your own space before making a purchase decision.

## 🎯 Overview

FurniCraft AR is an innovative furniture shopping application that leverages cutting-edge AR technology to help users visualize furniture items in their real environment. Built with Jetpack Compose and modern Android architecture, it provides a seamless and intuitive user experience for exploring and interacting with 3D furniture models.

## ✨ Features

### Core Features
- **Augmented Reality Visualization**: View 3D furniture models in real-time using your device's camera
- **Extensive Furniture Catalog**: Browse through multiple furniture categories including:
  - Tables
  - Chairs
  - Beds
  - Sofas
  - Desks
  - Curtains
- **Real-time Search**: Quickly find furniture items with instant search functionality
- **Favorites System**: Save your favorite items for quick access later
- **Product Details**: Comprehensive information about each furniture piece

### User Interface
- **Navigation Drawer**: Easy access to app sections (Favorites, Settings, Theme, About)
- **Material Design 3**: Modern, responsive UI with smooth animations
- **Dark/Light Theme Support**: Toggle between dark and light modes
- **Smooth Animations**: Interactive button animations and transitions for enhanced UX
- **Responsive Design**: Optimized layouts for various screen sizes

### AR Features
- **3D Model Rotation**: Rotate furniture models to view from different angles
- **Color Customization**: Change furniture colors to match your interior
- **Dynamic Scaling**: Adjust furniture size to fit your space
- **Model Sharing**: Share AR experiences with others
- **Reset Functionality**: Quickly reset model transformations

### Settings & Personalization
- **Theme Customization**: Dark/Light mode toggle
- **Notification Control**: Enable/disable app notifications
- **Animation Preferences**: Toggle UI animations on/off
- **App Information**: View version, features, and developer credits

## 🛠️ Tech Stack

### Architecture & Framework
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Navigation Compose

### Data & Storage
- **Local Database**: Room Database
- **Data Persistence**: DataStore Preferences
- **API Communication**: Retrofit + OkHttp3
- **JSON Serialization**: Gson

### AR & 3D
- **AR Engine**: ARSceneView
- **3D Models**: Sketchfab API Integration
- **Model Rendering**: Real-time 3D visualization

### UI & Animation
- **Material Design**: Material 3 Components
- **Image Loading**: Coil
- **Loading States**: Shimmer animations
- **Lottie**: Advanced animations
- **Palette**: Dynamic color extraction

### Other Libraries
- **Coroutines**: Asynchronous programming
- **Paging**: Efficient list pagination
- **Timber**: Logging
- **Kotlin Reflect**: Runtime reflection utilities

## 📋 Requirements

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Compile SDK**: API 34
- **Java Version**: 17
- **Kotlin Compiler Extension**: 1.5.11

### Device Requirements
- Device with AR Core support (for AR features)
- Camera permission enabled
- Minimum 2GB RAM recommended

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- JDK 17 or higher
- Android SDK with API 34

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Augmented-Reality-in-Interior-Design.git
   cd Augmented-Reality-in-Interior-Design
   ```

2. **Set up API Keys**
   - Create `apikeys.properties` file in the project root
   - Add your Sketchfab API key:
     ```properties
     SKETCHFAB_API_KEY=your_api_key_here
     ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
   - Connect an Android device or start an emulator
   - Run: `./gradlew installDebug` or use Android Studio's Run button

## 📁 Project Structure

```
Augmented-Reality-in-Interior-Design/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/simform/ar/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/          # Room database, entities, DAOs
│   │   │   │   │   ├── remote/         # API services (Sketchfab)
│   │   │   │   │   └── repository/     # Data repositories
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/          # Domain models
│   │   │   │   │   └── usecase/        # Business logic
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/        # Compose screens
│   │   │   │   │   ├── components/     # Reusable UI components
│   │   │   │   │   ├── theme/          # Material Design 3 theme
│   │   │   │   │   └── viewmodel/      # ViewModels
│   │   │   │   ├── util/               # Utility functions
│   │   │   │   └── ARApplication.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/           # Images and icons
│   │   │   │   ├── values/             # Strings, colors, dimensions
│   │   │   │   └── xml/                # Configuration files
│   │   │   └── AndroidManifest.xml
│   │   └── test/                       # Unit tests
│   └── build.gradle.kts
├── buildSrc/                           # Build configuration
├── gradle/                             # Gradle wrapper
├── settings.gradle.kts
└── README.md
```

## 🎨 Key Components

### Screens
- **Products Screen**: Main catalog with category filtering and search
- **AR View Screen**: Interactive AR furniture visualization
- **Favorites Screen**: Collection of saved favorite items
- **Settings Screen**: User preferences and app configuration
- **About Screen**: App information and credits

### Database Schema
- **ProductEntity**: Furniture product information
- **FavoriteEntity**: User's favorite items
- **Relationships**: Proper foreign key constraints

### API Integration
- **Sketchfab API**: Fetches 3D furniture models
- **Retrofit Service**: Handles API requests and responses
- **Error Handling**: Graceful error management and user feedback

## 🔐 Security & Permissions

### Required Permissions
- `android.permission.ACCESS_NETWORK_STATE`: For internet connectivity checks
- Camera access (implicit for AR functionality)

### API Key Management
- Store API keys in `apikeys.properties` (not in version control)
- Use BuildConfig to access keys securely
- Never hardcode sensitive information

## 🧪 Testing

The project includes testing infrastructure for:
- Unit tests with JUnit
- UI tests with Espresso and Compose Test
- Instrumented tests with AndroidJUnitRunner

Run tests with:
```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests
```

## 📦 Build Configuration

### Build Types
- **Debug**: Development build with full logging
- **Release**: Optimized release build with ProGuard

### ABI Splits
The app generates separate APKs for:
- x86
- x86_64
- armeabi-v7a
- arm64-v8a
- Universal APK (includes all ABIs)

## 🎬 Animation System

The app features comprehensive animations:
- **Button Press Animations**: Scale effects with spring physics
- **Navigation Transitions**: Smooth screen transitions
- **Loading States**: Shimmer animations for data loading
- **Interactive Feedback**: Visual feedback for user interactions

## 🌐 Deep Linking

The app supports deep linking for furniture models:
```
https://Augmented-Reality-in-Interior-Design.vercel.app/models/{modelId}
```

## 📱 Supported Devices

- Android 7.0 (API 24) and above
- Devices with ARCore support for AR features
- Tablets and phones with various screen sizes

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



## 🙏 Acknowledgments

- [Google ARCore](https://developers.google.com/ar) for AR capabilities
- [Sketchfab](https://sketchfab.com) for 3D model hosting
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI toolkit
- [Material Design 3](https://m3.material.io) for design guidelines

## 📞 Support

For support, email support@simform.com or open an issue on GitHub.

## 🗺️ Roadmap

- [ ] Advanced AR filters and effects
- [ ] Furniture comparison tool
- [ ] User reviews and ratings
- [ ] Wishlist sharing
- [ ] AR furniture placement history
- [ ] Integration with e-commerce platforms
- [ ] Multiplayer AR experiences
- [ ] Voice-controlled AR navigation

## 📊 Version History

### v1.0.0
- Initial release
- Core AR furniture visualization
- Product catalog with categories
- Favorites system
- Settings and theme customization
- Search functionality

---

**Last Updated**: November 2024

For the latest updates and information, visit the [GitHub repository](https://github.com/simform/SSCompose-FurniCraftAR).
