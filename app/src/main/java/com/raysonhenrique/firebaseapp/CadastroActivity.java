package com.raysonhenrique.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.raysonhenrique.firebaseapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raysonhenrique.firebaseapp.R;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class CadastroActivity extends AppCompatActivity {
    private Button btnCadastrar;
    private EditText  editEmail, editSenha, editNome;

    //Referência para autenticação
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
    // private DatabaseReference database = FirebaseStorage.getInstance().getReference("users");


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadastrar = findViewById(R.id.cadastro_btn_cadastrar);

        editEmail = findViewById(R.id.cadastro_edit_email);
        editNome = findViewById(R.id.cadastro_edit_nome);
        editSenha = findViewById(R.id.cadastro_edit_senha);

        btnCadastrar.setOnClickListener(v -> {
            cadastrar();
        });

    }

    public  void cadastrar(){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String nome = editNome.getText().toString();
        if(email.isEmpty() || senha.isEmpty() || nome.isEmpty()){
            Toast.makeText(this, "Preencha os campos",Toast.LENGTH_LONG).show();
            return;
        }

        //Criar um usuário com email e senha

        Task<AuthResult> t = auth.createUserWithEmailAndPassword(email,senha);
        t.addOnCompleteListener(task -> {
            //Listener excutado com sucesso ou fracasso
            if(task.isSuccessful()){
                //Toast.makeText(getApplicationContext(),"Usuário criado com sucesso!",
                //  Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Erro",Toast.LENGTH_LONG).show();
            }
        });

        t.addOnSuccessListener(authResult -> {
            //request para mudar o nome do uauário
            UserProfileChangeRequest update = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(nome)
                    .build();

            //Inserir no database
            User u = new User(authResult.getUser().getUid(),email,nome);
            database.child(u.getId()).setValue(u);

            //Setando nome do usuário
            authResult.getUser().updateProfile(update)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Nome Usuário criado com sucesso!",
                                    Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"Erro Nome",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

    }
}