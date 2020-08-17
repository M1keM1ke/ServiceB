![APM](https://img.shields.io/apm/l/vim-mode)  **[RU](README.md)**  

Part of the test task
(see repositories [ServiceA](https://github.com/M1keM1ke/serviceA.git), 
 [Adapter](https://github.com/M1keM1ke/Adapter.git) and
[ServiceB](https://github.com/M1keM1ke/ServiceB.git)), consisting  
of three microservices.  

# Task description  
Write a microservice "Adapter" that accepts a message from "Service A",  
performs the transformations described below and transfers it to "Service B".  
1. Communication between all microservices is done using  
REST.  
2. Format of messages sent by "Service A":  
example:  
_{  
"msg": "Hello",  
"lng": "en",  
"coordinates": {  
"latitude": "54.35",  
"longitude": "52.52"  
}  
}_  
3. Process messages only with the "lng": "ru" flag and using  
   coordinates, enrich the message with data from the weather service. Messages not  
   passed filtering conditions - ignore. If the weather service is not available -  
   consider it a mistake.  
4. In the future, the list of supported weather services with their formats will expand.  
5. Format of messages received by service "B":  
example:  
_{  
"txt": "Hello",  
"createdDt": "2020-06-10T10:15:30Z",  
"currentTemp": "28"  
}_  
6. Add error handling when receiving an empty message from service “A”.  
   An empty message is a message that does not contain any characters in the field  
   "Msg".    
7. Write component tests to validate your code.  
8. Use gradle to build the project.  
9. Spring customization with Java annotations. 

# Technologies used in the project  
* Spring boot 2.3.1  
* AMPQ 2.3.2  
* JUnit 4.13  
* OpenWeatherAPI  

# Launch and testing
I chose RabbitMQ as a message broker between services, so for  
running, you need to install the RabbitMQ server, as well as the latest Erlang versions:  
https://www.rabbitmq.com/download.html  
https://www.erlang.org/  
After installation, you need to go to http: // localhost: 15672 / to track incoming  
messages (after installation, authorization is required, by default login / password is guest / guest).  
Also by reason of the use in the project OpenWeatherAPI, which provides data about  
weather, you need to generate your unique token and substitute it in the request link for  
successfully receiving json response. After the above steps, you need to run all three  
service for correct work of the application.  
# Principle of operation  
ServiceA generates random messages and sends them to the msg-a-queue.  
After that Adapter accepts messages waiting in the msg-a-queue, pulls them out  
width and longitude, and sends a request to the weather server  
with the specified ones, after which it receives json. Adapter takes the temperature from the answer, and  
generates a new message, the format of which was described above. After creating the message template  
Adapter generates json and sends it to the msg-b-queue. After that ServiceB receives messages  
waiting in the queue msg-b-queue.  