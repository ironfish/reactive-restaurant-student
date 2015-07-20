# Exercise 011: Create a Faulty Actor

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will explore resilience by managing a faulty actor.

1. Change `Guest` as follows:
    - Add a `foodLimit` parameter.
    - Add the `CaffeineException` case object extending `IllegalStateException` to the companion object.
    - Upon receiving `FoodFinished` throw the `CaffeineException` if `foodCount` exceeds `foodLimit`.
2. Change `Restaurant` as follows:
    - So that a `Guest` can be created with a `foodLimit`.
    - Log the `Guest` path name instead of just the `Guest`.
3. Change `RestaurantApp` as follows:
    - So that a `Guest` can be created with a `foodLimit`.
4. Run the `run` command to boot the `RestaurantApp` and verify:
    - Create a `Guest` with an individual `foodLimit` less than the global one and watch its lifecycle.
    - *HINT*: Enter g 2 or guest 2 to create a `Guest` with a `foodLimit` of 2; if you omit the limit, `Int.MaxValue` will be used by default.
5. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
6. Run the `next` command to move to and initialize the next exercise.
