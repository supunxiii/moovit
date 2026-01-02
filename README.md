## Overview

Moovit is an Android bus pass booking application developed as part of COMP50011-K-I - Mobile Application Development I. The app provides user registration and sign-in, profile management, route and duration selection, payment entry, QR/pass download, and offline fallback handling, with a light and dark theme for accessibility and user preference.

![Java](https://img.shields.io/badge/Java-8-007396?style=flat-square&logo=java&logoColor=white)
![Android](https://img.shields.io/badge/Android%20SDK-33-3DDC84?style=flat-square&logo=android&logoColor=white)
![Firebase%20Auth](https://img.shields.io/badge/Firebase%20Auth-22.0.0-FFCA28?style=flat-square&logo=firebase&logoColor=black)
![Firebase%20Firestore](https://img.shields.io/badge/Firebase%20Firestore-24.6.1-FFCA28?style=flat-square&logo=firebase&logoColor=black)
![Material%20Components](https://img.shields.io/badge/Material%20Components-1.9.0-757575?style=flat-square&logo=materialdesign&logoColor=white)
![CircleImageView](https://img.shields.io/badge/CircleImageView-3.1.0-4CAF50?style=flat-square)

## Features

The Moovit mobile application delivers the following features:

1. The mobile application was developed with 10 screens, exceeding the requirement of having at least four screens.
2. Suitable navigation was implemented to allow users to move smoothly between all screens.
3. Android and general mobile interface design guidelines were applied throughout the application.
4. All screens contained appropriate, well-structured content, with no placeholder text such as Lorem ipsum.
5. Text elements were correctly formatted, using appropriate font families and sizes for mobile readability.
6. The application utilised suitable and optimised media in the correct file formats.
7. The interface responded correctly to orientation changes, supporting both landscape and portrait modes.
8. The application adapted effectively to different screen sizes and resolutions, ensuring full mobile responsiveness.
9. Offline functionality was implemented, including fallback screens when online data sources were unavailable.
10. The application successfully captured user input through well-designed forms.
11. Data transfer between screens was implemented to support multi-screen workflows.
12. The app retrieved data from an external API/JSON source (Firebase API).
13. The application read from and wrote data to a local data source, supporting persistent storage.
14. A day and night mode (light and dark theme) was implemented to enhance accessibility and user preference.
15. All forms were designed specifically for mobile devices, ensuring usability and clarity.
16. The application was fully configured with an Android app manifest and made use of appropriate icons for branding and navigation.

## Technologies Used

- **Java 8**: Source and target compatibility set to Java 1.8.
- **Android SDK**: compileSdk 33, minSdk 30, targetSdk 33.
- **Android Gradle Plugin**: 8.0.1.
- **Google Services Gradle Plugin**: 4.3.15.
- **AndroidX AppCompat**: 1.6.1.
- **Material Components for Android**: 1.9.0 (Google Material Icons).
- **AndroidX ConstraintLayout**: 2.1.4.
- **Firebase Authentication**: 22.0.0 (including firebase-auth-ktx 22.0.0).
- **Firebase Firestore**: 24.6.1.
- **CircleImageView**: 3.1.0.
- **JUnit**: 4.13.2 (unit testing).
- **AndroidX Test**: junit 1.1.5, espresso-core 3.5.1 (instrumentation testing).

## Project Specifications

- **Course**: COMP50011-K-I - Mobile Application Development I
- **Platform**: Android (Java)
- **Screens/Activities**: SplashActivity, LoginUser, RegisterUser, MainActivity, UserProfile, RouteDate, Payment, BusPass, TermsConditions, FallbackScreen
- **Data Sources**: Firebase Authentication and Firestore
- **Local Storage**: Bus pass image saved to device Downloads
- **Offline Handling**: Fallback screen when there is no network connectivity

## User Interfaces

### UIs

![Moovit UI 1](https://github.com/supunxiii/supunxiii/blob/7653f59dcf38771e7791a1cc0795c9d6b4cdcd3c/user-interfaces/moovit/moovit-ui-1.png)

### UIs

![Moovit UI 2](https://github.com/supunxiii/supunxiii/blob/7653f59dcf38771e7791a1cc0795c9d6b4cdcd3c/user-interfaces/moovit/moovit-ui-2.png)

### UIs

![Moovit UI 3](https://github.com/supunxiii/supunxiii/blob/7653f59dcf38771e7791a1cc0795c9d6b4cdcd3c/user-interfaces/moovit/moovit-ui-3.png)

### UIs

![Moovit UI 4](https://github.com/supunxiii/supunxiii/blob/7653f59dcf38771e7791a1cc0795c9d6b4cdcd3c/user-interfaces/moovit/moovit-ui-4.png)

## Getting Started

To run the Moovit Android application locally, follow these steps:

1. Clone the repository:

   ```shell
   git clone https://github.com/supunxiii/moovit.git
   ```

2. Open the project in Android Studio.

3. Allow Gradle to sync dependencies.

4. Run the app on an emulator or physical Android device (API 30+ recommended).

Optional build command:

```shell
./gradlew assembleDebug
```

## Project Structure

```
moovit/
├── app/
│   ├── build.gradle
│   ├── google-services.json
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/userprofile/   # Activities and application logic
│       └── res/                             # Layouts, drawables, values, mipmaps
├── build.gradle
├── gradle/
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Developer

This project was developed by:

- **Supun Wijesooriya** - Developer

## Contributing

Contributions to Moovit are welcome. If you would like to contribute, please follow these steps:

1. Fork the repository.

2. Create a new branch:

   ```shell
   git checkout -b feature/your-feature-name
   ```

3. Make your changes and commit them:

   ```shell
   git commit -m "Add your commit message"
   ```

4. Push your changes to your forked repository:

   ```shell
   git push origin feature/your-feature-name
   ```

5. Open a pull request to the main repository, describing your changes and the purpose of the pull request.

## Important Notes

- The application is fully mobile-responsive and optimised for different screen sizes and orientations.
- The project was developed as an academic submission following Android UI and UX guidelines.
- Firebase services provide authentication and cloud data storage for user profiles.
- Offline fallback screens are shown when network connectivity is unavailable.
- All content is for demonstration and educational purposes.

## Contact

For any enquiries or feedback, please contact the developer:

- **Supun Wijesooriya**: [GitHub Profile](https://github.com/supunxiii)
- **Project Repository**: [moovit](https://github.com/supunxiii/moovit)

---

_Designed and developed in June 2023_
