package uniempresarial.edu.ue.apis;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import classes.User;
import model.UserDAO;

public class FiltrarActivity extends AppCompatActivity {

    private ListView listUsers;
    private EditText etBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);
        begin();
        userList("");
    }

    private void begin(){
        listUsers = findViewById(R.id.lvLista);
        etBuscar = findViewById(R.id.etBuscar);
        etBuscar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}

        });

        listUsers.setOnItemClickListener((adapterView, view, position, l) -> {
            User selectedUser = (User) adapterView.getItemAtPosition(position);
            openEditarActivity(selectedUser);
        });

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FiltrarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void userList(String filter){
        UserDAO userDAO = new UserDAO(this, listUsers);
        ArrayList<User> userList;
        if (filter.isEmpty()) {
            userList = userDAO.getUserList();
        } else {
            userList = userDAO.getFilteredUserList(filter);
        }
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listUsers.setAdapter(adapter);
    }

    private void openEditarActivity(User user) {
        Intent intent = new Intent(FiltrarActivity.this, ActualizarActivity.class);
        intent.putExtra("USER_DATA", user);
        startActivity(intent);
        finish();
    }
}