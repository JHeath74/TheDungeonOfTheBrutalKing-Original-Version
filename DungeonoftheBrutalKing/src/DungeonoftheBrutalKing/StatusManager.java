
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatusManager {
 private List<Status> activeStatuses = new ArrayList<>();
 private List<String> myChar; // Assuming myChar is accessible here

 // Add a new status to the manager
 public void addStatus(Status status) {
  activeStatuses.add(status);
 }

 // Update all statuses based on elapsed time
 public void updateStatuses(Charecter character, int timeElapsed) {
  Iterator<Status> iterator = activeStatuses.iterator();
  while (iterator.hasNext()) {
   Status status = iterator.next();
   status.reduceDuration(timeElapsed);
   status.applyEffect(character);
   if (status.isExpired()) {
    iterator.remove();
   }
  }
 }

 // Get a list of all active statuses
 public List<Status> getActiveStatuses() {
  return new ArrayList<>(activeStatuses);
 }

 // Remove a specific status by name
 public void removeStatusByName(String statusName) {
  activeStatuses.removeIf(status -> status.getName().equals(statusName));
 }

 // Check if a specific status is active
 public boolean hasStatus(String statusName) {
  return activeStatuses.stream().anyMatch(status -> status.getName().equals(statusName));
 }

 // Add a status to myChar[28]
 public void addStatusToMyChar(String status) {
  String currentStatuses = myChar.get(28); // Get current statuses
  if (!currentStatuses.contains(status)) { // Avoid duplicates
   if (!currentStatuses.isEmpty()) {
    currentStatuses += ","; // Add delimiter if not empty
   }
   currentStatuses += status; // Append new status
   myChar.set(28, currentStatuses); // Update myChar
  }
 }

 // Remove a status from myChar[28]
 public void removeStatusFromMyChar(String status) {
  String currentStatuses = myChar.get(28); // Get current statuses
  String[] statusesArray = currentStatuses.split(","); // Split into array
  StringBuilder updatedStatuses = new StringBuilder();
  for (String s : statusesArray) {
   if (!s.equals(status)) { // Exclude the status to be removed
    if (updatedStatuses.length() > 0) {
     updatedStatuses.append(","); // Add delimiter
    }
    updatedStatuses.append(s);
   }
  }
  myChar.set(28, updatedStatuses.toString()); // Update myChar
 }

 // Retrieve all statuses from myChar[28]
 public List<String> getStatusesFromMyChar() {
  String currentStatuses = myChar.get(28); // Get current statuses
  if (currentStatuses.isEmpty()) {
   return new ArrayList<>(); // Return empty list if no statuses
  }
  return new ArrayList<>(List.of(currentStatuses.split(","))); // Convert to list
 }
}
