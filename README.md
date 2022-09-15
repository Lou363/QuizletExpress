# QuizletExpress

A small java program to quickly respond to an MCQ that keeps looping itself until you've got all the correct answers, with a GUI Based on Swing.

It accept files which layout are based on this design:

## Quizz File layout

the program looks for textfiles located in the Database subfolder

```TXT

BEGIN // the start of the quizz database.

Q : put here the question topic.
put here the good answer.
put here a wrong answer.
// you can put multiple wrong answers.

// Repeat the format until you've inputed all of your questions.

END // Indicate the end of the quizz database.

```
