# Exercise 015: Detect the Bottleneck

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will change our configuration settings to see if we can detect a bottleneck.

1. Make the following changes to the configuration files:
    - Set `food-limit` to 1000 (no more caffeinated guests).
    - Set `prepare-food-duration` to 2 seconds.
    - Set `accuracy` to 100 (no more frustrated waiters).
    - Set `finish-food-duration` to 2 seconds.
2. Run the `run` command to boot the `RestaurantApp` and verify:
    - Create one `Guest`, then another, then some more and watch the throughput per `Guest`.
    - QUIZ: Why does the application not scale?
    - QUIZ: Where is the bottleneck?
3. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
4. Run the `next` command to move to and initialize the next exercise.
