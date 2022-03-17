package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropdown;
    TextView rev;
    Button addtocart,cart;
    LinearLayout mainlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page);


        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner2);
        cart=findViewById(R.id.addtocartproductbtn);
        addtocart = findViewById(R.id.addtocartproductbtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        rev=findViewById(R.id.reviewsbtn);
        /*mainlayout=findViewById(R.id.productdetailpagemainlinear);*/

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetailPage.this,CartScreen.class);
                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ProductDetailPage.this,CartScreen.class);
                Toast.makeText(getApplicationContext(), "Cart Screen", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


/*
                // create a frame layout
                FrameLayout fragmentLayout = new FrameLayout(ProductDetailPage.this);
// set the layout params to fill the activity
                fragmentLayout.setLayoutParams(new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
// set an id to the layout
                fragmentLayout.setId(R.id.fragmentLayout); // some positive integer
// set the layout as Activity content
                setContentView(fragmentLayout);
// Finally , add the fragment
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout,new ReviewsFragment()).commit();
*/

                /*ReviewsFragment revfrag= new ReviewsFragment();*/
                /*FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.productdetailpagemainlinear,fragmentLayout.);
                transaction.commit();*/

                Intent intent=new Intent(ProductDetailPage.this,ReviewsDetailPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text= adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}