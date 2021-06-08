# Validation

--
## In this lesson
We will cover
- Introduction
- Validation Quickstart
- Defining The Routes
- Creating The Controller
- Writing The Validation Logic
- Displaying The Validation Errors
- Repopulating Forms
- A Note On Optional Fields

--
## In this lesson
We will cover
- Form Request Validation
- Creating Form Requests
- Authorizing Form Requests
- Customizing The Error Messages
- Preparing Input For Validation
- Manually Creating Validators
- Automatic Redirection
- Named Error Bags
- Customizing The Error Messages
- After Validation Hook

--
## In this lesson
We will cover
- Working With Error Messages
- Specifying Custom Messages In Language Files
- Specifying Attributes In Language Files
- Specifying Values In Language Files
- Available Validation Rules
- Conditionally Adding Rules
- Validating Arrays
- Validating Passwords
- Custom Validation Rules

--
## In this lesson
We will cover
- Using Rule Objects
- Using Closures
- Implicit Rules

--
##  Introduction
Validate your application's incoming data
- Laravel includes a wide variety of convenient validation rules that you may apply to data, even providing the ability to validate if values are unique in a given database table

--
## Validation Quickstart
Validating a form and displaying the error messages back to the user:
- Define The Routes
- Create The Controller
- Write The Validation Logic

-
## Define The Routes
```
use App\Http\Controllers\PostController;
Route::get('/post/create', [PostController::class, 'create']);
Route::post('/post', [PostController::class, 'store']);
```

--
## Create The Controller
Next, let's take a look at a simple controller that handles incoming requests to these routes. We'll leave the store method empty for now:
```
<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class PostController extends Controller
{
    /**
     * Show the form to create a new blog post.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        return view('post.create');
    }

    /**
     * Store a new blog post.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        // Validate and store the blog post...
    }
}
```

--
## Write The Validation Logic
Now we are ready to fill in our store method with the logic to validate the new blog post
- use the validate method provided by the Illuminate\Http\Request object

If the validation rules pass, your code will keep executing normally; however, if validation fails, an exception will be thrown and the proper error response will automatically be sent back to the user.

If validation fails during a traditional HTTP request, a redirect response to the previous URL will be generated. If the incoming request is an XHR request, a JSON response containing the validation error messages will be returned.

--
## Write The Validation Logic
To get a better understanding of the validate method, let's jump back into the store method:
```
/**
 * Store a new blog post.
 *
 * @param  \Illuminate\Http\Request  $request
 * @return \Illuminate\Http\Response
 */
public function store(Request $request)
{
    $validated = $request->validate([
        'title' => 'required|unique:posts|max:255',
        'body' => 'required',
    ]);

    // The blog post is valid...
}
```
As you can see, the validation rules are passed into the validate method. 

--
## Write The Validation Logic
Alternatively, validation rules may be specified as arrays of rules instead of a single | delimited string:
```
$validatedData = $request->validate([
    'title' => ['required', 'unique:posts', 'max:255'],
    'body' => ['required'],
]);
```
In addition, you may use the validateWithBag method to validate a request and store any error messages within a named error bag:
```
$validatedData = $request->validateWithBag('post', [
    'title' => ['required', 'unique:posts', 'max:255'],
    'body' => ['required'],
]);
```

--
## Stopping On First Validation Failure
Sometimes you may wish to stop running validation rules on an attribute after the first validation failure. To do so, assign the bail rule to the attribute
```
$request->validate([
    'title' => 'bail|required|unique:posts|max:255',
    'body' => 'required',
]);
```
In this example, if the unique rule on the title attribute fails, the max rule will not be checked. Rules will be validated in the order they are assigned.

--
## A Note On Nested Attributes
If the incoming HTTP request contains "nested" field data, you may specify these fields in your validation rules using "dot" syntax:
```
$request->validate([
    'title' => 'required|unique:posts|max:255',
    'author.name' => 'required',
    'author.description' => 'required',
]);
```
On the other hand, if your field name contains a literal period, you can explicitly prevent this from being interpreted as "dot" syntax by escaping the period with a backslash:
```
$request->validate([
    'title' => 'required|unique:posts|max:255',
    'v1\.0' => 'required',
]);
```

--
## Displaying The Validation Errors
So, what if the incoming request fields do not pass the given validation rules? 
Laravel will automatically redirect the user back to their previous location 
- all of the validation errors and request input will automatically be flashed to the session
- an $errors variable is shared with all of your application's views 

