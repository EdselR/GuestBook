package com.narindo.guestbook.util;

public class packageClass {

    private String dispatcher;
    private String courierService;
    private String description;
    private String deliveryType;
    private String recipient;
    private String sender;
    private String receiving_sending_time;

    public packageClass(){
    }

    public packageClass(String dispatcher, String courierService, String description,
                 String deliveryType, String recipient, String sender, String receiving_sending_time){

        this.dispatcher = dispatcher;
        this. courierService = courierService;
        this.description = description;
        this.deliveryType = deliveryType;
        this.recipient = recipient;
        this.sender = sender;
        this.receiving_sending_time = receiving_sending_time;
    }

    //setters
    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiving_sending_time(String receiving_sending_time) {
        this.receiving_sending_time = receiving_sending_time;
    }

    //getters
    public String getCourierService() {
        return courierService;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getDescription() {
        return description;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiving_sending_time() {
        return receiving_sending_time;
    }
}
