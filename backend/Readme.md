###Food calculator v0.0.1

To start project need to be installed:
*1) JDK v8 or later*
*2) Maven*
*3) Npm*
*4) MySql* 
	*    a)configuration of DB connection stored in the application.properties file*
	*    b)you need to configure your database as provided in config*
*5) tomcat app server*

###To run app use mvn spring-boot:run command or run-> java Application from menu of IDE


###REST DESCRIPTION (will be moved to swagger files)

Account REST:
localhost:8080/accounts

required fields: email - string, password-string, isAdmin - bollean (false by default)
create :
```{
    "email":"some@emai.com",
    "password":"Qwerty123456",
    "isAdmin":false
}
getAll:
[{
   "id": 1,
   "email": "some@emai.com",
   "password": "Qwerty123456",
   "userDetail":    {
      "id": 4,
      "name": null,
      "nickName": null,
      "weight": 0,
      "height": 0
   },
   "admin": false
}]

delete: localhost:8080/accounts/{id}


Users REST:
localhost:8080/users

POST path: localhost:8080/users - create user command request pattern:
{
	"name": "Some name",
	"nickName": "some nickname",
	"email": "some@email.com'"
} 

GET path: localhost:8080/users/all - get all users response pattern
[{
   "id": null,
   "uuid": null,
   "createDate": null,
   "createPrincipal": null,
   "updateDate": null,
   "updatePrincipal": null,
   "name": "Some name",
   "nickName": "some nickname",
   "email": "some@email.com'",
   "weight": 0,
   "height": 0,
   "new": true
}]

```

