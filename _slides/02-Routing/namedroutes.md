# Named Routes


--
## Named Routes

Named routes allow the convenient generation of URLs or redirects for specific routes

```
Route::get('/user/profile', function () {
    //
})->name('profile');
```

--
## Named Routes

You may also specify route names for controller actions:

```
Route::get(
    '/user/profile',
    [UserProfileController::class, 'show']
)->name('profile');
```

> Route names should always be unique

--
## Generating URLs To Named Routes
Once you have assigned a name to a given route, use the route's name when generating URLs or redirects 

```
// Generating URLs...
$url = route('profile');

// Generating Redirects...
return redirect()->route('profile');
```

--
## Generating URLs To Named Routes
If the named route defines parameters, you may pass the parameters as the second argument

```
Route::get('/user/{id}/profile', function ($id) {
    //
})->name('profile');

$url = route('profile', ['id' => 1]);
```

If you pass additional parameters in the array, those key / value pairs will automatically be added to the generated URL's query string:
```
Route::get('/user/{id}/profile', function ($id) {
    //
})->name('profile');

$url = route('profile', ['id' => 1, 'photos' => 'yes']);

// /user/1/profile?photos=yes
```

--
## Inspecting The Current Route
If you would like to determine if the current request was routed to a given named route, you may use the named method on a Route instance

```
/**
 * Handle an incoming request.
 *
 * @param  \Illuminate\Http\Request  $request
 * @param  \Closure  $next
 * @return mixed
 */
public function handle($request, Closure $next)
{
    if ($request->route()->named('profile')) {
        //
    }

    return $next($request);
}
```

