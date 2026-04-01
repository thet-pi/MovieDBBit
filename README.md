# Movie Info Android App

Hello World 2
A native Android application built with Jetpack Compose that displays movie information using The Movie Database (TMDB) API.Yoyo
Battle
Jelly
Power
Frequent | Yellow

## 🎬 Features

### Core Features
- **Browse Movies**: View popular, top-rated, upcoming, and now playing movies
- **Search Movies**: Search for movies with real-time results
- **Movie Details**: View detailed information including cast, genres, ratings, and similar movies  
- **Favorites**: Add/remove movies to/from favorites with local storage
- **Offline Support**: Cached data for improved offline experience

### Technical Features
- **MVVM Architecture**: Clean architecture with separation of concerns
- **Jetpack Compose UI**: Modern declarative UI toolkit
- **Hilt Dependency Injection**: Simplified dependency management
- **Room Database**: Local data persistence for favorites
- **Retrofit + OkHttp**: Network layer for API communication
- **Coroutines + Flow**: Asynchronous programming and reactive streams
- **Coil**: Efficient image loading and caching

## 🏗️ Architecture

The app follows MVVM (Model-View-ViewModel) architecture with clean architecture principles:

```
app/src/main/java/com/example/moviedbbit/
├── data/
│   ├── api/                    # API service and interceptors
│   ├── database/              # Room database DAOs and entities
│   ├── mappers/               # Data mapping functions
│   ├── models/                # Data models (local and remote)
│   └── repository/            # Repository implementations
├── domain/
│   ├── models/                # Domain models
│   ├── repository/            # Repository interfaces
│   └── usecases/             # Business logic use cases
├── presentation/
│   ├── components/            # Reusable UI components
│   ├── navigation/           # Navigation setup
│   ├── screens/              # Screen composables and ViewModels
│   └── theme/                # App theming
└── di/                       # Dependency injection modules
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox (2021.2.1) or later
- JDK 11 or later
- Android SDK API 26 (Android 8.0) or higher

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MovieDBBit
   ```

