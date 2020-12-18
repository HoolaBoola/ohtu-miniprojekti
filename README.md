![Github Actions](https://github.com/HoolaBoola/ohtu-miniprojekti/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![codecov](https://codecov.io/gh/HoolaBoola/ohtu-miniprojekti/branch/main/graph/badge.svg?token=OkQ6MbteWP)](https://codecov.io/gh/HoolaBoola/ohtu-miniprojekti)

# About app

RecommendationLibrary is an app for storing reading recommendations of different types: books, videos, blogs and podcasts. 
With this app you can 
  - add, list, delete and edit recommendations in your library 
  - add tags for your recommendations
  - add timestamps for videos
  - search recommendations by tags

# User guide

Make sure you have Java installed in your computer. Download the most recent .jar file from this repository’s Tags and run it from the command prompt with: 
java -jar pathToJar\DownloadedJarName.jar

# About project

RecommendationLibrary is a Java-based project built using gradle. SQLite is utilized as a project's database solution. Project's methods and classes (that contain some logic) are unit tested (JUnit). Functionalities/features are tested using Cucumber. Dependencies to input/output and dao are injected to classes through interfaces to avoid concrete dependencies between classes and to ease testing.

[Product backlog](https://docs.google.com/spreadsheets/d/1UFZhNW9bPXnuyy8PhZKPM4rtDv5bZwuUcMMI8oJrBF4/edit#gid=701303499)

[Sprint1 Backlog](https://docs.google.com/spreadsheets/d/1UFZhNW9bPXnuyy8PhZKPM4rtDv5bZwuUcMMI8oJrBF4/edit#gid=0)

[Sprint2 Backlog](https://docs.google.com/spreadsheets/d/1UFZhNW9bPXnuyy8PhZKPM4rtDv5bZwuUcMMI8oJrBF4/edit#gid=2019524618)

[Sprint3 Backlog](https://docs.google.com/spreadsheets/d/1UFZhNW9bPXnuyy8PhZKPM4rtDv5bZwuUcMMI8oJrBF4/edit#gid=1736023819)

[LOPPURAPORTTI](https://docs.google.com/document/d/1R4rHjfip2k4Nl7xZlq4Uu9pzODvWWeepNWErekDrVbw/edit#)

## Class diagram

![Class Diagram](ClassDiagram.jpg?raw=true)

## Definition of Done

* Koodattu
* Yksikkötestattu
* Integraatiotestattu
* Testit on mennyt läpi
* Katselmoitu
* Dokumentoitu
* Integroitu tuotantoon
