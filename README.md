Server instructions:

1. Open the project in IntelliJ
2. Build > Build Project
3. Services (Alt + 8) > Add new configuration
4. Select Tomcat > Local
5. For http port, enter 8081, not 8080. For JMX port change to 1080.
6. Click on ok
7. Right click on Tomcat from services and select artifacts
8. Add both demo_war and demo_war_exploded
9. Click on ok
10. Run Tomcat

Visit this url to check if it works: http://localhost:8081/demo_war_exploded/


SQL instructions:

1. open game.sql in MySQL Workbench. 
2. run the script.
3. open DatabaseConnection.java in IntelliJ.
4. update database connection details.
