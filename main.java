package projects;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}


class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}


class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        car.rent();
        rentals.add(new Rental(car, customer, days));
    }

    public void returnCar(Car car) {
        car.returnCar();
        rentals.removeIf(rental -> rental.getCar() == car);
    }

 
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Available Cars");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {
                case 1 -> rentCarMenu(scanner);
                case 2 -> returnCarMenu(scanner);
                case 3 -> viewAvailableCars();
                case 4 -> {
                    System.out.println("Thank you for using the Car Rental System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }

    
    private void rentCarMenu(Scanner scanner) {
        System.out.println("\n== Rent a Car ==\n");
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        viewAvailableCars();

        System.out.print("\nEnter the car ID you want to rent: ");
        String carId = scanner.nextLine();

        System.out.print("Enter the number of days for rental: ");
        int rentalDays;
        try {
            rentalDays = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of days. Please try again.");
            return;
        }

        Car selectedCar = cars.stream()
                .filter(car -> car.getCarId().equals(carId) && car.isAvailable())
                .findFirst()
                .orElse(null);

        if (selectedCar != null) {
            double totalPrice = selectedCar.calculatePrice(rentalDays);
            System.out.printf("\nTotal Price: $%.2f%n", totalPrice);
            System.out.print("Confirm rental (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);
                rentCar(selectedCar, newCustomer, rentalDays);
                System.out.println("\nCar rented successfully.");
            } else {
                System.out.println("\nRental canceled.");
            }
        } else {
            System.out.println("\nInvalid car selection or car not available.");
        }
    }

   
    private void returnCarMenu(Scanner scanner) {
        System.out.println("\n== Return a Car ==\n");
        System.out.print("Enter the car ID you want to return: ");
        String carId = scanner.nextLine();

        Car carToReturn = cars.stream()
                .filter(car -> car.getCarId().equals(carId) && !car.isAvailable())
                .findFirst()
                .orElse(null);

        if (carToReturn != null) {
            returnCar(carToReturn);
            System.out.println("\nCar returned successfully.");
        } else {
            System.out.println("\nInvalid car ID or car is not rented.");
        }
    }

  
    private void viewAvailableCars() {
        System.out.println("\n== Available Cars ==\n");
        cars.stream()
                .filter(Car::isAvailable)
                .forEach(car -> System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel()));
    }
}


public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Car("C001", "Toyota", "Camry", 160.0));
        rentalSystem.addCar(new Car("C002", "Honda", "Accord", 170.0));
        rentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 150.0));
        rentalSystem.addCar(new Car("C004", "Ford", "Fiesta", 1000.0));
        rentalSystem.addCar(new Car("C005", "Hyundai", "Elantra", 150.0));
        rentalSystem.addCar(new Car("C006", "Mercedes", "C-Class", 200.0));
        rentalSystem.addCar(new Car("C007", "BMW", "5 Series", 220.0));
        rentalSystem.addCar(new Car("C008", "Jeep", "Wrangler", 120.0));
        rentalSystem.addCar(new Car("C009", "Toyota", "RAV4", 110.0));
        rentalSystem.addCar(new Car("C010", "Tesla", "Model 3", 150.0));
        rentalSystem.addCar(new Car("C011", "Nissan", "Leaf", 90.0));
        rentalSystem.addCar(new Car("C012", "Porsche", "911", 300.0));
        rentalSystem.addCar(new Car("C013", "Chevrolet", "Corvette", 280.0));
         rentalSystem.addCar(new Car("C014", "Ford", "F-150", 130.0));
        rentalSystem.addCar(new Car("C015", "Chevrolet", "Silverado", 140.0));
   
          rentalSystem.menu();
    }
}
