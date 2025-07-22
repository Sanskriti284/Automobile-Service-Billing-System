import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Service {
    protected String name;
    protected double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void display();
}

class OilChange extends Service {
    public OilChange(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.println("Oil Change Service: " + name + " - Rs." + price);
    }
}

class TireRotation extends Service {
    public TireRotation(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.println("Tire Rotation Service: " + name + " - Rs." + price);
    }
}

class CarWash extends Service {
    public CarWash(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.println("Car Wash Service: " + name + " - Rs." + price);
    }
}

class VehicleServiceOrder {
    private List<Service> services;
    private String ownerName;
    private String vehiclePlate;

    public VehicleServiceOrder(String ownerName, String vehiclePlate) {
        this.ownerName = ownerName;
        this.vehiclePlate = vehiclePlate;
        services = new ArrayList<>();
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void display() {
        System.out.println("Service Order for " + ownerName + "'s Vehicle (Plate: " + vehiclePlate + "):");
        for (Service service : services) {
            service.display();
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Service service : services) {
            totalPrice += service.price;
        }
        return totalPrice;
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) { 
            writer.write("Owner Name: " + ownerName + "\n");
            writer.write("Vehicle Plate: " + vehiclePlate + "\n");
            writer.write("Services:\n");
            for (Service service : services) {
                writer.write(service.name + " - Rs." + service.price + "\n");
            }
            writer.write("Total Price: Rs." + getTotalPrice() + "\n\n"); 
            System.out.println("Service order details appended to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save service order details to file: " + e.getMessage());
        }
    }
}

public class VehicleServicingBillingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Service> services = new ArrayList<>();
        services.add(new OilChange("Regular Oil Change", 500));
        services.add(new OilChange("Synthetic Oil Change", 800));
        services.add(new TireRotation("Standard Tire Rotation", 1100));
        services.add(new TireRotation("Balanced Tire Rotation", 900));
        services.add(new CarWash("Exterior Wash", 550));
        services.add(new CarWash("Interior and Exterior Wash", 1200));

        System.out.print("Enter owner's name: ");
        String ownerName = scanner.nextLine();
        System.out.print("Enter vehicle plate: ");
        String vehiclePlate = scanner.nextLine();

        VehicleServiceOrder serviceOrder = new VehicleServiceOrder(ownerName, vehiclePlate);

        while (true) {
            System.out.println("Choose a service by entering its corresponding number:");
            for (int i = 0; i < services.size(); i++) {
                System.out.println((i + 1) + ". " + services.get(i).name + " - Rs." + services.get(i).price);
            }
            System.out.println("Enter '0' to finish selection.");
            System.out.print("Enter the index of the service: ");
            int serviceChoice = scanner.nextInt();
            scanner.nextLine();
            if (serviceChoice == 0) {
                break;
            }
            if (serviceChoice >= 1 && serviceChoice <= services.size()) {
                serviceOrder.addService(services.get(serviceChoice - 1));
            } else {
                System.out.println("Invalid service choice.");
            }
        }

        scanner.close();

        serviceOrder.display();

        System.out.println("Total Price: Rs." + serviceOrder.getTotalPrice());

        serviceOrder.saveToFile("service_bill.txt");
    }
}

