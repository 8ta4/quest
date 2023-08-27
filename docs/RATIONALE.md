# Rationale

## Goal

> What is the goal of this Chrome extension?

The goal of this Chrome extension is to revolutionize the way we read, particularly technical and educational content. It will present an engaging sequence of questions and answers, allowing readers to check their understanding as they progress. This enhances comprehension and keeps the reader engaged. The unique formats of "Quick Calculus" and "Little Schemer" inspired this approach, and generative AI has made it viable by enabling automatic question generation.

## Pledge

> What is your level of commitment to this project?

I have pledged to commit to this project. I will not read any new book until the vision of this extension, "Choose Your Own Question", becomes a reality.

## Non-linear Reading

> How does "Choose Your Own Question" change the traditional reading process?

"Choose Your Own Question" disrupts the traditional linear reading process that often makes reading tedious. It empowers readers to jump, skip, and revisit sections while automatically tracking their progress. This non-linear approach revitalizes the reading experience, making it more dynamic and engaging.

## Chrome Extension

> Why did you choose a Chrome extension?

The choice to develop a Chrome extension was influenced by several factors:
- **Market Share and User Base**: Chrome is the most popular browser.
- **Integration**: Users can effortlessly activate this extension to convert online content.
- **Maintainability**: The Chrome Web Store manages updates, simplifying the process.

## Content and Question Separation

> Why is there a need for content and question separation?

- **PDF**: The fixed format of PDFs makes it challenging to insert questions directly.
- **Navigation**: A centralized view can make navigating questions easier.

## Visual Feedback

> How does the extension provide visual feedback?

The extension provides visual feedback to test understanding. It presents a question, hides the answer, and lets you test your comprehension. It also hides the next answer and indicates where the next answer starts, so you know where to stop reading.

## Keyboard Shortcut

> How did you choose keyboard shortcuts?

- **Efficiency**: Keyboard shortcuts allow you to navigate content seamlessly, ensuring you remain engaged.
- **Vim Inspiration**: Vim allows efficient navigation. This extension mirrors its shortcuts.
- **Mnemonic**: Non-Vim keys are inspired by mnemonics or commonly used shortcuts in other software.

## Technical Constraints

### HTML Content

> How does it deal with HTML content?

- **Text fragment**: Text fragments do not alter the highlighted DOM segment, making it impossible to directly get the Y-coordinate of the highlighted content.
- **window.find**: This function does not seem to allow highlighting text that spans multiple elements or multiline text. This can be worked around by searching multiple times, but the scrolling behavior could be jarring.
- **Y-coordinate**: Hiding content simply using the Y-coordinate can inadvertently obscure the sidebar, disrupting web browsing.

### PDF Content

> How does it deal with PDF content?

- **Search**: PDF.js allows you to search strings to highlight text. However, if the search string spans multiple pages, highlighting fails (https://github.com/mozilla/pdf.js/issues/10620).
- **Text Fragment**: PDF.js does not support Text Fragment (https://github.com/mozilla/pdf.js/issues/15223).
- **window.find**: PDF.js does not highlight text when window.find is used to search text.

## Future Endeavors

> What are your future plans?

- **Complementary Application**: In the future, the extension will allow you to save files locally on your computer.
- **Related Question**: Each answer may contain related questions, similar to "Choose Your Own Adventure".
- **Conversational AI**: I envision this extension eventually integrating advanced conversational AI to offer more dynamic and interactive Q&A.
