# Face Recognition System

## Description
The aim of this open-ended project is to simulate a biometric user identification and information system in JAVA. CMUA students will be the subject of the system (but its application could be generalized). The system itself will be used by faculty and staff administrators. The primary feature of the system will be its ability to detect and recognise faces using a web camera and automatically open a dashboard of information for the person of interest.

The basic specification for all students is to develop a JavaFX GUI based desktop application that uses a video-camera to detect a person’s face, identify the person and provide useful information and analytics about this person (to the users of the system).

## Demo
![img1](https://github.com/chen-star/Face_Recognition_System/raw/master/img/1.JPG)
![img2](https://github.com/chen-star/Face_Recognition_System/raw/master/img/2.png)
![img3](https://github.com/chen-star/Face_Recognition_System/raw/master/img/3.JPG)

# Main Features
1. Using **OpenCV** to support face detection and recognition

2. For each user, maintaining a record in a **MySQL** database (using **JDBC**). If the user is a first time student then a record needs to be created. The record needs to be updated each time the student visits the reception.

3. The system will present a dashboard to the receptionist containing the following information about the student:
  * The students photo and basic details (Name, Program, etc)
  * When the student last visited reception
  * How many times the students visited reception
  * What was the reason for visit (stapler, tuition fees, complaints, collect assignments, meet people like Nereshnee, Lourdes, other categories)
  * Any other announcements of interest to the student
  
 
4. Finally the system should in the least be able to generate the following analytic reports for the admin staff.
  * Report on students visiting reception by categories for a given date range
  * Report on the frequency of visits to the reception by students grouped by gender and categories for a specific date range.


