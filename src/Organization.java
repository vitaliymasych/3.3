import java.util.*;

public class Organization {
    private Employee root;

    // ----------- 3. Додати нового працівника ----------------
    public boolean add(Employee e) {
        if (e == null) return false;
        root = insert(root, e);
        return true;
    }

    // ----------- 4. Видалити працівника ---------------------
    public Employee delete(Employee e) {
        if (!contains(e)) return null;
        root = deleteNode(root, e.salary);
        return e;
    }

    // ----------- 2. Перевірити, чи існує працівник ----------
    public boolean contains(Employee e) {
        return contains(root, e);
    }

    // ----------- 1. Повернути працівників з зарплатою > N ---
    public List<Employee> getAllWithSalaryMoreThan(int n) {
        List<Employee> result = new ArrayList<>();
        collectEmployeesWithSalary(root, n, result);
        return result;
    }

    // ----------- 5. BFS: Перші 3 рівні дерева ----------------
    public List<Employee> getFirstThreeLevels() {
        List<Employee> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Employee> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;

        while (!queue.isEmpty() && level < 3) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Employee current = queue.poll();
                result.add(current);
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
            level++;
        }
        return result;
    }

    // ----------- 6. Повернути sibling працівника ------------
    public Employee getSibling(Employee target) {
        return getSibling(root, target);
    }

    // ----------- 7. Вивести всіх у порядку зростання зарплат --
    public List<Employee> inOrder() {
        List<Employee> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    // ----------- Приватні методи AVL -------------------------

    private Employee insert(Employee node, Employee e) {
        if (node == null) return e;

        if (e.salary < node.salary) {// рекурсивно топаю тут
            node.left = insert(node.left, e);
        } else if (e.salary > node.salary) {
            node.right = insert(node.right, e);
        } else {
            return node; // Не дозволяємо дублікати
        }

        updateHeight(node);
        return balance(node);
    }

    private Employee deleteNode(Employee node, int salary) {
        if (node == null) return null;

        if (salary < node.salary) {
            node.left = deleteNode(node.left, salary);
        } else if (salary > node.salary) {
            node.right = deleteNode(node.right, salary);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Employee successor = minValueNode(node.right);
                node.name = successor.name;
                node.salary = successor.salary;
                node.pos = successor.pos;
                node.right = deleteNode(node.right, successor.salary);
            }
        }

        if (node == null) return null;

        updateHeight(node);
        return balance(node);
    }

    private boolean contains(Employee node, Employee e) { // топаю перевіряти чи є тіпчик?
        if (node == null) return false;
        if (node.equals(e)) return true;
        if (e.salary < node.salary) return contains(node.left, e);
        return contains(node.right, e);
    }

    private void collectEmployeesWithSalary(Employee node, int salary, List<Employee> result) {//у яких зарплата більша за задану
        if (node == null) return;
        if (node.salary > salary) result.add(node);
        collectEmployeesWithSalary(node.left, salary, result);// рекурсивно далі
        collectEmployeesWithSalary(node.right, salary, result);
    }

    private void inOrderTraversal(Employee node, List<Employee> result) {
        if (node == null) return;
        inOrderTraversal(node.left, result);
        result.add(node);
        inOrderTraversal(node.right, result);
    }

    private Employee getSibling(Employee parent, Employee target) {
        if (parent == null) return null;

        if (parent.left != null && parent.left.equals(target)) return parent.right;
        if (parent.right != null && parent.right.equals(target)) return parent.left;

        Employee leftResult = getSibling(parent.left, target);
        if (leftResult != null) return leftResult;
        return getSibling(parent.right, target);
    }

    // ----------- AVL balance help -------------------------

    private int getHeight(Employee node) {
        return node == null ? 0 : node.height;//
    }

    private void updateHeight(Employee node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getBalance(Employee node) {
        return getHeight(node.left) - getHeight(node.right);
    }

    private Employee balance(Employee node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Employee rotateRight(Employee y) {
        Employee x = y.left;
        Employee T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Employee rotateLeft(Employee x) {
        Employee y = x.right;
        Employee T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Employee minValueNode(Employee node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
