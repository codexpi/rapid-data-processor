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
-   Unzip it and you will get `AppRdp.zip` in
    `rapid-data-processor-master/assets/AppDist` in your machine.

### Getting Executable Jar
-   Unzip the `AppRdp.zip` in your favorite location and enter the
    extracted `AppRdp` folder.
-   Now you are in `AppRdp` folder, and it is your root or base folder.
    You will find two folders named config & data and one jar file named
    `AppRdp.jar`

Please configure the app before running it.

### Configuration
-   Look at the **App Execution & Required Paths Tree** carefully. Look
    at the folders and files here.
-   Open `AppRdp/config/app.properties` file and change the values of
    following properties as your requirement.

### Database connection string
`dbConnStr=jdbc:sqlserver://YourHost\\\\INSTANCE;databaseName=YourDbName;user=YourUserName;password=your_password;encrypt=true;trustServerCertificate=true`

This property is for database connection string. Change it as your
environment.

### Data Folder Path
`pathFolderData=`

Or

`pathFolderData=D:/Work/RapidDataProcessing/AppJar/data`

The default value of this property is blank. If you keep it blank, then
the system will set AppRdp/data as the default value of the property.
Keep in mind that this path is relative with respect to app execution
folder AppRdp. Otherwise, you can set any absolute path as its value.

### Input file Path
`customerDataFileName=in/in.customers.sample.txt`

Copy your data file and paste the file in `AppRdp/data/in` folder. in is a
short form of input.

Change the file name `in.customers.sample.txt` into the name of your input
data file which you want to process. Otherwise, you can rename your data
file into `in.customers.sample.txt`. You must keep in mind that
`AppJar/data/in` is the parent folder of the input file.

Please do not change anything more for basic configuration. Now you are
ready to run the app.

### Run the App

Open cmd console on AppRdp folder and write below command on the
console.

```
java -jar AppRdp.jar
```
Look at the console and give it some time to execute the app. After
completing the program execution. Please check the followings:
-   AppJar/data/out and AppJar/data/outDb folders for your expected
    output file.
-   ProcessedDataValid_01 and ProcessDataInvalid_01 table in your
    database.

## Other Properties
For other properties, please open the AppRdp/config/app.properties and
read the property and its value. The name of all the property is easily
understandable. Most probably you will understand the meaning of the
property. If you have any confusion, then let me know that.

### Properties Table
<table>
<colgroup>
<col style="width: 32%" />
<col style="width: 67%" />
</colgroup>
<thead>
<tr class="header">
<th><strong>Name</strong></th>
<th><strong>Description</strong></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>dbConnStr</td>
<td><p>Database connection string. need to change the value as your requirement. The system will use the value to connect the database.</p>

`dbConnStr=jdbc:sqlserver://YourHost\\INSTANCE;databaseName=YourDbName;user=YourUserName;password=your_password;encrypt=true;trustServerCertificate=true`
</td>
</tr>
<tr class="even">
<td colspan="2"><strong>Locations/Paths Tree</strong></td>
</tr>
<tr class="odd">
<td>pathFolderData</td>
<td><p>Data folder path for input and output file. Default value is blank. If it is blank, then the app will consider that data folder exists in app jar location. Otherwise, you can set any location for the data folder.</p>

`pathFolderData=`
<p>or</p>

`pathFolderData=D:/Work/OrangeToolz/AppJar/data`
</td>
</tr>
<tr class="even">
<td>customerDataFileName</td>
<td><p>Input date file name.</p>
<p>customerDataFileName=in/in.customers.sample.txt</p></td>
</tr>
<tr class="odd">
<td>customerDataOutFileValid</td>
<td><p>Output file name for valid data.</p>
<p>customerDataOutFileValid=out/out.valid.txt</p></td>
</tr>
<tr class="even">
<td>customerDataOutFileInvalid</td>
<td><p>Output file name for invalid data.</p>
<p>customerDataOutFileInvalid=out/out.invalid.txt</p></td>
</tr>
<tr class="odd">
<td>customerDataOutBatch</td>
<td><p>Batch folder path for current input data.</p>
<p>customerDataOutBatch=out/batch</p></td>
</tr>
<tr class="even">
<td colspan="2"><strong>Threads</strong></td>
</tr>
<tr class="odd">
<td>noOfMaxLinesPerBatchFile</td>
<td><p>Maximum numbers of lines in a file in batch.</p>
<p>noOfMaxLinesPerBatchFile=5000</p></td>
</tr>
<tr class="even">
<td>threadCountMax</td>
<td><p>Maximum thread count. The default value is 64. If the number of execution blocks &gt;= 1000 (threadCountLimit) then, the system will execute all the execution blocks using 64 threads.</p>
<p>threadCountMax=64</p></td>
</tr>
<tr class="odd">
<td>threadCountMin</td>
<td><p>Minimum thread count.</p>
<p>threadCountMin=1</p></td>
</tr>
<tr class="even">
<td>threadCountLimit</td>
<td><p>Thread count limit. The default value is 1000. If number of execution blocks &gt;= 1000 then number of threads will be 64 (threadCountMax). If the number of execution block &lt; 1000 than thread count will be 1 (threadCountMin) for every 100 (threadCountFactor) execution block.</p>
<p>threadCountLimit=1000</p></td>
</tr>
<tr class="odd">
<td>threadCountFactor</td>
<td><p>thread count factor. See thread count limit for detail.</p>
<p>threadCountFactor=100</p></td>
</tr>
</tbody>
</table>

