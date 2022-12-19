import model.cycleList.CycleList;
import model.factory.FactoryType;
import model.usertype.prototype.UserType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class TestCycleList {
    private FactoryType factory;
    private UserType userType;
    private CycleList actualList, expectedList;

    @BeforeEach
    public void setUp() {
        factory = new FactoryType();
        userType = factory.getBuilderByName("Integer");
        actualList = new CycleList(userType.getTypeComparator());
        expectedList = new CycleList(userType.getTypeComparator());
    }

    // Исходный набор содержит одинаковые значения
    @Test
    public void testSortEqualElements() {
        for (int i = 0; i <= 10; i++) {
            actualList.add(userType.parseValue(String.valueOf(10)));
            expectedList.add(userType.parseValue(String.valueOf(10)));
        }

        actualList.sort();

        assertEquals(actualList.toString(), expectedList.toString());
    }

    // Исходный набор упорядочен в прямом порядке
    @Test
    public void testSortOrderList() {
        for (int i = 0; i <= 10; i++) {
            actualList.add(userType.parseValue(String.valueOf(i)));
        }
        for (int i = 0; i <= 10; i++) {
            expectedList.add(userType.parseValue(String.valueOf(i)));
        }
        actualList.sort();
        assertEquals(actualList.toString(), expectedList.toString());
    }

    // Исходный набор упорядочен в обратном порядке
    @Test
    public void testSortReverseOrderList() {
        for (int i = 10; i >= 0; i--) {
            actualList.add(userType.parseValue(String.valueOf(i)));
        }
        for (int i = 0; i <= 10; i++) {
            expectedList.add(userType.parseValue(String.valueOf(i)));
        }
        actualList.sort();
        assertEquals(actualList.toString(), expectedList.toString());
    }

    // В наборе имеются повторяющиеся элементы
    @Test
    public void testSortRepeatedElements() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "2"));
        for (String number : numbers) {
            actualList.add(userType.parseValue(number));
        }

        actualList.sort();

        Collections.sort(numbers);
        for (String number : numbers) {
            expectedList.add(userType.parseValue(number));
        }
        assertEquals(actualList.toString(), expectedList.toString());
    }

    // В наборе имеются несколько групп повторяющихся элементов
    @Test
    public void testSortRepeatedGroups() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("8", "8", "4", "7", "2", "2"));
        for (String number : numbers) {
            actualList.add(userType.parseValue(number));
        }

        actualList.sort();

        Collections.sort(numbers);
        for (String number : numbers) {
            expectedList.add(userType.parseValue(number));
        }
        assertEquals(actualList.toString(), expectedList.toString());
    }
// Экстремальное значение находится в середине набора
    @Test
    public void testSortExtremumInCenter() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("1", "2", "8", "999", "3", "9", "6"));
        for (String number : numbers) {
            actualList.add(userType.parseValue(number));
        }

        actualList.sort();

        Collections.sort(numbers);
        for (String number : numbers) {
            expectedList.add(userType.parseValue(number));
        }
        assertEquals(actualList.toString(), expectedList.toString());
    }

    // Экстремальное значение находится в начале набора
    @Test
    public void testSortExtremeInBeginning() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("9999", "2", "8", "5", "3", "9", "6"));
        for (String number : numbers) {
            actualList.add(userType.parseValue(number));
        }

        actualList.sort();

        Collections.sort(numbers);
        for (String number : numbers) {
            expectedList.add(userType.parseValue(number));
        }
        assertEquals(actualList.toString(), expectedList.toString());
    }

    // Экстремальное значение находится в конце набора
    @Test
    public void testSortExtremeInEnding() {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("7", "2", "8", "5", "3", "9", "9999"));
        for (String number : numbers) {
            actualList.add(userType.parseValue(number));
        }

        actualList.sort();

        Collections.sort(numbers);
        for (String number : numbers) {
            expectedList.add(userType.parseValue(number));
        }
        assertEquals(actualList.toString(), expectedList.toString());
    }

    @Test
    public void testSortPerformance() {
        for (int i = 1; i < 4000; i *= 2) {
            int n = i * 1000;
            System.out.print(n + "\t");
            CycleList cycleList = new CycleList(userType.getTypeComparator());
            for (int j = 0; j < n; j++) cycleList.add(userType.create());

            long start = System.nanoTime();

            try {
                cycleList.sort();
            } catch (StackOverflowError ignored) {
                System.err.println("Stack error");
                return;
            }
            long end = System.nanoTime();
            System.out.println((end - start) * 1.0 / 1_000_000);
        }
    }
}
