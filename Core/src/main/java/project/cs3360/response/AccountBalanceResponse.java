package project.cs3360.response;

public class AccountBalanceResponse extends AbstractResponse{
    private final boolean state;
    private final double balance;

    public AccountBalanceResponse(boolean state, double balance) {
        this.state = state;
        this.balance = balance;
    }

    protected AccountBalanceResponse() {
        this.state = false;
        this.balance = 0;
    }

    public boolean isSuccess() {
        return state;
    }

    public double getBalance() {
        return balance;
    }
}
