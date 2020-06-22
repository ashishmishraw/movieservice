# movieservice
Movie data listing service 

Contributors: mishra.ashish@icloud.com

Requires: Java 8

-----------------------------------------------------------
== Description ==
-----------------------------------------------------------

Provides two APIs

API1 : http://<url>/movies/user/$userId/search?text=<text>
Provides an array of movies names, found based on the preferences of users, sorted alphabetically

API2:  http://<url>/movies/users
Returns json array of userids and top 3 movies recommended

-----------------------------------------------------------
==Pre-requisites
-----------------------------------------------------------

1. maven 3
2. JDK 1.8

-----------------------------------------------------------
== Installation ==
-----------------------------------------------------------
Unzip the archive at your favorite location

-----------------------------------------------------------
== Starting / Running the app ==
------------------------------------------------------------
You can start the movie-service (once "mvn install" done) by using the following commands:

1. Go to app home folder and type
        mvn exec:java

-----------------------------------------------------------
== Stopping the app/server from command-line ==
-----------------------------------------------------------

Just hit <Enter> or <Return> key at commandline / terminal to stop the app


