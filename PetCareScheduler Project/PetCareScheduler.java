import java.util.*;

import javax.sound.sampled.SourceDataLine;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PetCareScheduler{

    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer, Pet> pets = new HashMap<>();
    public static void main(String[] args){
        
        boolean running = true;
        while (running) {
            System.out.println("1.Register pets");
            System.out.println("2.Schedule Appointments");
            System.out.println("3.Save Pet Data To File");
            System.out.println("4.Display Appointments");
            System.out.println("5.Generate Reports");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    savePetDataToFile();
                    break;
                case "4":
                    displayAppointments();
                    break;
                case "5":
                    generateReports();
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }

    }
    private static void registerPet(){
        System.out.println("Enter Pet Id:");
        int petId=Integer.parseInt(scanner.nextLine().trim());
        if(pets.containsKey(petId)){
            System.out.println("Error: Pet already exists.");
            return;
        }
        System.out.println("Enter Pet Name: ");
        String name=scanner.nextLine().trim();

        System.out.println("Enter Pet species: ");
        String species=scanner.nextLine().trim();

        System.out.println("Enter Pet Age: ");
        int age=Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Enter Pet Owner Name: ");
        String ownerName=scanner.nextLine().trim();

        System.out.println("Enter Pet Contact Info: ");
        String contactInfo=scanner.nextLine().trim();

        System.out.println("Enter Registration Date: ");
        DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim(), formatter);

        Pet pet=new Pet(petId, name, species, age, ownerName, contactInfo);

        pets.put(petId,pet);
        System.out.println("Pet registered successfully on " + pet.getDate());
    }

    private static void scheduleAppointment(){
        System.out.println("Enter Pet Id: ");
        int petId=Integer.parseInt(scanner.nextLine().trim());
        Pet pet=pets.get(petId);
        if(pet==null){
            System.out.println("Error: Pet Id Not Found");
            return;
        }
        System.out.println("Enter Appointment Type: CHECKUP, VACCINATION, GROOMING, SURGERY");
        String appointmentType=scanner.nextLine().trim();
        AppointmentType type = null;

        while (true) {
            try {
                type = AppointmentType.valueOf(appointmentType.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid appointment type. Try again:");
                appointmentType = scanner.nextLine().trim();
            }
        }

        System.out.println("Enter Date and Time: ");
        LocalDateTime dateAndTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        while (true) {
            try {
                System.out.print("Enter Date and Time (MM/dd/yyyy HH:mm:ss): ");
                dateAndTime = LocalDateTime.parse(scanner.nextLine().trim(), formatter);

                if (dateAndTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Appointment must be in the future. Try again.");
                    continue;
                }

                break; // valid date-time
            } catch (Exception e) {
                System.out.println("Invalid format. Please enter in MM/dd/yyyy HH:mm:ss");
            }
}

        System.out.println("Enter Notes/instructions: ");
        String notes=scanner.nextLine().trim();

        Appointment appointment= new Appointment(type, dateAndTime, notes);
        pet.addAppointment(appointment);
        System.out.println("Appointment Scheduled on "+ dateAndTime +" for pet with pet id "+ pet.getPetId());
    }

    private static void savePetDataToFile(){
        try {
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("petData.ser"));
            out.writeObject(pets);
        } catch (Exception e) {
            System.out.println("Error Saving Data: "+ e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private static void loadDataFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("petData.ser"))) {
            pets = (Map<Integer, Pet>) in.readObject();
            System.out.println("All pet data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void displayAppointments(){
        System.out.println("1.Dispaly all the pets"+
                            "\n2.Display all appointments for a specific pet"+
                            "\n3.Display upcoming appointments for all pets"+
                            "\n4.Display past appointment history of each pet");
        
        String choice=scanner.nextLine().trim();

        switch(choice){
            case "1":dipslayAllPets(); break;
            case "2":displayAppointmentsForPet(); break;
            case "3":displayUpcomingAppointments(); break;
            case "4":displayPastAppointments(); break;
            default: System.out.println("Invalid choice");
            
        }
    }
    private static void dipslayAllPets(){
        if(pets.isEmpty()){
            System.out.println("No pets registered");
        }

        System.out.println("Registered Pets");
        for(Pet pet:pets.values()){
            System.out.println(
            "Pet ID: " + pet.getPetId() +
            ", Name: " + pet.getName() +
            ", Species: " + pet.getSpecies() +
            ", Age: " + pet.getAge() +
            ", Owner: " + pet.getOwnerName()
            );
        }
    }
    private static void displayAppointmentsForPet(){
        System.out.println("Enter Pet Id");
        int petId=Integer.parseInt(scanner.nextLine().trim());
        Pet pet=pets.get(petId);
        if(pet==null){
            System.out.println("Pet not found");
            return;
        }
        System.out.println("\nAppointments for " + pet.getName() + ":");
        if (pet.getAppointments().isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment appt : pet.getAppointments()) {
            System.out.println(appt);
            System.out.println("--------------------");
        }

    }
    private static void displayUpcomingAppointments(){
        LocalDateTime now=LocalDateTime.now();
        boolean found=false;
        System.out.println("All Upcoming Appointments");
        for(Pet pet:pets.values()){
            for(Appointment appointment:pet.getAppointments()){
                if(appointment.getDateAndTime().isAfter(now)){
                    System.out.println("Pet name"+ pet.getName()+
                                        "Pet id" + pet.getPetId());
                }
                System.out.println(appointment);
                found=true;

            }
        }
        if(!found){
            System.out.println("No upcoming appointmentsm");        }

    }

    private static void displayPastAppointments(){
        LocalDateTime now=LocalDateTime.now();
        boolean found=false;
        System.out.println("All Past Appointments");
        for(Pet pet:pets.values()){
            for(Appointment appointment:pet.getAppointments()){
                if(appointment.getDateAndTime().isBefore(now)){
                    System.out.println("Pet name"+ pet.getName()+
                                        "Pet id" + pet.getPetId());
                }
                //System.out.println(appointment);
                found=true;

            }
        }
        if(!found){
            System.out.println("No past appointmentsm");
        }

    }

    private static void generateReports(){
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime nextWeek=now.plusDays(7);
        LocalDateTime sixMonthsAgo=now.minusMonths(6);

        System.out.println("Pet with appointmenets in the next week");
        boolean upcomingFound=false;
        for(Pet pet:pets.values()){
            for(Appointment appoint: pet.getAppointments()){
                LocalDateTime appointTime= appoint.getDateAndTime();

                if(appointTime.isAfter(now) && appointTime.isBefore(nextWeek)){
                    System.out.println("Pet "+ pet.getName()+ pet.getPetId() + "has appointment in the next week");
                    
                }
                System.out.println(appoint);
                upcomingFound=true;
            }
        }
        if (!upcomingFound) {
            System.out.println("No upcoming appointments in the next week.");
        }


        System.out.println("\nPets Overdue for a Vet Visit (No visit in last 6 months):");
        boolean overdueFound = false;

        for (Pet pet : pets.values()) {
            LocalDateTime lastVisit = null;

            for (Appointment appt : pet.getAppointments()) {
                LocalDateTime apptTime = appt.getDateAndTime();
                if (lastVisit == null || apptTime.isAfter(lastVisit)) {
                    lastVisit = apptTime;
                }
            }

            if (lastVisit == null || lastVisit.isBefore(sixMonthsAgo)) {
                System.out.println(
                    "Pet: " + pet.getName() +
                    " (ID: " + pet.getPetId() + ")" +
                    (lastVisit == null
                        ? " | No visits recorded"
                        : " | Last visit: " + lastVisit)
                );
                overdueFound = true;
            }
        }

        if (!overdueFound) {
            System.out.println("No pets are overdue for a vet visit.");
        }

        
    }
    
}