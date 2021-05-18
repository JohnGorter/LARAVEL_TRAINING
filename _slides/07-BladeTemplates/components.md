# Components

--
## Components
Components and slots provide similar benefits to sections, layouts, and includes
- some may find the mental model of components and slots easier to understand

Two approaches to writing components
- class based components 
- anonymous components

--
## Class based components
To create a class based component, use the make:component Artisan command
- for example a simple Alert component
```
php artisan make:component Alert
```

Components are automatically discovered within the app/View/Components directory and resources/views/components directory, so no further component registration is typically required

--
## Class based components
You may also create components within subdirectories:

```
php artisan make:component Forms/Input
```

The command above will create an Input component in the App\View\Components\Forms directory and the view will be placed in the resources/views/components/forms directory

--
## Rendering Components
To display a component, you may use a Blade component tag within one of your Blade templates

Blade component tags start with the string x- followed by the kebab case name of the component class
```
<x-alert/>

<x-user-profile/>
```

--
## Rendering Components
If the component class is nested deeper within the App\View\Components directory, you may use the . character to indicate directory nesting

```
<x-inputs.button/>
```

--
## Passing Data To Components
You may pass data to Blade components using 
- HTML attributes

- Hard-coded, primitive values may be passed  using simple HTML attribute strings. 
- PHP expressions and variables should be passed  via attributes that use the : character as a prefix

```
<x-alert type="error" :message="$message"/>
```

--
## Passing Data To Components
You should define the component's required data in its class constructor 

All public properties on a component will automatically be made available to the component's view

```
<?php
namespace App\View\Components;
use Illuminate\View\Component;

class Alert extends Component
{
    /**
     * The alert type.
     *
     * @var string
     */
    public $type;

    /**
     * The alert message.
     *
     * @var string
     */
    public $message;

    /**
     * Create the component instance.
     *
     * @param  string  $type
     * @param  string  $message
     * @return void
     */
    public function __construct($type, $message)
    {
        $this->type = $type;
        $this->message = $message;
    }

    /**
     * Get the view / contents that represent the component.
     *
     * @return \Illuminate\View\View|\Closure|string
     */
    public function render()
    {
        return view('components.alert');
    }
}
```

--
## Passing Data To Components
When your component is rendered, you may display the contents of your component's public variables by echoing the variables by name:

```
<div class="alert alert-{{ $type }}">
    {{ $message }}
</div>
```

--
## Casing
Component constructor arguments should be specified using camelCase

Kebab-case should be used when referencing the argument names in your HTML attributes. For example, given the following component constructor:

```
/**
 * Create the component instance.
 *
 * @param  string  $alertType
 * @return void
 */
public function __construct($alertType)
{
    $this->alertType = $alertType;
}
```
The $alertType argument may be provided to the component like so:
```
<x-alert alert-type="danger" />
```

--
##  Escaping Attribute Rendering
Since some JavaScript frameworks such as Alpine.js also use colon-prefixed attributes, you may use a double colon (::) prefix to inform Blade that the attribute is not a PHP expression

```
<x-button ::class="{ danger: isDeleting }">
    Submit
</x-button>
```
The following HTML will be rendered by Blade:
```
<button :class="{ danger: isDeleting }">
    Submit
</button>
```

--
## Component Methods
In addition to public variables being available to your component template, any public methods on the component may be invoked

```
/**
 * Determine if the given option is the currently selected option.
 *
 * @param  string  $option
 * @return bool
 */
public function isSelected($option)
{
    return $option === $this->selected;
}
```

You may execute this method from your component template by invoking the variable matching the name of the method
```
<option {{ $isSelected($value) ? 'selected="selected"' : '' }} value="{{ $value }}">
    {{ $label }}
</option>
```

--
## Accessing Attributes & Slots Within Component Classes
Blade components also allow you to access 
- component name
- attributes
- slot 

```
/**
 * Get the view / contents that represent the component.
 *
 * @return \Illuminate\View\View|\Closure|string
 */
public function render()
{
    return function (array $data) {
        // $data['componentName'];
        // $data['attributes'];
        // $data['slot'];

        return '<div>Components content</div>';
    };
}
```

The closure should return a string
- If the returned string corresponds to an existing view, that view will be rendered 
- otherwise, the returned string will be evaluated as an inline Blade view

--
## Accessing Attributes & Slots Within Component Classes
The componentName is equal to the name used in the HTML tag after the x- prefix.
- So x-alert's componentName will be alert
The attributes element will contain all of the attributes that were present on the HTML tag
The slot element is an Illuminate\Support\HtmlString instance with the contents of the component's slot

