[![Build Status](https://travis-ci.org/sebaslogen/KotlinWeatherApp.svg?branch=master)](https://travis-ci.org/sebaslogen/KotlinWeatherApp)

# KotlinWeatherApp
Sample Android project of a Weather app implemented purely in Kotlin.

The app connects to an internet API to retrieve weather forecasts for the next week. The data is also stored locally in a database cache.

Running the project and the tests
=============
Open the project in Android Studio and select the gradle task '**installDebug**' or simply press the Run button.

To run all the tests from the command line execute ```./gradlew check connectedCheck``` from the project's folder.

_Note: Make sure to connect a phone to the computer or start an emulator before running the tests._

Continuous integration environment
============
Builds are triggered and automatically built on every commit to git repository, executing all unit and instrumentation tests.
Build history and reports can be accessed here: https://travis-ci.org/sebaslogen/KotlinWeatherApp/builds

License
-------
This content is released under the MIT License: http://opensource.org/licenses/MIT
