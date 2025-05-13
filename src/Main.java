import java.util.List;

public class Main {
    public static void main(String[] args) {
        Organization org = new Organization();

        // Додаємо працівників
        org.add(new Employee("Іван", 5000, IT.BACKEND_DEV));
        org.add(new Employee("Оксана", 7000, IT.FRONTEND_DEV));
        org.add(new Employee("Андрій", 6000, IT.FULLSTACK_DEV));
        org.add(new Employee("Марія", 8000, IT.HQ));
        org.add(new Employee("Сергій", 3000, IT.BACKEND_DEV));

        // Вивід всіх у порядку зростання зарплатні
        System.out.println("🔷 Працівники за зростанням зарплатні:");
        for (Employee e : org.inOrder()) {
            printEmployee(e);
        }

        // Пошук працівника
        Employee searchEmp = new Employee("Марія", 8000, IT.HQ);
        System.out.println("\n Чи існує Марія? " + org.contains(searchEmp));

        // Працівники з зарплатнею понад 5000
        System.out.println("\n Працівники з зарплатнею > 5000:");
        List<Employee> richEmployees = org.getAllWithSalaryMoreThan(5000);
        for (Employee e : richEmployees) {
            printEmployee(e);
        }

        // Видалення працівника
        System.out.println("\n🗑 Видаляємо працівника Андрій (6000):");
        org.delete(new Employee("Андрій", 6000, IT.FULLSTACK_DEV));

        // Знову вивід дерева
        System.out.println("\n Стан дерева після видалення:");
        for (Employee e : org.inOrder()) {
            printEmployee(e);
        }

        // Вивід перших трьох рівнів (BFS)
        System.out.println("\n Перші 3 рівні дерева:");
        for (Employee e : org.getFirstThreeLevels()) {
            printEmployee(e);
        }

        // Знаходження sibling
        Employee target = new Employee("Оксана", 7000, IT.FRONTEND_DEV);
        Employee sibling = org.getSibling(target);
        System.out.println("\n Сіблінг для Оксани:");
        if (sibling != null) printEmployee(sibling);
        else System.out.println("Сіблінга не знайдено.");
    }

    public static void printEmployee(Employee e) {
        System.out.printf(" %s |  %d |  %s\n", e.name, e.salary, e.pos);
    }
}