```

<!-- /resources/views/post/create.blade.php -->

<h1>Create Post</h1>

@if ($errors->any())
    <div class="alert alert-danger">
        <ul>
            @foreach ($errors->all() as $error)
                <li>{{ $error }}</li>
            @endforeach
        </ul>
    </div>
@endif

<!-- Create Post Form -->
```

--
## Customizing The Error Messages
Laravel's built-in validation rules each has an error message that is located in your application's resources/lang/en/validation.php file

- you are free to change or modify these messages based on the needs of your application
- you may copy this file to another translation language directory to translate the messages for your application's language

--
## XHR Requests & Validation
When using the validate method during an XHR request, Laravel will not generate a redirect response. Instead, Laravel generates a JSON response containing all of the validation errors. This JSON response will be sent with a 422 HTTP status code.

--
## The @error Directive
You may use the @error Blade directive to quickly determine if validation error messages exist for a given attribute. Within an @error directive, you may echo the $message variable to display the error message:
```
<!-- /resources/views/post/create.blade.php -->

<label for="title">Post Title</label>

<input id="title" type="text" name="title" class="@error('title') is-invalid @enderror">

@error('title')
    <div class="alert alert-danger">{{ $message }}</div>
@enderror
```

--
## Repopulating Forms
When Laravel generates a redirect response due to a validation error, the framework will automatically flash all of the request's input to the session. This is done so that you may conveniently access the input during the next request and repopulate the form that the user attempted to submit

To retrieve flashed input from the previous request, invoke the old method 
```
$title = $request->old('title');
```

--
## Repopulating Forms
Laravel also provides a global old helper. If you are displaying old input within a Blade template, it is more convenient to use the old helper to repopulate the form. If no old input exists for the given field, null will be returned:
```
<input type="text" name="title" value="{{ old('title') }}">
```

--
## A Note On Optional Fields
By default, Laravel includes the TrimStrings and ConvertEmptyStringsToNull middleware in your application's global middleware stack. You need to mark your "optional" request fields as nullable if you do not want the validator to consider null values as invalid
```
$request->validate([
    'title' => 'required|unique:posts|max:255',
    'body' => 'required',
    'publish_at' => 'nullable|date',
]);
```
In this example, we are specifying that the publish_at field may be either null or a valid date representation

--
## Form Request Validation
Creating Form Requests
- For more complex validation scenarios, you may wish to create a "form request". Form requests are custom request classes that encapsulate their own validation and authorization logic. 

To create a form request class, you may use the make:request Artisan CLI command:
```
php artisan make:request StorePostRequest
```
The generated form request class will be placed in the app/Http/Requests directory

Each form request generated by Laravel has two methods
- authorize 
- rules

--
## Form Request Validation
As you might have guessed, the authorize method is responsible for determining if the currently authenticated user can perform the action represented by the request, while the rules method returns the validation rules that should apply to the request's data

```
/**
 * Get the validation rules that apply to the request.
 *
 * @return array
 */
public function rules()
{
    return [
        'title' => 'required|unique:posts|max:255',
        'body' => 'required',
    ];
}
```

--
## Form Request Validation
So, how are the validation rules evaluated? All you need to do is type-hint the request on your controller method. The incoming form request is validated before the controller method is called, meaning you do not need to clutter your controller with any validation logic
```
/**
 * Store a new blog post.
 *
 * @param  \App\Http\Requests\StorePostRequest  $request
 * @return Illuminate\Http\Response
 */
public function store(StorePostRequest $request)
{
    // The incoming request is valid...

    // Retrieve the validated input data...
    $validated = $request->validated();
}
```
If validation fails, a redirect response will be generated to send the user back to their previous location. The errors will also be flashed to the session so they are available for display

If the request was an XHR request, an HTTP response with a 422 status code will be returned to the user including a JSON representation of the validation errors

--
## Adding After Hooks To Form Requests
If you would like to add an "after" validation hook to a form request, you may use the withValidator method
```
/**
 * Configure the validator instance.
 *
 * @param  \Illuminate\Validation\Validator  $validator
 * @return void
 */
public function withValidator($validator)
{
    $validator->after(function ($validator) {
        if ($this->somethingElseIsInvalid()) {
            $validator->errors()->add('field', 'Something is wrong with this field!');
        }
    });
}
```

--
## Stopping On First Validation Failure Attribute
By adding a stopOnFirstFailure property to your request class, you may inform the validator that it should stop validating all attributes once a single validation failure has occurred

