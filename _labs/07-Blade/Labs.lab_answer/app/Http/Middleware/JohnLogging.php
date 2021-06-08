<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class JohnLogging
{
    protected $log = '';
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next)
    {
        $this->log = $this->log . "<hr /> request: " . $request;
        $response =  $next($request);
        $this->log = $this->log . "<hr /> response: " . $response;

        $response->setContent($response->original . "<style>
            .debugger { padding:10px;background-color:black; color:white; font-family:tahoma;}
        </style><div class='debugger'>" . $this->log . "</div>");

        return $response;
    }
}
