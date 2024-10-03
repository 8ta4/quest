# quest

## Goals

### Injection

> Do the questions in `quest` form an injection with the content?

Yes, the questions in `quest` form an injection with the content. You don't have to read the same stuff over and over.

### Surjection

> Do the questions in `quest` form a surjection with the content?

Not necessarily. `quest` tries to cover as much of the content as possible, but there are a couple of exceptions:

1. Content before the first answer: Sometimes there's some introductory text before you get to the first answer.

1. Content after the last answer: There might be some text after the last question and answer.

So, if you go through all the questions and read the text they reveal, plus the exceptions I mentioned, you’ll cover everything. This isn't just about learning; it's about earning the right to say, "I've read this book." This can boost your confidence in discussions, give you credibility among peers, and provide a sense of accomplishment.

> Is the content before the first answer always visible?

Yes, it is. Let me explain:

- Engagement: You're usually most interested when you start reading a book, so if I hide this content, it might disrupt your natural curiosity.

- Onboarding: The first question should be easy to answer, something that pulls you in. Having the context visible helps with that.

- Simplicity: Some elements, like navigation, should always stay visible. The main content could be hidden and then revealed. But figuring out which is which would be tricky. Keeping everything visible simplifies the implementation.

> Is the content after the last answer always visible?

Yes, it is. Here's why:

- Naturalness: Sometimes, the content at the end doesn't really fit with a question. If I tried to force a question here, it might come off as awkward.

- Simplicity: Some elements at the end, like footers or navigation, need to stay visible. The main content could be hidden and then revealed. But sorting this out would be tricky. Keeping everything visible simplifies the implementation.

### Keyboard

> Can you control `quest` entirely with your keyboard?

Absolutely, you can navigate `quest` entirely using keyboard shortcuts. It's quicker and helps you stay in the zone.

> Do `quest`'s shortcuts conflict with Vimium?

Nope, `quest`'s shortcuts are designed to avoid any conflicts with Vimium.

### Random Access

> Can you freely move between segments when using `quest` for reading?

Yep, with `quest`, you can jump around between segments as you like. It keeps track of what you've already read, so you don't have to. This way, you can explore at your own pace and follow your curiosity.

## Setup

> How do I get `quest`'s dev environment up and running?

