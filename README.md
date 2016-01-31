# Running the application

* To package the application run:

        mvn package

* To setup the h2 database run:

        java -jar target/voting-core-1.0-SNAPSHOT.jar db migrate voting.yml

* To start the server run:

        java -jar target/voting-core-1.0-SNAPSHOT.jar server voting.yml

* To post a question into the application:

	curl -H "Content-Type: application/json" -X POST -d '{"text":"What kind of monster are you?"}' http://localhost:8080/questions

	open http://localhost:8080/questions