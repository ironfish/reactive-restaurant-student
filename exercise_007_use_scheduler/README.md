# Exercise 007: Use Scheduler

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will implement the Akka `Scheduler` to simulate the `Guest` ordering more `Food`.

1. Change `Guest` as follows:
    - Add a `finishFoodDuration` parameter of type `scala.concurrent.duration.FiniteDuration`.
    - Change the behavior on receiving `FoodServed` to schedule the sending of `FoodFinished` to the `Guest`.
    - QUIZ: What other changes are needed for the guest to enjoy their food?
2. Change `Restaurant` as follows:
    - Adjust the code for creating a new `Guest`.
    - For `finishFoodDuration`, use a configuration value with key `restaurant.guest.finish-food-duration`.
    - To get the configuration value, use the `getDuration` method on `context.system.settings.config`.
3. Run the `run` command to boot the `RestaurantApp` and verify:
    - *"Restaurant Open"* is logged.
    - Lifecycle debug messages are logged.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the `Guest` actors are enjoying their food.
    - *HINT*: Enter g {food} or guest {food} where {food} has to be the first letter of one of the defined foods (`a`, `m`, or `k`). If you omit {food}, `Akkacore` will be used by default.
4. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
5. Run the `next` command to move to and initialize the next exercise.
