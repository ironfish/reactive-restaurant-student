# Exercise 006: Actor State

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will implement state by tracking a `Guest` actor's favorite `Food`.

1. Create the `Waiter` actor in the `com.typesafe.training.restaurant` package as follows:
    - Define a `Props` factory.
    - Create a `ServeFood` message with parameter `food` of type `Food`.
    - Create a `FoodServed` message with parameter `food` of type `Food`.
    - Define the behavior as when `ServeFood(food)` is received, respond with `FoodServed(food)` to the sender.
2. Change `Guest` as follows:
    - Create a `FoodFinished` message.
    - Add a `waiter` parameter of type `ActorRef`.
    - Add a `favoriteFood` parameter of type `Food`.
    - Add a local mutable `foodCount` field of type `Int`.
    - Define the behavior as:
        - When `FoodServed(food)` is received:
            - Increase the `foodCount` by one.
            - Log *"Enjoying my {foodCount} yummy {food}!"* at `info`.
        - When `FoodFinished` is received, respond with `ServeFood(favoriteFood)` to `waiter`.
3. Change `Restaurant` as follows:
    - Create a `private waiter` actor with name *"waiter"*.
    - Use a `createWaiter` factory method.
    - Add `favoriteFood` parameter of type `Food` to the `CreateGuest` message.
    - Update the `createGuest` factory method to account for the `waiter` and `favoriteFood` parameters.
4. Change `RestaurantApp` to account for the `favoriteFood` parameter required by the `CreateGuest` message.
5. Run the `run` command to boot the `RestaurantApp` and verify:
    - *"Restaurant Open"* is logged.
    - Lifecycle debug messages are logged.
    - Make sure the correct number of `Guest` creations were logged.
    - *HINT*: Enter g {food} or guest {food} where {food} has to be the first letter of one of the defined foods (`a`, `m`, or `k`). If you omit {food}, `Akkacore` will be used by default.
6. QUIZ: Why don't you see any log messages from `Guest` actors enjoying their food?
7. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
8. Run the `next` command to move to and initialize the next exercise.
