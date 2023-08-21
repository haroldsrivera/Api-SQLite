package uniempresarial.edu.ue.apis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import classes.User;
import model.UserDAO;

public class ActualizarActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        user = (User) getIntent().getSerializableExtra("USER_DATA");

        EditText etNombres = findViewById(R.id.etNombres);
        EditText etApellidos = findViewById(R.id.etApellidos);
        EditText etUsuario = findViewById(R.id.etUsuario);
        EditText etContra = findViewById(R.id.etContra);

        etNombres.setText(user.getNames());
        etApellidos.setText(user.getLastNames());
        etUsuario.setText(user.getUser());
        etContra.setText(user.getPass());

        Button btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario(user);
            }
        });

        Button btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

    }

    private void eliminarUsuario(User userToDelete) {
        UserDAO userDAO = new UserDAO(this, findViewById(android.R.id.content));
        userDAO.deleteUser(userToDelete);

        Intent intent = new Intent(ActualizarActivity.this, FiltrarActivity.class);
        startActivity(intent);
        finish();
    }

    private void actualizarDatos() {
        EditText etNombres = findViewById(R.id.etNombres);
        EditText etApellidos = findViewById(R.id.etApellidos);
        EditText etUsuario = findViewById(R.id.etUsuario);
        EditText etContra = findViewById(R.id.etContra);

        String nombres = etNombres.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String usuario = etUsuario.getText().toString();
        String contra = etContra.getText().toString();

        if (nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contra.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Completa todos los campos antes de actualizar", Snackbar.LENGTH_LONG).show();
            return;
        }

        user.setNames(nombres);
        user.setLastNames(apellidos);
        user.setUser(usuario);
        user.setPass(contra);

        UserDAO userDAO = new UserDAO(this, findViewById(android.R.id.content));
        userDAO.updateUser(user);

        Intent intent = new Intent(ActualizarActivity.this, FiltrarActivity.class);
        startActivity(intent);
        finish();
    }
}