package android.C868.Capstone.All.GUI;

import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.User;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editUserName;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = findViewById(R.id.editText_username);
        editPassword = findViewById(R.id.editText_password);

        //Add sample username and password.
        addSampleUser();

    }

    //Enter button to Term List if username and password are verified.
    public void enterButton(View view) {

        User currentUser;
        String username = editUserName.getText().toString();
        String password = editPassword.getText().toString();

        //Check for empty text fields.
        if (username.isEmpty() || password.isEmpty()) {

            Toast.makeText(MainActivity.this, "Username/Password required.", Toast.LENGTH_LONG).show();

        } else {

            //Compare text fields to verify username and password.
            repository user_repository = new repository(getApplication());
            List<User> allUsers = user_repository.getAllUsers();
            for (int i = 0; i < allUsers.size(); i++) {

                currentUser = allUsers.get(i);

                if (currentUser.getUserName().equals(username) && currentUser.getPassword().equals(password)) {

                    Intent intent = new Intent(MainActivity.this, termList.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(MainActivity.this, "Username/Password incorrect.", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    //Add sample username and password for testing purposes.
    public void addSampleUser() {
        User testUser = new User(0, "test", "test");
        repository addUser = new repository(getApplication());
        addUser.insert(testUser);
    }
}
