# Rationale

## Choose Your Own Question

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

## Keyboard Shortcuts

> How did you choose keyboard shortcuts?

- **Efficiency**: I aimed for minimal keystrokes.
- **Vim Inspiration**: `quest` borrows Vim's shortcuts.
- **Mnemonic**: For non-Vim keys, I went with mnemonics or shortcuts common in other software.

`quest` is designed with efficiency and Vim inspiration in mind. But, these two aspects can sometimes conflict, such as in the case of auto save versus manual save.

## Data Management

> Why does this extension auto save?

Auto save is used for efficiency and to provide a customized Vim experience. It eliminates the need for a manual save step that could distract from the immersive reading experience.

> How does this extension handle accidental saves?

Accidental saves can be undone, even after a browser restart or crash. But, there is a limit to the number of undoable actions.

> Why is there an action-based undo limit?

The fixed limit ensures consistent memory and computational overhead, and is inspired by Vim's `undolevels`.

> How did you decide which actions are undoable?

`quest` follows Vim's approach, where navigation is not undoable but actions that add, edit, or delete text are.

But using an import file is recommended for adding, editing, or deleting questions or answers.

> Why do you want to use an import file?

`quest` is a reading extension and lacks some features of text editors. The YAML format is easy to manipulate using text editors.

> Why YAML?

YAML is more human-readable and easier to edit than JSON, and allows nesting unlike CSV.

## Icons

> How did you choose the icons?

I chose intuitive icons that are commonly used in other software.
