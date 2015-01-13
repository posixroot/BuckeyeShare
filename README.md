# BuckeyeShare

###Description:
The application provides a platform for hosting files useful to end-users on a centralized server for download. There are two kinds of users: Privilege and Normal. Privilege user can both upload and download files but a normal user can only browse catalogs(of privilege users) and download files.These catalogs are unique to a privilege user and have information on all the files owned by that user and available for download. Essentially catalogs are collections of files like a folder. 
New users will need to register first to login and logout. Privilege Users can create new catalogs and delete existing catalogs. Also, files can be uploaded to existing catalogs/ new catalogs. Files can also be deleted by the user if required.
Changes to the catalogs, as a result of the above mentioned user-activities, will be updated accordingly to all the other users who are viewing/about to view them. 
Privilege users have access to live feed of download activity associated with their files by other users. This download activity is updated (for Privilege Users) whenever a file they own is downloaded by any other user.
The periodical updates of catalogs and download-activity are the two loosely coupled features of the system.  
In short, the application facilitates users to view the catalogs (of all the privileged users) and mediate the transfer of the requested file to the user(both privileged and normal). Also, Privilege Users can monitor the download activity associated with their catalogs.

Points to note:
OSUShare can be deployed using Wildfly 8 Application Server.
OSUShare includes OSUShare-Session-Entity as a project dependency.
OSUShare-Session-Entity has the necessary session and entity beans.

####Summary of Major Use Cases:
1. All users can browse the catalog list and download files they need. Eg. A privilege user (Lecturer at OSU) can upload files to make them available for students to download. 
2. The catalogs could be named after a course-number or topic to make it easier for the user to know what kind of files are within a catalog.
3. Privilege Users can monitor the download activity associated with their catalogs. Eg. If at a later point of time, the lecturer decided to add more content to an existing file, he/she can use the download-activity feature to get a list of students who downloaded the old copy of the file so the lecturer could alert them about any changes being made to the file.

####Important Conifguration options:
Java Compiler Compliance level: 1.7
JSF 2.2 needs to be used.
Wildfly 8 can be used as the application server to host it which has support for JSF 2.2.
