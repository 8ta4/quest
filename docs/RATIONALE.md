# Rationale

## Goal

> What is this extension all about?

`quest` is all about changing how you consume technical content. It turns it into a Q&A journey - like a "Choose Your Own Adventure" but with questions.

I pledge not to read any new book until my vision comes to life. And until WebAssembly supports garbage collection, I hereby pledge not to take out the trash.

> Why use a Q&A format?

As you read along, you can answer questions to check your understanding and keep yourself engaged. This idea was sparked by the unique formats of "Quick Calculus" and "Little Schemer".

## Medium

> Why not a book?

- **Universality**: `quest` can theoretically morph any content into questions using generative AI.
- **Non-Linear Reading**: With `quest`, you can hop around sections while it keeps track of your progress. Follow your curiosity!
- **Interactivity**: `quest` throws a question, hides the answer, and lets you gauge your understanding.
- **Keyboard Shortcuts**: `quest` lets you navigate content only using keyboard shortcuts.

> Why not generate answers too?

Generative AI can hallucinate. Hallucinated questions may be confusing, but hallucinated answers are real deal breakers.

> Why a Chrome extension?

- **Popularity**: Chrome is the most popular browser.
- **Integration**: You can use your favorite extensions alongside `quest`. Vimium, Dark Reader, you name it.
- **Easy Updates**: Chrome Web Store makes updates a walk in the park.

But some designs had to be ditched for a Chrome extension. Like the footer bar - it had to go because I needed to separate content and questions.

## Technical Constraints

> Why separate content and questions?

- **Persistence**: Page refresh or navigation won't mess with the questions.
- **PDFs**: Their fixed format makes it tricky to insert questions directly.

But this separation makes it hard to spot where your next question is.

> Why do you want to know where your next question is?

`quest` lets you finish reading everything if you answer every question. But if you don't know where your next question is, you might end up reading it twice. That's why `quest` either hides or highlights your next answer so you know where to stop.

> Why not hide all answers?

Hiding multiple answers at once is tricky in both HTML and PDF.

> Why is it hard to hide multiple answers in HTML?

- **Text Fragment**: They don't alter the highlighted part of the DOM, so I can't directly get its Y-coordinate.
- **window.find**: This function doesn't seem to allow highlighting text that spans multiple elements or multiline text. This can be worked around by searching multiple times, but the scrolling behavior could be jarring.
- **Y-Coordinate**: If I hide content based on its Y-coordinate alone, I might end up hiding the sidebar too.

> Why is it hard to hide multiple answers in PDF?

- **Search**: PDF.js lets me search for strings to highlight text. But if the string spans multiple pages, highlighting fails (https://github.com/mozilla/pdf.js/issues/10620).
- **Text Fragment**: PDF.js does not support Text Fragment (https://github.com/mozilla/pdf.js/issues/15223).
- **window.find**: PDF.js doesn't highlight text when I use window.find to search for text.

## Keyboard Shortcuts

> How did you choose keyboard shortcuts?

- **Efficiency**: I aimed for minimal keystrokes.
- **Vim Inspiration**: `quest` borrows Vim's shortcuts.
- **Mnemonic**: For non-Vim keys, I went with mnemonics or shortcuts common in other software.
