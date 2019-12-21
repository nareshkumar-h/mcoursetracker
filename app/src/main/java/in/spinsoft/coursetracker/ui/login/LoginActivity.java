package in.spinsoft.coursetracker.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import in.spinsoft.coursetracker.MainActivity;
import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.SideMenuActivity;
import in.spinsoft.coursetracker.client.AuthClient;
import in.spinsoft.coursetracker.client.RestClient;
import in.spinsoft.coursetracker.dto.LoginDTO;
import in.spinsoft.coursetracker.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {



    private static final String TAG = "LoginActivity";
    EditText usernameTxt;

    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((Toolbar) findViewById(R.id.loginToolbar)).setTitle("Login");

        usernameTxt = (EditText) findViewById(R.id.usernameText);
        passwordTxt = (EditText) findViewById(R.id.passwordText);
        checkAlreadyLogin();
    }

    public void checkAlreadyLogin(){
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String username = sp.getString("username", null);
        if (username != null){
            nextPage();
        }
    }


    public void login(View view){

        if (usernameTxt == null  ){

            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if (passwordTxt == null  ){

            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            LoginDTO loginDTO = new LoginDTO(username,password);
            login(loginDTO, this);

        }


    }

    public void login(LoginDTO loginDTO, final LoginActivity mainActivity) {

        findViewById(R.id.loading2).setVisibility(View.VISIBLE);
        AuthClient courseClient = RestClient.getAuthClient();


        Call<User> call = courseClient.login(loginDTO);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call1, Response<User> response) {

                User user = response.body();
                if ( response.isSuccessful()) {
                    Log.d("Login", "" + response.isSuccessful());
                    findViewById(R.id.loading2).setVisibility(View.GONE);

                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.putString("username", user.getUsername());
                    Ed.commit();
                    nextPage();
                }
                else{
                    findViewById(R.id.loading2).setVisibility(View.GONE);
                    Toast.makeText(mainActivity,"Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call1, Throwable t) {

                Toast.makeText(mainActivity,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                Log.e(TAG, t.toString());
            }
        });
    }


    public void nextPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
