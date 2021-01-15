# pasteBin1

Below are some of the different ways I would have approached the design and implementation of such an application to make it scalable and improve the performance of the app :
1: Use a microservice architecture  and  create at least three services:	A:   CreateSnippet Service(Handles all the write operations to create a new snippet)	B:  GetSnippet Service (handles all the get operations e.g retrieve snippets from DB, like a snippet)	C: SnippetDeleter Service:  A service that runs every X mins, scans through the databases, and deletes all expired snippets. (This will free more DB space).
2: Run multiple instances of the services and use a reverse proxy like Nginx to load balance them. I think the least connection load balancing strategy will be better.
3: Integrate Spring Security and do proper client authentication and authorization using JWT Token.
4:  Auto-generate a  unique snippet name using base-64 encoding and a random integer or string so that users can also create a new snippet without providing the name of the snippet.
5: Index the snippet table using the name field.
6: I wanted to add Redis to cache the GetSnippet responses but I decided not to due to the frequent change of the snippet object, i.e expires_at field. Adding a cache would have also introduced a bug to incrementing the expires_at field.
7: I would have stored all the snippets in cloud storage (s3 bucket) and stored the URL in my database table. Maybe storing all snippets less than 1kb directly in my database and storing any snippets greater than 1kb and less than 100Kb on the cloud would be a better option.
8: Restrict the maximum snippet size to 100kb or its equivalent number of characters.
9: In the nearest future, I would have considered replicating my database using the master-slave architecture.
10: Write both unit (Junit 5) and integration tests (Mockito).
11: Ensure at least 50% test coverage using Jacoco.
12: Maybe I would have created my own custom exception.
13: Run a load test on the services/API using Jmeter.
14: Profile my code and use the result to improve the performance of my methods.
	 
TECHNOLOGIES USED / REASONS
1: MYSQL:  Because I am more comfortable using it and it can easily be scaled. With the help of Hibernate, I do not need to worry about writing native queries and I can easily switch to other relationship databases without changing my implementation.
2: Spring Boot:  Because it is very easy to build scalable and secure REST applications and it auto-configures all the necessary configurations.
3: Hibernate-Validator: It’s a very easy and reliable library to validate user input.
