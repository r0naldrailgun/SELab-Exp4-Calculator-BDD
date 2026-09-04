# گزارش پیاده‌سازی ماشین‌حساب مبتنی بر BDD

## هدف تمرین

پیاده‌سازی و آزمون یک ماشین‌حساب برای دو عدد صحیح و چهار عملگر زیر، با استفاده از Java، Maven، JUnit و Cucumber:

- جمع: `+`
- ضرب: `*`
- تقسیم: `/`
- توان: `^`

طبق صورت مسئله، تست‌ها باید هم به شکل سناریوی معمولی و هم به شکل `Scenario Outline` نوشته شوند.

## محیط اجرا

- IntelliJ IDEA 2026.2.1
- Maven
- JUnit 4.12
- Cucumber 1.2.5
- Eclipse Temurin JDK 8 (`1.8.0_504`)

## روند انجام کار

1. یک پروژهٔ Maven با نام `calculator-bdd` ایجاد شد.
2. وابستگی‌های Cucumber و JUnit به `pom.xml` اضافه شدند.
3. خطای اولیهٔ Maven با پیام `Expected root element 'project'` رخ داد؛ دلیل آن شروع شدن فایل `pom.xml` با تگ `dependencies` بود. ساختار کامل و صحیح `project` به فایل اضافه شد.
4. ساختار استاندارد Maven برای کد اصلی، Step Definitionها و فایل feature ایجاد شد.
5. در اجرای نخست، Cucumber 1.2.5 روی Java جدید با خطای `InaccessibleObjectException` روبه‌رو شد. علت، ناسازگاری Cucumber قدیمی با محدودیت ماژول‌های Java جدید بود.
6. JDK 8 نصب و Maven با JDK 8 اجرا شد؛ خطای ناسازگاری برطرف شد.
7. یک خطای Gherkin نیز رخ داد، زیرا فایل feature با `Scenario Outline` شروع می‌شد. با افزودن `Feature: Calculator` در ابتدای فایل، مشکل برطرف شد.
8. عملیات جمع، ضرب، تقسیم و توان با سناریوهای BDD کامل شدند.

## پیاده‌سازی نهایی

### کلاس `Calculator`

- `add(first, second)` جمع دو عدد را محاسبه می‌کند.
- `multiply(first, second)` حاصل‌ضرب دو عدد را محاسبه می‌کند.
- `divide(first, second)` نتیجهٔ تقسیم را به شکل `double` برمی‌گرداند تا تقسیم‌های اعشاری نیز درست باشند.
- تقسیم بر صفر یک `ArithmeticException` با پیام مشخص ایجاد می‌کند.
- `power(base, exponent)` توان را با ضرب تکراری محاسبه می‌کند؛ از `Math.pow` استفاده نشده است.
- توان صفر برابر `1` است و توان منفی خارج از محدودهٔ این تمرین در نظر گرفته شده است.

### تست‌های BDD

فایل `calculator.feature` شامل موارد زیر است:

- سناریوی عادی برای جمع: `6 + 2 = 8`
- سناریوی عادی برای ضرب: `6 * 2 = 12`
- سناریوی عادی برای تقسیم: `6 / 2 = 3`
- سناریوی عادی برای توان: `6 ^ 2 = 36`
- سناریوی عادی برای بررسی خطای تقسیم بر صفر
- یک `Scenario Outline` با جدول نمونه‌های خواسته‌شده:

| first | second | opt | result |
| --- | --- | --- | --- |
| 6 | 2 | `+` | 8 |
| 6 | 2 | `*` | 12 |
| 6 | 2 | `/` | 3 |
| 6 | 2 | `^` | 36 |

نتایج `double` با خطای مجاز `0.0001` مقایسه می‌شوند تا مقایسهٔ اعداد اعشاری قابل‌اعتماد باشد.

## نتیجهٔ اجرای تست

دستور اجرا:

```text
mvn test
```

نتیجهٔ نهایی با JDK 8:

```text
9 Scenarios (9 passed)
27 Steps (27 passed)
BUILD SUCCESS
```

گزارش Maven نیز نشان داد:

```text
Tests run: 36, Failures: 0, Errors: 0, Skipped: 0
```

## فایل‌های مهم

- `src/main/java/calculator/Calculator.java`: منطق عملیات ماشین‌حساب
- `src/test/java/calculator/MyStepdefs.java`: تبدیل سناریوهای Gherkin به تست Java
- `src/test/java/calculator/RunnerTest.java`: اجرای Cucumber با JUnit
- `src/test/resources/features/calculator.feature`: سناریوهای عادی و Outline
- `PLAN.md`: برنامهٔ پیاده‌سازی تهیه‌شده پیش از کدنویسی
- `IMPLEMENTATION_NOTES.md`: یادداشت‌های پیاده‌سازی و نتیجهٔ تست

## راهنمای اجرای مجدد در IntelliJ

1. در IntelliJ به مسیر **Settings > Build, Execution, Deployment > Build Tools > Maven > Runner** بروید.
2. گزینهٔ **JRE** را روی JDK 8 تنظیم کنید.
3. در پنجرهٔ Maven، گزینهٔ **Lifecycle > test** را اجرا کنید.
4. باید پیام `BUILD SUCCESS` و عبور تمام سناریوها نمایش داده شود.
