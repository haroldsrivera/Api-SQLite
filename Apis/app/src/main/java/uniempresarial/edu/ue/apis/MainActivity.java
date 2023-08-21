package uniempresarial.edu.ue.apis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import classes.User;
import model.UserDAO;

public class MainActivity extends AppCompatActivity {

    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;
    private TextView tvStatus;
    private ListView listUsers;
    int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;
    int status;

    SQLiteDatabase dbusuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        begin();

    }

    private void begin(){
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etContra = findViewById(R.id.etContra);
        listUsers = findViewById(R.id.lvLista);
    }

    private boolean isValidName(String name) {

        if (!Character.isUpperCase(name.charAt(0))) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFields(){
        usuario = etUsuario.getText().toString().trim();
        nombres = etNombres.getText().toString().trim();
        apellidos = etApellidos.getText().toString().trim();
        contra = etContra.getText().toString().trim();
        boolean valid = true;

        try {
            documento = Integer.parseInt(etDocumento.getText().toString());

            if (documento <= 0) {
                etDocumento.setError("El documento no puede ser 0 o negativo");
                valid = false;
            }

        } catch (NumberFormatException e) {
            etDocumento.setError("Ingrese un número válido");
            valid = false;
        }

        if (!isValidName(nombres)) {
            etNombres.setError("El nombre debe comenzar con mayúscula y no contener números");
            valid = false;
        }

        if (!isValidName(apellidos)) {
            etApellidos.setError("El apellido debe comenzar con mayúscula y no contener números");
            valid = false;
        }

        if (usuario.isEmpty() || usuario.length() < 5 || !usuario.matches("^[a-zA-Z0-9]+$")) {
            etUsuario.setError("El usuario debe tener al menos 5 caracteres y solo puede contener letras y números");
            valid = false;
        }

        if (contra.isEmpty() || contra.length() < 6) {
            etContra.setError("La contraseña debe tener al menos 6 caracteres");
            valid = false;
        }

        return valid;
    }

    public void registerUser(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (checkFields()){
            UserDAO userDAO = new UserDAO(this, view);
            User user = new User();
            user.setDocument(documento);
            user.setUser(usuario);
            user.setNames(nombres);
            user.setLastNames(apellidos);
            user.setPass(contra);
            user.setStatus(1);
            userDAO.insertUser(user);
            limpiar(view);
        }

    }

    public void limpiar(View view) {
        etDocumento.setText("");
        etUsuario.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etContra.setText("");
    }


    public void callUserList(View view){
        Intent intent = new Intent(MainActivity.this, FiltrarActivity.class);
        startActivity(intent);
    }
}