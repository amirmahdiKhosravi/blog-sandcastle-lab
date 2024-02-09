### Steps to run

1. Open terminal
2. Run ```git clone https://github.com/pranjalcborty/blog-sandcastle.git```
3. ```cd``` into ```blog-sandcastle```
4. Change ```STUDENT_ID``` and ```STUDENT_NUMBER``` in ```Main.java```
5. Compile ```javac -d target -cp postgresql-42.7.1.jar *.java```
6. Run ```java -cp ./target:postgresql-42.7.1.jar:. Main```
