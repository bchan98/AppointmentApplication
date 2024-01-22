Appointment Application.

Authored by Brendan Chan - contact at bchan7@wgu.edu
Written for Software II - Advanced Java Concepts, C195 at Western Governor's University.
Written in IntelliJ IDEA Community Edition 2021.1.3 x64, using JDK Java SE 17.0.1, with JavaFX-SDK-11.0.2, with MySQL Connector Driver 8.0.25

This application generates manages schedules for customers, contacts, and users. This application connects to a MySQL database to create, retrieve, update, and delete entries as required by the user. Appointments can be scheduled on various dates and locations, with various customers, contacts, and users as needed. Appointments must fall within company working hours (8:00 EST to 22:00 EST), and must not have overlap with other appointments involving the same customer. Customers records and appointments can be added, updated, and deleted, with all information being handled by the MySQL database.

To start, users should log in by providing a username and password to login to the system. Once logged in, the user can then proceed to manage customer records and manage appointments. The information required will be prompted by several text boxes and labels to assure ease-of-use.

This application can also generate several reports reflecting information as required by the user. The reports generated include reports on the total amount of appointments, sorted by appointment type and month of appointment, reports on the appointments scheduled for specific contacts, and reports on the amount of appointments created by users, sorted by user and month of creation.
