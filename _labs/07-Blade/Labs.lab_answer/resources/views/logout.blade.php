@extends('layouts.app')

@section('title')
    <p>Are you sure you want to logout? </p>
@endsection

@section('content')
    <form method="post">
        <input type="submit" value="yes" />
    </form>
@endsection