public class Employee {
    public int salary;
    public String name;
    public IT pos;
    public int height;
    public Employee left, right;

    public Employee(String name, int salary, IT pos) {
        this.name = name;
        this.salary = salary;
        this.pos = pos;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee)) return false;
        Employee other = (Employee) obj; // obj до типу Employee
        return this.salary == other.salary && this.name.equals(other.name);
    }
}
