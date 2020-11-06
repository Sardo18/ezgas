# Project Estimation  

Authors: Federico Quarta, Victor Seguin, Alessandro Lepori, Silvio Girolami 

Date:29/04/2020

Version: 1.0

# Contents



- [Estimate by product decomposition]
- [Estimate by activity decomposition ]



# Estimation approach

<Consider the EZGas  project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>

# Estimate by product decomposition



### 

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   |             30               |             
|  A = Estimated average size per class, in LOC       |         150 LOC       | 
| S = Estimated size of project, in LOC (= NC * A) |  4500 LOC | 
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  |                  450 ph                  |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 13500€ | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) |      2 calendar weeks           |               


# Estimate by activity decomposition



### 

|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- | 
|**Requirements**  | |
| Define Requirements and Use cases | 20 |
| Write Requirements Document       | 12 |
| Requirements inspection           | 8 |
|**Design**| |
| Define Frontend                   | 20 |
| Define Backend                    | 20 |
| Design inspection                 | 10 |
|**Coding**| |
| Frontend coding                   | 80  |
| Backend coding                    | 120 |
| Code inspection                   | 20  |
|**Unit Testing**| |
| Frontend testing                  | 40 |
| Backend testing                   | 80 |
| Test inspection                   | 20 |



## Gantt Chart

 ```plantuml
printscale daily
[Req&UC] lasts 1 days
[Req Doc] lasts 1 days
[Req Ins] lasts 1 days
[Req Doc] starts after [Req&UC]'s end
[Req Ins] starts after [Req Doc]'s end
[Frontend] lasts 1 days
[Backend] lasts 1 days
[Design Ins] lasts 1 days
[Frontend] starts after [Req Ins]'s end
[Backend] starts after [Frontend]'s end
[Design Ins] starts after [Backend]'s end
[Front Code] lasts 3 days
[Back Code] lasts 4 days
[Code Ins] lasts 1 days
[Front Code] starts after [Design Ins]'s end
[Back Code] starts after [Design Ins]'s end
[Code Ins] starts after [Back Code]'s end
[Code Ins] starts after [Front Code]'s end
[Front test] lasts 2 days
[Back test] lasts 3 days
[Test Ins] lasts 1 days
[Front test] starts after [Design Ins]'s end
[Back test] starts after [Design Ins]'s end
[Test Ins] starts after [Front test]'s end
[Test Ins] starts after [Back test]'s end

 ```