<table>
<colgroup>
<col style="width: 32%" />
<col style="width: 67%" />
</colgroup>
<thead>
<tr class="header">
<th colspan="2"><strong>Database Tables</strong></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>tableColumnList</td>
<td><p>Column list for input data. If the first line of input data file does not contain columns name, then system will consider the property value as columns name list.</p>
<p>tableColumnList=[firstName],[lastName],[city],[state],[zip],[phone],[email],[ip]</p></td>
</tr>
<tr class="even">
<td>tableNameValidCustomer</td>
<td><p>Table name for the valid customer. The system will create a table for valid data using this name.</p>
<p>tableNameValidCustomer=ProcessedDataValid_01</p></td>
</tr>
<tr class="odd">
<td>tableNameInvalidCustomer</td>
<td><p>Table name for the invalid customer. The system will create a table for valid data using this name.</p>
<p>tableNameInvalidCustomer=ProcessedDataValid_01</p></td>
</tr>
<tr class="even">
<td>tableNeedToDeleteBeforeInsert</td>
<td><p>Hello</p>
<p>tableNeedToDeleteBeforeInsert=true</p></td>
</tr>
<tr class="odd">
<td>checkItemExistsBeforeInsert</td>
<td><p>Hello</p>
<p>checkItemExistsBeforeInsert=false</p></td>
</tr>
<tr class="even">
<td colspan="2"><strong>Data Export</strong></td>
</tr>
<tr class="odd">
<td>exportFromDbRequired</td>
<td><p>If the value is true, the from database will be exported. Otherwise, will not be.</p>
<p>exportFromDbRequired=true</p></td>
</tr>
<tr class="even">
<td>exportPathValidData</td>
<td><p>File name to export valid data.</p>

`exportPathValidData=outDb/exp.valid.txt`

</td>
</tr>
<tr class="odd">
<td>exportPathInvalidData</td>
<td><p>File name to export invalid data.</p>

`exportPathInvalidData=outDb/exp.invalid.txt`
</td>
</tr>
<tr class="even">
<td>exportPathBatch</td>
<td><p>Output path to export files in a batch.</p>
<p>exportPathBatch=outDb/batch</p></td>
</tr>
<tr class="odd">
<td colspan="2"><strong>Validation</strong></td>
</tr>
<tr class="even">
<td>columnIndexToValidatePhone</td>
<td><p>Column index to validate phone.</p>
<p>columnIndexToValidatePhone=5</p></td>
</tr>
<tr class="odd">
<td>columnIndexToValidateEmail</td>
<td><p>Column index to validate email.</p>
<p>columnIndexToValidateEmail=6</p></td>
</tr>
<tr class="even">
<td colspan="2"><strong>Flag</strong></td>
</tr>
<tr class="odd">
<td>flagForValid</td>
<td><p>Flag for valid. This flag will be used to name anything related to valid something. E.g.: if the value of the property is valid then a file name may be out.valid.001.txt</p>
<p>flagForValid=valid</p></td>
</tr>
<tr class="even">
<td>flagForInvalid</td>
<td><p>Flag for valid. This flag will be used to name anything related to invalid something. E.g.: if the value of the property is invalid then a file name may be out.batch.invalid.001.txt</p>
<p>flagForInvalid=invalid</p></td>
</tr>
</tbody>
</table>

## Development Dependencies
-   Any Java Development IDE
-   JDK 8 or later
-   SQL Express 2008R2 or later
-   Apache Maven 3.8.3 or later