<?php

use Illuminate\Support\Facades\Route;
use Illuminate\Http\Request;
use App\Http\Controllers\GreetingController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\SearchController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\StudentController;
use App\Http\Controllers\LoginController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', [HomeController::class, "index"])->name("home");
Route::get('/user/{id}', [UserController::class, "get"])->whereNumber("id")->name("userid");
Route::get('/user/{name}', [UserController::class, "getByName"])->whereAlpha("name")->name("username");
Route::any('/greeting', GreetingController::class)->middleware("john");
Route::get('/search/{search}', [SearchController::class, "search"])->where('search', '.*');

Route::get('/login', function() { return view("login"); });
Route::post('/login', [LoginController::class, "login"]);
Route::get('/logout', function() { return view("logout"); });
Route::post('/logout', [LoginController::class, "logout"]);
Route::post('/register', [LoginController::class, "register"]);
Route::get('/status', [ LoginController::class, "status"]);

Route::resource('student', StudentController::class);
// function handle(Request $request, $param)
// {
//     if ($request->route()->named('userid')) {
//         //
//         return "user id request (" . $param . ")";
//     }
//     return "user name request (" . $param . ")";
// }

// Route::get('/user/{id}', function (Request $request, $id) {
//     return handle($request, $id);
// })->whereNumber("id")->name("userid");

// Route::get('/user/{name}', function (Request $request, $name) {
//     return handle($request, $name);
// })->whereAlpha("name")->name("username");

// Route::any('/greeting', function () {
//     return 'Hello World (any)';
// })->middleware("john");

// Route::get('/greeting', function () {
//     return 'Hello World (get)';
// })->middleware("john");

Route::fallback(function() { return 'Hm, why did you land here somehow?'; });
