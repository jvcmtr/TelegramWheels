package Wheels;

public class Bike {
    // create the BikeList
    protected static Bike[] bikeList = new Bike[5];
    // set up member variables
    protected int deposit = 0;
    protected int rate = 0;
    protected int bikeNumber = 0;

    /* This block is run when the class is loaded and sets up our bike store.
     * It arbitrarily populates the attributes: deposit, rate and bikeNumber */
    static {
        int j = 0;
        for (int i = 10; i < 15; i++) {
            Bike b = new Bike(i, i, (j * 100));
            bikeList[j] = b;
            j++;
        }
    }
    public Bike( int dep, int rat, int num){
// set the member variables
        deposit = dep;
        rate = rat;
        bikeNumber = num;
    }

    public int getDeposit () {
        return deposit;
    }
    public int getRate () {
        return rate;
    }
    public int getBikeNumber () {
        return bikeNumber;
    }
    public static Bike findBikeByNumber ( int bikeNum) {
        int numberOfBikes = bikeList.length;
        for (int i = 0; i < numberOfBikes; i++) {
            if (bikeList[i].getBikeNumber() == bikeNum) {
                System.out.println("Bike with number '" + bikeNum + "' found" + "\n");
                return bikeList[i];
            }
        }
// if we don't find the bike, tell the user and return nothing
        System.out.println("Bike with number '" + bikeNum + "' not found" + "\n");
        return null;
    }
    public String getDetails() {
        return "Details for bike number '" + bikeNumber + "'" + "\n" + "DEPOSIT: " + deposit + "\n" + "RATE: " + rate + "\n";
    }
    public String calculateCost(int numberOfDays){
        int cost = deposit + (rate * numberOfDays);
        return "COST would be $" + cost + "\n";
    }
}