# quest

## Goals

### Random Access

> Can you freely move between sections when using `quest` for reading?

Yep, with `quest`, you can jump around between sections as you like. It keeps track of what you've already read, so you don't have to. This way, you can explore at your own pace and follow your curiosity.

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
    A startup that does all three will probably succeed.
- question: |
    Is getting rich doable?
  answer: |
    getting rich is doable too. Hard, but doable.
```

> Is the YAML file required to be sorted?

No, the YAML file doesn't need to be sorted. The software takes care of the ordering based on the content.

> Does `quest` generate questions?

Nope, `quest` doesn't generate questions. Even though AI might get there someday, it's been pretty tough to make sure the questions meet specific requirements right now.

> Does a question correspond to a book section?

Not necessarily. A question in `quest` doesn't always align with a specific book section. It might cover part of a section, multiple sections, or even span across chapter boundaries.

> Can a question be arbitrary text?

Technically, yes, a question in `quest` can be pretty much any text you want. But it's recommended that each question should:

- Be binary choice

- Have an answer in the text

- Not use terms unique to the author or text

- Not refer to the author or text

> Can an answer be an arbitrary part of the text?

Technically, yes, an answer can be any part of the text. However, it's best to avoid ending an answer in the middle of a sentence. Ending an answer mid-sentence can make the reading experience a bit jarring, as the next segment would pick up in the middle of a sentence.

> Does an answer need to be unique within the text?

No, an answer doesn't have to be unique. If the text contains multiple instances of the answer, `quest` will use the first instance as the corresponding segment for the question-answer pair.

> Does `quest` autosave?

Yes, `quest` autosaves your progress. It's like they say, "It's better to be saved than sorry." You can just stay focused on what you're reading.

> Does `quest` allow you to create highlights?

No, `quest` doesn't allow you to create highlights. This is by design. By not allowing highlighting, `quest` encourages users to rely on memory, which might improve long-term recall and comprehension.

> Does `quest` allow you to take notes?

No, `quest` doesn't allow you to take notes. This is also by design. The idea here is that the need for extensive note-taking often suggests an issue with the content itself. If readers frequently feel the need to take notes for later clarification, it could mean that the content isn't explained clearly enough. The responsibility for easily digestible content lies with the content creator, not the reader. For highly complex content, well-structured external resources, like detailed notes made by experts, tend to be more valuable than personal notes. These resources benefit all readers and are likely more accurate.
