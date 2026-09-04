# گزارش کوتاه تعامل و رفع خطای Scenario Outline

**درس:** آزمایشگاه مهندسی نرم‌افزار

**دانشجو:** امیرهمایون شریفی‌زاده

**شماره دانشجویی:** 401106114

## روند انجام کار

1. پروژهٔ Maven و وابستگی‌های Cucumber و JUnit تنظیم شدند.
2. ساختار BDD شامل فایل feature، Step Definitionها، کلاس `Calculator` و `RunnerTest` ایجاد شد.
3. ناسازگاری Cucumber 1.2.5 با Java جدید شناسایی شد و اجرای Maven با JDK 8 انجام شد.
4. خطای parse مربوط به شروع شدن فایل با `Scenario Outline` برطرف شد؛ هر فایل Gherkin باید با `Feature: Calculator` شروع شود.
5. عملیات جمع، ضرب، تقسیم و توان پیاده‌سازی و با سناریوهای عادی و `Scenario Outline` تست شدند.

## گزارش خطای Undefined در Scenario Outline

**موارد تست مشکل‌دار:** در مثال Scenario Outline مستند، ردیف زیر Undefined می‌شد:

```text
| first | second | result |
| -1    | 6      | 5      |
```

در پروژهٔ نهایی، همین حالت به‌صورت زیر اضافه شده است:

```text
| first | second | opt | result |
| -1    | 6      | +   | 5      |
```

**علت بروز مشکل:** Step Definition اولیه از الگوی `(\\d+)` استفاده می‌کرد. این الگو فقط رقم‌های بدون علامت را می‌پذیرد و مقدار `-1` را قبول نمی‌کند؛ بنابراین Cucumber هیچ Step Definition منطبقی برای مرحلهٔ `Given` پیدا نمی‌کرد و خطای `Undefined Step` رخ می‌داد.

**نحوهٔ رفع مشکل:** الگو در `MyStepdefs.java` به شکل زیر تغییر داده شد:

```java
@Given("^Two input values, (-?\\d+) and (-?\\d+)$")
public void twoInputValuesAnd(int first, int second) {
    value1 = first;
    value2 = second;
}
```

عبارت `-?` یعنی علامت منفی صفر یا یک بار می‌تواند ظاهر شود. در نتیجه، هم اعداد مثبت و هم اعداد منفی با Step Definition تطابق پیدا می‌کنند. ردیف `-1 | 6 | + | 5` نیز به Scenario Outline افزوده شد تا این اصلاح در هر اجرای تست بررسی شود.

## نتیجه

اجرای نهایی `mvn test` با JDK 8 موفق بود:

```text
10 Scenarios (10 passed)
30 Steps (30 passed)
Tests run: 40, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
