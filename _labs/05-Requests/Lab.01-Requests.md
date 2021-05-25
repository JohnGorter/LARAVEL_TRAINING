# Lab Requests
## Lab time: 45 minutes

--
## Excercise 1: Make a login processing controller
Make a Controller (ad a route to it) that accepts a POST with the data
"username", "password", which tests the parameter "password" to be "secret".
When true, it returns true, otherwise false.

--
## Excercise 2: Make a registration handler in your controller
The user is able to register with his "username", "password (2x)" and a profile photo. Check all data for validity and presence, if all succeeds, return "Congrats" otherwise return "Failed registration".


--
## Extra exercise: Login attempts
If the user for this session fails to login within 3 valid sequential requests, then return "this user has been locked out!"
