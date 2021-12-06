# ShoppingCartJava

## CMPE 202 Individual Assignment
Name: Anirudh Satyam <br/>
SJSU ID: 015406365

## Prerequisities to run the code <br/>

Ensure that Java, Maven and JDK are installed in your system and set the path variables appropriately.

## Steps to run the code

1. Clone the repository and navigate to the Inventory folder <br/>
2. Paste the "Input.csv" inside the Inventory folder
3. Run the command - mvn compile <br/>
4. Run the command - mvn clean install <br/>
5. Run the command - mvn -X clean install exec:java -Dexec.mainClass="com.inventory.ReadCsv" -Dexec.args="Input.csv"
6. You will be able to see the outputs generated in Inventory\src\main\resources\input

## Design Patterns implemented

1. Singleton <br/>
2. Chain of Responsibility <br/>
