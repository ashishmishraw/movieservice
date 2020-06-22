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
== Service Design ==
-----------------------------------------------------------

Movie service is designed as a lightweight REST endpoint hosted in Jersey (JAX-RS) container using Grizzly HTTP Server

Reason for choosing Grizzly:
http://menelic.com/2016/01/06/java-rest-api-benchmark-tomcat-vs-jetty-vs-grizzly-vs-undertow/

Main class starts the Grizzly server and hosts the REST APIs

-----------------------------------------------------------
== Searching in-memory ==
-----------------------------------------------------------

Movie_metadata.csv is a 1.5 MB file which can be fully loaded in memory, however this data could grow huge

As a scalable solution the data should be indexed and sharded in a distributed storage or memory grid for better performance, however in scope of current application it is being loaded in-memory

-----------------------------------------------------------
== Reduced Snapshot of data-set ==
-----------------------------------------------------------

In scope of current problem, movies are searched based on following criteria only:
Actor names, Director name, Title, Language and imdb_score (top movies)

In this context, only above properties are loaded in-memory from CSV file

User preferences JSON data is also loaded in a map in memory

Loading CSV file
CSV data is loaded in-memory using OpenCSV library. Only above mentioned columns are mapped to <Movie.java> bean and parsed

-----------------------------------------------------------
== Searching and Filtering ==
-----------------------------------------------------------

Data is searched by creating filter chains and applying it lazily once over the data set for processing using java 8 APIs

Filtering Top Movies
The definition of “top” movies varies (based on facebook likes, actor popularity, director rating, critic reviews and IMDB ratings) 

In this application, IMDB_score is taken as a single criteria for matching & sorting top rated movies 

-----------------------------------------------------------
== Deployment ==
-----------------------------------------------------------

Typical microservice deployment is hosted over cloud using EC2 or Heroku instances. These instances are designed to provide high-performance response time and have a resilient/ reactive architecture using various design patterns like API Gateway, load balancers, distributed cache and circuit breakers

In scope of current application, we can only deploy the app over single instance since could not scale beyond it (being free account), hence Heroku instance is chosen for deployment over single web dyno


-----------------------------------------------------------
==Pre-requisites
-----------------------------------------------------------

1. maven 3
2. JDK 1.8
3. opencsv.sourceforge.net

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