```
/**
 * Indicates if the validator should stop on the first rule failure.
 *
 * @var bool
 */
protected $stopOnFirstFailure = true;
```

--
## Authorizing Form Requests
The form request class also contains an authorize method. Within this method, you may determine if the authenticated user actually has the authority to update a given resource. For example, you may determine if a user actually owns a blog comment they are attempting to update. Most likely, you will interact with your authorization gates and policies within this method
```
use App\Models\Comment;

/**
 * Determine if the user is authorized to make this request.
 *
 * @return bool
 */
public function authorize()
{
    $comment = Comment::find($this->route('comment'));

    return $comment && $this->user()->can('update', $comment);
}
```


--
## Authorizing Form Requests
If you plan to handle authorization logic for the request in another part of your application, you may simply return true from the authorize method:
```
/**
 * Determine if the user is authorized to make this request.
 *
 * @return bool
 */
public function authorize()
{
    return true;
}
```

You may type-hint any dependencies you need within the authorize method's signature. They will automatically be resolved via the Laravel service container

--
## Customizing The Error Messages
You may customize the error messages used by the form request by overriding the messages method. This method should return an array of attribute / rule pairs and their corresponding error messages
```
/**
 * Get the error messages for the defined validation rules.
 *
 * @return array
 */
public function messages()
{
    return [
        'title.required' => 'A title is required',
        'body.required' => 'A message is required',
    ];
}
```

--
## Customizing The Validation Attributes
Many of Laravel's built-in validation rule error messages contain an :attribute placeholder. If you would like the :attribute placeholder of your validation message to be replaced with a custom attribute name, you may specify the custom names by overriding the attributes method

```
/**
 * Get custom attributes for validator errors.
 *
 * @return array
 */
public function attributes()
{
    return [
        'email' => 'email address',
    ];
}
```
--
## Preparing Input For Validation
If you need to prepare or sanitize any data from the request before you apply your validation rules, you may use the prepareForValidation method

```
use Illuminate\Support\Str;

/**
 * Prepare the data for validation.
 *
 * @return void
 */
protected function prepareForValidation()
{
    $this->merge([
        'slug' => Str::slug($this->slug),
    ]);
}
```

--
## Manually Creating Validators
If you do not want to use the validate method on the request, you may create a validator instance manually using the Validator facade

```
<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class PostController extends Controller
{
    /**
     * Store a new blog post.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'title' => 'required|unique:posts|max:255',
            'body' => 'required',
        ]);

        if ($validator->fails()) {
            return redirect('post/create')
                        ->withErrors($validator)
                        ->withInput();
        }

        // Store the blog post...
    }
}
```
The first argument passed to the make method is the data under validation. The second argument is an array of the validation rules that should be applied to the data



--
## Stopping On First Validation Failure
The stopOnFirstFailure method will inform the validator that it should stop validating all attributes once a single validation failure has occurred

```
if ($validator->stopOnFirstFailure()->fails()) {
    // ...
}
```

--
## Automatic Redirection
If you would like to create a validator instance manually but still take advantage of the automatic redirection offered by the HTTP request's validate method, you may call the validate method on an existing validator instance

```
Validator::make($request->all(), [
    'title' => 'required|unique:posts|max:255',
    'body' => 'required',
])->validate();
```

--
## Automatic Redirection
You may use the validateWithBag method to store the error messages in a named error bag if validation fails:

```
Validator::make($request->all(), [
    'title' => 'required|unique:posts|max:255',
    'body' => 'required',
])->validateWithBag('post');
```

--
## Named Error Bags
If you have multiple forms on a single page, you may wish to name the MessageBag containing the validation errors, allowing you to retrieve the error messages for a specific form

```
return redirect('register')->withErrors($validator, 'login');
```

You may then access the named MessageBag instance from the $errors variable:
```
{{ $errors->login->first('email') }}
```

--
## Customizing The Error Messages
If needed, you may provide custom error messages that a validator instance should use instead of the default error messages provided by Laravel. There are several ways to specify custom messages

- First, you may pass the custom messages as the third argument to the Validator::make method:
```
$validator = Validator::make($input, $rules, $messages = [
    'required' => 'The :attribute field is required.',
]);
```
In this example, the :attribute placeholder will be replaced by the actual name of the field under validation. You may also utilize other placeholders in validation messages. For example:

--
## Customizing The Error Messages
$messages = [
    'same' => 'The :attribute and :other must match.',
    'size' => 'The :attribute must be exactly :size.',
    'between' => 'The :attribute value :input is not between :min - :max.',
    'in' => 'The :attribute must be one of the following types: :values',
];

--
## Specifying A Custom Message For A Given Attribute
Sometimes you may wish to specify a custom error message only for a specific attribute. You may do so using "dot" notation
```
$messages = [
    'email.required' => 'We need to know your email address!',
];
```

--
## Specifying Custom Attribute Values
Many of Laravel's built-in error messages include an :attribute placeholder that is replaced with the name of the field or attribute under validation. To customize the values used to replace these placeholders for specific fields, you may pass an array of custom attributes as the fourth argument to the Validator::make method:
```
$validator = Validator::make($input, $rules, $messages, [
    'email' => 'email address',
]);
```

--
## After Validation Hook
You may also attach callbacks to be run after validation is completed. This allows you to easily perform further validation and even add more error messages to the message collection

```
$validator = Validator::make(...);

$validator->after(function ($validator) {
    if ($this->somethingElseIsInvalid()) {
        $validator->errors()->add(
            'field', 'Something is wrong with this field!'
        );
    }
});

if ($validator->fails()) {
    //
}
```

--
## Working With Error Messages
After calling the errors method on a Validator instance, you will receive an Illuminate\Support\MessageBag instance, which has a variety of convenient methods for working with error messages. 

The $errors variable that is automatically made available to all views is also an instance of the MessageBag class.

--
## Retrieving The First Error Message For A Field
To retrieve the first error message for a given field, use the first method:
```
$errors = $validator->errors();

echo $errors->first('email');
```

--
## Retrieving All Error Messages For A Field
If you need to retrieve an array of all the messages for a given field, use the get method

```
foreach ($errors->get('email') as $message) {
    //
}
```
If you are validating an array form field, you may retrieve all of the messages for each of the array elements using the * character:
```
foreach ($errors->get('attachments.*') as $message) {
    //
}
```

--
## Retrieving All Error Messages For All Fields
To retrieve an array of all messages for all fields, use the all method:
```
foreach ($errors->all() as $message) {
    //
}
```

--
## Determining If Messages Exist For A Field
The has method may be used to determine if any error messages exist for a given field:
```
if ($errors->has('email')) {
    //
}
```

--
## Specifying Custom Messages In Language Files
Laravel's built-in validation rules each has an error message that is located in your application's resources/lang/en/validation.php file. Within this file, you will find a translation entry for each validation rule

In addition, you may copy this file to another translation language directory to translate the messages for your application's language

--
## Custom Messages For Specific Attributes
You may customize the error messages used for specified attribute and rule combinations within your application's validation language files. To do so, add your message customizations to the custom array of your application's resources/lang/xx/validation.php language file

```
'custom' => [
    'email' => [
        'required' => 'We need to know your email address!',
        'max' => 'Your email address is too long!'
    ],
],
```

--
## Specifying Attributes In Language Files
Many of Laravel's built-in error messages include an :attribute placeholder that is replaced with the name of the field or attribute under validation. If you would like the :attribute portion of your validation message to be replaced with a custom value, you may specify the custom attribute name in the attributes array of your resources/lang/xx/validation.php language file

```
'attributes' => [
    'email' => 'email address',
],
```

--
## Specifying Values In Language Files
Some of Laravel's built-in validation rule error messages contain a :value placeholder that is replaced with the current value of the request attribute. However, you may occasionally need the :value portion of your validation message to be replaced with a custom representation of the value

```
Validator::make($request->all(), [
    'credit_card_number' => 'required_if:payment_type,cc'
]);
```

If this validation rule fails, it will produce the following error message:
```
The credit card number field is required when payment type is cc.
```

--
## Specifying Values In Language Files
Instead of displaying cc as the payment type value, you may specify a more user-friendly value representation in your resources/lang/xx/validation.php language file by defining a values array:

```
'values' => [
    'payment_type' => [
        'cc' => 'credit card'
    ],
],
```
After defining this value, the validation rule will produce the following error message:
```
The credit card number field is required when payment type is credit card.
```

