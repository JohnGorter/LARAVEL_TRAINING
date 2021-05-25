# Query Builder

--
## In this lesson
- Introduction
- Running Database Queries
- Chunking Results
- Streaming Results Lazily
- Aggregates
- Select Statements
- Raw Expressions
- Joins
- Unions

--
## In this lesson
- Basic Where Clauses
- Where Clauses
- Or Where Clauses
- JSON Where Clauses
- Additional Where Clauses
- Logical Grouping
- Advanced Where Clauses
- Where Exists Clauses
- Subquery Where Clauses
- Ordering, Grouping, Limit & Offset

--
## In this lesson
- Ordering
- Grouping
- Limit & Offset
- Conditional Clauses
- Insert Statements
- Upserts
- Update Statements
- Updating JSON Columns

--
## In this lesson
- Increment & Decrement
- Delete Statements
- Pessimistic Locking
- Debugging

--
##  Introduction
Laravel's database query builder provides a convenient, fluent interface to creating and running database queries
The Laravel query builder uses PDO parameter binding to protect your application against SQL injection attacks. 

--
## Running Database Queries
Retrieving All Rows From A Table
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
        $users = DB::table('users')->get();

        return view('user.index', ['users' => $users]);
    }
}
```

--
## Running Database Queries
The get method returns an Illuminate\Support\Collection instance containing the results of the query 
- each result is an instance of the PHP stdClass object
- access each column's value by accessing the column as a property of the object

```
use Illuminate\Support\Facades\DB;

$users = DB::table('users')->get();

foreach ($users as $user) {
    echo $user->name;
}
```
--
## Retrieving A Single Row / Column From A Table
If you just need to retrieve a single row from a database table, you may use the DB facade's first method. This method will return a single stdClass object:

```
$user = DB::table('users')->where('name', 'John')->first();

return $user->email;
```

--
## Retrieving A Single Row / Column From A Table
If you don't need an entire row, you may extract a single value from a record using the value method. This method will return the value of the column directly

```
$email = DB::table('users')->where('name', 'John')->value('email');
```

--
## Retrieving A Single Row / Column From A Table
To retrieve a single row by its id column value, use the find method

```
$user = DB::table('users')->find(3);
```

--
## Retrieving A List Of Column Values
If you would like to retrieve an Illuminate\Support\Collection instance containing the values of a single column, you may use the pluck method

```
use Illuminate\Support\Facades\DB;
$titles = DB::table('users')->pluck('title');
foreach ($titles as $title) {
    echo $title;
}
```

--
## Retrieving A List Of Column Values
You may specify the column that the resulting collection should use as its keys by providing a second argument to the pluck method

```
$titles = DB::table('users')->pluck('title', 'name');

foreach ($titles as $name => $title) {
    echo $title;
}
```

--
## Chunking Results
If you need to work with thousands of database records, consider using the chunk method provided by the DB facade. This method retrieves a small chunk of results at a time and feeds each chunk into a closure for processing. For example, let's retrieve the entire users table in chunks of 100 records at a time

```
use Illuminate\Support\Facades\DB;

DB::table('users')->orderBy('id')->chunk(100, function ($users) {
    foreach ($users as $user) {
        //
    }
});
```

--
## Chunking Results
You may stop further chunks from being processed by returning false from the closure

```
DB::table('users')->orderBy('id')->chunk(100, function ($users) {
    // Process the records...

    return false;
});
```

--
## Streaming Results Lazily
The lazy method works similarly to the chunk method in the sense that it executes the query in chunks

However, instead of passing each chunk into a callback, the lazy() method returns a LazyCollection, which lets you interact with the results as a single stream
```
use Illuminate\Support\Facades\DB;

DB::table('users')->lazy()->each(function ($user) {
    //
});
```

--
## Aggregates
The query builder also provides a variety of methods for retrieving aggregate values like count, max, min, avg, and sum

```
use Illuminate\Support\Facades\DB;

