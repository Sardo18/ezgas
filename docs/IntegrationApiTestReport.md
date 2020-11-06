# Integration and API Test Documentation

Authors:

Date:

Version:

# Contents

- [Dependency graph](#dependency graph)

- [Integration approach](#integration)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 
  
```plantuml
top to bottom direction
package "Controller" {
    class UserController{}
    class GasStationController{}
}

package "Service"{
    class UserService{}
    class GasStationService{}
}

package "Converter" {
    class UserConverter{}
    class GasStationConverter{}
}
package "Repository" {
    class UserRepository{}
    class GasStationRepository{}
}

package "Entity" {
    class User{}
    class GasStation{}
}

package "Dto" {
    class LoginDto{}
    class IdPw{}
    class GasStationDto{}
    class UserDto{}
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
    


   
   ``` 
# Integration approach

   We use a bottom up approach to test the application. 
   - Step 1: classes UserConverter and GasStationConverter
   - Step 2: classes UserConverter, GasStationConverter, UserRepository and GasStationRepository
   - Step 3: classes UserConverter, GasStationConverter, UserRepository, GasStationRepository, UserServiceimpl and GasStationimpl


#  Tests

## Step 1
| Classes  | JUnit test cases |
|--|--|
| UserConverter | UserConverterTest.TestToUserDto  |
| UserConverter | UserConverterTest.TestToUserDto1 |
| UserConverter | UserConverterTest.TestToUserDto2 |
| UserConverter | UserConverterTest.TestToUserDto3 |
| UserConverter | UserConverterTest.TestToUserDto4 |
| UserConverter | UserConverterTest.TestToUser |
| UserConverter | UserConverterTest.TestToUser1 |
| UserConverter | UserConverterTest.TestToUser2 |
| UserConverter | UserConverterTest.TestToUser3 |
| UserConverter | UserConverterTest.TestToUser4 |
| GasStationConverter | GasStationConverterTest.TestToGasStationDto |
| GasStationConverter | GasStationConverterTest.TestToGasStationDto1 |
| GasStationConverter | GasStationConverterTest.TestToGasStationDto2 |
| GasStationConverter | GasStationConverterTest.TestToGasStation |
| GasStationConverter | GasStationConverterTest.TestToGasStation1 |
| GasStationConverter | GasStationConverterTest.TestToGasStation2 |



## Step 2
| Classes  | JUnit test cases |
|--|--|
| UserRepository | UserRepositoryTest.TestFindByEmailPresent |
| UserRepository | UserRepositoryTest.TestFindByEmailNotPresent |
| GasStationRepository | GasStationRepositoryTest.TestFindByLatAndLonPresent |
| GasStationRepository | GasStationRepositoryTest.TestFindByLatAndLonNotPresent |

## Step 3
| Classes  | JUnit test cases |
|--|--|
|UserServiceimpl| TestUserServiceimpl.testGetUserById1 |
|UserServiceimpl| TestUserServiceimpl.testGetUserById2 |
|UserServiceimpl| TestUserServiceimpl.testGetUserById3 | 
|UserServiceimpl| TestUserServiceimpl.testGetUserById4 |
|UserServiceimpl| TestUserServiceimpl.testSaveUser1 |
|UserServiceimpl| TestUserServiceimpl.testSaveUser2 |
|UserServiceimpl| TestUserServiceimpl.testSaveUser3 |
|UserServiceimpl| TestUserServiceimpl.testSaveUser4 |
|UserServiceimpl| TestUserServiceimpl.testSaveUser5 |
|UserServiceimpl| TestUserServiceimpl.testGetAllUsers1 |
|UserServiceimpl| TestUserServiceimpl.testGetAllUsers2 |
|UserServiceimpl| TestUserServiceimpl.testDeleteUser1 |
|UserServiceimpl| TestUserServiceimpl.testDeleteUser2 | 
|UserServiceimpl| TestUserServiceimpl.testDeleteUser3 | 
|UserServiceimpl| TestUserServiceimpl.testDeleteUser4 | 
|UserServiceimpl| TestUserServiceimpl.testLogin1 |
|UserServiceimpl| TestUserServiceimpl.testLogin2 |
|UserServiceimpl| TestUserServiceimpl.testLogin3 |
|UserServiceimpl| TestUserServiceimpl.testLogin4 |
|UserServiceimpl| TestUserServiceimpl.testincreaseUserReputation1 |
|UserServiceimpl| TestUserServiceimpl.testincreaseUserReputation2 |
|UserServiceimpl| TestUserServiceimpl.testIncreaseUserReputation3 |
|UserServiceimpl| TestUserServiceimpl.testIncreaseUserReputation4 | 
|UserServiceimpl| TestUserServiceimpl.testIncreaseUserReputation5 | 
|UserServiceimpl| TestUserServiceimpl.testIncreaseUserReputation6 | 
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation1 |
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation2 |
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation3 | 
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation4 |
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation5 | 
|UserServiceimpl| TestUserServiceimpl.testDecreaseUserReputation6 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetAllGasStations |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetAllGasStations2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationById |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationById2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testSaveGasStation |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testSaveGasStation2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testDeleteGasStation |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testDeleteGasStation2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsByGasolineType |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsByGasolineType2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationByProximity |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationByProximity2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsWithCoordinates |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsWithCoordinates2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsWithoutCoordinates |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationsWithoutCoordinates2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationByCarSharing |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testGetGasStationByCarSharing2 |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testSetReport |	
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability   |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability2  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability3  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability4  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability5  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability6  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability7  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability8  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability9  |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability10 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability11 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability12 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability13 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability14 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability15 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability16 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability17 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability18 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability19 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability20 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability21 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability22 |
|GasStationServiceimpl| EZGasGasStationServiceTest.testEvaluateDependability23 |
	


## Step 4 API Tests


| Classes  | JUnit test cases |
|--|--|
|UserServiceimpl| UserServiceAPITest.testGetUserById1 |
|UserServiceimpl| UserServiceAPITest.testGetUserById2 |
|UserServiceimpl| UserServiceAPITest.testGetUserById3 | 
|UserServiceimpl| UserServiceAPITest.testGetUserById4 |
|UserServiceimpl| UserServiceAPITest.testSaveUser1 |
|UserServiceimpl| UserServiceAPITest.testSaveUser2 |
|UserServiceimpl| UserServiceAPITest.testSaveUser3 |
|UserServiceimpl| UserServiceAPITest.testSaveUser4 |
|UserServiceimpl| UserServiceAPITest.testSaveUser5 |
|UserServiceimpl| UserServiceAPITest.testGetAllUsers1 |
|UserServiceimpl| UserServiceAPITest.testDeleteUser1 |
|UserServiceimpl| UserServiceAPITest.testDeleteUser2 | 
|UserServiceimpl| UserServiceAPITest.testDeleteUser3 | 
|UserServiceimpl| UserServiceAPITest.testDeleteUser4 | 
|UserServiceimpl| UserServiceAPITest.testLogin1 |
|UserServiceimpl| UserServiceAPITest.testLogin2 |
|UserServiceimpl| UserServiceAPITest.testLogin3 |
|UserServiceimpl| UserServiceAPITest.testLogin4 |
|UserServiceimpl| UserServiceAPITest.testincreaseUserReputation1 |
|UserServiceimpl| UserServiceAPITest.testincreaseUserReputation2 |
|UserServiceimpl| UserServiceAPITest.testIncreaseUserReputation3 |
|UserServiceimpl| UserServiceAPITest.testIncreaseUserReputation4 | 
|UserServiceimpl| UserServiceAPITest.testIncreaseUserReputation5 | 
|UserServiceimpl| UserServiceAPITest.testDecreaseUserReputation1 |
|UserServiceimpl| UserServiceAPITest.testDecreaseUserReputation2 |
|UserServiceimpl| UserServiceAPITest.testDecreaseUserReputation3 | 
|UserServiceimpl| UserServiceAPITest.testDecreaseUserReputation4 |
|UserServiceimpl| UserServiceAPITest.testDecreaseUserReputation5 | 





# Scenarios



## Scenario UC4.1

| Scenario        |  Coordinates already exist |
| --------------- |:---------------------:| 
|  Precondition   | Admin A is logged in, Gas Station G doesn't exist, (G.Lat,G.Lon) already exist |
|  Post condition |   |
| Step#  | Description  |
|  1     |  A insert gasStation name |  
|  2     |  A insert the (Lat, Lon)|
|  3     |  A set flag for fuel types and carSharing |
|  4     |  Application doesn't create G | 


# Coverage of Scenarios and FR


| Scenario ID | Functional Requirements covered | JUnit  Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  1          | FR1.1                           | UserServiceimpl.testSaveUser2, UserServiceimpl.testSaveUser3 |             
|  1.1        | FR1.1                           | UserServiceimpl.testSaveUser4 |             
|  2          | FR1.1                           | UserServiceimpl.testSaveUser5 |             
|  3          | FR1.2                           | UserServiceimpl.testDeleteUser3 |             
|  4          | FR3.1                           | GasStationServiceimpl.testSaveGasStation  |             
|  4.1        | FR3.1                           |             |  
|  5          | FR3.1                           |             |            
|  6          | FR3.2                           |             | 
|  7          | FR5.1                           | GasStationServiceimpl.testSetReport2 |
|  7.1        | FR5.1                           |             | 
|  7.2        | FR5.1                           |             | 
|  8          | FR4.1, FR4.2                    | GasStationServiceimpl.testGetGasStationByProximity2 |
|  8.1        | FR4.5                           | GasStationServiceimpl.testGetGasStationsByGasolineType | 
|  8.2        | FR4.5                           | testGetGasStationByCarSharing2 | 
|  9          | FR5.2                           | GasStationServiceimpl.testEvaluateDependability8, GasStationServiceimpl.testEvaluateDependability9, GasStationServiceimpl.testEvaluateDependability10, GasStationServiceimpl.testEvaluateDependability22, GasStationServiceimpl.testEvaluateDependability23  | 
|  10.1       | FR5.3                           | UserServiceimpl.testIncementUserReputation3, UserServiceimpl.testIncementUserReputation4 |
|  10.2       | FR5.3                           | UserServiceimpl.testDecreaseUserReputation3, UserServiceimpl.testDecreaseUserReputation4 | 

# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|                            |           |


