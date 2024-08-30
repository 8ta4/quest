# quest

## Goals

### Random Access

> Can you freely move between segments when using `quest` for reading?

Yep, with `quest`, you can jump around between segments as you like. It keeps track of what you've already read, so you don't have to. This way, you can explore at your own pace and follow your curiosity.

### Keyboard

> Can you control `quest` entirely with your keyboard?

Absolutely, you can navigate `quest` entirely using keyboard shortcuts. It's quicker and helps you stay in the zone.

> Do `quest`'s shortcuts conflict with Vimium?

Nope, `quest`'s shortcuts are designed to avoid any conflicts with Vimium.

### Coverage

> Do the questions in `quest` form a bijection with the content?

Yeah, absolutely. The questions in `quest` are designed to form a bijection with the content. So if you go through all the questions and read the text they reveal, you'll cover everything. This isn't just about learning; it's about earning the right to say, "I've read this book." This can boost your confidence in discussions, give you credibility among peers, and provide a sense of accomplishment.

## Functionality

### Navigation

> When a user reveals an answer, how does the viewport adjust?

When someone reveals an answer, the viewport does its thing and automatically scrolls to the start of the current segment. You probably expect to see new stuff when you do something like revealing an answer. Plus, it nudges you to read through the whole segment from the top. That way, you've got all the context before you reaching the answer.

> Is navigation undoable in `quest`?

No, you can't undo navigation in `quest`. It follows a similar approach to Vim.

> Does `quest` remember where I left off reading?

Yes, `quest` automatically saves your reading progress. When you reopen the application, it picks up from the last question you were viewing and maintains your scrolling position.

> Does `quest` assume a left-to-right language or right-to-left language?

`quest` is mainly designed for left-to-right (LTR) languages. The segment indicators are placed in the left margin to fit LTR reading habits. This makes it easier for readers to spot the segment boundaries as they read from left to right.

### Data

> What's the query string parameter used to specify a YAML file?

The query string parameter you need to specify a YAML file is simply `quest`.

Let's take Paul Graham's "How to Start a Startup" essay as an example. To add `quest` functionality to it, you'd take the essay's URL and tack on the `quest` parameter. This parameter points to a YAML file. It'd look something like this:

TODO: Replace `[HASH]` with the actual commit hash once available.

```
https://paulgraham.com/start.html?quest=https://github.com/8ta4/quests/blob/[HASH]/paulgraham.com/start.html
```

> Can I host the YAML file anywhere other than GitHub?

Yes, you can host the YAML file wherever you like, as long as it's accessible to users.

> Does the URL for the YAML file need to contain a hash?

Technically, no, it doesn't need a hash. But it's recommended to include one, or use some other method to ensure the URL points to the exact content you want. This makes sure that everyone accessing the link gets the same version.

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

> Is the YAML file required to be sorted?

No, the YAML file doesn't need to be sorted. The software takes care of the ordering based on the content.

> Does `quest` generate questions?

Nope, `quest` doesn't generate questions. Even though AI might get there someday, it's been pretty tough to make sure the questions meet specific requirements right now.

> Does a question correspond to a book section?

Not necessarily. A question in `quest` doesn't always align with a specific book section. It might cover part of a section, multiple sections, or even span across chapter boundaries.

> Can a question be arbitrary text?

Technically, yes, a question in `quest` can be pretty much any text you want. But it's recommended that each question should:

- Have an answer in the text: Including the answer directly in the text provides immediate validation and prevents the frustration of encountering a question you can't answer.

- Be a binary choice: This helps you make quick decisions, keeping your reading momentum going. It also makes it easier to provide clear right or wrong feedback.

- Not use terms unique to the author or text: Avoiding these terms ensures that you can understand the question even if you haven't read the text first. If you don't understand the question, it's hard to form a tentative answer in your mind.

- Not refer to the author or text: Not mentioning the author or text in the question keeps it concise. Every question is subjective in the sense that its answer is given by the author and found in the text, so there's no need to point this out.

- Be as concise as possible: The shorter the question, the better. Concise questions help users quickly understand what's being asked. This way, users can focus more on the text, not the questions themselves.