$users = DB::table('users')->count();

$price = DB::table('orders')->max('price');
```

--
## Aggregates
Of course, you may combine these methods with other clauses to fine-tune how your aggregate value is calculated:

```
$price = DB::table('orders')
                ->where('finalized', 1)
                ->avg('price');
```

--
## Determining If Records Exist
Instead of using the count method to determine if any records exist that match your query's constraints, you may use the exists and doesntExist methods
```
if (DB::table('orders')->where('finalized', 1)->exists()) {
    // ...
}

if (DB::table('orders')->where('finalized', 1)->doesntExist()) {
    // ...
}
```

--
## Select Statements
Specifying A Select Clause
```
use Illuminate\Support\Facades\DB;

$users = DB::table('users')
            ->select('name', 'email as user_email')
            ->get();
```
The distinct method allows you to force the query to return distinct results:
```
$users = DB::table('users')->distinct()->get();
```

--
## Select Statements
If you already have a query builder instance and you wish to add a column to its existing select clause, you may use the addSelect method:
```
$query = DB::table('users')->select('name');

$users = $query->addSelect('age')->get();
```

--
## Raw Expressions
Sometimes you may need to insert an arbitrary string into a query

```
$users = DB::table('users')
             ->select(DB::raw('count(*) as user_count, status'))
             ->where('status', '<>', 1)
             ->groupBy('status')
             ->get();
```

Raw statements will be injected into the query as strings, so you should be extremely careful to avoid creating SQL injection vulnerabilities

--
## Raw Methods
Instead of using the DB::raw method, you may also use the following methods to insert a raw expression into various parts of your query
```
$orders = DB::table('orders')
                ->selectRaw('price * ? as price_with_tax', [1.0825])
                ->get();
```
--
## Raw Methods
```
$orders = DB::table('orders')
                ->whereRaw('price > IF(state = "TX", ?, 100)', [200])
                ->get();
```

--
## Raw Methods
```
$orders = DB::table('orders')
                ->select('department', DB::raw('SUM(price) as total_sales'))
                ->groupBy('department')
                ->havingRaw('SUM(price) > ?', [2500])
                ->get();
```

--
## Raw Methods
```
$orders = DB::table('orders')
                ->orderByRaw('updated_at - created_at DESC')
                ->get();
```

--
## Raw Methods
```
$orders = DB::table('orders')
                ->select('city', 'state')
                ->groupByRaw('city, state')
                ->get();
```

--
## Joins
Inner Join Clause
```
use Illuminate\Support\Facades\DB;
$users = DB::table('users')
            ->join('contacts', 'users.id', '=', 'contacts.user_id')
            ->join('orders', 'users.id', '=', 'orders.user_id')
            ->select('users.*', 'contacts.phone', 'orders.price')
            ->get();
```
--
## Joins
Left Join / Right Join Clause
```
$users = DB::table('users')
            ->leftJoin('posts', 'users.id', '=', 'posts.user_id')
            ->get();

$users = DB::table('users')
            ->rightJoin('posts', 'users.id', '=', 'posts.user_id')
            ->get();
```

--
## Joins
Cross Join Clause
```
$sizes = DB::table('sizes')
            ->crossJoin('colors')
            ->get();
```

--
## Advanced Join Clauses
You may also specify more advanced join clauses. To get started, pass a closure as the second argument to the join method. The closure will receive a Illuminate\Database\Query\JoinClause instance which allows you to specify constraints on the "join" clause:
```
DB::table('users')
        ->join('contacts', function ($join) {
            $join->on('users.id', '=', 'contacts.user_id')->orOn(...);
        })
        ->get();
```

--
## Advanced Join Clauses
If you would like to use a "where" clause on your joins, you may use the where and orWhere methods provided by the JoinClause instance. Instead of comparing two columns, these methods will compare the column against a value:

```
DB::table('users')
        ->join('contacts', function ($join) {
            $join->on('users.id', '=', 'contacts.user_id')
                 ->where('contacts.user_id', '>', 5);
        })
        ->get();
