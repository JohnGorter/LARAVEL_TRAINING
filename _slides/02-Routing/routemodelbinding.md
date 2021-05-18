# Route Model Binding

--
## Route Model Binding
When injecting a model ID to a route or controller action, you will often query the database to retrieve the model that corresponds to that ID

Laravel route model binding provides a convenient way to automatically inject the model instances directly into your routes

--
## Implicit Binding
Laravel automatically resolves Eloquent models defined in routes or controller actions whose type-hinted variable names match a route segment name

```
use App\Models\User;

Route::get('/users/{user}', function (User $user) {
    return $user->email;
});
```

--
## Implicit Binding
Since the $user variable is type-hinted as the App\Models\User Eloquent model and the variable name matches the {user} URI segment, Laravel will automatically inject the model instance that has an ID matching the corresponding value from the request URI

If a matching model instance is not found in the database, a 404 HTTP response will automatically be generated

Of course, implicit binding is also possible when using controller methods. Again, note the {user} URI segment matches the $user variable in the controller which contains an App\Models\User type-hint:

use App\Http\Controllers\UserController;
use App\Models\User;

// Route definition...
Route::get('/users/{user}', [UserController::class, 'show']);

// Controller method definition...
public function show(User $user)
{
    return view('user.profile', ['user' => $user]);
}
Customizing The Key
Sometimes you may wish to resolve Eloquent models using a column other than id. To do so, you may specify the column in the route parameter definition:

use App\Models\Post;

Route::get('/posts/{post:slug}', function (Post $post) {
    return $post;
});
If you would like model binding to always use a database column other than id when retrieving a given model class, you may override the getRouteKeyName method on the Eloquent model:

/**
 * Get the route key for the model.
 *
 * @return string
 */
public function getRouteKeyName()
{
    return 'slug';
}
Custom Keys & Scoping
When implicitly binding multiple Eloquent models in a single route definition, you may wish to scope the second Eloquent model such that it must be a child of the previous Eloquent model. For example, consider this route definition that retrieves a blog post by slug for a specific user:

use App\Models\Post;
use App\Models\User;

Route::get('/users/{user}/posts/{post:slug}', function (User $user, Post $post) {
    return $post;
});
When using a custom keyed implicit binding as a nested route parameter, Laravel will automatically scope the query to retrieve the nested model by its parent using conventions to guess the relationship name on the parent. In this case, it will be assumed that the User model has a relationship named posts (the plural form of the route parameter name) which can be used to retrieve the Post model.

Customizing Missing Model Behavior
Typically, a 404 HTTP response will be generated if an implicitly bound model is not found. However, you may customize this behavior by calling the missing method when defining your route. The missing method accepts a closure that will be invoked if an implicitly bound model can not be found:

use App\Http\Controllers\LocationsController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;

Route::get('/locations/{location:slug}', [LocationsController::class, 'show'])
        ->name('locations.view')
        ->missing(function (Request $request) {
            return Redirect::route('locations.index');
        });
Explicit Binding
You are not required to use Laravel's implicit, convention based model resolution in order to use model binding. You can also explicitly define how route parameters correspond to models. To register an explicit binding, use the router's model method to specify the class for a given parameter. You should define your explicit model bindings at the beginning of the boot method of your RouteServiceProvider class:

use App\Models\User;
use Illuminate\Support\Facades\Route;

/**
 * Define your route model bindings, pattern filters, etc.
 *
 * @return void
 */
public function boot()
{
    Route::model('user', User::class);

    // ...
}
Next, define a route that contains a {user} parameter:

use App\Models\User;

Route::get('/users/{user}', function (User $user) {
    //
});
Since we have bound all {user} parameters to the App\Models\User model, an instance of that class will be injected into the route. So, for example, a request to users/1 will inject the User instance from the database which has an ID of 1.

If a matching model instance is not found in the database, a 404 HTTP response will be automatically generated.

Customizing The Resolution Logic
If you wish to define your own model binding resolution logic, you may use the Route::bind method. The closure you pass to the bind method will receive the value of the URI segment and should return the instance of the class that should be injected into the route. Again, this customization should take place in the boot method of your application's RouteServiceProvider:

use App\Models\User;
use Illuminate\Support\Facades\Route;

/**
 * Define your route model bindings, pattern filters, etc.
 *
 * @return void
 */
public function boot()
{
    Route::bind('user', function ($value) {
        return User::where('name', $value)->firstOrFail();
    });

    // ...
}
Alternatively, you may override the resolveRouteBinding method on your Eloquent model. This method will receive the value of the URI segment and should return the instance of the class that should be injected into the route:

/**
 * Retrieve the model for a bound value.
 *
 * @param  mixed  $value
 * @param  string|null  $field
 * @return \Illuminate\Database\Eloquent\Model|null
 */
public function resolveRouteBinding($value, $field = null)
{
    return $this->where('name', $value)->firstOrFail();
}
If a route is utilizing implicit binding scoping, the resolveChildRouteBinding method will be used to resolve the child binding of the parent model:

/**
 * Retrieve the child model for a bound value.
 *
 * @param  string  $childType
 * @param  mixed  $value
 * @param  string|null  $field
 * @return \Illuminate\Database\Eloquent\Model|null
 */
public function resolveChildRouteBinding($childType, $value, $field)
{
    return parent::resolveChildRouteBinding($childType, $value, $field);
}
Fallback Routes
Using the Route::fallback method, you may define a route that will be executed when no other route matches the incoming request. Typically, unhandled requests will automatically render a "404" page via your application's exception handler. However, since you would typically define the fallback route within your routes/web.php file, all middleware in the web middleware group will apply to the route. You are free to add additional middleware to this route as needed:

