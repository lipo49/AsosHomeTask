# Asos Test App

We use Appium for our iOS and Android tests.

For some useful reading or for reference, see:

- [Appium Guide](http://appium.io/docs/en/about-appium/getting-started/)
- [Appium API documentation](http://appium.io/docs/en/about-appium/api/)

## Requirements

- [Java Runtime Environment version 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
- [Appium](http://appium.io/downloads.html)
- [Android Studio](https://developer.android.com/studio)
- [xcode(to run iOS tests in mac)](https://apps.apple.com/us/app/xcode/id497799835)

## Folder structure

- testCases - main location for all the tests
- pageObjects - page classes with elements and functions that are used on the current area
- utils - all the tools that use across the project
- suites - location of the xml suites configuration files

## Installation

- Clone the repo to a new Java Maven project
- Open pom.xml file and import changes

## Run iOS / Android tests

### Android:

- On configRun.properties file write "android" on platform value
- Connect real device

Run any test from Tests.java or TestNG smoke-Suite-android.xml file under Suites folder

### iOS:

- On configRun.properties file write "ios" on platform value
- Install the app on the device/simulator

Only Real device should pass all this steps:

[Real iOS device setup](http://appium.io/docs/en/drivers/ios-xcuitest-real-devices/)

Run any test / test class or TestNG suite xml file

### More Documentation about the project structure and setup you can fing here:

- [Setup and structure](https://docs.google.com/document/d/1xRnQpVide27NS5V0vEABr9bX13gbXoskr6H7o5OZnng/edit?usp=sharing)

