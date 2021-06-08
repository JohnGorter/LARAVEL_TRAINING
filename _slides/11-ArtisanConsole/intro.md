# Artisan Console


--
## In this lesson
- tinker (REPL)
- writing Commands
- generating Commands
- command Structure
- closure Commands
- defining Input Expectations
- arguments
- options

--
## In this lesson
- tinker (REPL)
- input Arrays
- input Descriptions
- command I/O
- retrieving Input
- prompting For Input
- writing Output
- registering Commands

--
## In this lesson
- tinker (REPL)
- programmatically Executing Commands
- calling Commands From Other Commands
- signal Handling
- stub Customization
- events

--
## Introduction
Artisan is the command line interface included with Laravel

Artisan provides a number of helpful commands
- php artisan list -> view a list of all available Artisan commands

Every command also includes a "help" screen
```
php artisan help migrate
```

--
## Laravel Sail
If you are using Laravel Sail as your local development environment, remember to use the sail command line to invoke Artisan commands. 

Sail will execute your Artisan commands within your application's Docker containers:
```
./sail artisan list
```

--
## Tinker (REPL)
Laravel Tinker is a powerful REPL for the Laravel framework, powered by the PsySH package.

--
## Installation
All Laravel applications include Tinker by default. 
- you may install Tinker using Composer 
```
composer require laravel/tinker
```

> Looking for a graphical UI for interacting with your Laravel application? Check out Tinkerwell!

--
## Usage
Tinker allows you to interact with your entire Laravel application on the command line
```
php artisan tinker
```

You can publish Tinker's configuration file using the vendor:publish command:
```
php artisan vendor:publish --provider="Laravel\Tinker\TinkerServiceProvider"
```

The dispatch helper function and dispatch method on the Dispatchable class depends on garbage collection to place the job on the queue. Therefore, when using tinker, you should use Bus::dispatch or Queue::push to dispatch jobs.


--
## Command Allow List
Tinker utilizes an "allow" list to determine which Artisan commands are allowed to be run within its shell

By default
- clear-compiled
- down
- env 
- inspire
- migrate
- optimize
- up commands

If you would like to allow more commands you may add them to the commands array in your tinker.php configuration file
```
'commands' => [
    // App\Console\Commands\ExampleCommand::class,
],
```

--
## Classes That Should Not Be Aliased
Typically, Tinker automatically aliases classes as you interact with them in Tinker

However, you may wish to never alias some classes
```
'dont_alias' => [
    App\Models\User::class,
],
```

--
## Writing Commands
In addition to the commands provided with Artisan, you may build your own custom commands

Commands are typically stored in the app/Console/Commands directory 

you are free to choose your own storage location as long as your commands can be loaded by Composer

--
## Generating Commands
To create a new command, you may use the make:command Artisan command. This command will create a new command class in the app/Console/Commands directory. Don't worry if this directory does not exist in your application - it will be created the first time you run the make:command Artisan command:
```
php artisan make:command SendEmails
```

--
## Command Structure
After generating your command, define appropriate values for the signature and description properties of the class 

These properties will be used when displaying your command on the list screen
- the signature property also allows you to define your command's input expectations
- the handle method will be called when your command is executed

--
## Example
Let's take a look at an example command
```
<?php

namespace App\Console\Commands;

use App\Models\User;
use App\Support\DripEmailer;
use Illuminate\Console\Command;

class SendEmails extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'mail:send {user}';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Send a marketing email to a user';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
    }

    /**
     * Execute the console command.
     *
     * @param  \App\Support\DripEmailer  $drip
     * @return mixed
     */
    public function handle(DripEmailer $drip)
    {
        $drip->send(User::find($this->argument('user')));
    }
}
```

> For greater code reuse, it is good practice to keep your console commands light and let them defer to application services to accomplish their tasks. In the example above, note that we > > inject a service class to do the "heavy lifting" of sending the e-mails.

--
## Closure Commands
Closure based commands provide an alternative to defining console commands as classes

```
/**
 * Register the closure based commands for the application.
 *
 * @return void
 */
protected function commands()
{
    require base_path('routes/console.php');
}
```

Even though this file does not define HTTP routes, it defines console based entry points (routes) into your application

--
## Closure Commands

Within this file, you may define all of your closure based console commands using the Artisan::command method
The command method accepts two arguments
- the command signature 
- a closure which receives the command's arguments and options