```

--
## Subquery Joins
You may use the joinSub, leftJoinSub, and rightJoinSub methods to join a query to a subquery. Each of these methods receives three arguments: the subquery

```
$latestPosts = DB::table('posts')
                   ->select('user_id', DB::raw('MAX(created_at) as last_post_created_at'))
                   ->where('is_published', true)
                   ->groupBy('user_id');

$users = DB::table('users')
        ->joinSub($latestPosts, 'latest_posts', function ($join) {
            $join->on('users.id', '=', 'latest_posts.user_id');
        })->get();
```

--
## Unions
The query builder also provides a convenient method to "union" two or more queries together

```
use Illuminate\Support\Facades\DB;

$first = DB::table('users')
            ->whereNull('first_name');

$users = DB::table('users')
            ->whereNull('last_name')
            ->union($first)
            ->get();
```

In addition to the union method, the query builder provides a unionAll method

Queries that are combined using the unionAll method will not have their duplicate results removed

--
## Basic Where Clauses
Where Clauses

```
$users = DB::table('users')
                ->where('votes', '=', 100)
                ->where('age', '>', 35)
                ->get();
```

For convenience, if you want to verify that a column is = to a given value, you may pass the value as the second argument to the where method

```
$users = DB::table('users')->where('votes', 100)->get();
```

--
## Basic Where Clauses
You may also pass an array of conditions to the where function. Each element of the array should be an array containing the three arguments typically passed to the where method:

```
$users = DB::table('users')->where([
    ['status', '=', '1'],
    ['subscribed', '<>', '1'],
])->get();
```

--
## Or Where Clauses
When chaining together calls to the query builder's where method, the "where" clauses will be joined together using the and operator. However, you may use the orWhere method to join a clause to the query using the or operator

```
$users = DB::table('users')
                    ->where('votes', '>', 100)
                    ->orWhere('name', 'John')
                    ->get();
```

--
## Or Where Clauses
If you need to group an "or" condition within parentheses, you may pass a closure as the first argument to the orWhere method:
```
$users = DB::table('users')
            ->where('votes', '>', 100)
            ->orWhere(function($query) {
                $query->where('name', 'Abigail')
                      ->where('votes', '>', 50);
            })
            ->get();

 // select * from users where votes > 100 or (name = 'Abigail' and votes > 50)
```

--
## JSON Where Clauses
Laravel also supports querying JSON column types on databases that provide support for JSON column types. 

Currently, this includes MySQL 5.7+, PostgreSQL, SQL Server 2016, and SQLite 3.9.0 (with the JSON1 extension). 

To query a JSON column, use the -> operator:
```
$users = DB::table('users')
                ->where('preferences->dining->meal', 'salad')
                ->get();
```

--
## JSON Where Clauses
You may use whereJsonContains to query JSON arrays. This feature is not supported by the SQLite database:
```
$users = DB::table('users')
                ->whereJsonContains('options->languages', 'en')
                ->get();
```

--
## JSON Where Clauses
If your application uses the MySQL or PostgreSQL databases, you may pass an array of values to the whereJsonContains method:
```
$users = DB::table('users')
                ->whereJsonContains('options->languages', ['en', 'de'])
                ->get();
```

--
## JSON Where Clauses
You may use whereJsonLength method to query JSON arrays by their length:
```
$users = DB::table('users')
                ->whereJsonLength('options->languages', 0)
                ->get();

$users = DB::table('users')
                ->whereJsonLength('options->languages', '>', 1)
                ->get();
```

--
## Additional Where Clauses
whereBetween / orWhereBetween

The whereBetween method verifies that a column's value is between two values
```
$users = DB::table('users')
           ->whereBetween('votes', [1, 100])
           ->get();
