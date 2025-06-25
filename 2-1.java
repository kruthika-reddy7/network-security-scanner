class Vehicle {
    void displayType() {
        System.out.println("This is a Vehicle");
    }
}

class Car extends Vehicle {
    void displayBrand() {
        System.out.println("Brand: Toyota");
    }
}

class SportsCar extends Car {
    void displayModel() {
        System.out.println("Model: Supra MK4");
    }
}

public class MultilevelInheritance {
    public static void main(String[] args) {
        SportsCar sc = new SportsCar();
        sc.displayType();
        sc.displayBrand();
        sc.displayModel();
    }
}
