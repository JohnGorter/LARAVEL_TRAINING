@extends('layouts.app')

@section('title')
{!! '<h1>login</h1>' !!}
@endsection

@section('content')
    {{ var_dump($users) }}

    @env('test')
        <style> body { background-color:yellow } </style>
    @endenv
    @guest

    <x-Alert class="warning" :message="''">
        <x-slot name="header">Login</x-slot>
        <h1>hello world<small>from john</small></h1>
        <x-slot name="footer">All rights reserved 2020</x-slot>
    </x-Alert>

    <form method="post">
        Username: <input type="text" name="name" />
        Password: <input type="password" name="password" />
        <input type="submit" value="login" />
    </form>
    @endguest
    @auth
    Welcome user!   
    @endauth
@endsection