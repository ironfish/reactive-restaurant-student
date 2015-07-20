# Exercise 010: Lifecycle Monitoring

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

Sometimes we need to perform certain tasks before stopping an actor. In this exercise, we will explore this idea by watching for the `Termination` message.

1. Change `Restaurant` to monitor each `Guest` as follows:
    - When the `Guest` terminates, remove the `Guest` from foodLimit bookkeeping.
    - Log *"Thanks {guest}, for being our guest!"* at `info`.
2. Run the `run` command to boot the `RestaurantApp` and verify:
    - *"Restaurant Open"* is logged.
    - Lifecycle debug messages are logged.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the `Guest` actors are enjoying their food.
    - Make sure your `Guest` actors are stopped as expected.
3. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
4. Run the `next` command to move to and initialize the next exercise.
