package deepshikha.jangidyahoo.finalyearproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

public class dialer_fragment extends Fragment {
    TextView phoneNumberEditText;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnZero;
    Button btnStar;
    Button btnHash;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialer, container, false);

        phoneNumberEditText = view.findViewById(R.id.dialPhoneNumber);
         //Inflate the layout for this fragment
        btnOne = view.findViewById(R.id.ButtonOne);
        btnTwo = view.findViewById(R.id.ButtonTwo);
        btnThree = view.findViewById(R.id.ButtonThree);
        btnFour = view.findViewById(R.id.ButtonFour);
        btnFive = view.findViewById(R.id.ButtonFive);
        btnSix = view.findViewById(R.id.ButtonSix);
        btnSeven = view.findViewById(R.id.ButtonSeven);
        btnEight = view.findViewById(R.id.ButtonEight);
        btnNine = view.findViewById(R.id.ButtonNine);
        btnZero = view.findViewById(R.id.ButtonZero);
        btnStar = view.findViewById(R.id.ButtonStar);
        btnHash = view.findViewById(R.id.ButtonHash);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "1");
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "2");
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "3");
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "4");
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "5");
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "6");
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "7");
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "8");
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "9");
            }
        });
        return view;
    }


}
