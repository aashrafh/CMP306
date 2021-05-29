#!/bin/bash 
sudo /opt/apache-tomcat-10/bin/startup.sh
javac *.java 
sudo cp *.class /usr/apache-tomcat-10/webapps/ROOT/WEB-INF/classes/
sudo cp *.html /usr/apache-tomcat-10/webapps/ROOT/