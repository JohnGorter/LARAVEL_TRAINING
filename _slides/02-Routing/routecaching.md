# Route Caching

--
## Route Caching
When deploying your application to production, take advantage of Laravel's route cache
- decreases the amount of time it takes to register all of your application's routes

```
php artisan route:cache
```

After running this command, your cached routes file will be loaded on every request

--
## Route Caching
Only run the route:cache command during your project's deployment.

You may use the route:clear command to clear the route cache:

```
php artisan route:clear
```