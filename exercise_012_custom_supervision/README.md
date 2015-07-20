# Exercise 012: Custom Supervision

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will further explore resilience by implementing custom supervision.

1. Look up the default supervisor strategy in the `Akka` documentation.
2. Change `Restaurant` as follows:
    - Caffeinated `Guest` actors should not restart.
    - Apply a custom supervision strategy that stops them instead.
3. Run the `run` command to boot the `RestaurantApp` and verify:
    - Create a `Guest` with an individual `foodLimit` less than the global one and watch its lifecycle.
    - Verify that the caffeinated `Guest` does not restart.
4. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
5. Run the `next` command to move to and initialize the next exercise.
