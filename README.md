# Characters
This repository contains the code for Marvel Characters App which is my personal project.

It includes the following popular libraries:

- [Hilt](https://dagger.dev/hilt) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.

- [Room](https://developer.android.com/training/data-storage/room) - Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.

- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and the JVM.

## API Key Setup

This project uses the MarvelAPI to fetch marvel characters. 

### Steps to Configure Your API Key

1. **Obtain an API Key**:
    - Visit the [MarvelAPI](https://developer.marvel.com/account) website.
    - Sign up and get your free API key.

2. **Link the API Key in the Code**:
    - Replace the current API Key with your own key in the `build.gradle` file.

3. **Rebuild the Project**:
    - Sync your project with Gradle files and rebuild the project to apply the changes.
