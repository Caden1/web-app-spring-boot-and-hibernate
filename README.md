This repository contains my practice and notes from section 7 of the Udemy course [Master Spring Boot 3 & Spring Framework 6 with Java](https://www.udemy.com/course/spring-boot-and-spring-framework-tutorial-for-beginners), created by Ranga Karanam, the founder of in28minutes.

# Section 7: Build Java Web Application with Spring Framework, Spring Boot and Hibernate

Step by step guide and troubleshooting for section 7 can be found here

Introduction to Building Web App with Spring Boot:
* Can be complex:
    * Web App concepts:
        * Browser, HTML, CSS, Request, Response, Form, Session, Authentication
    * Spring MVC:
        * Dispatcher Servlet, View Resolvers, Model, View, Controller, Validations, ..
    * Spring Boot:
        * Starters, Auto Configurations, ..
    * Frameworks/Tools:
        * JSP, JSTL, JPA, Bootstrap, Spring Security, MySQL, H2
* Goal: Build Todo Management Web App with a Modern Spring Boot approach
    * AND explore all concepts in a HANDS-ON way

Creating Spring Boot Web Application with Spring Initializr:
* Use Spring Initializr to start the project
    * Maven
    * Java
    * Spring Boot Version: 3.2.4
    * Group: com.learn.springboot
    * Artifact: myfirstwebapp
    * Packaging: Jar
    * Java Version: 17
    * Dependencies:
        * Spring Web
        * Spring Boot DevTools

Quick overview of Spring Boot Project:
* Important Files:
    * Under “src/main/java” there’s a file with the main method and code that runs the application
    * Under “src/main/resources” there’s the application.properties file:
        * Here, you can configure a lot of details about your application
    * The pom.xml file is where you define all the dependencies related to the application

First Spring MVC Controller, @ResponseBody, @Controller:
* @RequestMapping(“”) annotation:
    * Provides mapping URL paths to methods
    * When the URL path contained in the quotes is visited, the method below the annotation is executed
    * Example, when “localhost:8080/say-hello” the “sayHello()” method is executed:
@RequestMapping("say-hello")
public String sayHello() {
	return "Hello! What are you learning today?";
} 
* Add the “@ResponseBody” annotation to the method that’s returning the data so Spring knows to return the data as-is to the browser
* Need the “@Controller” annotation above the class declaration so Spring knows this class handles web requests
* Basic example of bringing to a web page:
@Controller
public class SayHelloController {
	@RequestMapping("say-hello")
	@ResponseBody
	public String sayHello() {
		return "Hello! What are you learning today?";
	}
}

* For the above, launch the application, go to “localhost:8080/say-hello”, the web page will display “Hello! What are you learning today?”
* Side Note: Make sure your controller is created in a sub-package of the package where your Class that contains the code to run the application is

Enhancing Spring MVC Controller to provide HTML response:
* It’s bad practice to hard code HTML like this, but for this example we are doing it
@Controller
public class SayHelloController {
	@RequestMapping("say-hello")
	@ResponseBody
	public String sayHello() {
		return "Hello! What are you learning today?";
	}
	@RequestMapping("say-hello-html")
	@ResponseBody
	public String sayHelloHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>My first HTML Page</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("My First html page with body");
		sb.append("</body>");
		sb.append("</html>");	
		return sb.toString();
	}
}

* The above shows the new method “sayHelloHtml()” mapped to the path “say-hello-html”. When “http://localhost:8080/say-hello-html” is visited it displays “My First html page with body” as HTML, and the tab is titled “My first HTML Page”

Resources for Next Step:
* You need to be careful with adding tomcat-embed-jasper dependency. Configuration is different for different IDEs.
* Use the following dependency based on the IDE:
* Eclipse:
<dependency>
        <groupId>org.apache.tomcat.embed</groupId>
	<artifactId>tomcat-embed-jasper</artifactId>
	<scope>provided</scope>
</dependency>

* IntelliJ:
<dependency>
	<groupId>org.apache.tomcat.embed</groupId>
	<artifactId>tomcat-embed-jasper</artifactId>
</dependency>

* NOTE: <scope>provided</scope> is NOT needed for INTELLIJ

Redirect to a JSP using Spring Boot - Controller, @ResponseBody & View:
* Java Server Pages (JSP) is a popular Java View technology
* We want the same method we used before “sayHelloHtml()” to redirect to our “sayHello.jsp” file
* The “sayHello.jsp” file will contain our html
* All JSP files should be created in this folder (Spring Boot created this folder):
    * /src/main/resources/META-INF/resources/WEB-INF/jsp/
* In this case:
    * /src/main/resources/META-INF/resources/WEB-INF/jsp/sayHello.jsp
* The “WEB-INF” folder is where we can create all our views; in our case we’re using JSP so we also create a “jsp” folder
* The “sayHello.jsp” file is called a view
* In the “application.properties” file you define the prefix and suffix for the views
    * Basically the prefix is where to look for the views and the suffix is the file type of the views
    * Spring already knows up to “/src/main/resources/META-INF/resources”, so all we need for the prefix in “application.properties” is “/WEB-INF/jsp/”
* application.properties looks like this:
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

* The “sayHello.jsp” file looks like:
<html>
	<head>
		<title>My first HTML Page</title>
	</head>
	<body>
		My first html page with body - using JSP!
	</body>
</html>

* In order to run JSP in Tomcat, we also need to add the following dependency to our POM file (it’s already provided by Tomcat, which is why we have a scope of “provided”):
<dependency>
	<groupId>org.apache.tomcat.embed</groupId>
	<artifactId>tomcat-embed-jasper</artifactId>
	<scope>provided</scope>
</dependency>

