# Route Parameters

--
## Required Parameters
To capture segments of the URI within your route

```
Route::get('/user/{id}', function ($id) {
    return 'User '.$id;
});
```
You may define as many route parameters as required by your route:
```
Route::get('/posts/{post}/comments/{comment}', function ($postId, $commentId) {
    //
});
```

--
## Required Parameters
Route parameters are 
- encased within {} braces 
- injected into route callbacks / controllers based on their order 

--
## Parameters & Dependency Injection
List your route parameters after your dependencies:
```
use Illuminate\Http\Request;

Route::get('/user/{id}', function (Request $request, $id) {
    return 'User '.$id;
});
```

--
## Optional Parameters
Occasionally you may need to specify a route parameter that may not always be present in the URI

```
Route::get('/user/{name?}', function ($name = null) {
    return $name;
});

Route::get('/user/{name?}', function ($name = 'John') {
    return $name;
});
```

--
## Regular Expression Constraints
You may constrain the format of your route parameters using the where method on a route instance

```
Route::get('/user/{name}', function ($name) {
    //
})->where('name', '[A-Za-z]+');

Route::get('/user/{id}', function ($id) {
    //
})->where('id', '[0-9]+');

Route::get('/user/{id}/{name}', function ($id, $name) {
    //
})->where(['id' => '[0-9]+', 'name' => '[a-z]+']);
```

--
## Regular Expression Constraints
For convenience, some commonly used regular expression patterns have helper methods
```
Route::get('/user/{id}/{name}', function ($id, $name) {
    //
})->whereNumber('id')->whereAlpha('name');

Route::get('/user/{name}', function ($name) {
    //
})->whereAlphaNumeric('name');

Route::get('/user/{id}', function ($id) {
    //
})->whereUuid('id');
```

--
## Regular Expression Constraints
If the incoming request does not match the route pattern constraints, a 404 HTTP response will be returned.

--
## Global Constraints
If you would like a route parameter to always be constrained by a given regular expression, you may use the pattern method. 

In the boot method of your App\Providers\RouteServiceProvider class:
```
/**
 * Define your route model bindings, pattern filters, etc.
 *
 * @return void
 */
public function boot()
{
    Route::pattern('id', '[0-9]+');
}
```

Once the pattern has been defined, it is automatically applied to all routes using that parameter name:

```
Route::get('/user/{id}', function ($id) {
    // Only executed if {id} is numeric...
});
```

--
## Encoded Forward Slashes
The Laravel routing component allows all characters except / to be present within route parameter values. You must explicitly allow / to be part of your placeholder using a where condition regular expression:

```
Route::get('/search/{search}', function ($search) {
    return $search;
})->where('search', '.*');
```

Encoded forward slashes are only supported within the last route segment

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 2. Routing

