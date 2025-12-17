package Entities.AdminRequests;

import Entities.Users.Admin;
import Entities.Users.Customer;

public abstract class AdminRequest {
    private String requestID;
    private String requestType;
    private Customer customer;
    private Admin admin;
    private String description;
    private RequestStatus requestStatus;
    public AdminRequest(String requestID, String requestType,String description, Customer customer, Admin admin) {
        this.requestID = requestID;
        this.requestType = requestType;
        this.description = description;
        this.customer = customer;
        this.admin = admin;
        requestStatus = RequestStatus.PENDING;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