* In our controller we need to remove the “@ResponseBody” annotation so the method does not return to the web page immediately
* We want it to redirect to “sayHello.jsp” by making the method return “sayHello”, the “application.properties” tells Spring what to do based on the prefix and suffix
* This is the method in our controller for redirecting to the JSP file:
@RequestMapping("say-hello-jsp")
public String sayHelloJsp() {
	return "sayHello";
}

* Now visiting “http://localhost:8080/say-hello-jsp” will return the html in the .jsp file

Exercise - Creating LoginController and login view:
* Create “LoginController” in “myfirstwebapp.login” package that redirects to “login.jsp”
* Go to “http://localhost:8080/login”
* LoginController:
@Controller
public class LoginController {
	@RequestMapping("login")
	public String goToLoginPage() {
		return "login";
	}
}

* login.jsp:
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		Log into your account
	</body>
</html>

Quick Overview - How does web work - Request and Response:
* To see details of the web request:
    * Go to a web page you built. Example: “http://localhost:8080/say-hello-jsp”
    * Right-click -> inspect
    * Network tab -> Doc tab -> refresh the web page
    * In the “Name” window on the left side click the path. Example: “say-hello-jsp”
    * This gives you access to info such as the Headers (has request info), Response, Cookies, etc
* How does Web work?:
    * A: Browser sends a request
        * HttpRequest
    * B: Server handles the request
        * Your Spring Boot Web Application
    * C: Server returns the response 
        * HttpResponse

Capturing QueryParams using RequestParam and First Look at Model:
* Query parameters are a way to pass information in a request URL
    * Example: “http://localhost:8080/login?name=Caden”
    * Everything after the ‘?’ is a query parameter in the format “key=value” 
* To use the query parameter our LoginController’s “goToLoginPage()” method needs to be setup to accept it
* @RequestParam annotation:
    * Add this to the method’s parameters (inside the parentheses) with the type and name of the query parameter. Example:
@Controller
public class LoginController {
	@RequestMapping("login")
	public String goToLoginPage(@RequestParam String name) {
		System.out.println("Query Param \"name\" is " + name); // DON'T USE PRINT STATEMENT IN PROD CODE
		return "login";
	}
}

* Then go to “http://localhost:8080/login?name=Caden” and it logs “Query Param "name" is Caden” to the console
* How do we pass the query parameter into our JSP?
    * We can do this using a Model
* Anytime we want to pass something from the Controller to JSP (view) we use a Model
    * One way is using a “ModelMap”
        * “ModelMap” is an implementation of java.util.Map so it holds key-value pairs
* In the controller’s method you’re passing the query parameter to, you add the value to the ModelMap. Example:
@Controller
public class LoginController {
	@RequestMapping("login")
	public String goToLoginPage(@RequestParam String name, ModelMap model) {
		model.put("name", name);
		return "login";
	}
}

* You can then retrieve this value in the JSP by using Expression Language (EL)
    * EL has the format of ${key}
    * Example:
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		Welcome ${name}!
		Please log into your account
	</body>
</html>

Quick Overview - Importance of Logging with Spring Boot:
* When debugging problems in production, most of the time you only have logs. Be aware of what’s being logged
* In Development environment, “debug” level is often used:
logging.level.org.springframework=debug

* “debug” level logs a lot and causes performance issues
* The Production environment will often have “info” level logging:
logging.level.org.springframework=info

* “info” level has a lot less logs than “debug” level
* Remember that the package in the “application.properties” file determines which files log at the specified level
    * In the case of “logging.level.org.springframework”, this means everything in “org.springframework” (I think this is everything using Spring, which I think is our entire application)
* I can get more specific to my application by doing something like:
logging.level.com.learn.springboot.myfirstwebapp=debug

* “com.learn.springboot.myfirstwebapp” is the top level package for my application

* Make use of logging within your Java code by using “slf4j”
@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping("login")
	public String goToLoginPage(@RequestParam String name, ModelMap model) {
		model.put("name", name);
		logger.debug("Request param is {}", name);
		return "login";
	}
}

* The above logger works as long as I have this in my “application.properties”:
logging.level.com.learn.springboot.myfirstwebapp=debug

* Changing to “info” level will make the “logger.debug("Request param is {}", name);” log not print:
logging.level.com.learn.springboot.myfirstwebapp=info

* To log at “info” level:
logger.info("Log this at INFO level");

* Each level will print it’s own level plus all levels below it
* Levels in order of most logs to least amount of logs:
    * TRACE
    * DEBUG
    * INFO
    * WARN
    * ERROR

* Knowing what to log is an essential skill to be a great programmer
* Spring Boot makes logging easy with the following starter project
    * spring-boot-starter-logging
* “spring-boot-starter-logging” is a transitive dependency for “spring-boot-starter-web”
* You can go to the “pom.xml” file and click the “Dependency Hierarchy” tab at the bottom to look at and search for dependencies
    * Searching for “logging” shows spring-boot-starter-logging under spring-boot-starter-web
* Default Logging Framework in Spring Boot:
    * Logback with SLF4j

Understanding DispatcherServlet, Model 1, Model 2 and Front Controller:
* How web applications used to be developed in Java:
    * Model 1 Architecture:
        * All the code (view logic, flow logic, querying databases) was done in Views (JSPs, …)
            * No controllers
    * Model 2 Architecture:
        * Separation of concerns
            * Model: Data to generate the view
            * View: Show information to user
            * Controller: Controls the flow
    * Model 2 Architecture with Front Controller:
        * All requests go to one controller called the Front Controller
            * This makes things like security, authentication, etc a lot easier
        * Front controller controls flow to controller’s and view’s
        * Common features can be implemented in the Front Controller
