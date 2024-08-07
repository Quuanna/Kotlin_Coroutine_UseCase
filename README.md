# Coroutine Use Case

1. Perform single network request
2. Perform two **sequential** network requests
3. Perform network requests two **sequential** compare two **concurrently** run time
4. Perform network requests timeout Use suspending function `withTimeout()`、`withTimeoutOrNull()`
5. Perform retry network requests
6. Perform network requests retry With Timeout
7. Using Room and Coroutines Perform "offline-first"
8. Coroutines Exception Handling
9. calculation is performed on a background thread using the default Dispatcher with cooperative cancellation use `isActive()`, `ensureActive()` or `yield()`
10. Using Room and Coroutines Perform "offline-first"，Continue Coroutine execution when the user leaves the screen 
11. Using WorkManager with Coroutines

# Flow Use Case

1. Flow Basics
2. Basic Flow Intermediate Operators、class FlowIntermediateOperators Operators
3. Flow Exception Handling， it use `catch` operator to handle exceptions、`retry` operator retry failed network request
4. Exposing Flows in the ViewModel

# based

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.

# Jetpack

- Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
- ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive
  configuration changes such as screen rotations.
- [Room](https://developer.android.com/topic/libraries/architecture/room): for data storage
  persistence.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager): for
  background scheduling needs.

# Construct the REST APIs And network data

- [Retrofit](https://github.com/square/retrofit): Retrofit supports coroutines suspend function
  after 2.6.0
- [Okhttp3](https://github.com/square/okhttp)
    - interceptor: Implementation to judge various status display messages of Http Status Code, ex:
      2xx Success, 3xx Redirection, 4xx Client Errors, 5xx Server Errors

# Unit Test

- Retrofit mock: tests more readable & controllable, use Retrofit's mock web server just like how it
  is
  used，[mentioned by @JakeWharton](https://github.com/square/retrofit/issues/1413#issuecomment-168905741)
- [coroutines test](https://developer.android.com/kotlin/coroutines/test#additional-resources)


# Learn resource
[Kotlin Coroutines and Flow - Use Cases on Android](https://github.com/LukasLechnerDev/Kotlin-Coroutines-and-Flow-UseCases-on-Android)

