# NABL - Not a bad (time) logger

#####  Background: imt3673 (Mobile development course) project.

#### Original assignement text: (Written by us) 

There exists a lot of applications made for logging time. However, none of the ones we have tried have been any good. We wish to create an application that can export time-loggs in two ways. The first is by exporting the time-logg to an excel spread-sheet that can be passed to a third party application using intent. 
The second, is to register the logged time into firebase. When using firebase, the user would have to register. The user might work for a branch or a project within a company. We also wish to create an interface that are useful for managers that wish to know how much time / resources that are spent on one single project or subdivision of a company. 


#### Time logging
After some research into similar applications, we see two primary ways of logging time in an application. There is check-in and check-out, and register after. We are only going to support register after. This means that you will make an entry at the end of the day. We also want the worker be able to input what he / she  has done that day. 


#### User registration
When a user registers he / she must select a company and possibly a project. Each company must have defined a passkey. This is to prevent spam. The user will also have the option to not register. Then the user will create an excel-file that will be sent to the employer using email or google drive. 

To make a meaningful time-sheet the user would have to input the following parameters regardless of using the app locally or not:
* Name
* Employee number
We must make sure that it is not possible to input an account-number into the employee-number field. We do want to make it possible to transmit sensitive information through our application. 


#### Company registration
A company owner needs to add the company to our system. And be able to inspect how many hours spent on each section of the company. This functionality could be in a separate application however, we will have it in the same application. It must be possible for the leader / owner to register time even though the user is also administering the company account. 


#### Exporting time-sheets
We need to be able to export time logged locally as well as time logged in firebase. We will anyhow use excel spread-sheets. If time is logged in firebase, the administrator will have the possibility to retrieve all time-sheets from a project for a specified amount of time. 