* Spring MVC Front Controller - Dispatcher Servlet
    * Example of a Servlet Engine is Tomcat
    * Dispatcher Servlet is a Spring MVC implementation of the Model 2 Front Controller pattern
    * Flow of Execution:
        * A: Receives HTTP Request
        * B: Processes HTTP Request
            * B1: Identifies correct Controller method
            * B2: Executes Controller method
                * Returns Model and View Name
            * B3: Identifies correct View
                * Using ViewResolver
            * B4: Executes View
        * C: Returns HTTP Response

Creating a Login Form:
* Create a simple login page
* Validate User ID and Password
* Create a simple welcome page
* Usually we would use Spring Security for validation, but for now we’re not using it
* To allow user input we use the “form” and “input” HTML tags
* Notice the password is of type password
* Example:
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		Welcome!
		Please log into your account
		<form action="">
			Name: <input type="text" name="name">
			Password: <input type="password" name="password">
			<input type="submit">
		</form>
	</body>
</html>

* When the user presses the “submit” button, it adds the text from the text box as a query parameter in the URL
* Before user input:
    * http://localhost:8080/login
* After user enters “Caden” and “hello” then presses enter:
    * http://localhost:8080/login?name=Caden&password=hello
* This is not secure because the internet uses routers and the routers can see the URL and the username and password are in plain text in the URL
* We’re using a GET request. GET requests can only pass information through query parameters. We don’t want to do this, so we can change it to a POST
    * We can do this by adding the attribute “method="post"” in the “form” tag. Example:
…
		<form method="post">
			Name: <input type="text" name="name">
			Password: <input type="password" name="password">
			<input type="submit">
		</form>
…

* Now, when the user enters a username and password it’s not passed as query parameters, but instead it’s passed in a payload as Form Data
    * You can see this by right clicking the login page in a web browser -> inspect -> Network tab -> Doc tab -> redo the request -> click the page on the left under “Name” -> Payload tab
        * I think this data is sent in the form of:
            * name=Caden&password=hello

Displaying Login Credentials in a JSP using Model:
* Once the user enters credentials and presses submit (this is a POST request), we want to direct them to the welcome page
* Create a welcome.jsp file:
<html>
	<head>
		<title>Welcome Page</title>
	</head>
	<body>
		<div>Everyone is welcome</div>
	</body>
</html>

* Currently the “goToLoginPage” method in the “LoginController” is handling both the GET request when a user visits “http://localhost:8080/login” and the POST request when the user enters credentials and presses Submit
    * To make this method only handle the GET request we have to add some details to the @RequestMapping annotation
    * Used to be:
@Controller
public class LoginController {
	@RequestMapping("login")
	public String goToLoginPage() {	
		return "login";
	}
}

    * Now it’s:
@Controller
public class LoginController {
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String goToLoginPage() {	
		return "login";
	}
}

    * We can add another method to handle directing to the welcome page:
@RequestMapping(value="login", method=RequestMethod.POST)
public String goToWelcomePage() {		
	return "welcome";
}

    * The above method is saying, when the login path is used with a POST request, direct to the welcome.jsp file
* Next, we want to capture the username and password from the request. We can obtain these from the Form Data using the @RequestParam annotation
* We also want to pass the Form Data to the welcome page. We do this using ModelMap
* New goToWelcomePage method:
@RequestMapping(value="login", method=RequestMethod.POST)
public String goToWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model) {
	model.put("name", name);
	model.put("password", password);		
	return "welcome";
}

* For testing purposes, displaying the name and password on the welcome page (welcome.jsp):
<html>
	<head>
		<title>Welcome Page</title>
	</head>
	<body>
		<div>Everyone is welcome</div>
		<div>Name: ${name}</div>
		<div>Password: ${password}</div>
	</body>
</html>

* This is following the MVC pattern by using the login.jsp view -> LoginController controller -> ModelMap class to handle data -> welcome.jsp view

Add hard coded validation of userid and password:
* Still not using Spring security, but we’ll do some authentication logic
* Check for a specific username and password. If these are entered go to welcome page, if not, stay on login page
    * Username: in28minutes
    * Password: dummy
* Created class called “AuthenticationService” in same package as the LoginController. We use the “@Service” annotation to make it a Spring Bean because our LoginController will need to use it:
@Service
public class AuthenticationService {
	public boolean authenticate(String username, String password) {
		boolean isValidUserName = username.equalsIgnoreCase("in28minutes");
		boolean isValidPassword = password.equalsIgnoreCase("dummy");	
		return isValidUserName && isValidPassword;
	}	
}

* In the above, we use boolean logic to only return true if both isValidUserName and isValidPassword return true
* Now in the LoginController, we create an instance of AuthenticationService, generate a constructor so Spring can use injection, and use the instance to perform authentication:
@Controller
public class LoginController {
	
	private AuthenticationService authenticationService;
	
	public LoginController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@RequestMapping(value="login", method=RequestMethod.GET)
	public String goToLoginPage() {	
		return "login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String goToWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model) {
		if (authenticationService.authenticate(name, password)) {
			model.put("name", name);
			model.put("password", password);
			return "welcome";
		}	
		model.put("errorMessage", "Invalid Credentials");
		return "login";
	}
}

* We also added an error message when the credentials are invalid. To show the error message on the login page we use the “pre” tag
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		Please log into your account
		<pre>${errorMessage}</pre>
		<form method="post">
			Name: <input type="text" name="name">
			Password: <input type="password" name="password">
			<input type="submit">
		</form>
	</body>
</html>

* Pre is for showing pre-formatted text
* Remember to remove adding the password to the model in the LoginController and remove displaying the password in welcome.jsp

