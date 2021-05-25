<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class GreetingController extends Controller
{
    
    public function __invoke(Request $request)
    {
        return 'Hello World (inside controller)'; 
    }
}
