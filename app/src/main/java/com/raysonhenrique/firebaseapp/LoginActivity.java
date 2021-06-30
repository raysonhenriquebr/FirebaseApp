package com.raysonhenrique.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private Button btnLogin;
    private EditText editEmail, editSenha;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCadastrar = findViewById(R.id.login_btn_cadastrar);
        btnLogin = findViewById(R.id.login_btn_logar);
        editEmail = findViewById(R.id.login_edit_email);
        editSenha = findViewById(R.id.login_edit_senha);
        btnCadastrar.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);

                startActivity(intent);

        });
        btnLogin.setOnClickListener(view ->{
            logar();
        });
        //Caso o usuario logado
        if(auth.getCurrentUser() != null){
            String email = auth.getCurrentUser().getEmail();
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            //passar email p/ MainActivity
            intent.putExtra("email",email);
            startActivity(intent);

        }
    }
    public void logar() {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        //t -> é uma tarefa para logar
        Task<AuthResult> t  = auth.signInWithEmailAndPassword(email,senha);

        //listenner de sucesso
        t.addOnSuccessListener(authResult -> {
            Toast.makeText(this, "Bem vindo!" ,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(intent);
        });
        //Listener de falha
        t.addOnFailureListener( e -> {
            //parametro e -> Exception
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        });
    }

}
