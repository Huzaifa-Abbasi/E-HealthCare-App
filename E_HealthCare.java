import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class Doctor {
    String id, name, specialty;
    List<String> appointmentTimes = new ArrayList<>();
    String email, password;

    // Constructor
    public Doctor(String name, String specialty, String email, String password) {
        this.id = UUID.randomUUID().toString().substring(0, 3);
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.password = password;
    }

    // Method to schedule appointments
    public void scheduleAppointment(Date appointmentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String formattedDate = sdf.format(appointmentDate);
        appointmentTimes.add(formattedDate);
        System.out.println("Appointment scheduled for " + name + " at " + formattedDate);
    }

    // Method to view doctor profile
    public void viewProfile() {
        System.out.println("ID: " + id + ", Name: " + name + ", Specialty: " + specialty);
        System.out.println("Scheduled Appointments: " + appointmentTimes);
    }

    // Method to edit doctor profile
    public void editProfile(String name, String specialty, String email, String password) {
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.password = password;
        System.out.println("Profile updated successfully!");
    }

    // Method for doctor login
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}

class Patient {
    String id, name, symptoms;
    String email, password;

    // Constructor
    public Patient(String name, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Method to add symptoms
    public void addSymptoms(String symptoms) {
        this.symptoms = symptoms;
        System.out.println("Symptoms added for " + name + ": " + symptoms);
    }

    // Method for patient login
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Method to book an appointment
    public void bookAppointment(Doctor doctor) {
        if (!doctor.appointmentTimes.isEmpty()) {
            System.out.println("Available appointments for Dr. " + doctor.name + ": " + doctor.appointmentTimes);
            System.out.print("Enter the time to book (MM-dd HH:mm): ");
            Scanner scanner = new Scanner(System.in);
            String chosenTime = scanner.nextLine();
    
            // Check if the chosen time exists in the doctor's available appointments
            if (doctor.appointmentTimes.contains(chosenTime)) {
                System.out.println("Appointment booked for " + name + " with Dr. " + doctor.name + " at " + chosenTime);
                doctor.appointmentTimes.remove(chosenTime);  // Remove the time from the doctor's available appointments
                
                // Create an Appointment object and add it to the appointments list
                Appointment appointment = new Appointment(this.id, doctor.id, chosenTime, this.symptoms);
                E_HealthCare.appointments.add(appointment);
            } else {
                System.out.println("Invalid time selected.");
            }
        } else {
            System.out.println("No available appointments for Dr. " + doctor.name);
        }
    }
    

    // Method to view patient profile
    public void viewProfile() {
        System.out.println("ID: " + id + ", Name: " + name + ", Symptoms: " + symptoms);
    }

    // Method to edit patient profile
    public void editProfile(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        System.out.println("Profile updated successfully!");
    }
}

class Appointment {
    String appointmentId, patientId, doctorId, appointmentDate;
    String symptoms;

    // Constructor
    public Appointment(String patientId, String doctorId, String appointmentDate, String symptoms) {
        this.appointmentId = UUID.randomUUID().toString();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.symptoms = symptoms;
    }

    // Method to view appointment details
    public void viewAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Symptoms: " + symptoms);
    }
}

