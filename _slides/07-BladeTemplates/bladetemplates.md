
# Introduction

--
## Introduction
Blade is the simple, yet powerful templating engine that is included with Laravel
- Blade does not restrict you from using plain PHP code in your templates
- all Blade templates are compiled into plain PHP code and cached 

Blade template files 
- use the .blade.php file extension 
- are typically stored in the resources/views directory

--
## Introduction
Blade views may be returned from routes or controller using the global view helper
- data may be passed to the Blade view using the view helper's second argument

```
Route::get('/', function () {
    return view('greeting', ['name' => 'Finn']);
});
```

--
## Displaying Data
You may display data that is passed to your Blade views by wrapping the variable in curly braces. For example, given the following route

```
Route::get('/', function () {
    return view('welcome', ['name' => 'Samantha']);
});
```
You may display the contents of the name variable like so:
```
Hello, {{ $name }}.
```

Blade's {{ }} echo statements are automatically sent through PHP's htmlspecialchars function to prevent XSS attacks


--
## Displaying Data
You are not limited to displaying the contents of the variables passed to the view. You may also echo the results of any PHP function

```
The current UNIX timestamp is {{ time() }}.
```

--
## Rendering JSON
Sometimes you may pass an array to your view with the intention of rendering it as JSON in order to initialize a JavaScript variable. For example:
```
<script>
    var app = <?php echo json_encode($array); ?>;
</script>
```
However, instead of manually calling json_encode, you may use the @json Blade directive
- by default, the @json directive calls the json_encode function with the JSON_HEX_TAG, JSON_HEX_APOS, JSON_HEX_AMP, and JSON_HEX_QUOT flags
```
<script>
    var app = @json($array);
    var app = @json($array, JSON_PRETTY_PRINT);
</script>
```

--
## Displaying Unescaped Data
Blade {{ }} statements are automatically sent through PHP's htmlspecialchars function to prevent XSS attacks. 

If you do not want your data to be escaped, you may use the following syntax:
```
Hello, {!! $name !!}.
```

Be very careful when echoing content that is supplied by users of your application

--
## Blade & JavaScript Frameworks
Many JavaScript frameworks also use "curly" braces 
- to indicate a given expression should be displayed in the browser, use the @ symbol 

```
<h1>Laravel</h1>

Hello, @{{ name }}.
```

In this example, the @ symbol will be removed by Blade; however, {{ name }} expression will remain untouched

--
## Blade & JavaScript Frameworks
The @ symbol may also be used to escape Blade directives:
```
{{-- Blade template --}}
@@json()

<!-- HTML output -->
@json()
```

--
## The @verbatim Directive
If you are displaying JavaScript variables in a large portion of your template, you may wrap the HTML in the @verbatim directive so that you do not have to prefix each Blade echo statement with an @ symbol:
```
@verbatim
    <div class="container">
        Hello, {{ name }}.
    </div>
@endverbatim
```

--
## Blade Directives
In addition to template inheritance and displaying data, Blade also provides convenient shortcuts for common PHP control structures, such as conditional statements and loops

--
## If Statements
You may construct if statements using the @if, @elseif, @else, and @endif directives

```
@if (count($records) === 1)
    I have one record!
@elseif (count($records) > 1)
    I have multiple records!
@else
    I don't have any records!
@endif
```

--
## If Statements
For convenience, Blade also provides an @unless directive:
```
@unless (Auth::check())
    You are not signed in.
@endunless
```

--
## Isset Statements
In addition to the conditional directives already discussed, the @isset and @empty directives may be used as convenient shortcuts for their respective PHP functions:

```
@isset($records)
    // $records is defined and is not null...
@endisset

@empty($records)
    // $records is "empty"...
@endempty
```

--
## Authentication Directives
The @auth and @guest directives may be used to quickly determine if the current user is authenticated or is a guest:
```
@auth
    // The user is authenticated...
@endauth

@guest
    // The user is not authenticated...
@endguest
```

--
## Authentication Directives
If needed, you may specify the authentication guard that should be checked when using the @auth and @guest directives

```
@auth('admin')
    // The user is authenticated...
@endauth

@guest('admin')
    // The user is not authenticated...
@endguest
```

--
## Environment Directives
You may check if the application is running in the production environment using the @production directive

```
@production
    // Production specific content...
@endproduction
```

--
## Environment Directives
Or, you may determine if the application is running in a specific environment using the @env directive:
```
@env('staging')
    // The application is running in "staging"...
@endenv

@env(['staging', 'production'])
    // The application is running in "staging" or "production"...
@endenv
```

--
## Section Directives
You may determine if a template inheritance section has content using the @hasSection directive:
```
@hasSection('navigation')
    <div class="pull-right">
        @yield('navigation')
    </div>

    <div class="clearfix"></div>
@endif
```


--
## Section Directives
You may use the sectionMissing directive to determine if a section does not have content:

```
@sectionMissing('navigation')
    <div class="pull-right">
        @include('default-navigation')
    </div>
@endif
```

