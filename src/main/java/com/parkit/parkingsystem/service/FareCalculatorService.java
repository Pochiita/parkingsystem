package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, boolean discount){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

            long inHour = (ticket.getInTime().toInstant().toEpochMilli()/3600);
            long outHour = (ticket.getOutTime().toInstant().toEpochMilli()/3600);
            float duration = (float) (outHour - inHour)/1000;

            if (duration <= 0.5){
                ticket.setPrice(0.0);
                return;
            }

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                if (discount){
                    ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR)*0.95);
                }else {
                    ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                }
                break;
            }
            case BIKE: {
                if (discount){
                    ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR)*0.95);
                }else {
                    ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                }
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    public void calculateFare (Ticket ticket){
        calculateFare(ticket,false);
    }
}