--
## Available Validation Rules
Below is a list of all available validation rules and their function:
```
Accepted
Active URL
After (Date)
After Or Equal (Date)
Alpha
Alpha Dash
Alpha Numeric
Array
Bail
Before (Date)
Before Or Equal (Date)
Between
Boolean
Confirmed
Date
Date Equals
Date Format
Different
Digits
Digits Between
Dimensions (Image Files)
Distinct
Email
Ends With
Exclude If
Exclude Unless
Exists (Database)
File
Filled
Greater Than
Greater Than Or Equal
Image (File)
In
In Array
Integer
IP Address
JSON
Less Than
Less Than Or Equal
Max
MIME Types
MIME Type By File Extension
Min
Multiple Of
Not In
Not Regex
Nullable
Numeric
Password
Present
Prohibited
Prohibited If
Prohibited Unless
Regular Expression
Required
Required If
Required Unless
Required With
Required With All
Required Without
Required Without All
Same
Size
Sometimes
Starts With
String
Timezone
Unique (Database)
URL
UUID
```

--
## Conditionally Adding Rules
Skipping Validation When Fields Have Certain Values
You may occasionally wish to not validate a given field if another field has a given value. You may accomplish this using the exclude_if validation rule
```
use Illuminate\Support\Facades\Validator;

$validator = Validator::make($data, [
    'has_appointment' => 'required|boolean',
    'appointment_date' => 'exclude_if:has_appointment,false|required|date',
    'doctor_name' => 'exclude_if:has_appointment,false|required|string',
]);
```
Alternatively, you may use the exclude_unless rule to not validate a given field unless another field has a given value
```
$validator = Validator::make($data, [
    'has_appointment' => 'required|boolean',
    'appointment_date' => 'exclude_unless:has_appointment,true|required|date',
    'doctor_name' => 'exclude_unless:has_appointment,true|required|string',
]);
```

--
## Validating When Present
In some situations, you may wish to run validation checks against a field only if that field is present in the data being validated
```
$v = Validator::make($request->all(), [
    'email' => 'sometimes|required|email',
]);
```
In the example above, the email field will only be validated if it is present in the $data array.


--
## Complex Conditional Validation
Sometimes you may wish to add validation rules based on more complex conditional logic. For example, you may wish to require a given field only if another field has a greater value than 100. Or, you may need two fields to have a given value only when another field is present. Adding these validation rules doesn't have to be a pain. First, create a Validator instance with your static rules that never change:

use Illuminate\Support\Facades\Validator;

$validator = Validator::make($request->all(), [
    'email' => 'required|email',
    'games' => 'required|numeric',
]);
Let's assume our web application is for game collectors. If a game collector registers with our application and they own more than 100 games, we want them to explain why they own so many games. For example, perhaps they run a game resale shop, or maybe they just enjoy collecting games. To conditionally add this requirement, we can use the sometimes method on the Validator instance.

$v->sometimes('reason', 'required|max:500', function ($input) {
    return $input->games >= 100;
});
The first argument passed to the sometimes method is the name of the field we are conditionally validating. The second argument is a list of the rules we want to add. If the closure passed as the third argument returns true, the rules will be added. This method makes it a breeze to build complex conditional validations. You may even add conditional validations for several fields at once:

$v->sometimes(['reason', 'cost'], 'required', function ($input) {
    return $input->games >= 100;
});

The $input parameter passed to your closure will be an instance of Illuminate\Support\Fluent and may be used to access your input and files under validation.

--
## Validating Arrays
Validating array based form input fields doesn't have to be a pain. You may use "dot notation" to validate attributes within an array
```
use Illuminate\Support\Facades\Validator;

$validator = Validator::make($request->all(), [
    'photos.profile' => 'required|image',
]);
```

--
## Validating Arrays
You may also validate each element of an array. For example, to validate that each email in a given array input field is unique, you may do the following:
```
$validator = Validator::make($request->all(), [
    'person.*.email' => 'email|unique:users',
    'person.*.first_name' => 'required_with:person.*.last_name',
]);
```

--
## Validating Arrays
Likewise, you may use the * character when specifying custom validation messages in your language files, making it a breeze to use a single validation message for array based fields:
```
'custom' => [
    'person.*.email' => [
        'unique' => 'Each person must have a unique email address',
    ]
],
```

--
## Validating Passwords
To ensure that passwords have an adequate level of complexity, you may use Laravel's Password rule object
```
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\Rules\Password;

$validator = Validator::make($request->all(), [
    'password' => ['required', 'confirmed', Password::min(8)],
]);
```