Getting started with Todo Features - Creating Todo and TodoService:
* Going to code a basic ToDo list and format the webpages
* In this exercise we’re using a List to store our data; normally we would want to use a database
* You initialize static variables in a static block

Creating first version of List Todos Page:
* Creating a TodoController and redirect to a listTodos.jsp view

Understanding Session vs Model vs Request - @SessionAttributes:
* Request:
    * All requests from browser are handled by our web application deployed on a server
    * Incoming data from a request is only available for the request they belong to
    * Once the response is sent back, the request attributes will be removed from memory
    * The data from one request won’t have its data transferred to subsequent requests
    * Recommended for most use cases
* Model:
    * Data in a Model is only available for the scope of that request
    * By default, everything stored in Model is also Request scope
    * Example is when we add to the ModelMap in the LoginController:
        * model.put("name", name);
* Session:
    * When you want data to live longer (use across different requests), use session
    * Details stored across multiple requests
    * Be careful about what you store in session (Takes additional memory as all details are stored on server)
    * To use session:
        * In the controller that adds the data to the Model, add the “@SessionAttributes()” annotation above the class declaration. The annotation needs the name of the attribute ion it’s parenthesis with double quotes around it
        * Next, in the controllers you want to still have access to that data, add the “@SessionAttributes()” annotation above the class declaration
        * The session attribute will be available in both of the views
        * Example:
            * The LoginController adds “name” to the Model and we want to access the “name” attribute in the TodoController and listTodos.jsp:
@Controller
@SessionAttributes("name")
public class LoginController {
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String goToWelcomePage(ModelMap model) {
		model.put("name", name);
		return "welcome";
	}
}

@Controller
@SessionAttributes("name")
public class TodoController {
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		model.addAttribute("todos", todos);
		return "listTodos";
	}
}

<html>
	<head>
		<title>List Todos Page</title>
	</head>
	<body>
		<div>Hi ${name}. Here is your todo list: ${todos}</div>
	</body>
</html>

Course Update: Spring Boot 3.2.x JSTL Change:
Change 1:
* Use jakarta.servlet.jsp.jstl instead of glassfish-jstl
* For Spring Boot 3.2.x and greater:
<dependency>
	<groupId>org.glassfish.web</groupId>
	<artifactId>jakarta.servlet.jsp.jstl</artifactId>
</dependency>

* jakarta.servlet.jsp.jstl replaces glassfish-jstl (recommended for Spring Boot <= 3.1.x):
<dependency>
	<groupId>org.eclipse.jetty</groupId>
	<artifactId>glassfish-jstl</artifactId>
</dependency>
————————————
Change 2:
* Use jakarta.tags.core instead of http://java.sun.com/jsp/jstl/core as taglib
* Spring Boot 3.2.X and greater:
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

* Spring Boot 3.1.X and lower:
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
————————————
Change 3:
* Run the “mvn clean install” command to update libraries


Adding JSTL to Spring Boot Project and Showing Todos in a Table:
* We want the todo items to be added as items to a table, regular html won’t be sufficient. We can use JSTL instead
* jakarta.tags.core library <- for Spring Boot 3.2.X and greater
* JSTL core library <- only for Spring Boot 3.1.X and lower
* These allow for writing basic logic in JSP (views) such as if-statements and foreach loops
* Since I’m using at least Spring Boot 3.2.X, I need to add the following to the top of my listTodos.jsp:
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

* The above import is saying:
    * We’re using a tag library
    * The name we’re using in our code is ‘c’
    * And the library is the one from jakarta.tags.core
* Now we can use tags such as:
<c:forEach></c:forEach>

* Example:
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
	<head>
		<title>List Todos Page</title>
	</head>
	<body>
		<div>Hi ${name}</div>
		<hr>
		<h1>Your todo list:</h1>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>Description</th>
					<th>Target Date</th>
					<th>Is Done?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.id}</td>
						<td>${todo.description}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.done}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>


Adding Bootstrap CSS framework to Spring Boot Project using webjars:
* Previously, to use CSS, you would have to download CSS and add it to the “src/main/resources” -> “static” folder
* Now you can use webjars 
* We do this by adding it as a dependency in the POM
* We need to specify a version because Spring does not automatically assign it a version
* Add these dependencies:
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>bootstrap</artifactId>
	<version>5.1.3</version>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>jquery</artifactId>
	<version>3.6.0</version>
</dependency>

* After adding these dependencies, find bootstrap in the Maven Dependencies in Package Explorer and expand it
    * After expanding it, navigate to: “/META-INF/resources/webjars/bootstrap/5.1.3/css/bootstrap.min.css”, right click “bootstrap.min.css”, click Copy Qualified Name
    * You also want to Copy Qualified Name of the js file: “/META-INF/resources/webjars/bootstrap/5.1.3/js/bootstrap.min.js”
* Find jquery in the Maven Dependencies in Package Explorer and expand it
    * Copy Qualified Name of “/META-INF/resources/webjars/jquery/3.6.0/jquery.min.js”
* We want to add these into our JSP files
    * When we load a web page, the CSS file should be loaded at the start
    * We can load it by using the “link” tag
    * We can remove the “/META-INF/resources/” part of the path. Example:
<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">

* We also want to add the JavaScript files by using the “script” tag
    * Always add these right before the closing “body” tag. Examples:
<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>

* When you refresh your browser, you’ll notice immediately that bootstrap is doing some formatting
* You can see that the browser has downloaded these webjars by going to the webpage -> right click -> inspect -> Network tab -> Refresh the Webpage -> All tab
    * The All tab shows the downloaded CSS and JavaScript files
* Web Jars, as in, Jars downloaded from the web

