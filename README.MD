# RUS
## Добро пожаловать в приложение для разбора текстовых файлов
Приложение предназначено для разбора текстовых файлов на различные типы данных
### Как запустить
1. Клонируйте репозиторий в любое удобное место на вашем компьютере с помощью команды:
```bash
git clone git@git.shpy.ru:release/txt_file_parser.git
```
2. После этого вам нужно добавить **maven-jar-plugin** для запуска jar-файла из командной строки.
   Чтобы установить **maven-jar-plugin**, вам нужно выполнить команду
   Для Deb:
```bash
sudo apt install maven-jar-plugin -y 
```
Для RPM:
```bash
sudo dnf install maven-jar-plugin -y 
```
3. Создайте jar-файл
   Перейдите в каталог проекта и выполните команду:
```bash
mvn clean package
```
4. Запустите
   Чтобы запустить программу, необходимо выполнить команду
```bash
java -jar target/txt_file_parser-1.0-SNAPSHOT.jar
```
**ВНИМАНИЕ**, если вы хотите запустить программу, вам необходимо заранее подготовить текстовые файлы и указать пути к ним в параметрах запуска.
Ниже приведены параметры запуска с примерами:
- `-p` — префикс для имен выходных файлов
- `-o` — пользовательский путь для вывода файлов
- `-a` — режим добавления в существующие файлы
- `-s` — краткая статистика (количество элементов, записанных в исходящие файлы)
- `-f` — полная статистика (для чисел: минимальное количество, максимальное количество, среднее значение, сумма элементов.
Для строк: количество строк, самая короткая и самая длинная строка.)

### ПРИМЕР
```bash
java -jar target/txt_file_parser-1.0-SNAPSHOT.jar -p hello- -o /home/user/abc/ -a -s -f input_files/test1.txt input_files/test2.txt input_files/test3.txt
```

### Зависимости проекта:
- Apache Commons IO -> 2.19.0
- Ломбок -> 1.18.38
- Jansi -> 2.4.2
- Spring Boot Starter Test -> 3.5.3
- Java -> 17
- Project Assembler -> Maven -> 3.6.3

### TODO

Исправить запуск с параметрами, чтобы он не выдавал ошибку при передаче имени файла и выходного каталога (не влияет на работу)

# ENG
## Welcome to Txt File Parser Application
The application is created for parsing txt files into various files by data types
### How to run
1. Clone the repository to any convenient location on your computer using the command:
```bash
git clone git@git.shpy.ru:release/txt_file_parser.git
```
2. After that you need to add **maven-jar-plugin** for command line jar launching.
To install **maven-jar-plugin** you need to run the command
For Deb:
```bash
sudo apt install maven-jar-plugin -y 
```
For RPM:
```bash
sudo dnf install maven-jar-plugin -y 
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
- `-p` — is the prefix of the output file names
- `-o` — Custom path for file output
- `-a` — Mode for adding to existing files
- `-s` — Short statistics (number of items recorded in outgoing files)
- `-f` — Full statistics (For numbers: minimum quantity, maximum quantity, average, sum of elements.
For strings: number of strings, shortest string and longest.)

### EXAMPLE
```bash
java -jar target/txt_file_parser-1.0-SNAPSHOT.jar -p hello- -o /home/user/abc/ -a -s -f input_files/test1.txt input_files/test2.txt input_files/test3.txt
```

### Dependencies of project:
- Apache Commons IO -> 2.19.0
- Lombok -> 1.18.38
- Jansi -> 2.4.2
- Spring Boot Starter Test -> 3.5.3
- Java -> 17
- Project Assembler -> Maven -> 3.4.1

### TODO

Fix launch with parameters so that it does not give an error for parameters in which the file name and output directory are passed (Does not affect the work)