The closure should return a string. If the returned string corresponds to an existing view, that view will be rendered; otherwise, the returned string will be evaluated as an inline Blade view.

--
## Additional Dependencies
If your component requires dependencies from Laravel's service container, list them before any of the component's data attributes and they will automatically be injected by the container

```
use App\Services\AlertCreator

/**
 * Create the component instance.
 *
 * @param  \App\Services\AlertCreator  $creator
 * @param  string  $type
 * @param  string  $message
 * @return void
 */
public function __construct(AlertCreator $creator, $type, $message)
{
    $this->creator = $creator;
    $this->type = $type;
    $this->message = $message;
}
```

--
## Hiding Attributes / Methods
If you would like to prevent some public methods or properties from being exposed as variables to your component template, add them to an $except array property on your component

```
<?php

namespace App\View\Components;

use Illuminate\View\Component;

class Alert extends Component
{
    /**
     * The alert type.
     *
     * @var string
     */
    public $type;

    /**
     * The properties / methods that should not be exposed to the component template.
     *
     * @var array
     */
    protected $except = ['type'];
}
```

--
## Component Attributes
Sometimes you may need to specify additional HTML attributes, such as class, that are not part of the data required for a component to function. 

Typically, you want to pass these additional attributes down to the root element of the component template. 

For example, imagine we want to render an alert component like so:
```
<x-alert type="error" :message="$message" class="mt-4"/>
```

--
## Component Attributes
All of the attributes that are not part of the component's constructor will automatically be added to the component's "attribute bag"

This attribute bag is automatically made available to the component via the $attributes variable 

All of the attributes may be rendered within the component by echoing this variable:

```
<div {{ $attributes }}>
    <!-- Component content -->
</div>
```

--
## Default / Merged Attributes
Sometimes you may need to specify default values for attributes or merge additional values into some of the component's attributes. 

```
<div {{ $attributes->merge(['class' => 'alert alert-'.$type]) }}>
    {{ $message }}
</div>
```
If we assume this component is utilized like so:
```
<x-alert type="error" :message="$message" class="mb-4"/>
```
The final, rendered HTML of the component will appear like the following:
```
<div class="alert alert-error mb-4">
    <!-- Contents of the $message variable -->
</div>
```

--
## Conditionally Merge Classes
Sometimes you may wish to merge classes if a given condition is true
```
<div {{ $attributes->class(['p-4', 'bg-red' => $hasError]) }}>
    {{ $message }}
</div>
```

--
## Conditionally Merge Classes
If you need to merge other attributes onto your component, you can chain the merge method onto the class method:

```
<button {{ $attributes->class(['p-4'])->merge(['type' => 'button']) }}>
    {{ $slot }}
</button>
```

--
## Non-Class Attribute Merging
When merging attributes that are not class attributes, the values provided to the merge method will be considered the "default" values of the attribute.

Unlike the class attribute, these attributes will not be merged with injected attribute values. Instead, they will be overwritten

```
<button {{ $attributes->merge(['type' => 'button']) }}>
    {{ $slot }}
</button>
```
To render the button component with a custom type, it may be specified when consuming the component. If no type is specified, the button type will be used:
```
<x-button type="submit">
    Submit
</x-button>
```
The rendered HTML of the button component in this example would be:
```
<button type="submit">
    Submit
</button>
```

--
## Non-Class Attribute Merging
If you would like an attribute other than class to have its default value and injected values joined together, use the prepends method. 

```
<div {{ $attributes->merge(['data-controller' => $attributes->prepends('profile-controller')]) }}>
    {{ $slot }}
</div>
```

--
## Retrieving & Filtering Attributes
You may filter attributes using the filter method. This method accepts a closure which should return true if you wish to retain the attribute in the attribute bag:

```
{{ $attributes->filter(fn ($value, $key) => $key == 'foo') }}
```
For convenience, use the whereStartsWith method to retrieve all attributes whose keys begin with a given string
```
{{ $attributes->whereStartsWith('wire:model') }}
```
Conversely, the whereDoesntStartWith method may be used to exclude all attributes whose keys begin with a given string
```
{{ $attributes->whereDoesntStartWith('wire:model') }}
```
--
## Retrieving & Filtering Attributes
Using the first method, you may render the first attribute in a given attribute bag:
```
{{ $attributes->whereStartsWith('wire:model')->first() }}
```

