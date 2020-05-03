package com.NRB.gpas;

public class VisitorInfo {
    private int id;
    private String Name;
    private String Address;
    private String Email;
    private String Contact;
    private String Org;
    private String VDate;
    private String VTime;
    private String LTime;
    private String ConcernPerson;
    private String Purpose;
    private String Status;
    private String Vehicle;
    private String startMeet;
    private String closeMeet;
    public VisitorInfo(int id, String name, String address, String email, String contact, String vehicle, String org, String VDate, String VTime, String LTime, String concernPerson, String purpose, String status,String startMeet, String closeMeet) {
        this.id = id;
        Name = name;
        Address = address;
        Email = email;
        Contact = contact;
        Org = org;
        this.VDate = VDate;
        this.VTime = VTime;
        this.LTime = LTime;
        ConcernPerson = concernPerson;
        Purpose = purpose;
        Status = status;
        Vehicle=vehicle;
        this.startMeet = startMeet;
        this.closeMeet = closeMeet;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getContact() {
        return Contact;
    }

    public String getOrg() {
        return Org;
    }

    public String getVDate() {
        return VDate;
    }

    public String getVTime() {
        return VTime;
    }

    public String getLTime() {
        return LTime;
    }

    public String getConcernPerson() {
        return ConcernPerson;
    }

    public String getPurpose() {
        return Purpose;
    }

    public String getStatus() {
        return Status;
    }

    public String getStartMeet() {
        return startMeet;
    }

    public String getCloseMeet() {
        return closeMeet;
    }
}
