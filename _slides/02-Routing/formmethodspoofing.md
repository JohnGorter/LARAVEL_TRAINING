
# Form Method Spoofing

--
## Form Method Spoofing
HTML forms do not support PUT, PATCH, or DELETE actions. So, when defining PUT, PATCH, or DELETE routes that are called from an HTML form, you will need to add a hidden _method field to the form

```
<form action="/example" method="POST">
    <input type="hidden" name="_method" value="PUT">
    <input type="hidden" name="_token" value="{{ csrf_token() }}">
</form>
```

--
## Form Method Spoofing
For convenience, you may use the @method Blade directive to generate the _method input field:

```
<form action="/example" method="POST">
    @method('PUT')
    @csrf
</form>
```






