package uni.dubna.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import uni.dubna.app.data.model.LoggedInUser;
import uni.dubna.app.data.model.Role;
import uni.dubna.app.databinding.ActivityMainBinding;
import uni.dubna.app.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private LoggedInUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserDataFromBundle();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.nav_log_out) {
//                    Intent intent = new Intent(getApplication(), LoginActivity.class).putExtra(LoginActivity.SHOULD_CLEAR_CACHED_USER_DATA, true);
//                    startActivity(intent);
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//        });

        if (user.getRole() != Role.TEACHER) {
            navigationView.getMenu().removeItem(R.id.nav_account);
        }

        if (user.getRole() == Role.TEACHER || user.getRole() == Role.ADMIN) {
            binding.appBarMain.fab.setVisibility(View.VISIBLE);
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navController.navigate(R.id.nav_add_event);
                }
            });
        }
    }

    private void getUserDataFromBundle() {
        Bundle b = getIntent().getExtras();
        String userId = b.getString(ID_ARGUMENT);
        if (userId == null) throw new RuntimeException(CANNOT_BE_NULL_ERROR);

        String name = b.getString(NAME_ARGUMENT);
        if (name == null) throw new RuntimeException(CANNOT_BE_NULL_ERROR);

        String role = b.getString(ROLE_ARGUMENT);
        if (role == null) throw new RuntimeException(CANNOT_BE_NULL_ERROR);

        user = new LoggedInUser(userId, name, Role.valueOf(role.toUpperCase()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static final String ID_ARGUMENT = "user_id";
    public static final String NAME_ARGUMENT = "name";
    public static final String ROLE_ARGUMENT = "role";

    private static final String CANNOT_BE_NULL_ERROR = "This arg cannot be null";

}