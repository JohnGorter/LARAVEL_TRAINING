<?php

use Illuminate\Support\Facades\Route;
use Illuminate\Http\Request;

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

// demo 1 bsic routing
Route::get('/greeting', function () {
    return 'Hello World';
});

Route::get('/request', function(Request $request) {
    dd("request", $request);
});

Route::get('/', function () {
    return view('welcome');
});


