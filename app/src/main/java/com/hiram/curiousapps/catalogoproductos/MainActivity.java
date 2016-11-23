package com.hiram.curiousapps.catalogoproductos;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Puente {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    BuscarFragment buscar;
    LectorFragment lector;
    RegistrarFragment registrar;
    ProductosSQLiteHelper bdProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bdProductos = new ProductosSQLiteHelper(this, "DBProductos", null, 1);

        //Instanciamos los fragmentos a utilizar
        buscar = new BuscarFragment();
        lector = new LectorFragment();
        registrar = new RegistrarFragment();

        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Mi Toolbar");
        getSupportActionBar().setTitle("Catalogos");

        //Instanciamos el ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        //Instanciamos el TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(lector, "LECTOR");
        viewPagerAdapter.addFragment(buscar, "BUSCAR");
        viewPagerAdapter.addFragment(registrar, "REGISTRAR");
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
            //Log.d("CODIGO: ", scanContent);
            buscar.setCodigoBarras(scanContent);
            //registrar.setCodigoBarras(scanContent);
            viewPager.setCurrentItem(1);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        //Si llego por lo visto, importare el metodo del otro ejemplo, aunque aqui la cosa va
        //a estar rara porque en realidad la info debería recibirla el fragment, en un input
        //el main activity solo tiene tabs D:
        /* ok, en la documentacion viene una forma para recibirlo en fragmetn pero
        * depende mucho de si ves que existe Fragmente , fragmentActivity y no recuerdo cuales mas
        * segun recuerdo solo era compatible con uno , al menos en este sdk
        * anyway si necesitas pasarle la info al textView o EditText de tu fragment
        * seria haciendo public los elmentos de tu fragment*/
        //Supongo que tendre que crear una instancia y metodos set para definir esa info y que sea
        // la main activity quien los manipule no? , una vez intente eso pero no me salio pero igual
        //intentale solo lo hice por unas horas porque tenia fecha limite de entrega XD
        //vale lo haré pero ya mejor mañana con mas calma que vea mas opciones, thx rene,   sale de nda  :)
    }

    @Override
    public void accionador(String accion, String codigo) {
        if(accion.equals("registrar")){
            viewPager.setCurrentItem(2);
            registrar.setCodigoBarras(codigo);
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.borrar) {
            Log.i("LAnzando", "A ver si entro");
            SQLiteDatabase db = bdProductos.getWritableDatabase();
            db.execSQL("delete from Productos");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



















