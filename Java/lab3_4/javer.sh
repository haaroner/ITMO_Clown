#!/bin/bash

javac -sourcepath src -d classes $(find src -name "*.java") #2>>errors

cat > Manifest.mf << EOF
Manifest-Version: 1.0
Main-Class: Main
EOF

jar cfm "$1.jar" manifest.mf -C classes .

echo "compiled!"
#java -cp "Pokemon.jar:." Main
java -jar "$1.jar"
