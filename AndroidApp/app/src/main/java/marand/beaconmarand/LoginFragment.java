package marand.beaconmarand;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import marand.beaconmarand.marand.beaconmarand.api.RestClient;
import marand.beaconmarand.marand.beaconmarand.utils.Util;

/**
 * Created by virusss8 on 13.7.2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText firstname, surname;
    private Spinner gender;
    private Button register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("LoginFragment", "onCreateView");
        view = inflater.inflate(R.layout.fragment_login, container, false);
        firstname = (EditText) view.findViewById(R.id.et_firstname);
        surname = (EditText) view.findViewById(R.id.et_surname);
        gender = (Spinner) view.findViewById(R.id.spinner_gender);
        register = (Button) view.findViewById(R.id.btn_register);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        switch (v.getId()) {
            case R.id.btn_register:
                Log.v("registrar",
                        firstname.getText() + " " +
                                surname.getText() + " " +
                                gender.getSelectedItem().toString());

                Log.v("doctorid", "" + Util.getDoctorID());
                if (Util.getDoctorID() != -1 && checkFields()) {
                    Log.v("doctorIDD", "yes");
                    ((MainActivity)this.getActivity()).setProgressDialog();
                    RestClient restClient = new RestClient();
                    restClient.firstLoginToDoctor(
                            this.getActivity(),
                            firstname.getText().toString(),
                            surname.getText().toString(),
                            gender.getSelectedItem().toString(),
                            Util.getDoctorID());
                }
                break;
        }
    }

    public boolean checkFields() {
        if (firstname.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this.getActivity(), "Prosim vnesite svoje ime...", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (surname.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this.getActivity(), "Prosim vnesite svoj priimek...", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender.getSelectedItem().toString().equalsIgnoreCase("IZBERITE")) {
            Toast.makeText(this.getActivity(), "Prosim izberite spol...", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
