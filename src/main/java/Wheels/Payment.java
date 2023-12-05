package Wheels;

public class Payment {
    // Set up the member variables
    private Customer customer = null;
    private int paymentId = 0;
    private static int paymentCount = 001;

    public Payment(Customer cust) {
// set the member variables
        customer = cust;
        paymentId = paymentCount++;
    }

    public String calculateTotalPayment(Hire hire) {
// call the private method
        return issueReceipt(hire);
    }

    private String issueReceipt(Hire hire) {

        String cust = hire.getCustomer().getName();
        String pCode = hire.getCustomer().getPostcode();

        return "Printing out receipt for '" + cust + "'...." + "In postcode: " + pCode + "\n" + "Hiring bike number '" + hire.getBike().getBikeNumber() + "' for " + hire.getNumberOfDays() + " days" + "\n" + hire.getBike().calculateCost(hire.getNumberOfDays());
    }
}