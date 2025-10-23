Useful when you're remote controling and you try to copy/paste something but it's not available. The keystriker tool will simulate you typing on the keyboard using a file.

Steps to utilize the keystriker tool:
1. Build the jar`mvn clean install`.
2. After building `target` folder will be available, go to the folder `cd target`.
3. Rename the jar for easier usage `mv my_keystriker-1.0-SNAPSHOT-jar-with-dependencies keystriker.jar`.
4. Create a file that has the content you want the key striker tool to simulate you typing it. For example: `input.txt` -> paste inside the content you want to type.
5. Run the jar `java -jar keystriker.jar input.txt` and in 2 seconds focus on the area you want to type in and the tool will simulate the text typing into the focused area.
 
