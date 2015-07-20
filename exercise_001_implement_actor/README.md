# Exercise 001: Implement an Actor

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will implement the `Restaurant` actor with logging.

1. Create the `Restaurant` actor in the `com.typesafe.training.restaurant` package as follows:
    - Mix-in the `Actor` and `ActorLogging` traits.
    - Define the initial behavior to handle any message by logging *"Food Prepared"* at `info`.
2. Run the `run` command to verify the main class `RestaurantApp` boots up as expected.
3. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
4. Run the `next` command to move to and initialize the next exercise.