1. Install [Homebrew](https://brew.sh/#install).

1. Install [devenv](https://github.com/cachix/devenv/blob/bfc6481188a3e7ddda2e745292b737c03949a1ab/docs/getting-started.md#installation).

1. Install [direnv](https://github.com/cachix/devenv/blob/bfc6481188a3e7ddda2e745292b737c03949a1ab/docs/automatic-shell-activation.md#installing-direnv).

1. Run the following commands:

   ```sh
   git clone git@github.com:8ta4/quest.git
   cd quest
   direnv allow
   ```

The `devenv.nix` file has got all the scripts you need.

## Functionality

### Navigation

> When a new question window opens, which window is focused: the question window or the answer window? (Planned)

The question window gets focused. Since it's a new window, it might pop up in an inconvenient spot. By focusing on it first, it gives users the chance to move it using keyboard shortcuts.

If you need to get back to the answer window, switching focus is easy with shortcuts like `Cmd + ~`.

> Does `quest` use `chrome.commands`?

No, it doesn't. `chrome.commands` only supports [up to 4 shortcuts](https://developer.chrome.com/docs/extensions/reference/api/commands#:~:text=at%20most%20four%20suggested%20keyboard%20shortcuts), but `quest` needs more than that.

Instead, `quest` relies on event listeners in the content scripts.

> How does the viewport adjust when you reveal an answer? (Planned)

When you reveal an answer, the viewport automatically scrolls to the start of the current segment. Here's why:

- Expectation: You probably expect to see new content when you reveal an answer.

- Context: This adjustment nudges you to read through the whole segment from the top. That way, you've got all the context before you reaching the answer.

> Does `quest` use inline styles, internal styles, or external styles to control the visibility of content?

`quest` uses inline styles to manage content visibility. This ensures that visibility changes take precedence over other CSS rules. That way, `quest` can work smoothly on different websites, even when their CSS structure is unknown.

> Is navigation undoable in `quest`?

No, you can't undo navigation in `quest`. It follows a similar approach to Vim.

> Can you open the same document with the same URL? (Planned)

Technically, yes, but when you do that, the new tab will close right away, and it'll bring you back to the tab where the document is already open. This happens to avoid having multiple versions of the same document open, which can mess up data syncing.

If you need to view the document in a separate tab, you can do that, but without the query string.

> Does `quest` detect the URL using a content script or a background script?

`quest` uses a background script to detect the URL. Content scripts can run at different times during the page load, and sometimes other scripts or extensions could change the URL before the content script has a chance to grab it.

> Is it possible to use just one window for both questions and the main content in `quest`?

Technically, yes, you can do that by using Chrome's API to combine them into a single window. But, the way `quest` is meant to be used is with separate windows for the main content and the questions. Here's why:

- Integrity: Keeping questions in a separate window helps make sure we don't mess up the layout or functionality of the original content.

- Flexibility: You can arrange the windows side by side, on different monitors, or in any setup that suits your reading style and screen space.

> Is the question window in `quest` a `normal` window or a `popup`?

The question window in `quest` actually uses a `popup` type. Here's why:

- Appearance: With no address bar or tabs cluttering the view, the window looks cleaner. This is especially helpful if you're working on a smaller screen.

- Functionality: Since it's a `popup`, you can't open new tabs. It prevents you from navigating away from the questions.

> Does `quest` use Chrome extension message passing or `window.postMessage()` to communicate between the answer window and the question window?

`quest` uses Chrome extension message passing to handle communication between the answer window and the question window. It simplifies things by removing the need for manual origin checking, which can be overlooked when using `window.postMessage()`. By sticking with Chrome extension message passing, communication is restricted to within the extension.

> Does `quest` assume a left-to-right language or right-to-left language?

`quest` is mainly designed for left-to-right (LTR) languages. The segment indicators are placed in the left margin to fit LTR reading habits. This makes it easier for readers to spot the segment boundaries as they read from left to right.

### Data

> What's the query string parameter used to specify a YAML file?

The query string parameter you need to specify a YAML file is simply `quest`.

Let's take Paul Graham's "How to Start a Startup" essay as an example. To add `quest` functionality to it, you'd take the essay's URL and tack on the `quest` parameter. This parameter points to a YAML file. It'd look something like this:

TODO: Replace `[HASH]` with the actual commit hash once available.

```
https://paulgraham.com/start.html?quest=https://github.com/8ta4/quests/blob/[HASH]/paulgraham.com/start.html/quest.yaml
```

> Can I host the YAML file anywhere other than GitHub?

Yes, you can host the YAML file wherever you like, as long as it's accessible.

> Does the URL for the YAML file need to contain a hash?

Technically, no, it doesn't need a hash. But it's recommended to include one, or use some other method to ensure the URL points to the exact content you want. This makes sure that everyone accessing the link gets the same version.

> Does the URL for the YAML file need to contain the full path of the original content?

Technically, no, but it's a smart move. Including the full path of the original content in the YAML file URL has a few benefits:

- Connection: It keeps a direct link between the YAML file and the content it's tied to.

- Uniqueness: Using the full path ensures each YAML file has its own unique URL, helping you avoid any naming conflicts.

- Naming: You don't have to brainstorm new names for each YAML file. Just use the original content's path, and you're set.

- Discovery: If someone's looking for the YAML file for a specific piece of content, they can figure out where it's located just by following the original URL.

> Why choose YAML over JSON for import files?

YAML is chosen over JSON because:

- You can add comments.

- It doesn't need extra commas, so it's easier to edit.

> Why the `.yaml` and not `.yml`?

The YAML FAQ recommends "[using '.yaml' when possible.](https://yaml.org/faq.html#:~:text=Is%20there%20an,yaml%22%20when%20possible.)"

> Is the structure of the YAML file a list or a map?

The YAML file is a list of maps. In each of these maps, you'll find a "question" field and an "answer" field, both using literal block scalars. Here's a quick example to show you what that looks like:

```yaml
- question: |
    Will a startup that starts with good people, makes something customers actually want, and spends as little money as possible probably succeed?
  answer: |
    to start with good people, to make something customers actually want, and to spend as little money as possible. Most startups that fail do it because they fail at one of these. A startup that does all three will probably succeed.
- question: |
    Is getting rich doable?
  answer: |
    getting rich is doable too. Hard, but doable.
```

This example is based on Paul Graham's essay "[How to Start a Startup](https://paulgraham.com/start.html)."

> Is the YAML file required to be sorted? (Planned)

No, the YAML file doesn't need to be sorted. The software takes care of the ordering based on the content.

> Does `quest` generate questions?

Nope, `quest` doesn't generate questions. Even though AI might get there someday, it's been pretty tough to make sure the questions meet specific requirements right now.

> Does a question correspond to a book section?

Not necessarily. A question in `quest` doesn't always align with a specific book section. It might cover part of a section, multiple sections, or even span across chapter boundaries.

> Do questions in a quest need to be unique?

No, they don't have to be unique. Here's the reasoning:

- Simplicity: You don't need to create different versions of questions just to make them unique.

- Alignment: Questions should stay true to the original content's structure.

- Reinforcement: Sometimes, repeating questions reinforces important points that are repeated in the text.

> Can a question be arbitrary text?

Technically, yes, a question in `quest` can be pretty much any text you want. But it's recommended that each question should:

- Have an answer in the text: Including the answer directly in the text provides immediate validation and prevents the frustration of encountering a question you can't answer.

- Be a binary choice: This helps you make quick decisions, keeping your reading momentum going. It also makes it easier to provide clear right or wrong feedback.

- Not use terms unique to the author or text: Avoiding these terms ensures that you can understand the question even if you haven't read the text first. If you don't understand the question, it's hard to form a tentative answer in your mind.

- Not refer to the author or text: Not mentioning the author or text in the question keeps it concise. Every question is subjective in the sense that its answer is given by the author and found in the text, so there's no need to point this out.

- Be as concise as possible: The shorter the question, the better. Shorter questions help you quickly understand what's being asked, allowing you to focus more on the text, not the questions themselves.

> Can an answer include content from multiple HTML elements?

Yeah, absolutely. So whether your answer is spread across a bunch of `<div>`s, `<span>`s, or whatever other tags, `quest` will handle it. As long as the content is contiguous in the original text, `quest` will be able to identify it as the answer. This way, if someone decides to change up the HTML structure but keeps the content the same, `quest` will still find answer.

> Can an answer span multiple paragraphs?

You bet! Paragraphs are often wrapped in separate `<p>` tags. Since `quest` can deal with multiple HTML elements, it's got no problem handling multiple paragraphs.

> Can an answer be any part of the text?

Technically, yes, an answer can be any part of the text. However, it is recommended that each answer should:

- Answer the question: This way, you can evaluate the quality of the Q&A pair without having to go back to the original document.

- Be unique in the text: A unique answer makes it easier to pinpoint the right segment in the text when checking the Q&A pair.

- Be unique in the YAML file: If different questions have the same answer, the system will only keep one of them and throw the rest away.

- Avoid ending mid-sentence: Ending an answer mid-sentence can make the reading experience a bit jarring, as the next segment would pick up in the middle of a sentence.

- Be as concise as possible: The shorter the answer, the easier and quicker it is to evaluate the Q&A pair. It also makes the program run more efficiently by speeding up string searches.

> Which instance does `quest` use if the answer shows up multiple times in the text? (Planned)

If the answer appears more than once in the text, `quest` will pick the first one. Here's why:

- Evaluation: This approach makes it easier for anyone manually checking the Q&A pairs, as the first instance is usually the quickest to locate.

- Processing: It's simpler and faster for the program to use the first match it finds when sorting questions based on where the answers appear in the text.

> Will `quest` crash if an answer is not found in the text? (Planned)

Nah. `quest` isn't gonna crash just because it can't find an answer. If `quest` can't find an answer in the text that matches what's in the YAML file, it'll just skip that question.

> Does `quest` autosave? (Planned)

Yes, `quest` autosaves your progress. It's like they say, "It's better to be saved than sorry." You can just stay focused on what you're reading.

> Does `quest` allow you to create highlights?

No, `quest` doesn't allow you to create highlights. This is intentional. By not allowing highlighting, `quest` encourages you to rely on memory, which might improve long-term recall and comprehension.

> Does `quest` allow you to take notes?

No, `quest` doesn't allow you to take notes. This is also by design. The idea here is that the need for extensive note-taking often suggests an issue with the content itself. If readers frequently feel the need to take notes for later clarification, it could mean that the content isn't explained clearly enough. The responsibility for easily digestible content lies with the content creator, not the reader. For highly complex content, well-structured external resources, like detailed notes made by experts, tend to be more valuable than personal notes. These resources benefit all readers and are likely more accurate.

> Does `quest` use DataScript?

Nope, `quest` doesn't use DataScript. It sticks to good old plain ClojureScript data structures. Here's why:

- Overhead: You don't have to deal with the extra setup and configuration that DataScript would need.

- Reasoning: Using plain data structures makes it easier to wrap your head around what's going on and to debug when things go wrong.

> Does `quest` store data on a server?

No, `quest` doesn't store data on a server. Using server storage would mean setting up server-side infrastructure.

> Does `quest` use the File System Access API for storing data?

No, `quest` doesn't use the File System Access API. This API requires you to pick files or directories through a dialog, which could disrupt the flow of using the extension.

> Does `quest` use chrome.storage.sync for storing data?

No, `quest` doesn't rely on chrome.storage.sync. "[The quota limitation is approximately 100 KB](https://developer.chrome.com/docs/extensions/reference/api/storage#storage_areas:~:text=The%20quota%20limitation%20is%20approximately%20100%20KB)", which is far too limited for the amount of data `quest` needs to store.

> Does `quest` use IndexedDB for storing data?

No, `quest` doesn't bother with IndexedDB. The extra complexity, especially with how IndexedDB is asynchronous, would just make the code harder to manage.

> Does `quest` use `chrome.storage.local` or `localStorage` for storing data? (Planned)

`quest` goes with `chrome.storage.local`. Here's why:

- `chrome.storage.local` is tucked away in the extension, so website scripts can't mess with it. That's a big plus over `localStorage`, which can be accessed by any script running on the website.

- `localStorage` can get wiped out when you clear your browsing data. That's not great if you want your progress to stick around. On the flip side, `chrome.storage.local` is more reliable for keeping your data safe because it doesn't get cleared as easily.
