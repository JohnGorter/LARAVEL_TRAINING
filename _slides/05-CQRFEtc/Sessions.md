# HTTP Session

--
## Introduction
Since HTTP driven applications are stateless, sessions provide a way to store information about the user across multiple requests. 

That user information is typically placed in a persistent store / backend that can be accessed from subsequent requests

--
## Introduction
Laravel ships with a variety of session backends that are accessed through an expressive, unified API. Support for popular backends such as Memcached, Redis, and databases is included

--
## Configuration
Your application's session configuration file is stored at config/session.php. 
 
 By default, Laravel is configured to use the file session driver
 
 If your application will be load balanced across multiple web servers, you should choose a centralized store that all servers can access, such as Redis or a database

--
## Configuration
The session driver configuration option defines where session data will be stored for each request. Laravel ships with several great drivers out of the box
- file - sessions are stored in storage/framework/sessions.
- cookie - sessions are stored in secure, encrypted cookies.
- database - sessions are stored in a relational database.
- memcached / redis - sessions are stored in one of these fast, cache based stores.
- dynamodb - sessions are stored in AWS DynamoDB.
- array - sessions are stored in a PHP array and will not be persisted.

The array driver is primarily used during testing and prevents the data stored in the session from being persisted

--
## Driver Prerequisites
- Database
When using the database session driver, you will need to create a table to contain the session records. 

An example Schema declaration for the table may be found below:
```
Schema::create('sessions', function ($table) {
    $table->string('id')->primary();
    $table->foreignId('user_id')->nullable()->index();
    $table->string('ip_address', 45)->nullable();
    $table->text('user_agent')->nullable();
    $table->text('payload');
    $table->integer('last_activity')->index();
});
```

You may use the session:table Artisan command to generate this migration
```
php artisan session:table
php artisan migrate
```

--
## Redis
Before using Redis sessions with Laravel, you will need to either install the PhpRedis PHP extension via PECL or install the predis/predis package (~1.0) via Composer

In the session configuration file, the connection option may be used to specify which Redis connection is used by the session.

--
## Interacting With The Session
Retrieving Data
There are two primary ways of working with session data in Laravel
- the global session helper 
- via a Request instance


--
## Interacting With The Session
The request instance
```
<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class UserController extends Controller
{
    /**
     * Show the profile for the given user.
     *
     * @param  Request  $request
     * @param  int  $id
     * @return Response
     */
    public function show(Request $request, $id)
    {
        $value = $request->session()->get('key');
        // or 
        $value = $request->session()->get('key', 'default');
    }
}
```
--
## Interacting With The Session
The Global Session Helper

You may also use the global session PHP function to retrieve and store data in the session

```
Route::get('/home', function () {
    // Retrieve a piece of data from the session...
    $value = session('key');

    // Specifying a default value...
    $value = session('key', 'default');

    // Store a piece of data in the session...
    session(['key' => 'value']);
});
```

There is little practical difference between using the session via an HTTP request instance versus using the global session helper

--
## Retrieving All Session Data
If you would like to retrieve all the data in the session, you may use the all method

```
$data = $request->session()->all();
```

--
## Determining If An Item Exists In The Session
To determine if an item is present in the session, you may use the has method. 
```
if ($request->session()->has('users')) {
    //
}
```
To determine if an item is present in the session, even if its value is null, you may use the exists method
```
if ($request->session()->exists('users')) {
    //
}
```

--
## Determining If An Item Exists In The Session
To determine if an item is not present in the session, you may use the missing method
```
if ($request->session()->missing('users')) {
    //
}
```

--
## Storing Data
To store data in the session, you will typically use the request instance's put method or the session helper
```
// Via a request instance...
$request->session()->put('key', 'value');

// Via the global "session" helper...
session(['key' => 'value']);
```

--
## Pushing To Array Session Values
The push method may be used to push a new value onto a session value that is an array. For example, if the user.teams key contains an array of team names, you may push a new value onto the array like so
```
$request->session()->push('user.teams', 'developers');
```

--
## Retrieving & Deleting An Item
The pull method will retrieve and delete an item from the session in a single statement
```
$value = $request->session()->pull('key', 'default');
```

--
## Incrementing & Decrementing Session Values
If your session data contains an integer you wish to increment or decrement, you may use the increment and decrement methods

```
$request->session()->increment('count');
$request->session()->increment('count', $incrementBy = 2);
$request->session()->decrement('count');
$request->session()->decrement('count', $decrementBy = 2);
```

--
## Flash Data
Sometimes you may wish to store items in the session for the next request. You may do so using the flash method

Data stored in the session using this method will be available immediately and during the subsequent HTTP request

```
$request->session()->flash('status', 'Task was successful!');
```

--
## Flash Data
If you need to persist your flash data for several requests, you may use the reflash method, which will keep all of the flash data for an additional request. 

If you only need to keep specific flash data, you may use the keep method

```
$request->session()->reflash();

$request->session()->keep(['username', 'email']);
```

--
## Flash Data
To persist your flash data only for the current request, you may use the now method

```
$request->session()->now('status', 'Task was successful!');
```

--
## Deleting Data
The forget method will remove a piece of data from the session. If you would like to remove all data from the session, you may use the flush method:

```
// Forget a single key...
$request->session()->forget('name');

// Forget multiple keys...
$request->session()->forget(['name', 'status']);

$request->session()->flush();
```

--
## Regenerating The Session ID
Regenerating the session ID is often done in order to prevent malicious users from exploiting a session fixation attack on your application.

If you need to manually regenerate the session ID, you may use the regenerate method:
```
$request->session()->regenerate();
```
If you need to regenerate the session ID and remove all data from the session in a single statement, you may use the invalidate method:
```
$request->session()->invalidate();
```

--
## Session Blocking
To utilize session blocking, your application must be using a cache driver that supports atomic locks. 

Currently, those cache drivers include the memcached, dynamodb, redis, and database drivers. In addition, you may not use the cookie session driver.

```
Route::post('/profile', function () {
    //
})->block($lockSeconds = 10, $waitSeconds = 10)

Route::post('/order', function () {
    //
})->block($lockSeconds = 10, $waitSeconds = 10)

Route::post('/profile', function () {
    //
})->block()
```

--
## Adding Custom Session Drivers
Implementing The Driver
It is possible to write your own session handler. Your custom session driver should implement PHP's built-in SessionHandlerInterface. This interface contains just a few simple methods

```
<?php

namespace App\Extensions;

class MongoSessionHandler implements \SessionHandlerInterface
{
    public function open($savePath, $sessionName) {}
    public function close() {}
    public function read($sessionId) {}
    public function write($sessionId, $data) {}
    public function destroy($sessionId) {}
    public function gc($lifetime) {}
}
```

Laravel does not ship with a directory to contain your extensions. 

You are free to place them anywhere you like

--
## Registering The Driver
Once your driver has been implemented, you are ready to register it with Laravel

To add additional drivers to Laravel's session backend, you may use the extend method provided by the Session facade

```
<?php

namespace App\Providers;

use App\Extensions\MongoSessionHandler;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\ServiceProvider;

class SessionServiceProvider extends ServiceProvider
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
        Session::extend('mongo', function ($app) {
            // Return an implementation of SessionHandlerInterface...
            return new MongoSessionHandler;
        });
    }
}
```

Once the session driver has been registered, you may use the 
driver in your config/session.php configuration file.

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Session Demo


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!