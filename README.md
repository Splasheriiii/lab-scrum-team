# Командная разработка кода (дисциплина “Проектирование информационных систем”)

Репозиторий содержит четыре самостоятельных Java-проекта: **Lab2, Lab3, Lab4, Lab5**.
Система сборки — Gradle (через wrapper), версия Java — 21, тестовый фреймворк — JUnit 5.

Предварительная установка Gradle и JDK не требуется (исключение — Eclipse, см. ниже):
при первой сборке wrapper и toolchain автоматически загрузят необходимые компоненты.

## Структура репозитория

```
├── settings.gradle.kts          # перечень модулей
├── gradlew.bat                  # сборка из командной строки (Windows)
├── modules/
│   ├── Lab2/                    # Модель Джелинского-Моранды
│   │   │ 
│   │   ├── build.gradle.kts     # конфигурация проекта
│   │   └── src/
│   │       ├── main/java/lab2/  # исходный код
│   │       └── test/java/lab2/  # тесты
│   │
│   │
│   ├── Lab3/  (пакет lab3)      # Метрики Холстеда
│   ├── Lab4/  (пакет lab4)      # Статический анализ кода
│   └── Lab5/  (пакет lab5)      # Оценка качества ПО по ГОСТ 28195-89
└── .github/workflows/ci.yml     # CI: сборка и тесты
```

Работу следует вести только в закреплённом за вами модуле (Lab3 и т.д.).
Имена пакетов не переименовывать.

## Сборка из командной строки

```bat
gradlew.bat build                  :: сборка и тесты всех проектов
gradlew.bat :modules:Lab3:build    :: только Lab3
gradlew.bat :modules:Lab3:test     :: только тесты Lab3
```

## IntelliJ IDEA (рекомендуемая среда)

1. Установите IntelliJ IDEA Community Edition: https://www.jetbrains.com/idea/download
2. **File → Open…** → выберите корневую папку репозитория (содержащую `settings.gradle.kts`) → Open.
3. IDEA распознает Gradle-проект автоматически. Подтвердите «Trust project»
   и согласитесь на импорт Gradle-проекта, если будет предложено.
4. Дождитесь завершения индексации (в правом нижнем углу). При первом открытии
   загружаются Gradle и JDK — это может занять несколько минут.
5. Запуск теста: откройте `MainTest.java` и нажмите зелёную стрелку слева от объявления класса.
   Запуск приложения: откройте `Main.java` и нажмите стрелку рядом с методом `main`.
6. Встроенный терминал IDEA поддерживает команду `gradlew.bat build`.

Если JDK не установлен — препятствий нет: Gradle загрузит JDK 21 самостоятельно (toolchain).
Альтернативно: File → Project Structure → SDK → Download JDK → версия 21 (Temurin).

## Eclipse

1. Установите Eclipse IDE for Java Developers (2024-03 или новее): https://www.eclipse.org/downloads/
2. Поддержка Gradle в Eclipse реализована плагином Buildship, входящим в данный пакет.
3. **File → Import… → Gradle → Existing Gradle Project** → Next → выберите корневую папку
   репозитория → Next → Finish.
4. Дождитесь завершения импорта (при первом запуске загружаются зависимости).
5. Запуск теста: правый клик на `MainTest.java` → Run As → JUnit Test.
   Запуск приложения: правый клик на `Main.java` → Run As → Java Application.

Если Eclipse сообщает об отсутствии JDK: Window → Preferences → Java → Installed JREs →
Add → Standard VM → укажите каталог JDK 21 (при отсутствии — установите Temurin:
https://adoptium.net/temurin/releases/?version=21).

## VS Code

1. Установите VS Code: https://code.visualstudio.com
2. В разделе расширений (Ctrl+Shift+X) установите пакет **"Extension Pack for Java"**
   (Microsoft) — он включает все необходимые компоненты, в том числе поддержку Gradle.
3. **File → Open Folder…** → выберите папку репозитория.
4. Модули Lab2–Lab5 отображаются в панели Java-проектов в левой части окна.
5. Запуск теста: откройте `MainTest.java` — над объявлением класса появятся ссылки `Run | Debug`.
   Запуск приложения: откройте `Main.java` — ссылка `Run` над методом `main`.
6. Полная сборка: терминал (`Ctrl+`) → `gradlew.bat build`.

## Непрерывная интеграция (CI)

При каждом push в ветку `main` и при каждом Pull Request GitHub Actions выполняет
`.github/workflows/ci.yml`: сборка и тесты всех четырёх проектов на виртуальной машине
GitHub (`windows-latest`). 

Детали доступны на вкладке Actions.

## Правила работы с репозиторием

- Разработку вести в отдельной ветке: `git checkout -b feature/lab-n`.
  Прямые push в `main` запрещены — изменения попадают в основную ветку только через
  Pull Request.
- Коммитить следует только содержимое `src/` и `build.gradle.kts`. Каталоги `build/`,
  `.gradle/`, `.idea/` не коммитить (уже добавлены в `.gitignore`).
- Перед созданием Pull Request выполните `gradlew.bat build` и убедитесь,
  что сборка завершается успешно.
