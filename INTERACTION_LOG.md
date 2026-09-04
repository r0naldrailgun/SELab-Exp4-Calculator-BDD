# مستند تعاملات با دستیار در انجام آزمایش BDD

**دانشجو:** امیرهمایون شریفی‌زاده

**شماره دانشجویی:** 401106114

## هدف تعامل

هدف، خواندن مستند آزمایش BDD، ساخت پروژهٔ Maven و Cucumber، پیاده‌سازی ماشین‌حساب، رفع خطاها، اجرای تست‌ها، تهیهٔ گزارش، و آماده‌سازی repository در GitHub بود.

## روند گفت‌وگو و اقدامات انجام‌شده

### ۱. مطالعهٔ مستند اولیه

- شما فایل `Example.pdf` را فرستادید و درخواست راهنمای گام‌به‌گام کردید.
- مستند بررسی شد و مشخص شد که تمرین اولیه مربوط به Cucumber، JUnit، Maven و سناریوی جمع دو عدد است.
- مراحل ایجاد پروژه، افزودن dependencyها، ساخت فایل feature، Step Definitionها و RunnerTest توضیح داده شد.

### ۲. ساخت پروژهٔ Maven

- شما پرسیدید که پروژهٔ Maven در IntelliJ چگونه ایجاد می‌شود.
- مسیر `File → New Project → Maven` و انتخاب JDK توضیح داده شد.
- سپس نحوهٔ Import کردن تغییرات Maven و اجرای `Lifecycle → test` توضیح داده شد.

### ۳. رفع خطای `pom.xml`

- در اولین اجرای Maven، خطای زیر مشاهده شد:

  ```text
  Expected root element 'project' but found 'dependencies'
  ```

- علت مشخص شد: فایل `pom.xml` با تگ `<dependencies>` آغاز شده بود، در حالی که Maven به تگ ریشهٔ `<project>` نیاز دارد.
- یک ساختار کامل و معتبر برای `pom.xml` شامل Cucumber، JUnit و تنظیم Java 8 ارائه و اعمال شد.

### ۴. ایجاد ساختار و کد اولیهٔ BDD

- فایل‌های زیر ایجاد شدند:

  ```text
  src/main/java/calculator/Calculator.java
  src/test/java/calculator/MyStepdefs.java
  src/test/java/calculator/RunnerTest.java
  src/test/resources/features/calculator.feature
  ```

- مثال اولیهٔ جمع دو عدد با Cucumber اجرا شد.

### ۵. رفع ناسازگاری Cucumber و Java

- اجرای تست با Cucumber 1.2.5 روی Java جدید با خطای `InaccessibleObjectException` شکست خورد.
- دلیل این بود که نسخهٔ قدیمی Cucumber/XStream با محدودیت ماژول‌های Java جدید سازگار نیست.
- با درخواست شما، JDK 8 از Eclipse Temurin نصب شد و Maven Runner روی JDK 8 تنظیم گردید.
- پس از تغییر JDK، تست‌ها با موفقیت اجرا شدند.

### ۶. رفع خطای parse شدن فایل feature

- هنگام اجرای `Scenario Outline` خطای زیر دیده شد:

  ```text
  Found scenario_outline when expecting one of: comment, feature, tag
  ```

- علت این بود که فایل `.feature` با `Scenario Outline` شروع شده بود.
- راه‌حل: اضافه شدن خط زیر در ابتدای فایل:

  ```gherkin
  Feature: Calculator
  ```

### ۷. برنامه‌ریزی و توسعهٔ ماشین‌حساب

- شما مسئلهٔ توسعهٔ ماشین‌حساب برای عملگرهای ضرب، تقسیم و توان را مطرح کردید.
- دربارهٔ طراحی تصمیم‌گیری شد که هر عمل در متد جداگانه قرار بگیرد:

  ```text
  add, multiply, divide, power
  ```

- دربارهٔ توان، ابتدا استفاده از `Math.pow` مطرح شد؛ سپس طبق صورت مسئله تصمیم گرفته شد توان با ضرب تکراری پیاده‌سازی شود.
- برای تقسیم، خروجی `double` و برای تقسیم بر صفر، `ArithmeticException` انتخاب شد.
- برای نتیجه‌های اعشاری، مقایسه با `delta = 0.0001` انتخاب شد.

### ۸. نوشتن سناریوهای عادی و Scenario Outline

- سناریوهای عادی برای جمع، ضرب، تقسیم، توان و تقسیم بر صفر نوشته شدند.
- یک `Scenario Outline` برای اجرای چند مثال با ستون‌های `first`، `second`، `opt` و `result` ایجاد شد.
- عملگرهای `+`، `*`، `/` و `^` در Step Definitionها با `switch` به متد متناظر متصل شدند.

### ۹. خطای Undefined برای عدد منفی

- در مستند اولیه، ردیف `-1 | 6 | 5` در Scenario Outline با خطای `Undefined Step` روبه‌رو می‌شد.
- علت: regex اولیه یعنی `(\d+)` علامت منفی را نمی‌پذیرفت.
- راه‌حل: regex به `(-?\d+)` تغییر داده شد تا عددهای منفی و مثبت هر دو پذیرفته شوند.
- برای اطمینان از رفع مشکل، ردیف زیر به Scenario Outline نهایی اضافه شد:

  ```text
  | -1 | 6 | + | 5 |
  ```

### ۱۰. نتیجهٔ نهایی تست‌ها

- Maven با JDK 8 اجرا شد.
- نتیجهٔ نهایی:

  ```text
  10 Scenarios (10 passed)
  30 Steps (30 passed)
  Tests run: 40, Failures: 0, Errors: 0, Skipped: 0
  BUILD SUCCESS
  ```

### ۱۱. مستندسازی و GitHub

- یک `README.md` فارسی شامل هدف آزمایش، کدها، سناریوها، خطاها، راه‌حل‌ها و تصاویر شما ایجاد شد.
- شش screenshot گرفته‌شده توسط شما در پوشهٔ `screenshots/` قرار گرفتند.
- repository محلی Git ساخته شد و commitهای زیر ایجاد شدند:

  ```text
  chore: initialize Maven calculator project
  feat: add BDD calculator operations
  docs: add BDD implementation report
  docs: add illustrated Persian README
  ```

- repository به آدرس زیر متصل و روی branch `main` push شد:

  ```text
  https://github.com/r0naldrailgun/SELab-Exp4-Calculator-BDD
  ```

## جمع‌بندی

در این تعامل، پروژه از ایجاد Maven و پیکربندی dependencyها تا پیاده‌سازی BDD، رفع خطاهای `pom.xml`، Java/Cucumber، parse فایل feature و Undefined Step پیش رفت. نتیجهٔ نهایی یک ماشین‌حساب تست‌شده با سناریوهای عادی و Scenario Outline، README تصویری و repository آمادهٔ GitHub است.
