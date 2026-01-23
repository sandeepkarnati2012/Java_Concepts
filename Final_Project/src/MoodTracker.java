import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MoodTracker{

    public static boolean isMoodValid(Mood mood, ArrayList<Mood> moodsList) throws InvalidMoodException {
        for(Mood tempMood: moodsList) {
            if (tempMood.equals(mood)) {
                throw new InvalidMoodException();
            }
        }
        return true;
    }
    public static boolean deleteMoods(LocalDate moodDate, ArrayList<Mood> moodsList) {
        boolean removed = false;
        for(Mood tempMood: moodsList) {
            if (tempMood.getDate().equals(moodDate)) {
                moodsList.remove(tempMood);
                removed = true;
            }
        }
        return removed;
    }
    public static boolean deleteMood(Mood mood, ArrayList<Mood> moodsList) {
        for(Mood tempMood: moodsList) {
            if (tempMood.equals(mood)) {
                moodsList.remove(tempMood);
                return true;
            }
        }
        return false;
    }
    public static boolean editMood(Mood moodToEdit, ArrayList<Mood> moodsList) {
        for(Mood tempMood: moodsList) {
            if (tempMood.equals(moodToEdit)) {
                tempMood.setNotes(moodToEdit.getNotes());
                return true;
            }
        }
        return false;
    }

    public static void searchMoods(LocalDate moodDate, ArrayList<Mood> moodsList) {
        boolean found = false;
        for(Mood tempMood: moodsList) {
            if (tempMood.getDate().equals(moodDate)) {
                found = true;
                System.out.println(tempMood);
            }
        }
        if(!found) {
            System.out.println("No matching records could be found!");
        }
    }

    public static void searchMood(Mood mood, ArrayList<Mood> moodsList) {
        boolean found = false;
    
        for(Mood tempMood: moodsList) {
            if (tempMood.equals(mood)) {
                found = true;
                System.out.println(tempMood);
            }
        }
        if(!found) {
            System.out.println("No matching records could be found!");
        }
    }
    public static void main(String[] args){
        ArrayList<Mood> moodsList= new ArrayList<>();
        Scanner scanner=new Scanner(System.in);
        while(true){
            System.out.println("Press 'a' to add mood\n" +
                                "'d' to delete mood(s)\n" +
                                "'e' to edit mood\n" +
                                "'s' to search for moods\n" +
                                "'M' to get all moods\n" +
                                "'w' to write the moods to a file\n" +
                                "Type 'Exit' to exit");
            String menuOptions=scanner.nextLine();
            switch(menuOptions) {
                case "a": 	// code to add mood
                            System.out.println("Enter the mood name");
                            String moodName=scanner.nextLine();
                            System.out.println("Are you tracking the mood for today? y/n");
                            String isForToday=scanner.nextLine();
                            Mood moodToAdd=null;
                            if(isForToday.equalsIgnoreCase("n")){
                                try{
                                    System.out.println("Input the date in MM/dd/yyyy format:");
                                    String moodDateStr=scanner.nextLine();
                                    DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
                                    LocalDate moodDate=LocalDate.parse(moodDateStr,dateFormatter);
                                    System.out.println("Input the time in HH:mm:ss format:");
                                    String moodTimeStr = scanner.nextLine();
                                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                    LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                                    System.out.println("Add notes about this mood");
                                    String moodNotes = scanner.nextLine();
                                    if(moodNotes.strip().equalsIgnoreCase("")) {
                                        moodToAdd = new Mood(moodName, moodDate, moodTime);
                                    } else {
                                        moodToAdd = new Mood(moodName, moodDate, moodTime, moodNotes);
                                    }
                                } 
                                catch (DateTimeParseException dfe) {
                                    System.out.println("Incorrect format of date or time. Cannot create mood.\n"+dfe);
                                    continue;
                                }
                            } else {
                                System.out.println("Add notes about this mood");
                                String moodNotes = scanner.nextLine();
                                if(moodNotes.strip().equalsIgnoreCase("")) {
                                    moodToAdd = new Mood(moodName);
                                } else {
                                    moodToAdd = new Mood(moodName, moodNotes);
                                }
                            }
                            try {
                                boolean isValid = isMoodValid(moodToAdd, moodsList);
                                if(isValid) {
                                    moodsList.add(moodToAdd);
                                    System.out.println("The mood has been added to the tracker");
                                    continue;
                                }
                            } catch(InvalidMoodException ime) {
                                System.out.println("The mood is not valid");
                            }


                case "d": 	// code to delete mood
                    System.out.println("Enter '1' to delete all moods by date\n"+
                    "Enter '2' to delete a specific mood");
                    String deleteVariant = scanner.nextLine();
                    if(deleteVariant.equals("1")) {
                    try {
                    System.out.println("Input the date in MM/dd/yyyy format:");
                    String moodDateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                    boolean areMoodsDeleted = deleteMoods(moodDate, moodsList);
                    if(areMoodsDeleted) {
                    System.out.println("The moods have been deleted");
                    } else {
                    System.out.println("No matching moods found");
                    }
                    } catch (DateTimeParseException dfe) {
                    System.out.println("Incorrect format of date. Cannot delete mood.");
                    continue;
                    }
                    } else if (deleteVariant.equals("2")) {
                    try {
                    System.out.println("Enter the mood name");
                    moodName = scanner.nextLine();
                    System.out.println("Input the date in MM/dd/yyyy format:");
                    String moodDateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                    System.out.println("Input the time in HH:mm:ss format:");
                    String moodTimeStr = scanner.nextLine();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                    Mood delMood = new Mood(moodName, moodDate, moodTime);
                    boolean isMoodDeleted = deleteMood(delMood, moodsList);
                    if(isMoodDeleted) {
                    System.out.println("The mood has been deleted");
                    } else {
                    System.out.println("No matching mood found");
                    }
                    } catch (DateTimeParseException dfe) {
                    System.out.println("Incorrect format of date or time. Cannot delete mood.");
                    continue;
                    }
}
                case "e": 	// code to edit mood
                Mood moodToEdit = null;
                try {
                    System.out.println("Enter the mood name");
                    moodName = scanner.nextLine();
                    System.out.println("Input the date in MM/dd/yyyy format:");
                    String moodDateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                    System.out.println("Input the time in HH:mm:ss format:");
                    String moodTimeStr = scanner.nextLine();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                    System.out.println("Add new notes about this mood");
                    String moodNotes = scanner.nextLine();
                    if(moodNotes.strip().equalsIgnoreCase("")) {
                        System.out.println("No notes entered");
                        continue;
                    } else {
                        moodToEdit = new Mood(moodName, moodDate, moodTime, moodNotes);
                        boolean isMoodEdited = editMood(moodToEdit, moodsList);
                        if(isMoodEdited) {
                            System.out.println("The mood has been successfully edited");
                        } else {
                            System.out.println("No matching mood could be found");
                        }
                    }
                } catch (DateTimeParseException dfe) {
                    System.out.println("Incorrect format of date or time. Cannot create mood.");
                    continue;
                }
                case "s": 	// code to search mood
                System.out.println("Enter '1' to search for all moods by date\n"+
                "Enter '2' to search for a specific mood");
                String searchVariant = scanner.nextLine();
                if(searchVariant.equals("1")) {
                try {
                    System.out.println("Input the date in MM/dd/yyyy format:");
                    String moodDateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                    searchMoods(moodDate, moodsList);
                    } catch (DateTimeParseException dfe) {
                    System.out.println("Incorrect format of date. Cannot search mood.");
                    continue;
                    }
                    } else if (searchVariant.equals("2")) {
                    try {
                    System.out.println("Enter the mood name");
                    moodName = scanner.nextLine();
                    System.out.println("Input the date in MM/dd/yyyy format:");
                    String moodDateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                    System.out.println("Input the time in HH:mm:ss format:");
                    String moodTimeStr = scanner.nextLine();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                    Mood delMood = new Mood(moodName, moodDate, moodTime);
                    searchMood(delMood, moodsList);
                    } catch (DateTimeParseException dfe) {
                    System.out.println("Incorrect format of date or time. Cannot search mood.");
                    continue;
                    }
                }
                case "M": 	// code to get all moods
                    for(Mood moodObj: moodsList) {
                        System.out.println(moodObj);
                }
                case "w": 	// code to write mood to a file
                            try(PrintWriter writer=new PrintWriter(new FileWriter("Moods.txt"))){
                                for(Mood mood:moodsList){
                                    writer.println(mood+"\n\n");
                                    System.out.println("moods are written to the file");
                                }
                            } catch(IOException e){
                                System.out.println("Error writing to file"+e.getMessage());
                            }
                case "Exit": 	System.out.println("Thank you for using the MoodTracker. Goodbye!");
                                break;
                default: 	System.out.println("Not a valid input!");
                            continue;
            }

        }  
    }
}