--
## Switch Statements
Switch statements can be constructed using the @switch, @case, @break, @default and @endswitch directives
```
@switch($i)
    @case(1)
        First case...
        @break

    @case(2)
        Second case...
        @break

    @default
        Default case...
@endswitch
```

--
## Loops
In addition to conditional statements, Blade provides simple directives for working with PHP's loop structures

```
@for ($i = 0; $i < 10; $i++)
    The current value is {{ $i }}
@endfor

@foreach ($users as $user)
    <p>This is user {{ $user->id }}</p>
@endforeach

@forelse ($users as $user)
    <li>{{ $user->name }}</li>
@empty
    <p>No users</p>
@endforelse

@while (true)
    <p>I'm looping forever.</p>
@endwhile
```

--
## Loops
When looping
- you may use the loop variable to gain information about the loop, for example if you are in the first or last iteration through the loop
- you may also end the loop or skip the current iteration using the @continue and @break directives

```
@foreach ($users as $user)
    @if ($user->type == 1)
        @continue
    @endif

    <li>{{ $user->name }}</li>

    @if ($user->number == 5)
        @break
    @endif
@endforeach
```

--
## Loops
You may also include the continuation or break condition within the directive declaration
```
@foreach ($users as $user)
    @continue($user->type == 1)

    <li>{{ $user->name }}</li>

    @break($user->number == 5)
@endforeach
```

--
## Loops
The Loop Variable
```
@foreach ($users as $user)
    @if ($loop->first)
        This is the first iteration.
    @endif

    @if ($loop->last)
        This is the last iteration.
    @endif

    <p>This is user {{ $user->id }}</p>
@endforeach
```


--
## Loops
If you are in a nested loop, you may access the parent loop's $loop variable via the parent property

```
@foreach ($users as $user)
    @foreach ($user->posts as $post)
        @if ($loop->parent->first)
            This is the first iteration of the parent loop.
        @endif
    @endforeach
@endforeach
```


--
## Loops
The $loop variable also contains a variety of other useful properties

Property|Description
---|---
$loop->index|The index of the current loop iteration (starts at 0)
$loop->iteration|The current loop iteration (starts at 1)
$loop->remaining|The iterations remaining in the loop
$loop->count|The total number of items in the array being iterated
$loop->first|Whether this is the first iteration through the loop
$loop->last|Whether this is the last iteration through the loop
$loop->even|Whether this is an even iteration through the loop
$loop->odd|Whether this is an odd iteration through the loop
$loop->depth|The nesting level of the current loop
$loop->parent|When in a nested loop, the parent's loop variable

--
## Comments
Blade also allows you to define comments in your views
- blade comments are not included in the HTML returned 

```
{{-- This comment will not be present in the rendered HTML --}}
```

--
## Including Subviews
Blade's @include directive allows you to include a Blade view from within another view
- all variables that are available to the parent view will be made available to the included view

```
<div>
    @include('shared.errors')

    <form>
        <!-- Form Contents -->
    </form>
</div>
```
> While you're free to use the @include directive, Blade components provide similar functionality and offer several benefits over the @include directive such as data and attribute binding.

--
## Including Subviews
Even though the included view will inherit all data available in the parent view, you can pass additional data

```
@include('view.name', ['status' => 'complete'])
```

If you attempt to @include a view which does not exist, Laravel will throw an error

--
## Including Subviews
If you would like to include a view that may or may not be present, use the @includeIf
```
@includeIf('view.name', ['status' => 'complete'])
```

--
## Including Subviews
If you would like to @include a view if a boolean expression is true or false, use the @includeWhen and @includeUnless directives:
```
@includeWhen($boolean, 'view.name', ['status' => 'complete'])

@includeUnless($boolean, 'view.name', ['status' => 'complete'])
```

--
## Including Subviews
To include the first view that exists from a given array of views, use the includeFirst directive

```
@includeFirst(['custom.admin', 'admin'], ['status' => 'complete'])
```

> You should avoid using the __DIR__ and __FILE__ constants in your Blade views, since they will refer to the location of the cached, compiled view.


--
## Rendering Views For Collections
You may combine loops and includes into one line with Blade's @each directive

```
@each('view.name', $jobs, 'job')
```

The @each directive's 
- first argument is the view to render for each element in the array 
- second argument is the array or collection
- third argument is the variable name that will be assigned to the current iteration within the view

--
## Rendering Views For Collections
You may also pass a fourth argument to the @each directive. This argument determines the view that will be rendered if the given array is empty.

```
@each('view.name', $jobs, 'job', 'view.empty')
```

--
## Rendering Views For Collections
Views rendered via @each do not inherit the variables from the parent view!
If the child view requires these variables
- use the @foreach and @include directives


--
## The @once Directive
The @once directive allows you to define a portion of the template that will only be evaluated once per rendering cycle

For example, if you are rendering a given component within a loop, you may wish to only push the JavaScript to the header the first time the component is rendered

```
@once
    @push('scripts')
        <script>
            // Your custom JavaScript...
        </script>
    @endpush
@endonce
```

--
## Raw PHP
In some situations, it's useful to embed PHP code into your views
- use the Blade @php directive 
```
@php
    $counter = 1;
@endphp
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Blade Templates 

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!