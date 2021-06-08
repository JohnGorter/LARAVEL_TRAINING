@extends('layouts.app')

@section('title')
@endsection

@section('content')
    <?php 
        if(isset($user)) {
            echo 'Hello ' . $user . ' <a href="/logout">logout</a>';
        } 
        else {
            echo '<a href="/login">Login here</a>';
        }
    ?>
@endsection