--
## Retrieving & Filtering Attributes
If you would like to check if an attribute is present on the component, use the has method 

This method accepts the attribute name as its only argument and returns a boolean indicating whether or not the attribute is present
```
@if ($attributes->has('class'))
    <div>Class attribute is present</div>
@endif
```

--
## Retrieving & Filtering Attributes
You may retrieve a specific attribute's value using the get method:
```
{{ $attributes->get('class') }}
```

--
## Reserved Keywords
By default, some keywords are reserved for Blade's internal use in order to render components. The following keywords cannot be defined as public properties or method names

```
data
render
resolveView
shouldRender
view
withAttributes
withName
Slots
```

You will often need to pass additional content to your component via "slots". Component slots are rendered by echoing the $slot variable. To explore this concept, let's imagine that an alert component has the following markup:

<!-- /resources/views/components/alert.blade.php -->

<div class="alert alert-danger">
    {{ $slot }}
</div>
We may pass content to the slot by injecting content into the component:

<x-alert>
    <strong>Whoops!</strong> Something went wrong!
</x-alert>
Sometimes a component may need to render multiple different slots in different locations within the component. Let's modify our alert component to allow for the injection of a "title" slot:

<!-- /resources/views/components/alert.blade.php -->

<span class="alert-title">{{ $title }}</span>

<div class="alert alert-danger">
    {{ $slot }}
</div>
You may define the content of the named slot using the x-slot tag. Any content not within an explicit x-slot tag will be passed to the component in the $slot variable:

<x-alert>
    <x-slot name="title">
        Server Error
    </x-slot>

    <strong>Whoops!</strong> Something went wrong!
</x-alert>
Scoped Slots
If you have used a JavaScript framework such as Vue, you may be familiar with "scoped slots", which allow you to access data or methods from the component within your slot. You may achieve similar behavior in Laravel by defining public methods or properties on your component and accessing the component within your slot via the $component variable. In this example, we will assume that the x-alert component has a public formatAlert method defined on its component class:

<x-alert>
    <x-slot name="title">
        {{ $component->formatAlert('Server Error') }}
    </x-slot>

    <strong>Whoops!</strong> Something went wrong!
</x-alert>
Inline Component Views
For very small components, it may feel cumbersome to manage both the component class and the component's view template. For this reason, you may return the component's markup directly from the render method:

/**
 * Get the view / contents that represent the component.
 *
 * @return \Illuminate\View\View|\Closure|string
 */
public function render()
{
    return <<<'blade'
        <div class="alert alert-danger">
            {{ $slot }}
        </div>
    blade;
}
Generating Inline View Components
To create a component that renders an inline view, you may use the inline option when executing the make:component command:

php artisan make:component Alert --inline
Anonymous Components
Similar to inline components, anonymous components provide a mechanism for managing a component via a single file. However, anonymous components utilize a single view file and have no associated class. To define an anonymous component, you only need to place a Blade template within your resources/views/components directory. For example, assuming you have defined a component at resources/views/components/alert.blade.php, you may simply render it like so:

<x-alert/>
You may use the . character to indicate if a component is nested deeper inside the components directory. For example, assuming the component is defined at resources/views/components/inputs/button.blade.php, you may render it like so:

<x-inputs.button/>
Data Properties / Attributes
Since anonymous components do not have any associated class, you may wonder how you may differentiate which data should be passed to the component as variables and which attributes should be placed in the component's attribute bag.

You may specify which attributes should be considered data variables using the @props directive at the top of your component's Blade template. All other attributes on the component will be available via the component's attribute bag. If you wish to give a data variable a default value, you may specify the variable's name as the array key and the default value as the array value:

<!-- /resources/views/components/alert.blade.php -->

@props(['type' => 'info', 'message'])

<div {{ $attributes->merge(['class' => 'alert alert-'.$type]) }}>
    {{ $message }}
</div>
Given the component definition above, we may render the component like so:

<x-alert type="error" :message="$message" class="mb-4"/>
Dynamic Components
Sometimes you may need to render a component but not know which component should be rendered until runtime. In this situation, you may use Laravel's built-in dynamic-component component to render the component based on a runtime value or variable:

<x-dynamic-component :component="$componentName" class="mt-4" />
Manually Registering Components

The following documentation on manually registering components is primarily applicable to those who are writing Laravel packages that include view components. If you are not writing a package, this portion of the component documentation may not be relevant to you.


When writing components for your own application, components are automatically discovered within the app/View/Components directory and resources/views/components directory.

