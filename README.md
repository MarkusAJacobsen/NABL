# NABL - Not a bad (time) logger
#### Build status 
master: [![CircleCI](https://circleci.com/gh/MarkusAJacobsen/NABL/tree/master.svg?style=svg)](https://circleci.com/gh/MarkusAJacobsen/NABL/tree/master) develop: [![CircleCI](https://circleci.com/gh/MarkusAJacobsen/NABL/tree/develop.svg?style=svg)](https://circleci.com/gh/MarkusAJacobsen/NABL/tree/develop)
#####  Background: imt3673 (Mobile development course) project. Bases for imt3602 (Professional Programming) assignment. Reports are in the Wiki
##### Project management: https://tree.taiga.io/project/markusja-nabl/

#### Description

There exists a lot of applications made for logging time. However, none of the ones we have tried have suited our need. Our original uses cases was to have a time logging application for sole proprietorships or smaller companies. A company may have projects and clients, and log hours against these. The hours logged must be exportable, as of now this is done by creating a excel sheet, and having this exported via email or cloud storage. 

##### Time logging
After some research into similar applications, we see two primary ways of logging time in an application. There is check-in and check-out, and register after. We are only going to support register after. This means that you will make an entry at the end of the day. We also want the worker be able to include a description of the workday. 

##### User registration
User regustration is done either by email or by google oauth.  

##### Company registration
A company owner needs to add the company to our system. And be able to inspect how many hours spent on each section of the company. This functionality could be in a separate application, as of now we have it in the same. It must be possible for the leader / owner to register time even though the user is also administering the company account. 

##### Exporting time-sheets
We need to be able to export time logged locally as well as time logged in our database. We will anyhow use excel spread-sheets. If time is logged in firebase, the administrator will have the possibility to retrieve all time-sheets from a project for a specified amount of time. 

#### Done
- [x] Create/Delete companies
- [x] Register/see/update/ delete company clients and projects
- [x] Log hours against a company's project/client
- [x] Export timesheet
- [x] Summary
- [x] Email and google login 
- [ ] Hour Log entries getter network calls
- [ ] Add multiple users to a company
- [ ] Create a group or log hour internal 
- [ ] User privilege
- [ ] Invoice generation

#### Building
The application builds using Gradle.

To enable Firebase you have to [add a Firebase project](https://firebase.google.com/docs/android/setup#use_the_firebase_assistant) an download the generated 'google-services.json'. Place it under '/app/'. Note: that document is not meant to be public i.e. every who has that document have full access to your firebase services.

To enable the Google login (email login will work without) you have to add your computers SHA1 fingerprint to [Firebase](https://developers.google.com/android/guides/client-auth?authuser=0)

#### Authors
* Markus Andre Jacobsen
* Ahmed S. M. Madhun
* Martin Klingenberg