Formatting JSP pages with Bootstrap CSS framework:
* One of the Bootstrap recommendations is to put all the contents of the body inside a “div” tag with the attribute of class=“container”
    * Adding Bootstrap’s “container” will center everything nicely
* Bootstrap provides nice features for tables as well
    * Adding class=“table” to the “table” tag adds a nicely formatted table
* New “listTodos.jsp”:
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
		<title>List Todos Page</title>
	</head>
	<body>
		<div class="container">
			<h1>Your todo list:</h1>
			<table class="table">
				<thead>
					<tr>
						<th>id</th>
						<th>Description</th>
						<th>Target Date</th>
						<th>Is Done?</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${todos}" var="todo">
						<tr>
							<td>${todo.id}</td>
							<td>${todo.description}</td>
							<td>${todo.targetDate}</td>
							<td>${todo.done}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
		<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
	</body>
</html>

Lets Add a New Todo - Create a new View:
* Adding functionality to add a Todo item to the list
* Added button that executes a method in the controller. Achieved with code:
    * <a href="add-todo" class="btn btn-success">Add Todo</a>
* Controller method executed:
@RequestMapping(value="add-todo", method=RequestMethod.POST)
public String addNewTodo() {	
	return "redirect:list-todos";
}

* The "redirect:list-todos" will execute this method that we already created:
@RequestMapping("list-todos")
public String listAllTodos(ModelMap model) {
	List<Todo> todos = todoService.findByUsername("in28minutes");
	model.addAttribute("todos", todos);		
	return "listTodos";
}

* In “todo.jsp”, I don’t understand how the form executes the “addNewTodo” method in the ToDoController
<form method="post">
	Description: <input type="text" name="description">
	<input type="submit" class="btn btn-success">
</form>


Enhancing TodoService to add the todo:
* We’re going to actually add the todo based on user input
* All logic should be in our TodoService class


Adding Validations using Spring Boot Starter Validation:
* Validations in HTML and JavaScript can easily be overridden by hackers. This is why we always want server side validations (in our Java code)
* Validations with Spring Boot:
    * 1. Spring Boot Starter Validation
        * Import this starter project
    * 2. Command Bean (Form Backing Object)
        * 2-way binding (todo.jsp & TodoController.java)
* Added to POM:
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency>

* Command Bean:
    * Currently when the user enters a Description and clicks the Submit button, it executes the “addNewTodo” method. In order to get the data entered, we use @RequestParam as a parameter for the method. Example:
@RequestMapping(value="add-todo", method=RequestMethod.POST)
public String addNewTodo(@RequestParam String description, ModelMap model)

    * If we have 10 request parameters, we will need 10 @RequestParam’s in the method’s parameters. This gets hard to manage
    * Instead, we can bind to the fields that already exist (in this case, our Todo Java Class)
        * Instead of using @RequestParam String description, we bind directly to the “description” field in the Todo class
    * The bean created from the Todo class is called a Command Bean or Form Backing Object
    * We do this by removing the @RequestParam and adding the parameter “Todo todo”; example:

@RequestMapping(value="add-todo", method=RequestMethod.POST)
public String addNewTodo(ModelMap model, Todo todo) {
	String username = (String) model.get("name");
	todoService.addTodo(username, todo.getDescription(), LocalDate.now().plusYears(1), false);		
	return "redirect:list-todos";
}

    * We can now use “todo.getDescription()” when we need the description
    * In order to get the Form Backing Object in JSP we need to use Spring Form Tag Libraries
        * To use:
            * Import:
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            * Since the prefix is “form”, add “form:” in front of the form tags you’re using
                * Example:

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
		<title>Add Todo Page</title>
	</head>
	<body>
		<div class="container">
			<h1>Enter Todo Details</h1>
			<form:form method="post">
				Description: <input type="text" name="description" required="required">
				<input type="submit" class="btn btn-success">
			</form:form>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
		<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
	</body>
</html>

    * Now, in order to map the form from our JSP to the Todo in our Controller, we need to add the attribute “modelAttribute” to our JSP form tag like this:

<form:form method="post" modelAttribute="todo">
	Description: <input type="text" name="description" required="required">
	<input type="submit" class="btn btn-success">
</form:form>

    * Now, modelAttribute="todo" maps to the “Todo todo” in this:

public String addNewTodo(ModelMap model, Todo todo)

    * To map the description entered to the description in the Todo java class:
        * Add “form:” in the “input” tag, and replace the “name” attribute with the “path” attribute
            * Example:
Description: <form:input type="text" path="description" required="required"/>

    * Added some place-holder code

Using Command Beans to implement New Todo Page Validations:
* Validations with Spring Boot, 2 More Steps:
* 3. Add Validations to Bean
    * Todo.java
    * Example: For description field the maximum number of characters is 10
* 4. Display Validation Errors in the View
    * todo.jsp
* Add Validations to Bean:
    * In the Class that has all the fields, you can add validations with Annotations using the “jakarta.validation.constraints…” import. Example in Todo Java Class:
@Size(min=10, message="Enter at least 10 characters")
private String description;
    * You also need to add the “@Valid” annotation to the Controller method using the Bean. Example (in TodoController):
public String addNewTodo(ModelMap model, @Valid Todo todo)

* Display Validation Errors in the View:
    * When the user uses less than 10 characters it throws a “MethodArgumentNotValidException”. Instead of this we simply want an error message displayed on the webpage
        * We can do this by using the “BindingResult” class, adding it to the Controller methods parameters, and using it. Example:
@RequestMapping(value="add-todo", method=RequestMethod.POST)
public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
	if (result.hasErrors()) {
		return "todo";
	}
	String username = (String) model.get("name");
	todoService.addTodo(username, todo.getDescription(), LocalDate.now().plusYears(1), false);		
	return "redirect:list-todos";
}

        * We need to add the error message in the JSP file with the “form:errors” tag. We can also make it look better by adding css; since we’re using Spring Tags now (e.g. form:) we need to use the attribute cssClass and “text-warning” (“text-warning” is from Bootstrap). Example:

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
		<title>Add Todo Page</title>
	</head>
	<body>
		<div class="container">
			<h1>Enter Todo Details</h1>
			<form:form method="post" modelAttribute="todo">
				Description: <form:input type="text" path="description" required="required"/>
				<form:input type="hidden" path="id"/> <!-- Place Holder -->
				<form:input type="hidden" path="done"/> <!-- Place Holder -->
				<input type="submit" class="btn btn-success"/>
				<form:errors path="description" cssClass="text-warning"/>
			</form:form>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
		<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
	</body>
</html>


Implementing Delete Todo Feature - New View:
* Added delete button next to each todo list item with the id. Also added a query parameter as part of the “href” attribute; this way the “id” is passed to the Controller method. Example:
<td> <a href="delete-todo?id=${todo.id}" class="btn btn-warning">DELETE ${todo.id}</a> </td>

* We need a new method in our Controller that uses the “id”. Example:
@RequestMapping("delete-todo")
public String deleteTodo(@RequestParam int id) {	
	todoService.deleteById(id);	
	return "redirect:list-todos";
}

* Add delete logic in TodoService Class
* We’re defining a predicate which is part of functional programming in Java. Example:
public void deleteById(int id) {
	Predicate<? super Todo> predicate = todo -> todo.getId() == id;
	todos.removeIf(predicate);
}


Implementing Update Todo - 1 - Show Update Todo Page:
* Added functionality for updating a todo item

Implementing Update Todo - 1 - Save changes to Todo:
* Added functionality to save the updated todo item


<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>bootstrap-datepicker</artifactId>
	<version>1.9.0</version>
</dependency>

<link href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet" >
		 
<fieldset class="mb-3">
	<form:label path="description">Description</form:label>
	<form:input type="text" path="description" required="required"/>
	<form:errors path="description" cssClass="text-warning"/>
</fieldset>
<fieldset class="mb-3">
	<form:label path="targetDate">Target Date</form:label>
	<form:input type="text" path="targetDate" required="required"/>
	<form:errors path="targetDate" cssClass="text-warning"/>
</fieldset>
		
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
$('#targetDate').datepicker({
format: 'yyyy-mm-dd'
});
</script>

 Adding Target Date Field to Todo Page:
* The “fieldset” tag defines a form control group
* Added “form:label” for Description
* Added functionality so the user can enter a target date
* To define a standard for how dates are displayed in our application:
    * Go to “application.properties”
    * Add the following:
        * spring.mvc.format.date=yyyy-MM-dd
            * Lowercase “mm” is for minutes, so we use uppercase “MM” for month
* To allow the user to select a date from a popup, use Bootstrap Date Picker
    * Add dependency to POM:
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>bootstrap-datepicker</artifactId>
	<version>1.9.0</version>
</dependency>

    * Find it in the Maven Dependencies in Package Explorer
    * Copy the path of the “min” of the css and js (not sure why we’re using standalone for css)
/META-INF/resources/webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css
/META-INF/resources/webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js

    * The “css” is for styling and the “js” is for functionality

    * Add their paths to their proper tags starting from “webjars” onward in the “todo.jsp” file
<link href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet" >
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

    * We also need the javascript. Add this just before the closing “body” tag:
<script type="text/javascript">
	$('#targetDate').datepicker({
	    	format: 'yyyy-mm-dd',
	});
</script>

    * We use ‘#’ because we’re using the “id” of the field
    * We use “targetDate” because we want the data picker to popup when the user clicks in the Target Date text box
        * “targetDate” is the id of the field. You can see this when you go to the webpage and inspect it
    * In JavaScript, the month format is lowercase “mm”


<nav class="navbar navbar-expand-md navbar-light bg-light mb-3 p-1">
<a class="navbar-brand m-1" href="https://courses.in28minutes.com">in28Minutes</a>
<div class="collapse navbar-collapse">
<ul class="navbar-nav">
<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
<li class="nav-item"><a class="nav-link" href="/list-todos">Todos</a></li>
</ul>
</div>
<ul class="navbar-nav">
<li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
</ul>
</nav>



Adding a Navigation Bar and Implementing JSP Fragments:
* They provided this code snippet that implements the navigation bar:
<nav class="navbar navbar-expand-md navbar-light bg-light mb-3 p-1">
	<a class="navbar-brand m-1" href="https://courses.in28minutes.com">in28Minutes</a>
	<div class="collapse navbar-collapse">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
			<li class="nav-item"><a class="nav-link" href="/list-todos">Todos</a></li>
		</ul>
	</div>
	<ul class="navbar-nav">
	<li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
	</ul>
</nav>

* The “nav” tag is used to add a navigation bar. All the classes used are from Bootstrap
* JSP Fragments:
    * This is a way to reuse code for views (in our case JSPs)
    * Create a new folder called “common” in the folder where I have all my JSP files
    * Inside the “common” folder create these 3 files:
        * header.jspf
        * footer.jspf
        * navigation.jspf
* For “navigation.jspf”, put the navigation bar code in this file
* To use the JSP Fragment in other files, use the following code at the location you want it:
<%@ include file="common/navigation.jspf" %>

* Moving code into JSP Fragments can cause your JSP files to look incomplete. It’s important to recognize any of the “<%@ include file="common/filename.jspf" %>” pieces of code

