# Layouts Using Template Inheritance

--
## Defining A Layout
Layouts may also be created via "template inheritance"

Since most web applications maintain the same general layout across various pages, it's convenient to define this layout as a single Blade view

```
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
```

--
## Defining A Layout
@section and @yield directives 
- @section directive, as the name implies, defines a section of content
-  @yield directive is used to display the contents of a given section

Now that we have defined a layout for our application, let's define a child page that inherits the layout.

--
## Extending A Layout
The @extends Blade directive to specify which layout the child view should "inherit". 

Views which extend a Blade layout may inject content into the layout's sections using @section directives

```
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
```

In this example, the sidebar section is utilizing the @parent directive to append (rather than overwriting) content to the layout's sidebar

--
## Extending A Layout
Contrary to the previous example, this sidebar section ends with @endsection instead of @show. 
- the @endsection directive will only define a section while @show will define and immediately yield the section.


The @yield directive also accepts a default value as its second parameter
```
@yield('content', 'Default content')
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Layouts

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!