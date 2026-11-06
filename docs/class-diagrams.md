# Sơ đồ lớp để đưa vào báo cáo (Mermaid)

## Exercise 6 — Không sửa interface Show

```mermaid
classDiagram
    class Show {
       <<interface>>
       +description() String
       +runningTime() int
       +copy() Show
    }
    class Iterable~Show~ {
       <<interface>>
       +iterator() Iterator~Show~
    }
    Show <|.. Movie
    Show <|.. Concert
    Show <|.. CompositeShow
    Show <|.. IntroducedShow
    Iterable~Show~ <|.. CompositeShow
    CompositeShow o-- "2..5" Show : contains
    IntroducedShow o-- "1" Show : wraps
```

## Exercise 7 — Interface Show kế thừa Iterable

```mermaid
classDiagram
    class Iterable~Show~ {
       <<interface>>
       +iterator() Iterator~Show~
    }
    class Show {
       <<interface>>
       +description() String
       +runningTime() int
       +copy() Show
       +iterator() Iterator~Show~
    }
    Iterable~Show~ <|-- Show
    Show <|.. Movie
    Show <|.. Concert
    Show <|.. CompositeShow
    Show <|.. IntroducedShow
    CompositeShow o-- "2..5" Show : contains
    IntroducedShow o-- "1" Show : wraps
```

Dán mã vào https://mermaid.live/ hoặc app.diagrams.net -> Arrange -> Insert -> Advanced -> Mermaid.