```
Artisan::command('mail:send {user}', function ($user) {
    $this->info("Sending email to: {$user}!");
});
```

> The closure is bound to the underlying command instance, so you have full access to all of the helper methods you would typically be able to access on a full command class

--
## Type-Hinting Dependencies
In addition to receiving your command's arguments and options, command closures may also type-hint additional dependencies that you would like resolved out of the service container:

```
use App\Models\User;
use App\Support\DripEmailer;

Artisan::command('mail:send {user}', function (DripEmailer $drip, $user) {
    $drip->send(User::find($user));
});
```

--
## Closure Command Descriptions
When defining a closure based command, you may use the purpose method to add a description to the command

This description will be displayed when you run the php artisan list or php artisan help commands:
```
Artisan::command('mail:send {user}', function ($user) {
    // ...
})->purpose('Send a marketing email to a user');
```

--
## Defining Input Expectations
When writing console commands, it is common to gather input from the user through arguments or options

Laravel makes it very convenient to define the input you expect from the user using the signature property on your commands
The signature property allows you to define 
- the name
- the arguments
- the options

--
## Arguments
All user supplied arguments and options are wrapped in curly braces

In the following example, the command defines one required argument: user

```
/**
 * The name and signature of the console command.
 *
 * @var string
 */
protected $signature = 'mail:send {user}';
You may also make arguments optional or define default values for arguments:

// Optional argument...
mail:send {user?}

// Optional argument with default value...
mail:send {user=foo}
```

--
## Options
Options, like arguments, are another form of user input

Options are prefixed by two hyphens (--) when they are provided via the command line

There are two types of options: 
- those that receive a value 
- those that don't

> Options that don't receive a value serve as a boolean "switch"

--
## Example 
Let's take a look at an example of this type of option:

```
/**
 * The name and signature of the console command.
 *
 * @var string
 */
protected $signature = 'mail:send {user} {--queue}';
```

In this example, the --queue switch may be specified when calling the Artisan command
```
php artisan mail:send 1 --queue
```

--
## Options With Values
Next, let's take a look at an option that expects a value
```
/**
 * The name and signature of the console command.
 *
 * @var string
 */
protected $signature = 'mail:send {user} {--queue=}';
```
In this example, the user may pass a value for the option like so
- if the option is not specified when invoking the command, its value will be null
```
php artisan mail:send 1 --queue=default
```
You may assign default values to options by specifying the default value after the option name
```
mail:send {user} {--queue=default}
```

--
## Option Shortcuts
To assign a shortcut when defining an option, you may specify it before the option name and use the | character as a delimiter to separate the shortcut from the full option name
```
mail:send {user} {--Q|queue}
```

--
## Input Arrays
If you would like to define arguments or options to expect multiple input values, you may use the * character
```
mail:send {user*}
```
When calling this method, the user arguments may be passed in order to the command line
```
php artisan mail:send foo bar
```
This * character can be combined with an optional argument definition to allow zero or more instances of an argument:
```
mail:send {user?*}
```

--
## Option Arrays
When defining an option that expects multiple input values, each option value passed to the command should be prefixed with the option name
```
mail:send {user} {--id=*}

php artisan mail:send --id=1 --id=2
```

--
## Input Descriptions
You may assign descriptions to input arguments and options by separating the argument name from the description using a colon. If you need a little extra room to define your command, feel free to spread the definition across multiple lines:

/**
 * The name and signature of the console command.
 *
 * @var string
 */
protected $signature = 'mail:send
                        {user : The ID of the user}
                        {--queue= : Whether the job should be queued}';
Command I/O
Retrieving Input
While your command is executing, you will likely need to access the values for the arguments and options accepted by your command. To do so, you may use the argument and option methods. If an argument or option does not exist, null will be returned:

/**
 * Execute the console command.
 *
 * @return int
 */
public function handle()
{
    $userId = $this->argument('user');

    //
}
If you need to retrieve all of the arguments as an array, call the arguments method:

$arguments = $this->arguments();
Options may be retrieved just as easily as arguments using the option method. To retrieve all of the options as an array, call the options method:

// Retrieve a specific option...
$queueName = $this->option('queue');

// Retrieve all options as an array...
$options = $this->options();
Prompting For Input
In addition to displaying output, you may also ask the user to provide input during the execution of your command. The ask method will prompt the user with the given question, accept their input, and then return the user's input back to your command:

