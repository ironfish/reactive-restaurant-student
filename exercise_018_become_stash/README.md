# Exercise 018: Changing Actor Behavior

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will demonstrate through the use of `become` and `stash` to modify actor

1. Re-implement the `Chef` actors behavior as a finite state machine:
    - Do not use the `busy` method.
    - Use `become`, `stash` and the `scheduler`.
2. Run the `run` command to boot the `RestaurantApp`, create some `Guest` actors and verify everything works as expected.
3. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
4. Run the `next` command to move to and initialize the next exercise.
