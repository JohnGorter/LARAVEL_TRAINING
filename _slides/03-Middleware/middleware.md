# Introduction

--
## Introduction
Middleware is a mechanism for inspecting and filtering HTTP requests entering your application. 
- for example user authentication

Other scenario's:
- logging
- caching

--
## Defining Middleware
To create a new middleware
```
php artisan make:middleware EnsureTokenIsValid
```

This command will place a new EnsureTokenIsValid class within your app/Http/Middleware directory


--
## Defining Middleware
In this middleware, only access to the route is allowed if the supplied token input matches a specified value

```
<?php
namespace App\Http\Middleware;
use Closure;
class EnsureTokenIsValid
{
    public function handle($request, Closure $next)
    {
        if ($request->input('token') !== 'my-secret-token') {
            return redirect('home');
        }

        return $next($request);
    }
}
```

All middleware are resolved via the service container, so you may type-hint any dependencies you need within a middleware's constructor.

--
## Middleware & Responses
Of course, a middleware can perform tasks before or after passing the request deeper into the application
```
<?php

namespace App\Http\Middleware;
use Closure;
class BeforeMiddleware
{
    public function handle($request, Closure $next)
    {
        // Perform action before
        $response = $next($request);
        // Perform action after
        return $response;
    }
}
```

--
## Registering Middleware
- Global Middleware
    - ran during every HTTP request to your application
    - list the middleware class in the $middleware property of your app/Http/Kernel.php class

--
## Registering Middleware
- Assigning Middleware To Routes
    - ran with specific routes
    - first assign the middleware a key in your application's app/Http/Kernel.php file
```
// Within App\Http\Kernel class...
protected $routeMiddleware = [
    'auth' => \App\Http\Middleware\Authenticate::class,
    'auth.basic' => \Illuminate\Auth\Middleware\AuthenticateWithBasicAuth::class,
    ...
    'cache.headers' => \Illuminate\Http\Middleware\SetCacheHeaders::class,
    'verified' => \Illuminate\Auth\Middleware\EnsureEmailIsVerified::class,
];
```
    - then use the middleware method to assign middleware to a route
```
Route::get('/profile', function () {
    //
})->middleware('auth');
```

--
## Registering Middleware
You may assign multiple middleware to the route by passing an array of middleware names to the middleware method:
```
Route::get('/', function () {
    //
})->middleware(['first', 'second']);
```

--
## Registering Middleware
When assigning middleware, you may also pass the fully qualified class name:
```
use App\Http\Middleware\EnsureTokenIsValid;

Route::get('/profile', function () {
    //
})->middleware(EnsureTokenIsValid::class);
```

--
## Registering Middleware
When assigning middleware to a group of routes, and you want to prevent the middleware from being applied to an individual route within the group
```
use App\Http\Middleware\EnsureTokenIsValid;

Route::middleware([EnsureTokenIsValid::class])->group(function () {
    Route::get('/', function () {
        //
    });

    Route::get('/profile', function () {
        //
    })->withoutMiddleware([EnsureTokenIsValid::class]);
});
```

The withoutMiddleware method can only remove route middleware and does not apply to global middleware

--
## Middleware Groups
Sometimes you may want to group several middleware under a single key to make them easier to assign to routes
```
/**
 * The application's route middleware groups.
 *
 * @var array
 */
protected $middlewareGroups = [
    'web' => [
        \App\Http\Middleware\EncryptCookies::class,
        \Illuminate\Cookie\Middleware\AddQueuedCookiesToResponse::class,
        \Illuminate\Session\Middleware\StartSession::class,
        // \Illuminate\Session\Middleware\AuthenticateSession::class,
        \Illuminate\View\Middleware\ShareErrorsFromSession::class,
        \App\Http\Middleware\VerifyCsrfToken::class,
        \Illuminate\Routing\Middleware\SubstituteBindings::class,
    ],

    'api' => [
        'throttle:api',
        \Illuminate\Routing\Middleware\SubstituteBindings::class,
    ],
];
```

--
## Middleware Groups
Middleware groups may be assigned to routes and controller actions using the same syntax as individual middleware. 
```
Route::get('/', function () {
    //
})->middleware('web');

Route::middleware(['web'])->group(function () {
    //
});
```

--
## Sorting Middleware
If you need middleware to execute in a specific order but not have control over their order when they are assigned to the route use the $middlewarePriority property of your app/Http/Kernel.php 
```
/**
 * The priority-sorted list of middleware.
 *
 * This forces non-global middleware to always be in the given order.
 *
 * @var array
 */
protected $middlewarePriority = [
    \Illuminate\Cookie\Middleware\EncryptCookies::class,
    \Illuminate\Session\Middleware\StartSession::class,
    \Illuminate\View\Middleware\ShareErrorsFromSession::class,
    \Illuminate\Contracts\Auth\Middleware\AuthenticatesRequests::class,
    \Illuminate\Routing\Middleware\ThrottleRequests::class,
    \Illuminate\Session\Middleware\AuthenticateSession::class,
    \Illuminate\Routing\Middleware\SubstituteBindings::class,
    \Illuminate\Auth\Middleware\Authorize::class,
];
```

--
## Middleware Parameters
Middleware can also receive additional parameters. 

For example, check the authenticated user for a given "role" before performing a given action

```
<?php
namespace App\Http\Middleware;
use Closure;
class EnsureUserHasRole
{
    public function handle($request, Closure $next, $role)
    {
        if (! $request->user()->hasRole($role)) {
            // Redirect...
        }
        return $next($request);
    }
}
```
--
## Middleware Parameters
Middleware parameters may be specified when defining the route by separating the middleware name and parameters with a :
- multiple parameters should be delimited by commas

```
Route::put('/post/{id}', function ($id) {
    //
})->middleware('role:editor');
```

--
## Terminable Middleware
Sometimes a middleware may need to do some work after the HTTP response has been sent to the browser
- define a terminate method on your middleware
- this terminate method will automatically be called after the response is sent to the browser


--
## Terminable Middleware
```
<?php

namespace Illuminate\Session\Middleware;

use Closure;

class TerminatingMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        return $next($request);
    }

    /**
     * Handle tasks after the response has been sent to the browser.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Illuminate\Http\Response  $response
     * @return void
     */
    public function terminate($request, $response)
    {
        // ...
    }
}
```

The terminate method should receive both the request and the response

--
## Terminable Middleware
Once you have defined a terminable middleware, add it to the list of routes or global middleware in the app/Http/Kernel.php file

```
use App\Http\Middleware\TerminatingMiddleware;

/**
 * Register any application services.
 *
 * @return void
 */
public function register()
{
    $this->app->singleton(TerminatingMiddleware::class);
}
```

