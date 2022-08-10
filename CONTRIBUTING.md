# Documentation
Dokka is added to the project, so to create (or, better say, update) the docs, you need to run
```
./gradlew charts:dokkaHtml
```
on the root dir of the project.

After the task is done, copy the generated docs into `./docs`, which can also be done by running
```
rm -rf ./docs ; cp -r ./charts/build/dokka/html ./docs
```

# Testing
Testing is implemented using [Shot](https://github.com/pedrovgs/Shot) library. In order to run
it against prerecorded results, create at least one of the following emulators:
- tablet: Nexus 10, running API 31
- phone: Pixel 4a, running API 31

For tests to work, make sure you create emulators with default values, as **screen size and
density must be exactly the same**.

Details as to how to run the tests are in the link to the library itself, but to run it against
prerecorded screenshots, run the following command:
```
./gradlew executeScreenshotTests -PdirectorySuffix=$deviceName
```
where `$deviceName` is one of:
- Nexus_10_API_31
- Pixel_4a_API_31

## Flakiness
Due to differences in graphics between M1 and x86 chips, images appear different to the script
and so the tests fail (difference between images can be well above 10%). To human eye they do
seem the same, though. This is explained and shown in [issue 265](https://github.com/pedrovgs/Shot/issues/265). Until this is fixed, there is a way to test stuff at least locally:

1. uncomment tests that are commented out because of this issue
2. record all the tests
3. make the desired changes in the code
4. check the new UI state against the pre-recorded screenshots (I suggest removing tolerance if
   the tests were recorded on your machine!)
5. comment the flaky tests out again
6. revert the screenshots

## Other issues

- Library [does not work with Java 17](https://github.com/pedrovgs/Shot/pull/292). The error
  itself is not as obvious as one might expect, though. To fix the issue, use Java 11.
- Java heap space sometimes [needs to be enlarged](https://github.com/pedrovgs/Shot/issues/304)
- You might encounter [INSTALL_FAILED_SHARED_USER_INCOMPATIBLE](https://stackoverflow.com/questions/15205159/install-failed-shared-user-incompatible-while-using-shared-user-id)
  issue. [Here](https://stackoverflow.com/a/21809883/6835732) is a shortcut to the answer.
