# Localization

--
## In this lesson
Introduction
Configuring The Locale
Defining Translation Strings
Using Short Keys
Using Translation Strings As Keys
--
## In this lesson
Retrieving Translation Strings
Replacing Parameters In Translation Strings
Pluralization
Overriding Package Language Files

--
## Introduction
Laravel's localization features provide a convenient way to retrieve strings in various languages, allowing you to easily support multiple languages within your application.

--
## Introduction
Laravel provides two ways to manage translation strings
- language strings may be stored in files within the resources/lang directory
```
/resources
    /lang
        /en
            messages.php
        /es
            messages.php
```
- translation strings may be defined within JSON files that are placed within the resources/lang directory (recommended when a large number of translatable strings)
```
/resources
    /lang
        en.json
        es.json
```

--
## Configuring The Locale
The default language for your application is stored in the config/app.php configuration file's locale configuration option


You can modify the default language for a single HTTP request at runtime using the setLocale method provided by the App facade
```
use Illuminate\Support\Facades\App;

Route::get('/greeting/{locale}', function ($locale) {
    if (! in_array($locale, ['en', 'es', 'fr'])) {
        abort(400);
    }

    App::setLocale($locale);

    //
});
```

--
## Configuring The Locale
You may configure a "fallback language", which will be used when the active language does not contain a given translation string

```
'fallback_locale' => 'en',
```

--
## Determining The Current Locale
You may use the currentLocale and isLocale methods on the App facade to determine the current locale or check if the locale is a given value

```
use Illuminate\Support\Facades\App;

$locale = App::currentLocale();

if (App::isLocale('en')) {
    //
}
```

--
## defining Translation Strings

- Using Short Keys
Typically, translation strings are stored in files within the resources/lang directory. Within this directory, there should be a subdirectory for each language supported by your application. This is the approach Laravel uses to manage translation strings for built-in Laravel features such as validation error messages:

/resources
    /lang
        /en
            messages.php
        /es
            messages.php
All language files return an array of keyed strings. For example:

<?php

// resources/lang/en/messages.php

return [
    'welcome' => 'Welcome to our application!',
];

For languages that differ by territory, you should name the language directories according to the ISO 15897. For example, "en_GB" should be used for British English rather than "en-gb".


Using Translation Strings As Keys
For applications with a large number of translatable strings, defining every string with a "short key" can become confusing when referencing the keys in your views and it is cumbersome to continually invent keys for every translation string supported by your application.

For this reason, Laravel also provides support for defining translation strings using the "default" translation of the string as the key. Translation files that use translation strings as keys are stored as JSON files in the resources/lang directory. For example, if your application has a Spanish translation, you should create a resources/lang/es.json file:

{
    "I love programming.": "Me encanta programar."
}
Key / File Conflicts
You should not define translation string keys that conflict with other translation filenames. For example, translating __('Action') for the "NL" locale while a nl/action.php file exists but a nl.json file does not exist will result in the translator returning the contents of nl/action.php.

Retrieving Translation Strings
You may retrieve translation strings from your language files using the __ helper function. If you are using "short keys" to define your translation strings, you should pass the file that contains the key and the key itself to the __ function using "dot" syntax. For example, let's retrieve the welcome translation string from the resources/lang/en/messages.php language file:

echo __('messages.welcome');
If the specified translation string does not exist, the __ function will return the translation string key. So, using the example above, the __ function would return messages.welcome if the translation string does not exist.

If you are using your default translation strings as your translation keys, you should pass the default translation of your string to the __ function;

echo __('I love programming.');
Again, if the translation string does not exist, the __ function will return the translation string key that it was given.

If you are using the Blade templating engine, you may use the {{ }} echo syntax to display the translation string:

{{ __('messages.welcome') }}
Replacing Parameters In Translation Strings
If you wish, you may define placeholders in your translation strings. All placeholders are prefixed with a :. For example, you may define a welcome message with a placeholder name:

'welcome' => 'Welcome, :name',
To replace the placeholders when retrieving a translation string, you may pass an array of replacements as the second argument to the __ function:

echo __('messages.welcome', ['name' => 'dayle']);
If your placeholder contains all capital letters, or only has its first letter capitalized, the translated value will be capitalized accordingly:

'welcome' => 'Welcome, :NAME', // Welcome, DAYLE
'goodbye' => 'Goodbye, :Name', // Goodbye, Dayle
Pluralization
Pluralization is a complex problem, as different languages have a variety of complex rules for pluralization; however, Laravel can help you translate strings differently based on pluralization rules that you define. Using a | character, you may distinguish singular and plural forms of a string:

'apples' => 'There is one apple|There are many apples',
Of course, pluralization is also supported when using translation strings as keys:

{
    "There is one apple|There are many apples": "Hay una manzana|Hay muchas manzanas"
}
You may even create more complex pluralization rules which specify translation strings for multiple ranges of values:

'apples' => '{0} There are none|[1,19] There are some|[20,*] There are many',
After defining a translation string that has pluralization options, you may use the trans_choice function to retrieve the line for a given "count". In this example, since the count is greater than one, the plural form of the translation string is returned:

echo trans_choice('messages.apples', 10);
You may also define placeholder attributes in pluralization strings. These placeholders may be replaced by passing an array as the third argument to the trans_choice function:

'minutes_ago' => '{1} :value minute ago|[2,*] :value minutes ago',

echo trans_choice('time.minutes_ago', 5, ['value' => 5]);
If you would like to display the integer value that was passed to the trans_choice function, you may use the built-in :count placeholder:

'apples' => '{0} There are none|{1} There is one|[2,*] There are :count',
Overriding Package Language Files
Some packages may ship with their own language files. Instead of changing the package's core files to tweak these lines, you may override them by placing files in the resources/lang/vendor/{package}/{locale} directory.

So, for example, if you need to override the English translation strings in messages.php for a package named skyrim/hearthfire, you should place a language file at: resources/lang/vendor/hearthfire/en/messages.php. Within this file, you should only define the translation strings you wish to override. Any translation strings you don't override will still be loaded from the package's original language files.