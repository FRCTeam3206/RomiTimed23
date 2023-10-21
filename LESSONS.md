# RomiTimed23
Learn WPILib by programming the Romi

## Lesson 1: Clone the repo
This repo (short for repository) is stored in the [FRCTeam3206 GitHub account](https://github.com/FRCTeam3206). In order to work with it on your own computer, you will need to [clone](https://git-scm.com/docs/git-clone) it. Cloning creates a local copy of the repository on your computer that is linked to the remote repository stored on GitHub. You will be able to edit this local repository in wPILib VSCode.

There are several ways to clone a repo from GitHub. First, open the [RomiTimed23](https://github.com/FRCTeam3206/RomiTimed23) repo on GitHub. Above the code window, there is a green button that says `<>Code` on it. Click on this button to see a menu with several options to clone the repo. The simplest option, if you have [GitHub Desktop](https://desktop.github.com/) installed is to select `Open in GitHub Desktop`. This will create a clone linked to the remote repository. The clone will be located in the "GitHub" directory under "Documents". 

Now that you have cloned this repo, open WPILib VSCode and then open the repository. 
* On the menu bar, click on `File` then `Open Folder ...`, you could also use the keyboard shortcut `Ctrl-K Ctrl-O`.
* Navigate to `Documents\GitHub`
* Click on the folder with this repository
* Click on the `Select Folder` button (this still works if you navigate into the folder

You should now see the contents of this repo on the left side of the VSCode window. Most of the folders and files that you see are there to support the build and deploy process and you won't need to make any changes to them. The robot code will all be stored in:
``` 
src\main\java\frc\robot\
```
Open this directory and you will see several .java files. 
- `Main.java` - This is the "entry point" for the Java program. This file already contains the correct code and you won't need to edit it
- `Robot.java` - The top-level file that defines the behavior of the robot. This is where you'll place the code that controls your robot.
- `RomiDrivetrain.java` - The code that defines the drivetrain and how it operates.

Go ahead and open `Robot.java` and take a look at the code.

----
## Lesson 2: A bit of `git`
*NOTE: If you are using the shared CREATE_XX computer, you are signed in to GitHub with the "RoyalT-Wrecks" account. Please don't sign in with your own account on these computers. If you are working from your own computer, please use your own GitHub account. If you have any trouble using git or GitHub, post a note on the team Discord.*

[`git`](https://git-scm.com/) is a popular version control system (VCS) for managing source code. A VCS provides a solution to the problem of managing code changes when multiple people are contributing to the same code base. It can also make it easier to keep track of changes and correct ones that cause bugs.

In a VCS, all code changes are tracked and can be reverted if they cause problems. `git` does this using the concept of **branches** and **commits**. 

All repositories have at least one branch (called 'main' in this repo). When you want to modify the code, you create a new branch and edit the code in that branch. Once it has been tested, you then merge the new branch back into the main branch. `git` supports having multiple parallel branches, so it is possible to work on several differnt parts of the code base at the same time. A good branch is one that represents a single type of change to the code base, for example a branch to fix a specifc bug or one to add a new feature.

Code changes are added to a branch with a commit. This allows you to specify exactly which changed files are added to the branch and document what has been changed in a given commit. 

### Using `git`

There are several ways to access `git` commands. The command line is the most powerful option, but it can be tricky to remember the correct commands. VSCode has integrated support for `git` and is convenient since you can do things directly in the IDE (integrated development environment). Finally, there is GitHub desktop, which you probably used to clone the interface. The dedicated GUI is easy to use and allows you to do everything you'll need to contribute to the team code repository.

#### Command Line Interface
If you don't already have `git`, you can install it from [git-scm.com](https://git-scm.com/). The WPILib Documentation includes a [Git Version Control Introduction](https://docs.wpilib.org/en/stable/docs/software/basic-programming/git-getting-started.html) that provides a good overview of using the `git` command line from the `git bash` shell.

#### Git and GitHub in VSCode
VS Code has built-in support for managing git repositories, which allows you to conduct the common actions directly fromm the IDE. We also have the GitHub extension installed so that you can create pull requests in the IDE.

#### GitHub Desktop
[GitHub Desktop](https://desktop.github.com/) is a graphical user interface to the most common features of `git`. You can use this to clone repos, make commits, and push and pull code.

### Making a branch
A branch is an isolated copy of the code. You can make any changes you want within a branch without affecting the code in any other branches. You can have as many branches as you want, but having a lot can get hard to manage. Once you have made and tested your changes, you can `merge` the code from your branch back to the `main` branch to make it part of the robot code. 

Always work on a branch since it isolates your changes and allows mutliple contributors to work on the code simultaneously without overwriting one anothers changes.

If you don't already have it open, load your local repo in WPILib VSCode. Open a terminal from the `Terminal` menu if one isn't already open. If you have installed `git` then you can type the following to create a new branch:

``` 
git checkout -b "your-branch-name"
```
This command both creates and checks-out (makes active) the new branch. Branch names should be short and descriptive. The name can't contain spaces, so it is common to use dashes between words for readability. 

You can see which branch you are on by typing:
```
git branch
```
This will display a list of all local branches with an asterisk (*) by the current branch.

If you want to change to a different branch, you can use:
```
git checkout "branch-to-checkout"
```
Make sure that you've committed any changes you want to keep before changing branches.

### committing code to a branch
Now that you are on your own branch go ahead and edit `Robot.java`. For exammple, add a comment with your name at the top of the file and save the file. To store this change in the repo, you need to `commit` it to the repo. There are two steps to making a commit, staging the files and then making the commit. 

First you indicate which files you want to update by `add`ing them to the stage (list of files that will be committed):
```
git add Robot.java
```
Second, you `commit` them to the repo:
```
git commit -m "Short description of the change"
```
The `-m` tells `git commit` to add the text following it as the description of the commit. Good comments a short and help people understand what was changed. Instead of saying "fixed stuff", explain what you fixed, such as "corrected motor ids". You don't have to describe the exact code, since people can see how the code changed when they look at the commit, instead, try to describe why you made the change.








### (An incomplete list of) Coding Rules
* When working on code, always create a branch with a meaningful name followed by your initials (eg. `documentation_CRS`). 
* Commit frequently and push code to GitHub **every day**. This makes sure that everyone will have access to the latest version of any branch. Think of it as the programmers equivalent to putting away your supplies at the end of the shop time.   
* For the competition bot code, do not commit directly to `main`. Create a `pull request` and the code will be reviewed and then merged if appropriate. This ensures that the `main` branch represents fully functional code.
* Before competition, create a new branch for that competition. All edits made during the competition should be made to that branch. After we return, we can review changes and decide which changes we want to merge back to `main`.




When you edit the local repository, the changes are only made on your computer. 