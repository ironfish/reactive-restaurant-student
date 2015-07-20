# Exercise 008: Keeping Actors Busy

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will introduce a `Chef` actor who specializes in making our fine caffeinated beverages and will keep our other actors busy.

1. Create the `Chef` actor in the `com.typesafe.training.restaurant` package as follows:
    - Add a `prepareFoodDuration` parameter of type `FiniteDuration`.
    - Define a `Props` factory.
    - Create a `PrepareFood` message with parameters of `food` type `Food` and `guest` type `ActorRef`.
    - Create a `FoodPrepared` message with parameters of `food` type `Food` and `guest` type `ActorRef`.
    - Define the behavior as when `PrepareFood(food, guest)` is received:
        - Busily prepare food for `prepareFoodDuration`.
        - Respond with `FoodPrepared(food, guest)` to the sender.
        - *HINT*: Use `busy` from the `foodhouse` package object in the `common` project to simulate being busy while preparing the `Food`.
2. Change `Waiter` as follows:
    - Add a reference to the `Chef` actor.
    - Instead of serving food immediately, defer to the `Chef` for preparation.
3. Change `Restaurant` as follows:
    - Create a `private chef` actor with name *"chef"*.
    - Use a `createChef` factory method.
    - For `prepareFoodDuration`, use a configuration value with key `restaurant.chef.prepare-food-duration`.
4. Run the `run` command to boot the `RestaurantApp` and verify:
    - *"Restaurant Open"* is logged.
    - Lifecycle debug messages are logged.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the `Guest` actors are enjoying their food.
5. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
6. Run the `next` command to move to and initialize the next exercise.
