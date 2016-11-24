# mhealthapp
Andriod app/Python REST API to collect mobile health information

This was written as a computer science/bioinfomatics research project to facilitate the collection of data of health issues including the effects of taking opioid drugs. This is a Proof of Concept app/system and is not in production. 
This file will serve as a space for me to collect information on how I put this system together and may serve as a reference for anyone trying to do something similar.

The app is built to run on a tablet running Android 4.4 or later and the API is written using Python 3.5 and runs the web app framework Flask to act as a RESTful interface between the mobile app and the POSTGRESQL database.

# The Python application and data models and requirements.txt file are located in the folder named "python-files"
The rest of the code is for the mobile application.


# Server Setup:
The server is running on a free-tier Amazon EC2 instance with Ubuntu 16.04 as the operating system.  

After starting up the fresh Ubuntu image issue the following commands:

<code>
sudo apt-get update 
</code>
<br>
<code>
sudo apt-get upgrade
</code>
<br>
<code>
sudo apt-get install python3
</code>
<br>
<code>
sudo apt-get install python3-pip
</code>

# Installing POSTGRES 9.5
These next two commands install the POSTGRESQL server and additional developer libraries

<code>sudo apt-get install postresql-9.5</code><br>
<code>sudo apt-get install postgresql-server-dev-9.5</code>

# Installing Python Libraries
To quickly install all of the required libraries to run the Flask server, use the requirements.txt file as input to pip3 with the following command:

<code>sudo pip3 install –r requirements.txt</code><br>

# Create database and user
Switch to the user "postgres" to create the database and view the contents (or use a GUI tool if your situation supports it! :P)
<br><code>sudo su - postgres</code><br>

<code>createdb yourdbname</code><br>

This will create a user "your_username" who will own the database we just created
<code>createuser --encrypted --login --pwprompt your_username</code><br>

<code>psql</code> is an interactive terminal for the POSTGRESQL database