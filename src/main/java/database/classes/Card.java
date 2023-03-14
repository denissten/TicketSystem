package database.classes;

public class Card {
    private int id_card;
    private String bankName;
    private String cardNumber;
    private String date;
    private String CVV;
    private int user_id;
    private float balance;

    public Card(int id_card, String bankName, String cardNumber, String date, String CVV, int user_id, float balance) {
        this.id_card = id_card;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.date = date;
        this.CVV = CVV;
        this.user_id = user_id;
        this.balance = balance;
    }

    public Card(){

    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
