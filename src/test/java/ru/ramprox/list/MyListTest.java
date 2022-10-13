package ru.ramprox.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit тесты MyList")
public class MyListTest {

    @DisplayName("Тест конструкторов списка")
    @Test
    public void createTest() {
        assertDoesNotThrow(() -> new MyList<Integer>());
        assertDoesNotThrow(() -> new MyList<Integer>(10));
        assertDoesNotThrow(() -> new MyList<Integer>(10, 20));
        assertDoesNotThrow(() -> new MyList<Integer>(10, 10));
        assertThrows(IllegalArgumentException.class, () -> new MyList<Integer>(-1, 20));
        assertThrows(IllegalArgumentException.class, () -> new MyList<Integer>(10, -1));
        assertThrows(IllegalArgumentException.class, () -> new MyList<Integer>(10, 5));
    }

    @DisplayName("Тест пустого списка")
    @Test
    public void emptyListTest() {
        List<Integer> list = new MyList<>();
        assertThat(list.isEmpty()).isEqualTo(true);
        assertThat(list.size()).isEqualTo(0);
        assertThat(list.toString()).isEqualTo("[]");
    }

    @DisplayName("Тест метода size")
    @Test
    public void sizeTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2));

        assertThat(list.size()).isEqualTo(2);

        list.remove(Integer.valueOf(1));
        assertThat(list.size()).isEqualTo(1);

        list.remove(Integer.valueOf(2));
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("Проверка метода isEmpty")
    @Test
    public void isEmptyTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2));

        assertThat(list.isEmpty()).isEqualTo(false);

        list.remove(Integer.valueOf(2));
        assertThat(list.isEmpty()).isEqualTo(false);

        list.remove(Integer.valueOf(1));
        assertThat(list.isEmpty()).isEqualTo(true);
    }

    @DisplayName("Тест метода contains")
    @Test
    public void containsTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2));

        assertThat(list.contains(1)).isEqualTo(true);
        assertThat(list.contains(2)).isEqualTo(true);

        list.remove(Integer.valueOf(2));
        assertThat(list.contains(2)).isEqualTo(false);
        assertThat(list.contains(1)).isEqualTo(true);

        list.add(null);
        assertThat(list.contains(null)).isEqualTo(true);
    }

    @DisplayName("Тест метода toArray()")
    @Test
    public void toArrayTest() {
        Integer[] expectedItems = { 1, 2, 3 };
        MyList<Integer> list = new MyList<>();
        list.addAll(List.of(expectedItems));

        Object[] actualItems = list.toArray();

        assertThat(actualItems.length).isEqualTo(expectedItems.length);
        assertThat(actualItems).containsExactly(expectedItems);
    }

    @DisplayName("Тест метода toArray(T1[] a)")
    @Test
    public void toArrayWithCastTest() {
        Integer[] expectedItems = { 1, 2, 3 };
        List<Number> list = new MyList<>();
        list.addAll(List.of(expectedItems));

        Integer[] array = new Integer[0];
        Integer[] actualItems = list.toArray(array);

        assertThat(actualItems != array).isEqualTo(true);
        assertThat(actualItems.length).isEqualTo(expectedItems.length);
        assertThat(actualItems).containsExactly(expectedItems);

        array = new Integer[10];
        for(int i = expectedItems.length; i < array.length; i++) {
            array[i] = i;
        }
        actualItems = list.toArray(array);

        assertThat(actualItems == array).isEqualTo(true);
        for(int i = 0; i < array.length; i++) {
            if(i < expectedItems.length) {
                assertThat(actualItems[i]).isEqualTo(expectedItems[i]);
            } else {
                assertThat(actualItems[i]).isNull();
            }
        }

        assertThrows(ArrayStoreException.class, () -> list.toArray(new Double[0]));
        assertThrows(ArrayStoreException.class, () -> list.toArray(new Double[10]));
        assertDoesNotThrow(() -> list.toArray(new Object[0]));
        assertDoesNotThrow(() -> list.toArray(new Object[10]));
        assertThrows(NullPointerException.class, () -> list.toArray((Object[]) null));
    }

    @DisplayName("Тест метода add()")
    @Test
    public void addTest() {
        List<Integer> list = new MyList<>(0, 5);
        assertThat(list.add(1)).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1]");

        assertThat(list.add(2)).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 2]");

        assertThat(list.add(3)).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 2, 3]");

        assertThat(list.add(4)).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 2, 3, 4]");

        assertThat(list.add(5)).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 2, 3, 4, 5]");

        assertThrows(IllegalStateException.class, () -> list.add(6));
    }

    @DisplayName("Тест добавления по индексу одного элемента")
    @Test
    public void addByIndexTest() {
        List<Integer> list = new MyList<>(3, 5);
        list.add(0, 1);
        assertThat(list.toString()).isEqualTo("[1]");

        list.add(0, 2);
        assertThat(list.toString()).isEqualTo("[2, 1]");

        list.add(1, 3);
        assertThat(list.toString()).isEqualTo("[2, 3, 1]");

        list.add(3, 4);
        assertThat(list.toString()).isEqualTo("[2, 3, 1, 4]");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, 1));
    }

    @DisplayName("Проверка метода addAll()")
    @Test
    public void addAllTest() {
        List<Integer> list = new MyList<>();

        boolean added = list.addAll(List.of(1, 2, 3));

        assertThat(added).isEqualTo(true);
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.toString()).isEqualTo("[1, 2, 3]");

        added = list.addAll(List.of(4, 5, 6));

        assertThat(added).isEqualTo(true);
        assertThat(list.size()).isEqualTo(6);
        assertThat(list.toString()).isEqualTo("[1, 2, 3, 4, 5, 6]");

        assertThrows(NullPointerException.class, () -> list.addAll(null));
    }

    @DisplayName("Проверка метода addAll(index, Collection<? extends T>)")
    @Test
    public void addAllByIndexTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2));

        assertThat(list.addAll(0, List.of(3, 4, 5))).isEqualTo(true);
        assertThat(list.size()).isEqualTo(5);
        assertThat(list.toString()).isEqualTo("[3, 4, 5, 1, 2]");

        assertThat(list.addAll(2, List.of(6, 7))).isEqualTo(true);
        assertThat(list.size()).isEqualTo(7);
        assertThat(list.toString()).isEqualTo("[3, 4, 6, 7, 5, 1, 2]");

        assertThat(list.addAll(7, List.of(8, 9))).isEqualTo(true);
        assertThat(list.size()).isEqualTo(9);
        assertThat(list.toString()).isEqualTo("[3, 4, 6, 7, 5, 1, 2, 8, 9]");

        assertThrows(NullPointerException.class, () -> list.addAll(null));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(-1, List.of(1, 2)));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(10, List.of(1, 2)));
    }

    @DisplayName("Тест метода remove()")
    @Test
    public void removeTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5, 4));

        assertThat(list.remove(Integer.valueOf(2))).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 3, 4, 5, 4]");

        assertThat(list.remove(Integer.valueOf(1))).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[3, 4, 5, 4]");

        assertThat(list.remove(Integer.valueOf(4))).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[3, 5, 4]");
    }

    @DisplayName("Тест метода удаления по индексу")
    @Test
    public void removeByIndexTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5));

        Integer removed = list.remove(0);
        assertThat(removed).isEqualTo(1);
        assertThat(list.toString()).isEqualTo("[2, 3, 4, 5]");

        removed = list.remove(1);
        assertThat(removed).isEqualTo(3);
        assertThat(list.toString()).isEqualTo("[2, 4, 5]");

        removed = list.remove(2);
        assertThat(removed).isEqualTo(5);
        assertThat(list.toString()).isEqualTo("[2, 4]");

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
    }

    @DisplayName("Проверка метода removeAll(Collection<?> col)")
    @Test
    public void removeAllTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5, 3, 4, 3, 4));

        assertThat(list.removeAll(List.of(1, 3, 4))).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[2, 5]");

        assertThrows(NullPointerException.class, () -> list.removeAll(null));
    }

    @DisplayName("Тест метода containsAll()")
    @Test
    public void containsAllTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3));

        assertThat(list.containsAll(List.of(3, 1, 2, 1))).isEqualTo(true);
        assertThat(list.containsAll(List.of(3, 2, 4))).isEqualTo(false);

        assertThrows(NullPointerException.class, () -> list.containsAll(null));
    }

    @DisplayName("Проверка метода retainAll(Collection<?> c)")
    @Test
    public void retainAllTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5, 3, 4, 3, 4));

        assertThat(list.retainAll(List.of(1, 2))).isEqualTo(true);
        assertThat(list.toString()).isEqualTo("[1, 2]");

        list.addAll(List.of(4, 5, 6));
        assertThat(list.toString()).isEqualTo("[1, 2, 4, 5, 6]");

        assertThat(list.retainAll(List.of(1, 2, 4, 5, 6))).isEqualTo(false);

        assertThrows(NullPointerException.class, () -> list.retainAll(null));
    }

    @DisplayName("Тест метода clear")
    @Test
    public void clearTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4));

        list.clear();

        assertThat(list.toString()).isEqualTo("[]");
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("Извлечение элемента по индексу")
    @Test
    public void getByIndexTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4));

        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(2)).isEqualTo(3);
        assertThat(list.get(3)).isEqualTo(4);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
    }

    @DisplayName("Изменение элемента по индексу")
    @Test
    public void setByIndexTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4));

        Integer changedItem = list.set(0, 5);
        assertThat(changedItem).isEqualTo(1);
        assertThat(list.toString()).isEqualTo("[5, 2, 3, 4]");

        changedItem = list.set(2, 6);
        assertThat(changedItem).isEqualTo(3);
        assertThat(list.toString()).isEqualTo("[5, 2, 6, 4]");

        changedItem = list.set(3, 7);
        assertThat(changedItem).isEqualTo(4);
        assertThat(list.toString()).isEqualTo("[5, 2, 6, 7]");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(4, 10));
    }

    @DisplayName("Поиск первого индекса элемента")
    @Test
    public void indexOfTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 3, 5));

        assertThat(list.indexOf(1)).isEqualTo(0);
        assertThat(list.indexOf(3)).isEqualTo(2);
        assertThat(list.indexOf(5)).isEqualTo(5);
        assertThat(list.indexOf(10)).isEqualTo(-1);
    }

    @DisplayName("Поиск последнего индекса элемента")
    @Test
    public void lastIndexOfTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 3, 5));

        assertThat(list.lastIndexOf(1)).isEqualTo(0);
        assertThat(list.lastIndexOf(3)).isEqualTo(4);
        assertThat(list.indexOf(5)).isEqualTo(5);
        assertThat(list.indexOf(10)).isEqualTo(-1);
    }

    @DisplayName("Перебор элементов итератором")
    @Test
    public void iteratorTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5));

        int count = 0;
        for(Integer actualItem : list) {
            assertThat(actualItem).isEqualTo(list.get(count++));
        }
        assertThat(count).isEqualTo(list.size());
    }

    @DisplayName("Перебор элементов listIterator'ом по умолчанию")
    @Test
    public void listIteratorTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5));

        int count = 0;
        ListIterator<Integer> listIterator = list.listIterator();
        while(listIterator.hasNext()) {
            assertThat(listIterator.nextIndex()).isEqualTo(count);
            assertThat(listIterator.previousIndex()).isEqualTo(count - 1);
            Integer actualItem = listIterator.next();
            assertThat(actualItem).isEqualTo(list.get(count++));
        }
        assertThat(count).isEqualTo(list.size());

        assertThrows(NoSuchElementException.class, listIterator::next);

        while(listIterator.hasPrevious()) {
            assertThat(listIterator.nextIndex()).isEqualTo(count);
            assertThat(listIterator.previousIndex()).isEqualTo(count - 1);
            Integer actualItem = listIterator.previous();
            assertThat(actualItem).isEqualTo(list.get(--count));
        }
        assertThat(count).isEqualTo(0);
        assertThrows(NoSuchElementException.class, listIterator::previous);
    }

    @DisplayName("Модификация списка при помощи listIterator")
    @Test
    public void listIteratorModifyTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3));
        ListIterator<Integer> listIterator = list.listIterator();

        listIterator.add(0);
        assertThat(list.toString()).isEqualTo("[0, 1, 2, 3]");

        listIterator.next();
        listIterator.remove();
        assertThat(list.toString()).isEqualTo("[0, 2, 3]");

        listIterator.next();
        listIterator.remove();
        assertThat(list.toString()).isEqualTo("[0, 3]");

        listIterator.add(0);
        assertThat(list.toString()).isEqualTo("[0, 0, 3]");
        assertThrows(IllegalStateException.class, listIterator::remove);

        listIterator.next();
        listIterator.remove();
        assertThat(list.toString()).isEqualTo("[0, 0]");
        assertThrows(IllegalStateException.class, listIterator::remove);

        listIterator.previous();
        listIterator.set(1);
        assertThat(list.toString()).isEqualTo("[0, 1]");
        listIterator.set(2);
        assertThat(list.toString()).isEqualTo("[0, 2]");
    }

    @DisplayName("Перебор элементов listIterator'ом с начальным индексом")
    @Test
    public void listIteratorWithStartIndexTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5));

        int count = 2;
        ListIterator<Integer> listIterator = list.listIterator(count);
        while(listIterator.hasNext()) {
            assertThat(listIterator.nextIndex()).isEqualTo(count);
            assertThat(listIterator.previousIndex()).isEqualTo(count - 1);
            Integer actualItem = listIterator.next();
            assertThat(actualItem).isEqualTo(list.get(count++));
        }
        assertThat(count).isEqualTo(list.size());

        assertThrows(NoSuchElementException.class, listIterator::next);

        count = 2;
        listIterator = list.listIterator(count);
        while(listIterator.hasPrevious()) {
            assertThat(listIterator.nextIndex()).isEqualTo(count);
            assertThat(listIterator.previousIndex()).isEqualTo(count - 1);
            Integer actualItem = listIterator.previous();
            assertThat(actualItem).isEqualTo(list.get(--count));
        }
        assertThat(count).isEqualTo(0);
        assertThrows(NoSuchElementException.class, listIterator::previous);
    }

    @DisplayName("Проверка метода subList()")
    @Test
    public void subListTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(1, 2, 3, 4, 5));

        List<Integer> actualSubList = list.subList(0, 5);
        assertThat(actualSubList.toString()).isEqualTo("[1, 2, 3, 4, 5]");

        actualSubList = list.subList(0, 3);
        assertThat(actualSubList.toString()).isEqualTo("[1, 2, 3]");

        actualSubList = list.subList(2, 5);
        assertThat(actualSubList.toString()).isEqualTo("[3, 4, 5]");

        actualSubList = list.subList(1, 4);
        assertThat(actualSubList.toString()).isEqualTo("[2, 3, 4]");

        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(1, 6));
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 6));
    }

    @DisplayName("Сортировка детерминированного списка")
    @Test
    public void determinateSortTest() {
        List<Integer> list = new MyList<>();
        list.addAll(List.of(4, 3, 6, 1, 7, 8, 5, 9, 2, 10));
        list.sort(Integer::compareTo);
        assertThat(list.toString()).isEqualTo("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");

        list.clear();

        list.addAll(List.of(4, 1, 4, 1, 4, 4, 1, 4, 1, 4));
        list.sort(Integer::compareTo);
        assertThat(list.toString()).isEqualTo("[1, 1, 1, 1, 4, 4, 4, 4, 4, 4]");

        list.clear();

        list.add(5);
        list.sort(Integer::compareTo);
        assertThat(list.toString()).isEqualTo("[5]");

        list.add(1);
        list.sort(Integer::compareTo);
        assertThat(list.toString()).isEqualTo("[1, 5]");

        list.add(3);
        list.sort(Integer::compareTo);
        assertThat(list.toString()).isEqualTo("[1, 3, 5]");

    }

    @DisplayName("Сортировка списка со случайными элементами")
    @Test
    public void randomListSortTest() {
        int n = 10_000_000;
        List<Integer> actualList = new MyList<>(n);
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            actualList.add(random.nextInt(n));
        }
        List<Integer> expectedList = new ArrayList<>(actualList);
        long start = System.currentTimeMillis();
        expectedList.sort(Integer::compareTo);
        long end = System.currentTimeMillis();
        System.out.printf("Время стандартной сортировки : %d%n", end - start);

        start = System.currentTimeMillis();
        actualList.sort(Integer::compareTo);
        end = System.currentTimeMillis();
        System.out.printf("Время моей сортировки : %d%n", end - start);

        assertEqualsLists(actualList, expectedList);
    }

    @DisplayName("Сортировка списка с элементами в порядке убывания")
    @Test
    public void sortWithDecreaseItemsTest() {
        int n = 10_000_000;
        List<Integer> actualList = new MyList<>(n);
        for(int i = 0; i < n; i++) {
            actualList.add(n - i);
        }

        long start = System.currentTimeMillis();
        actualList.sort(Integer::compareTo);
        long end = System.currentTimeMillis();
        System.out.printf("Время сортировки : %d%n", end - start);

        List<Integer> expectedList = new ArrayList<>(n);
        for(int i = 1; i <= n; i++) {
            expectedList.add(i);
        }

        assertEqualsLists(actualList, expectedList);
    }

    @DisplayName("Автор списка")
    @Test
    public void getAuthorTest() {
        AuthorHolder list = new MyList<>();
        assertThat(list.getAuthor()).isEqualTo("Курбаналиев Рамиль");
    }

    private void assertEqualsLists(List<Integer> actualList, List<Integer> expectedList) {
        assertThat(actualList.size()).isEqualTo(expectedList.size());

        Iterator<Integer> actualListIterator = actualList.iterator();
        Iterator<Integer> expectedListIterator = expectedList.iterator();
        while(actualListIterator.hasNext() && expectedListIterator.hasNext()) {
            Integer actualItem = actualListIterator.next();
            Integer expectedItem = expectedListIterator.next();
            assertThat(actualItem).isEqualTo(expectedItem);
        }
    }

}
