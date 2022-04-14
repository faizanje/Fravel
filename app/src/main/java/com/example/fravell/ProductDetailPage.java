package com.example.fravell;

import android.content.Intent;
import android.graphics.Paint;
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

import com.bumptech.glide.Glide;
import com.example.fravell.Utils.CartUtils;
import com.example.fravell.Models.Product;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.NumberUtils;
import com.example.fravell.databinding.ActivityProductDetailPageBinding;

public class ProductDetailPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropdown;
    TextView rev;
    Button addtocart, cart;
    LinearLayout mainlayout;
    ActivityProductDetailPageBinding binding;
    Product product;
    String selectedVariant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        product = (Product) getIntent().getSerializableExtra(Constants.KEY_PRODUCT);

        populateData();
        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner2);
        cart = findViewById(R.id.addtocartproductbtn);
        addtocart = findViewById(R.id.addtocartproductbtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        rev = findViewById(R.id.reviewsbtn);
        /*mainlayout=findViewById(R.id.productdetailpagemainlinear);*/

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailPage.this, CartScreen.class);
                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        binding.tvReadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailPage.this, ReviewsDetailPage.class);
                intent.putExtra(Constants.KEY_PRODUCT, product);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedVariant == null || selectedVariant.equals("Size")) {
                    Toast.makeText(ProductDetailPage.this, "Please select a size first", Toast.LENGTH_SHORT).show();
                    return;
                }
                CartUtils.addToCart(product, selectedVariant, 1);
                Toast.makeText(ProductDetailPage.this, "Item added to cart", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(-ProductDetailPage.this, CartScreen.class);
//                Toast.makeText(getApplicationContext(), "Cart Screen", Toast.LENGTH_LONG).show();
//                startActivity(intent);

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

                Intent intent = new Intent(ProductDetailPage.this, ReviewsDetailPage.class);
                startActivity(intent);
            }
        });
    }

    private void populateData() {
        binding.tvTitle.setText(product.getTitle());
        Glide.with(this)
                .load(product.getImage())
                .into(binding.ivImage);
        binding.tvDescription.setText(product.getDescription());
        binding.tvManufacturer.setText(product.getManufacturer());

        binding.tvPrice.setText("$" + NumberUtils.round(product.getPrice(), 1));
        binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        double discountedPrice = NumberUtils.getDiscountedPrice(product.getPrice());
        binding.tvDiscountedPrice.setText("$" + NumberUtils.round(discountedPrice, 1));


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedVariant = adapterView.getItemAtPosition(i).toString();
//        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}