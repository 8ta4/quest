# Rationale

## Goal

> What is the goal of this Chrome extension?

The goal of this Chrome extension is to revolutionize the way you read, particularly technical and educational content. It works by transforming the existing content into a series of engaging questions and answers. As you read along, you can answer these questions to check your understanding. This not only enhances your comprehension but also keeps you engaged. This idea was sparked by the unique formats of "Quick Calculus" and "Little Schemer". And now, thanks to generative AI, I can make it happen.

I pledge not to read any new book until my vision, "Choose Your Own Question", comes to life. And until WebAssembly supports garbage collection, I hereby pledge not to take out the trash.

## Non-linear Reading

> How does "Choose Your Own Question" change the traditional reading process?

"Choose Your Own Question" disrupts the traditional linear reading process. It lets you jump, skip, and revisit sections while automatically tracking your progress. This non-linear approach revitalizes the reading experience, making it more dynamic and fun.

## Chrome Extension

> Why did you choose a Chrome extension?

- **Popularity**: Chrome is the most popular browser.
- **Ease of Use**: Just a click and voila! Your online content is transformed.
- **Easy updates**: Thanks to the Chrome Web Store, updates are a breeze.

## Content and Question Separation

> Why separate content and questions?

- **Navigation**: Having all questions in one place makes it easier to navigate.
- **PDFs**: Their fixed format makes it tricky to insert questions directly.

## Visual Feedback

> How does the extension give visual feedback?

It's like a pop quiz! The extension pops a question, hides the answer, and lets you see how well you've understood. When you view the current answer, it hides the next answer or shows where it starts so you know when to stop reading. I wish I could hide multiple answers simultaneously. But it’s hard.

## Technical Constraints

### HTML

> Why is it difficult to hide multiple answers in HTML?

- **Text Fragment**: They don't change the highlighted part of the DOM, so I can't directly get its Y-coordinate.
- **window.find**: This function doesn't seem to allow highlighting text that spans multiple elements or multiline text. This can be worked around by searching multiple times, but the scrolling behavior could be jarring.
- **Y-coordinate**: If I hide content based on its Y-coordinate alone, I might end up hiding the sidebar too.

### PDF

> Why is it difficult to hide multiple answers in PDF?

- **Search**: PDF.js lets me search for strings to highlight text. But if the string goes over multiple pages, highlighting fails (https://github.com/mozilla/pdf.js/issues/10620).
- **Text Fragment**: PDF.js does not support Text Fragment (https://github.com/mozilla/pdf.js/issues/15223).
- **window.find**: PDF.js doesn't highlight text when I use window.find to search for text.

## Keyboard Shortcuts

> How did you choose keyboard shortcuts?

- **Efficiency**: I aimed for the least number of keystrokes
- **Vim Inspiration**: This extension mirrors Vim's shortcuts.
- **Mnemonic**: For non-Vim keys, I went with mnemonics or shortcuts that are common in other software.
