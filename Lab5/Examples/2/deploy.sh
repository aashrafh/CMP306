#!/bin/bash 
/usr/apache-tomcat/bin/startup.sh 
javac *.java 
sudo cp *.class /usr/apache-tomcat/webapps/ROOT/WEB-INF/classes/
sudo cp *.html /usr/apache-tomcat/webapps/ROOT/