# Programming WPILib on a Romi

## Lesson 1: Clone-ing around
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
<div class="page"/>

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

### Basic `git` operations
For the next several sections, we'll use the `git` command line interface for the examples. You can do the same things inside VSCode or with the GitHub Desktop by finding the right menus.

#### Making a branch
A branch is an isolated copy of the code. You can make any changes you want within a branch without affecting the code in any other branches. You can have as many branches as you want, but having a lot can get hard to manage. Once you have made and tested your changes, you can `merge` the code from your branch back to the `main` branch to make it part of the robot code. 

Always work on a branch since it isolates your changes and allows mutliple contributors to work on the code simultaneously without overwriting one anothers changes.

If you don't already have it open, load your local repo in WPILib VSCode. Open a terminal from the `Terminal` menu if one isn't already open. If you have installed `git` then you can type the following to create a new branch:

``` pwsh
git checkout -b "your-branch-name"
```
This command both creates and checks-out (makes active) the new branch. Branch names should be short and descriptive. The name can't contain spaces, so it is common to use dashes between words for readability. 

The branch you create will be based on whatever code is in your current working directory. If you make edits to the code, then create a new branch, your edits will be available in your new branch. 

You can see which branch you are on by typing:
```pwsh
git branch
```
This will display a list of all local branches with an asterisk (*) by the current branch.

If you want to change to a different branch, you can use:
```pwsh
git checkout "branch-to-checkout"
```
Make sure that you've committed any changes you want to keep before changing branches.

#### committing code to a branch
Now that you are on your own branch go ahead and edit `Robot.java`. For exammple, add a comment with your name at the top of the file and save the file. To store this change in the repo, you need to `commit` it to the repo. There are two steps to making a commit, staging the files and then making the commit. 

First you indicate which files you want to update by `add`ing them to the stage (list of files that will be committed):
```pwsh
git add Robot.java
```
To add all files that have been changed, type:
```pwsh
git add .
```

Second, you `commit` them to the repo:
```pwsh
git commit -m "Short description of the change"
```
The `-m` tells `git commit` to add the text following it as the description of the commit. Good comments a short and help people understand what was changed. Instead of saying "fixed stuff", explain what you fixed, such as "corrected motor ids". You don't have to describe the exact code, since people can see how the code changed when they look at the commit, instead, try to describe why you made the change.

#### Push code to the remote (GitHub)
Finally, we need to add this to the `GitHub` repository. The first time you `push` a branch to the upstream repository, you need to tell it that you want to add the branch to that repo.
```pwsh
git push -u origin "your-branch-name"
```
After that, you'll be able to update the branch any time you make a new commit by just typing:
```pwsh
git push
```
Whenever you start working on code, you should update the local repo from the remote repe. This will make sure that you are always working from the latest version of the code from GitHub. The opposite of pushing code to the repo is pulling it from the repo:
```pwsh
git pull
```

If there is a branch on GitHub that you want to work on, but it isn't on the computer you are currently on, you can crete a local tracking branch with:
```pwsh
git fetch
git checkout "name-of-remote-branch"
```

#### Merging code from another branch
The primary way that you get code from one branch into another is with the `merge` command. 
``` pwsh
git checkout "destination-branch"
git merge "source-branch"
```
`git` will try to integrate the new code into the current branch automatically, but that isn't always possible. If it can't figure out how to do the merge because there are conflicting edits, it will let you know that there has been a merge conflict. Managing and correcting merge conflicts is an advanced topic that we won't cover here.

### The takeaway
The more frequently you use git, the more benefit you will get from it. Pull from the remote when you start working on code, make branches for the work you are doing, create frequent commits to capture specific, granular changes. Finally, don't be afraid to ask for help.

----
<div class="page"/>

## Lesson 3: Driving Lessons - Basic Teleop
The driver-controlled portion of the match is called `telop`. During this part of the game, the driver is primarily in control of the robot and commands are relayed from controllers attached to the drive station to the robot through the FMS (field managemnt system). Typically, the drive team consists of a driver, a mechanism operator, and a drive coach. 

### Inputs
The primary way that the drive and mechanism operator control the robot is via a [joystick or controller](https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html). Let's add a controller to the program so that we can operate the Romi.

> **JAVA Concept: `import`**
>
> Java and WPILib provide a lot of classes that you can use in your programs, but they aren't avaiable by default in your code. In order to access them, you have to `import` them into the `namespace` of your program. You do this by using the `import` statement followed by the path to the class that you want to import. You must import classes into any file that needs them. Typically, `import` statements are located at the top of a `.java` file, just after the `package` statement. 

> **NOTE**
> Before making any changes to your code, create a new branch.

The first step is to `import` the class so that we can interact with the controller. The class that we're going to use it the [`XboxController`](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/XboxController.html) class, which works with the Logitech controllers set to 'X' mode. It's located in `edu.wpi.first.wpilibj`. Add this line after the other imports at the top of the `Robot.java` file:

``` java
import edu.wpi.first.wpilibj.XboxController;
```
The next step is to create a new instance of the `XboxController` class. First, we have to define a variable that will hold the reference to the controller instance. We only want to have one reference to the joystick that we will re-use whenever we need to read values from the joystick. We'll do this inside the `Robot()` class before the constructor so that it is available to all the methods in the class. 

Find the line that instantiates the `RomiDrivetrain()`:
``` java
  private final RomiDrivetrain m_drivetrain = new RomiDrivetrain();
```
and add this line after it:
``` java
  private XboxController m_controller = new XboxController(0);
  (1)---- (2)----------- (3)--------- (4)--------------------
```
This line defines a member variable and initializes it to a new instance of the XboxController class. The individual parts of this statement do the following:
1. `private` is an optional keyword that says this variable can only be accessed within this class. This prevents other classes from reading or altering our controller.
2. `XboxController` defines the `type` of the variable. Every variable must have a specific `type` in Java, which specifies what type of object it can hold.
3. `m_controller` is the name of the variable. This is how our code will access the instance.
4. `= new XboxController(0)` creates a new instance by calling the constructor of the `XboxController` class and assigns it to the variable. In this case, we pass the constructor the value 0, which indicates that the controller will be connected to channel 0 of the driver station.

Finally, we need to connect the values we read from the controller with the inputs to the drivetrain. This should be done in the `teleopPeriodic()` method of the `Robot` class. This method is automatically called once every 20 ms when the robot is in `teleop` mode. Any code you put in here will run 50 times/second, so this is the place for the code you will use to operate the robot in `teleop`. 

Scroll down in the code until you see the definition of `teleopPeriodic()`. Currently this method does nothing. There is no code between the curly brackets `{` and `}`. Update the method to include the line between the curly brackets:

```java
    m_drivetrain.arcadeDrive(m_controller.getLeftY(), m_controller.getRightX());
```
Once you've done this, `teleopPeriodic()` should look like:

``` java
  @Override
  public void teleopPeriodic() {
    m_drivetrain.arcadeDrive(m_controller.getLeftY(), m_controller.getRightX());
  }
```
### Test the code
Now you're ready to test the code. 

First, power on the Romi and connect to it's WiFi access point. You should also plug in an XboxController into your computer for controlling the Romi.

You start the simulation by clicking on the WPILib icon above the code window and selecting `WPILib: Simulate Robot Code`. This will compile your code and let you know if there are any errors. If the code compiles, then it will open a `dashboard` called `glass` that shows you information about the robot status.


> **WIP**
>
>outputs: motor configuration, drivetrains
>
>inputs: joystick, buttons
>
>driver station & dashboard
>
>telemetry: sending data to dashboards (don't use print!)
