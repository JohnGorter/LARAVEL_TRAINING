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

- The $user variable is type-hinted as the App\Models\User Eloquent model 
- The variable name matches the {user} URI segment

Laravel will automatically inject the model instance that has an ID matching the corresponding value from the request URI

If a matching model instance is not found in the database, a 404 HTTP response will automatically be generated

--
## Implicit Binding
Implicit binding is also possible when using controller methods

```
use App\Http\Controllers\UserController;
use App\Models\User;

// Route definition...
Route::get('/users/{user}', [UserController::class, 'show']);

// Controller method definition...
public function show(User $user)
{
    return view('user.profile', ['user' => $user]);
}
```

--
## Customizing The Key
Sometimes you may wish to resolve Eloquent models using a column other than id

```
use App\Models\Post;

Route::get('/posts/{post:slug}', function (Post $post) {
    return $post;
});
```

--
## Customizing The Key
If you would like model binding to always use a database column other than id when retrieving a given model class, override the getRouteKeyName method on the Eloquent model

```
/**
 * Get the route key for the model.
 *
 * @return string
 */
public function getRouteKeyName()
{
    return 'slug';
}
```

--
## Customizing Missing Model Behavior
Typically, a 404 HTTP response will be generated if an implicitly bound model is not found

Customize this behavior by calling the missing method when defining your route

```
use App\Http\Controllers\LocationsController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;

Route::get('/locations/{location:slug}', [LocationsController::class, 'show'])
        ->name('locations.view')
        ->missing(function (Request $request) {
            return Redirect::route('locations.index');
        });
```

--
## Explicit Binding
You are not required to use Laravel's implicit, convention based model resolution in order to use model binding. You can also explicitly define how route parameters correspond to models

Define your explicit model bindings at the beginning of the boot method of your RouteServiceProvider class:

```
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
```
Next, define a route that contains a {user} parameter:
```
use App\Models\User;

Route::get('/users/{user}', function (User $user) {
    //
});
```

Since we have bound all {user} parameters to the App\Models\User model, an instance of that class will be injected into the route

> So, for example, a request to users/1 will inject the User instance from the database which has an ID of 1.


