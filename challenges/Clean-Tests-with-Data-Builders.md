# Clean Tests with Data Builders

In test automation, we want to automatically verify the correctness of a system under test[^1]. To do so, a typical test can be divided into three parts:

1. Arrange - brings the system into the required state
2. Act - execute the system under test
3. Assert - verify that the desired output has been generated or the desired behavior has been executed

In the field, we often see tests, that have a very long Arrange phase compared
to Act and Assert. Often much of this code is just boilerplate to fulfill
invariants (e.g. fields that must not be null) or generate test data. This
boilerplate often hides the important details that are relevant for the test.

For example, the following code block shows a unit test from our
[taskman](../src/taskman/) sample project that tests that a goal is not
critical, if it has less than 50 tasks which are in state "ToDo" or "Waiting".
The important detail is, that 1 of the 50 tasks this Arrange-step generates is
set to done and hence the goal shall not be identified as critical.

https://github.com/XITASO/xtrain-clean-code-and-beyond/blob/bfeb5d52fdca5651eb1cf1d90aed2eb32032c236/src/taskman/src/test/java/de/xitaso/taskman/unit/GoalServiceTests.java#L80-L114

[^1]: Depending on the test-scope this can be a class, a module or a whole system.

## Test Data Builders to the rescue :bulb:

A Test Data Builder is an application of the [builder
pattern](https://en.wikipedia.org/wiki/Builder_pattern) to create required test
data. A Test Data Builder provides an abstraction so that the required
boilerplate in the test is reduced to a minimum and the builder provides a
readable fluent interface in the language of the business-domain to create the
required objects. For more details, see this [tutorial](https://www.arhohuttunen.com/test-data-builders/#-make-construction-easier-with-the-builder-pattern).

Our [taskman](../src/taskman/) sample app already creates an abstract
[DataBuilder](../src/taskman/src/test/java/de/xitaso/taskman/databuilders/DataBuilder.java)
class and an
[AddressBuilder](../src/taskman/src/test/java/de/xitaso/taskman/databuilders/AddressBuilder.java)
that helps to build valid address objects. Using such a builder makes it much easier to create for example a valid address with city Hamburg:

```java
// Without builders, all setters must be set manually to avoid illegal null-valued properties:
var address = new Address();
address.setStreet("Some Street");
address.setStreetNumber("3 1/2");
address.setZipCode("12345");
address.setCity("Hamburg")

// With builder, just add required data, everything else gets filled with defaults:
var address = new AddressBuilder().with(a -> a.setCity("Hamburg")).build();
```

## Assignment :dart:

### Context

In our [taskman](../src/taskman/) sample app, we are introducing a new feature called "Goals". With the following requirements:

- A goal is assigned to 0..n projects.
- A goal is assigned to a responsible person.
- A goal is critical when the responsible person is located in Munich.
- A goal is critical when the number of open (= status is ToDo or Waiting) is more than 50.

A colleague already started to implement a first version of a service that finds
critical projects, it can be found in
[GoalPriorizationService.java](../src/taskman/src/main/java/de/xitaso/taskman/services/GoalPriorizationService.java).
She also wrote unit tests that verify the correctness of the algorithm but found
out that manually setting up the test scenarios with all the goals, projects,
tasks and so on isn't optimal (as we already saw in the example above).

### Your job

1. Create Test Data Builders for the domain objects _Goal_, _Project_, _Task_ and _User_. You can re-use the abstract _DataBuilder_ if you like or come up with your own implementation.
2. Refactor [GoalServiceTests](../src/taskman/src/test/java/de/xitaso/taskman/unit/GoalServiceTests.java) to use these Test Data Builders.
3. (Bonus) Remove all setters of the [Goal](../src/taskman/src/main/java/de/xitaso/taskman/entities/Goal.java) class and introduce a constructor `Goal(String title, String description, LocalDate deadline, User responsiblePerson)`. Due to the Test Data Builder, there should be only one location in the test code you need to adapt!
