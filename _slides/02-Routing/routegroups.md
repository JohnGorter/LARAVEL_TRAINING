# Route Groups

--
## Route Groups
Route groups allow you to share route attributes, such as middleware, across a large number of routes without needing to define those attributes on each individual route

--
## Middleware
To assign middleware to all routes within a group, you may use the middleware method

```
Route::middleware(['first', 'second'])->group(function () {
    Route::get('/', function () {
        // Uses first & second middleware...
    });

    Route::get('/user/profile', function () {
        // Uses first & second middleware...
    });
});
```

--
## Subdomain Routing
Route groups may also be used to handle subdomain routing

```
Route::domain('{account}.example.com')->group(function () {
    Route::get('user/{id}', function ($account, $id) {
        //
    });
});
```

--
## Route Prefixes
The prefix method may be used to prefix each route in the group with a given URI. For example, you may want to prefix all route URIs within the group with admin

```
Route::prefix('admin')->group(function () {
    Route::get('/users', function () {
        // Matches The "/admin/users" URL
    });
});
```

--
## Route Name Prefixes
The name method may be used to prefix each route name in the group with a given string. For example, you may want to prefix all of the grouped route's names with admin.

```
Route::name('admin.')->group(function () {
    Route::get('/users', function () {
        // Route assigned name "admin.users"...
    })->name('users');
});
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Basic Routing