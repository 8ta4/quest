# quest

## Choose Your Own Question

> What is this extension?

`quest` is a Chrome extension that transforms the way you read technical content. It offers a Q&A journey, similar to the "[Choose Your Own Adventure](https://www.goodreads.com/genres/choose-your-own-adventure)" approach.

As you read along, you can answer questions to check your understanding and keep yourself engaged. This idea came from the quirky style of "[The Little Schemer](https://www.goodreads.com/en/book/show/548914.The_Little_Schemer)".

I pledge not to read any new book until my vision comes to life. And until WebAssembly supports garbage collection, I hereby pledge not to take out the trash.

## Setup

> How do I install it?

TODO: Add a link to the Chrome Web Store where the quest extension can be installed.

You can install it from [here]().

## Usage

> How do I try it?

TODO: Add a link to a demo page where users can try out the quest extension.

You can try it on this [demo page]().

> How do I see the answer?

If a question is marked as unseen, pressing `Space` will mark it as seen and reveal its answer. `Space` is chosen because it's used to toggle checkboxes, and "seen" and "Space" both have "s".

> How do you identify the start of a segment that corresponds to a question?

When you're using `quest`, you'll notice a little triangle icon (▼) in the left margin next to the first line of each segment. This is the marker that shows you where the relevant text for that question starts.

> How do you identify the end of a segment that corresponds to a question?

To wrap up a segment, `quest` uses a similar icon in the left margin, but this one points up (▲). This marker lines up with the last line of text for that particular question's content.

> How do I unsee the answer?

`Space` lets you toggle the current question's seen status. If a question is marked as seen, pressing `Space` will mark it as unseen and hide its answer.

> How do I get to the next question?

Press `Shift + j` for the next question. This is inspired by the Vim command for moving the cursor down.

> How do I get to the previous question?

Press `Shift + k` for the previous question. This is inspired by the Vim command for moving the cursor up.

> How do I get to the next unseen question?

Press `⌘ + j` for the next unseen question. This is inspired by the Vim command for moving the cursor down.

> How do I get to the previous unseen question?

Press `⌘ + k` for the previous unseen question. This is inspired by the Vim command for moving the cursor up.

> How do I undo toggling the seen status?

`Space` lets you toggle the current question's seen status.

Alternatively, you can press `⌘ + z` to undo it.

> How do I redo my undo?

You can hit `⌘ + Shift + z`.

> Is there an undo limit?

Yes, the undo limit is 1,000 actions.

> Can I read something other than the demo page?

Yep, you just need to import a file. You can load it by pressing `Ctrl + q`. "q" stands for "question".

> What's the keyboard shortcut to deactivate this extension?

`Option + r` toggles this extension. Think "r" for "read," similar to a browser's reader mode.

## Compatibility

> Can I use `quest` to read PDF files?

Absolutely, `quest` can handle PDF files without any issues.

> Can I use `quest` to read DRM-protected EPUB files?

Nope, `quest` isn't built for DRM-protected EPUB files.

> Can I use `quest` to read DRM-free EPUB files?

Yes, you can! But you'll need to extract the HTML from the EPUB first. If the content is spread across multiple HTML files, you might have to combine them.

> Can I use `quest` alongside other browser extensions?

Absolutely! You can use `quest` with other browser extensions. It's built to play nicely with popular ones like Vimium and Dark Reader, so you can have a smooth experience even with multiple extensions running.
