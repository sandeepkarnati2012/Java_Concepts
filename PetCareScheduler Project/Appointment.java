import java.io.Serializable;
import java.time.LocalDateTime;


enum AppointmentType {
    CHECKUP,
    VACCINATION,
    GROOMING,
    SURGERY
}

public class Appointment implements Serializable{
    private AppointmentType appointmentType;
    private LocalDateTime dateAndTime;
    private String notes;

    // Constructor
    public Appointment(AppointmentType appointmentType, LocalDateTime dateAndTime, String notes) {
        this.appointmentType = appointmentType;
        this.dateAndTime = dateAndTime;
        this.notes = notes;
    }

    // Getter & Setter
    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        if (dateAndTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment must be in the future.");
        }
        this.dateAndTime = dateAndTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment Type: " + appointmentType +
               "\nDate and Time: " + dateAndTime +
               "\nNotes: " + notes;
    }
}
