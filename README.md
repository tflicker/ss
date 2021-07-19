# README for Java Coding Challenge
### Overview
I used Spring Boot v2.5.2 for this project along with Jersey to handle REST calls. I didn't have enough time to
hook in a database so I used an in-memory collection for storage. I have the data layer separated 
so hooking up a database in the future will be localized to this layer. 

##### My test environment
~~~
Apache Maven 3.3.3
Java version: 1.8.0_291
Vendor: Oracle Corporation
OS name: Windows 10
Eclipse 2020-12
JUnit 5
~~~

##### Code organization
* `com.ss.contacts` - contains code related to the Spring application<br />
* `com.ss.contacts.db` - contain data objects as well as logic needed to perform CRUD operations on the storage layer<br />
* `com.ss.contacts.core` - contains classes directly related to the core of the application and is used by all interfaces<br />
* `com.ss.contacts.interfaces` - contain code related to all interfaces. Currently, only a REST interface is hooked up. I also included a placeholder package for a SOAP interface
  
##### Other notes
It was unclear from the documentation how the JSON should be formated for a collection of results. I went with the above conventions keeping each entry in line with the desired format.
```
GET /contacts
{
   "contact":[
      {
         "id":0,
         "name":{
            "first":"MyFirst",
            "middle":"MyMiddle",
            "last":"MyLast"
         },
         "address":{
            "street":"My Street",
            "city":"My City",
            "state":"My State",
            "zip":"My Zip"
         },
         "phone":[
            {
               "number":"111-111-1111",
               "type":"home"
            }
         ],
         "email":"my@email.com"
      },
      {
         "id":1,
         "name":{
            "first":"Harold",
            "middle":"Francis",
            "last":"Gilkey"
         },
         "address":{
            "street":"8360 High Autumn Row",
            "city":"Cannon",
            "state":"Delaware",
            "zip":"19797"
         },
         "phone":[
            {
               "number":"302-611-9148",
               "type":"home"
            },
            {
               "number":"302-532-9427",
               "type":"mobile"
            }
         ],
         "email":"harold.gilkey@yahoo.com"
      }
   ]
}

GET /contacts/call-list
{
   "contact":[
      {
         "name":{
            "first":"MyFirst",
            "middle":"MyMiddle",
            "last":"MyLast"
         },
         "phone":"111-111-1111"
      },
      {
         "name":{
            "first":"UpdatedHarold",
            "middle":"Francis",
            "last":"Gilkey"
         },
         "phone":"302-611-9148"
      }
   ]
}
```


### Running and testing
##### Download the project from GIT
```
$ git clone https://github.com/tflicker/ss.git
$ cd ss
```

##### Run unit tests
```
$ mvn clean test
```

##### Run the application
```
$ mvn spring-boot:run
```
Once the application has started, you can access the REST API at `http://localhost:8080/contacts`<br />
<br />
You will know the application has started when you see the following:
> Started ContactsApplication in X.YZ seconds (JVM running for 2.814)

##### Run additional tests
I included a simple JMeter project that performs CRUD operations. You can find the executable at:<br />
`Tests\apache-jmeter-5.4.1\bin`
<br /><br />
The test is located in the bin folder with name:<br />
 `single-stone-contacts.jmx`<br />
 <br />
 The JMeter tests expect the application is running on localhost port 8080. Also, it assumes the storage layer does'
 not contain any data.  