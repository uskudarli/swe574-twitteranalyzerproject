package com.example.twitteranalysisandroid;

public class User {

  private static User currentUser;
  
  private long id;
  private String firstName, lastName, email;
  private boolean emailNotificationsEnabled, phoneNotificationsEnabled;
  
  public User(){ 
  }
  
  public long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
  }
  
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEmailNotificationsEnabled() {
    return emailNotificationsEnabled;
  }

  public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
    this.emailNotificationsEnabled = emailNotificationsEnabled;
  }

  public boolean isphoneNotificationsEnabled() {
    return phoneNotificationsEnabled;
  }

  public void setphoneNotificationsEnabled(boolean phoneNotificationsEnabled) {
    this.phoneNotificationsEnabled = phoneNotificationsEnabled;
  }
  
  public static User getCurrentUser(){
    return currentUser;
  }
  
  public static void login(final String pEmail, final String pPassword, final Callback<User> pCallback){
    new Runnable(){
      @Override
      public void run() {
        // TODO: check user credentials against the database
        // assuming successful login
        currentUser = new User();
        currentUser.setId(1);
        currentUser.setEmail(pEmail);
        currentUser.setFirstName("Yildiz");
        currentUser.setLastName("Kabaran");
        currentUser.setEmailNotificationsEnabled(true);
        currentUser.setphoneNotificationsEnabled(false);
        
        if(pCallback != null){
          pCallback.onSuccess(currentUser);
        }
      }
    }.run();
  }
  
  public void saveChanges(final Callback<User> pCallback){
    new Runnable(){
      @Override
      public void run() {
        // TODO update changes to database
        // assume successful save
        if(pCallback != null){
          pCallback.onSuccess(User.this);
        }
      }
    }.run();
  }
}
