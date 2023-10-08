Project Title: Simple Java HTTP Server


Project Description:
This project is a simple HTTP server implemented in Java. It listens for incoming client requests, parses the requests, and attempts to serve requested files from a resources folder. If the requested file doesn't exist, it returns a 404 error. It follows the basic principles of HTTP communication, including handling the default file (index.html) for root requests and sending appropriate HTTP headers in responses.


Why You Used the Technologies You Used;
Java: Java was chosen as the programming language for this project due to its platform independence, ease of socket programming, and robust exception handling capabilities.


Some of the Challenges You Faced
Handling exceptions effectively: Ensuring that the server can gracefully handle exceptions without crashing was a significant challenge. Careful exception handling has been implemented to prevent unexpected crashes and provide meaningful error messages to clients.


Features You Hope to Implement in the Future
While this project serves as a basic HTTP server, there are several potential improvements and features that could be implemented in the future:
-Concurrency: Implementing multithreading to handle multiple client requests simultaneously for better performance.
-Logging: Adding logging functionality to keep track of server activities and errors.
-Security: Implementing security features such as input validation and access control.
-Support for More HTTP Features: Expanding support for various HTTP methods (e.g., POST, PUT) and response codes.
-Dynamic Content: Introducing server-side scripting to generate dynamic content.


How to Install and Run the Project


1. Clone the Repository: Clone this repository to your local machine.
2. Open the Project: Navigate to the project folder and compile the Java Code using javac.
3. Run the Server: Start the server by running the Main class. 
4. Once the server is running, you can access it through a web browser or use curl to send requests. To access the default page (index.html), open your browser and navigate to: http://localhost:8080/index.html
5. To request a specific file, replace fileYouWant with the filename you want to access: http://localhost:8080/fileYouWant
6. Use curl in your terminal to send HTTP requests and see the server's responses:




Credits
This project was created by Melanie Prettyman. Special thanks to Professor Nabil Makarem for guidance and support during the development process.