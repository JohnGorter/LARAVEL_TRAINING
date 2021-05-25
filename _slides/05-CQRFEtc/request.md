# HTTP Requests


--
## Introduction
Laravel's Illuminate\Http\Request class provides an object-oriented way to interact with the current HTTP request.

You can access the 
- input
- cookies
- files 
that were submitted with the request

--
## Interacting With The Request
Accessing The Request through injection

```
<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class UserController extends Controller
{
    /**
     * Store a new user.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $name = $request->input('name');

        //
    }
}
```

You may also type-hint the Illuminate\Http\Request class on a route closure
```
use Illuminate\Http\Request;

Route::get('/', function (Request $request) {
    //
});
```

--
## Dependency Injection & Route Parameters
If your controller method is also expecting input from a route parameter you should list your route parameters after your other dependencies

```
use App\Http\Controllers\UserController;
Route::put('/user/{id}', [UserController::class, 'update']);
```

```
<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class UserController extends Controller
{
    /**
     * Update the specified user.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }
}
```

--
## Request Path & Method
The Illuminate\Http\Request instance provides a variety of methods for examining the incoming HTTP request

- Retrieving The Request Path
```
$uri = $request->path();  
```
- Inspecting The Request Path / Route using the is and routeIs method
```
if ($request->is('admin/*')) {
    //
}
if ($request->routeIs('admin.*')) {
    //
}
```
--
## Request Path & Method

- Retrieving The Request URL usig url or fullUrl 
```
$url = $request->url();
$urlWithQueryString = $request->fullUrl();
$request->fullUrlWithQuery(['type' => 'phone']);
```

- Retrieving The Request Method
```
$method = $request->method();
if ($request->isMethod('post')) {
    //
}
```
- Retrieving Request Headers
```
$value = $request->header('X-Header-Name');
$value = $request->header('X-Header-Name', 'default');
if ($request->hasHeader('X-Header-Name')) {
    //
}
$token = $request->bearerToken();
```

--
## Request Path & Method

- Request IP Address
```
$ipAddress = $request->ip();
```

--
## Content Negotiation
Laravel provides several methods for inspecting the incoming request's requested content types via the Accept header. 

First, the getAcceptableContentTypes method will return an array containing all of the content types accepted by the request:

```
$contentTypes = $request->getAcceptableContentTypes();
```

--
## Content Negotiation
The accepts method accepts an array of content types and returns true if any of the content types are accepted by the request. Otherwise, false will be returned:

```
if ($request->accepts(['text/html', 'application/json'])) {
    // ...
}
```
You may use the prefers method to determine which content type out of a given array of content types is most preferred by the request
```
$preferred = $request->prefers(['text/html', 'application/json']);
```

--
## Content Negotiation
Since many applications only serve HTML or JSON, you may use the expectsJson method to quickly determine if the incoming request expects a JSON response
```
if ($request->expectsJson()) {
    // ...
}
```


--
## Input
Retrieving Input
Retrieving All Input Data
```
$input = $request->all();
```
Retrieving An Input Value
```
$name = $request->input('name');
$name = $request->input('name', 'Sally');
```

--
## Input
When working with forms that contain array inputs, use "dot" notation to access the arrays

```
$name = $request->input('products.0.name');
$names = $request->input('products.*.name');
```

You may call the input method without any arguments in order to retrieve all of the input values as an associative array
```
$input = $request->input();
```

--
## Retrieving Input From The Query String
While the input method retrieves values from the entire request payload (including the query string), the query method will only retrieve values from the query string
```
$name = $request->query('name');
```

If the requested query string value data is not present, the second argument to this method will be returned:
```
$name = $request->query('name', 'Helen');
```

--
## Retrieving Input From The Query String
You may call the query method without any arguments in order to retrieve all of the query string values as an associative array
```
$query = $request->query();
```

--
## Retrieving JSON Input Values
When sending JSON requests to your application, you may access the JSON data via the input method as long as the Content-Type header of the request is properly set to application/json

```
$name = $request->input('user.name');
```

--
## Retrieving Boolean Input Values
When dealing with HTML elements like checkboxes, your application may receive "truthy" values that are actually strings. For example, "true" or "on". For convenience, you may use the boolean method to retrieve these values as booleans

```
$archived = $request->boolean('archived');
```

--
## Retrieving Input Via Dynamic Properties
You may also access user input using dynamic properties on the Illuminate\Http\Request instance. For example, if one of your application's forms contains a name field, you may access the value of the field like so:
```
$name = $request->name;
```

When using dynamic properties, Laravel will first look for the parameter's value in the request payload. If it is not present, Laravel will search for the field in the matched route's parameters

--
## Retrieving A Portion Of The Input Data
If you need to retrieve a subset of the input data, you may use the only and except methods. Both of these methods accept a single array or a dynamic list of arguments

```
$input = $request->only(['username', 'password']);
$input = $request->only('username', 'password');
$input = $request->except(['credit_card']);
$input = $request->except('credit_card');
```

--
## Determining If Input Is Present
You may use the has method to determine if a value is present on the request. 

```
check for existence
if ($request->has('name')) {}
// check multiple
if ($request->has(['name', 'email'])) {}
// run closure when name is present
$request->whenHas('name', function ($input) {});
// run close when one is present
if ($request->hasAny(['name', 'email'])) {}
// check for empty
if ($request->filled('name')) {}
// check for non empty
$request->whenFilled('name', function ($input) {});
// check for absence 
if ($request->missing('name')) {}
```

--
## Old Input
Laravel allows you to keep input from one request during the next request. This feature is particularly useful for re-populating forms after detecting validation errors


