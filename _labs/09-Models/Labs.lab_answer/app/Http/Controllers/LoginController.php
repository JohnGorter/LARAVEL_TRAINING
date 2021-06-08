<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class LoginController extends Controller
{
    //
    public function logout(Request $request) {
        $request->session()->invalidate();
        return redirect()->route("home")->withoutCookie('user');
    }

    public function login(Request $request) {
        // THIS IS REDICULUS
        $attempts = $request->session()->get("attempts", 0);
        if ($attempts > 2) return "TOO MANY ATTEMPTS, USER BLOCKED";
        
        if ($request->has(["name", "password"])) {
            if ($request->password == "secret") {
                $request->session()->forget('attempts');
                
                return redirect()->route("home")->cookie('user', $request->name);
            }
        }
        $request->session()->put("attempts", $attempts++);
        return "Failed login attempt!" . $attempts;
    }

    public function status(Request $request) {
        $user = $request->cookie('user');
        if (isset($user)) return response("User " . $user . " is logged in");
        else return response("Anonymous here");
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
