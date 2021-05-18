# Introduction


--
## Introduction

Of course, we don't return entire HTML documents strings directly from routes and controllers 

Views 
- provide a convenient way to place all of our HTML in separate files
- separate your controller / application logic from your presentation logic 
- stored in the resources/views directory

--
## Views
A simple view might look something like this:
```
<!-- View stored in resources/views/greeting.blade.php -->

<html>
    <body>
        <h1>Hello, {{ $name }}</h1>
    </body>
</html>
```

--
## Views
Since this view is stored at resources/views/greeting.blade.php, we return it using the global view helper
```
Route::get('/', function () {
    return view('greeting', ['name' => 'James']);
});
```

> More about blade templates later!

--
## Creating & Rendering Views
Views are created by placing a file with the .blade.php extension in your application's resources/views directory 
- Blade templates contain HTML as well as Blade directives that allow you to easily 
    - echo values,
    - create "if" statements
    - iterate over data, and more


--
## Creating & Rendering Views
Once you have created a view, you may return it from one of your application's routes or controllers using the global view helper

```
Route::get('/', function () {
    return view('greeting', ['name' => 'James']);
});
```

--
## Creating & Rendering Views
Views may also be returned using the View facade:
```
use Illuminate\Support\Facades\View;

return View::make('greeting', ['name' => 'James']);
```

--
## Nested View Directories
Views may also be nested within subdirectories of the resources/views directory
- for example, view stored at resources/views/admin/profile.blade.php 

```
return view('admin.profile', $data); // note the dot here
```

View directory names should not contain the . character.

--
## Creating The First Available View
Using the View facade's first method, you may create the first view that exists in a given array of views 

This may be useful if your application or package allows views to be customized or overwritten:

```
use Illuminate\Support\Facades\View;
return View::first(['custom.admin', 'admin'], $data);
```

--
## Determining If A View Exists
If you need to determine if a view exists, you may use the View facade

The exists method will return true if the view exists:
```
use Illuminate\Support\Facades\View;

if (View::exists('emails.customer')) {
    //
}
```

--
## Passing Data To Views
As you saw in the previous examples, you may pass an array of data to views:
```
return view('greetings', ['name' => 'Victoria']);
```
When passing information in this manner, the data should be an array with key / value pairs. After providing data to a view, access each value using the data's keys, such as 

```
<?php echo $name; ?>.
```

--
## Passing Data To Views
As an alternative to passing a complete array of data to the view helper function, you may use the with method to add individual pieces of data to the view
```
return view('greeting')
            ->with('name', 'Victoria')
            ->with('occupation', 'Astronaut');
```

--
## Sharing Data With All Views
Occasionally, you may need to share data with all views that are rendered by your application
- use the View facade's share method

Typically, you should place calls to the share method within a service provider's boot method
```
<?php
namespace App\Providers;
use Illuminate\Support\Facades\View;
class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        View::share('key', 'value');
    }
}
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Views


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!

--
## View Composers
View composers are callbacks or class methods that are called when a view is rendered

If you have data that you want to be bound to a view each time that view is rendered, a view composer can help you organize that logic into a single location

Typically, view composers will be registered within one of your application's service providers. In this example, we'll assume that we have created a new App\Providers\ViewServiceProvider to house this logic.

--
## View Composers
We'll use the View facade's composer method to register the view composer
```
<?php
namespace App\Providers;
use App\Http\View\Composers\ProfileComposer;
use Illuminate\Support\Facades\View;
use Illuminate\Support\ServiceProvider;

class ViewServiceProvider extends ServiceProvider
{
    ...
    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        // Using class based composers...
        View::composer('profile', ProfileComposer::class);

        // Using closure based composers...
        View::composer('dashboard', function ($view) {
            //
        });
    }
}
```

Remember, if you create a new service provider to contain your view composer registrations, you will need to add the service provider to the providers array in the config/app.php configuration file.

--
## View Composers
Now that we have registered the composer, the compose method of the App\Http\View\Composers\ProfileComposer class will be executed each time the profile view is being rendered
```
<?php
namespace App\Http\View\Composers;
use App\Repositories\UserRepository;
use Illuminate\View\View;
class ProfileComposer
{
    /**
     * The user repository implementation.
     *
     * @var \App\Repositories\UserRepository
     */
    protected $users;

    /**
     * Create a new profile composer.
     *
     * @param  \App\Repositories\UserRepository  $users
     * @return void
     */
    public function __construct(UserRepository $users)
    {
        // Dependencies are automatically resolved by the service container...
        $this->users = $users;
    }

    /**
     * Bind data to the view.
     *
     * @param  \Illuminate\View\View  $view
     * @return void
     */
    public function compose(View $view)
    {
        $view->with('count', $this->users->count());
    }
}
```

As you can see, all view composers are resolved via the service container, so you may type-hint any dependencies you need within a composer's constructor.

--
## Attaching A Composer To Multiple Views
You may attach a view composer to multiple views at once by passing an array of views as the first argument to the composer method:

```
use App\Http\Views\Composers\MultiComposer;

View::composer(
    ['profile', 'dashboard'],
    MultiComposer::class
);
```

--
## Attaching A Composer To Multiple Views
The composer method also accepts the * character as a wildcard, allowing you to attach a composer to all views:
```
View::composer('*', function ($view) {
    //
});
```

--
## View Creators
View "creators" are very similar to view composers; however, they are executed immediately after the view is instantiated instead of waiting until the view is about to render

```
use App\Http\View\Creators\ProfileCreator;
use Illuminate\Support\Facades\View;

View::creator('profile', ProfileCreator::class);
```

--
## Optimizing Views
By default, Blade template views are compiled on demand
- when a request is executed that renders a view, Laravel checks if a compiled version of the view exists

If the file exists, Laravel will then determine if the uncompiled view has been modified more recently than the compiled view and when needed Laravel will recompile the view.

--
## Optimizing Views
Compiling views during the request may have a small negative impact on performance, so Laravel provides - view:cache Artisan command to precompile all of the views utilized by your application

For increased performance, you may wish to run this command as part of your deployment process
```
php artisan view:cache
```
You may use the view:clear command to clear the view cache:
```
php artisan view:clear
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. View Composers

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!