public class E_HealthCare {
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print("\t*****************-------E-HealthCare App-----*************************************************\n");
            System.out.print("\t*                                                                                            *\n");
            System.out.print("\t*                  1. Register as Doctor                                                     *\n");
            System.out.print("\t*                  2. Register as Patient                                                    *\n");
            System.out.print("\t*                  3. Login as Doctor                                                        *\n");
            System.out.print("\t*                  4. Login as Patient                                                       *\n");
            System.out.print("\t*                  				                                                           *\n");
            System.out.print("\t*                                                                                            *\n");
            System.out.print("\t*                  5. EXIT                                                                   *\n");
            System.out.print("\t**********************************************************************************************\n");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerDoctor();
                    break;
                case 2:
                    registerPatient();
                    break;
                case 3:
                    loginDoctor();
                    break;
                case 4:
                    loginPatient();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    static void registerDoctor() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Doctor doctor = new Doctor(name, specialty, email, password);
        doctors.add(doctor);
        System.out.println("Doctor registered successfully!");
    }

    static void registerPatient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Patient patient = new Patient(name, email, password);
        patients.add(patient);
        System.out.println("Patient registered successfully!");
    }

    static void loginDoctor() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Doctor doctor = findDoctorByEmail(email);
        if (doctor != null && doctor.login(email, password)) {
            System.out.println("Login successful!");
            doctorMenu(doctor);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    static void doctorMenu(Doctor doctor) {
        while (true) {
            System.out.print("\t******************--- Doctor Menu ---*********************************************************\n");
            System.out.print("\t*                                                                                            *\n");
            System.out.print("\t*                  1. View Profile                                                           *\n");
            System.out.print("\t*                  2. Edit Profile                                                           *\n");
            System.out.print("\t*                  3. Schedule Appointment                                                   *\n");
            System.out.print("\t*                  4. LOGOUT                                                                 *\n");
            System.out.print("\t*                                                                                            *\n");
            System.out.print("\t*                  Choose An Option                                                          *\n");
            System.out.print("\t**********************************************************************************************\n");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    doctor.viewProfile();
                    break;
                case 2:
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new specialty: ");
                    String specialty = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();
                    doctor.editProfile(name, specialty, email, password);
                    break;
                case 3:
                    scheduleAppointmentForDoctor(doctor);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    static void scheduleAppointmentForDoctor(Doctor doctor) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        System.out.print("Enter appointment date and time (MM-dd HH:mm): ");
        String inputDate = scanner.nextLine();
        try {
            Date appointmentDate = sdf.parse(inputDate);
            doctor.scheduleAppointment(appointmentDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use MM-dd HH:mm.");
        }
    }

    static void loginPatient() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Patient patient = findPatientByEmail(email);
        if (patient != null && patient.login(email, password)) {
            System.out.println("Login successful!");
            patientMenu(patient);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    static void patientMenu(Patient patient) {
        while (true) {
            System.out.print("\t******************--- Patient Menu ---*********************************************************\n");
            System.out.print("\t*                                                                                             *\n");
            System.out.print("\t*                  1. View Profile                                                            *\n");
            System.out.print("\t*                  2. Edit Profile                                                            *\n");
            System.out.print("\t*                  3. Book Appointment                                                        *\n");
            System.out.print("\t*                  4. View Appointments                                                       *\n");
            System.out.print("\t*                  5. LOGOUT                                                                  *\n");
            System.out.print("\t*                                                                                             *\n");
            System.out.print("\t*                  Choose an Option                                                           *\n");
            System.out.print("\t***********************************************************************************************\n");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    patient.viewProfile();
                    break;
                case 2:
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();
                    patient.editProfile(name, email, password);
                    break;
                case 3:
                    System.out.print("Enter symptoms: ");
                    String symptoms = scanner.nextLine();
                    patient.addSymptoms(symptoms);
                    bookAppointment(patient);
                    break;
                case 4:
                    viewAppointments(patient);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    static void bookAppointment(Patient patient) {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }

        System.out.println("Available doctors:");
        for (Doctor doctor : doctors) {
            System.out.println("ID: " + doctor.id + ", Name: " + doctor.name + ", Specialty: " + doctor.specialty);
        }

        System.out.print("Enter doctor ID to book an appointment with: ");
        String doctorId = scanner.nextLine();
        Doctor doctor = findDoctorById(doctorId);

        if (doctor != null) {
            patient.bookAppointment(doctor);
        } else {
            System.out.println("Doctor not found.");
        }
    }

    static void viewAppointments(Patient patient) {
        for (Appointment appointment : appointments) {
            if (appointment.patientId.equals(patient.id)) {
                appointment.viewAppointment();
            }
        }
    }

    static Doctor findDoctorByEmail(String email) {
        for (Doctor doctor : doctors) {
            if (doctor.email.equals(email)) {
                return doctor;
            }
        }
        return null;
    }

    static Patient findPatientByEmail(String email) {
        for (Patient patient : patients) {
            if (patient.email.equals(email)) {
                return patient;
            }
        }
        return null;
    }

    static Doctor findDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.id.equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    static Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient .id.equals(id)) {
                return patient;
            }
        }
        return null;
    }
}