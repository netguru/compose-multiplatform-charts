# Library of charts for Kotlin Multiplatform projects

Library contains several chart composables for usage in Kotlin Multiplatform projects. 
Currently supported platforms are **Desktop** and **Android**.

## Testing
Testing is implemented using [Shot](https://github.com/pedrovgs/Shot) library. In order to run it
against prerecorded results, create at least one of the following emulators:
- tablet: Nexus 10, running API 31
- phone: Pixel 4a, running API 31

For tests to work, make sure you create emulators with default values, as **screen size and density
must be exactly the same**.

Details as to how to run the tests are in the link to the library itself, but to run it against
prerecorded screenshots, run the following command:

`./gradlew executeScreenshotTests -PdirectorySuffix=$deviceName`

where `$deviceName` is one of:
- Nexus_10_API_31
- Pixel_4a_API_31
