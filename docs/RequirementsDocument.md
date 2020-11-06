# Requirements Document 

Authors:
- SEGUIN Victor
- LEPORI Alessandro
- QUARTA Federico
- GIROLAMI Silvio

Date: 13/04/2020

Version: 1.0

# Contents

- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Requirements Document](#requirements-document)
- [Contents](#contents)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	- [Context Diagram](#context-diagram)
	- [Interfaces](#interfaces)
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	- [Functional Requirements](#functional-requirements)
	- [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	- [Use case diagram](#use-case-diagram)
	- [Use Cases](#use-cases)
		- [Use case 1, UC1 - FR1  Log in](#use-case-1-uc1---fr1-log-in)
		- [Use case 2, UC2 - FR2 Log out](#use-case-2-uc2---fr2-log-out)
		- [Use case 3, UC3 - FR3 Sign up](#use-case-3-uc3---fr3-sign-up)
		- [Use case 4, UC4 - FR4 Continue without login](#use-case-4-uc4---fr4-continue-without-login)
		- [Use case 5, UC5 - FR5, FR5.1 Show map with gas stations](#use-case-5-uc5---fr5-fr51-show-map-with-gas-stations)
		- [Use case 6, UC6 - FR6 Interact with the map](#use-case-6-uc6---fr6-interact-with-the-map)
		- [Use case 7, UC7 - FR6.1, FR6.2 Resize the map and Move the map](#use-case-7-uc7---fr61-fr62-resize-the-map-and-move-the-map)
		- [Use case 8, UC8 - FR6.3 Select a gas station](#use-case-8-uc8---fr63-select-a-gas-station)
		- [Use case 9, UC9 - FR7.1 Add a new gas station](#use-case-9-uc9---fr71-add-a-new-gas-station)
		- [Use case 10, UC10 - FR7.2 Ask to remove a gas station](#use-case-10-uc10---fr72-ask-to-remove-a-gas-station)
		- [Use case 11, UC11 - FR7.3 Update information](#use-case-11-uc11---fr73-update-information)
		- [Use case 12, UC12 - FR7.4 Leave a feedback](#use-case-12-uc12---fr74-leave-a-feedback)
		- [Use case 13, UC13 - FR8 Compute the route](#use-case-13-uc13---fr8-compute-the-route)
		- [Use case 14, UC14 - FR9, FR9.1, FR9.2, FR9.3 List gas station and sort](#use-case-14-uc14---fr9-fr91-fr92-fr93-list-gas-station-and-sort)
		- [Use case 15, UC15 - FR9.4, FR9.5, FR9.6 Filter list](#use-case-15-uc15---fr94-fr95-fr96-filter-list)
		- [Use case 16, UC16 - FR10 Center map on the user location](#use-case-16-uc16---fr10-center-map-on-the-user-location)
		- [Use case 17, UC17 - FR11.1 change password](#use-case-17-uc17---fr111-change-password)
		- [Use case 18, UC16 - FR11.2 change username](#use-case-18-uc16---fr112-change-username)
		- [Use case 19, UC19 - FR11.3 delete account](#use-case-19-uc19---fr113-delete-account)
		- [Use case 20, UC20 - FR12 remove a gas station](#use-case-20-uc20---fr12-remove-a-gas-station)
- [Relevant Scenarios](#relevant-scenarios)
	- [Scenario 1](#scenario-1)
	- [Scenario 2](#scenario-2)
	- [Scenario 3](#scenario-3)
	- [Scenario 4.1](#scenario-41)
	- [Scenario 4.2](#scenario-42)
	- [Scenario 5](#scenario-5)
	- [Scenario 6](#scenario-6)
	- [Scenario 7](#scenario-7)
	- [Scenario 8](#scenario-8)
- [Glossary](#glossary)
- [System Design](#system-design)
- [Deployment Diagram](#deployment-diagram)


# Stakeholders

| Stakeholder name  | Description | 
| ----------------- |:-----------:|
| User 				| Uses the application to find the gas station with best prices in his area or the nearest one | 
| Developer			| Deals with the development and maintenance of the application |
| Administrator		| Administrators check that the application works properly|
| Google Maps 		| Provides geolocation services | 
| Database			| Where all the information about users and gas stations are stored |

# Context Diagram and interfaces

## Context Diagram

```plantuml
left to right direction
actor User as u
actor "Registered User" as ru
actor Administrator as a
actor "Google Maps" as gm
actor Database as db

u -- (EZGas)
ru -- (EZGas)
a -- (EZGas)
u <- ru
(EZGas) -- gm
(EZGas) -- db
```

## Interfaces
| Actor | Logical Interface | Physical Interface  |
| ------------- |:---------:| -----:|
| User			| GUI 		| Screen, keyboard		|
| Administrator	| GUI 		| Screen, keyboard		|
| Google Maps	| API 		| Internet connection 	|
| Database 		| GUI, API 	| Screen, keyboard 		|

# Stories and personas
Laura is a 38 years old energy engineer of a company, she's single and loves to spend his holidays making long journeys by car and therefore she often needs to refuel her car in a gas station. She really loves coffee so she always trys to stop in gas stations where there is also a bar: in order to know which is the nearest gas station that also has a bar, she opens the EZgas application and looks for the desired station in the list of all the stations located in the selected range. She chooses to order the list by distance and filter it by fuel type and by bar availability. Then she chooses the one she likes the most, looking at its feedbacks, and the application shows her the route to get to that station. 

Andy is 40 years old, he has a wife and two young children. His family owns only one car which is used by him and his wife mainly to go on errands around the city. Andy is in charge of refueling, so every Sunday he goes to the usual gas station. Every time he notices that the price of the fuel has changed since the last time, he opens EZGas and updates the prices on the application to let other users know about the change.

Giorgio's 50 years old and works at the post office. Its duty is to deliver packeges and letters around the city with the post office van. Every day, when the last packege of the morning is delivered, he has to refuel the van for the afternoon and the post office asked him to spend as less as possible. So when the moment comes, he opens the EZgas application and checks on the list of gas stations around him which is the cheapest, ordering the list by prices. When he get to the station he stops there to enjoy is homemade lunch during his break and before leaving the place, he always leaves a feedback on the application, giving a rating to the gas station, so he can help other user to choose where to stop.

Jacob is 20 years old, he is a student and lives close to his university so he is used to take his bike to get there and also to get to the city center to see his friends. Rarely he has to take his mother's car, for exemple when his girlfriend wants to go out of the city for a trip: everytime he does so his mother asks him to refuel her car before bringing it back to her. Before getting back home Jacob opens the EZgas application, which he uses only in this case so he hasn't an account, so enters it without logging in. He's not interested in leaving feedbacks or reading it, he just wants an application that tells him which his the cheapest gas station along the his way back home and he doesn't need an account to have this information.

# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |:-------------:| 
|  FR1     | Log in |  
|  FR2     | Log out |
|  FR3     | Sign up |
|  FR4     | Continue without login |
|  FR5     | Show the map |
|   FR5.1  |  Show the map with gas stations |
|  FR6     | Interact with the map |  
|   FR6.1  |  Resize the map |
|   FR6.2  |  Move the map |
|   FR6.3  |  Select a gas station |
|  FR7     | Manage a gas station |
|   FR7.1  |  Add a new gas station |
|   FR7.2  |  Ask to remove a gas station |
|   FR7.3  |  Update information for a certain gas station |
|   FR7.4  |  Leave a feedback to a certain gas station |
|  FR8	   | Compute the route to the selected gas station |
|  FR9     | List the gas stations |
|   FR9.1  |  Sort list by price |
|   FR9.2  |  Sort list by distance |
|   FR9.3  |  Sort list by rating |
|   FR9.4  |  Filter by fuel type |
|   FR9.5  |  Filter by payment |
|   FR9.6  |  Filter by bar availability |
|  FR10    | Center map on user location |
|  FR11    | Manage account |
|  FR11.1  |  Change password |
|  FR11.2  |  Change username |
|  FR11.3  |  Delete an account |
|  FR12    | Remove a gas station |
<!--- Only the administrator can remove a gas station(FR12).
We will make it clear in the use case diagram---> 


## Non Functional Requirements

| ID        | Type (efficiency, reliability, .. see iso 9126)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Portability   | Application runs on Android (v4.4+), iOS(v9+), Chrome(79+), Firefox(57+), Edge(80+), Safari(13+)| All FR |
|  NFR2     | Efficency     | All functions should complete in < 0.5 sec  | All FR |
|  NFR3     | Efficency     | Map should be rendered in < 1 sec  | FR5 | 
|  NFR4     | Usability     | Application should be used with no training  | All FR |
|  NFR5		| Localisation  | The prices are expressed in euro | All FR |
|  NFR6     | Reliability   | Application should be available 99% of times | ALL FR|


# Use case diagram and use cases


## Use case diagram
```plantuml
left to right direction
actor User as u
actor GoogleMaps as gm
actor RegisteredUser as ru
actor Administrator as a
actor Database as db

usecase "FR1 log in" as login
usecase "FR2 log out" as logout
usecase "FR3 sign up" as signup
usecase "FR4 continue without login" as nologin
usecase "FR5 show the map" as showMap
usecase "FR5.1 show the map with gas station" as showGs
usecase "FR6 interact with the map" as interact
usecase "FR6.1 resize the map" as resize
usecase "FR6.2 move the map" as move
usecase "FR6.3 select a gas station" as selectGs
usecase "FR7 manage a gas station" as manageGs
usecase "FR7.1 add a new gas station" as addGs
usecase "FR7.2 ask to remove a gas station" as askRemoveGs
usecase "FR7.3 update information" as updateGs
usecase "FR7.4 leave a feedback" as feedback
usecase "FR8 compute route" as route
usecase "FR9 list gas stations\n--\n**extension points**\ninteract with the map" as list
usecase "FR9.1 sort by price" as byPrice
usecase "FR9.2 sort by distance" as byDistance
usecase "FR9.3 sort by rating" as byRating
usecase "FR9.4 filter by fuel type" as byFuel
usecase "FR9.5 filter by payment" as byPayment
usecase "FR9.6 filter by bar availability" as bar
usecase "FR10 center map on user location" as location
usecase "FR11 manage account" as manageAccount
usecase "FR11.1 change password" as changePassword
usecase "FR11.2 change username" as changeUsername
usecase "FR11.3 delete an account" as deleteAccount
usecase "FR12 remove a gas station" as removeGs

u -- signup
u -- nologin
u -- location
u -- interact
u -- route
u -- list
u <|- ru

ru ---- login
ru ---- logout
ru --- manageGs
ru --- manageAccount

removeGs <-- a
askRemoveGs --> a

showMap ---> gm
route ---> gm

location ..> interact : <<include>>

showMap ..> showGs : <<include>>

interact ..> resize : <<include>>
interact ..> move : <<include>>
interact ..> selectGs : <<include>>
interact ..> showMap : <<include>>
interact ..> list : <<extend>>

manageGs ..> addGs : <<include>>
manageGs ..> askRemoveGs : <<include>>
manageGs ..> updateGs : <<include>>
manageGs ..> feedback : <<include>>

interact <. manageGs : <<include>>


list ..> byPrice : <<include>>
list ..> byDistance : <<include>>
list ..> byRating : <<include>>
list ..> byFuel : <<include>>
list ..> byPayment : <<include>>
list ..> bar : <<include>>

manageAccount ..> changePassword : <<include>>
manageAccount ..> changeUsername : <<include>>
manageAccount ..> deleteAccount : <<include>>


route .> interact : <<include>>

feedback ---> db
addGs ---> db
updateGs ---> db
removeGs ---> db
login ---> db
showGs --> db
changePassword ---> db
changeUsername ---> db
deleteAccount ---> db
list --> db
signup --> db
selectGs --> db

'alignment
manageGs -[hidden] changePassword
manageGs -[hidden] changeUsername
logout -[hidden]--> db
login -[hidden] logout
manageGs -[hidden] manageAccount
deleteAccount -[hidden] login
changePassword -[hidden] changeUsername
list -[hidden] signup
nologin -[hidden] location
nologin -[hidden] interact
showMap -[hidden] resize
resize -[hidden] move
list -[hidden] selectGs
selectGs -[hidden] list
```

## Use Cases

### Use case 1, UC1 - FR1  Log in

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU has not logged in yet, username RU.U is valid, password RU.P is valid |  
|  Post condition     | Registered User RU is logged in the application |
|  Nominal Scenario   | Registered User RU enters username RU.U and password RU.P. The application validates and confirms |
|  Variants           | Username U or password P is not valid. User is not logged in the application. User asks to recover his credentials |

### Use case 2, UC2 - FR2 Log out

| Actors Involved     | Registered User |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in |  
|  Post condition     | Registered User RU is no longer logged in the application |
|  Nominal Scenario   | Registered User RU wants to quit the application and logs out |

### Use case 3, UC3 - FR3 Sign up

| Actors Involved     | User, Database |
| -------------       |:-------------:| 
|  Precondition       | Email E is not used by another Registered User, username U is not used by another Registered User, password P contains at least 8 characters |  
|  Post condition     | User becomes a Registered User RU |
|  Nominal Scenario   | User enters the email E, the username U and the password P. The application validates, confirms and adds the Registered User RU into the database |

### Use case 4, UC4 - FR4 Continue without login

| Actors Involved     | User |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | User skips the login and uses the application as normal User so he can access to a reduced set of functionalities |

### Use case 5, UC5 - FR5, FR5.1 Show map with gas stations

| Actors Involved     | Google Maps, Database |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | Application uses the Google Maps API and the Database to retrieve and display the gas stations near the user location. All the gas stations displayed are in a range of 5 Km |

### Use case 6, UC6 - FR6 Interact with the map

| Actors Involved     | User |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | User interacts with the map to perform some actions: resizing, movement, selection of a gas station |

### Use case 7, UC7 - FR6.1, FR6.2 Resize the map and Move the map

| Actors Involved     | User |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | User zooms in and zooms out the map to determine the range of the area shown by the map. User moves the map to see other areas |

### Use case 8, UC8 - FR6.3 Select a gas station

| Actors Involved     | User, Database |
| -------------       |:-------------:| 
|  Precondition       | Gas station GS exists in the database |  
|  Post condition     | Information of the gas station GS are shown |
|  Nominal Scenario   | User selects the gas station GS on the map. The application displays the information of the gas station GS |

### Use case 9, UC9 - FR7.1 Add a new gas station

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, gas station GS does not exist in the database |  
|  Post condition     | Gas station GS exists in the database |
|  Nominal Scenario   | Registered User RU selects the location of the gas station GS on the map and enters the name and the fuel prices of the gas station GS. Application adds the new gas station GS |
|  Variants		      | The Registered User RU adds optional information (bar, method of payment) |

### Use case 10, UC10 - FR7.2 Ask to remove a gas station

| Actors Involved     | Registered User, Administrator |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, gas station GS exists in the database |  
|  Post condition     | Message M is sent to the Administrator A and GS.signalization++ |
|  Nominal Scenario   | Registered User RU selects the gas station GS to be removed. Application sends message M to the Administrator A and updates GS.signalization |

### Use case 11, UC11 - FR7.3 Update information

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, gas station GS exists in the database |  
|  Post condition     | New information are stored in database |
|  Nominal Scenario   | Registered User RU selects the gas station GS on the map and enters the new information. User can change only fuel prices and optional information (bar, payment methods). Application validates and updates the information of the gas station GS |

### Use case 12, UC12 - FR7.4 Leave a feedback

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, gas station GS exists in the database, 1 <= feedback.rating F.R <= 5 |  
|  Post condition     | GS.avarageRating = new_averagerating and GS.addFeedback(F) and  RU.addFeedback(F) |
|  Nominal Scenario   | Registered User RU selects the gas station GS, enters a comment and a rating F.R. The application stores the feedback F into the database |

### Use case 13, UC13 - FR8 Compute the route

| Actors Involved     | User, Google maps |
| -------------       |:-------------:| 
|  Precondition       | Gas station GS exists in the database |  
|  Post condition     | Route shown on the map |
|  Nominal Scenario   | User U selects the gas station GS. The application uses the Google Maps API to compute the best route to the gas station GS and displays it on the map |

### Use case 14, UC14 - FR9, FR9.1, FR9.2, FR9.3 List gas station and sort 

| Actors Involved     | User, Database |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | The list of gas stations is displayed. User U choose to sort it by price, distance or rating |
|  Variants 		  | User U keeps the default sorting value: sorting by distance |

### Use case 15, UC15 - FR9.4, FR9.5, FR9.6 Filter list

| Actors Involved     | User |
| -------------       |:-------------:| 
|  Precondition       | Fuel types FT, payment methods PM exist in the database |  
|  Post condition     | Filtered list is shown on the screen |
|  Nominal Scenario   | User U selects fuel types FT and/or payment methods PM and/or bar availability B. Application lists only the gas stations that provide all the filters selected |

### Use case 16, UC16 - FR10 Center map on the user location

| Actors Involved     | User |
| -------------       |:-------------:| 
|  Precondition       | |  
|  Post condition     | |
|  Nominal Scenario   | Application centers the map on the user location |

### Use case 17, UC17 - FR11.1 change password

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, RU insert the right current password RU.CP and the new password NP contains at least 8 characters |  
|  Post condition     | RU.CP = NP |
|  Nominal Scenario   | Registered User RU enters the current password RU.CP and the new password NP. Application replaces the old password RU.CP with the new password NP in the database |

### Use case 18, UC16 - FR11.2 change username

| Actors Involved     | Registered User, Database|
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in, the new username NU is not used by a Registered User |  
|  Post condition     | RU.username = NU |
|  Nominal Scenario   | Registered User RU enters the new username NU and the application replaces the old username of RU with the NU in the database |

### Use case 19, UC19 - FR11.3 delete account

| Actors Involved     | Registered User, Database |
| -------------       |:-------------:| 
|  Precondition       | Registered User RU is logged in the application |  
|  Post condition     | Registered User RU is deleted from the database |
|  Nominal Scenario   | Registered User RU deletes his account and the application deletes it from the database |

### Use case 20, UC20 - FR12 remove a gas station

| Actors Involved     | Administrator, Database  |
| -------------       |:-------------:| 
|  Precondition       | Messages M and gas station GS exist in the database |  
|  Post condition     | Gas station GS is deleted from the database |
|  Nominal Scenario   | Administrator A receives messages from Registered Users that ask to remove gas station GS. When the number of messages for the same GS reaches 5, administrator A receives a notification and removes the gas station GS |
|  Variants			  | Administrator A decides to keep the gas station GS |

# Relevant Scenarios 

## Scenario 1

| Scenario ID: SC1    | Corresponds to UC9 |
| ------------- 	  |:-------------| 
| Description 		  | Registered User (RU) is adding a new GS, Registered User is logged in |
| Precondition 		  | GS does not already exist, There is no GS at this location |
| Postcondition 	  | New GS is created |
| Step# | Step description |
| 1     | RU selects the button Create New GS |  
| 2     | RU selects the location of the new GS |  
| 3     | RU chooses the name of the GS |  
| 4     | RU inserts the type of fuel |  
| 5     | RU inserts the price |
| 6     | RU selects the type of payment |
| 7     | RU selects if there is a bar |
| 8     | RU selects upload button |


## Scenario 2

| Scenario ID: SC2     | Corresponds to UC14 |
| ------------- 	   |:-------------| 
| Description 		   | Registered User (RU) is updating a Gas Station (GS) |
| Precondition 		   | GS exists, RU is logged in |
| Postcondition 	   | New Information about GS posted |
| Step# | Step description |
| 1     | RU selects an existing GS |  
| 2     | RU selects the Update GS Button |  
| 3     | RU chooses the type of fuel to update |  
| 4     | RU types the new price |
| 5     | RU selects Upload Button |

## Scenario 3

| Scenario ID: SC3     | Corresponds to UC10 |
| ------------- 	   |:-------------| 
| Description 		   | Registered User RU asks to remove the gas station GS |
| Precondition 		   | RU is logged in, GS exists in the application |
| Postcondition 	   | Message M is sent to the Administrator and GS.signalization++ |
| Step#  | Step description |
|  1     | RU selects GS on the map |  
|  2     | RU asks to remove GS|
|  3	 | Application sends a message M to the Administrator with the GS location and increase GS.signalization |

## Scenario 4.1

| Scenario ID: SC4.1   | Corresponds to UC20 |
| ------------- 	   |:-------------| 
| Description 		   | Administrator remove gas station GS |
| Precondition 		   | Messages M.GS==GS, GS exists |
| Postcondition		   | GS does not exist in the application |
| Step#  | Step description |
|  1     | Administrator A receives a notification about GS |  
|  2     | A decides to delete GS |
|  3     | The application removes GS from the map and delete all the information about GS |

## Scenario 4.2

| Scenario ID: SC4.2   | Corresponds to UC20 |
| ------------- 	   |:-------------| 
| Description 		   | Administrator keeps gas station GS |
| Precondition 		   | Messages M.GS==GS, GS exists in the application |
| Postcondition 	   | GS exists in the application |
| Step#  | Step description |
|  1     | Administrator A receives a notification about GS |  
|  2     | A decides to keep GS |
|  3     | The application removes all messages M about GS and GS.signalization=0 |

## Scenario 5

| Scenario ID: SC5     | Corresponds to UC14 |
| ------------- 	   |:-------------| 
| Description 		   | Application shows the list of the gas stations sorted by rating |
| Precondition 		   | distance(GS.location, user.location) <= 5 km |
| Postcondition 	   | |
| Step#  | Step description |  
|  1     | User U opens the list of gas stations |
|  2     | U chooses to sort by rating |
|  3     | Application shows the sorted list |


## Scenario 6

| Scenario ID: SC6     | Corresponds to UC15 |
| ------------- 	   |:-------------| 
| Description 		   | User U filters the list of gas stations by fuel type and payment method |
| Precondition 		   | Fuel type FT and payment method PM exist in the application |
| Postcondition 	   | |
| Step#  | Step description |
|  1     | U opens the list of gas stations |
|  2     | U chooses to filter the list by FT |  
|  3     | U chooses to filter the list by PM | 
|  4     | Application lists only the gas stations that provide all the filters selected |

## Scenario 7

| Scenario ID: SC7     | Corresponds to UC6,UC7,UC16 |
| ------------- 	   |:-------------| 
| Description 		   | User U wants to see on the Map M the location of all the gas stations of his city |
| Precondition 		   | U opens the application |
| Postcondition		   | |
| Step#  | Step description |
|  1     | U opens the application and M is shown on the screen. M is centered in U.location and shows the area around U whose range is 5 Km |
|  2     | U uses 2 fingers to resize M, in order to change the value of the range of the area shown on the screen |  
|  3     | U user 1 finger to move M in any direction, in order to change the center of M and display differet areas of the city | 
|  4     | U uses the "My location" button and M is centered on U.location and shows the area around U whose range is 5 Km again |

## Scenario 8

| Scenario ID: SC8     | Corresponds to UC12 |
| ------------- 	   |:-------------| 
| Description 		   | Registered User RU wants to leave a Feedback F witha Rating R on a Gas Station GS |
| Precondition 		   | RU is logged in && GS exists |
| Postcondition		   | U.addFeedback(F) && GS.addFeedback(F) && 1<= R <= 5 |
| Step#  | Step description |
|  1     | U selects GS |
|  2     | U presses the "Leave a feedback" button |
|  3     | U inserts a comment and a rating about his experience at GS |  
|  4     | F is added to the Database and linked both to GS and U | 

# Glossary

```plantuml
class EZGas

class Administrator {
}

class User {
}

class RegisteredUser {

}

class GasStation {
+ name
+ bar:{yes, no, undefined}
+ signalization
+ avaragerating
}

class Location{
+ longitude
+ latitude
}

class Fuel {
+ type
}

class Supply {
+ price
}

class Feedback {
+ date
+ rating
+ comment
}

class Payment {
+ method
}

class Message {
+ date
}

class Account {
	+ email
	+ username
	- password
}

EZGas -[hidden]-- "*" GasStation
EZGas --- "*" GasStation
EZGas -[hidden]-- "*" GasStation

EZGas -- "*" User
EZGas --- Administrator

User <|--- RegisteredUser

Location -  User
Location  -- GasStation

GasStation --"*"  Payment
GasStation "*" - "*" Fuel : supply >
Supply  .. (GasStation, Fuel)

GasStation "0..*" ---  RegisteredUser: < add 
GasStation "0..*" -- RegisteredUser : < update

GasStation -- "0..*" Feedback
Feedback  "0..*" --  RegisteredUser : < leave 

Administrator - "0..*" GasStation: > remove
Administrator   -- "0..*" Message : > receive
Message  "0..*" -- RegisteredUser: < send

Administrator -- Account
Account  -- RegisteredUser

'alignment
Account  -[hidden] Message
Location -[hidden]- Fuel
User -[hidden]- GasStation
```
# System Design
```plantuml
	class EzGas {
	}

	class EzGasiOsApp {
	}
	
	class EzGasAndroidApp{
	}

	class EzGasServer{
	}

	EzGas o-- EzGasiOsApp
	EzGas o-- EzGasAndroidApp
	EzGas o-- EzGasServer
```
# Deployment Diagram 

```plantuml
left to right direction
artifact "EZGas Application" as app
artifact "EZGas Application" as app2
node "Personal Computer" as pc
node "Smartphone" as sm
node "Server" as apps{
	node "Application Server" as APS
	node "Web Server"as WS
	} 
node "Database" as db

apps -- pc
apps -- sm
pc -- app
sm -- app2
db -- apps

```
