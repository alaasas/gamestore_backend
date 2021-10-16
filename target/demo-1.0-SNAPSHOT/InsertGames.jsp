<!DOCTYPE html>
<html>
<head>
    <title>Post Games</title>
</head>
<body>
<!-- Give Servlet reference to the form as an instances
GET and POST services can be according to the problem statement-->
<form action="./games/PostGames" method="post">
    <p>Game:</p>
    <input type="text" name="gameName"/>
    <br/>
    <p>Developer:</p>
    <input type="text" name="developer"/>
    <br/>
    <br/>
    <br/>
    <input type="submit"/>
</form>
</body>
</html>