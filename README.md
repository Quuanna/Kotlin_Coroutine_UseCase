
# based
- [Kotlin](https://kotlinlang.org/) 
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- 
## Architecture
    - MVVM Architecture (Model - View - ViewModel)
    - Repository Pattern

## Jetpack
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata): observable data holder class and lifecycle-aware, only update app Component observers that are in an active lifecycle state.

## Construct the REST APIs And network data
    - [Retrofit](https://github.com/square/retrofit): Retrofit supports coroutines suspend function after 2.6.0
    - [Okhttp3](https://github.com/square/okhttp) 
      - interceptor: Implementation to judge various status display messages of Http Status Code, ex: 2xx Success, 3xx Redirection, 4xx Client Errors, 5xx Server Errors
      - mockserver: MockWebServer provides fake json, Mock fake Http Response, or request, response, header-related verification

## Unit Test
  - Retrofit mock: tests more readable & controllable, use Retrofit's mock web server just like how it is used，[mentioned by @JakeWharton](https://github.com/square/retrofit/issues/1413#issuecomment-168905741) 
  - [coroutines test](https://developer.android.com/kotlin/coroutines/test#additional-resources): 


# Kotlin Coroutine UseCase
1. Perform single network request
2. Perform two **sequential** network requests
3. Perform network requests **concurrently**
4. Perform Network requests timeout or retry
5. Room and Coroutines Perform "offline-first"
6. Coroutines Exception Handling
7. Continue Coroutine execution when the user leaves the screen
8. Using WorkManager with Coroutines


# Open API
[PokeAPI](https://pokeapi.co/) for constructing REST ful API

# Learning source
[Kotlin Coroutines and Flow - Use Cases on Android](https://github.com/LukasLechnerDev/Kotlin-Coroutines-and-Flow-UseCases-on-Android?tab=readme-ov-file)