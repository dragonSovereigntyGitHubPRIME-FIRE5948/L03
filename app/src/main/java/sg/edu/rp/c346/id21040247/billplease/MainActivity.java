package sg.edu.rp.c346.id21040247.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    EditText amountInput;
    EditText numOfPaxInput;
    EditText discountInput;

    ToggleButton svsToggle;
    ToggleButton gstToggle;
    ToggleButton splitToggle;

    Button resetButton;
    Button enterButtton;

    RadioGroup rGrp1;

    TextView totalView;
    TextView eachPayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amount);
        numOfPaxInput = findViewById(R.id.numOfPax);
        discountInput = findViewById(R.id.discount);
        svsToggle = findViewById(R.id.SVS);
        splitToggle = findViewById(R.id.split);
        gstToggle = findViewById(R.id.gst);
        resetButton = findViewById(R.id.reset);
        enterButtton = findViewById(R.id.enter);
        rGrp1 = findViewById(R.id.radioGroup1);
        totalView = findViewById(R.id.total);
        eachPayView = findViewById(R.id.eachPay);

        //TOGGLE DEFAULT VALUE S
        svsToggle.setText("SVS");
        svsToggle.setTextOn("SVS");
        svsToggle.setTextOff("SVS");
        gstToggle.setText("GST");
        gstToggle.setTextOn("GST");
        gstToggle.setTextOff("GST");

        splitToggle.setText("SPLIT");
        splitToggle.setTextOn("SPLIT");
        splitToggle.setTextOff("SPLIT");
        //TOGGLE DEFAULT VALUE E

        //SVS & GST TOGGLE S
        svsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (svsToggle.isChecked()){
                    gstToggle.setChecked(false);
                }
                else{
                    gstToggle.setChecked(true);
                }

            }
        });

        gstToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gstToggle.isChecked()){
                    svsToggle.setChecked(false);
                }
                else{
                    svsToggle.setChecked(true);
                }

            }
        });
        //SVS & GST TOGGLE E

        enterButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //.trim() is to account for white space
                //can use isEmpyty() too (does not count white spaces)
                //checks for any missing inputs & requires either svs or gst to be checked (|| means if any or all missing)

                //if statement start to check
                //without if else, the app will keep crashing due to empty String/object error
                if ((amountInput.getText().toString().trim().isEmpty()) ||
                    (numOfPaxInput.getText().toString().trim().isEmpty())||
                    (!svsToggle.isChecked()) && (!gstToggle.isChecked()))
                {
                    Toast.makeText(MainActivity.this, "Missing Blanks/Unchecked Boxes", Toast.LENGTH_SHORT).show();
                }

                //Base calculations
                else{
                double discountPercentage = Double.parseDouble(discountInput.getText().toString())/100;

                double totalAmount = Double.parseDouble(amountInput.getText().toString())*
                        Double.parseDouble(numOfPaxInput.getText().toString());

                double totalAmountAfterDiscount = totalAmount-totalAmount*discountPercentage;

                //SVS & GST S
                //I made only only either 1 can be selected by accident, can select both is easier
                //I also made it SVS instead of no SVS so it matches with GST
                //can delete code on top if want both, calculation will be svs first then gst, both b4 discount & b4 vs/gst
                if (svsToggle.isChecked()){
                    totalAmountAfterDiscount = totalAmountAfterDiscount+totalAmount*0.10;
                }
                if (gstToggle.isChecked()){
                    totalAmountAfterDiscount = totalAmountAfterDiscount+totalAmount*0.07;
                }

                totalView.setText("Total Bill: $" + String.format("%.2f", totalAmountAfterDiscount));

                if (splitToggle.isChecked()){
                    eachPayView.setText("Each Pays: $" + String.format("%.2f", totalAmountAfterDiscount/Double.parseDouble(numOfPaxInput.getText().toString())));
                }
                //SVS & GST E

                //Cash or PayNow S
                int checkedRadioId = rGrp1.getCheckedRadioButtonId();
                if(checkedRadioId == R.id.r1){
                    totalView.setText("Total Bill: $" + String.format("%.2f", totalAmountAfterDiscount) + " in Cash");
                }
                else{
                    totalView.setText("Total Bill: $" + String.format("%.2f", totalAmountAfterDiscount) + " via PayNow to 912345678");
                }
                //Cash or PayNow E

            }}
        });

        //RESET S
        resetButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){

                amountInput.setText("");
                numOfPaxInput.setText("");
                discountInput.setText("0");
                totalView.setText("");
                eachPayView.setText("");
                svsToggle.setChecked(false);
                gstToggle.setChecked(false);

                RadioButton r1s = findViewById(R.id.r1);
                r1s.setChecked(true);

            }
            });
        //RESET E






    }
}
