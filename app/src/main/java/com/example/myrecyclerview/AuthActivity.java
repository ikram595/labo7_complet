package com.example.myrecyclerview;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myrecyclerview.databinding.ActivityAuthBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Obtenez les références aux champs de texte
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton=findViewById(R.id.loginButton);
        Button createAccountButton=findViewById(R.id.createAccountButton);
        // Lorsque l'utilisateur clique sur le bouton de connexion
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
        // Ajoutez ici les contrôles de saisie nécessaires
            // Exemple de contrôle de saisie pour l'e-mail
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(AuthActivity.this, "erreur dans la saisie d'email!",
                        Toast.LENGTH_SHORT).show();
            }
            if (email.length() ==0) {
                Toast.makeText(AuthActivity.this, "champ obligatoire !",
                        Toast.LENGTH_SHORT).show();
            }
// Exemple de contrôle de saisie pour le mot de passe (minimum 6caractères)
            if (password.length() ==0) {
                Toast.makeText(AuthActivity.this, "champ obligatoire !",
                        Toast.LENGTH_SHORT).show();
            }
// Exemple de contrôle de saisie pour le mot de passe (minimum 6caractères)
            if (password.length() < 6) {
                Toast.makeText(AuthActivity.this, "erreur dans la saisie de mot de passe!",
                        Toast.LENGTH_SHORT).show();
            }
        // Utilisez le service d'authentification Firebase pour la connexion
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
        // L'utilisateur est connecté avec succès
        // Ajoutez ici le code à exécuter après la connexion réussie
                            user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(AuthActivity.this, ProductActivity.class);
                            startActivity(intent);
                        } else {
        // La connexion a échoué, affichez un message d'erreur
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        // Lorsque l'utilisateur clique sur le bouton de création de compte
        createAccountButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
// Ajoutez ici les contrôles de saisie nécessaires
            // Exemple de contrôle de saisie pour l'e-mail
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(AuthActivity.this, "erreur dans la saisie d'email!",
                        Toast.LENGTH_SHORT).show();
            }
            if (email.length() ==0) {
                Toast.makeText(AuthActivity.this, "champ obligatoire !",
                        Toast.LENGTH_SHORT).show();
            }
// Exemple de contrôle de saisie pour le mot de passe (minimum 6caractères)
            if (password.length() ==0) {
                Toast.makeText(AuthActivity.this, "champ obligatoire !",
                        Toast.LENGTH_SHORT).show();
            }
            if (password.length() < 6) {
                Toast.makeText(AuthActivity.this, "erreur dans la saisie de mot de passe!",
                        Toast.LENGTH_SHORT).show();
            }

// Utilisez le service d'authentification Firebase pour la création decompte
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,
                            password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
// Le compte a été créé avec succès
                            Toast.makeText(AuthActivity.this, "Votre compte a été créé avec succès",
                                    Toast.LENGTH_SHORT).show();
// Ajoutez ici le code à exécuter après la créationréussie du compte
                        } else {
// La création du compte a échoué, affichez un messaged'erreur
                            Toast.makeText(AuthActivity.this, "La création du votre compte a échoué, réseillez SVP",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        });

    }


}
