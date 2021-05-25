<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class SearchController extends Controller
{
    public function search(Request $request, $params)
    {
        return "searched for " . $params;
    }
}