/**
 * Execute the console command.
 *
 * @return mixed
 */
public function handle()
{
    $name = $this->ask('What is your name?');
}
The secret method is similar to ask, but the user's input will not be visible to them as they type in the console. This method is useful when asking for sensitive information such as passwords:

$password = $this->secret('What is the password?');
Asking For Confirmation
If you need to ask the user for a simple "yes or no" confirmation, you may use the confirm method. By default, this method will return false. However, if the user enters y or yes in response to the prompt, the method will return true.

if ($this->confirm('Do you wish to continue?')) {
    //
}
If necessary, you may specify that the confirmation prompt should return true by default by passing true as the second argument to the confirm method:

if ($this->confirm('Do you wish to continue?', true)) {
    //
}
Auto-Completion
The anticipate method can be used to provide auto-completion for possible choices. The user can still provide any answer, regardless of the auto-completion hints:

$name = $this->anticipate('What is your name?', ['Taylor', 'Dayle']);
Alternatively, you may pass a closure as the second argument to the anticipate method. The closure will be called each time the user types an input character. The closure should accept a string parameter containing the user's input so far, and return an array of options for auto-completion:

$name = $this->anticipate('What is your address?', function ($input) {
    // Return auto-completion options...
});
Multiple Choice Questions
If you need to give the user a predefined set of choices when asking a question, you may use the choice method. You may set the array index of the default value to be returned if no option is chosen by passing the index as the third argument to the method:

$name = $this->choice(
    'What is your name?',
    ['Taylor', 'Dayle'],
    $defaultIndex
);
In addition, the choice method accepts optional fourth and fifth arguments for determining the maximum number of attempts to select a valid response and whether multiple selections are permitted:

$name = $this->choice(
    'What is your name?',
    ['Taylor', 'Dayle'],
    $defaultIndex,
    $maxAttempts = null,
    $allowMultipleSelections = false
);
Writing Output
To send output to the console, you may use the line, info, comment, question, warn, and error methods. Each of these methods will use appropriate ANSI colors for their purpose. For example, let's display some general information to the user. Typically, the info method will display in the console as green colored text:

/**
 * Execute the console command.
 *
 * @return mixed
 */
public function handle()
{
    // ...

    $this->info('The command was successful!');
}
To display an error message, use the error method. Error message text is typically displayed in red:

$this->error('Something went wrong!');
You may use the line method to display plain, uncolored text:

$this->line('Display this on the screen');
You may use the newLine method to display a blank line:

// Write a single blank line...
$this->newLine();

// Write three blank lines...
$this->newLine(3);
Tables
The table method makes it easy to correctly format multiple rows / columns of data. All you need to do is provide the column names and the data for the table and Laravel will automatically calculate the appropriate width and height of the table for you:

use App\Models\User;

$this->table(
    ['Name', 'Email'],
    User::all(['name', 'email'])->toArray()
);
Progress Bars
For long running tasks, it can be helpful to show a progress bar that informs users how complete the task is. Using the withProgressBar method, Laravel will display a progress bar and advance its progress for each iteration over a given iterable value:

use App\Models\User;

$users = $this->withProgressBar(User::all(), function ($user) {
    $this->performTask($user);
});
Sometimes, you may need more manual control over how a progress bar is advanced. First, define the total number of steps the process will iterate through. Then, advance the progress bar after processing each item:

$users = App\Models\User::all();

$bar = $this->output->createProgressBar(count($users));

$bar->start();

foreach ($users as $user) {
    $this->performTask($user);

    $bar->advance();
}

$bar->finish();

For more advanced options, check out the Symfony Progress Bar component documentation.


Registering Commands
All of your console commands are registered within your application's App\Console\Kernel class, which is your application's "console kernel". Within the commands method of this class, you will see a call to the kernel's load method. The load method will scan the app/Console/Commands directory and automatically register each command it contains with Artisan. You are even free to make additional calls to the load method to scan other directories for Artisan commands:

/**
 * Register the commands for the application.
 *
 * @return void
 */
protected function commands()
{
    $this->load(__DIR__.'/Commands');
    $this->load(__DIR__.'/../Domain/Orders/Commands');

    // ...
}
If necessary, you may manually register commands by adding the command's class name to the $commands property of your App\Console\Kernel class. When Artisan boots, all the commands listed in this property will be resolved by the service container and registered with Artisan:

