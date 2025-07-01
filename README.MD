# Welcome to Txt File Parser Application
The application is created for parsing txt files into various files by data types
## How to run
1. Clone the repository to any convenient location on your computer using the command:
```bash
git clone git@git.shpy.ru:ShpyMakeLove/demo_txt_file_parser.git
```
2. After that you need to add **maven-jar-plugin** for command line jar launching.
To install **maven-jar-plugin** you need to run the command
For Deb:
```bash
sudo apt instal maven-jar-plugin -y 
```
For RPM:
```bash
sudo dnf instal maven-jar-plugin -y 
```
3. Build jar-file
cd into project directory, then run:
```bash
mvn clean package
```
4. Run
To run the program you must execute the command
```bash
java -jar target/txt_file_parser-1.0-SNAPSHOT.jar
```
**WARNING**, if you want to run the program, you must prepare txt files in advance and pass the paths to the files to the launch parameters
Below are the launch parameters with examples:
-p is the prefix of the output file names
-o - Custom path for file output
-a - Mode for adding to existing files
-s - Short statistics (number of items recorded in outgoing files)
-f - Full statistics (For numbers: minimum quantity, maximum quantity, average, sum of elements.
For strings: number of strings, shortest string and longest.)

## EXAMPLE
```bash
java -jar target/txt_file_parser-1.0-SNAPSHOT.jar -p hello- -o /home/user/abc/ -a -s -f input_files/test1.txt input_files/test2.txt input_files/test3.txt
```

## Dependencies of project:
- Apache Commons IO -> 2.19.0
- Lombok -> 1.18.38
- Jansi -> 2.4.2
- Spring Boot Starter Test -> 3.5.3
- Java -> 17
- Project Assembler -> Maven -> 3.6.3

## TODO

Fix launch with parameters so that it does not give an error for parameters in which the file name and output directory are passed