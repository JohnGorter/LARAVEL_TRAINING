# Databases

--
## In this lesson
We will cover
- Introduction
- Configuration
- Read & Write Connections
- Running SQL Queries
- Using Multiple Database Connections
- Listening For Query Events
- Database Transactions
- Connecting To The Database CLI

--
## Introduction
Laravel supports database interaction using
- raw SQL
- fluent query builder
- Eloquent ORM

--
## Introduction
Currently, Laravel provides first-party support for four databases

- MySQL 5.7+ (Version Policy)
- PostgreSQL 9.6+ (Version Policy)
- SQLite 3.8.8+
- SQL Server 2017+ (Version Policy)

--
## Configuration
The configuration is located in config/database.php

--
## SQLite Configuration
SQLite databases are
- a single file on your filesystem

``` 
touch database/database.sqlite
``` 

Then configure environment variable
```
DB_CONNECTION=sqlite
DB_DATABASE=/absolute/path/to/database.sqlite
```

--
## SQLite Configuration
To enable foreign key constraints for SQLite connections, 
set the DB_FOREIGN_KEYS environment

```
DB_FOREIGN_KEYS=true
```

--
## Microsoft SQL Server Configuration
To use a Microsoft SQL Server database
- have sqlsrv and pdo_sqlsrv PHP extensions installed 
- have Microsoft SQL ODBC driver installed

--
## Configuration Using URLs
Database connections are configured using multiple configuration values such as host, database, username, password, etc. 

Each of these configuration values has its own corresponding environment variable. This means that when configuring your database connection information on a production server, you need to manage several environment variables.

An example database URL 
```
mysql://root:password@127.0.0.1/forge?charset=UTF-8
```
These URLs typically follow a standard schema convention:
```
driver://username:password@host:port/database?options
```

--
## Read & Write Connections
Sometimes you may wish to use one database connection for SELECT statements, and another for INSERT, UPDATE, and DELETE statements

```
'mysql' => [
    'read' => [
        'host' => [
            '192.168.1.1',
            '196.168.1.2',
        ],
    ],
    'write' => [
        'host' => [
            '196.168.1.3',
        ],
    ],
    'sticky' => true,
    'driver' => 'mysql',
    'database' => 'database',
    'username' => 'root',
    'password' => '',
    'charset' => 'utf8mb4',
    'collation' => 'utf8mb4_unicode_ci',
    'prefix' => '',
]
```

--
## The sticky Option
The sticky option is an optional value that can be used to allow the immediate reading of records that have been written to the database during the current request cycle. 

If the sticky option is enabled and a "write" operation has been performed against the database during the current request cycle, any further "read" operations will use the "write" connection. This ensures that any data written during the request cycle can be immediately read back from the database during that same request.

--
## Running SQL Queries
Once you have configured your database connection, run queries using the DB facade. 

This DB facade provides methods for each type of query: select, update, insert, delete, and statement.

--
## Running A Select Query
To run a basic SELECT query
```
<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\DB;

class UserController extends Controller
{
    /**
     * Show a list of all of the application's users.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $users = DB::select('select * from users where active = ?', [1]);

        return view('user.index', ['users' => $users]);
    }
}
```

--
## Running A Select Query
The select method will always return an array of results. 

Each result within the array will be a PHP stdClass object representing a record from the database:

```
use Illuminate\Support\Facades\DB;

$users = DB::select('select * from users');

foreach ($users as $user) {
    echo $user->name;
}
```

--
## Using Named Bindings
Instead of using ? to represent your parameter bindings, you may execute a query using named bindings:
```
$results = DB::select('select * from users where id = :id', ['id' => 1]);
```

--
## Running An Insert Statement
To execute an insert statement, you may use the insert method on the DB facade

```
use Illuminate\Support\Facades\DB;

DB::insert('insert into users (id, name) values (?, ?)', [1, 'Marc']);
```

--
## Running An Update Statement
The update method should be used to update existing records in the database. The number of rows affected by the statement is returned by the method

```
use Illuminate\Support\Facades\DB;

$affected = DB::update(
    'update users set votes = 100 where name = ?',
    ['Anita']
);
```

--
## Running A Delete Statement
The delete method should be used to delete records from the database

```
use Illuminate\Support\Facades\DB;

$deleted = DB::delete('delete from users');
```

--
## Running A General Statement
Some database statements do not return any value. For these types of operations, use the statement method on the DB facade
```
DB::statement('drop table users');
```

--
## Running An Unprepared Statement
Sometimes you may want to execute an SQL statement without binding any values. You may use the DB facade's unprepared method to accomplish this

```
DB::unprepared('update users set votes = 100 where name = "Dries"');
```
Since unprepared statements do not bind parameters, they may be vulnerable to SQL injection

--
## Implicit Commits
When using the DB facade's statement and unprepared methods within transactions you must be careful to avoid statements that cause implicit commits. 

These statements will cause the database engine to indirectly commit the entire transaction, leaving Laravel unaware of the database's transaction level. 

An example of such a statement is creating a database table
```
DB::unprepared('create table a (col varchar(1) null)');
```

Please refer to the MySQL manual for a list of all statements that trigger implicit commits

--
## Using Multiple Database Connections
If your application defines multiple connections in your config/database.php file, you may access each connection via the connection method provided by the DB facade. 

The connection name passed to the connection method should correspond to one of the connections listed in your config/database.php file or configured at runtime using the config helper:

```
use Illuminate\Support\Facades\DB;

$users = DB::connection('sqlite')->select(...);
```

--
## Using Multiple Database Connections
You may access the raw, underlying PDO instance of a connection using the getPdo method on a connection instance:
```
$pdo = DB::connection()->getPdo();
```

--
## Listening For Query Events
If you would like to specify a closure that is invoked for each SQL query executed by your application, you may use the DB facade's listen method

```
<?php

namespace App\Providers;

use Illuminate\Support\Facades\DB;
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
        DB::listen(function ($query) {
            // $query->sql;
            // $query->bindings;
            // $query->time;
        });
    }
}
```

--
## Database Transactions
You may use the transaction method provided by the DB facade to run a set of operations within a database transaction. 

If an exception is thrown within the transaction closure, the transaction will automatically be rolled back. If the closure executes successfully, the transaction will automatically be committed

```
use Illuminate\Support\Facades\DB;

DB::transaction(function () {
    DB::update('update users set votes = 1');

    DB::delete('delete from posts');
});
```

--
## Handling Deadlocks
The transaction method accepts an optional second argument which defines the number of times a transaction should be retried when a deadlock occurs

```
use Illuminate\Support\Facades\DB;

DB::transaction(function () {
    DB::update('update users set votes = 1');

    DB::delete('delete from posts');
}, 5);
```

--
## Manually Using Transactions
If you would like to begin a transaction manually and have complete control over rollbacks and commits, you may use the beginTransaction method provided by the DB facade

```
use Illuminate\Support\Facades\DB;

DB::beginTransaction();
```
You can rollback the transaction via the rollBack method
```
DB::rollBack();
```
Lastly, you can commit a transaction via the commit method
```
DB::commit();
```

--
## Connecting To The Database CLI
If you would like to connect to your database's CLI, you may use the db Artisan command:
```
php artisan db
```

If needed, you may specify a database connection name
```
php artisan db mysql
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Database Queries


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!