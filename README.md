# GitHub Repository Search App

## Overview

This application uses the GitHub Repository Search API to perform keyword-based searches
and display results with infinite scrolling.

It is implemented using a modern Android tech stack such as  
**Jetpack Compose / Hilt / Retrofit / Paging3**, which are commonly used in current Android development.
The project structure emphasizes readability, maintainability, and extensibility.

This app is designed as a sample application leveraging the GitHub Search API,
with a strong focus on production-oriented architecture and state management.

---

## Demo

Below is a demo of the implemented application.

![demo](docs/demo.gif)

---

## Features

- Search GitHub repositories
- Debounced search query handling
- Infinite scrolling with Paging
- Open repository pages in an external browser on item tap
- Loading and error state handling

---

## Architecture

- MVVM architecture
- State management using ViewModel
- API access abstraction via Repository layer
- Pagination implemented with Paging3

UI (Compose)
↓
ViewModel
↓
Repository
↓
GitHub API


---

## Technologies & Libraries

### Language & Build

- Kotlin
- Android Gradle Plugin

### UI

- Jetpack Compose
- Material3
- Navigation Compose

### Asynchronous & State Management

- Kotlin Coroutines / Flow
- Paging3

### Networking

- Retrofit
- OkHttp
- Kotlinx Serialization

### Dependency Injection

- Hilt

### Testing

- JUnit
- Espresso

---

## Versions (Partial)

- Kotlin: 2.3.0
- Compose BOM: 2025.12.01
- Paging: 3.3.6
- Retrofit: 3.0.0
- OkHttp: 5.3.2
- Hilt: 2.57.2

For more details, please refer to `libs.versions.toml`.

---

## Setup

### GitHub API Token

A Personal Access Token (PAT) is required to use the GitHub Search API.

Please add the following entries to `local.properties`:

```
API_BASE_URL=https://api.github.com/
GITHUB_TOKEN=your_github_personal_access_token
```


The token is not included in the repository.

---

## Build & Run

1. Open this repository in Android Studio
2. Add `API_BASE_URL` and `GITHUB_TOKEN` to `local.properties`
3. Run the `app` module

This project assumes a Debug build configuration.

---

## Notes

- The repository detail screen is not implemented within the app.
  Instead, repository pages are opened in an external browser.

---

## Design Considerations & Key Points

### Improved Search Experience

- Debounce processing is applied to search queries to prevent unnecessary API requests during typing
- UI states such as initial (no search), loading, error, and result display are clearly separated and rendered accordingly

### Pagination Design

- Infinite scrolling is implemented using **Paging3**, taking into account the characteristics of the GitHub Search API
- `LoadState` is used to differentiate UI behavior between initial load and append (pagination) loading

---

## Reasons for Technology Choices

### Jetpack Compose

- Declarative UI makes the relationship between state (UiState / LoadState) and UI explicit
- It is the current standard for Android UI development and offers better maintainability and scalability

### MVVM + Repository

- Separates concerns between UI, state management, and data fetching to improve readability and testability
- Centralizes management of search queries and paging state within the ViewModel

### Paging3

- Enables safe and concise implementation of pagination logic, including load state handling, retry mechanisms, and caching
- Works well with the GitHub Search API and is suitable for production-oriented use cases

### Retrofit + OkHttp

- A de facto standard networking stack in Android development with strong real-world applicability
- Interceptors allow clear separation of concerns such as authentication and logging

### Kotlin Coroutines / Flow

- Provides a simple and expressive way to handle asynchronous processing and state streams
- Allows declarative handling of search query changes and data reloading

---

## Out of Scope / Future Improvements

- In-app repository detail screen (currently handled via external browser)
- Favorite (bookmark) feature
- Additional unit tests and UI tests

---

## AI Usage

AI tools were used for the following purposes:

- Researching pagination implementation approaches
- Generating the initial README template
