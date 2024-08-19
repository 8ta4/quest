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

## Functionality

### Navigation

> When a user reveals an answer, how does the viewport adjust?

When someone reveals an answer, the viewport does its thing and automatically scrolls to the start of the current segment. You probably expect to see new stuff when you do something like revealing an answer. Plus, it nudges you to read through the whole segment from the top. That way, you've got all the context before you reaching the answer.

> Is navigation undoable in `quest`?

No, you can't undo navigation in `quest`. It follows a similar approach to Vim.

> Does `quest` assume a left-to-right language or right-to-left language?

`quest` is mainly designed for left-to-right (LTR) languages. The segment indicators are placed in the left margin to fit LTR reading habits. This makes it easier for readers to spot the segment boundaries as they read from left to right.

### Data

> Does `quest` generate questions?

Nope, `quest` doesn't generate questions. Even though AI might get there someday, it's been pretty tough to make sure the questions meet specific requirements right now.

> Do the questions in quest form a bijection with the content?

Yeah, absolutely. The questions in quest are designed to form a bijection with the content. So if you go through all the questions and read the text they reveal, you'll cover everything. This way, you can get through the entire material by just following the Q&A, which helps you get a full understanding.

> Do the questions correspond to book sections?

Not necessarily. The questions in `quest` don't always line up with specific book sections. They might cover part of a section, multiple sections, or even cross over chapter boundaries.

> Does `quest` autosave?

Yes, `quest` autosaves your progress. It's like they say, "It's better to be saved than sorry." You can just stay focused on what you're reading.

> Why choose YAML over JSON for import files?

YAML is chosen over JSON because:

- You can add comments.

- It doesn't need extra commas, so it's easier to edit.

> Why the `.yaml` and not `.yml`?

The YAML FAQ recommends "[using '.yaml' when possible.](https://yaml.org/faq.html#:~:text=Is%20there%20an,yaml%22%20when%20possible.)"
