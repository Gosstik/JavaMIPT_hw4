### Описание

* Реализованы обе части ДЗ: ядро системы и логгирование.

### Детали реализации

* Аннотация `LogTransformation` применяется для методов компонентов системы, а не на самих компонентах. Это сделано для большей гибкости (у компоненты могут быть внутренние методы, которые мы не хотим логировать), а также в силу ограничений `Spring AOP`.

* Для того, чтобы применять аннотацию `LogTransformation`, класс должен быть бином спринга, иначе аннотация не имеет эффекта.

* В реализации каждой компоненты по 2 класса: интерфейс и его `impl` версия. Как я понял, в спринге слабенький кодогенератор для создания прокси, поэтому он не может вместе с `Aspectj` присваивать объект напрямую в случае `Autowire`. Но зато он может присваивать интерфейсу.
