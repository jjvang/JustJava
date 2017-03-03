package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
       // Price of 1 cup of coffee
        int basePrice = 5;
        // If customer wants whipped cream, adds $1 to total price
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        // If customer wants chocolate, adds $2 to total price
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        // Calculates total price based on added toppings and quantity
        return quantity * basePrice;
    }

    public void submitOrder(View view) {
        // Getting the input from the edit text and making it into a string variable
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + "YEAH")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        displayMessage(priceMessage); (not needed anymore)
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */


    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name:" + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: "+ quantity;
        priceMessage = priceMessage + "\nTotal: $" + calculatePrice(addWhippedCream, addChocolate);
        priceMessage = priceMessage + "\nThank You!";
        //        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    public void increment (View view){
        if (quantity == 100){
            // Gives error message to user
            Toast.makeText(MainActivity.this, "You can not have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            // Exits method since quantity should stay at 100
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);
    }
    public void decrement (View view) {
        if (quantity == 1){
            // Gives error message to user
            Toast.makeText(MainActivity.this, "You must have at least 1 cup of coffee", Toast.LENGTH_SHORT).show();
            // Exits method since quantity should stay at 1
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}
