# Rapid Data Processor
This is a test project to
develop a software application to process large volumes of data with 
efficiency and maximum possible speed using multithreading concept.

## Overview
No business can run without data. The volume of data is increasing day
by day along with the demand for the data. It is a very big challenge to
organize and manage the large volume of data and obviously it is very
expensive in terms of technology as well as cost. This project is to
develop a software application for dealing with large volumes of data.
Some important functions like importing large volumes of data, filtering
garbage from the data, validating the data with rules, storing the data
into database, exporting the data into preferable format etc. are
included in the software. The main objective of this project is to
develop software to process large volumes of data with efficiency and
maximum possible speed using multithreading concept.

## Features
-   importing large volumes of data to process from csv or comma
    separated text file.
-   Filtering data using specified condition and logic.
-   Removing duplicate and garbage records from the data.
-   Validating specified fields like phone numbers, emails etc.
    following standard patterns and rules.
-   Storing the valid and invalid data into separate tables in RDBMS
    like SQL Server.
-   Exporting the data into single file and/or into a batch of files.
-   Preparing a consolidated database of well-organized data by
    processing different raw data repeatedly by this application.
-   Processing data with efficiency and maximum possible speed using
    multithreading concept.

## How IT Works
**The Application,**
-   Checks required files and directory tree and path structure is
    available.
-   Checks database availability specified in the properties file.
-   Loads large volumes of data from csv or comma separated text file.
-   Identifies data fields either from the first line of csv file or
    from the field list property.
-   Removes duplicate records considering some all or some fields
    specified in properties.
-   Removes garbage records using specified conditions, rules, and
    logics.
-   Filters data with specified conditions, rules, and logics.
-   Validates all the records according to specified fields like phone
    number, email address etc.
-   Uses this pattern  
    555-555-5555, (555)555-5555, (555) 555-5555, 555 555 5555,
    5555555555, 1 555 555 5555  
    to validate phone number.
-   Uses combination of RFC 5322 Internet Message Standard and most
    popular email application like Gmail, Live and Outlook practiced
    email address pattern to validate the email address.
-   Separate invalid data considering the validation required field.
-   Creates two tables using the name specified in properties file with
    columns from the previously identified fields if the tables don’t
    exist in the database.
-   Stores the valid and invalid data into the two tables accordingly.
-   Exports the valid and invalid data into two separate files.
-   Exports the valid and invalid data into two separate batches of
    files including specified numbers of records.
-   Uses multithreading concept to read data from files, to insert data
    into database, to read data from database and two export data to
    files.

## Deployment & Run the App
Before running the application confirm the Runtime Dependency.

### Runtime Dependencies
-   Java Runtime Environment JRE 8 or later
-   SQL Server 2008R2 or later

### App Execution & Required Paths Tree
```
	AppRdp
	│   AppRdp.jar
	│
	├───config
	│       app.properties
	│
	└───data
		├───in
		│       in.customers.sample.00.txt
		│		
		├───out
		│   └───batch
		└───outDb
			└───batch
```

### Download Executable Jar
-   Download the repository
    <https://github.com/therafique/rapid-data-processor>, from GitHub.
-   Unzip it and you will get AppRdp.zip in
    rapid-data-processor-master/assets/AppDist in your machine.

### Getting Executable Jar
-   Unzip the AppRdp.zip in your favorite location and enter the
    extracted AppRdp folder.
-   Now you are in AppRdp folder, and it is your root or base folder.
    You will find two folders named config & data and one jar file named
    AppRdp.jar

Please configure the app before running it.

**  
**

**Configuration**

-   Look at the **App Execution & Required Paths Tree** carefully. Look
    at the folders and files here.

-   Open AppRdp/config/app.properties file and change the values of
    following properties as your requirement.

**Database connection string**

dbConnStr=jdbc:sqlserver://YourHost\\\\INSTANCE;databaseName=YourDbName;user=YourUserName;password=your_password;encrypt=true;trustServerCertificate=true

This property is for database connection string. Change it as your
environment.

**Data Folder Path**

pathFolderData=

Or

pathFolderData=D:/Work/RapidDataProcessing/AppJar/data

The default value of this property is blank. If you keep it blank, then
the system will set AppRdp/data as the default value of the property.
Keep in mind that this path is relative with respect to app execution
folder AppRdp. Otherwise, you can set any absolute path as its value.

**Input file Path**  
customerDataFileName=in/in.customers.sample.txt

Copy your data file and paste the file in AppRdp/data/in folder. in is a
short form of input.

Change the file name in.customers.sample.txt into the name of your input
data file which you want to process. Otherwise, you can rename your data
file into in.customers.sample.txt. You must keep in mind that
AppJar/data/in is the parent folder of the input file.

Please do not change anything more for basic configuration. Now you are
ready to run the app.

**Run the App**

Open cmd console on AppRdp folder and write below command on the
console.

java -jar AppRdp.jar

Look at the console and give it some time to execute the app. After
completing the program execution. Please check the followings:

-   AppJar/data/out and AppJar/data/outDb folders for your expected
    output file.

-   ProcessedDataValid_01 and ProcessDataInvalid_01 table in your
    database.

**Other Properties**

For other properties, please open the AppRdp/config/app.properties and
read the property and its value. The name of all the property is easily
understandable. Most probably you will understand the meaning of the
property. If you have any confusion, then let me know that.