## Explanation

I've created an android application to demonstrate the grid to show which point that has been
visited (Red box), non visitable(Black box), not visited (White box) and shortest path (Blue box)

I've tested on the android app with 1000x1000 it take a lot of time to render, but if you run unit
testing of debug the code the algorithm is ran completed only the drawing part is still processing

## Demo
https://github.com/arohim/yts-mobile-dev-assignment-android-abdulrohim-sama/assets/4177366/07feb089-0620-41c5-8746-2c56d6f8656f



## Source code structure

- `common/core/src/main/java/com/him/sama/ytstest/core/searchalgorithm/` this path contains the
  actual code
- `common/core/src/test/java/com/him/sama/ytstest/core/searchalgorithm/` this path contains unit
  testing

## Prerequisite

The project is using kotlin + gradle, you need to install Android studio to run it

## How to run the project using Android studio

1. Open the project
2. Wait for the project to sync
3. Run unit testing by going to
   `common/core/src/test/java/com/him/sama/ytstest/core/searchalgorithm/UnitTesting.kt` and run the
   unit testing

## How to run unit testing by command line

Use command line ``./gradlew test`` to run unit testing