However, if you are building a package that utilizes Blade components or placing components in non-conventional directories, you will need to manually register your component class and its HTML tag alias so that Laravel knows where to find the component. You should typically register your components in the boot method of your package's service provider:

use Illuminate\Support\Facades\Blade;
use VendorPackage\View\Components\AlertComponent;

/**
 * Bootstrap your package's services.
 *
 * @return void
 */
public function boot()
{
    Blade::component('package-alert', AlertComponent::class);
}
Once your component has been registered, it may be rendered using its tag alias:

<x-package-alert/>
Autoloading Package Components
Alternatively, you may use the componentNamespace method to autoload component classes by convention. For example, a Nightshade package might have Calendar and ColorPicker components that reside within the Package\Views\Components namespace:

use Illuminate\Support\Facades\Blade;

/**
 * Bootstrap your package's services.
 *
 * @return void
 */
public function boot()
{
    Blade::componentNamespace('Nightshade\\Views\\Components', 'nightshade');
}
This will allow the usage of package components by their vendor namespace using the package-name:: syntax:

<x-nightshade::calendar />
<x-nightshade::color-picker />
Blade will automatically detect the class that's linked to this component by pascal-casing the component name. Subdirectories are also supported using "dot" notation.

Building Layouts
Layouts Using Components
Most web applications maintain the same general layout across various pages. It would be incredibly cumbersome and hard to maintain our application if we had to repeat the entire layout HTML in every view we create. Thankfully, it's convenient to define this layout as a single Blade component and then use it throughout our application.

Defining The Layout Component
For example, imagine we are building a "todo" list application. We might define a layout component that looks like the following:

<!-- resources/views/components/layout.blade.php -->

<html>
    <head>
        <title>{{ $title ?? 'Todo Manager' }}</title>
    </head>
    <body>
        <h1>Todos</h1>
        <hr/>
        {{ $slot }}
    </body>
</html>
Applying The Layout Component
Once the layout component has been defined, we may create a Blade view that utilizes the component. In this example, we will define a simple view that displays our task list:

<!-- resources/views/tasks.blade.php -->

<x-layout>
    @foreach ($tasks as $task)
        {{ $task }}
    @endforeach
</x-layout>
Remember, content that is injected into a component will be supplied to the default $slot variable within our layout component. As you may have noticed, our layout also respects a $title slot if one is provided; otherwise, a default title is shown. We may inject a custom title from our task list view using the standard slot syntax discussed in the component documentation:

<!-- resources/views/tasks.blade.php -->

<x-layout>
    <x-slot name="title">
        Custom Title
    </x-slot>

    @foreach ($tasks as $task)
        {{ $task }}
    @endforeach
</x-layout>
Now that we have defined our layout and task list views, we just need to return the task view from a route:

use App\Models\Task;

Route::get('/tasks', function () {
    return view('tasks', ['tasks' => Task::all()]);
});
Layouts Using Template Inheritance
Defining A Layout
Layouts may also be created via "template inheritance". This was the primary way of building applications prior to the introduction of components.

To get started, let's take a look at a simple example. First, we will examine a page layout. Since most web applications maintain the same general layout across various pages, it's convenient to define this layout as a single Blade view:

<!-- resources/views/layouts/app.blade.php -->

<html>
    <head>
        <title>App Name - @yield('title')</title>
    </head>
    <body>
        @section('sidebar')
            This is the master sidebar.
        @show

        <div class="container">
            @yield('content')
        </div>
    </body>
</html>
As you can see, this file contains typical HTML mark-up. However, take note of the @section and @yield directives. The @section directive, as the name implies, defines a section of content, while the @yield directive is used to display the contents of a given section.

Now that we have defined a layout for our application, let's define a child page that inherits the layout.

Extending A Layout
When defining a child view, use the @extends Blade directive to specify which layout the child view should "inherit". Views which extend a Blade layout may inject content into the layout's sections using @section directives. Remember, as seen in the example above, the contents of these sections will be displayed in the layout using @yield:

<!-- resources/views/child.blade.php -->

@extends('layouts.app')

@section('title', 'Page Title')

@section('sidebar')
    @parent

    <p>This is appended to the master sidebar.</p>
@endsection

@section('content')
    <p>This is my body content.</p>
@endsection
In this example, the sidebar section is utilizing the @parent directive to append (rather than overwriting) content to the layout's sidebar. The @parent directive will be replaced by the content of the layout when the view is rendered.


Contrary to the previous example, this sidebar section ends with @endsection instead of @show. The @endsection directive will only define a section while @show will define and immediately yield the section.


