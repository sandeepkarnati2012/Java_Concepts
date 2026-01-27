import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Pet implements Serializable{
    private int petId;
    private String name;
    private String species;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate date;
    private List<Appointment> appointments=new java.util.ArrayList<>();

    public Pet(int petId,String name, String species, int age, String ownerName, String contactInfo){
        this.petId=petId;
        this.species=species;
        this.name=name;
        this.age=age;
        this.ownerName=ownerName;
        this.contactInfo=contactInfo;
        this.date=LocalDate.now();
        this.appointments=new ArrayList<>();

    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNo() {
        return contactInfo;
    }

    public void setContactNo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        
    }

}