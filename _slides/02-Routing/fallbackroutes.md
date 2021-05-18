# Fallback Routes

--
## Fallback routes
Using the Route::fallback method, you may define a route that will be executed when no other route matches the incoming request

```
Route::fallback(function () {
    //
});
```

The fallback route should always be the last route registered by your application.

