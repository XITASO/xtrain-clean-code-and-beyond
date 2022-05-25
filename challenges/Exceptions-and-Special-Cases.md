# Exceptions and Special Cases Challenge

Special cases increase the complexity of software. The main reason is, that
special cases need to be handled in a different manner than the common case. The
existence of these special cases as well as the conditions when they take place
are not always obvious to the caller. A recent study found out, that 90% of
catastrophic failures in distributed data-intensive systems were caused by
incorrect error handling [^1].

Typical special cases are:

- Null Values
- Exceptions
- Specific values with a special meaning `0` or `""`

## Assignment :dart:

An unexperienced developer implemented a first version of the requirement to
assign a task to a project. There were the following acceptance criteria that had to be met:

1. If I assign an existing task to a project, the project should contain this task.
2. If I assign a non-existing task, an error should be returned.
3. If I assign a task that is already assigned to another project, the task should be updated and assigned to the new project.

The code implementing these requirements can be found in the method
`de.xitaso.taskman.api.ProjectsController#updateProject`. Although it fulfills
all the requirements, it has to handle many special cases which makes it quite
difficult to understand and error prone.

- Discuss with your sparring partner which unnecessary special cases exist in this code base and what alternatives could be used.
- Try to define all of the special cases out of existense!
- Your solution should no longer need any `if` or `try...catch` statement

### Hint 🤓
You can return an [Optional<T>](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Optional.html) instead of `null` to avoid null-checks. See [this tutorial](https://www.baeldung.com/java-optional) on how to use it.

[^1]:
    Ding Yuan et. al., "Simple Testing Can Prevent Most Critical Failures: An
    Analysis of Production Failures in Distributed Data-Intensive Systems," 2014
    USENIX Conference on Operating Systems Design and Implementation.
