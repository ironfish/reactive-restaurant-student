# Exercise 017: Configure the Dispatcher

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will optimize parallelism through configuring a dispatcher.

1. Configure the `default-dispatcher`.
2. Use the default `fork-join-executor`.
3. Run the `run` command to boot the `RestaurantApp` with different values for `parallelism-max` as follows:
    - Less than the number of cores available.
    - Equal to the number of cores available.
    - More than the number of cores available.
    - Watch the throughput for each scenario.
4. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
5. Run the `next` command to move to and initialize the next exercise.