Preparing for Spring Security:
* Spring Security makes it easy to implement Authentication, Login, and Logout
* In the LoginController we need to change the GET request method “gotoLoginPage” so the path is just a ‘/’ and the return is “welcome”
    * This makes it so if anyone goes to the root of the application it sends them to the welcome page
* We no longer need the POST method in the LoginController, the AuthenticationService, or “login.jsp”
* We also rename the method to “gotoWelcomePage” and the class to “WelcomeController”

Setting up Spring Security with Spring Boot Starter Security:
* Add “spring-boot-starter-security” to POM
* Now when you restart the server you will see something like this in the logs:
Using generated security password: 334823c3-372e-4190-9f9c-379834a40145
This generated password is for development use only. Your security configuration must be updated before running your application in production

* Now when you go to “http://localhost:8080” it will automatically take you to “http://localhost:8080/login” and produce a login page
    * It will also go to the login page if the user tries going to another page on the application without logging in!
* To sign into localhost, the username will be “user” and the password will be whatever was generated when you started the server:
    * Example:
        * Username: user
        * Password: 334823c3-372e-4190-9f9c-379834a40145

Configuring Spring Security with Custom User and Password Encoder:
* Will configure Spring Security with hard-coded username and password
* Create SpringSecurityConfiguration class
* This class will contain many Spring Beans
* Add the “@Configuration” annotation
* Normally for usernames and passwords we’ll use LDAP or a Database, but for our purposes we’ll use in-memory storage
* We’ll be using “InMemoryUserDetailsManager”, which is a class provided by Spring Security
* InMemoryUserDetailsManager takes “UserDetails” as a parameter, so we need to create UserDetails then pass it as an argument into InMemoryUserDetailsManager
* “Function<String, String> passwordEncoder;” is a lambda function that accepts a String as input and returns a String as output
* For “Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);”:
    * Whatever password comes in as input, the passwordEncoder() will encode it, then configure the user details

@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder().passwordEncoder(passwordEncoder)
				.username("in28minutes").password("dummy").roles("USER", "ADMIN").build();	
		return new InMemoryUserDetailsManager(userDetails);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

* In the above, we have a few places we see “passwordEncoder”
    * In “User.builder().passwordEncoder(passwordEncoder)”:
        * The function “passwordEncoder(…)” is provided by Spring Security
        * The variable “passwordEncoder” being passed as an argument is from the Lambda we created “Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);”
    * In “Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);”:
        * The “passwordEncoder()” function is the one we created:
@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

* Now logout and sign in with this hard-coded user
    * You’ll notice the logout button works now. This is because Spring Security provides a default logout

Refactoring and Removing Hardcoding of User Id:
* There are various places we added Hardcoded usernames, we’re removing those and adding the Spring Security way of getting usernames
    * Using SecurityContextHolder we get the logged-in username and add it to the ModelMap
* I think we should be getting the username from Spring Security everywhere, instead of using ModelMap

Setting up a New User for Todo Application:
* Used Spring Security to hard-code usernames and passwords

Adding Spring Boot Starter Data JPA and Getting H2 database ready:
* Replacing the static ArrayList in TodoService class with database
* H2 is a in-memory database
* We’ll use JPA to talk to the H2 database
* Added the following dependencies to POM:
    * spring-boot-starter-data-jpa
    * h2
* Starting the server shows log:
    * H2 console is available at '/h2-console'. Database available at 'jdbc:h2:mem:e7de5c64-e723-44d5-b207-938025ec15e3'
        * This database URL changes every time the server is restarted
    * Because the JDBC URL is “jdbc:h2:mem:testdb” when you visit “http://localhost:8080/h2-console”, you can add the following to the application.properties to allow the database URL to update dynamically:
spring.datasource.url=jdbc:h2:mem:testdb

* Restarting the server shows:
    * H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'

Configuring Spring Security to Get H2 console Working:
* Due to using Spring Security, “http://localhost:8080/h2-console” has an error. We’re going to fix the error.
* In order to access the H2 Console we need to:
    * Disable Cross Site Request Forgery (CSRF)
    * Enable Frames because Spring Security does not allow them by default and the H2 Console needs them
* SecurityFilterChain (An interface provided by Spring Security):
    * Every request that comes in has to go through the SecurityFilterChain
    * By default, ensures the following:
        * All URLs are protected
        * A login form is shown for unauthorized requests
    * You can add functionality to the SecurityFilterChain by creating the method:

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
	http.formLogin(withDefaults());	
	return http.build();
}

* When implementing the SecurityFilterChain, you have to add the default functionality of:
    * All URLs are protected
    * A login form is shown for unauthorized requests
* To find the “import static org.springframework.security.config.Customizer.withDefaults;” import, I had to:
    * Press “command + shift + t”
    * Search for “customizer”
    * Open the one from springframework security
    * Right click the interface name
    * Select “Copy Qualified Name”
    * Paste it with “import static” and “.withDefaults”
* Now that we’ve re-defined the defaults, we can do anything else we want to the requests coming in
* In our case:
    * Disable CSRF and enable frames:

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
	http.formLogin(withDefaults());
	http.csrf().disable();
	http.headers().frameOptions().disable();
	return http.build();
}

* The line “http.headers().frameOptions().disable();” is actually enabling the use of frames
* Now you should be able to connect to the H2 Console


Making Todo an Entity and Population Todo Data into H2:
* We already have a “Todo” java class (or Bean), let’s say we want to create a Todo database table; JPA allows us to map the “Todo” class (or Bean) to the “Todo” database table
* You do this by adding the “@Entity” annotation above the “Todo” java class (or Bean)
* When you use “@Entity” you also need an “@Id” annotation
* “@GeneratedValue” creates an id for you
* Spring Boot will automatically create tables in H2 by looking for @Entity annotations
* You can create a “data.sql” file in the src/main/resources folder that will execute its SQL on startup
    * Make sure you use single quotes around strings in SQL