--
## Flashing Input To The Session
The flash method on the Illuminate\Http\Request class will flash the current input to the session so that it is available during the user's next request to the application
```
$request->flash();
```
You may also use the flashOnly and flashExcept methods to flash a subset of the request data to the session

These methods are useful for keeping sensitive information such as passwords out of the session:
```
$request->flashOnly(['username', 'email']);
$request->flashExcept('password');
```

--
## Flashing Input Then Redirecting
Since you often will want to flash input to the session and then redirect to the previous page, you may easily chain input flashing onto a redirect using the withInput method
```
return redirect('form')->withInput();
return redirect()->route('user.create')->withInput();
return redirect('form')->withInput(
    $request->except('password')
);
```

--
## Retrieving Old Input
To retrieve flashed input from the previous request, invoke the old method on an instance of Illuminate\Http\Request

```
$username = $request->old('username');
```

Laravel also provides a global old helper. If you are displaying old input within a Blade template, it is more convenient to use the old helper to repopulate the form
```
<input type="text" name="username" value="{{ old('username') }}">
```

--
## Cookies
Retrieving Cookies From Requests
All cookies created by the Laravel framework are encrypted and signed with an authentication code, meaning they will be considered invalid if they have been changed by the client

```
$value = $request->cookie('name');
```

--
## Input Trimming & Normalization
By default, Laravel includes the App\Http\Middleware\TrimStrings and App\Http\Middleware\ConvertEmptyStringsToNull 
- automatically trim all incoming string fields on the request
- converts any empty string fields to null


If you would like to disable this behavior, you may remove the two middleware from your application's middleware stack by removing them from the $middleware property of your App\Http\Kernel class

--
## Files
Retrieving Uploaded Files

```
$file = $request->file('photo');
$file = $request->photo;
```

--
## Files
You may determine if a file is present on the request using the hasFile method:
```
if ($request->hasFile('photo')) {
    //
}
```

--
## Validating Successful Uploads
In addition to checking if the file is present, you may verify that there were no problems uploading the file via the isValid method:
```
if ($request->file('photo')->isValid()) {
    //
}
```

--
## File Paths & Extensions
The UploadedFile class also contains methods for accessing the file's fully-qualified path and its extension

```
$path = $request->photo->path();
$extension = $request->photo->extension();
```

--
## Other File Methods
There are a variety of other methods available on UploadedFile instance
- Storing Uploaded Files
```
$path = $request->photo->store('images');
$path = $request->photo->store('images', 's3');
```
If you do not want a filename to be automatically generated, you may use the storeAs method, which accepts the path, filename, and disk name as its arguments
```
$path = $request->photo->storeAs('images', 'filename.jpg');
$path = $request->photo->storeAs('images', 'filename.jpg', 's3');
```

--
## Configuring Trusted Proxies
When running your applications behind a load balancer that terminates TLS / SSL certificates, notice your application sometimes does not generate HTTPS links when using the url helper. 

Typically this is because your application is being forwarded traffic from your load balancer on port 80 and does not know it should generate secure links

--
## Configuring Trusted Proxies
To solve this, you may use the App\Http\Middleware\TrustProxies middleware that is included in your Laravel application, which allows you to quickly customize the load balancers or proxies that should be trusted by your application. 

Your trusted proxies should be listed as an array on the $proxies property of this middleware. In addition to configuring the trusted proxies, you may configure the proxy $headers that should be trusted

```
<?php

namespace App\Http\Middleware;

use Fideloper\Proxy\TrustProxies as Middleware;
use Illuminate\Http\Request;

class TrustProxies extends Middleware
{
    /**
     * The trusted proxies for this application.
     *
     * @var string|array
     */
    protected $proxies = [
        '192.168.1.1',
        '192.168.1.2',
    ];

    /**
     * The headers that should be used to detect proxies.
     *
     * @var int
     */
    protected $headers = Request::HEADER_X_FORWARDED_FOR | Request::HEADER_X_FORWARDED_HOST | Request::HEADER_X_FORWARDED_PORT | Request::HEADER_X_FORWARDED_PROTO;
}
```

--
## Configuring Trusted Proxies
If you are using AWS Elastic Load Balancing, your $headers value should be Request::HEADER_X_FORWARDED_AWS_ELB. For more information on the constants that may be used in the $headers property, check out Symfony's documentation on trusting proxies.


--
## Trusting All Proxies
If you are using Amazon AWS or another "cloud" load balancer provider, you may not know the IP addresses of your actual balancers. In this case, you may use * to trust all proxies
```
/**
 * The trusted proxies for this application.
 *
 * @var string|array
 */
protected $proxies = '*';
```

--
## Configuring Trusted Hosts
By default, Laravel will respond to all requests it receives regardless of the content of the HTTP request's Host header. In addition, the Host header's value will be used when generating absolute URLs to your application during a web request

Typically, you should configure your web server, such as Nginx or Apache, to only send requests to your application that match a given host name. However, if you do not have the ability to customize your web server directly and need to instruct Laravel to only respond to certain host names, you may do so by enabling the App\Http\Middleware\TrustHosts middleware for your application

--
## Configuring Trusted Hosts
The TrustHosts middleware is already included in the $middleware stack of your application; 

however, you should uncomment it so that it becomes active. 

Within this middleware's hosts method, you may specify the host names that your application should respond to. Incoming requests with other Host value headers will be rejected:

```
/**
 * Get the host patterns that should be trusted.
 *
 * @return array
 */
public function hosts()
{
    return [
        'laravel.test',
        $this->allSubdomainsOfApplicationUrl(),
    ];
}
```
The allSubdomainsOfApplicationUrl helper method will return a regular expression matching all subdomains of your application's app.url configuration value 

This helper method provides a convenient way to allow all of your application's subdomains when building an application that utilizes wildcard subdomains.

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. CSRF Demo


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!