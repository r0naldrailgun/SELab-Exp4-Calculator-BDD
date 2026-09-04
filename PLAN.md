# BDD Calculator: addition, multiplication, division, and exponentiation

## Context

Extend the calculator to accept two integer inputs and one operator: `+`, `*`, `/`, or `^`. The deliverable must demonstrate both ordinary Cucumber scenarios and a Scenario Outline, execute through the existing Maven/JUnit runner, and show every scenario passing. The existing project uses Cucumber 1.2.5 and JUnit 4; it must run Maven with JDK 8.

## Approach

1. Define the calculation contract in `Calculator`.
   - Retain addition and implement explicit multiplication, division, and exponentiation methods.
   - Return `double` from division and exponentiation so that division remains mathematically correct (for example, `5 / 2` can be `2.5`). Multiplication may remain integer internally but is asserted as a numeric result in the tests.
   - For division, reject a second operand of zero with an `ArithmeticException` and a clear message.
   - For exponentiation, start at `1` and multiply by the base repeatedly. Support zero and positive exponents; `base ^ 0` must return `1`.
   - Do not use `Math.pow`, an enum, a UI, or new dependencies.

2. Replace the old addition Stepdefs with operator-driven calculator Stepdefs.
   - Keep the existing `@Before` setup and create one `Calculator` per scenario.
   - Parse signed integer operands and store the selected operator.
   - Route `+`, `*`, `/`, and `^` to the corresponding `Calculator` method with a small `switch` statement.
   - Store the numeric result as `double` and assert it with `Assert.assertEquals(expected, actual, 0.0001)`.
   - Add dedicated steps for the divide-by-zero scenario: execute the attempted division, capture its exception, then assert that it is an `ArithmeticException`.

3. Replace `calculator.feature` with both required styles of BDD coverage.
   - Add four ordinary scenarios: one each for addition (`6 + 2 = 8`), multiplication (`6 * 2 = 12`), division (`6 / 2 = 3`), and exponentiation (`6 ^ 2 = 36`).
   - Add one ordinary scenario confirming that division by zero is rejected.
   - Add one `Scenario Outline` with the exact required columns: `first`, `second`, `opt`, and `result`.
   - Put the assignment's examples in the Outline table: `6 | 2 | * | 12`, `6 | 2 | / | 3`, and `6 | 2 | ^ | 36`; retain one addition example: `6 | 2 | + | 8`.
   - Keep `Feature: Calculator` as the first meaningful line; Cucumber cannot parse an Outline before a Feature header.

4. Reuse the current `RunnerTest` configuration.
   - Verify that its feature path remains `src/test/resources/features` and that it discovers the updated file.
   - Change `RunnerTest.java` only if the existing path or Cucumber annotations are no longer correct; no new runner is needed.

5. Run the Maven `test` lifecycle with JDK 8 and inspect the Cucumber report.
   - The expected successful run has nine executed scenarios: four ordinary success cases, one ordinary zero-division case, and four Outline examples.
   - Record any implementation deviation in `IMPLEMENTATION_NOTES.md`.

## Key decisions

- **Separate methods rather than one generic calculator method:** `add`, `multiply`, `divide`, and `power` make the beginner-level exercise readable, let each operation be tested directly, and avoid unnecessary abstractions.
- **Repeated multiplication for power:** this follows the stated assignment requirement; `Math.pow` is deliberately not used.
- **Floating-point result assertion:** division can have a fractional result, so expected and actual values use a delta of `0.0001` rather than direct equality.
- **Division by zero is a BDD case:** it must throw `ArithmeticException`, rather than silently return a numeric value.
- **JDK 8 for test execution:** Cucumber 1.2.5 cannot run under modern module-enforced JDKs; Maven's Runner JRE must be configured to JDK 8, not merely the compiler source/target properties.

## Files to modify

- `src/main/java/calculator/Calculator.java`
  - Retain `add` and add the three calculator operations with their validation.
- `src/test/java/calculator/MyStepdefs.java`
  - Use generic operator execution for addition, multiplication, division, and exponentiation, plus numeric assertions and divide-by-zero steps.
- `src/test/resources/features/calculator.feature`
  - Cover addition and the required operations with ordinary scenarios, the error scenario, and the required Outline table.
- `src/test/java/calculator/RunnerTest.java`
  - Verify only; modify only if feature discovery is not working.
- `IMPLEMENTATION_NOTES.md`
  - Keep a short record of deviations and final test evidence while implementing.

## Out of scope

- Negative exponents: repeated multiplication is defined here for zero and positive exponents only.
- Operator parsing beyond `+`, `*`, `/`, and `^`.
- A graphical calculator, user input UI, persistence, or additional Maven dependencies.
- Replacing the assignment's legacy Cucumber/JUnit versions with modern Cucumber.

## STOP conditions

- Maven reports a Java version above 8, or repeats the `InaccessibleObjectException` from old Cucumber/XStream. Stop and configure IntelliJ's Maven Runner JRE to JDK 8 before changing code.
- The required assessment explicitly demands negative exponents or a different division-by-zero behavior. Stop and obtain the expected rule before extending the contract.
- `RunnerTest` cannot discover the feature after confirming it is under `src/test/resources/features`; stop and inspect its Cucumber configuration rather than adding a second runner.

## Acceptable finish

`mvn test` run with JDK 8 completes with `BUILD SUCCESS`; all nine scenarios pass; the report shows the four regular calculation cases, the divide-by-zero case, and the four Scenario Outline examples. The final entry in `IMPLEMENTATION_NOTES.md` records that result or an actual stop condition.

## Verification

1. In IntelliJ, set **Settings > Build, Execution, Deployment > Build Tools > Maven > Runner > JRE** to installed JDK 8.
   - Expected: `mvn -version` reports `Java version: 1.8`.
2. Run Maven **Lifecycle > test**, or run `mvn test` from the project directory.
   - Expected: `BUILD SUCCESS`, nine scenarios executed, zero failures and zero errors.
3. Inspect the Cucumber output.
   - Expected: regular scenarios validate `8`, `12`, `3`, and `36`; the Outline repeats those four outcomes; the zero-division scenario validates `ArithmeticException`.

## Plan validation

Answers the request: `## Context` and `## Approach` cover all specified operations, both BDD styles, code, execution, and passing results.

Answers landed: 5 of 5 decisions recorded - separate methods, repeated multiplication, double division, tolerance `0.0001`, both scenario styles plus zero-division coverage.

Scope gate: `## Approach`, `## Key decisions`, and `## Out of scope` use existing files and dependencies only; no UI, enum, or framework extension is introduced.

Assumptions explicit: `## Out of scope` and `## STOP conditions` state non-negative exponents, JDK 8, and error behavior.

Verification: the three commands/checks in `## Verification` prove the real Maven/Cucumber execution path.