The @yield directive also accepts a default value as its second parameter. This value will be rendered if the section being yielded is undefined:

@yield('content', 'Default content')
Forms
CSRF Field
Anytime you define an HTML form in your application, you should include a hidden CSRF token field in the form so that the CSRF protection middleware can validate the request. You may use the @csrf Blade directive to generate the token field:

<form method="POST" action="/profile">
    @csrf

    ...
</form>
Method Field
Since HTML forms can't make PUT, PATCH, or DELETE requests, you will need to add a hidden _method field to spoof these HTTP verbs. The @method Blade directive can create this field for you:

<form action="/foo/bar" method="POST">
    @method('PUT')

    ...
</form>
Validation Errors
The @error directive may be used to quickly check if validation error messages exist for a given attribute. Within an @error directive, you may echo the $message variable to display the error message:

<!-- /resources/views/post/create.blade.php -->

<label for="title">Post Title</label>

<input id="title" type="text" class="@error('title') is-invalid @enderror">

@error('title')
    <div class="alert alert-danger">{{ $message }}</div>
@enderror
Since the @error directive compiles to an "if" statement, you may use the @else directive to render content when there is not an error for an attribute:

<!-- /resources/views/auth.blade.php -->

<label for="email">Email address</label>

<input id="email" type="email" class="@error('email') is-invalid @else is-valid @enderror">
You may pass the name of a specific error bag as the second parameter to the @error directive to retrieve validation error messages on pages containing multiple forms:

<!-- /resources/views/auth.blade.php -->

<label for="email">Email address</label>

<input id="email" type="email" class="@error('email', 'login') is-invalid @enderror">

@error('email', 'login')
    <div class="alert alert-danger">{{ $message }}</div>
@enderror
Stacks
Blade allows you to push to named stacks which can be rendered somewhere else in another view or layout. This can be particularly useful for specifying any JavaScript libraries required by your child views:

@push('scripts')
    <script src="/example.js"></script>
@endpush
You may push to a stack as many times as needed. To render the complete stack contents, pass the name of the stack to the @stack directive:

<head>
    <!-- Head Contents -->

    @stack('scripts')
</head>
If you would like to prepend content onto the beginning of a stack, you should use the @prepend directive:

@push('scripts')
    This will be second...
@endpush

// Later...

@prepend('scripts')
    This will be first...
@endprepend
Service Injection
The @inject directive may be used to retrieve a service from the Laravel service container. The first argument passed to @inject is the name of the variable the service will be placed into, while the second argument is the class or interface name of the service you wish to resolve:

@inject('metrics', 'App\Services\MetricsService')

<div>
    Monthly Revenue: {{ $metrics->monthlyRevenue() }}.
</div>
Extending Blade
Blade allows you to define your own custom directives using the directive method. When the Blade compiler encounters the custom directive, it will call the provided callback with the expression that the directive contains.

The following example creates a @datetime($var) directive which formats a given $var, which should be an instance of DateTime:

<?php

namespace App\Providers;

use Illuminate\Support\Facades\Blade;
use Illuminate\Support\ServiceProvider;

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
        Blade::directive('datetime', function ($expression) {
            return "<?php echo ($expression)->format('m/d/Y H:i'); ?>";
        });
    }
}
As you can see, we will chain the format method onto whatever expression is passed into the directive. So, in this example, the final PHP generated by this directive will be:

<?php echo ($var)->format('m/d/Y H:i'); ?>

After updating the logic of a Blade directive, you will need to delete all of the cached Blade views. The cached Blade views may be removed using the view:clear Artisan command.


Custom If Statements
Programming a custom directive is sometimes more complex than necessary when defining simple, custom conditional statements. For that reason, Blade provides a Blade::if method which allows you to quickly define custom conditional directives using closures. For example, let's define a custom conditional that checks the configured default "disk" for the application. We may do this in the boot method of our AppServiceProvider:

use Illuminate\Support\Facades\Blade;

/**
 * Bootstrap any application services.
 *
 * @return void
 */
public function boot()
{
    Blade::if('disk', function ($value) {
        return config('filesystems.default') === $value;
    });
}
Once the custom conditional has been defined, you can use it within your templates:

@disk('local')
    <!-- The application is using the local disk... -->
@elsedisk('s3')
    <!-- The application is using the s3 disk... -->
@else
    <!-- The application is using some other disk... -->
@enddisk

@unlessdisk('local')
    <!-- The application is not using the local disk... -->
@enddisk

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Components

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!