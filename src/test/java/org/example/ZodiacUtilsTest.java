package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

class ZodiacUtilsTest {

    @MethodSource("getSignNameTestData")
    @ParameterizedTest
    void getSignName(LocalDate beginDate, LocalDate endDate, String signName) {
        for (LocalDate date = beginDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            System.out.println(signName + " " + beginDate + " " + endDate + " ? " + date);

            Assertions.assertEquals(signName, ZodiacUtils.getSignName(date.getDayOfMonth(), date.getMonthValue()));
        }

    }

    private static Object[][] getSignNameTestData() {
        return new Object[][] {
                {LocalDate.parse("2023-03-21"), LocalDate.parse("2023-04-19"), "Овен"},
                {LocalDate.parse("2023-04-20"), LocalDate.parse("2023-05-20"), "Телец"},
                {LocalDate.parse("2023-05-21"), LocalDate.parse("2023-06-20"), "Близнецы"},
                {LocalDate.parse("2023-06-21"), LocalDate.parse("2023-07-22"), "Рак"},
                {LocalDate.parse("2023-07-23"), LocalDate.parse("2023-08-22"), "Лев"},
                {LocalDate.parse("2023-08-23"), LocalDate.parse("2023-09-22"), "Дева"},
                {LocalDate.parse("2023-09-23"), LocalDate.parse("2023-10-22"), "Весы"},
                {LocalDate.parse("2023-10-23"), LocalDate.parse("2023-11-21"), "Скорпион"},
                {LocalDate.parse("2023-11-22"), LocalDate.parse("2023-12-21"), "Стрелец"},
                {LocalDate.parse("2023-12-22"), LocalDate.parse("2024-01-19"), "Козерог"},
                {LocalDate.parse("2024-01-20"), LocalDate.parse("2024-02-18"), "Водолей"},
                {LocalDate.parse("2024-02-19"), LocalDate.parse("2024-03-20"), "Рыбы"},
        };
    }

    public static void main(String[] args) throws Exception {

        System.setOut(new PrintStream("code.txt"));

        String testCodeTemplate = Files.readString(Paths.get("templates.test.txt"));

        List<String> lines = Files.readAllLines(Paths.get("data.txt"));

        YearMonth yearMonth = YearMonth.of(2023, Month.MARCH);

        for (String line : lines) {
            String signName = line.substring(0, line.indexOf(':'));

            String[] lineItems = line.split(" ");
            LocalDate beginDate = LocalDate.parse(yearMonth + "-" + lineItems[2]);
            yearMonth = yearMonth.plusMonths(1);
            LocalDate endDate = LocalDate.parse(yearMonth + "-" + lineItems[5]);

            String testCode = testCodeTemplate
                    .replace("{bd}", beginDate.toString())
                    .replace("{ed}", endDate.toString())
                    .replace("{sn}", signName);

            System.out.println(testCode);
        }
    }
}