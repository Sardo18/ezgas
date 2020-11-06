# Design Document 


Authors: Federico Quarta, Alessandro Lepori, Victor Seguin, Silvio Girolami 

Date: 02/05/2020

Version: 1.0


# Contents

- [Design Document](#design-document)
- [Contents](#contents)
- [Instructions](#instructions)
- [High level design](#high-level-design)
  - [Front End](#front-end)
  - [Back End](#back-end)
- [Low level design](#low-level-design)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)
  - [Use Case 1: Create a user](#use-case-1-create-a-user)
  - [Use Case 2: Modify a user](#use-case-2-modify-a-user)
  - [Use Case 3: Delete a user](#use-case-3-delete-a-user)
  - [Use Case 4: Save a gas station](#use-case-4-save-a-gas-station)
  - [Use case 5: Modify a gas station](#use-case-5-modify-a-gas-station)
  - [Use case 6: Delete a gas station](#use-case-6-delete-a-gas-station)
  - [Use case 7.1: Report fuel prices for a gas station without attached price list](#use-case-71-report-fuel-prices-for-a-gas-station-without-attached-price-list)
  - [Use case 7.2: Report fuel prices for a gas station with an attached price list](#use-case-72-report-fuel-prices-for-a-gas-station-with-an-attached-price-list)
  - [Use case 8.1: Get all the gas stations in a certain area](#use-case-81-get-all-the-gas-stations-in-a-certain-area)
  - [Use case 8.2: Get all the gas stations in a certain area filtered by fuel type](#use-case-82-get-all-the-gas-stations-in-a-certain-area-filtered-by-fuel-type)
  - [Use case 8.3: Get all the gas stations in a certain area filtered by car sharing](#use-case-83-get-all-the-gas-stations-in-a-certain-area-filtered-by-car-sharing)

# Instructions

The design must satisfy the Official Requirements document (see EZGas Official Requirements.md ). <br>
The design must comply with interfaces defined in package it.polito.ezgas.service (see folder ServicePackage ) <br>
UML diagrams **MUST** be written using plantuml notation.

# High level design 

The style selected is client - server. Clients can be smartphones, tablets, PCs.
The choice is to avoid any development client side. The clients will access the server using only a browser. 

The server has two components: the frontend, which is developed with web technologies (JavaScript, HTML, Css) and is in charge of collecting user inputs to send requests to the backend; the backend, which is developed using the Spring Framework and exposes API to the front-end.
Together, they implement a layered style: Presentation layer (front end), Application logic and data layer (back end). 
Together, they implement also an MVC pattern, with the V on the front end and the MC on the back end.



```plantuml
@startuml
package "Backend" {

}

package "Frontend" {

}


Frontend -> Backend
@enduml


```


## Front End

The Frontend component is made of: 

Views: the package contains the .html pages that are rendered on the browser and that provide the GUI to the user. 

Styles: the package contains .css style sheets that are used to render the GUI.

Controller: the package contains the JavaScript files that catch the user's inputs. Based on the user's inputs and on the status of the GUI widgets, the JavaScript controller creates REST API calls that are sent to the Java Controller implemented in the back-end.


```plantuml
@startuml
package "Frontend" {

    package "it.polito.ezgas.resources.views" {

    }


package "it.polito.ezgas.resources.controller" {

    }


package "it.polito.ezgas.resources.styles" {

    }



it.polito.ezgas.resources.styles -down-> it.polito.ezgas.resources.views

it.polito.ezgas.resources.views -right-> it.polito.ezgas.resources.controller


}
@enduml

```

## Back End

The backend  uses a MC style, combined with a layered style (application logic, data). 
The back end is implemented using the Spring framework for developing Java Entrerprise applications.

Spring was selected for its popularity and relative simplicity: persistency (M and data layer) and interactions are pre-implemented, the programmer needs only to add the specific parts.

See in the package diagram below the project structure of Spring.

For more information about the Spring design guidelines and naming conventions:  https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3



```plantuml
@startuml
package "Backend" {

package "it.polito.ezgas.service"  as ps {
   interface "GasStationService"
   interface "UserService"
} 


package "it.polito.ezgas.controller" {

}

package "it.polito.ezgas.converter" {

}

package "it.polito.ezgas.dto" {

}

package "it.polito.ezgas.entity" {

}

package "it.polito.ezgas.repository" {
    
}

    
}
note "see folder ServicePackage" as n
n -- ps
@enduml
```



The Spring framework implements the MC of the MVC pattern. The M is implemented in the packages Entity and Repository. The C is implemented in the packages Service, ServiceImpl and Controller. The packages DTO and Converter contain classes for translation services.



**Entity Package**

Each Model class should have a corresponding class in this package. Model classes contain the data that the application must handle.
The various models of the application are organised under the model package, their DTOs(data transfer objects) are present under the dto package.

In the Entity package all the Entities of the system are provided. Entities classes provide the model of the application, and represent all the data that the application must handle.




**Repository Package**

This package implements persistency for each Model class using an internal database. 

For each Entity class, a Repository class is created (in a 1:1 mapping) to allow the management of the database where the objects are stored. For Spring to be able to map the association at runtime, the Repository class associated to class "XClass" has to be exactly named "XClassRepository".

Extending class JpaRepository provides a lot of CRUD operations by inheritance. The programmer can also overload or modify them. 



**DTO package**

The DTO package contains all the DTO classes. DTO classes are used to transfer only the data that we need to share with the user interface and not the entire model object that we may have aggregated using several sub-objects and persisted in the database.

For each Entity class, a DTO class is created (in a 1:1 mapping).  For Spring the Dto class associated to class "XClass" must be called "XClassDto".  This allows Spring to find automatically the DTO class having the corresponding Entity class, and viceversa. 




**Converter Package**

The Converter Package contains all the Converter classes of the project.

For each Entity class, a Converter class is created (in a 1:1 mapping) to allow conversion from Entity class to DTO class and viceversa.

For Spring to be able to map the association at runtime, the Converter class associated to class "XClass" has to be exactly named "XClassConverter".




**Controller Package**

The controller package is in charge of handling the calls to the REST API that are generated by the user's interaction with the GUI. The Controller package contains methods in 1:1 correspondance to the REST API calls. Each Controller can be wired to a Service (related to a specific entity) and call its methods.
Services are in packages Service (interfaces of services) and ServiceImpl (classes that implement the interfaces)

The controller layer interacts with the service layer (packages Service and ServieImpl) 
 to get a job done whenever it receives a request from the view or api layer, when it does it should not have access to the model objects and should always exchange neutral DTOs.

The service layer never accepts a model as input and never ever returns one either. This is another best practice that Spring enforces to implement  a layered architecture.



**Service Package**


The service package provides interfaces, that collect the calls related to the management of a specific entity in the project.
The Java interfaces are already defined (see file ServicePackage.zip) and the low level design must comply with these interfaces.


**ServiceImpl Package**

Contains Service classes that implement the Service Interfaces in the Service package.










# Low level design

```plantuml
@startuml
top to bottom direction
package "it.polito.ezgas.controller" {
    class UserController {
        login(IdPw credentials) : LoginDto
        getUserById(Integer userId) : UserDto
        getAllUsers() : List<UserDto>
        saveUser(UserDto userDto) : UserDto
        deleteUser(Integer userId) : Boolean
        increaseUserReputation(Integer userId) : Integer
        decreaseUserReputation(Integer userId) : Integer
    }

    class GasStationController {
        getGasStationById(Integer gasStationId) : GasStationDto
        getAllGasStations() : List<GasStationDto>
        saveGasStation(GasStationDto gasStationDto) : GasStationDto
        deleteGasStation(Integer gasStationId) : Boolean
        getGasStationsByGasolineType(String gasolinetype) : List<GasStationDto>
        getGasStationsByProximity(double lat, double lon) : List<GasStationDto>
        getGasStationsWithCoordinates(double lat, double lon, String gasolinetype, String carsharing) : List<GasStationDto>
        setGasStationReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, Integer userId) : List<GasStationDto>
    }


}

package "it.polito.ezgas.service" {
   interface "GasStationService"
   interface "UserService"
} 



package "it.polito.ezgas.converter" {

        class GasStationConverter {

            toGasStationDto(gasStation : GasStation) : GasStationDto
            toGasStation(gasStation : GasStationDto) : GasStatio

    }

    class UserConverter {

        toUserDto(user : User) : UserDto
        toUser(user : UserDto) : User
    } 
    

}

package "it.polito.ezgas.repository" {
    class UserRepository{
        findByEmail(String Email): User
    }

    class GasStationRepository {
    }
}

package "it.polito.ezgas.dto" {
    class LoginDto{
        - userId : Integer
        - userName : String
        - token : String
        - email : String
        - reputation : Integer
        - admin : Boolean
        + getUserId() : Integer
        + getUserName() : String
        + getToken() : String
        + getEmail() : String
        + getReputation() : Integer
        + getAdmin() : Boolean
        + setUserId(Integer userId)
        + setUserName(String userName)
        + setToken(String token)
        + setEmail(String email)
        + setReputation(Integer reputation)
        + setAdmin(Booelan admin)
        }

        class GasStationDto {
        - gasStationId : Integer
        - gasSationName : String
        - gasStationAddress : String
        - brand : String
        - hasDiesel : Boolean
        - hasGas : Boolean
        - hasSuper : Boolean
        - hasSuperPlus : Boolean
        - hasMethane : Boolean
        - lat : Double
        - lon : Double
        - carSharing : String
        - dieselPrice : double
        - superPrice : double
        - superPlusPrice : double
        - gasPrice : double
        - methanePrice : double
        - reportUser : Integer
        - userDto : UserDto
        - reportTimestamp : String
        - reportDependability : double
        - priceReportDtos : List<PriceReportDto>
        + getReportDependability() : double
        + getGasStationId() : Integer
        + getGasStationName() : String
        + getGasStationAddress() : String
        + getHasDiesel() : Boolean
        + getHasSuper() : Boolean
        + getHasSuperPlus() : Boolean
        + getHasGas() : Boolean
        + getHasMethane() : Boolean
        + getLat() : Double
        + getLon() : Double
        + getDieselPrice() : double
        + getSuperPrice() : double
        + getSuperPlusPrice() : double
        + getGasPrice() : double
        + getMethanePrice() : double
        + getPriceReportDtos() : List<PriceReportDto>
        + getReportUser() : Integer
        + getReportTimestamp() : String
        + getUserDto() : UserDto
        + getCarSharing() : String
        + serReportDependability(double reportDependability)
        + setGasStationId(Integer gasStationId)
        + setGasStationName(String gasStationName)
        + setGasStationAddress(String gasStationAddress)
        + setHasDiesel(Boolean hasDiesel)
        + setHasSuper(Boolean hasSuper)
        + setHasSuperPlus(Boolean hasSuperPlus)
        + setHasGas(Boolean hasGas)
        + setHasMethane(Boolean hasMethane)
        + setLat(Double lat)
        + setLon(Double lon)
        + setDieselPrice(Double dieselPrice)
        + setSuperPrice(Double superPrice)
        + setSuperPlusPrice(Double superPlusPrice)
        + setGasPrice(Double gasPrice)
        + setMethanPrice(Double methanePrice)
        + setPriceReportStos(List<PriceReportDto> priceReportDtos)
        + setReportUser(Integer reportUser)
        + setReportTimestamp(String reportTimestamp)
        + setUserDto(UserDto userDto)
        + setCarSharing(String carSharing)

    }

    class PriceReportDto {
        - priceReportId : Integer
        - user : User
        - dieselPrice : double
        - superPrice : double
        - superPlusPrice : double
        - gasPrice : double
        + getPriceReportId() : Integer
        + getUser() : User
        + getDieselPrice() : double
        + getSuperPrice() : double
        + getSuperPlusPrice() : double
        + getGasPrice() : double
        + setPriceReportId(Integer priceReportId)
        + setUser(User user)
        + setDieselPrice(double dieselPrice)
        + setSuperPrice(double superPrice)
        + setSuperPlusPrice(double superPlusPrice)
        + setGasPrice(double gasPrice)
    }

    class UserDto {
        - userId : Integer
        - userName : String
        - password : String
        - email : String
        - reputation : Integer
        - admin : Boolean
        + getUserId() : Integer
        + getUserName() : String
        + getPassword() : String
        + getEmail() : String
        + getReputation() : Integer
        + getAdmin() : Boolean
        + setUserId(Integer userId)
        + setUserName(String userName)
        + setPassword(String password)
        + setEmail(String email)
        + setReputation(Integer reputation)
        + setAdmin(Boolean admin)
    } 
    
    class IdPw{        
        - user: String
        - pw : String
        + getUser() : String
        + getPw() : String
        + setUser(String user)
        + setPw(String pw)
    }

}

package "it.polito.ezgas.entity" {

  class GasStation {
        - gasStationId : Integer
        - gasSationName : String
        - gasStationAddress : String
        - brand : String
        - hasDiesel : Boolean
        - hasGas : Boolean
        - hasSuper : Boolean
        - hasSuperPlus : Boolean
        - hasMethane : Boolean
        - lat : Double
        - lon : Double
        - carSharing : String
        - dieselPrice : double
        - superPrice : double
        - superPlusPrice : double
        - gasPrice : double
        - methanePrice : double
        - reportUser : Integer
        - user : User
        - reportTimestamp : String
        - reportDependability : double
        + getReportDependability() : double
        + getGasStationId() : Integer
        + getGasStationName() : String
        + getGasStationAddress() : String
        + getHasDiesel() : Boolean
        + getHasSuper() : Boolean
        + getHasSuperPlus() : Boolean
        + getHasGas() : Boolean
        + getHasMethane() : Boolean
        + getLat() : Double
        + getLon() : Double
        + getDieselPrice() : double
        + getSuperPrice() : double
        + getSuperPlusPrice() : double
        + getGasPrice() : double
        + getMethanePrice() : double
        + getReportUser() : Integer
        + getReportTimestamp() : String
        + getUser() : User
        + getCarSharing() : String
        + serReportDependability(double reportDependability)
        + setGasStationId(Integer gasStationId)
        + setGasStationName(String gasStationName)
        + setGasStationAddress(String gasStationAddress)
        + setHasDiesel(Boolean hasDiesel)
        + setHasSuper(Boolean hasSuper)
        + setHasSuperPlus(Boolean hasSuperPlus)
        + setHasGas(Boolean hasGas)
        + setHasMethane(Boolean hasMethane)
        + setLat(Double lat)
        + setLon(Double lon)
        + setDieselPrice(Double dieselPrice)
        + setSuperPrice(Double superPrice)
        + setSuperPlusPrice(Double superPlusPrice)
        + setGasPrice(Double gasPrice)
        + setMethanPrice(Double methanePrice)
        + setPriceReportDtos(List<PriceReportDto> priceReportDtos)
        + setReportUser(Integer reportUser)
        + setReportTimestamp(String reportTimestamp)
        + setUser(User user)
        + setCarSharing(String carSharing)
    }

    class PriceReport {
        - priceReportId : Integer
        - user : User
        - dieselPrice : double
        - superPrice : double
        - superPlusPrice : double
        - gasPrice : double
        + getPriceReportId() : Integer
        + getUser() : User
        + getDieselPrice() : double
        + getSuperPrice() : double
        + getSuperPlusPrice() : double
        + getGasPrice() : double
        + setPriceReportId(Integer priceReportId)
        + setUser(User user)
        + setDieselPrice(double dieselPrice)
        + setSuperPrice(double superPrice)
        + setSuperPlusPrice(double superPlusPrice)
        + setGasPrice(double gasPrice)
    }
    class User {
        - userId : Integer
        - userName : String
        - password : String
        - email : String
        - reputation : Integer
        - admin : Boolean
        + getUserId() : Integer
        + getUserName() : String
        + getPassword() : String
        + getEmail() : String
        + getReputation() : Integer
        + getAdmin() : Boolean
        + setUserId(Integer userId)
        + setUserName(String userName)
        + setPassword(String password)
        + setEmail(String email)
        + setReputation(Integer reputation)
        + setAdmin(Boolean admin)
    } 
    

}

    UserController -down-> UserService
    GasStationController -down-> GasStationService
    UserService -down-> UserRepository
    UserService -down-> UserConverter
    UserService -down-> LoginDto
    UserService -down-> IdPw
    GasStationService -down-> GasStationRepository
    GasStationService -down-> GasStationConverter
    GasStationService -> UserService
    UserConverter -down-> User
    UserConverter -down-> UserDto
    GasStationConverter -down-> GasStation
    GasStationConverter -down-> GasStationDto
    UserRepository -down-> User
    GasStationRepository -down-> GasStation
    PriceReportDto -left- GasStationDto
    PriceReport -left- GasStation
@enduml
```












# Verification traceability matrix

|             | User   |   GasStation   | PriceReport  | UserController | GasStationController |  UserService |  GasStationService |
| ----------- | ------ |  ------------  | ------------ | ------------   | -----------------    | ------------ | ----------------   |
|FR1.1        |   X    |                |              |        X       |                      |       X      |                    |
|FR1.2        |   X    |                |              |        X       |                      |       X      |                    |
|FR1.3        |   X    |                |              |        X       |                      |       X      |                    |
|FR1.4        |   X    |                |              |        X       |                      |       X      |                    |
|FR2          |   X    |                |              |                |                      |              |                    |
|FR3.1        |   X    |       X        |     X        |                |          X           |              |         X          |
|FR3.2        |   X    |       X        |              |                |          X           |              |         X          |
|FR3.3        |        |       X        |     X        |                |          X           |              |         X          |  
|FR4.1        |        |       X        |     X        |                |          X           |              |         X          |
|FR4.2        |        |       X        |     X        |                |          X           |              |         X          |
|FR4.3        |        |       X        |     X        |                |          X           |              |         X          |
|FR4.4        |        |       X        |     X        |                |          X           |              |         X          |
|FR4.5        |        |       X        |     X        |                |          X           |              |         X          |    
|FR5.1        |   X    |       X        |     X        |        X       |          X           |       X      |         X          |
|FR5.2        |   X    |       X        |     X        |        X       |          X           |       X      |         X          |
|FR5.3        |   X    |       X        |     X        |        X       |          X           |       X      |         X          |









# Verification sequence diagrams 
## Use Case 1: Create a user
```plantuml
@startuml
actor User as u
autonumber
u -> UserController : saveUser(UserDto)
UserController -> UserService : saveUser(UserDto)
UserService -> UserConverter : toUser(UserDto)
UserConverter -> User : setUserName(userName)
UserConverter -> User : setPassword(password)
UserConverter -> User : setEmail(email)
UserConverter -> User : setReputation(0)
UserConverter -> User : setAdmin(admin)
UserService -> UserRepository : saveUser(User)
@enduml
```
## Use Case 2: Modify a user
```plantuml
@startuml
actor User as u
autonumber
u -> UserController : saveUser(UserDto)
UserController -> UserService : saveUser(UserDto)
UserService -> UserConverter : toUser(UserDto)
UserConverter -> User : setUserName(userName)
UserConverter -> User : setPassword(password)
UserConverter -> User : setEmail(email)
UserConverter -> User : setReputation()
UserConverter -> User : setAdmin(admin)
UserService -> UserRepository : saveUser(User)
@enduml
```

## Use Case 3: Delete a user
```plantuml
@startuml
actor User as u
autonumber
u -> UserController : deleteUser(userId)
UserController -> UserService : deleteUser(userId)
UserService -> UserRepository : deleteUser(userId)
@enduml
```

## Use Case 4: Save a gas station
```plantuml
@startuml
actor Admin as a
autonumber
a -> GasStationController : saveGasStation(gasStationDto)
GasStationController -> GasStationService : saveGasStation(gasStationDto)
GasStationService -> GasStationConverter : toGasStation(GasStationDto)
GasStationConverter -> GasStation : setGasStationName(gasStationName)
GasStationConverter -> GasStation : setGasStationAddress(gasStationAddress)
GasStationConverter -> GasStation : setHasDiesel(hasDiesel)
GasStationConverter -> GasStation : setHasSuper(hasSuper)
GasStationConverter -> GasStation : setHasSuperPlus(hasSuperPlus)
GasStationConverter -> GasStation : setHasGas(hasGas)
GasStationConverter -> GasStation : setHasMethane(hasMethane)
GasStationConverter -> GasStation : setLat(lat)
GasStationConverter -> GasStation : setLon(lon)
GasStationConverter -> GasStation : setCarSharing(carSharing)
GasStationService -> GasStationRepository : saveGasStation(gasStation)
@enduml
```

## Use case 5: Modify a gas station
```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant GasStationRepository as gsr
participant GasStation as gs
participant GasStationConverter as gsconv

u -> gsc : saveGasStation()
gsc -> gss: saveGasStation()
gss -> gsconv: toGasStation()
gss -> gsr: findById()
note left: get the GasStation entity from the db
gss -> gs: setGasStationName(gasStationName)
gss -> gs: setGasStationAddress(gasStationAddress)
gss -> gs: setHasDiesel(hasDiesel)
gss -> gs: setHasSuper(hasSuper)
note left: modify the gas station properties
gss -> gs: setHasSuperPlus(hasSuperPlus)
gss -> gs: setHasGas(hasGas)
gss -> gs: setHasMethane(hasMethane)
gss -> gs: setLat(lat)
gss -> gs: setLon(lon)
gss -> gs: setCarSharing(carSharing)
gss -> gsr: saveGasStation(gasStation)
note left: save the modification back to the db
gss -> gsconv: toGasStationDto(gasStation)
gss -> gsc: return gasStationDto
gsc -> u: return gasStationDto
@enduml
```

## Use case 6: Delete a gas station
```plantuml
@startuml
actor User as u
participant GasStationController as gc
participant GasStationService as gs
participant GasStationRepository as gr

u -> gc : deleteGasStation(gasStationId)
gc -> gs: deleteGasStation(gasStationId)
gs -> gr: deleteById(gasStationId)

@enduml
```

## Use case 7.1: Report fuel prices for a gas station without attached price list

```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant PriceReport as pr
participant UserRepository as ur
participant User as user
participant GasStationRepository as gsr
participant GasStation as gs

u -> gsc : setGasStationReport()
gsc -> gss: setReport()
note right gss:the gas station has no attached price report 
gss -> gsr: findById()
create pr
gss -> pr: new PriceReport()
note left
create a new PriceReport object 
and set its properties
end note
gss -> pr: setGasStationId(gasStationId) 
gss -> pr: setUser(user) 
gss -> pr: setReportTimestamp(reportTimestamp) 
gss -> pr: setDieselPrice(dieselPrice)
gss -> pr: setSuperPrice(superPrice)
gss -> pr: setSuperPluslPrice(superPlusPrice)
gss -> pr: setGasPrice(gasPrice)
gss -> pr: setMethanePrice(methanePrice)
gss -> ur: getById()
gss -> user: getReputation()
gss -> gss: computeReputation
gss -> pr: setReputation(reputation)
gss ->  gs: setReportDependability(reportDependability)
note left: attach the price report to the gas station
gss -> gss: saveGasStation()
note left
 save the gas station with the new
 price list into the db
end note
@enduml
```

## Use case 7.2: Report fuel prices for a gas station with an attached price report

```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant PriceReport as pr
participant UserRepository as ur
participant User as user
participant GasStationRepository as gsr
participant GasStation as gs

u -> gsc : setGasStationReport()
gsc -> gss: setReport()
note right gss:a price list is already attached to the gas station 
gss -> gsr: getById()
gss -> gs: getPriceReportDtos()
note left
get the current price report 
and update its properties
end note
gss -> pr: setUser(user) 
gss -> pr: setReportTimestamp(reportTimestamp) 
gss -> pr: setDieselPrice(dieselPrice)
gss -> pr: setSuperPrice(superPrice)
gss -> pr: setSuperPlusPrice(superPlusPrice)
gss -> pr: setGasPrice(gasPrice)
gss -> pr: setMethanePrice(methanePrice)
gss -> ur: getById()
gss -> user: getReputation()
gss -> gss: computeReputation
gss -> pr: setReputation(reputation)
gss -> gss: saveGasStation()
note left: save the modification back to the db
@enduml
```

## Use case 8.1: Get all the gas stations in a certain area
```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant GasStationRepository as gsr
participant GasStation as gs
participant GasStationConverter as gsconv

u -> gsc : getGasStationsWithCoordinates()
gsc -> gss: getGasStationsWithCoordinates()
note right gss:gasolineType and carSharing are null 
gss -> gss: getGasStationByProximity()
gss -> gsr: findAll()
loop gasStationList.length()
gss -> gs: getLat()
gss -> gs: getLon()
gss -> gss: if(distance > 5km) then: remove from gsList
gss -> gsconv: toGasStationDto(gasStation)
end
note left
[inside getGasStationByProximity()]
discard all the gas stations 
with a distance > 5 km
Convert the object GasStation to GasStationDto
and add it to dtoList
end note
gss -> gsc: return dtoList
gsc -> u: return dtoList
@enduml
```

## Use case 8.2: Get all the gas stations in a certain area filtered by fuel type
```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant GasStationRepository as gsr
participant GasStation as gs
participant GasStationConverter as gsconv
participant GasStationDto as gsd

u -> gsc : getGasStationsWithCoordinates()
gsc -> gss: getGasStationsWithCoordinates()
note right gss:gasolineType is X and carSharing is null 
gss -> gss: getGasStationByProximity()
gss -> gsr: findAll()
loop gasStationList.length()
gss -> gs: getLat()
gss -> gs: getLon()
gss -> gss: if(distance > 5km) then: remove from gsList
gss -> gsconv: toGasStationDto()
end
note left
[inside getGasStationByProximity()]
discard all the gas stations 
with a distance > 5 km
Convert the object GasStation to GasStationDto
and add it to dtoList
end note
loop dtoList.length()
gss -> gsd: hasX()
gss -> gss: if(not hasX) then: remove from dtoList
end
note left
[inside getGasStationsWithCoordinates()]
discard all the gas stations 
that doesn't have gasoline type X
end note
gss -> gsc: return dtoList
gsc -> u: return dtoList
@enduml
```

## Use case 8.3: Get all the gas stations in a certain area filtered by car sharing
```plantuml
@startuml
actor User as u
autonumber 
participant GasStationController as gsc
participant GasStationService as gss
participant GasStationRepository as gsr
participant GasStation as gs
participant GasStationConverter as gsconv
participant GasStationDto as gsd

u -> gsc : getGasStationsWithCoordinates()
gsc -> gss: getGasStationsWithCoordinates()
note right gss:gasolineType is null and carSharing is X
gss -> gss: getGasStationByProximity()
gss -> gsr: findAll()
loop gasStationList.length()
gss -> gs: getLat()
gss -> gs: getLon()
gss -> gss: if(distance > 5km) then: remove from gsList
gss -> gsconv: toGasStationDto(gasStation)
end
note left
[inside getGasStationByProximity()]
discard all the gas stations 
with a distance > 5 km
Convert the object GasStation to GasStationDto
and add it to dtoList
end note
loop dtoList.length()
gss -> gsd: getCarSharing()
gss -> gss: if(carSharing != X) then: remove from dtoList
end
note left
[inside getGasStationsWithCoordinates()]
discard all the gas stations 
that doesn't have a deal with 
the car sharing company X
end note
gss -> gsc: return dtoList
gsc -> u: return dtoList
@enduml
```

## Scenario 10.1: Price is correct
```plantuml
@startuml
actor "User" as U
autonumber 

U -> "GasStationController" : GetGasStationById()
"GasStationController" -> GasStationService : getGasStationById(gasStationId)
GasStationService -> GasStationRepository : FindGasStationById()
GasStationService -> GasStationConverter : toGasStationDto()

U -> UserController : getUserById(userId)
UserController -> UserService : getUserById(userId)
UserService -> UserRepository : FindUserById()
UserService -> UserConverter : toUserDto()

U-> "UserController" : increaseUserReputation(userId)
UserController -> UserService : increaseUserReputation(userId)
UserService -> UserRepository : FindUserById()
UserService -> User : getReputation()
UserService -> User : setReputation()
UserService -> UserRepository : saveUser()
@enduml
```

## Scenario 10.2: Price is wrong
```plantuml
@startuml
actor "User" as U
autonumber 

U -> "GasStationController" : GetGasStationById()
"GasStationController" -> GasStationService : getGasStationById(gasStationId)
GasStationService -> GasStationRepository : FindGasStationById()
GasStationService -> GasStationConverter : toGasStationDto()

U -> UserController : getUserById(userId)
UserController -> UserService : getUserById(userId)
UserService -> UserRepository : FindUserById()
UserService -> UserConverter : toUserDto()

U-> "UserController" : increaseUserReputation(userId)
UserController -> UserService : decreaseUserReputation(userId)
UserService -> UserRepository : FindUserById()
UserService -> User : getReputation()
UserService -> User : setReputation()
UserService -> UserRepository : saveUser()
@enduml
```


