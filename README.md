# shutterstock android app
Load images from shutterstock and group by category
Its also possible to search by category

This code makes use of:
Retrofit - Client to handle REST API calls, with a GSONConverter to make easier the parsing
RxJava2 - To better handling of asynchronous network calls
Dagger2 - Framework for better handling DI, makes testing easier
Glide - LIbrary to efficiently load images on android
Data Binding - Empowers UI development and eliminates boilerplate

--Tests--

Espresso - Framework used to create user intereface tests
Mockito - Framweork used to simplify mock objects for testing
Hamcrest - Improves testing making use of matchers classes

MVP architecture was chosen for this project as its simples, focus on business logic and makes testing easier

Things to improve:
- Increase code coverage. Right now just some example of unit tests and UI test. Include instrumentation test with roboletric
- Refactor build.gradle to make use of for eg: libraries.gradle where would be defined all dependencies adn its versions
- Remove hardcoded values from layouts. Using the dimens values. Include more styles
- Unify use of Data Binding, is not being used in all activities/fragments
- Include caching
- Improve UI. Layout could be nicer