--
## Validating Passwords
The Password rule object allows you to easily customize the password complexity requirements for your application, such as specifying that passwords require at least one letter, number, symbol, or characters with mixed casing:
```
// Require at least 8 characters...
Password::min(8)

// Require at least one letter...
Password::min(8)->letters()

// Require at least one uppercase and one lowercase letter...
Password::min(8)->mixedCase()

// Require at least one number...
Password::min(8)->numbers()

// Require at least one symbol...
Password::min(8)->symbols()
```

--
## Validating Passwords
In addition, you may ensure that a password has not been compromised in a public password data breach leak using the uncompromised method:
```
Password::min(8)->uncompromised()
```
Internally, the Password rule object uses the k-Anonymity model to determine if a password has been leaked via the haveibeenpwned.com service without sacrificing the user's privacy or security

By default, if a password appears at least once in a data leak, it will be considered compromised. You can customize this threshold using the first argument of the uncompromised method
```
// Ensure the password appears less than 3 times in the same data leak...
Password::min(8)->uncompromised(3);
```

--
## Validating Passwords
Of course, you may chain all the methods in the examples above:
```
Password::min(8)
    ->letters()
    ->mixedCase()
    ->numbers()
    ->symbols()
    ->uncompromised()
```

--
## Defining Default Password Rules
You may find it convenient to specify the default validation rules for passwords in a single location of your application. You can easily accomplish this using the Password::defaults method, which accepts a closure. The closure given to the defaults method should return the default configuration of the Password rule

```
use Illuminate\Validation\Rules\Password;

/**
 * Bootstrap any application services.
 *
 * @return void
 */
public function boot()
{
    Password::defaults(function () {
        $rule = Password::min(8);

        return $this->app->isProduction()
                    ? $rule->mixedCase()->uncompromised()
                    : $rule;
    });
}
```
Then, when you would like to apply the default rules to a particular password undergoing validation, you may invoke the defaults method with no arguments:
```
'password' => ['required', Password::defaults()],
```

--
## Custom Validation Rules
Using Rule Objects
Laravel provides a variety of helpful validation rules; however, you may wish to specify some of your own. One method of registering custom validation rules is using rule objects. 

To generate a new rule object, you may use the make:rule Artisan command. 
```
php artisan make:rule Uppercase
```

--
## Custom Validation Rules
Once the rule has been created, we are ready to define its behavior. 

A rule object contains two methods: 
- passes => receives the attribute value and name, and should return true or false depending on whether the attribute value is valid or not
- message => should return the validation error message that should be used when validation fails

```
<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class Uppercase implements Rule
{
    /**
     * Determine if the validation rule passes.
     *
     * @param  string  $attribute
     * @param  mixed  $value
     * @return bool
     */
    public function passes($attribute, $value)
    {
        return strtoupper($value) === $value;
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'The :attribute must be uppercase.';
    }
}
```

--
## Custom Validation Rules
You may call the trans helper from your message method if you would like to return an error message from your translation files
```
/**
 * Get the validation error message.
 *
 * @return string
 */
public function message()
{
    return trans('validation.uppercase');
}
```

--
## Custom Validation Rules
Once the rule has been defined, you may attach it to a validator by passing an instance of the rule object with your other validation rules
```
use App\Rules\Uppercase;

$request->validate([
    'name' => ['required', 'string', new Uppercase],
]);
```

--
## Using Closures
If you only need the functionality of a custom rule once throughout your application, you may use a closure instead of a rule object

```
use Illuminate\Support\Facades\Validator;

$validator = Validator::make($request->all(), [
    'title' => [
        'required',
        'max:255',
        function ($attribute, $value, $fail) {
            if ($value === 'foo') {
                $fail('The '.$attribute.' is invalid.');
            }
        },
    ],
]);
```

--
## Implicit Rules
By default, when an attribute being validated is not present or contains an empty string, normal validation rules, including custom rules, are not run. For example, the unique rule will not be run against an empty string:
```
use Illuminate\Support\Facades\Validator;

$rules = ['name' => 'unique:users,name'];

$input = ['name' => ''];

Validator::make($input, $rules)->passes(); // true
```

For a custom rule to run even when an attribute is empty, the rule must imply that the attribute is required

--
## Implicit Rules
To create an "implicit" rule, implement the Illuminate\Contracts\Validation\ImplicitRule interface. 

This interface serves as a "marker interface" for the validator; therefore, it does not contain any additional methods you need to implement beyond the methods required by the typical Rule interface


An "implicit" rule only implies that the attribute is required. Whether it actually invalidates a missing or empty attribute is up to you


--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo. Components

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!