protected $commands = [
    Commands\SendEmails::class
];
Programmatically Executing Commands
Sometimes you may wish to execute an Artisan command outside of the CLI. For example, you may wish to execute an Artisan command from a route or controller. You may use the call method on the Artisan facade to accomplish this. The call method accepts either the command's signature name or class name as its first argument, and an array of command parameters as the second argument. The exit code will be returned:

use Illuminate\Support\Facades\Artisan;

Route::post('/user/{user}/mail', function ($user) {
    $exitCode = Artisan::call('mail:send', [
        'user' => $user, '--queue' => 'default'
    ]);

    //
});
Alternatively, you may pass the entire Artisan command to the call method as a string:

Artisan::call('mail:send 1 --queue=default');
Passing Array Values
If your command defines an option that accepts an array, you may pass an array of values to that option:

use Illuminate\Support\Facades\Artisan;

Route::post('/mail', function () {
    $exitCode = Artisan::call('mail:send', [
        '--id' => [5, 13]
    ]);
});
Passing Boolean Values
If you need to specify the value of an option that does not accept string values, such as the --force flag on the migrate:refresh command, you should pass true or false as the value of the option:

$exitCode = Artisan::call('migrate:refresh', [
    '--force' => true,
]);
Queueing Artisan Commands
Using the queue method on the Artisan facade, you may even queue Artisan commands so they are processed in the background by your queue workers. Before using this method, make sure you have configured your queue and are running a queue listener:

use Illuminate\Support\Facades\Artisan;

Route::post('/user/{user}/mail', function ($user) {
    Artisan::queue('mail:send', [
        'user' => $user, '--queue' => 'default'
    ]);

    //
});
Using the onConnection and onQueue methods, you may specify the connection or queue the Artisan command should be dispatched to:

Artisan::queue('mail:send', [
    'user' => 1, '--queue' => 'default'
])->onConnection('redis')->onQueue('commands');
Calling Commands From Other Commands
Sometimes you may wish to call other commands from an existing Artisan command. You may do so using the call method. This call method accepts the command name and an array of command arguments / options:

/**
 * Execute the console command.
 *
 * @return mixed
 */
public function handle()
{
    $this->call('mail:send', [
        'user' => 1, '--queue' => 'default'
    ]);

    //
}
If you would like to call another console command and suppress all of its output, you may use the callSilently method. The callSilently method has the same signature as the call method:

$this->callSilently('mail:send', [
    'user' => 1, '--queue' => 'default'
]);
Signal Handling
The Symfony Console component, which powers the Artisan console, allows you to indicate which process signals (if any) your command handles. For example, you may indicate that your command handles the SIGINT and SIGTERM signals.

To get started, you should implement the Symfony\Component\Console\Command\SignalableCommandInterface interface on your Artisan command class. This interface requires you to define two methods: getSubscribedSignals and handleSignal:

<?php

use Symfony\Component\Console\Command\SignalableCommandInterface;

class StartServer extends Command implements SignalableCommandInterface
{
    // ...

    /**
     * Get the list of signals handled by the command.
     *
     * @return array
     */
    public function getSubscribedSignals(): array
    {
        return [SIGINT, SIGTERM];
    }

    /**
     * Handle an incoming signal.
     *
     * @param  int  $signal
     * @return void
     */
    public function handleSignal(int $signal): void
    {
        if ($signal === SIGINT) {
            $this->stopServer();

            return;
        }
    }
}
As you might expect, the getSubscribedSignals method should return an array of the signals that your command can handle, while the handleSignal method receives the signal and can respond accordingly.

Stub Customization
The Artisan console's make commands are used to create a variety of classes, such as controllers, jobs, migrations, and tests. These classes are generated using "stub" files that are populated with values based on your input. However, you may want to make small changes to files generated by Artisan. To accomplish this, you may use the stub:publish command to publish the most common stubs to your application so that you can customize them:

php artisan stub:publish
The published stubs will be located within a stubs directory in the root of your application. Any changes you make to these stubs will be reflected when you generate their corresponding classes using Artisan's make commands.

Events
Artisan dispatches three events when running commands: Illuminate\Console\Events\ArtisanStarting, Illuminate\Console\Events\CommandStarting, and Illuminate\Console\Events\CommandFinished. The ArtisanStarting event is dispatched immediately when Artisan starts running. Next, the CommandStarting event is dispatched immediately before a command runs. Finally, the CommandFinished event is dispatched once a command finishes executing.