Route::fallback(function () {
    //
});

The fallback route should always be the last route registered by your application.


Rate Limiting
Defining Rate Limiters
Laravel includes powerful and customizable rate limiting services that you may utilize to restrict the amount of traffic for a given route or group of routes. To get started, you should define rate limiter configurations that meet your application's needs. Typically, this should be done within the configureRateLimiting method of your application's App\Providers\RouteServiceProvider class.

Rate limiters are defined using the RateLimiter facade's for method. The for method accepts a rate limiter name and a closure that returns the limit configuration that should apply to routes that are assigned to the rate limiter. Limit configuration are instances of the Illuminate\Cache\RateLimiting\Limit class. This class contains helpful "builder" methods so that you can quickly define your limit. The rate limiter name may be any string you wish:

use Illuminate\Cache\RateLimiting\Limit;
use Illuminate\Support\Facades\RateLimiter;

/**
 * Configure the rate limiters for the application.
 *
 * @return void
 */
protected function configureRateLimiting()
{
    RateLimiter::for('global', function (Request $request) {
        return Limit::perMinute(1000);
    });
}
If the incoming request exceeds the specified rate limit, a response with a 429 HTTP status code will automatically be returned by Laravel. If you would like to define your own response that should be returned by a rate limit, you may use the response method:

RateLimiter::for('global', function (Request $request) {
    return Limit::perMinute(1000)->response(function () {
        return response('Custom response...', 429);
    });
});
Since rate limiter callbacks receive the incoming HTTP request instance, you may build the appropriate rate limit dynamically based on the incoming request or authenticated user:

RateLimiter::for('uploads', function (Request $request) {
    return $request->user()->vipCustomer()
                ? Limit::none()
                : Limit::perMinute(100);
});
Segmenting Rate Limits
Sometimes you may wish to segment rate limits by some arbitrary value. For example, you may wish to allow users to access a given route 100 times per minute per IP address. To accomplish this, you may use the by method when building your rate limit:

RateLimiter::for('uploads', function (Request $request) {
    return $request->user()->vipCustomer()
                ? Limit::none()
                : Limit::perMinute(100)->by($request->ip());
});
To illustrate this feature using another example, we can limit access to the route to 100 times per minute per authenticated user ID or 10 times per minute per IP address for guests:

RateLimiter::for('uploads', function (Request $request) {
    return $request->user()
                ? Limit::perMinute(100)->by($request->user()->id)
                : Limit::perMinute(10)->by($request->ip());
});
Multiple Rate Limits
If needed, you may return an array of rate limits for a given rate limiter configuration. Each rate limit will be evaluated for the route based on the order they are placed within the array:

RateLimiter::for('login', function (Request $request) {
    return [
        Limit::perMinute(500),
        Limit::perMinute(3)->by($request->input('email')),
    ];
});
Attaching Rate Limiters To Routes
Rate limiters may be attached to routes or route groups using the throttle middleware. The throttle middleware accepts the name of the rate limiter you wish to assign to the route:

Route::middleware(['throttle:uploads'])->group(function () {
    Route::post('/audio', function () {
        //
    });

    Route::post('/video', function () {
        //
    });
});
Throttling With Redis
Typically, the throttle middleware is mapped to the Illuminate\Routing\Middleware\ThrottleRequests class. This mapping is defined in your application's HTTP kernel (App\Http\Kernel). However, if you are using Redis as your application's cache driver, you may wish to change this mapping to use the Illuminate\Routing\Middleware\ThrottleRequestsWithRedis class. This class is more efficient at managing rate limiting using Redis:

'throttle' => \Illuminate\Routing\Middleware\ThrottleRequestsWithRedis::class,
Form Method Spoofing
HTML forms do not support PUT, PATCH, or DELETE actions. So, when defining PUT, PATCH, or DELETE routes that are called from an HTML form, you will need to add a hidden _method field to the form. The value sent with the _method field will be used as the HTTP request method:

<form action="/example" method="POST">
    <input type="hidden" name="_method" value="PUT">
    <input type="hidden" name="_token" value="{{ csrf_token() }}">
</form>
For convenience, you may use the @method Blade directive to generate the _method input field:

<form action="/example" method="POST">
    @method('PUT')
    @csrf
</form>
Accessing The Current Route
You may use the current, currentRouteName, and currentRouteAction methods on the Route facade to access information about the route handling the incoming request:

use Illuminate\Support\Facades\Route;

$route = Route::current(); // Illuminate\Routing\Route
$name = Route::currentRouteName(); // string
$action = Route::currentRouteAction(); // string
You may refer to the API documentation for both the underlying class of the Route facade and Route instance to review all of the methods that are available on the router and route classes.

Cross-Origin Resource Sharing (CORS)
Laravel can automatically respond to CORS OPTIONS HTTP requests with values that you configure. All CORS settings may be configured in your application's config/cors.php configuration file. The OPTIONS requests will automatically be handled by the HandleCors middleware that is included by default in your global middleware stack. Your global middleware stack is located in your application's HTTP kernel (App\Http\Kernel).


For more information on CORS and CORS headers, please consult the MDN web documentation on CORS.


Route Caching
When deploying your application to production, you should take advantage of Laravel's route cache. Using the route cache will drastically decrease the amount of time it takes to register all of your application's routes. To generate a route cache, execute the route:cache Artisan command:

php artisan route:cache
After running this command, your cached routes file will be loaded on every request. Remember, if you add any new routes you will need to generate a fresh route cache. Because of this, you should only run the route:cache command during your project's deployment.

You may use the route:clear command to clear the route cache:

php artisan route:clear