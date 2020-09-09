package com.samoylov.gameproject.authorization;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class LoginFragment extends Fragment {
    private TextView RedText;
    private EditText UserName, UserPassword;
    private Button LoginBn,lTest;
    public static OnLoginFromActivityListener loginFromActivityListener;

    public interface OnLoginFromActivityListener {
        public void performRegister();

        public void performLogin(Hero hero,String cookie);
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        RedText = view.findViewById(R.id.register_text);
        UserName = view.findViewById(R.id.user_namw);
        UserPassword = view.findViewById(R.id.user_pass);
        LoginBn = view.findViewById(R.id.login_bn);
        lTest = view.findViewById(R.id.login_test);

        LoginBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();

            }
        });

        lTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.cookie = "test1";
                Hero hero=new Hero("Test1",
                        1000,1,1,
                        1,1,
                        1,"Москва",
                        30,0);

                loginFromActivityListener.performLogin(hero,Data.cookie);
            }
        });

        RedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFromActivityListener.performRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFromActivityListener = (OnLoginFromActivityListener) activity;
    }

    public void performLogin() {
        final String username = UserName.getText().toString();
        String password = UserPassword.getText().toString();


        Call<User> call = MainActivity.apiInterface.performUserLogin(username, password);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    MainActivity.prefConfig.writeLoginStatus(true);
//                    loginFromActivityListener.performLogin(response.body().getName());
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

                    MainActivity.prefConfig.writeName(hero.getName());

                    loginFromActivityListener.performLogin(hero,Data.cookie);


                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Login Failed: incorrect login/password content...");
                }else if(response.code() == 500){
                    MainActivity.prefConfig.displayToast("Server Error");
//                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.displayToast("Offline Version...");
//                    loginFromActivityListener.performLogin(username);
                }else if(response.code() == 401)
                    MainActivity.prefConfig.displayToast("Login failed: incorrect password...");
                else if(response.code() == 423)
                    MainActivity.prefConfig.displayToast("You'v been baned, sorry.");

            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
        UserName.setText("");
        UserPassword.setText("");
    }
}

