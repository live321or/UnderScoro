package com.samoylov.gameproject.authorization;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.samoylov.gameproject.Data;
import com.samoylov.gameproject.character.heroes.Hero;
import com.samoylov.gameproject.MainActivity;
import com.samoylov.gameproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    Cursor cursor;
    private EditText Name, UserName, UserPassword;
    private Button BnRegister;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);


        Name = view.findViewById(R.id.txt_name);
        UserName = view.findViewById(R.id.txt_user_name);
        UserPassword = view.findViewById(R.id.txt_password);
        BnRegister = view.findViewById(R.id.bn_reg);

        BnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
        return view;
    }



    public void performRegistration() {
        String name = Name.getText().toString();
        String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();
//        ContentValues cv = new ContentValues();
//        cv.put(DatabaseHelper.COLUMN_NAME1, name);
//        cv.put(DatabaseHelper.COLUMN_NAME2, username);
//        cv.put(DatabaseHelper.COLUMN_NAME3, password);
//        db.insert(DatabaseHelper.TABLE,null,cv);

        Call<User> call = MainActivity.apiInterface.performRegistration(name, username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {

//                    MainActivity.prefConfig.displayToast("Registration success...");
                    MainActivity.prefConfig.displayToast("ВАНДУФЛ "+response.body().getName()+" "+
                            "1000"+" "+response.body().getStr()+" "+response.body().getAgi()+" "+
                            response.body().getInt()+" "+response.body().getLuc()+" "+
                            response.body().getLvl()+" "+"Москва"+" "+
                            response.body().getHp()+" "+response.body().getEXP());

                    MainActivity.prefConfig.writeLoginStatus(true);
                    Data.cookie = "";
                    for(String s: response.raw().headers("Set-Cookie"))
                        Data.cookie+= "; " + s.split(";")[0];
                    Data.cookie = Data.cookie.substring(2);
                    Hero hero=new Hero(response.body().getName(),
                            1000,response.body().getStr(),response.body().getAgi(),
                            response.body().getInt(),response.body().getLuc(),
                            response.body().getLvl(),response.body().getLocation(),
                            response.body().getHp(),0);

                    hero.setLocation("Москва");
                    hero.setEXP(response.body().getEXP());


                    LoginFragment.loginFromActivityListener.performLogin(hero,Data.cookie);
                } else if (response.code() == 302) {
                    MainActivity.prefConfig.displayToast("User already exist...");
                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Registration failed: incorrect login/password content...");
                } else  if (response.code() == 500){
                    MainActivity.prefConfig.displayToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
        Name.setText("");
        UserName.setText("");
        UserPassword.setText("");
    }

}