```

--
## Additional Where Clauses
whereNotBetween / orWhereNotBetween

The whereNotBetween method verifies that a column's value lies outside of two values
```
$users = DB::table('users')
                    ->whereNotBetween('votes', [1, 100])
                    ->get();
```

--
## Additional Where Clauses
whereIn / whereNotIn / orWhereIn / orWhereNotIn

The whereIn method verifies that a given column's value is contained within the given array
```
$users = DB::table('users')
                    ->whereIn('id', [1, 2, 3])
                    ->get();
```
The whereNotIn method verifies that the given column's value is not contained in the given array:
```
$users = DB::table('users')
                    ->whereNotIn('id', [1, 2, 3])
                    ->get();
```

--
## Additional Where Clauses
whereNull / whereNotNull / orWhereNull / orWhereNotNull

The whereNull method verifies that the value of the given column is NULL
```
$users = DB::table('users')
                ->whereNull('updated_at')
                ->get();
```
The whereNotNull method verifies that the column's value is not NULL
```
$users = DB::table('users')
                ->whereNotNull('updated_at')
                ->get();
```

--
## Additional Where Clauses
whereDate / whereMonth / whereDay / whereYear / whereTime

The whereDate method may be used to compare a column's value against a date
```
$users = DB::table('users')
                ->whereDate('created_at', '2016-12-31')
                ->get();
```

--
## Additional Where Clauses
The whereMonth method may be used to compare a column's value against a specific month
```
$users = DB::table('users')
                ->whereMonth('created_at', '12')
                ->get();
```

--
## Additional Where Clauses
The whereDay method may be used to compare a column's value against a specific day of the month
```
$users = DB::table('users')
                ->whereDay('created_at', '31')
                ->get();
```

--
## Additional Where Clauses
The whereYear method may be used to compare a column's value against a specific year
```
$users = DB::table('users')
                ->whereYear('created_at', '2016')
                ->get();
```

--
## Additional Where Clauses
The whereTime method may be used to compare a column's value against a specific time
```
$users = DB::table('users')
                ->whereTime('created_at', '=', '11:20:45')
                ->get();
```

--
## Additional Where Clauses
whereColumn / orWhereColumn

The whereColumn method may be used to verify that two columns are equal
```
$users = DB::table('users')
                ->whereColumn('first_name', 'last_name')
                ->get();
```
You may also pass a comparison operator to the whereColumn method
```
$users = DB::table('users')
                ->whereColumn('updated_at', '>', 'created_at')
                ->get();
```

--
## Additional Where Clauses
You may also pass an array of column comparisons to the whereColumn method. These conditions will be joined using the and operator
```
$users = DB::table('users')
                ->whereColumn([
                    ['first_name', '=', 'last_name'],
                    ['updated_at', '>', 'created_at'],
                ])->get();
```

--
## Logical Grouping
Sometimes you may need to group several "where" clauses within parentheses in order to achieve your query's desired logical grouping
```
$users = DB::table('users')
           ->where('name', '=', 'John')
           ->where(function ($query) {
               $query->where('votes', '>', 100)
                     ->orWhere('title', '=', 'Admin');
           })
           ->get();

// select * from users where name = 'John' and (votes > 100 or title = 'Admin')
```

--
## Advanced Where Clauses
Where Exists Clauses
The whereExists method allows you to write "where exists" SQL clauses. The whereExists method accepts a closure which will receive a query builder instance, allowing you to define the query that should be placed inside of the "exists" clause
```
$users = DB::table('users')
           ->whereExists(function ($query) {
               $query->select(DB::raw(1))
                     ->from('orders')
                     ->whereColumn('orders.user_id', 'users.id');
           })
           ->get();

// select * from users
// where exists (
//    select 1
//    from orders
//    where orders.user_id = users.id
//)
```

--
## Subquery Where Clauses
Sometimes you may need to construct a "where" clause that compares the results of a subquery to a given value

```
use App\Models\User;

