# ISBDemo

## How to compile
Assuming you have maven and java 8 installed, just run mvn clean install to compile the application.

## How to run
To run application, either load it into Intellij or any other IDE and run "DemoApplication" or package application and then run jar.

## How to use

http://localhost:26000/swagger-ui.html#



## Architecture

Application was designed along the Hexagonal Architecture Pattern. Hexagonal Architecture implies a separation between the domain and the adapters which are in turn split into primary adapter and secondary adapters. A primary adapter makes calls to the domain such as a unit test or a rest controller while the secondary adapter is called from the domain such as a database or caching server. In this exercise the adapters and separated from the domain by packages. This for me offers a lot more control and easier testing as well.
