import com.sun.source.doctree.SeeTree;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    public static void main(final String[] args) {
        List<Person> people = List.of(
                new Person(1, "Lucas", 13),
                new Person(2, "Carlos", 19),
                new Person(3, "Vega", 25),
                new Person(4, "Loto", 42),
                new Person(0, "Moto", 13),
                new Person(6, "Huan", 25)
        );
        /*
            1-е задание: С помощью методов filter, map, reduce составить Map<Integer, List<Person>>
                        Person-ов, сгруппированных по возрасту, у которых ненулевой id
         */
//        {
//            Map<Integer, List<Person>> result = new HashMap<>();
//
//            people.stream()
//                    .filter(p -> p.getId() != 0)
//                    .forEach(p -> {
//                        result.putIfAbsent(p.getAge(), new ArrayList<>());
//                        result.get(p.getAge()).add(p);
//                    });
//            System.out.println(result);
//        }

        /*
            2-е задание: Те же классы, но нужно сформировать Map<Integer, Integer>
                         со статистикой по каждому возрасту (количество Person-ов с данным возрастом).
         */

//        {
//            Map<Integer, Integer> result1 = new HashMap<>();
//
//            people.stream()
//                    .filter(p -> p.getId() != 0)
//                    .forEach(p ->
//                            result1.compute(p.getAge(), (key, val) -> (val != null) ? val + 1 : 1)
//                    );
//            System.out.println(result1);
//        }

    /*
            3-е задание: Сделать это .parallel-стримом и thread-safe
     */
//        {
//            Map<Integer, Integer> result1 = new ConcurrentHashMap<>();
//            people.stream()
//                    .parallel()
//                    .filter(p -> p.getId() != 0)
//                    .forEach(p -> {
//                        if (result1.containsKey(p.getAge())) {
//                            AtomicInteger val = new AtomicInteger(result1.get(p.getAge()));
//                            result1.put(p.getAge(), val.incrementAndGet());
//                        } else {
//                            result1.put(p.getAge(), 1);
//                        }
//                    });
//        }
        /*
            4-е задание: Сделать задания 1 и 2 с помощью collect (+groupingBy)
        */
//        {
//            Map<Integer, List<Person>> result = people.stream()
//                    .filter(p -> p.getId() != 0)
//                    .collect(Collectors.groupingBy(Person::getAge));
//            System.out.println(result);
//
//            Map<Integer, Long> result1 = people.stream()
//                    .filter(p -> p.getId() != 0)
//                    .collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));
//            System.out.println(result1);
//        }

        List<PersonAdvanced> peopleAdvanced = List.of(
                new PersonAdvanced(1, "Lucas", 13, List.of(
                        new Order(111, "BOOK", 1, 500),
                        new Order(112, "FILE", 2, 30)
                )),
                new PersonAdvanced(2, "Carlos", 19, List.of(
                        new Order(113, "BOOK", 2, 1200)
                )),
                new PersonAdvanced(3, "Vega", 25, List.of(
                        new Order(114, "FILE", 1, 15)
                )),
                new PersonAdvanced(4, "Loto", 42, List.of(
                        new Order(115, "PEN", 10, 200),
                        new Order(116, "RULER", 1, 100)
                )),
                new PersonAdvanced(0, "Moto", 13, List.of(
                        new Order(117, "PEN", 3, 60),
                        new Order(118, "FILE", 2, 30)
                )),
                new PersonAdvanced(6, "Huan", 25, List.of(
                        new Order(119, "BOOK", 1, 500),
                        new Order(120, "FILE", 2, 30),
                        new Order(121, "RULER", 2, 150)
                ))
        );
        /*
            5-е задание: Посчитать суммарную цену всех заказов у List<Person>
        */
        {
            Integer sum = peopleAdvanced.stream()
                    .flatMap(person -> person.getOrders().stream())
                    .mapToInt(Order::getTotalPrice)
                    .sum();
            System.out.println(sum);
        }
        /*
            6-е задание: Посчитать сумму всех проданных товаров по артикулу (sku) - Map<String, Integer>
        */
        {
            Map<String, Integer> result = peopleAdvanced.stream()
                    .flatMap(person -> person.getOrders().stream())
                    .collect(Collectors.groupingBy(Order::getSku, Collectors.summingInt(Order::getTotalPrice)));
            System.out.println(result);
        }
        /*
            7-е задание: *По артикулу (sku) собрать средний возраст покупателей,
                          которые его покупали - Map<String, Double>
        */
        {
            
        }
    }
}
