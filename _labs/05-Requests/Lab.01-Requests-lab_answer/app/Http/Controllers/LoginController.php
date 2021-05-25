<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class LoginController extends Controller
{
    public static $attempts = 0;
    //
    public function login(Request $request) {

        // THIS IS REDICULUS
        if (LoginController::$attempts > 3) return "User account blocked!";

        if ($request->has(["name", "password"])) {
            if ($request->password == "secret") {
                return "Welcome " . $request->name;
            }
        }
        LoginController::$attempts++;
        return "Failed login attempt!" . LoginController::$attempts++;
    }

    public function register(Request $request) {
        if ($request->has(["name", "password", "password2", "photo"])) {
            if ($request->password == $request->password2) {
                return "Registering " . $request->name;
            }
        }
        return "Errors here!!";
    }
}