$users = User::where(function ($query) {
    $query->select('type')
        ->from('membership')
        ->whereColumn('membership.user_id', 'users.id')
        ->orderByDesc('membership.start_date')
        ->limit(1);
}, 'Pro')->get();
```

--
## Subquery Where Clauses
Or, you may need to construct a "where" clause that compares a column to the results of a subquery


For example, the following query will retrieve all income records where the amount is less than average

```
use App\Models\Income;

$incomes = Income::where('amount', '<', function ($query) {
    $query->selectRaw('avg(i.amount)')->from('incomes as i');
})->get();
```

--
## Ordering, Grouping, Limit & Offset
Ordering
The orderBy Method

```
$users = DB::table('users')
                ->orderBy('name', 'desc')
                ->get();
```
To sort by multiple columns, you may simply invoke orderBy as many times as necessary:
```
$users = DB::table('users')
                ->orderBy('name', 'desc')
                ->orderBy('email', 'asc')
                ->get();
```

--
## The latest & oldest Methods
The latest and oldest methods allow you to easily order results by date
```
$user = DB::table('users')
                ->latest()
                ->first();
```

--
## Random Ordering
The inRandomOrder method may be used to sort the query results randomly

```
$randomUser = DB::table('users')
                ->inRandomOrder()
                ->first();
```

--
## Removing Existing Orderings
The reorder method removes all of the "order by" clauses that have previously been applied to the query
```
$query = DB::table('users')->orderBy('name');

$unorderedUsers = $query->reorder()->get();
```

--
## Removing Existing Orderings
You may pass a column and direction when calling the reorder method in order to remove all existing "order by" clauses and apply an entirely new order to the query:
```
$query = DB::table('users')->orderBy('name');

$usersOrderedByEmail = $query->reorder('email', 'desc')->get();
```

--
## Grouping
The groupBy & having Methods

```
$users = DB::table('users')
                ->groupBy('account_id')
                ->having('account_id', '>', 100)
                ->get();
```
You may pass multiple arguments to the groupBy method to group by multiple columns:
```
$users = DB::table('users')
                ->groupBy('first_name', 'status')
                ->having('account_id', '>', 100)
                ->get();
```

To build more advanced having statements, see the havingRaw method

--
## Limit & Offset
The skip & take Methods
```
$users = DB::table('users')->skip(10)->take(5)->get();
```
Alternatively, you may use the limit and offset methods
```
$users = DB::table('users')
                ->offset(10)
                ->limit(5)
                ->get();
```

--
## Conditional Clauses
Sometimes you may want certain query clauses to apply to a query based on another condition. For instance, you may only want to apply a where statement if a given input value is present on the incoming HTTP request

```
$role = $request->input('role');

$users = DB::table('users')
                ->when($role, function ($query, $role) {
                    return $query->where('role_id', $role);
                })
                ->get();
```

The when method only executes the given closure when the first argument is true

--
## Insert Statements
The query builder also provides an insert method that may be used to insert records into the database table

```
DB::table('users')->insert([
    'email' => 'kayla@example.com',
    'votes' => 0
]);
```
You may insert several records at once by passing an array of arrays
```
DB::table('users')->insert([
    ['email' => 'picard@example.com', 'votes' => 0],
    ['email' => 'janeway@example.com', 'votes' => 0],
]);
```

--
## Insert Statements
The insertOrIgnore method will ignore duplicate record errors while inserting records into the database
```
DB::table('users')->insertOrIgnore([
    ['id' => 1, 'email' => 'sisko@example.com'],
    ['id' => 2, 'email' => 'archer@example.com'],
]);
```

--
## Auto-Incrementing IDs
If the table has an auto-incrementing id, use the insertGetId method to insert a record and then retrieve the ID

```
$id = DB::table('users')->insertGetId(
    ['email' => 'john@example.com', 'votes' => 0]
);
```

When using PostgreSQL the insertGetId method expects the auto-incrementing column to be named id.

--
## Upserts
The upsert method will insert records that do not exist and update the records that already exist with new values that you may specify
```
DB::table('flights')->upsert([
    ['departure' => 'Oakland', 'destination' => 'San Diego', 'price' => 99],
    ['departure' => 'Chicago', 'destination' => 'New York', 'price' => 150]
], ['departure', 'destination'], ['price']);
```
In the example above, Laravel will attempt to insert two records. If a record already exists with the same departure and destination column values, Laravel will update that record's price column


--
## Update Statements
In addition to inserting records into the database, the query builder can also update existing records using the update method

```
$affected = DB::table('users')
              ->where('id', 1)
              ->update(['votes' => 1]);

