# Simple Shell for CSCI 411

This is the solution to homework assignment 1: a simple shell interface which accepts user commands and executes each command as an external process; the shell runs both Unix commands and MS-DOS commands.

RUN THE PROGRAM FROM ShellGUI.java
Files provided in root directory for testing find, copy, del commands.

## Inconsistencies

- CommandExecutor.java is not strictly a controller class.
- Did not follow a pattern (e.g., Command pattern) as it would involve a lot of boilerplate that is unnecessary for this type of assignment. Thus, a few parts of executeCommand() in CommandExecutor.java are not scalable or maintainable (i.e., cd / pwd & history & exit command require a specific execution sequence which are implemented with conditional blocks).
- Commands which expect additional user input bypass the expected input (e.g., executing date command forces "\t" input to bypass date change prompt).

## Known Issues

- User can edit the working directory prompt. Attempting to execute a command without the ">" will cause an error message to be displayed. Not fatal.