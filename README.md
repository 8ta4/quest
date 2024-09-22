# quest

## Choose Your Own Question

> What is this extension?

`quest` is a Chrome extension that transforms any content into an interactive Q&A format.

As you read along, you can answer questions to check your understanding and keep yourself engaged. This idea came from the quirky style of "[Choose Your Own Adventure](https://www.goodreads.com/genres/choose-your-own-adventure)" and "[The Little Schemer](https://www.goodreads.com/en/book/show/548914.The_Little_Schemer)".

I pledge not to read any new book until my vision comes to life. And until WebAssembly supports garbage collection, I hereby pledge not to take out the trash.

## Setup

> How do I install it? (Planned)

TODO: Add a link to the Chrome Web Store where the `quest` extension can be installed.

You can install it from [here]().

## Usage

> How do I try it?

You can try it on this [demo page](https://paulgraham.com/start.html?quest=https://raw.githubusercontent.com/8ta4/quests/e313eabecc5e6c78e6c75a6ca7f02c5dd38d92cb/paulgraham.com/start.html/quest.yaml).

> How do I see the answer?

If a question is marked as hidden, pressing `Space` will mark it as visible and reveal its answer. `Space` is chosen because it's used to toggle checkboxes, and "visible" and "Space" both have "s".

> How do you identify the start of a segment that corresponds to a question? (Planned)

When you're using `quest`, you'll see a little triangle icon (▼) that'll always be at a fixed horizontal position in the left margin of your screen. This marker lines up vertically with the first line of the segment you're working on.

> How do you identify the end of a segment that corresponds to a question? (Planned)

To wrap up a segment, `quest` uses a similar icon in the left margin, but this one points up (▲). This marker lines up with the last line of text for that particular question's content.

> How do I hide the answer?

`Space` lets you toggle the current question's visible status. If a question is marked as visible, pressing `Space` will mark it as hidden and hide its answer.

> How do I get to the next question?

Hit `Down arrow` to move to the next question. But here's a pro tip: you might want to change that to `Control + j` across applications. This is inspired by the Vim command for moving the cursor down.

> How do I get to the previous question?

Hit `Up arrow` to move to the next question. Try switching it to `Control + k` across applications. This is inspired by the Vim command for moving the cursor up.

> How do I get to the next hidden question? (Planned)

Press `⌘ + j` for the next hidden question. This is inspired by the Vim command for moving the cursor down.

> How do I get to the previous hidden question? (Planned)

Press `⌘ + k` for the previous hidden question. This is inspired by the Vim command for moving the cursor up.

> How do I select the question whose segment is the topmost within the reading window's viewport? (Planned)

Hit the `s` key. I like to think of "s" for "select."

> How do I select the question whose segment is the lowermost within the reading window's viewport? (Planned)

Press `Shift + s`. Again, "s" for "select."

> How do you scroll to the beginning of the current segment? (Planned)

Hit `zt`. This Vim-inspired command will move your view to the start of the segment you're currently in.

> How do you scroll to the end of the current segment? (Planned)

Hit `zb`. This Vim-inspired command will bring the bottom of your current segment into view.

> How do you center the current segment in the viewport? (Planned)

Hit `zz`. This Vim-inspired command will adjust your view so that the segment is centered on your screen.

> How do I undo toggling the visible status? (Planned)

`Space` lets you toggle the current question's visible status.

Alternatively, you can press `⌘ + z` to undo it.

> How do I redo my undo? (Planned)

You can hit `⌘ + Shift + z`.

> Can I read something other than the demo page?

Yep, you can use `quest` with all sorts of content beyond just the demo page. Just click on a special link that has a query string pointing to a YAML file.

> Can I read the original page without `quest` functionality?

Absolutely. Just open the page using its regular URL, without any `quest`-related query string.

> If I close the reading window, does the question window close too? (Planned)

Yep, if you close the reading window, the question window will close as well.

> If I close the question window, does the reading window close too? (Planned)

Yes, it works the same way. Closing the question window will also close the reading window.

> Can I have multiple documents open in `quest` at the same time?

Yes, you can! `quest` is designed to let you work with multiple documents simultaneously. When you open a new document, it'll use the current reading window for its content and pop open a new question window just for that document.

> Can data be lost when `quest` updates?

Yes, it's possible. Since `quest` hasn't reached version 1.0 yet, there might be updates that introduce breaking changes. These changes could lead to losing data.

> Where does `quest` store data on a Mac? (Planned)

Your data is stored in this directory:

TODO: Replace [Extension ID] with the actual extension ID once the extension is uploaded to the Chrome Web Store.

```
~/Library/Application Support/Google/Chrome/Default/Local Extension Settings/[Extension ID]
```

This storage location is based on Chrome's current implementation and may change in future updates without notice.

> Can I use an external syncing or backup service to sync or back up `quest` data? (Planned)

Yes, you can use an external service to sync or back up your `quest` data by creating a symlink to the directory where `quest` stores its data.

For instance, if your Dropbox folder is at `~/Dropbox`, you can create a symlink like this:

TODO: Replace [Extension ID] with the actual extension ID once the extension is uploaded to the Chrome Web Store.

```
ln -s "~/Library/Application Support/Google/Chrome/Default/Local Extension Settings/[Extension ID]" "~/Dropbox/quest"
```

This symlink trick depends on how Chrome currently handles data storage. There's a chance it could stop working without notice.

## Compatibility

> Can I use `quest` to read web pages?

Yeah, you can totally use `quest` on web pages. But it works best on pages where the content doesn't change. If you're dealing with pages that get updated, it might not be the best fit. You could end up with questions that don't match what's actually on the page anymore.

But here's a workaround: you can use a Chrome extension called [SingleFile](https://github.com/gildas-lormeau/SingleFile) to save the web page as a single HTML file. By doing this, you're essentially freezing that version of the page, so whenever you come back to it, `quest` will still be in sync with the content, even if the original page has been updated.

> Can I use `quest` to read PDF files? (Planned)

Absolutely, `quest` can handle PDF files without any issues.

> Can I use `quest` to read DRM-protected EPUB files?

Nope, `quest` isn't built for DRM-protected EPUB files.

> Can I use `quest` to read DRM-free EPUB files?

Yes, you can! But you'll need to extract the HTML from the EPUB first. If the content is spread across multiple HTML files, you might have to combine them.

> Can I use `quest` with [Vimium](https://github.com/philc/vimium)? (Planned)

Totally! `quest` is designed to play nicely with Vimium both in the reading window and the question window.

> Can I use `quest` with [Dark Reader](https://github.com/darkreader/darkreader)? (Planned)

Yes, `quest` is compatible with Dark Reader in both the reading and question windows.
