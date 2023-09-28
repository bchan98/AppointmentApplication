package sample.model;

import java.sql.Timestamp;

public class customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postCode;
    private String phone;
    private Timestamp createDate;
    private String createBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int divisionID;

    /** The customer object. Contains information pertaining to each customer.
     *
     * @param customerID The customer's ID.
     * @param customerName The customer's name.
     * @param address The customer's home address.
     * @param postCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param createDate The date the customer was created.
     * @param createBy The user that created the customer.
     * @param lastUpdate The last time the customer's information was updated.
     * @param lastUpdateBy The user that last updated the customer.
     * @param divisionID The first level division the customer is located in.
     */
    public customer(int customerID, String customerName, String address, String postCode, String phone, Timestamp createDate, String createBy, Timestamp lastUpdate, String lastUpdateBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionID = divisionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
