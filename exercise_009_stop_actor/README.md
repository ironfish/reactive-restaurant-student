# Exercise 009: Stop an Actor

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will limit the number of foods a `Guest` consumes by setting a caffeine limit per `Guest`. When the `Guest` reaches their limit, we will stop the actor.

1. Run the `groom` command to initialize this exercise.
2. Change `Restaurant` as follows:
    - Create an `ApproveFood` message with parameters of `food` type `Food` and `guest` type `ActorRef`.
    - Add a `foodLimit` parameter of type `Int`.
    - When creating the `Waiter` pass along `self` instead of `Chef`.
    - Add to the behavior, `ApproveFood` and look at the current `Guest` foodLimit as follows:
        - If less than `foodLimit`, send `PrepareFood` to the `Chef`.
        - Else log *"Sorry, {guest}, but you have reached your limit."* at `info` and stop the `Guest`.
3. Change `Waiter` as follows:
    - Rename the `chef` parameter to `restaurant`.
    - Change the behavior to reflect using `Restaurant`.
4. Change `Guest` as follows:
    - Override the `postStop` hook to log *"Goodbye!"* at `info`.
5. Change `RestaurantApp` as follows:
    - Get the foodLimit from configuration.
6. Run the `test` command to verify the solution works as expected.
7. Run the `run` command to boot the `RestaurantApp` and verify:
    - *"Restaurant Open"* is logged.
    - Lifecycle debug messages are logged.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the `Guest` actors are enjoying their food.
    - Make sure your `Guest` actors are stopped as expected.
8. QUIZ: Your implementation may have a hidden issue; see if you can find it!
9. Run the `next` command to advance to the next exercise.
