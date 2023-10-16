package ma;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.StarAdapter;
import ma.ensa.Star;
import ma.ensa.StarService;
import ma.ensa.list.R;


public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;

    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();

        recyclerView = findViewById(R.id.recycle_view);

        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String shareText = "Partagez votre texte ici"; // Remplacez par le texte que vous souhaitez partager
            shareText(shareText);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareText(String textToShare) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(textToShare)
                .getIntent();
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }


    public void init() {
        service.create(new Star("Hajar MACHMOUM", "https://media.licdn.com/dms/image/D4E03AQExJd-d-0hzYg/profile-displayphoto-shrink_800_800/0/1686318034456?e=2147483647&v=beta&t=7TpfVPHdmLZqbPUW-OFCXZUE3ZxdkVev5q5pn3jLbAA", 4));

        service.create(new Star("Roa EL DHIMNI", "https://media.licdn.com/dms/image/D4E03AQFRGhzwy2brWg/profile-displayphoto-shrink_800_800/0/1675349749975?e=2147483647&v=beta&t=1JBgiz-7nyFE_RqYWIBuFWFdWxPy1jLLxECT9_C3o8Q", 4));
        service.create(new Star("Fatiha DAOUDI", "https://www.oujdacity.net/thumbs/r800/data/Image/correspondants2/fatiha_daoudi.jpg", 5));
        service.create(new Star("Ali Berrada", "https://img.a.transfermarkt.technology/portrait/big/182573-1523371925.jpg?lm=1", 1));
        service.create(new Star("Karim Benani", "https://www.lequipe.fr/_medias/img-photo-jpg/cette-saison-karim-bennani-presentera-le-multiplex-de-ligue-1-pour-amazon-canal/1500000001521762/91:4,1006:615-828-552-75/c40f9.jpg", 5));
    }
}