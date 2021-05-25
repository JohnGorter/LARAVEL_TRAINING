<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class UserController extends Controller
{
    //
    public function get($id) {
        return "got user by id: " . $id;
    }

    public function getByName($name) {
        return "got user by name: " . $name;
    }
}
