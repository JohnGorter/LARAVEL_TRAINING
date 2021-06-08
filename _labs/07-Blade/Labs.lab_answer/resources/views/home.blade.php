 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <?php 
        if(isset($user)) {
            echo 'Hello ' . $user . ' <a href="/logout">logout</a>';
        } 
        else {
            echo '<a href="/login">Login here</a>';
        }
    ?>
</body>
</html>