* By default, the sql in “data.sql” is executed before the “@Entity” tables are created. You can change this by adding the following to application.properties:
spring.jpa.defer-datasource-initialization=true

Creating TodoRepository & Connecting List Todos page from H2 database:
* We’re going to add CRUD operations to our Todos
* You achieve this by using a Repository
* A Repository allows you to perform actions on anything marked with “@Entities”
* Create a Repository:
    * Create an interface and extend JpaRepository
        * JpaRepository<Bean, ID_Type>
        * Example:
            * JpaRepository<Todo, Integer>

public interface TodoRepository extends JpaRepository<Todo, Integer> { }

* Now when you create an instance of TodoRepository, you will have a lot of functionality you can use, but if there’s not default functionality, you can create it in the interface:

public interface TodoRepository extends JpaRepository<Todo, Integer> {
	public List<Todo> findByUsername(String username);	
}

* So now when you want to interact with the Todo table in the database, you use a TodoRepository instance
* Due to the syntax used for “findByUsername” and the “String username” argument, Spring automatically knows “username” from the “Todo” Bean and that we want to search by username
* Also, the Todo class needs a default constructor


Connecting All Todo App Features to H2 Database:
* Finishing CRUD operations to our Todos using JPA and Spring Data JPA to interact with our H2 in-memory database
* We replaced our TodoService operations with our TodoRepository operations

Exploring Magic of Spring Boot Starter JPA and JpaRepository:
* Abstraction levels from top to bottom:
    * Spring Data JPA
    * JPA
    * Database
* Adding the following to the application.properties file will show all the SQL statements that get executed when using operations provided by Spring Data JPA:
spring.jpa.show-sql=true

OPTIONAL - Overview of Connecting Todo App to MySQL database:
* You can connect to any database using JPA and Spring Data JPA very easily
* We’ll be launching MySQL as a Docker Container and connecting to it from our application

OPTIONAL - Installing Docker:
* Downloaded Docker Desktop

Resources for Next Step:
* Command to launch MySQL using Docker:
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle

* Add to application.properties:
#spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/todos
spring.datasource.username=todos-user
spring.datasource.password=dummytodos
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
#todos-user@localhost:3306

* mysqlsh commands:
mysqlsh
\connect todos-user@localhost:3306
\sql
use todos
select * from todo;
\quit

* Use “mysql-connector-j” instead of “mysql-connector-java” if you are using Spring Boot 3.1 or greater.
* Remember: groupId is a little different (“com.mysql” instead of “mysql”)

* Use this for Spring Boot 3.1 and higher:
<dependency>
	<groupId>com.mysql</groupId>
	<artifactId>mysql-connector-j</artifactId>
</dependency>
		 
* Use this if you are using Spring Boot 3.0 or lower:
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>


OPTIONAL - Connecting Todo App to MySQL database:
* Launch docker desktop, then type the command “docker version” to see the client and server
* Run the command to launch MySQL using Docker (it might have to download mysql locally):
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle

* Breakdown of the command above:
    * docker run:
        * Used to launch MySQL using the image “mysql:8-oracle”
            * Using MySQL version 8 because it can run on any OS
    * --env
        * MYSQL_ROOT_PASSWORD: not sure what we need this for
        * MYSQL_USER and MYSQL_PASSWORD are used to connect to the database
        * “MYSQL_DATABASE=todos” creates a database inside MySQL called “todos”
    * --name mysql
        * The name of the container
    * --publish 3306:3306
        * The port we’re publishing MySQL to

* Command “docker container ls” shows all the docker containers present
    * This should show your “mysql” container running 

* Docker is really good for installing anything on your development machine. You can use it and immediately delete the container. The alternative for MySQL is to install it locally and that has many complexities involved.

* Changes to POM:
    * Comment out the “h2” dependency
    * Add the appropriate mysql dependency (see “Resources for Next Step” above)

* Changes to application.properties:
    * Comment out “spring.datasource.url=jdbc:h2:mem:testdb”
    * Add all MySQL properties (see “Resources for Next Step” above)
    * SPECIAL NOTES:
        * “spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect” tells Spring which dialect to use. This is important because there are differences between H2 and MySQL dialect. We have hibernate in there because we’re using hibernate as our framework.
            * To find the path “org.hibernate.dialect.MySQL8Dialect”:
                * Press “command + shift + t” and search for “MySQL8Dialect” and open it. Right click the class name and select “Copy Qualified Name”
        * With H2, Spring automatically created the db tables. With MySQL, Spring does not know to do that by default. 
            * Adding the line “spring.jpa.hibernate.ddl-auto=update” tells Spring to automatically create db tables at startup.

* Now if you start the application and go to “localhost:8080”, sign in, and go to the Todos page, you won’t see any Todos listed. This is because with MySQL the “data.sql” file is not executed when connecting to a database like MySQL.
    * So you can add Todos from the web app
* To check if these are being added to MySQL (the “todo” table should show the todo item you added from the web app):
    * Download MySQL Shell if you don’t have it
    * In Terminal use these commands to connect to the database:
mysqlsh
    * pulls up MySQL Shell
\connect todos-user@localhost:3306
    * connects to db, use MYSQL_PASSWORD “dummytodos” when prompted
\use todos
    * connect to “todos” schema
\sql
    * allows you to use SQL statements
    * Example:
        * select * from todo;
\quit
    * exits MySQL Shell

* The great thing about MySQL is that the data is persistent. I can stop my application and the data will still be in the Todo table. Then starting the application back up will show the persistent data still.
