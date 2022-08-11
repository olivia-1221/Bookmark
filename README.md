# Story Graph 💡

**What will the application do?** Allow users to log books they’ve read, bestow each entry with a 
rating, and view reading statistics.

**Who will use it?** Anyone who wants to!

**Why is this a project of interest?** I love books and want to make a logging application that’s 
simple, expressive, and easy to use.

## User Stories (Phase 0 & 1)

- As a user, I want to be able to input a new book with a title & author
- As a user, I want to be able to rate a book on a scale of 1-5️
- As a user, I want to be able to view my reading history as a list
- As a user, I want to be able to view certain statistics:
  - total book #
  - average rating
  - % breakdown of each rating, visualized as a stacked bar

## User Stories (Phase 2)

- As a user, I want to be able to save my reading history to file
- As a user, I want to be able to load my reading history from file

## Instructions for Grader (Phase 3)

- You can generate the first required event (Mouse Event) by clicking **Create New Entry**, entering the inputs as 
prompted, and **clicking the Enter Title/Author/Rating button after each input** to set it. **Click to Finalize Input** 
when done.
- You can generate the second required event by clicking **View Statistics** and following the instruction that pops up
(Key Event). **This will also render my visual components.**
- You can save the state of my application by clicking **Save Data**.
- You can reload the state of my application by clicking **Load Data**.

## Phase 4: Task 2

**Representative sample of the logged events that occur when the program runs:**

Thu Aug 11 12:57:35 PDT 2022 <br>
book added to history | Pride & Prejudice by Jane Austen

Thu Aug 11 12:57:48 PDT 2022 <br>
book added to history | 1984 by George Orwell

Thu Aug 11 12:57:51 PDT 2022 <br>
computed & displayed basic stats (# books, average rating) of books in history

Thu Aug 11 12:58:52 PDT 2022 <br>
book added to history | Great Expectations by Charles Dickens

Thu Aug 11 12:58:53 PDT 2022 <br>
computed & displayed basic stats (# books, average rating) of books in history

## Phase 4: Task 3

Looking at the UML diagram I don't currently see major refactoring opportunities; I think the design is straightforward, doesn't
have problematic coupling and doesn't require composite/observer/iterator design patterns nor additional interfaces 
or abstract classes. <br>
However, I suspect `LoggerGUI` could be split into smaller and more cohesive classes, e.g. one for statistics-viewing 
behavior and one for save/load behavior. I'd do this by creating new classes, moving the functionality and related 
fields into them, and replacing the original method bodies with a call to the method in its new location.
Also, `actionPerformed(ActionEvent e)` in `LoggerGUI` contains duplication of 
`e.getActionCommand().equals("")`, so a helper with a string parameter could extract that method, improving readability.
The string parameters (`"entryButton"`, `"titleButton"`, etc.) could also become an enumeration representing button commands, 
providing a single point of control.
<br> 
I did consider merging `StatsBar` and `StatsKey` into one class for cohesion and the single responsibility principle, 
but they extend different Java classes and a class can extend only one.