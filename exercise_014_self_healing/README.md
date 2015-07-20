# Exercise 014: Implement Self Healing

## Eclipse Reminder (Only if your IDE is Eclipse based)

If you are using *Eclipse* as your IDE, you will need to import this exercise. **Attention**: It is **crucial** that you **complete this exercise** before running `next`.

When importing an exercise into *Eclipse* make sure you always do the following:

1. Close *all open tabs* in Eclipse.
2. Collapse the *current and completed* exercise project folder.
3. Select `File > Import` to run the `Import Wizard` and import the next exercise.

## Instructions

In this exercise, we will correct a problem introduced in the last exercise by implement self-healing.

1. QUIZ: Why does the message flow in exercise 013 get interrupted?
2. Reinitiate the message flow by providing the `Waiter` actors supervisor with all the necessary information.
    - *HINT*: Think supervision strategy!
3. Run the `run` command to boot the `RestaurantApp` and verify that `Guest` actors are served even after the `Waiter` gets frustrated.
4. Run the `test` command to verify the solution works as expected.
    - *Remember* to make sure all tests for this exercise pass before running `next`.
5. Run the `next` command to move to and initialize the next exercise.