2. **Get TMDB API Key**
   - Visit [The Movie Database](https://www.themoviedb.org/)
   - Create an account and request an API key
   - Go to Settings > API to get your API key

3. **Configure API Key**
   - Open `local.properties` file in the project root
   - Add your TMDB API key:
     ```properties
     TMDB_API_KEY=your_api_key_here
     ```

4. **Build and Run**
   - Open the project in Android Studio
   - Sync the project (Gradle will download dependencies)
   - Run the app on an emulator or physical device

### Build Commands

#### Native Build (Recommended)
```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test
```

#### Docker Build
For CI/CD or containerized builds:

```bash
# Build and start container
docker-compose up -d

# Build the app inside container
docker-compose exec android-builder ./gradlew clean assembleDebug

# Stop container
docker-compose down
```

**Note**: The Dockerfile uses x86_64 (linux/amd64) architecture:
- Ensures compatibility with Android SDK build-tools
- Works on both Apple Silicon (via Rosetta/emulation) and Intel Macs
- Most stable and reliable configuration for Android builds

## 📱 Screens

### Home Screen
- **Now Playing**: Horizontal carousel of currently playing movies
- **Popular Movies**: Trending and popular movies
- **Top Rated**: Highest rated movies
- **Upcoming**: Movies coming soon

### Search Screen
- **Live Search**: Real-time search with debouncing
- **Grid Layout**: Movies displayed in a responsive grid
- **Empty States**: Helpful messages for no results

### Movie Details Screen
- **Hero Section**: Backdrop image with movie poster
- **Movie Information**: Title, release date, runtime, rating
- **Genres**: Displayed as chips
- **Overview**: Expandable movie synopsis
- **Cast**: Horizontal scrolling cast list with photos
- **Similar Movies**: Related movie recommendations
- **Favorite Toggle**: Add/remove from favorites

### Favorites Screen
- **Saved Movies**: List of user's favorite movies
- **Persistent Storage**: Favorites saved locally using Room database
- **Remove Options**: Swipe or tap to remove favorites

## 🛠️ Technical Details

### Dependencies
- **Jetpack Compose**: UI framework
- **Hilt**: Dependency injection
- **Room**: Local database
- **Retrofit + OkHttp**: Networking
- **Coil**: Image loading
- **Navigation Compose**: Navigation
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive streams

### API Integration
- **Base URL**: `https://api.themoviedb.org/3/`
- **Image Base URL**: `https://image.tmdb.org/t/p/`
- **Endpoints Used**:
  - Popular Movies: `/movie/popular`
  - Top Rated: `/movie/top_rated`
  - Upcoming: `/movie/upcoming`
  - Now Playing: `/movie/now_playing`
  - Movie Details: `/movie/{id}`
  - Search: `/search/movie`
  - Genres: `/genre/movie/list`

### Data Flow
1. **UI Layer**: Composable screens observe ViewModels
2. **ViewModel**: Manages UI state and calls use cases
3. **Use Cases**: Business logic and repository calls
4. **Repository**: Coordinates between API and local database
5. **Data Sources**: API service and Room database

## 🎨 Design

### Material Design 3
- Uses Material 3 design system
- Dynamic color support (Android 12+)
- Dark/Light theme support
- Proper accessibility support

### UI Components
- **Movie Cards**: Reusable poster cards with ratings
- **Loading States**: Progress indicators during data fetch
- **Error States**: User-friendly error messages with retry
- **Empty States**: Helpful messages when no data

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with logging
- **Release**: Production build with ProGuard obfuscation

### ProGuard Rules
```proguard
# Keep data models
-keep class com.example.moviedbbit.data.models.** { *; }
-keep class com.example.moviedbbit.domain.models.** { *; }

# Retrofit and Gson rules
-keepattributes Signature
-keepattributes *Annotation*
```

### Network Security
- HTTPS-only communication
- API key stored securely in BuildConfig
- Certificate pinning can be added for production

## 🧪 Testing

### Unit Tests
- ViewModels with mock repositories
- Repository tests with mock API service
- Use cases with mock repositories

### UI Tests (Planned)
- Screen rendering tests
- User interaction tests
- Navigation tests

## 📊 Performance Optimizations

### Image Loading
- Coil for efficient image loading and caching
- Different image sizes for different use cases
- Memory and disk caching

### List Performance
- LazyColumn and LazyRow for efficient scrolling
- Key-based recomposition optimization
- Pagination support (ready for implementation)

### Data Caching
- Room database for offline access
- Repository pattern for data coordination
- Proper cache invalidation strategies

## 🔒 Security Considerations

### API Key Security
- Stored in local.properties (not committed to VCS)
- Accessed via BuildConfig
- Can be further secured with server-side proxy

### Data Protection
- Local database encrypted (can be enabled)
- HTTPS-only network communication
- No sensitive user data stored

## 🚀 Future Enhancements

### Planned Features
- **User Authentication**: User accounts and sync
- **Reviews and Ratings**: User reviews and ratings
- **Watchlist**: Movies to watch later
- **Personalized Recommendations**: ML-based suggestions
- **Social Features**: Share favorites with friends

### Technical Improvements
- **Pagination**: Infinite scrolling for movie lists
- **Background Sync**: Sync favorites across devices
- **Offline Mode**: Full offline functionality
- **Performance Monitoring**: Crash reporting and analytics
- **Modularization**: Feature-based module structure

## 🐛 Known Issues

1. **Pull-to-Refresh**: Temporarily removed due to API compatibility
2. **Image Loading**: Fallback images for missing posters
3. **Network Errors**: Retry mechanism can be improved

## 📝 License

This project is for educational purposes. Movie data provided by [The Movie Database](https://www.themoviedb.org/).

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For questions or issues:
1. Check the [Issues](../../issues) section
2. Review the documentation
3. Create a new issue with detailed description

---

**Built with ❤️ using Jetpack Compose and TMDB API**
