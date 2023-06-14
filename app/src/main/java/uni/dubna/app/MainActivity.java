package uni.dubna.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import uni.dubna.app.data.model.Role;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.databinding.ActivityMainBinding;
import uni.dubna.app.ui.edit_event.EditEventFragment;
import uni.dubna.app.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserDataFromBundle();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View navHeader = binding.navView.getHeaderView(0);
        ((TextView) navHeader.findViewById(R.id.nav_header_name)).setText(user.getFullName());
        ((TextView) navHeader.findViewById(R.id.nav_header_mail)).setText(user.getEmail());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_log_out) {
                Intent intent = new Intent(getApplication(), LoginActivity.class).putExtra(LoginActivity.SHOULD_CLEAR_CACHED_USER_DATA, true);
                startActivity(intent);
                finish();
                drawer.close();
                return true;
            } else if (item.getItemId() == R.id.nav_account) {
                Bundle b = new Bundle();
                b.putString(EditEventFragment.USER_ARG, new Gson().toJson(user));
                navController.navigate(R.id.nav_account, b);
                drawer.close();
                return true;
            } else if (item.getItemId() == R.id.nav_report) {
                navController.navigate(R.id.nav_report);
                drawer.close();
                return true;
            }
            return false;
        });

        if (user.getRole() == Role.STUDENT) {
            navigationView.getMenu().removeItem(R.id.nav_account);
        }
        if (user.getRole() != Role.ADMIN) {
            navigationView.getMenu().removeItem(R.id.nav_report);
        }

//        if (user.getRole() == Role.TEACHER || user.getRole() == Role.ADMIN) {
//            binding.appBarMain.fab.setVisibility(View.VISIBLE);
//            binding.appBarMain.fab.setOnClickListener(view -> {
//                Bundle b = new Bundle();
//                b.putString(EditEventFragment.USER_ARG, new Gson().toJson(user));
//
//                navController.navigate(R.id.nav_add_event, b);
//            });
//        }
    }

    private void getUserDataFromBundle() {

        Bundle b = getIntent().getExtras();
        String userJson = b.getString(USER_ARGUMENT);
        if (userJson == null) throw new RuntimeException(CANNOT_BE_NULL_ERROR);
        user = new Gson().fromJson(userJson, UserData.class);

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

    public static final String USER_ARGUMENT = "user";

    private static final String CANNOT_BE_NULL_ERROR = "This arg cannot be null";

}