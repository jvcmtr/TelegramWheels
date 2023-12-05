package Wheels;

public class Main {
    public static void main(String[] args) {

        IssueBikeUI ui = new IssueBikeUI();
        ui.getBikeDetails(100);
        ui.calculateCost(5);
        ui.createCustomer("Les Hargreaves", "PW2 6TR", 1462501339);
        ui.calculateTotalPayment();
    }
}