![APM](https://img.shields.io/apm/l/vim-mode)  **[EN](README_EN.md)**  
Часть тестового задания 
(смотри репозитории [ServiceA](https://github.com/M1keM1ke/serviceA.git),
 [Adapter](https://github.com/M1keM1ke/Adapter.git) и
[ServiceB](https://github.com/M1keM1ke/ServiceB.git)), состоящего  
из трех микросервисов.

# Описание задания
Написать микросервис «Adapter», который принимает сообщение из «Service А»,   
производит преобразования, описанные ниже, и передает его в «Service B».  
1. Коммуникация между всеми микросервисами осуществляется с использованием  
архитектурного стиля REST.  
2. Формат сообщений, отправляемых «Service А»:
example:  
_{  
"msg": "Привет",  
"lng": "ru",  
"coordinates": {  
"latitude": "54.35",  
"longitude": "52.52"  
}  
}_  
3. Требуется обрабатывать сообщения только с признаком "lng": "ru" и, используя  
координаты, обогащать сообщение данными из сервиса погоды. Сообщения, не  
прошедшие условия фильтрации – игнорировать. Если сервис погоды недоступен –  
считать это ошибкой.
4. В дальнейшем список поддерживаемых сервисов погоды с их форматами будет расширяться.  
5. Формат сообщений принимаемых сервисом «В»:  
example:  
_{  
"txt": "Привет",  
"createdDt": "2020-06-10T10:15:30Z",  
"currentTemp": "28"  
}_  
6. Добавить обработку ошибок при получении пустого сообщения из сервиса “А”.  
Пустым сообщением считать сообщение, не содержащее ни одного символа в поле  
“msg”.    
7. Написать компонентные тесты для проверки кода  
8. Использовать gradle для сборки проекта  
9. Настройка Spring с помощью Java-аннотаций  

# Используемые технологии 
* Spring boot 2.3.1  
* AMPQ 2.3.2  
* JUnit 4.13  
* OpenWeatherAPI  

# Запуск и тестирование  
В качестве брокера сообщений между сервисами мной был выбран RabbitMQ, поэтому для  
запуска необходимо установить сервер RabbitMQ, а также Erlang  последних версий:  
https://www.rabbitmq.com/download.html  
https://www.erlang.org/  
После установки необходимо перейти на http://localhost:15672/ для отслеживания поступающих  
сообщений (после установки требуется авторизация, по умолчанию логин/пароль - guest/guest).  
Также из-за использования в проекте OpenWeatherAPI, бесплатно предоставляющего данные о  
погоде, необходимо сгенерировать свой уникальный токен и подставить его в ссылку запроса для  
успешного получения json ответа. После вышеперечисленных действий необходимо запустить все три  
сервиса для корректной работы.  
# Принцип работы  
ServiceA генерирует случайные сообщения и после создания отправляет их в очередь msg-a-queue.  
После этого Adapter принимает сообщения, ожидающие в очереди msg-a-queue, вытаскивает их них  
ширину и долготу, которые были описаны по условию задания, и отправляет запрос на сервер погоды  
с указанными параметрами, после чего получает json. Из ответа Adapter берет температуру, и  
формирует новое сообщение, формат которого был описан выше. После создания шаблона сообщения,  
Adapter формирует json и отправляет его в очередь msg-b-queue. Далее ServiceB принимает сообщения,  
ожидающие в очереди msg-b-queue.  