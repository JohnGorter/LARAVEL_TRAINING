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
--
## Slots
Component slots are rendered by echoing the $slot variable

let's imagine that an alert component has the following markup:
```
<!-- /resources/views/components/alert.blade.php -->

<div class="alert alert-danger">
    {{ $slot }}
</div>
```
We may pass content to the slot by injecting content into the component:
```
<x-alert>
    <strong>Whoops!</strong> Something went wrong!
</x-alert>
```

--
## Slots
Sometimes a component may need to render multiple different slots in different locations within the component. Let's modify our alert component to allow for the injection of a "title" slot
```
<!-- /resources/views/components/alert.blade.php -->

<span class="alert-title">{{ $title }}</span>

<div class="alert alert-danger">
    {{ $slot }}
</div>
```
You may define the content of the named slot using the x-slot tag. Any content not within an explicit x-slot tag will be passed to the component in the $slot variable:
```
<x-alert>
    <x-slot name="title">
        Server Error
    </x-slot>

    <strong>Whoops!</strong> Something went wrong!
</x-alert>
```

--
## Scoped Slots
Allows you to access data or methods from the component within your slot

Assume that the x-alert component has a public formatAlert method defined on its component class:
```
<x-alert>
    <x-slot name="title">
        {{ $component->formatAlert('Server Error') }}
    </x-slot>

    <strong>Whoops!</strong> Something went wrong!
</x-alert>
```

--
## Inline Component Views
For very small components, it may feel cumbersome to manage both the component class and the component's view template. For this reason, you may return the component's markup directly from the render method:

```
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
```

--
## Generating Inline View Components
To create a component that renders an inline view, you may use the inline option when executing the make:component command
```
php artisan make:component Alert --inline
```

--
## Anonymous Components
Similar to inline components, anonymous components provide a mechanism for managing a component via a single file. 

However, anonymous components utilize a single view file and have no associated class. 

To define an anonymous component, you only need to place a Blade template within your resources/views/components directory

--
## Anonymous Components

For example, assuming you have defined a component at resources/views/components/alert.blade.php, you may simply render it like so

```
<x-alert/>
```
```
<x-inputs.button/>
```

--
## Data Properties / Attributes
Data is now passed
- using the @props directive at the top of your component's Blade template. 
- via the component's attribute bag

```
<!-- /resources/views/components/alert.blade.php -->
@props(['type' => 'info', 'message'])
<div {{ $attributes->merge(['class' => 'alert alert-'.$type]) }}>
    {{ $message }}
</div>
```
Given the component definition above, we may render the component like so:
```
<x-alert type="error" :message="$message" class="mb-4"/>
```

--
## Dynamic Components
Sometimes you may need to render a component but not know which component should be rendered until runtime. In this situation, you may use Laravel's built-in dynamic-component component to render the component based on a runtime value or variable:
```
<x-dynamic-component :component="$componentName" class="mt-4" />
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Components

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!