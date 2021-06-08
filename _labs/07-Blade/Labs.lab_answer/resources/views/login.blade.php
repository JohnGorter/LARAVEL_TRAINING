<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    @env('test')
        <style> body { background-color:yellow } </style>
        <h1>TEST</h1>
    @endenv
    @guest
    {!! '<h1>login</h1>' !!}
    <form method="post">
        Username: <input type="text" name="name" />
        Password: <input type="password" name="password" />
        <input type="submit" value="login" />
    </form>
    @endguest
    @auth
    Welcome user!   
    @endauth
</body>
</html>