```

--
## Update Or Insert
Sometimes you may want to update an existing record in the database or create it if no matching record exists. In this scenario, the updateOrInsert method may be used. The updateOrInsert method accepts two arguments: an array of conditions by which to find the record, and an array of column and value pairs indicating the columns to be updated.

The updateOrInsert method will attempt to locate a matching database record using the first argument's column and value pairs. If the record exists, it will be updated with the values in the second argument. If the record can not be found, a new record will be inserted with the merged attributes of both arguments:

```
DB::table('users')
    ->updateOrInsert(
        ['email' => 'john@example.com', 'name' => 'John'],
        ['votes' => '2']
    );
```

--
## Updating JSON Columns
When updating a JSON column, you should use -> syntax to update the appropriate key in the JSON object. This operation is supported on MySQL 5.7+ and PostgreSQL 9.5+

```
$affected = DB::table('users')
              ->where('id', 1)
              ->update(['options->enabled' => true]);
```

--
## Increment & Decrement
The query builder also provides convenient methods for incrementing or decrementing the value of a given column. Both of these methods accept at least one argument: the column to modify. A second argument may be provided to specify the amount by which the column should be incremented or decremented
```
DB::table('users')->increment('votes');

DB::table('users')->increment('votes', 5);

DB::table('users')->decrement('votes');

DB::table('users')->decrement('votes', 5);
```

You may also specify additional columns to update during the operation:
```
DB::table('users')->increment('votes', 1, ['name' => 'John']);
```

--
## Delete Statements
The query builder's delete method may be used to delete records from the table. You may constrain delete statements by adding "where" clauses before calling the delete method
```
DB::table('users')->delete();

DB::table('users')->where('votes', '>', 100)->delete();
```

If you wish to truncate an entire table, which will remove all records from the table and reset the auto-incrementing ID to zero, you may use the truncate method
```
DB::table('users')->truncate();
```

--
## Table Truncation & PostgreSQL
When truncating a PostgreSQL database, the CASCADE behavior will be applied. This means that all foreign key related records in other tables will be deleted as well

--
## Pessimistic Locking
The query builder also includes a few functions to help you achieve "pessimistic locking" when executing your select statements. To execute a statement with a "shared lock", you may call the sharedLock method. A shared lock prevents the selected rows from being modified until your transaction is committed

```
DB::table('users')
        ->where('votes', '>', 100)
        ->sharedLock()
        ->get();
```

--
## Pessimistic Locking
Alternatively, you may use the lockForUpdate method. A "for update" lock prevents the selected records from being modified or from being selected with another shared lock
```
DB::table('users')
        ->where('votes', '>', 100)
        ->lockForUpdate()
        ->get();
```

--
## Debugging
You may use the dd and dump methods while building a query to dump the current query bindings and SQL

- The dd method will display the debug information and then stop executing the request
- The dump method will display the debug information but allow the request to continue executing:

```
DB::table('users')->where('votes', '>', 100)->dd();

DB::table('users')->where('votes', '>', 100)->dump();
```

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. More Database Queries


--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!