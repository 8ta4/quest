# Rationale

## Pledge

> What is your level of commitment to this project?

I pledge not to read any new book until my vision, "Choose Your Own Question", comes to life.

And until WebAssembly supports garbage collection, I hereby pledge not to take out the trash.

## Goal

> What is the goal of this Chrome extension?

The goal of this Chrome extension is to revolutionize the way you read, particularly technical and educational content. It will present an engaging sequence of questions and answers, allowing readers to check their understanding as they progress. This enhances comprehension and keeps the reader engaged. This idea was sparked by the unique formats of "Quick Calculus" and "Little Schemer", and now, thanks to generative AI, I can make it happen.

## Non-linear Reading

> How does "Choose Your Own Question" change the traditional reading process?

"Choose Your Own Question" disrupts the traditional linear reading process that makes reading tedious. It lets you jump, skip, and revisit sections while automatically tracking their progress. This non-linear approach revitalizes the reading experience, making it more dynamic and fun.

## Chrome Extension

> Why did you choose a Chrome extension?

- **Popularity**: Chrome is the most popular browser.
- **Ease of Use**: Users can effortlessly activate this extension to convert online content.
- **Maintainability**: Thanks to the Chrome Web Store, updates are a breeze.

## Content and Question Separation

> Why is there a need for content and question separation?

- **PDF**: The fixed format of PDFs makes it challenging to insert questions directly.
- **Navigation**: A centralized view can make navigating questions easier.

## Visual Feedback

> How does the extension provide visual feedback?

Think of it as a mini quiz. The extension asks you a question, hides the answer, and lets you see how well you understood the material. It also hides the next answer or shows where it starts so you know when to stop reading.

## Keyboard Shortcuts

> How did you choose keyboard shortcuts?

- **Efficiency**: I aimed for the least number of keystrokes
- **Vim Inspiration**: This extension mirrors Vim's shortcuts.
- **Mnemonic**: For non-Vim keys, I went with mnemonics or shortcuts that are common in other software.

## Technical Constraints

### HTML Content

> How does it handle HTML content?

- **Text Fragment**: They don't change the highlighted part of the DOM, so I can't directly get its Y-coordinate.
- **window.find**: This function does not seem to allow highlighting text that spans multiple elements or multiline text. This can be worked around by searching multiple times, but the scrolling behavior could be jarring.
- **Y-coordinate**: If I just hide content based on its Y-coordinate, I might accidentally hide the sidebar too.

### PDF Content

> How does it handle PDF content?

- **Search**: PDF.js lets me search for strings to highlight text. But if the string goes over multiple pages, highlighting fails (https://github.com/mozilla/pdf.js/issues/10620).
- **Text Fragment**: PDF.js does not support Text Fragment (https://github.com/mozilla/pdf.js/issues/15223).
- **window.find**: PDF.js doesn't highlight text when I use window.find to search for text.

## Looking Ahead

> What are your future plans?

- **Save Locally**: I'm planning to let you save files right on your computer.
- **Related Question**: Each answer might lead to related questions, kind of like "Choose Your Own Adventure".
- **Conversational AI**: I dream of integrating advanced conversational AI into this extension for more dynamic and interactive Q&A sessions.