> Can an answer include content from multiple HTML elements?

Yeah, absolutely. So whether your answer is spread across a bunch of `<div>`s, `<span>`s, or whatever other tags, `quest` will handle it. As long as the content is contiguous in the original text, `quest` will be able to identify it as the answer. This way, if someone decides to change up the HTML structure but keeps the content the same, `quest` will still find answer.

> Can an answer span multiple paragraphs?

You bet! Paragraphs are often wrapped in separate `<p>` tags. Since `quest` can deal with multiple HTML elements, it's got no problem handling multiple paragraphs.

> Can an answer be any part of the text?

Technically, yes, an answer can be any part of the text. However, it is recommended that each answer should:

- Answer the question: This way, you can evaluate the quality of the Q&A pair without having to go back to the original document.

- Be unique: A unique answer makes it easier to pinpoint the right segment in the text when checking the Q&A pair.

- Avoid ending mid-sentence: Ending an answer mid-sentence can make the reading experience a bit jarring, as the next segment would pick up in the middle of a sentence.

- Be as concise as possible: The shorter the answer, the easier and quicker it is to evaluate the Q&A pair. It also makes the program run more efficiently by speeding up string searches.

> If the text contains multiple instances of the answer, which one will `quest` use as the corresponding segment?

If the answer appears more than once in the text, `quest` will go with the first occurrence. This makes things easier for anyone manually evaluating the Q&A pairs, as the first instance is usually the quickest to find. Plus, it's simpler and faster for the program to use the first match it finds when sorting questions based on where the answers appear in the text.

> Will `quest` crash if an answer is not found in the text?

Nah. `quest` isn't gonna crash just because it can't find an answer. If `quest` can't find an answer in the text that matches what's in the YAML file, it'll just skip that question.

> Does `quest` autosave?

Yes, `quest` autosaves your progress. It's like they say, "It's better to be saved than sorry." You can just stay focused on what you're reading.

> Does `quest` allow you to create highlights?

No, `quest` doesn't allow you to create highlights. This is by design. By not allowing highlighting, `quest` encourages users to rely on memory, which might improve long-term recall and comprehension.

> Does `quest` allow you to take notes?

No, `quest` doesn't allow you to take notes. This is also by design. The idea here is that the need for extensive note-taking often suggests an issue with the content itself. If readers frequently feel the need to take notes for later clarification, it could mean that the content isn't explained clearly enough. The responsibility for easily digestible content lies with the content creator, not the reader. For highly complex content, well-structured external resources, like detailed notes made by experts, tend to be more valuable than personal notes. These resources benefit all readers and are likely more accurate.

> Does `quest` store data on a server?

No, `quest` doesn't store data on a server. Using server storage would mean setting up server-side infrastructure.

> Does `quest` use the File System Access API for storing data?

No, `quest` doesn't use the File System Access API. This API requires the user to pick files or directories through a dialog, which could disrupt the flow of using the extension.

> Does `quest` use chrome.storage.sync for storing data?

No, `quest` doesn't rely on chrome.storage.sync. "[The quota limitation is approximately 100 KB](https://developer.chrome.com/docs/extensions/reference/api/storage#storage_areas:~:text=The%20quota%20limitation%20is%20approximately%20100%20KB)", which is far too limited for the amount of data `quest` needs to store.

> Does `quest` use IndexedDB for storing data?

No, `quest` doesn't use IndexedDB for saving data. IndexedDB is asynchronous, but DataScript [needs synchronous operations](https://github.com/tonsky/datascript/issues/358#issuecomment-657068278).

> Does `quest` use `chrome.storage.local` or `localStorage` for storing data?

`quest` goes with `chrome.storage.local`. Here's why:

- `chrome.storage.local` is tucked away in the extension, so website scripts can't mess with it. That's a big plus over `localStorage`, which can be accessed by any script running on the website.

- `localStorage` can get wiped out when users clear their browsing data. That's not great if you want your progress to stick around. On the flip side, `chrome.storage.local` is more reliable for keeping your data safe because it doesn't get cleared as easily.
