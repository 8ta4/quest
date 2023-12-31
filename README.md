# quest

## Introduction

> What is this extension?

`quest` is a Chrome extension that transforms the way you read technical content. It offers a Q&A journey, similar to the "Choose Your Own Adventure" approach.

## Getting Started

> How do I install it?

TODO: Add a link to the Chrome Web Store where the quest extension can be installed.

You can install it from [here]().

> How do I try it?

TODO: Add a link to a demo page where users can try out the quest extension.

You can try it on this [demo page]().

> How do I see the answer?

If a question is marked as unseen, pressing **Space** will mark it as seen and reveal its answer. (**Space** is chosen because it's used to toggle checkboxes, and "seen" and "Space" both have "s".)

> How do I unsee the answer?

**Space** lets you toggle the current question's seen status. If a question is marked as seen, pressing **Space** will mark it as unseen and hide its answer.

> How do I get to the next question?

Press **J** for the next question.

> How do I get to the previous question?

Press **K** for the previous question.

> How do I get to the next unseen question?

Press **Ctrl + J** for the next unseen question.

> How do I get to the previous unseen question?

Press **Ctrl + K** for the previous unseen question.

> How do I undo toggling the seen status?

**Space** lets you toggle the current question's seen status.

Alternatively, you can press **U** to undo it.

> How do I redo my undo?

You can hit **Ctrl + R**.

> Is there an undo limit?

Yes, the undo limit is 1,000 actions.

> How do I read something else?

You need to import a file.

If there's an existing import file, you can load it by pressing **Ctrl + Q**. ("Q" stands for "question".)

> How can I use it while using Vimium?

If you press **I**, you will enter Vimium's insert mode, and `quest` will be able to receive keystrokes.

If you want to use Vimium again, you can press **Escape** twice.

> Why do I need to press **Escape** twice?

You press **Escape** once to focus `quest`'s Content Window and press **Escape** once more to exit Vimium's insert mode. Any key excluding **Escape** will focus `quest`'s Question Window. To exit Vimium's insert mode, you first want to focus on the Content Window.

> How do I deactivate this extension?

**Option + R** toggles this extension. (Modeled after a browser's reader mode. "R" stands for "read".)
