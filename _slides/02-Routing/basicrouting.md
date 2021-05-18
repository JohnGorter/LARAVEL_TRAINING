# Basic Routing

--
# Basic Routing
The most basic Laravel route
```
use Illuminate\Support\Facades\Route;

Route::get('/greeting', function () {
    return 'Hello World';
});
```

--
## Default Route Files
Routes are located in the routes directory
- automatically loaded 
- routes/web.php routes are assigned the web middleware group
- routes/api.php are stateless and are assigned the api middleware group

For most applications start by defining routes in the routes/web.php file
```
use App\Http\Controllers\UserController;

Route::get('/user', [UserController::class, 'index']);
```

--
## API Routes
Routes defined in the routes/api.php file are nested within a route group
- the /api URI prefix is automatically applied 

You may modify the prefix and other route group options by modifying your RouteServiceProvider class

--
## Available Router Methods
The router allows you to register routes that respond to any HTTP verb:
```
Route::get($uri, $callback);
Route::post($uri, $callback);
Route::put($uri, $callback);
Route::patch($uri, $callback);
Route::delete($uri, $callback);
Route::options($uri, $callback);
```

--
## Available Router Methods
Multiple HTTP verb routes:
```
Route::match(['get', 'post'], '/', function () {
    //
});

Route::any('/', function () {
    //
});
```

--
## Dependency Injection
You may type-hint any dependencies required by your route in your route's callback signature
- declared dependencies will automatically be resolved and injected 

```
use Illuminate\Http\Request;

Route::get('/users', function (Request $request) {
    // ...
});
```

--
## CSRF Protection
Remember, any HTML forms pointing to POST, PUT, PATCH, or DELETE routes that are defined in the web routes file should include a CSRF token field. Otherwise, the request will be rejected. 
```
<form method="POST" action="/profile">
    @csrf
    ...
</form>
```

--
## Redirect Routes
To define a route that redirects to another URI, use the Route::redirect method
```
Route::redirect('/here', '/there');
```

By default, Route::redirect returns a 302 status code. You may customize the status code using the optional third parameter:
```
Route::redirect('/here', '/there', 301);
```
Or, you may use the Route::permanentRedirect method to return a 301 status code:
```
Route::permanentRedirect('/here', '/there');
```

--
## View Routes
If the route only needs to return a view, use the Route::view method
```
Route::view('/welcome', 'welcome');
Route::view('/welcome', 'welcome', ['name' => 'Taylor']);
```

When using route parameters in view routes, the following parameters are reserved by Laravel and cannot be used: view, data, status, and headers.

--
## Accessing The Current Route
You may use the current, currentRouteName, and currentRouteAction methods on the Route facade to access information about the route handling the incoming request

```
use Illuminate\Support\Facades\Route;

$route = Route::current(); // Illuminate\Routing\Route
$name = Route::currentRouteName(); // string
$action = Route::currentRouteAction(); // string
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Basic Routing