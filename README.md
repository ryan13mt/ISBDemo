# ISBDemo

## How to compile
Assuming you have maven and java 11 installed, just run mvn clean install to compile the application.

## How to run
To run application, either load it into Intellij or any other IDE and run "DemoApplication" or package application and then run jar.

You can also run the jar file directly by using the command:
 
 ``java -jar service-0.0.1-SNAPSHOT.jar``

## How to use

In the directory i have provided the yaml file containing the OAS3 definition and swagger2 definition. Importing it in http://editor.swagger.io/ will not work since it needs CORS enable from the application. Please visit 
http://localhost:26000/swagger-ui.html# which holds the API definitions requested including examples which can be used to call and test the application.

Jacoco report can be found in /service/target/jacoco-report/index.html after running 'mvn clean install'. There are some models that are not covered with tests since they are generated by libraries like lombok and QuesryDSL.

I implemented four endpoint:

### get => /mobileSubscription

This is a simple call that will return all the mobile subscriptions if no filter is provided. If filter is provided it will filter depending on what parameters are used. 

``{
    "customerIdOwner": int,
    "customerIdUser": int,
    "msisdn": string,
    "serviceStartDateFrom": long,
    "serviceStartDateTo": long,
    "serviceType": string (MOBILE_PREPAID or MOBILE_POSTPAID)
}``

Please note each parameter can either be left null or be used. Database starts out as empty.

### post => /mobileSubscription

This call will add a new mobile subscription in the system. The fields required are 

``{
   "customerIdOwner": 333,
   "customerIdUser": 334,
   "msisdn": "35679055445",
   "serviceType": "MOBILE_PREPAID"
}``


 
### delete => /mobileSubscription/{id}

This call will delete a mobile subscription based on the id provided.

### patch => /mobileSubscription/{id}

This call will update one or multiple values related with the mobile subscription that has the id provided. You only need to send the values you want changed. 
Example: 

``{
    "customerIdOwner": 222,
    "customerIdUser": 223,
    "serviceType": "MOBILE_PREPAID"
}``

The other values (id, msisdn, serviceStartDate) not in this json message cannot be edited.




## Architecture

Application was designed along the Hexagonal Architecture Pattern. Hexagonal Architecture implies a separation between the domain and the adapters which are in turn split into primary adapter and secondary adapters. A primary adapter makes calls to the domain such as a unit test or a rest controller while the secondary adapter is called from the domain such as a database or caching server. In this exercise the adapters and separated from the domain by packages. This for me offers a lot more control and easier testing as well.
