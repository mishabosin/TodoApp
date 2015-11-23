# TodoApp

This Android application is built for basic familiarity with Android and its development 
environment.

Time spent: Around 8 hours in total (including setting up the requirement)

Completed user stories:
 * [x] Required: Can you successfully add and remove items from the todo list within your app?
 * [x] Required: Did you include support for editing todo items as described above?
 * [x] Required: Does your app persist todo items and retrieve them properly on app restart?
 * [x] Required: Did you successfully push your code to github? Can you see the code on github?
 * [x] Required: Did you add a README which includes a GIF walkthrough of the app's functionality?
 * [x] Suggested: Use a DialogFragment instead of new Activity for editing items
 * [ ] Suggested: Persist the todo items into SQLite instead of a text file

Notes:
* Added an "add task" feature that uses a FloatingActionButton
* Added an undo feature for create/edit/remove using the Snackbar.
* Figure out how R.string.<value> works. How to get a string out of the int value?

TODO:
* Got kind of messy using both "item" and "task" interchangeably - need to refactor all "items" into "tasks"
* Implement SQL lite storage and use the settings widget to switch between storage options.

Walkthrough of all user stories:

![Video Walkthrough](todo_in_action.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
