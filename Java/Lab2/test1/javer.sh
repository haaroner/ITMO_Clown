#!/bin/bash



cat > Manifest.mf << EOF
Manifest-Version: 1.0
Main-Class: $1
EOF

javac -cp "Pokemon.jar" *.java

jar cfm "$1.jar" Manifest.mf *.class

java -cp "Pokemon.jar:." Main
#java -jar "$1.jar"
